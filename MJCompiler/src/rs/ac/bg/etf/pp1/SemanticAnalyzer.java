package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.ArrayDesignator;
import rs.ac.bg.etf.pp1.ast.BooleanValue;
import rs.ac.bg.etf.pp1.ast.CharValue;
import rs.ac.bg.etf.pp1.ast.ClassBodyBrackets;
import rs.ac.bg.etf.pp1.ast.ClassBodyConstructor;
import rs.ac.bg.etf.pp1.ast.ClassBodyFull;
import rs.ac.bg.etf.pp1.ast.ClassBodyMethods;
import rs.ac.bg.etf.pp1.ast.ClassBodyNoConstructorNoMethod;
import rs.ac.bg.etf.pp1.ast.ClassDecl;
import rs.ac.bg.etf.pp1.ast.ClassDeclNameOptionalExtend;
import rs.ac.bg.etf.pp1.ast.ClassFieldDesignator;
import rs.ac.bg.etf.pp1.ast.ClassHasNoParent;
import rs.ac.bg.etf.pp1.ast.ClassHasParent;
import rs.ac.bg.etf.pp1.ast.ClassSingleVarDecl;
import rs.ac.bg.etf.pp1.ast.ConcreteType;
import rs.ac.bg.etf.pp1.ast.ConstructorDecl;
import rs.ac.bg.etf.pp1.ast.ConstructorDeclName;
import rs.ac.bg.etf.pp1.ast.CorrectMethodDecl;
import rs.ac.bg.etf.pp1.ast.DesignatorAssignOperation;
import rs.ac.bg.etf.pp1.ast.DesignatorFunctionCall;
import rs.ac.bg.etf.pp1.ast.DesignatorPostDecrement;
import rs.ac.bg.etf.pp1.ast.DesignatorPostIncrement;
import rs.ac.bg.etf.pp1.ast.DivideOp;
import rs.ac.bg.etf.pp1.ast.DoWhileDummyStart;
import rs.ac.bg.etf.pp1.ast.EqualOp;
import rs.ac.bg.etf.pp1.ast.Expr;
import rs.ac.bg.etf.pp1.ast.ExprListAddOpTerm;
import rs.ac.bg.etf.pp1.ast.FactorArrayNewOperator;
import rs.ac.bg.etf.pp1.ast.FactorBoolConst;
import rs.ac.bg.etf.pp1.ast.FactorBracketExpression;
import rs.ac.bg.etf.pp1.ast.FactorCharConst;
import rs.ac.bg.etf.pp1.ast.FactorClassNewOperator;
import rs.ac.bg.etf.pp1.ast.FactorFunctionCall;
import rs.ac.bg.etf.pp1.ast.FactorNumConst;
import rs.ac.bg.etf.pp1.ast.FactorVariable;
import rs.ac.bg.etf.pp1.ast.FirstActualParameter;
import rs.ac.bg.etf.pp1.ast.FormalParameterDeclaration;
import rs.ac.bg.etf.pp1.ast.FunctionCallName;
import rs.ac.bg.etf.pp1.ast.FurtherActualParameters;
import rs.ac.bg.etf.pp1.ast.GreaterEqualOp;
import rs.ac.bg.etf.pp1.ast.GreaterOp;
import rs.ac.bg.etf.pp1.ast.InnerClassBodyDummyStart;
import rs.ac.bg.etf.pp1.ast.IntegerValue;
import rs.ac.bg.etf.pp1.ast.LessEqualOp;
import rs.ac.bg.etf.pp1.ast.LessOp;
import rs.ac.bg.etf.pp1.ast.MethodTypeName;
import rs.ac.bg.etf.pp1.ast.MinusOp;
import rs.ac.bg.etf.pp1.ast.ModuoOp;
import rs.ac.bg.etf.pp1.ast.MulOpFactorList;
import rs.ac.bg.etf.pp1.ast.MultiplyOp;
import rs.ac.bg.etf.pp1.ast.NotEqualOp;
import rs.ac.bg.etf.pp1.ast.PlusOp;
import rs.ac.bg.etf.pp1.ast.ProgName;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.ast.RecordDecl;
import rs.ac.bg.etf.pp1.ast.RecordDeclName;
import rs.ac.bg.etf.pp1.ast.RelOpExprCondition;
import rs.ac.bg.etf.pp1.ast.SimpleDesignator;
import rs.ac.bg.etf.pp1.ast.SingleExprCondition;
import rs.ac.bg.etf.pp1.ast.SingleFactor;
import rs.ac.bg.etf.pp1.ast.SingleNegativeTerm;
import rs.ac.bg.etf.pp1.ast.SinglePositiveTerm;
import rs.ac.bg.etf.pp1.ast.StatementBreak;
import rs.ac.bg.etf.pp1.ast.StatementContinue;
import rs.ac.bg.etf.pp1.ast.StatementDoWhile;
import rs.ac.bg.etf.pp1.ast.StatementPrintNoWidth;
import rs.ac.bg.etf.pp1.ast.StatementPrintWithWidth;
import rs.ac.bg.etf.pp1.ast.StatementRead;
import rs.ac.bg.etf.pp1.ast.StetementReturnExpression;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.Term;
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
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;
import rs.etf.pp1.symboltable.visitors.SymbolTableVisitor;

public class SemanticAnalyzer extends VisitorAdaptor{
	
	// log access
	private Logger log = Logger.getLogger(getClass());

	
	
	// print symbol table into log
	
	public void dump(SymbolTableVisitor stv) {
		System.out.println("=====================SYMBOL TABLE DUMP=========================");
		if (stv == null)
			stv = new DumpSymbolTableVisitor();
		for (Scope s = Tab.currentScope(); s != null; s = s.getOuter()) {
			s.accept(stv);
		}
		log.info(stv.getOutput());
	}
	
	public void tsdump() {
		dump(null);
	}
	

	private boolean errorDetected = false;	
	
	private static int programVariablesNumber = 0;
	private boolean mainMethodFound = false;
	
	private Struct currentType = null; // this will represent type of variable that is declaring
	
	private boolean isVariableArray = false;	
	
	private Obj currentMethod = null;
	private boolean returnFound = false;
	private int methodFormalParametersCount = 0;
	
	private boolean constructorBody = false; // determines if we are in the constructor body when process statement
	private boolean classMethod = false; // determines if called method is global (false) or belongs to the class (true)
	
	private boolean classOrRecordFieldsScope = false; // When variable is declared, if it is class or record field, it's type is Obj.Fld, otherwise it is Obj.Var

	private String currentRecordName = null;
	private Struct currentRecord = null;
	
	private String currentClassName = null;
	private Struct currentClass = null;
	private String superClassName = null;
	private Struct superClass = null;

	private Obj overridedMethod = null;

	private Map<Struct, String> mapOfRecords = new HashMap<Struct, String>(); // map of user defined records; Usage: check if some type (struct) is user defined; check if some class extends any record
	private Map<Struct, String> mapOfClasses = new HashMap<Struct, String>();// list of user defined classes; Usage: check if some type (struct) is user defined

	private int doWhileDepthCounter = 0; // depth of the do-while statement; it cannot be boolean because we do not know when to reset it to the false value
		
	private Stack<List<Struct>> stackOfActualParameters = new Stack<List<Struct>>(); // stack of actual parametes for compatibility check. f1(1, f2(3, f3(0)), 5) has different parameters so needs to be nested

	
	
//	private List<String> listOfDeclaredLabelsPerMethods = new ArrayList<String>(); // there will be collected all the labels declared in currentMethod - no constraints
//	private List<Map<String, SyntaxNode>> listOfUsedLabelsPerMethods = new ArrayList<Map<String, SyntaxNode>>(); // there will be collected all used labels (in goto statement) - those labels have to be in list of declared labels
	
	enum RelOpEnum { EQUAL, NOT_EQUAL, LESS, GREATER, GREATER_EQUAL, LESS_EQUAL }
	private RelOpEnum relationalOperation = null;

