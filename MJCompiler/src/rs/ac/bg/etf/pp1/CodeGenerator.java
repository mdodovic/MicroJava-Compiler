package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.ClassBodyBrackets;
import rs.ac.bg.etf.pp1.ast.ClassBodyConstructor;
import rs.ac.bg.etf.pp1.ast.ClassBodyFull;
import rs.ac.bg.etf.pp1.ast.ClassBodyMethods;
import rs.ac.bg.etf.pp1.ast.ClassBodyNoConstructorNoMethod;
import rs.ac.bg.etf.pp1.ast.ClassDecl;
import rs.ac.bg.etf.pp1.ast.ClassDeclNameOptionalExtend;
import rs.ac.bg.etf.pp1.ast.ClassFieldDesignator;
import rs.ac.bg.etf.pp1.ast.CorrectMethodDecl;
import rs.ac.bg.etf.pp1.ast.DesignatorAssignOperation;
import rs.ac.bg.etf.pp1.ast.DesignatorFunctionCall;
import rs.ac.bg.etf.pp1.ast.DesignatorPostDecrement;
import rs.ac.bg.etf.pp1.ast.DesignatorPostIncrement;
import rs.ac.bg.etf.pp1.ast.DesignatorStatement;
import rs.ac.bg.etf.pp1.ast.DivideOp;
import rs.ac.bg.etf.pp1.ast.ExprListAddOpTerm;
import rs.ac.bg.etf.pp1.ast.Factor;
import rs.ac.bg.etf.pp1.ast.FactorArrayNewOperator;
import rs.ac.bg.etf.pp1.ast.FactorBoolConst;
import rs.ac.bg.etf.pp1.ast.FactorCharConst;
import rs.ac.bg.etf.pp1.ast.FactorClassNewOperator;
import rs.ac.bg.etf.pp1.ast.FactorFunctionCall;
import rs.ac.bg.etf.pp1.ast.FactorNumConst;
import rs.ac.bg.etf.pp1.ast.FactorVariable;
import rs.ac.bg.etf.pp1.ast.FirstActualParameter;
import rs.ac.bg.etf.pp1.ast.FunctionCallName;
import rs.ac.bg.etf.pp1.ast.FurtherActualParameters;
import rs.ac.bg.etf.pp1.ast.IndirectArrayNameDesignator;
import rs.ac.bg.etf.pp1.ast.InnerClassBodyDummyStart;
import rs.ac.bg.etf.pp1.ast.MethodTypeName;
import rs.ac.bg.etf.pp1.ast.MulOpFactorList;
import rs.ac.bg.etf.pp1.ast.MultiplyOp;
import rs.ac.bg.etf.pp1.ast.PlusOp;
import rs.ac.bg.etf.pp1.ast.SimpleDesignator;
import rs.ac.bg.etf.pp1.ast.SingleNegativeTerm;
import rs.ac.bg.etf.pp1.ast.SingleStatementMatch;
import rs.ac.bg.etf.pp1.ast.StatementPrintNoWidth;
import rs.ac.bg.etf.pp1.ast.StatementPrintWithWidth;
import rs.ac.bg.etf.pp1.ast.StatementRead;
import rs.ac.bg.etf.pp1.ast.StatementReturnEmpty;
import rs.ac.bg.etf.pp1.ast.StetementReturnExpression;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	
	private int _start = -1;	// program start (in x86 it is _start label)
	
	private Map<String, Integer> mapClassVirtualFunctionsTableAddresses = new HashMap<String, Integer>(); 
	
	private List<Obj> virtualMethodNodesList = new ArrayList<Obj>(); 

	private Stack<Obj> functionNodesInInnerCallStack = new Stack<Obj>(); 
	// stack of Obj nodes during calls. 
	// nested function call example: f1(1, f2(3, f3(0)), 5) 
	// 								 ^     ^    (^)
	// ^ represents function Node and its relative position in the stack (^) is the top

	private List<Obj> classNodesList = new ArrayList<Obj>();
	private boolean classContext = false;
	
	public int getFirstInstruction() {
		return _start;
	}
	
	
	private void createVirtualTable() {
		
	}

	private void callLenMethod() {
		// there is an array address on exprStack
		Code.put(Code.arraylength);
	}
	
	private boolean checkIfMethodIsVirtual(Obj methodNode) {
		
		for(Obj virtualMethodNode: virtualMethodNodesList) {
			if(virtualMethodNode.equals(methodNode)) {
				return true;
			}
		}			
		
		return false;
	}
	
	public void visit(MethodTypeName methodTypeName){
		
		// address of the method is the current pc (address of the first instruction in method)
		methodTypeName.obj.setAdr(Code.pc);
		
		System.out.println("PC METODE: " + Code.pc);
		if(classContext == true) {
			// this method belongs to the class context: it is virtual
			System.out.println("Virtual method name: " + methodTypeName.obj.getName());
			virtualMethodNodesList.add(methodTypeName.obj);
		}
		
		if("main".equals(methodTypeName.getMethName())){
			// _start is the first instruction which will be executed:
			// it starts with system setup code and then continue with void main() method
			_start = Code.pc;
		}
		
		// entering in every method begins with the instruction
		// enter (parameters) (parameters + local variables)
		Code.put(Code.enter);
		Code.put(methodTypeName.obj.getLevel()); // level is number of formal arguments
		Code.put(methodTypeName.obj.getLocalSymbols().size()); // local symbols are both formal arguments and local variables
		
		// system setup code if this is _start method: 
		// - ord, chr, len are allready defined
		// - ord and chr methods' call are inherent
		// + len method call has to be using arraylen instruction
		// + virtual table pointer:
		
		if(_start != -1) {
			createVirtualTable();
		}
	}	
	
	@Override
	public void visit(CorrectMethodDecl correctMethodDecl) {
		
		// This is treated in semantic analysis!
		// but it can be treated as runtime error: if non-void method does not have the return statement information about it is passed from semantic analysis
		//if(correctMethodDecl.getMethodTypeName().obj.getFpPos() == -1) {
			// fpPos is set to -1 when it is realized that 
		//	Code.put(Code.trap);
	    //   Code.put(1);
		//}
				
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	/* return statements */
	
	@Override
	public void visit(StatementReturnEmpty StatementReturnEmpty) {
		// return; - no value on exprStack
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(StetementReturnExpression StetementReturnExpression) {
		// return expr; - expr value is on exprStack, it will be consumed in the caller code
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	/* function call - name */
	
	@Override
	public void visit(FunctionCallName functionCallName) {
		functionNodesInInnerCallStack.add(functionCallName.obj);
	}
	
	/* function call */ 
		
	@Override
	public void visit(FactorFunctionCall factorFunctionCall) {

		Obj methodNode = factorFunctionCall.getFunctionCallName().obj;
		System.out.println("Assgin fun call: " + factorFunctionCall.getFunctionCallName().obj.getName());

		if("len".equals(methodNode.getName())) {
			callLenMethod();
			return;
		}
		
		if(checkIfMethodIsVirtual(factorFunctionCall.getFunctionCallName().obj)) {
			System.out.println("Virtual call!");
		/*
		if( virtual function ) {
		
			if(methodNode.getType() != Tab.noType) {
				// non-void method will left returned value on the exprStack so it needs to be removed without any usage
				Code.put(Code.pop);
			}*/
		} else  {
			int offset = methodNode.getAdr() - Code.pc;
			
			Code.put(Code.call); 
			Code.put2(offset); // pc relative: pc = pc + offset = pc + &method - pc = &method
		}
	}
	
	@Override
	public void visit(DesignatorFunctionCall designatorFunctionCall) {
		// This is just function call and returned value will have never been used if it is non-void (Tab.noType)

		Obj methodNode = designatorFunctionCall.getFunctionCallName().obj;
		System.out.println("fun call not assign: " + designatorFunctionCall.getFunctionCallName().obj.getName());
		
		if("len".equals(methodNode.getName())) {
			callLenMethod();
			return;
		}
		if(checkIfMethodIsVirtual(designatorFunctionCall.getFunctionCallName().obj)) {
			System.out.println("Virtual call!");
			
			// virtual method call is different from ordinary method call
			// 
			
			System.out.println(designatorFunctionCall.getFunctionCallName().obj.getLevel());			

			Code.put(Code.getfield); // getfiled consume class address 
			Code.put2(0);
			
		/*
		if( virtual function ) {
		
			if(methodNode.getType() != Tab.noType) {
				// non-void method will left returned value on the exprStack so it needs to be removed without any usage
				Code.put(Code.pop);
			} */
		} else {
			
			int offset = methodNode.getAdr() - Code.pc;
			
			Code.put(Code.call); 
			Code.put2(offset); // pc relative: pc = pc + offset = pc + &method - pc = &method
		
			if(methodNode.getType() != Tab.noType) {
				// non-void method will left returned value on the exprStack so it needs to be removed without any usage
				Code.put(Code.pop);
			}
		}
	}
	
	/* actual parameters */
	
	@Override
	public void visit(FirstActualParameter firstActualParameter) {
		
		if(checkIfMethodIsVirtual(functionNodesInInnerCallStack.peek())) {
			System.out.println("First Actual Parameter");
			Code.put(Code.dup2);
			Code.put(Code.pop);			
		}

	}
	
	@Override
	public void visit(FurtherActualParameters furtherActualParameters) {

		if(checkIfMethodIsVirtual(functionNodesInInnerCallStack.peek())) {
			System.out.println("Further Actual Parameter");
			Code.put(Code.dup2);
			Code.put(Code.pop);			
		}
	
	}
	
	
	/*  */
	
	@Override
	public void visit(SingleStatementMatch SingleStatementMatch) {
		System.out.println("FINISH LINE " + SingleStatementMatch.getLine());
		System.out.println();
	}
	
	/* print */
	
	@Override
	public void visit(StatementPrintNoWidth statementPrintNoWidth) {
		if(statementPrintNoWidth.getExpr().struct == Tab.intType) {
			Code.loadConst(5);
			Code.put(Code.print);
		} else if(statementPrintNoWidth.getExpr().struct == TabStaticExtensions.boolType) {
			Code.loadConst(5);
			Code.put(Code.print);
		} else {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}
	
	@Override
	public void visit(StatementPrintWithWidth statementPrintWithWidth) {
	
		Code.loadConst(statementPrintWithWidth.getWidth());
		
		if(statementPrintWithWidth.getExpr().struct == Tab.intType) {
			Code.put(Code.print);
		} else if(statementPrintWithWidth.getExpr().struct == TabStaticExtensions.boolType) {
			Code.put(Code.print);
		} else {
			Code.put(Code.bprint);
		}		
	}
	
	/* read */
	
	@Override
	public void visit(StatementRead statementRead) {
		
		if(statementRead.getDesignator().obj.getType() == Tab.charType) {
			Code.put(Code.bread);			
		} else {
			Code.put(Code.read);
		}		

		Code.store(statementRead.getDesignator().obj);
		
	}
	
	/* class context */
	
	@Override
	public void visit(ClassDeclNameOptionalExtend classDeclNameOptionalExtend) {
		// entering the class context: 
		// save Obj node and set flag
		classNodesList.add(classDeclNameOptionalExtend.obj);
		classContext = true;
	}
	
	@Override
	public void visit(ClassDecl classDecl) {
		// leave class context:
		// reset flag
		classContext = false;
	}
	
	/* class constructor */
	
	@Override
	public void visit(InnerClassBodyDummyStart innerClassBodyDummyStart) {

    	if(innerClassBodyDummyStart.getParent() instanceof ClassBodyNoConstructorNoMethod) {
    		// class has no body:
        	// -dummy constructor created
    		
    		System.out.println("DUMMY CONSTRUCTOR - no body");

    	} else if(innerClassBodyDummyStart.getParent() instanceof ClassBodyBrackets) {
    		// class has empty body:
        	// -create dummy constructor
    		
    		System.out.println("DUMMY CONSTRUCTOR - empty body");

    	} else if(innerClassBodyDummyStart.getParent() instanceof ClassBodyConstructor) {
    		// class has only constructor:
        	// -actual constructor created
    		
    		System.out.println("REAL CONSTRUCTOR - has construcot");
    		
    	} else if(innerClassBodyDummyStart.getParent() instanceof ClassBodyMethods) {
    		// class has only methods:
        	// -dummy constructor created

    		System.out.println("DUMMY CONSTRUCTOR - NO constructor ");
    		
    	} else if(innerClassBodyDummyStart.getParent() instanceof ClassBodyFull) {
    		// class has both constructor and methods:
        	// -actual constructor created
    		
    		System.out.println("REAL CONSTRUCTOR - has ALL");
    		
    	} else {
    		// error - innerClassBodyDummyStart cannot be anything else
    	}

	}
	
	
	
	/* designator: class field */
	
	@Override
	public void visit(ClassFieldDesignator classFieldDesignator) {
		// &class or &record address
		Code.load(classFieldDesignator.getDesignator().obj);
	}

	/* designator: array element */
	
	@Override
	public void visit(IndirectArrayNameDesignator indirectArrayNameDesignator) {
		// &array address
		Code.load(indirectArrayNameDesignator.getDesignator().obj);
	}
		
	/* designator: simple name */

	@Override
	public void visit(SimpleDesignator simpleDesignator) {
		
		// designator can be global variable, local variable, global method name, class field access and class method name
		// if the designator is global or local variable nothing should be done
		// if the designator is the non-class method name nothing should be done as well
		// in this situation (simple) class fields and methods are accessed without "this." so it should be added
		// and "this" is the first (0) function argument
		
		
//		System.out.println(simpleDesignator.getName());
//		System.out.println(simpleDesignator.getParent().getClass());
		
		// class fields (Obj.Fld):
		if(simpleDesignator.obj.getKind() == Obj.Fld) {				
			// cover usage: 

			if(simpleDesignator.getParent() instanceof DesignatorStatement) {
				// left side of assignment
				// increment or decrement 
				Code.put(Code.load_n + 0); // this
				
//				System.out.println("##");
//				System.out.println("DesignatorStatement");
			}
			if(simpleDesignator.getParent() instanceof FactorVariable) {
				// part of some expression
				
				Code.put(Code.load_n + 0); // this
				
//				System.out.println("##");
//				System.out.println("Factor Variable");
			}

		}
		
		// class methods (in virtual method list):
		if(checkIfMethodIsVirtual(simpleDesignator.obj)) {
			// can be both part of expression and be called standalone
			
			if(simpleDesignator.getParent() instanceof FunctionCallName) {
				Code.put(Code.load_n + 0); // this
				
				System.out.println("##");
				System.out.println("Virtual function call");
				
			}
			
		}
		
	}
	
	/* designator: increment and decrement */
	
	@Override
	public void visit(DesignatorPostIncrement designatorPostIncrement) {

		// increment is just addition by one and assign the very same variable 
		// var++ => var(1) = var(2) + 1;
		
		// var(2) has to be loaded on exprStack
		if(designatorPostIncrement.getDesignator().obj.getKind() == Obj.Var) {		
			// var(2) is Obj.Var (local or global variable) 
			// exprStack does not required anything, load (load_n or getstatic n) will load var(2) properly
			Code.load(designatorPostIncrement.getDesignator().obj);	// appropriate load value var(2): consumes nothing

		} else if(designatorPostIncrement.getDesignator().obj.getKind() == Obj.Elem) {
			// var(2) is Obj.Elem (the element of the array) 
			// exprStack requires &array, index and it has aready been set on exprStack
			// (*) visiting "array[index]" will load &array and index deeper in the tree but it will load it only once
			// (*) &array, index has to be duplicated because of store
			Code.put(Code.dup2); 
			Code.load(designatorPostIncrement.getDesignator().obj);	// appropriate load value var(2): consumes one pair (&array,index) and left another pair for store 
			
		} else if(designatorPostIncrement.getDesignator().obj.getKind() == Obj.Fld) {
			// var(2) is Obj.Fld (the class field)
			// exprStack requires &class it has aready been set on exprStack
			// (**) visiting "class.field" will load &class deeper in the tree but it will load it only once
			// (**) &class has to be duplicated because of store
			Code.put(Code.dup);
			Code.load(designatorPostIncrement.getDesignator().obj);	// appropriate load value var(2): consumes one &class and left another &class for store

		}

		Code.loadConst(1); // 1
		Code.put(Code.add);
		// var(2) + 1 will be on exprStack and it has to be stored in var(1):

		// if var(1) is Obj.Var (local or global variable) store (store_n or putstatic) does not requires anything on exprStack
		// if var(1) is Obj.Elem (the element of the array) store (astore) requires &array, index, var (*)
		// if var(1) is Obj.Fld (class field) store (putfield) requires &class, var(**)
		
		Code.store(designatorPostIncrement.getDesignator().obj);
		// store var(2) + 1 into var(1): left empty exprStack

	}
	
	@Override
	public void visit(DesignatorPostDecrement designatorPostDecrement) {
		
		// decrement is just subtraction by one and assign the very same variable 
		// var-- => var(1) = var(2) - 1;
		
		// var(2) has to be loaded on exprStack
		if(designatorPostDecrement.getDesignator().obj.getKind() == Obj.Var) {		
			// var(2) is Obj.Var (local or global variable) 
			// exprStack does not required anything, load (load_n or getstatic n) will load var(2) properly
			Code.load(designatorPostDecrement.getDesignator().obj);	// appropriate load value var(2): consumes nothing

		} else if(designatorPostDecrement.getDesignator().obj.getKind() == Obj.Elem) {
			// var(2) is Obj.Elem (the element of the array) 
			// exprStack requires &array, index and it has aready been set on exprStack
			// (*) visiting "array[index]" will load &array and index deeper in the tree but it will load it only once
			// (*) &array, index has to be duplicated because of store
			Code.put(Code.dup2); 
			Code.load(designatorPostDecrement.getDesignator().obj);	// appropriate load value var(2): consumes one pair (&array,index) and left another pair for store 
			
		} else if(designatorPostDecrement.getDesignator().obj.getKind() == Obj.Fld) {
			// var(2) is Obj.Fld (the class field)
			// exprStack requires &class it has aready been set on exprStack
			// (**) visiting "class.field" will load &class deeper in the tree but it will load it only once
			// (**) &class has to be duplicated because of store
			Code.put(Code.dup);
			Code.load(designatorPostDecrement.getDesignator().obj);	// appropriate load value var(2): consumes one &class and left another &class for store

		}

		Code.loadConst(1); // 1
		Code.put(Code.sub);
		// var(2) - 1 will be on exprStack and it has to be stored in var(1):

		// if var(1) is Obj.Var (local or global variable) store (store_n or putstatic) does not requires anything on exprStack
		// if var(1) is Obj.Elem (the element of the array) store (astore) requires &array, index, var (*)
		// if var(1) is Obj.Fld (class field) store (putfield) requires &class, var(**)
		
		Code.store(designatorPostDecrement.getDesignator().obj);
		// store var(2) - 1 into var(1): left empty exprStack
		
	}
			
	/* assign */
	
	@Override
	public void visit(DesignatorAssignOperation designatorAssignOperation) {
		
		// add store to the exprStack: everything that is needed for this store has already been pushed to the stack
		// Expression stack looks like:
		// variable for storage
		// value for storage
		Code.store(designatorAssignOperation.getDesignator().obj);
        System.out.println("ASSIGN");
		
	}
	
	/* factor: variable (designator) */
	
	@Override
	public void visit(FactorVariable factorVariable) {
		// this is cumulative designator (includes access to the fields and elements of an array)
		Code.load(factorVariable.getDesignator().obj);
//		System.out.println(factorVariable.getDesignator().obj.getName());
//		System.out.println("FB");
	}

	/* factor: new class operator */
	
	@Override
	public void visit(FactorClassNewOperator factorClassNewOperator) {
		
		// class is created using new with additional argument that determines the class size
		// every field in the class is 4B (all visible fields + virtual table function)
		
		Code.put(Code.new_);
		Code.put2(factorClassNewOperator.struct.getNumberOfFields() * 4); 

		// new size execution will left &class on exprStack which is fine for the assignment operator and its store (store_n or putstatic n) operator
		// on the class object creation, virtual function table address has to be set on the appropriate value (dynamic type)
		// &vft is always at the position 0 in the class object, so &vft will be stored using putfield 2

		Code.put(Code.dup); // nevertheless putfield 2 will consume &class (which is meant to be for store operation in the assignment) so this address has to be copied

		Code.loadConst(0); // &vft

		Code.put(Code.putfield);
        Code.put2(0); // field at the position 0 (&vtf) in the class will be filled with &vtf

	}
	
	/* factor: new array operator */
	
	@Override
	public void visit(FactorArrayNewOperator factorArrayNewOperator) {
		// arrays are created using newarray with additional argument that determines if the elements are characters or some other types
		// number of array elements has already been set on the exprStact (it is calculated earlier)
		Code.put(Code.newarray);
		
		if(factorArrayNewOperator.struct == Tab.charType) {
			// char-array
			Code.put(0);
		} else {
			// non-char-array (there can be simple types (int and bool) as well as classes (every element will act as a 4B-reference)
			Code.put(1);
		}
	}
	
	/* factor: integer, character or boolean constant in expression */
	// push constant value on expression stack
	
	@Override
	public void visit(FactorNumConst factorNumConst) {
		// dummy Obj<Con> with appropriate (int) type, just for purpose of loading constant to the expression stack
		Obj con = Tab.insert(Obj.Con, "#const", Tab.intType);
		con.setLevel(0);
		con.setAdr(factorNumConst.getConstValue());
				
		Code.load(con);
	}
	
	@Override
	public void visit(FactorCharConst factorCharConst) {
		// dummy Obj<Con> with appropriate (int) type, just for purpose of loading constant to the expression stack
		Obj con = Tab.insert(Obj.Con, "#const", Tab.charType);
		con.setLevel(0);
		con.setAdr(factorCharConst.getConstValue());
				
		Code.load(con);
	}
	
	@Override
	public void visit(FactorBoolConst factorBoolConst) {
		// dummy Obj<Con> with appropriate (int) type, just for purpose of loading constant to the expression stack
		Obj con = Tab.insert(Obj.Con, "#const", TabStaticExtensions.boolType);
		con.setLevel(0);
				
		if(factorBoolConst.getConstValue() == true) {
			con.setAdr(1);
		} else {
			con.setAdr(0);
		}

		Code.load(con);
	}
	
	
	/* aritmethic operations */
	
	@Override
	public void visit(SingleNegativeTerm singleNegativeTerm) {
		Code.put(Code.neg); // this will negate value from the top of the exprStack
	}
	
	@Override
	public void visit(ExprListAddOpTerm exprListAddOpTerm) {
		// this will use 2 values from the exprStack and 
		if(exprListAddOpTerm.getAddop() instanceof PlusOp) {
			Code.put(Code.add); // add them
		} else {
			Code.put(Code.sub); // subtract them
		}
		// and put the result back to the exprStack		
	}
	
	@Override
	public void visit(MulOpFactorList mulOpFactorList) {
		// this will use 2 values from the exprStack and 
		if(mulOpFactorList.getMulop() instanceof MultiplyOp) {
			Code.put(Code.mul); // multiply them
		} else if(mulOpFactorList.getMulop() instanceof DivideOp) {
			Code.put(Code.div); // divide them (integer division) 
 		} else {
			Code.put(Code.rem); // find the remainder in the (integer) division
		}
		// and put the result back to the exprStack		
	}
	
}


