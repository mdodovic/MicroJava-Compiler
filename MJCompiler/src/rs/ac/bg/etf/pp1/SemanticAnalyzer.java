package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.ArrayBrackets;
import rs.ac.bg.etf.pp1.ast.BooleanValue;
import rs.ac.bg.etf.pp1.ast.CharValue;
import rs.ac.bg.etf.pp1.ast.ConcreteType;
import rs.ac.bg.etf.pp1.ast.ConstDecl;
import rs.ac.bg.etf.pp1.ast.ConstDeclType;
import rs.ac.bg.etf.pp1.ast.CorrectMethodDecl;
import rs.ac.bg.etf.pp1.ast.IntegerValue;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodTypeName;
import rs.ac.bg.etf.pp1.ast.MoreConstDeclarations;
import rs.ac.bg.etf.pp1.ast.MoreSingleLineConstDeclarations;
import rs.ac.bg.etf.pp1.ast.ProgName;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.Type;
import rs.ac.bg.etf.pp1.ast.VarFromLastPart;
import rs.ac.bg.etf.pp1.ast.VarFromMultiplePart;
import rs.ac.bg.etf.pp1.ast.VariableIsArray;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.ac.bg.etf.pp1.ast.VoidType;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticAnalyzer extends VisitorAdaptor{

	// Symbol table extensions
	public static final Struct boolType = new Struct(Struct.Bool);

	
	private Logger log = Logger.getLogger(getClass());

	private boolean errorDetected = false;	
	
	private int programVariablesNumber = 0;

	private Struct currentType = null; // this will represent type of variable that is declaring
	
	private boolean isVariableArray = false;	
	
	private Obj currentMethod = null;
	private boolean returnFound = false;
	
	/* Global utility functions */

	public SemanticAnalyzer() {
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
	}	
	
	// return name of struct type for the given object
	
	public String structDescription(Struct s) {
		switch (s.getKind()) {
			case Struct.None: return "none";
			case Struct.Int: return "int";
			case Struct.Char: return "char";
			case Struct.Array: return "array";
			case Struct.Class: return "class";
			case Struct.Bool: return "bool";
			case Struct.Enum: return "enum";
			case Struct.Interface: return "interface";
			default: return "";
		}
	
	}
	
	// return number of global variables (for the purpose of code generating)

	public int getProgramVariablesNumber() {
		return programVariablesNumber;
	}
	
	// Error handling
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder("Greska");
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line).append(": ");
		else 
			msg.append(": ");
		msg.append(message);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {

		StringBuilder msg = new StringBuilder("Info");
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line).append(": ");
		else 
			msg.append(": ");
		msg.append(message);
		log.info(msg.toString());
	}
	
	/* Program starting and ending */
	
    @Override
    public void visit(ProgName progName) {
    	System.out.println("ProgName");
    	progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
    	Tab.openScope(); // open global scope (to be in universe scope)
    }
    
    @Override
    public void visit(Program program) {
    	// complete syntax tree visiting is finished!
    	
    	programVariablesNumber = Tab.currentScope.getnVars();

    	Tab.chainLocalSymbols(program.getProgName().obj); // chain variables for program scope
    	Tab.closeScope();
    }
    
    /* Type processing */
    
    @Override
    public void visit(Type type) {
    	
    	Obj typeNode = Tab.find(type.getTypeName()); // Fetch type name
    	// This node in tree is visited when variable is declared!
    	// Hence typeName of that variable must be in symbol table
    	
    	if(typeNode == Tab.noObj) {
    		report_error("Tip " + type.getTypeName() + " nije pronadjen u tabeli simbola", null);
    		type.struct = Tab.noType; // noType represent further compatibility
    		// This allow us to use field type.struct without any check if it is null in further nodes in tree
    	} else {
			// We should check if the node from the tree is not Type (it can be Con, Var, Meth, Fld, Prog) and invoke an error
    		// This can happen while we want to create an instance of undeclared variable
    		if(Obj.Type == typeNode.getKind()) {
    			type.struct = typeNode.getType();
    		} else {
    			report_error("Ime " + type.getTypeName() + " ne predstavlja tip!", type);
        		type.struct = Tab.noType;
    		}
    	}
    	
    	currentType = type.struct; // type of variable that is declaring 
    	System.out.println("Type: " + structDescription(currentType));
    	
    	
    }

    
    /* Constants processing */
    
    // utility functions for constant processing
    
    private boolean checkConstantNameConstraint(String constantName, SyntaxNode info) {
    	
    	// Check if this is multiple declaration
    	Obj constNameNode = Tab.find(constantName);
    	if(constNameNode != Tab.noObj) {
        	// constant name exists in symbol table: indicates error
			report_error("Ime " + constantName + " je vec deklarisano!", info);    		
			return false;
    	}
    	// constant name does not exists in symbol table
    	return true; 
    }
    
    // ! Specification constraint
    private boolean checkConstantTypeConstraint(String constantName, Struct constantType, SyntaxNode info) {
    	
    	// Check if the declared constant type is the predefined type of constant value (inc, char, double)
    	if(!constantType.equals(currentType)) {
			report_error("Deklarisani tip konstante '" + structDescription(currentType) + "' nije isti kao tip vrednosti koja se dodeljuje '" + structDescription(constantType) + "'!", info);    		    		
    		return false;
    	}
    	
    	return true; 
    }

    private void insertConstantIntoSymbolTable(String constantName, Struct constantType, int constantValue, SyntaxNode info) {

    	Obj constNode = Tab.insert(Obj.Con, constantName, constantType);
		report_info("Kreirana je konstanta " + structDescription(constantType) + " " + constantName + " = " + constantValue + ".", info);
		constNode.setAdr(constantValue);
	}
    
    @Override
    public void visit(BooleanValue booleanValue) {
    	if(!checkConstantNameConstraint(booleanValue.getConstName(), booleanValue)) {
    		// error happened
    		return;
    	}
    	if(!checkConstantTypeConstraint(booleanValue.getConstName(), boolType, booleanValue)) {
    		// error happened
    		return;
    	}

    	int constValue = 0;
		if(booleanValue.getBoolConstValue() == true)
			constValue = 1;
    	
    	insertConstantIntoSymbolTable(booleanValue.getConstName(), boolType, constValue, booleanValue);
    	
    }
    
    @Override
    public void visit(IntegerValue integerValue) {
    	if(!checkConstantNameConstraint(integerValue.getConstName(), integerValue)) {
    		// error happened
    		return;
    	}
    	if(!checkConstantTypeConstraint(integerValue.getConstName(), Tab.intType, integerValue)) {
    		// error happened
    		return;
    	}
    	
    	insertConstantIntoSymbolTable(integerValue.getConstName(), Tab.intType, integerValue.getNumberConstValue(), integerValue);

    }
    
    @Override
    public void visit(CharValue charValue) {
    	if(!checkConstantNameConstraint(charValue.getConstName(), charValue)) {
    		// error happened
    		return;
    	}
    	if(!checkConstantTypeConstraint(charValue.getConstName(), Tab.charType, charValue)) {
    		// error happened
    		return;
    	}
    	
    	insertConstantIntoSymbolTable(charValue.getConstName(), Tab.charType, charValue.getCharConstValue(), charValue);

    }
    
    /* Variables processing */
    
    // utility functions for constant processing
    
    private boolean checkVariableNameConstraint(String variableName, SyntaxNode info) {
    	
    	// Check if this is multiple declaration
    	
    	Obj constNameNode = Tab.find(variableName);
    	if(constNameNode != Tab.noObj) {
        	// variable name exists in symbol table: indicates error if they are in the same scope
    		if(Tab.currentScope.findSymbol(variableName) != null) {
    			// If it is declared in current scope, then it is multiple declaration
    			// Otherwise, this variable hides the outer-scoped variable, which is allowed
				report_error("Ime " + variableName + " je vec deklarisano!", info);    		
				return false;
    		}
    	}
    	// variable name does not exists in symbol table
    	return true; 
    }
    
    private Obj insertVariableIntoSymbolTable(String variableName, SyntaxNode info) {
    	
    	// *** Uncomment when find the reason!
    	// if(currentType == Tab.noType) {    	
    	//	return Tab.noObj;
    	//}
    	
    	Struct variableType = currentType;
    	if(isVariableArray == true) {
    		// struct node for Array should be created
    		variableType = new Struct(Struct.Array, currentType);
    	}
    	
    	Obj variableNode = Tab.insert(Obj.Var, variableName, variableType);
    	// Obj.Var should be checked, now it is Var, but maybe it will become Obj.Fld
		report_info("Kreirana je promenjiva " + structDescription(variableType) + " " + variableName + (isVariableArray ? "[]" : "") +".", info);
		return variableNode;
	}
	
    
    @Override
    public void visit(VariableIsArray VariableIsArray) {
    	isVariableArray = true;
    }
    
    @Override
    public void visit(VarFromLastPart varFromLastPart) {
    	if(!checkVariableNameConstraint(varFromLastPart.getVarName(), varFromLastPart)) {
    		return;
    	}
    	
    	insertVariableIntoSymbolTable(varFromLastPart.getVarName(), varFromLastPart);
    	
    	isVariableArray = false;

    }
    
    @Override
    public void visit(VarFromMultiplePart varFromMultiplePart) {
    	if(!checkVariableNameConstraint(varFromMultiplePart.getVarName(), varFromMultiplePart)) {
    		return;
    	}
    	
    	insertVariableIntoSymbolTable(varFromMultiplePart.getVarName(), varFromMultiplePart);
    	
    	isVariableArray = false;

    }
    
    /* Methods processing */
    
    @Override
    public void visit(ConcreteType concreteType) {
    	// return type is one of, allready declared types
    	concreteType.struct = currentType;
    }
    
    @Override
    public void visit(VoidType voidType) {
    	// return type is void
    	voidType.struct = Tab.noType;
    }
    
    @Override
    public void visit(MethodTypeName methodTypeName) {
    	
    	System.out.println(structDescription(methodTypeName.getMethodReturnType().struct));
    	// methodTypeName.getMethodReturnType().struct is filled in return type visit methods (ConcreteType and VoidType)
    	
    	currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethName(), methodTypeName.getMethodReturnType().struct);
    	methodTypeName.obj = currentMethod;
    	
    	Tab.openScope(); // open new scope for method. 
    	// All variables declared after is in this (inner) scope and can overdeclared global variables.
		report_info("Definicija funkcije " + methodTypeName.getMethName(), methodTypeName);
    }

    @Override
    public void visit(CorrectMethodDecl correctMethodDecl) {
    	
    	if(!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Funkcija " + currentMethod.getName() + " nema return iskaz!", correctMethodDecl);    		
    	}
    	
    	Tab.chainLocalSymbols(currentMethod);
    	Tab.closeScope();

    	returnFound = false;
    	currentMethod = null;

    }
    
    /* End of parsing */
	public boolean passed() {
    	return !errorDetected;
    }
    
}