	enum AddOpEnum { PLUS, MINUS }
	@SuppressWarnings("unused")
	private AddOpEnum addLikeOperation = null;

	enum MulOpEnum { MULTIPLY, DIVIDE, MODUO }
	@SuppressWarnings("unused")
	private MulOpEnum mulLikeOperation = null;

	/* Global utility functions */

	public SemanticAnalyzer() {
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", TabStaticExtensions.boolType));
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
	
	// return operation for the given enum object

	 
    private String relationalOperationDecoding(RelOpEnum relationalOp) {
		switch (relationalOp) {
			case EQUAL: return "==";
			case NOT_EQUAL: return "!=";
			case LESS: return "<";
			case GREATER: return ">";
			case GREATER_EQUAL: return ">=";
			case LESS_EQUAL: return "<=";
			default: return "";
		}
	}

    @SuppressWarnings("unused")
	private String addLikeOperationDecoding(AddOpEnum addLikeOp) {
		switch (addLikeOp) {
			case PLUS: return "+";
			case MINUS: return "-";
			default: return "";
		}
	}

    @SuppressWarnings("unused")
	private String mulLikeOperationDecoding(MulOpEnum mulLikeOp) {
		switch (mulLikeOp) {
			case MULTIPLY: return "*";
			case DIVIDE: return "/";
			case MODUO: return "%";
			default: return "";
		}
	}

	
	// return number of global variables (for the purpose of code generating)

	public static int getProgramVariablesNumber() {
		return programVariablesNumber;
	}

	public static void setProgramVariablesNumber(int newProgramVariablesNumber) {
		programVariablesNumber = newProgramVariablesNumber;
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
    	progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
    	Tab.openScope(); // open global scope (to be in universe scope)
    }
    
    @Override
    public void visit(Program program) {
    	// complete syntax tree visiting is finished!
    	
    	programVariablesNumber = Tab.currentScope.getnVars();
    	
		if(mainMethodFound == false){
			report_error("glavni metod sa potpisom: void main() {...} metod nije pronadjen!", null);
		}

    	
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
    		report_error("Tip " + type.getTypeName() + " nije pronadjen u tabeli simbola!", type);
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
    
    // ! Specification constraint: constant type has to be equivalent to one of the three predefined types of terminals numConst, charConst, boolConst
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
    	if(!checkConstantTypeConstraint(booleanValue.getConstName(), TabStaticExtensions.boolType, booleanValue)) {
    		// error happened
    		return;
    	}

    	int constValue = 0;
		if(booleanValue.getBoolConstValue() == true)
			constValue = 1;
    	
    	insertConstantIntoSymbolTable(booleanValue.getConstName(), TabStaticExtensions.boolType, constValue, booleanValue);
    	
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

    	Obj variableNode;
		// this variable can be: global variable (Obj.Var), record/class field (Obj.Fld), local variable of class method or global method or constructor (Obj.Var)
    	
    	if(classOrRecordFieldsScope == true) {
    		variableNode = Tab.insert(Obj.Fld, variableName, variableType);
    	} else {
    		variableNode = Tab.insert(Obj.Var, variableName, variableType);
    	}
    	
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
    	
    	// reset array determining after inserting into symbol table
    	isVariableArray = false;

    }
    
    @Override
    public void visit(VarFromMultiplePart varFromMultiplePart) {
    	if(!checkVariableNameConstraint(varFromMultiplePart.getVarName(), varFromMultiplePart)) {
    		return;
    	}
    	
    	insertVariableIntoSymbolTable(varFromMultiplePart.getVarName(), varFromMultiplePart);
    	
    	// reset array determining after inserting into symbol table
    	isVariableArray = false;

    }
    
    /* Records processing */
    
    private boolean checkRecordNameConstraint(String recordName, SyntaxNode info) {
    	
    	// Check if this is multiple declaration
    	
    	Obj recordNameNode = Tab.find(recordName);
    	if(recordNameNode != Tab.noObj) {
    		// records can only be declared in program scope
			report_error("Ime rekorda " + recordName + " je vec deklarisano!", info);    		
			return false;

    	}
    	// record name does not exists in symbol table
    	return true; 
    }
    
    @Override
    public void visit(RecordDeclName recordDeclName) {
    	
    	if(!checkRecordNameConstraint(recordDeclName.getRecordName(), recordDeclName)) {
    		recordDeclName.obj = Tab.noObj;
    		currentRecord = Tab.noType;
    		currentRecordName = "";

    		Tab.openScope();
    		return;
    	}
    	
    	currentRecordName = recordDeclName.getRecordName();
    	currentRecord = new Struct(Struct.Class);
    	currentRecord.setElementType(Tab.noType);
    	
    	recordDeclName.obj = Tab.insert(Obj.Type, recordDeclName.getRecordName(), currentRecord);
		report_info("Kreiran je record (" + structDescription(currentRecord) + ") " + recordDeclName.getRecordName() + ".", recordDeclName);
        Tab.openScope();
        classOrRecordFieldsScope = true;
    }
    
    @Override
    public void visit(RecordDecl recordDecl) {
    	mapOfRecords.put(currentRecord, currentRecordName);
    	Tab.chainLocalSymbols(currentRecord);
    	Tab.closeScope();
    	classOrRecordFieldsScope = false;
    	currentRecord = null;
    	currentRecordName = null;
    }
    
    /* Classes processing */
    
    private boolean checkClassNameConstraint(String className, SyntaxNode info) {
    	
    	// Check if this is multiple declaration
    	
    	Obj classNameNode = Tab.find(className);
    	if(classNameNode != Tab.noObj) {
    		// classes can only be declared in program scope
			report_error("Ime kalse " + className + " je vec deklarisano!", info);    		
			return false;

    	}
    	// class name does not exists in symbol table
    	return true; 
    }
    
    private void transferFieldsFromSuperClassToCurrentClass() {
    	for(Obj classMember: superClass.getMembers()) {
    		if(classMember.getKind() == Obj.Fld && !"VTF_address".equals(classMember.getName())) {
    			// this is variable from super class (we do not copy virtual table address, every class has it inserted immediately after the scope opening
    			Tab.insert(Obj.Fld, classMember.getName(), classMember.getType());
    		}
   		}
		
	}
    
    @Override
    public void visit(ClassDeclNameOptionalExtend classDeclNameOptionalExtend) {
    	    	
    	if(!checkClassNameConstraint(classDeclNameOptionalExtend.getClassName(), classDeclNameOptionalExtend)) {
    		classDeclNameOptionalExtend.obj = Tab.noObj;
    		currentClass = Tab.noType;
    		currentClassName = "";
    		superClass = Tab.noType;
    		superClassName = "";
    		Tab.openScope();
    		return;
    	}
    	
    	currentClassName = classDeclNameOptionalExtend.getClassName();
    	currentClass = new Struct(Struct.Class);

    	superClass = classDeclNameOptionalExtend.getOptionalExtend().struct;
    	superClassName = mapOfClasses.get(superClass);

    	currentClass.setElementType(superClass);
    	
    	classDeclNameOptionalExtend.obj = Tab.insert(Obj.Type, classDeclNameOptionalExtend.getClassName(), currentClass);
		report_info("Kreirana je klasa (" + structDescription(currentClass) + ") " + classDeclNameOptionalExtend.getClassName() + ".", classDeclNameOptionalExtend);
        Tab.openScope();
 
        // insert mandatory field: virtual function table address
        Tab.insert(Obj.Fld, "VTF_address", Tab.intType);
        
        transferFieldsFromSuperClassToCurrentClass();
        
        classOrRecordFieldsScope = true;
    	
    }
    
    // extended classes processing
    // those productions are set to be in "class" className to be processed before this visit method.
    // so the info which type is the super class is sent through struct field
    
    private Struct checkSuperClassConstraints(String superClassName, SyntaxNode info) {
    	
    	// check if super class is defined 
    	Obj superClassNode = Tab.find(superClassName);
    	if(superClassNode == Tab.noObj) {
    		// super class name is in symbol table so the super class is not declared
    		report_error("Nadklasa " + superClassName + " nije deklarisana ranije!", info);    		
    		return Tab.noType;
    	}
    	// super class is in the symbol table
    	
        // ! Specification constraint: super class has to be one of the user defined classes
		for (Map.Entry<Struct, String> userDefinedClassStructName : mapOfClasses.entrySet()) {
	    	// in this iterrating getKey will return struct, getValue will return name
    		if(userDefinedClassStructName.getKey() == superClassNode.getType()) {
    			// user defined class
    			return userDefinedClassStructName.getKey();
    		}
    	}    	
    	// 
		report_error("Nadklasa " + superClassName + " ne predstavlja korisnicki definisanu klasu", info);    		
		return Tab.noType;
    }
    
    @Override
    public void visit(ClassHasParent classHasParent) {
    	classHasParent.struct = checkSuperClassConstraints(classHasParent.getType().getTypeName(), classHasParent);
    }
    
    @Override
    public void visit(ClassHasNoParent classHasNoParent) {
    	classHasNoParent.struct = Tab.noType;
    }

        
    // Class variables processing - very similar to the ordinary variables
        
    private boolean checkClassVariableNameConstraint(String classVariableName, SyntaxNode info) {
    	
    	// Check if this is multiple declaration
    	
    	Obj classVariableNameNode = Tab.find(classVariableName);
    	if(classVariableNameNode != Tab.noObj) {
        	// class variable name exists in symbol table: indicates error if they are in the same scope
    		if(Tab.currentScope.findSymbol(classVariableName) != null) {
    			// If it is declared in current scope, then it is multiple declaration
    			// Otherwise, this class variable hides the outer-scoped variable, which is allowed
				report_error("Ime " + classVariableName + " je vec deklarisano!", info);    		
				return false;
    		}
    	}
    	// class variable name does not exists in symbol table
    	return true; 
    }

    
    private Obj insertClassVariableIntoSymbolTable(String classVariableName, SyntaxNode info) {
    	
    	// *** Uncomment when find the reason!
    	// if(currentType == Tab.noType) {    	
    	//	return Tab.noObj;
    	//}
    	
    	Struct classVariableType = currentType;
    	if(isVariableArray == true) {
    		// struct node for Array should be created
    		classVariableType = new Struct(Struct.Array, currentType);
    	}

    	// this is class context - can only be Obj.Fld
    	Obj classVariableNode = Tab.insert(Obj.Fld, classVariableName, classVariableType);

    	report_info("Kreirano je polje klase: " + structDescription(classVariableType) + " " + classVariableName + (isVariableArray ? "[]" : "") +".", info);
		return classVariableNode;
	}
	
    @Override
    public void visit(ClassSingleVarDecl classSingleVarDecl) {
    	if(!checkClassVariableNameConstraint(classSingleVarDecl.getClassFieldName(), classSingleVarDecl)) {
    		return;
    	}
    	
    	insertClassVariableIntoSymbolTable(classSingleVarDecl.getClassFieldName(), classSingleVarDecl);
    	
    	// reset array determining after inserting into symbol table
    	isVariableArray = false;
    }

    // Class constructor and methods processing - method processing is the same as the ordinary methods, but preprocessing needs to be done

    private void copyMethodsFromSuperClass() {
    	if(superClass != Tab.noType) {
    		// there is superClass of the current class
   		
    		for(Obj superClassMember: superClass.getMembers()) {
        		if(superClassMember.getKind() == Obj.Meth && !superClassMember.getName().equals(superClassName)) {
        			// this is method from super class, and it is not super class constructor (name of the method is not equal to the super class name)
        			Tab.currentScope().addToLocals(superClassMember);
        		}
       		}
    		
    	}
    }
    
    private void createDummyConstructor() {

    	
    	// constructor's name is as same as class name
    	// there is no return type, so it can be noType
    	Tab.insert(Obj.Meth, currentClassName, Tab.noType);
      	currentMethod = Tab.insert(Obj.Meth, currentClassName, Tab.noType);
    	
    	Tab.openScope(); // open new scope for dummy constructor
    	
    	// every method in class has predefined parameter this
    	Tab.insert(Obj.Var, "this", currentClass);
    	
    	// and this is the only parameter in every constructor, so it is in this dummy
    	currentMethod.setLevel(1);
    	Tab.chainLocalSymbols(currentMethod); // chain the only parameter this

    	Tab.closeScope(); // close scope for dummy constructor
    	
    	methodFormalParametersCount = 0;
    	returnFound = false;
    	currentMethod = null;

    	
    }

    @Override
    public void visit(InnerClassBodyDummyStart innerClassBodyDummyStart) {
    	
    	// Fields declaring is finished:
    	classOrRecordFieldsScope = false;
    	
    	// At this point, currentClass and superClass are set, fields from super class are transfered to current and all current class' fields are insert
    	// innerClassBodyDummyStart will be helper in class body processing:
    	// create dummy constructor if it is necessary
    	// copy method from super class if it is necessary
    	
    	if(innerClassBodyDummyStart.getParent() instanceof ClassBodyNoConstructorNoMethod) {
    		// class has no body:
        	// -create dummy constructor
        	// -copy method from super class
    		
    		createDummyConstructor();
    		copyMethodsFromSuperClass();

    	} else if(innerClassBodyDummyStart.getParent() instanceof ClassBodyBrackets) {
    		// class has empty body:
        	// -create dummy constructor
        	// -copy method from super class

    		createDummyConstructor();
    		copyMethodsFromSuperClass();

    	} else if(innerClassBodyDummyStart.getParent() instanceof ClassBodyConstructor) {
    		// class has only constructor:
        	// -copy method from super class: this will be done at the end of constructor processing
    		
    	} else if(innerClassBodyDummyStart.getParent() instanceof ClassBodyMethods) {
    		// class has only methods:
        	// -create dummy constructor
        	// -copy method from super class

    		createDummyConstructor();
    		copyMethodsFromSuperClass();
    		
    	} else if(innerClassBodyDummyStart.getParent() instanceof ClassBodyFull) {
    		// class has both constructor and methods:
        	// -copy method from super class: this will be done at the end of constructor processing and this will be ready before the real methods are processed
    		
    	} else {
    		// error - innerClassBodyDummyStart cannot be anything else
    	}
    }
    
    // class constructor 
    

    
    // concrete constructor declaration 

	// ! Specification constraint: constructor name has to be as same as the class name
    private boolean checkConstructorNameConstraint(String constructorName, SyntaxNode info) {

    	if(!constructorName.equals(currentClassName)) {
    		report_error("Konstruktor " + constructorName + " nije istog imena kao klasa " + currentClassName + " u kojoj je definisan!", info);   
        	return false;
    	}
		
    	return true;
	}
    
    @Override
    public void visit(ConstructorDeclName constructorDeclName) {

     	if(!checkConstructorNameConstraint(constructorDeclName.getConstructorName(), constructorDeclName)) {
    		currentMethod = Tab.noObj;
    		constructorBody = true;
    		constructorDeclName.obj = Tab.noObj;
    		Tab.openScope();
    		return;
    	}
    	
    	// constructor name is equal to the class name
    	// if there is an return type; statement in constructor body, this will be processed at the end of the constructor declaration
    	// here is is assumed that everything is correct so constructor type is noType
    	currentMethod = Tab.insert(Obj.Meth, constructorDeclName.getConstructorName(), Tab.noType);
		constructorBody = true;
    	constructorDeclName.obj = currentMethod;
    	
    	Tab.openScope(); // open new scope for constructor
    	// All variables declared after is in this (inner) scope and can overdeclared global variables.
		
		report_info("Definicija konstruktora " + constructorDeclName.getConstructorName() + " u klasi " + currentClassName + ".", constructorDeclName);

		// there is an implicit parameter this
		methodFormalParametersCount++;
		Tab.insert(Obj.Var, "this", currentClass);
		
    }
    
    @Override
    public void visit(ConstructorDecl constructorDecl) {
    	
    	if(returnFound) {
			report_error("Konstruktor " + currentMethod.getName() + " ima tipiziran return iskaz!", constructorDecl);    		
    	}
    	
    	currentMethod.setLevel(methodFormalParametersCount);
 	
    	Tab.chainLocalSymbols(currentMethod);

    	Tab.closeScope();
    	
    	methodFormalParametersCount = 0;
    	returnFound = false;
    	currentMethod = null;
		constructorBody = false;
    	
    	// copy methods from super class to prepare current class for its methods (both completely new or method overriding
    	copyMethodsFromSuperClass();
    
    }
    
    
    
    // finish with the class declaration
    
    @Override
    public void visit(ClassDecl ClassDecl) {
    	mapOfClasses.put(currentClass, currentClassName);
    	Tab.chainLocalSymbols(currentClass);
    	Tab.closeScope();
    	currentClass = null;	
    	currentClassName = null;	
    	superClass = null;	
    	superClassName = null;	
    }
    
    
    /* Methods processing */
    
    private boolean checkMethodNameRedefinition(String methodName, SyntaxNode info) {
    	
    	// Check if this is multiple definition: 
    	// return false if it is not redefinition or it is not problem with redefinition (overriding)
    	
    	// search method name only in current scope: return null if there is no such name in symbol table
    	Obj methodNameNode = Tab.currentScope().findSymbol(methodName);
    	
    	if(methodNameNode == null) {
    		// No method name in symbol table from current scope to the universe scope
    		// this means that 
    		return false;
    	} else {
    		// This name exists in symbol table in this very scope: name clashing
    		if(currentClass == null) {
				// this name is not in class, so it is not overriding
				report_error("Ime metode " + methodName + " je ranije vec deklarisano!", info);    		
				overridedMethod = null;
				return true;
			} else {
				// this method has been copied after variable names and constructor finishing
				// so it is in the class but this is overriding => replace copied method for this
				overridedMethod = methodNameNode;
				return false;
			}
    			    		
    	}
    	
    }
    
    private boolean checkMethodFormalParameterConstraint(String formalParameterName, SyntaxNode info) {

    	Obj formalParameterNode = Tab.find(formalParameterName);

    	// check if formal parameter exists in symbol table
		if(formalParameterNode != Tab.noObj){

			// this name exists in symbol table. 
			// method scope has been opened on MethodTypeName visiting so if it is in this scope it is an error

			if (Tab.currentScope.findSymbol(formalParameterName) != null){
				report_error("Ime formalnog parametra " + formalParameterName + " je vec deklarisano u listi formalnih parametara!", info);    		
				return false;
			}
		}
		return true;
	}
     
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
    	
    	if(checkMethodNameRedefinition(methodTypeName.getMethName(), methodTypeName)) {
    		currentMethod = Tab.noObj;
    		methodTypeName.obj = Tab.noObj;
    		Tab.openScope();
    		return;
    	}
    	
    	// methodTypeName.getMethodReturnType().struct is filled in return type visit methods (ConcreteType and VoidType)
    	
    	if(overridedMethod == null) {
    		// this is no overriding, so insert this method normally
    		currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethName(), methodTypeName.getMethodReturnType().struct);
    	} else {
    		// this is overriding, so one Obj(methodName) already exists in symbol table
    		// this will be replaced with its overrider
    		// Check that should be done are:
    		// if the returned type is the same
    		// if all of the parameters are as same as in the overrided method
    		Tab.currentScope().getLocals().deleteKey(methodTypeName.getMethName());
    		currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethName(), methodTypeName.getMethodReturnType().struct);
    		
    	}
    	

    	
    	methodTypeName.obj = currentMethod;
    	
    	Tab.openScope(); // open new scope for method. 
    	// All variables declared after is in this (inner) scope and can overdeclared global variables.
		
		if(currentClass != null) {
			// this method belongs to the class:
			report_info("Definicija metode " + methodTypeName.getMethName() + " u klasi " + currentClassName + ".", methodTypeName);
			// there is an implicit parameter this
			methodFormalParametersCount++;
			Tab.insert(Obj.Var, "this", currentClass);
		} else {
			// method is not in any class
			report_info("Definicija funkcije " + methodTypeName.getMethName(), methodTypeName);
		}

    }

    @Override
    public void visit(FormalParameterDeclaration formalParameterDeclaration) {
		methodFormalParametersCount++;

		if(!checkMethodFormalParameterConstraint(formalParameterDeclaration.getParameterName(), formalParameterDeclaration)) {
    		return;
    	}
    	
    	Struct parameterType = currentType;
    	if(isVariableArray == true) {
    		// struct node for Array should be created
    		parameterType = new Struct(Struct.Array, currentType);
    	}
    	
    	Tab.insert(Obj.Var, formalParameterDeclaration.getParameterName(), parameterType);
    	
    	// reset array determining after inserting into symbol table
    	isVariableArray = false; 
    	
    }

    @Override
    public void visit(StetementReturnExpression stetementReturnExpression) {
    	returnFound = true;
    	Struct declaredMethodType = currentMethod.getType();
    	// expression which is written in the return statement has to be equivalent to the method type (from declaration)
    	if(declaredMethodType == Tab.noType) {
			report_error("Funkcija tipa void ne moze imati tipiziran izraz u return naredbi (" + structDescription(stetementReturnExpression.getExpr().struct) + ")!", stetementReturnExpression);
			return;    		  
    	}
    	if(!declaredMethodType.equals(stetementReturnExpression.getExpr().struct)){
			report_error("Tip izraza u return naredbi (" + structDescription(stetementReturnExpression.getExpr().struct) + ") nije kompatibilan sa tipom povratne vrednosti funkcije " + structDescription(declaredMethodType), stetementReturnExpression);
			return;
    	}
    }


    private boolean checkCurrentMethodAndOverrideMethod(SyntaxNode info) {
    	if(overridedMethod == null) {
    		// method is not overrided
    		return true;
    	}
    	
    	if(overridedMethod.getLevel() != currentMethod.getLevel()) {
    		// number of the formal arguments are different
    		report_error("Preklopljeni metod " + currentMethod.getName() + " nema adekvatan potpis (razlicit broj argumenata) kao metod u nadklasi!", info);    		    		
	    	return false;
    	}
    	// number of the formal arguments are the same
		// we should iterate only through the formal arguments, this is field level in both of Obj objects    	
    	
    	List<Struct> parametersInOverridedMethod = new ArrayList<Struct>(); 
    	
    	int currentIndexOfFormalArgument = 0;
    	
    	for (Iterator<Obj> overridedMethodParameterIterator = overridedMethod.getLocalSymbols().iterator(); 
    			currentIndexOfFormalArgument < overridedMethod.getLevel() && overridedMethodParameterIterator.hasNext();) {
    		
    	    Obj overridedMethodParameter = overridedMethodParameterIterator.next();
    	    
    	    if(currentIndexOfFormalArgument == 0) {
	    	    if(!"this".equals(overridedMethodParameter.getName())) {
	    	    	// first argument in overrided methods is not this
	        		report_error("Metod iz nadklase " + overridedMethod.getName() + " nema adekvatan potpis (prvi argument nije implicitni this)!", info);    		    		
			    	return false;
	    	    } else {
	    	    	currentIndexOfFormalArgument++;
	    	    	continue;
	    	    }
    	    }    	    
    	    parametersInOverridedMethod.add(overridedMethodParameter.getType());    	    
    	    currentIndexOfFormalArgument++;
    	}
	
    	
    	List<Struct> parametersInCurrentMethod = new ArrayList<Struct>(); 
    	
    	currentIndexOfFormalArgument = 0;
    	
    	for (Iterator<Obj> currentMethodParameterIterator = currentMethod.getLocalSymbols().iterator(); 
    			currentIndexOfFormalArgument < currentMethod.getLevel() && currentMethodParameterIterator.hasNext();) {

    	    Obj currentMethodParameter = currentMethodParameterIterator.next();
    	    
    	    if(currentIndexOfFormalArgument == 0) {
	    	    if(!"this".equals(currentMethodParameter.getName())) {
	    	    	// first argument in current method is not this
	        		report_error("Preklopljeni metod " + currentMethod.getName() + " nema adekvatan potpis (prvi argument nije implicitni this) kao metod u nadklasi!", info);
			    	return false;
	    	    } else {
	    	    	currentIndexOfFormalArgument++;
	    	    	continue;
	    	    }
    	    }    	    
    	    parametersInCurrentMethod.add(currentMethodParameter.getType());    	    
    	    currentIndexOfFormalArgument++;
    	}
    	
    	if(currentIndexOfFormalArgument == 1) {
    		// there is only one argument, this, in the method signature
    		return true;
    	}
    	// implicit this is present in both of methods
    	currentIndexOfFormalArgument = currentIndexOfFormalArgument - 1; // -1 is for this
    	// check others arguments
    	
    	for(int i = 0; i < currentIndexOfFormalArgument; i++) {
    		
    		if(!parametersInCurrentMethod.get(i).equals(parametersInOverridedMethod.get(i))) {
	    		report_error("Preklopljeni metod " + currentMethod.getName() + " nema adekvatan potpis (parametri na poziciji "+ (i + 1) + " se ne slazu po tipu (" + structDescription(parametersInCurrentMethod.get(i)) + "," + structDescription(parametersInOverridedMethod.get(i)) + ")) kao metod u nadklasi!", info);   
		    	return false;    				    			
    		}
    		
    	}
    	    	
    	return true;

	}
    
    @Override
    public void visit(CorrectMethodDecl correctMethodDecl) {
    	
    	if(!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Funkcija " + currentMethod.getName() + " nema (adekvatan) return iskaz!", correctMethodDecl);    		
	    	// This error can be treated as runtime error: pass info through field fpPos
    		// in code generator this will be threated as error
    		// correctMethodDecl.getMethodTypeName().obj.setFpPos(-1);
    	}

    	currentMethod.setLevel(methodFormalParametersCount);

		if("main".equals(currentMethod.getName()) && (currentMethod.getLevel() == 0)){
			mainMethodFound = true;
		}

    	
    	Tab.chainLocalSymbols(currentMethod);

    	
    	checkCurrentMethodAndOverrideMethod(correctMethodDecl);
    	
    	Tab.closeScope();
    	
    	// reset 
    	methodFormalParametersCount = 0;
    	returnFound = false;
    	currentMethod = null;
    	overridedMethod = null;
    }
    
    /* Label and goto Label procesing - removed from semantic analysis */
    
    /* Expressions processing */ // \
    /* Terms processing */       // - processed together
    /* Factors processing */     // /
    
    //  Factor -> constants - number, char, bool
    
    @Override
    public void visit(FactorBoolConst factorBoolConst) {
        // send type (boolType) to FactorList and then to Term
    	factorBoolConst.struct = TabStaticExtensions.boolType;
    }
    
    @Override
    public void visit(FactorNumConst factorNumConst) {
        // send type (intType) to FactorList and then to Term
    	factorNumConst.struct = Tab.intType;
    }

    @Override
    public void visit(FactorCharConst factorCharConst) {
        // send type (charType) to FactorList and then to Term
    	factorCharConst.struct = Tab.charType;
    }
    
    // Factor -> expression in brackets () processing
    
    @Override
    public void visit(FactorBracketExpression factorBracketExpression) {
        // expression type of bracketed expression is exactly the inner expression type
    	factorBracketExpression.struct = factorBracketExpression.getExpr().struct;
    }
    
    // Factor -> designator processing
    
    @Override
    public void visit(FactorVariable factorVariable) {
    	// factorVariable can be
    	// - simple designator (local/global variable)
    	// - class field or method name (method arguments are processed in FactorFunctionCall production)
    	// - array element access
    	
    	factorVariable.struct = factorVariable.getDesignator().obj.getType();
    	
    	if(factorVariable.getDesignator().obj.getKind() == Obj.Var) {
    		if(factorVariable.getDesignator().obj.getType().getKind() == Struct.Class) {
    	    	// - class field or method name (method arguments are processed in FactorFunctionCall production)
    		} else if(factorVariable.getDesignator().obj.getType().getKind() == Struct.Array) {
    	    	// - array element access: usage is reported on array[index] mathcing
    		} else {    			
		    	// - simple designator (local/global variable) : just report usage
    			// this has to be here because if it is in Designator->SimpleDesignator
    			// this x.y.z will report 3 variables x, y and z instead of accessing class fields
				report_info("Pristup promenjivoj " + factorVariable.getDesignator().obj.getName(), factorVariable);
    		}
    			

    	} 
    	
    }
    
    // Factor -> new operator processing

    @Override
    public void visit(FactorArrayNewOperator factorArrayNewOperator) {
    	
    	if(factorArrayNewOperator.getExpr().struct != Tab.intType) {
    		// ! Specification constraint: Expression that represents array's size has to be int type
    		factorArrayNewOperator.struct = Tab.noType;
			report_error("Broj elemenata kreiranog niza mora biti izraz konacnog tipa int, a ne " + structDescription(factorArrayNewOperator.getExpr().struct) + "!", factorArrayNewOperator);    		    		
    		return;
    	}
    	    	
    	factorArrayNewOperator.struct = new Struct(Struct.Array, factorArrayNewOperator.getType().struct);
    
    }
    
    
    @Override
    public void visit(FactorClassNewOperator factorClassNewOperator) {

    	// TODO: check this, equals iis ekvivalentan 
    	// ! Specification constraint: type of created object has to be user defined class (or record)
    	
    	// in this iterrating getKey will return struct, getValue will return name
		for (Map.Entry<Struct, String> userDefinedRecordStructName : mapOfRecords.entrySet()) {
    		if(/*userDefinedRecors.equals(factorClassNewOperator.getType().struct)*/
    				userDefinedRecordStructName.getKey() == factorClassNewOperator.getType().struct	
    				) {
    			// user defined record
    			factorClassNewOperator.struct = userDefinedRecordStructName.getKey();
    			return;
    		}
    	}
		for (Map.Entry<Struct, String> userDefinedClassStructName : mapOfClasses.entrySet()) {
    		if(/*userDefinedRecors.equals(factorClassNewOperator.getType().struct)*/
    				userDefinedClassStructName.getKey() == factorClassNewOperator.getType().struct	
        				) {
    			// user defined class
    			factorClassNewOperator.struct = userDefinedClassStructName.getKey();
    			return;
    		}
    	}
    	
    	// type is not among the user defined classes or records
		factorClassNewOperator.struct = Tab.noType;
		report_error("Tip kreiranog objekta mora biti korisnicki definisana klasa (class) ili rekord (record), a ne " + structDescription(factorClassNewOperator.getType().struct) + "!", factorClassNewOperator);    		    		
	
    	
    }
    
    /* calls of the methods processing*/

    @Override
    public void visit(FunctionCallName functionCallName) {
    	// pass obj from designator
    	functionCallName.obj = functionCallName.getDesignator().obj;
    	// open new frame for actual parameters
    	stackOfActualParameters.push(new ArrayList<Struct>());
    }
    
    @Override
    public void visit(FurtherActualParameters furtherActualParameters) {
    	// add parameter to the current actual parameters frame
    	stackOfActualParameters.peek().add(furtherActualParameters.getExpr().struct);
    }
    
    @Override
    public void visit(FirstActualParameter firstActualParameter) {
    	// add parameter to the current actual parameters frame
    	stackOfActualParameters.peek().add(firstActualParameter.getExpr().struct);
    }
    
	// ! Specification constraint: number of arguments in formal and actual arguments' list has to be the same (with one exception when first argument is this)
    private List<Obj> checkArgumentsNumberEquallity(Obj functionNode, int numberOfActualArguments, SyntaxNode info) {

    	// copy formal parameters into list for easier processing
    	
    	List<Obj> formalArgumentsForCalledFunction = new ArrayList<Obj>(); 
    	
    	int currentIndexOfFormalArgument = 0;
    	
    	for (Iterator<Obj> formalArgumentIterator = functionNode.getLocalSymbols().iterator(); 
    			currentIndexOfFormalArgument < functionNode.getLevel() && formalArgumentIterator.hasNext();) {

    	    Obj formalParameter = formalArgumentIterator.next();
    	    
    	    formalArgumentsForCalledFunction.add(formalParameter);    	    
    	    currentIndexOfFormalArgument++;
    	}
    	
    	int numberOfFormalArguments = functionNode.getLevel();
    	
    	if(formalArgumentsForCalledFunction.size() > 0) {
			if("this".equals(formalArgumentsForCalledFunction.get(0).getName())) {
				classMethod = true;
			}
    	}
    	
    	// number of actual and formal parameters has to be the same, except one case
    	// if this is class method, it has one implicit argument "this"
    	
    	if(numberOfActualArguments != numberOfFormalArguments) {
    		
    		if(numberOfActualArguments == numberOfFormalArguments - 1) {
    			// this indicates that first argument is this
    			if("this".equals(formalArgumentsForCalledFunction.get(0).getName())) {
    				// remove implicit argument to match the number
    				formalArgumentsForCalledFunction.remove(0);
    				return formalArgumentsForCalledFunction;
    			}
    		}
    		
    	} else {
			return formalArgumentsForCalledFunction;    		
    	}
    	
		report_error("Broj stvarnih (" + numberOfActualArguments + ") i formalnih (" + (numberOfFormalArguments - (classMethod ? 1 : 0)) + ") mora biti isti", info);        	    		
		
    	
    	return null;
    	
    }
    
    private Struct checkArgumentsMapping(Obj functionNode, SyntaxNode info) {
    	
    	if(functionNode.getKind() != Obj.Meth) {
    		// ! Specification constraint: functionName has to be either global function or class method
    		report_error(functionNode.getName() + " ne predstavlja funkciju", info);        	    		
    		return Tab.noType;
    	}
    	
    	List<Struct> actualArgumentsForCalledFunction = stackOfActualParameters.pop();
    	int numberOfActualArguments = actualArgumentsForCalledFunction.size();
    	
    	// check number of arguments
    	List<Obj> formalArgumentsForCalledFunction = checkArgumentsNumberEquallity(functionNode, numberOfActualArguments, info);
    			
    	if(formalArgumentsForCalledFunction == null)	{
    		return Tab.noType;
    	}

    	// in 2 lists there is an equal number of formal and actual parameters
    	// check their types compatibility    	
    	
    	for(int i = 0; i < numberOfActualArguments; i++) {
    		
    		if(!TabStaticExtensions.assignableTo(actualArgumentsForCalledFunction.get(i), formalArgumentsForCalledFunction.get(i).getType())){
    			report_error("Formalnom argumentu na poziciji " + (i + 1) + " (tip: " + structDescription(formalArgumentsForCalledFunction.get(i).getType()) + ") se ne moze dodeliti stvarni argument na toj poziciji (tip: " + structDescription(actualArgumentsForCalledFunction.get(i)) + ")!", info);
    			return Tab.noType;
    		}    		
    	}
    	
    	if(classMethod) {
        	report_info("Poziv klasne metode " + functionNode.getName() + ".", info);    		    		
    	} else {
        	report_info("Poziv funkcije " + functionNode.getName() + ".", info);    		
    	}

    	classMethod = false;
    	
    	return functionNode.getType();
	}
    
    @Override
    public void visit(FactorFunctionCall factorFunctionCall) {
    	
    	// type of function should be returned for type compatibility check in expressions
    	factorFunctionCall.struct = checkArgumentsMapping(factorFunctionCall.getFunctionCallName().obj, factorFunctionCall);
    	
    }
    
    @Override
    public void visit(DesignatorFunctionCall designatorFunctionCall) {
    	
    	// Designator statement does not need type
    	// This is function call which is not within any expression
    	checkArgumentsMapping(designatorFunctionCall.getFunctionCallName().obj, designatorFunctionCall);
     	
    }
        
    // specific method: read(Designator) processing
    
    @Override
    public void visit(StatementRead statementRead) {
    	if(!checkDestinationRightValueConstraint(statementRead.getDesignator().obj, statementRead, 3)) {
    		return;
    	}
    	// designator is left-value

    	if(statementRead.getDesignator().obj.getType() != Tab.intType && 
    		statementRead.getDesignator().obj.getType() != Tab.charType &&
    		statementRead.getDesignator().obj.getType() != TabStaticExtensions.boolType) {
    		// ! Specification constraint: variable has to be int, char or bool
			report_error("Ucitavanje sa standardnog ulaza se moze samo u promenjivu tipa int, char ili bool, ne u " + structDescription(statementRead.getDesignator().obj.getType()) + "!", statementRead);        	    			
		}
    }
    
    // Types passing
    
    @Override
    public void visit(SingleFactor singleFactor) {
    	// send type (FactorType) to the Term
    	singleFactor.struct = singleFactor.getFactor().struct;
    }
    
    @Override
    public void visit(MulOpFactorList mulOpFactorList) {
    	if(mulOpFactorList.getFactor().struct != Tab.intType || mulOpFactorList.getFactorList().struct != Tab.intType) {
    	    // ! Specification constraint: Term and Factor have to be the int type
    		report_error("Tip svih cinilaca treba da bude int", mulOpFactorList);        	
    		mulOpFactorList.struct = Tab.noType;
    		return;
    	}
    	// send cumulative type (intType) to the Term
    	mulOpFactorList.struct = Tab.intType;
    }
    
    @Override
    public void visit(Term term) {
        // single factor should only pass its type
    	term.struct = term.getFactorList().struct;
    } 
    
    @Override
    public void visit(SinglePositiveTerm singlePositiveTerm) {
    	// send type (TermType) to the upper nodes in tree
    	singlePositiveTerm.struct = singlePositiveTerm.getTerm().struct;
    }
    
    @Override
    public void visit(ExprListAddOpTerm exprListAddOpTerm) {
    	if(!exprListAddOpTerm.getExpr().struct.compatibleWith(exprListAddOpTerm.getTerm().struct)) {
    	    // ! Specification constraint: Expr and Term types have to be compatible
    		report_error("Tip svih sabiraka moraju biti kompatibilni (" + structDescription(exprListAddOpTerm.getExpr().struct) + "," + structDescription(exprListAddOpTerm.getTerm().struct) + ")", exprListAddOpTerm);        	
    		exprListAddOpTerm.struct = Tab.noType;
    		return;
    	}
    	if(exprListAddOpTerm.getExpr().struct != Tab.intType || exprListAddOpTerm.getTerm().struct != Tab.intType) {
    	    // ! Specification constraint: Expr and Term have to be the int type
    		report_error("Tip svih sabiraka treba da bude int", exprListAddOpTerm);        	
    		exprListAddOpTerm.struct = Tab.noType;
    		return;
    	}
    	// send cumulative type (intType) to the Expr
    	exprListAddOpTerm.struct = Tab.intType;
    }
        
    @Override
    public void visit(SingleNegativeTerm singleNegativeTerm) {
    	if(singleNegativeTerm.getTerm().struct != Tab.intType) {
    	    // ! Specification constraint: Expr has to be the int type
    		report_error("Tip negiranog izraza treba da bude int", singleNegativeTerm);        	
    		singleNegativeTerm.struct = Tab.noType;
    		return;
    	}
    	// send cumulative type (intType) to the Expr
    	singleNegativeTerm.struct = Tab.intType;    	
    }

    /* Designators processing */
    
    @Override
    public void visit(SimpleDesignator simpleDesignator) {
    	// designator in form of simple variable
    	Obj designatorNode = Tab.find(simpleDesignator.getName()); // This will find variable in the nearest scope
    	
    	// designatorNode is Tab.noObj when this name does not exists in symbol table
    	// it can be because the symbol is not defined previously: which is error
    	// or because this simbol is "super" and this means that we need to call a method or the constructor from the superclass;
    	
    	if("super".equals(simpleDesignator.getName()) && simpleDesignator.getParent() instanceof FunctionCallName ) {
    		// this instanceof is because the case of using symbol super without "()" or "(arguments)" is treated as an error
    		if(currentClass == null || currentClass == Tab.noType) {
        		report_error("Poziv metoda/konstruktora nadklase funkcijom super se ne moze koristiti izvan tela metode/konstruktora klase!", simpleDesignator);
        		simpleDesignator.obj = Tab.noObj;
        		return;
    		}
    		if(superClass == null || superClass == Tab.noType) {
        		report_error("Poziv metoda/konstruktora nadklase funkcijom super je moguc samo ukoliko trenutna klasa ima nadklasu!", simpleDesignator);
        		simpleDesignator.obj = Tab.noObj;
        		return;    			
    		}
    		// we are in the class context
    		if(constructorBody == true) {
    			// ! Specification constraint: super() in the constructor body will call constructor from the super class
        		
    			for (Obj constructorNode : superClass.getMembers()) {
    				if (constructorNode.getName().equals(superClassName)) {
    					// constructor from the super class
    					// object is passed to the upper nodes in processing
    					simpleDesignator.obj = constructorNode;    	
    			    	report_info("Poziv konstruktora nadklase (" + superClassName + ") iz konstruktora tekuce klase (" + currentClassName + ") koriscenjem predefinisane metode super().", simpleDesignator);
    			    	return;
    				}
    			}
    			
    		} else {
    			// ! Specification constraint: super() in the method will call the method which was overrided
    			
    			for (Obj methodNode : superClass.getMembers()) {
    				if (methodNode.getName().equals(currentMethod.getName())) {
    					// overrided method from the super class
    					// object is passed to the upper nodes in processing
    					simpleDesignator.obj = methodNode;
    			    	report_info("Poziv metode " + currentMethod.getName() + " nadklase (" + superClassName + ") iz konstruktora tekuce klase (" + currentClassName + ") koriscenjem predefinisane metode super.", simpleDesignator);
    			    	return;
    				}
    			}

    		}
    	}
    	
    	if(designatorNode == Tab.noObj) {
    		report_error("Promenjiva " + simpleDesignator.getName() + " nije deklarisana!", simpleDesignator);
    		// Tab.noObj will propagate to the upper nodes in the tree
    	} 
    	
    	simpleDesignator.obj = designatorNode;
    }
    
    @Override
    public void visit(ArrayDesignator arrayDesignator) {
    	
    	// Obj arrayDesignatorNode = Tab.find(arrayDesignator.getDesignator().obj.getName());
    	if(arrayDesignator.getIndirectArrayNameDesignator().getDesignator().obj.getType().getKind() != Struct.Array) {
    	    // ! Specification constraint: Designator has to be Array
    		report_error("Promenjiva " + arrayDesignator.getIndirectArrayNameDesignator().getDesignator().obj.getName() + " mora biti nizovskog tipa" , arrayDesignator);        	
        	arrayDesignator.obj = Tab.noObj;
        	return;
    	}
    	if(arrayDesignator.getExpr().struct != Tab.intType) {
    	    // ! Specification constraint: array index expression has to be intType
    		report_error("Indeks niza mora biti tipa int a ne tipa " + structDescription(arrayDesignator.getExpr().struct), arrayDesignator);        	
        	arrayDesignator.obj = Tab.noObj;
        	return;
    	}

    	report_info("Pristup elementu niza " + arrayDesignator.getIndirectArrayNameDesignator().getDesignator().obj.getName(), arrayDesignator);
    	
    	// Obj.Elem is used to send info about array element to the upper classes:
    	// (FactorVariable, ArrayDesignator, ClassFieldDesignator, DesignatorAssignOperation, DesignatorPostIncrement, DesignatorPostDecrement, StatementRead)
    	// key is to send the type of array's elements, not the type Array
    	arrayDesignator.obj = new Obj(Obj.Elem, arrayDesignator.getIndirectArrayNameDesignator().getDesignator().obj.getName(), arrayDesignator.getIndirectArrayNameDesignator().getDesignator().obj.getType().getElemType());
    
    }
    
    private boolean chechClassReferenceConstraints(Obj classReference, SyntaxNode info) {

    	if(classReference == Tab.noObj) {
    		// error from SimpleDesignator visit, no object in symbol table
    		return false;
    	}
    	// classReference is an object registered in SimpleDesignator
    	if(classReference.getType().getKind() != Struct.Class) {
    		// ! Specification constraint: classReference has to be an Class
    		report_error(classReference.getName() + " ne predstavlja referencu na klasu, vec je tipa " + structDescription(classReference.getType()) + "!", info);   
        	return false;
    	}
		
    	return true;
	}
    
    // ! Specification constraint: classField has to be the member of the classReference (either its field or its method)
    private Obj checkFieldConstraintAndReturnItsObjec(Obj classReference, String classField, SyntaxNode info) {
    	
    	if(currentClass == classReference.getType()) {
    		// access to the field or method using this reference
    		// those fields are in outer (class name) scope, not in methods scope:
    		
    		Obj currentClassField = Tab.currentScope().getOuter().findSymbol(classField);

    		if(currentClassField != null) {
    			// and this field exists as class local variable
        		report_info("Pristup polju tekuce klase, this." + classField, info);   
        		return currentClassField;
    		} else {
    			// but this field do not exists as class local field
    			
    			report_error(classField + " ne predstavlja polje na klase " + currentClassName + "!", info);       	        
    	    	return Tab.noObj;    			
    		}
    	} else {
    		// access to the field or method using reference on the created class object
    		for(Obj classMember: classReference.getType().getMembers()) {
    			if(classMember.getName().equals(classField)) {
    				// classField is one of the class members
    	    		report_info("Pristup polju " + classReference.getName() + "." + classField + " reference na klasu "  + classReference.getName() + ".", info);   
    				return classMember;
    			}
    		}
    	}
    	
    	// classField is neither field nor method 
    	report_error(classField + " ne predstavlja polje na klase " + classReference.getName() + "!", info);   
        
    	return Tab.noObj;
    	
	}
    
    
    @Override
    public void visit(ClassFieldDesignator classFieldDesignator) {
    	
    	Obj classReference = classFieldDesignator.getDesignator().obj;
    	String classField = classFieldDesignator.getRightField();
    	// classReference.classField statement is processing
    	
    	if(!chechClassReferenceConstraints(classReference, classFieldDesignator)) {
    		classFieldDesignator.obj = Tab.noObj;
    		return;
    	}
    	
    	// classReference is Class, check its field
    	// pass to the upper visiting filed object (for the purpose of type comparisons)
    	classFieldDesignator.obj = checkFieldConstraintAndReturnItsObjec(classReference, classField, classFieldDesignator);
    	
    }
    
    /* = processing */
    
	// ! Specification constraint: destination has to be variable, class field or element of the array
    private boolean checkDestinationRightValueConstraint(Obj dst, SyntaxNode info, int errorOutputFlagAssignmentIncDecRead) {
    	
    	if(dst.getKind() != Obj.Var && dst.getKind() != Obj.Fld && dst.getKind() != Obj.Elem) {
    		if(errorOutputFlagAssignmentIncDecRead == 0) {
    			// message is appropriate to the assignment
    			report_error("Leva strana jednakosti mora biti promenjiva, polje klase ili element niza!", info);        	
    		} else if(errorOutputFlagAssignmentIncDecRead == 1) {
    			// message is appropriate to the increment
    			report_error("Inkrementiranje se mora raditi mora raditi nad promenjivom, poljem klase ili elementom niza!", info);        	    			
    		} else if(errorOutputFlagAssignmentIncDecRead == 2) {
    			// message is appropriate to the increment
    			report_error("Dekrementiranje se mora raditi mora raditi nad promenjivom, poljem klase ili elementom niza!", info);        	    			
    		} else {
    			// message is appropriate to the read
    			report_error("Ucitavanje sa standardnog ulaza se moze samo u promenjivu, polje klase ili element niza!", info);        	    			
    		}
			return false;
		}
		return true;
	}
    
    @Override
    public void visit(DesignatorAssignOperation designatorAssignOperation) {
    	
    	Obj dst = designatorAssignOperation.getDesignator().obj;
    	Struct src = designatorAssignOperation.getExpr().struct;
    	
    	if(!checkDestinationRightValueConstraint(dst, designatorAssignOperation, 0)) {
    		return;
    	}
    	
    	// destination is left-value
    	if(!TabStaticExtensions.assignableTo(src, dst.getType())) {
    		// ! Specification constraint: source has to be assignable to the destination
    		report_error("Leva strana (" + structDescription(src) + ") nije kompatibilna desnoj strani (" + structDescription(dst.getType()) + ") pri dodeli!", designatorAssignOperation);        	    		
    	}

    }

    /* ++ and -- processing */
    
    @Override
    public void visit(DesignatorPostIncrement designatorPostIncrement) {

    	if(!checkDestinationRightValueConstraint(designatorPostIncrement.getDesignator().obj, designatorPostIncrement, 1)) {
    		return;
    	}
    	
    	// destination is left-value
    	if(designatorPostIncrement.getDesignator().obj.getType() != Tab.intType) {
    		// ! Specification constraint: variable has to be int 
			report_error("Inkrementiranje se mora raditi mora raditi nad tipom int, a ne " + (structDescription(designatorPostIncrement.getDesignator().obj.getType())) + "!", designatorPostIncrement);        	    			
    	}

    	
    }
    
    @Override
    public void visit(DesignatorPostDecrement designatorPostDecrement) {
    	if(!checkDestinationRightValueConstraint(designatorPostDecrement.getDesignator().obj, designatorPostDecrement, 2)) {
    		return;
    	}
    	
    	// destination is left-value
    	if(designatorPostDecrement.getDesignator().obj.getType() != Tab.intType) {
    		// ! Specification constraint: variable has to be int 
			report_error("Dekrementiranje se mora raditi mora raditi nad tipom int, a ne " + (structDescription(designatorPostDecrement.getDesignator().obj.getType())) + "!", designatorPostDecrement);        	    			
    	}
    }
    
    /* do-while processing */
    
    // do-while beginning
    @Override
    public void visit(DoWhileDummyStart doWhileDummyStart) {
    	// go into next do-while
    	doWhileDepthCounter++;
    }
    
    // do-while end    
    @Override
    public void visit(StatementDoWhile statementDoWhile) {
    	// finish the current do-while
    	doWhileDepthCounter--;
    }
    
    // ! Specification constraint: break/continue statement has to be in surrounding do-while
    private boolean checkBreakContinueConstraint(String operation, SyntaxNode info) {
    	// check the depth of do-while (if it is >= 1, then we are into do-while)
    	if(doWhileDepthCounter == 0) {
    		// otherwise it is an error
			report_error("Naredba " + operation + " mora biti unutar do-while kontrole toka!", info);        	
			return false;
		}
		return true;
	}
    
    /* break */
    
    @Override
    public void visit(StatementBreak statementBreak) {
    	checkBreakContinueConstraint("break", statementBreak);
    }
        
    /* continue */

    @Override
    public void visit(StatementContinue statementContinue) {
    	checkBreakContinueConstraint("continue", statementContinue);
    }

    /* print processing */
    
    // ! Specification constraint: first argument of the print statement has to be one of the following types: int, char or bool
    private boolean checkPrintConstraint(Struct expressionType, SyntaxNode info) {
    	if(expressionType != Tab.intType && expressionType != Tab.charType && expressionType != TabStaticExtensions.boolType) {
    		report_error("Funkcija print se mora pozvati sa prvim argumentom tipa int, a ne " + structDescription(expressionType), info);        	
			return false;
    	}
    	return true;
    }
    
    @Override
    public void visit(StatementPrintNoWidth statementPrintNoWidth) {
    	checkPrintConstraint(statementPrintNoWidth.getExpr().struct, statementPrintNoWidth);
    }
    
    @Override
    public void visit(StatementPrintWithWidth statementPrintWithWidth) {
    	checkPrintConstraint(statementPrintWithWidth.getExpr().struct, statementPrintWithWidth);
    }
    
    
    /* if-else/do-while conditions */
           
    @Override
    public void visit(SingleExprCondition singleExprCondition) {
    	// condition is just an expr
    	if(singleExprCondition.getExpr().struct != TabStaticExtensions.boolType) {
    		// ! Specification constraint: passed type from expr has to be bool
    		report_error("Tip izraza u uslovu kontrola toka mora biti tipa bool a ne " + structDescription(singleExprCondition.getExpr().struct), singleExprCondition);        	
			return;    	
    	}
    }
    
    @Override
    public void visit(RelOpExprCondition relOpExprCondition) {
    	
    	// exprLeft relOp exprRight
    	Expr exprLeft = relOpExprCondition.getExpr();
    	Expr exprRight = relOpExprCondition.getExpr1();
    	
    	if(!exprLeft.struct.compatibleWith(exprRight.struct)) {
    		// ! Specification constraint: left and right expressions have to be comparable
        	report_error("Izrazi u uslovu kontrole toka moraju biti uporedivi (kompatibilni); " + structDescription(exprLeft.struct) + " i " + structDescription(exprRight.struct) + " to nisu!", relOpExprCondition);  	    		
    	}
    	
    	if(exprLeft.struct.getKind() == Struct.Array
    		|| exprRight.struct.getKind() == Struct.Array
    		|| exprLeft.struct.getKind() == Struct.Class
    		|| exprRight.struct.getKind() == Struct.Class) {
    		
    		if(!relationalOperation.equals(RelOpEnum.EQUAL) && !relationalOperation.equals(RelOpEnum.NOT_EQUAL)) {    		
	    		// ! Specification constraint: if left or right expression is class or array, only operation between them can be the == or the !=
	        	report_error("Jedine dve operacije poredjenja izmedju referenci mogu biti samo == ili !=, a ne " + relationalOperationDecoding(relationalOperation), relOpExprCondition);  	    		
    		}    		
    	}
    	
    }
    
   
	/* operations processing */
    
    @Override
    public void visit(EqualOp EqualOp) {
    	relationalOperation = RelOpEnum.EQUAL;
    }
    
    @Override
    public void visit(NotEqualOp NotEqualOp) {
    	relationalOperation = RelOpEnum.NOT_EQUAL;
    }
    
    @Override
    public void visit(LessOp LessOp) {
    	relationalOperation = RelOpEnum.LESS;
    }
    
    @Override
    public void visit(LessEqualOp LessEqualOp) {
    	relationalOperation = RelOpEnum.LESS_EQUAL;
    }
    
    @Override
    public void visit(GreaterOp GreaterOp) {
    	relationalOperation = RelOpEnum.GREATER;
    }
    
    @Override
    public void visit(GreaterEqualOp GreaterEqualOp) {
    	relationalOperation = RelOpEnum.GREATER_EQUAL;
    }

    @Override
    public void visit(PlusOp PlusOp) {
    	addLikeOperation = AddOpEnum.PLUS;
    }
    
    @Override
    public void visit(MinusOp MinusOp) {
    	addLikeOperation = AddOpEnum.MINUS;
    }
    
    @Override
    public void visit(MultiplyOp MultiplyOp) {
    	mulLikeOperation = MulOpEnum.MULTIPLY;
    }
    
    @Override
    public void visit(DivideOp DivideOp) {
    	mulLikeOperation = MulOpEnum.DIVIDE;
    }
    
    @Override
    public void visit(ModuoOp ModuoOp) {
    	mulLikeOperation = MulOpEnum.MODUO;
    }
    
    
    /* End of parsing */
	public boolean passed() {
    	return !errorDetected;
    }
    
}
