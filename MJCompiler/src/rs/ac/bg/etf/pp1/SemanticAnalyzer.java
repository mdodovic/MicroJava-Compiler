package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.AddOpTermList;
import rs.ac.bg.etf.pp1.ast.ArrayDesignator;
import rs.ac.bg.etf.pp1.ast.BooleanValue;
import rs.ac.bg.etf.pp1.ast.CharValue;
import rs.ac.bg.etf.pp1.ast.ConcreteType;
import rs.ac.bg.etf.pp1.ast.CorrectMethodDecl;
import rs.ac.bg.etf.pp1.ast.FactorBoolConst;
import rs.ac.bg.etf.pp1.ast.FactorBracketExpression;
import rs.ac.bg.etf.pp1.ast.FactorCharConst;
import rs.ac.bg.etf.pp1.ast.FactorNumConst;
import rs.ac.bg.etf.pp1.ast.FactorVariable;
import rs.ac.bg.etf.pp1.ast.FormalParameterDeclaration;
import rs.ac.bg.etf.pp1.ast.IntegerValue;
import rs.ac.bg.etf.pp1.ast.MethodTypeName;
import rs.ac.bg.etf.pp1.ast.MulOpFactorList;
import rs.ac.bg.etf.pp1.ast.NegativeExpr;
import rs.ac.bg.etf.pp1.ast.PositiveExpr;
import rs.ac.bg.etf.pp1.ast.ProgName;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.ast.RecordDecl;
import rs.ac.bg.etf.pp1.ast.RecordDeclName;
import rs.ac.bg.etf.pp1.ast.SimpleDesignator;
import rs.ac.bg.etf.pp1.ast.SingleFactor;
import rs.ac.bg.etf.pp1.ast.SingleTerm;
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
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticAnalyzer extends VisitorAdaptor{

	// Symbol table extensions
	public static final Struct boolType = new Struct(Struct.Bool);

	
	private Logger log = Logger.getLogger(getClass());

	private boolean errorDetected = false;	
	
	private int programVariablesNumber = 0;
	private boolean mainMethodFound = false;
	
	private Struct currentType = null; // this will represent type of variable that is declaring
	
	private boolean isVariableArray = false;	
	
	private Obj currentMethod = null;
	private boolean returnFound = false;
	private int methodFormalParametersCount = 0;

	private Struct currentRecord = null;
	private boolean classOrRecordScope = false; // When variable is declared, if it is in class or record, it's type is Obj.Fld, othervise it is Obj.Var
	
	private Struct currentClass = null;
	private Obj overridedMethod = null;

	private List<Struct> listOfRecords = new ArrayList<Struct>(); // list of record for further check if some class extends any record
	
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
    		report_error("Tip " + type.getTypeName() + " nije pronadjen u tabeli simbola", type);
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

    	Obj variableNode;
    	if(classOrRecordScope == true) {
    		variableNode = Tab.insert(Obj.Fld, variableName, variableType);
    	} else {
    		variableNode = Tab.insert(Obj.Var, variableName, variableType);
    	}
    	
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
    
    /* Records processing */
    
    private boolean checkRecordNameConstraint(String recordName, SyntaxNode info) {
    	
    	// Check if this is multiple declaration
    	
    	Obj recordNameNode = Tab.find(recordName);
    	if(recordNameNode != Tab.noObj) {
    		// records can only be declared in program scope
			report_error("Ime rekorda " + recordName + " je vec deklarisano!", info);    		
			return false;

    	}
    	// variable name does not exists in symbol table
    	return true; 
    }
    
    @Override
    public void visit(RecordDeclName recordDeclName) {
    	
    	if(!checkRecordNameConstraint(recordDeclName.getRecordName(), recordDeclName)) {
    		recordDeclName.obj = Tab.noObj;
    		currentRecord = Tab.noType;
    		Tab.openScope();
    		return;
    	}
    	
    	currentRecord = new Struct(Struct.Class);
    	currentRecord.setElementType(Tab.noType);
    	
    	recordDeclName.obj = Tab.insert(Obj.Type, recordDeclName.getRecordName(), currentRecord);
		report_info("Kreiran je record (" + structDescription(currentRecord) + ") " + recordDeclName.getRecordName() + ".", recordDeclName);
        Tab.openScope();
        classOrRecordScope = true;
    }
    
    @Override
    public void visit(RecordDecl recordDecl) {
    	listOfRecords.add(currentRecord);
    	Tab.chainLocalSymbols(currentRecord);
    	Tab.closeScope();
    	classOrRecordScope = false;
    	currentRecord = null;
    }
    
    /* Classes processing */
    
    
    
    /* Methods processing */
    
    private boolean checkMethodNameRedefinition(String methodName, SyntaxNode info) {
    	
    	// Check if this is multiple definition: return false if it is not redefinition or it is not problem i this redefinition (overriding)
    	
    	Obj methodNameNode = Tab.find(methodName);
    	
    	if(methodNameNode == Tab.noObj) {
    		// No method name in symbol table
    		return false;
    	} else {
    		// This name exists in symbol table
    		if(Tab.currentScope.findSymbol(methodName) != null) {
    			// and this name is in current scope: name clashing
    			if(currentClass == null) {
    				// this name is not in class, so it is not overriding
    				report_error("Ime metode " + methodName + " je ranije vec deklarisano!", info);    		
    				overridedMethod = null;
    				return true;
    			} else {
    				// save method from parent class
    				overridedMethod = methodNameNode;
    				return false;
    			}
    			
    		} else {
    			// This name exists is in the outer scope, so it is correct redefinition
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
    	
    	currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethName(), methodTypeName.getMethodReturnType().struct);
    	methodTypeName.obj = currentMethod;
    	

    	Tab.openScope(); // open new scope for method. 
    	// All variables declared after is in this (inner) scope and can overdeclared global variables.
		report_info("Definicija funkcije " + methodTypeName.getMethName(), methodTypeName);
		
		if(currentClass != null) {
			// this method belongs to the class:
			// there is an implicit parameter this
			methodFormalParametersCount++;
			Tab.insert(Obj.Meth, "this", currentClass);
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
    	
    }
       
    
    @Override
    public void visit(StetementReturnExpression stetementReturnExpression) {
    	returnFound = true;
    	Struct declaredMethodType = currentMethod.getType();
    	// expression which is written in the return statement has to be compatible with the method type (from declaration)
    	// TODO: struct of the espression is not set yet
    	//if(!declaredMethodType.equals(stetementReturnExpression.getExpr().struct)){
		//	report_error("Tip izraza u return naredbi (" + structDescription(stetementReturnExpression.getExpr().struct) + ") nije kompatibilan sa tipom povratne vrednosti funkcije " + structDescription(declaredMethodType), stetementReturnExpression);
    	//}
    }
    
    
    @Override
    public void visit(CorrectMethodDecl correctMethodDecl) {
    	
    	if(!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Funkcija " + currentMethod.getName() + " nema (adekvatan) return iskaz!", correctMethodDecl);    		
    	}

    	currentMethod.setLevel(methodFormalParametersCount);

		if("main".equals(currentMethod.getName()) && (currentMethod.getLevel() == 0)){
			mainMethodFound = true;
		}

    	
    	Tab.chainLocalSymbols(currentMethod);

    	Tab.closeScope();
    	
    	// reset 
    	methodFormalParametersCount = 0;
    	returnFound = false;
    	currentMethod = null;

    }
    
    /* Expressions processing */
    
    /* Terms processing */
    
    /* Factors processing */
    
    //  Factor -> constants - number, char, bool
    
    @Override
    public void visit(FactorBoolConst factorBoolConst) {
        // send type (boolType) to FactorList and then to Term
    	factorBoolConst.struct = boolType;
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
    
    // TODO: new operator
    // TODO: function call
    
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
    public void visit(SingleTerm singleTerm) {
    	// send type (TermType) to the Expr
    	singleTerm.struct = singleTerm.getTerm().struct;
    }
    
    @Override
    public void visit(AddOpTermList addOpTermList) {
    	if(!addOpTermList.getTerm().struct.compatibleWith(addOpTermList.getTermList().struct)) {
    	    // ! Specification constraint: Expr and Term types have to be compatible
    		report_error("Tip svih sabiraka moraju biti kompatibilni (" + structDescription(addOpTermList.getTerm().struct) + "," + structDescription(addOpTermList.getTermList().struct) + ")", addOpTermList);        	
    		addOpTermList.struct = Tab.noType;
    		return;
    	}
    	if(addOpTermList.getTerm().struct != Tab.intType || addOpTermList.getTermList().struct != Tab.intType) {
    	    // ! Specification constraint: Expr and Term have to be the int type
    		report_error("Tip svih sabiraka treba da bude int", addOpTermList);        	
    		addOpTermList.struct = Tab.noType;
    		return;
    	}
    	// send cumulative type (intType) to the Expr
    	addOpTermList.struct = Tab.intType;
    }
    
    @Override
    public void visit(PositiveExpr positiveExpr) {
        // expression without "-" should only pass its type
    	positiveExpr.struct = positiveExpr.getTermList().struct;
    }
    
    @Override
    public void visit(NegativeExpr negativeExpr) {
    	if(negativeExpr.getTermList().struct != Tab.intType) {
    	    // ! Specification constraint: Expr has to be the int type
    		report_error("Tip negiranog izraza treba da bude int", negativeExpr);        	
    		negativeExpr.struct = Tab.noType;
    		return;
    	}
    	// send cumulative type (intType) to the Expr
    	negativeExpr.struct = Tab.intType;    	
    }
    
    /* Designators processing */
    
    @Override
    public void visit(SimpleDesignator simpleDesignator) {
    	// designator in form of simple variable
    	Obj designatorNode = Tab.find(simpleDesignator.getName()); // This will find variable in the nearest scope
    	
    	if(designatorNode == Tab.noObj) {
    		report_error("Promenjiva " + simpleDesignator.getName() + " nije deklarisana!", simpleDesignator);
    	} 
    	
    	simpleDesignator.obj = designatorNode;
    }
    
    @Override
    public void visit(ArrayDesignator arrayDesignator) {
    	
    	// Obj arrayDesignatorNode = Tab.find(arrayDesignator.getDesignator().obj.getName());
    	boolean errorHappened = false;
    	if(arrayDesignator.getDesignator().obj.getType().getKind() != Struct.Array) {
    	    // ! Specification constraint: Designator has to be Array
    		report_error("Promenjiva " + arrayDesignator.getDesignator().obj.getName() + " mora biti nizovskog tipa" , arrayDesignator);        	
        	errorHappened = true;
    	}
    	if(arrayDesignator.getExpr().struct != Tab.intType) {
    	    // ! Specification constraint: array index expression has to be intType
    		report_error("Indeks niza mora biti tipa int a ne tipa " + structDescription(arrayDesignator.getExpr().struct), arrayDesignator);        	
        	errorHappened = true;
    	}
    	if(errorHappened == false) {
    		report_info("Pristup elementu niza " + arrayDesignator.getDesignator().obj.getName(), arrayDesignator);
    	}
    	
    	// Obj.Elem is used to send info about array element to the upper classes:
    	// (FactorVariable, ArrayDesignator, ClassFieldDesignator, DesignatorAssignOperation, DesignatorPostIncrement, DesignatorPostDecrement, StatementRead)
    	// key is to send the type of array's elements, not the type Array
    	arrayDesignator.obj = new Obj(Obj.Elem, arrayDesignator.getDesignator().obj.getName(), arrayDesignator.getDesignator().obj.getType().getElemType());
    			
    
    }
    
    
    /* End of parsing */
	public boolean passed() {
    	return !errorDetected;
    }
    
}
