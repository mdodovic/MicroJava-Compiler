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
import rs.ac.bg.etf.pp1.ast.ConstructorDecl;
import rs.ac.bg.etf.pp1.ast.CorrectMethodDecl;
import rs.ac.bg.etf.pp1.ast.DesignatorAssignOperation;
import rs.ac.bg.etf.pp1.ast.DesignatorFunctionCall;
import rs.ac.bg.etf.pp1.ast.DesignatorPostDecrement;
import rs.ac.bg.etf.pp1.ast.DesignatorPostIncrement;
import rs.ac.bg.etf.pp1.ast.DesignatorStatement;
import rs.ac.bg.etf.pp1.ast.DivideOp;
import rs.ac.bg.etf.pp1.ast.DoWhileDummyConditionEnd;
import rs.ac.bg.etf.pp1.ast.DoWhileDummyConditionStart;
import rs.ac.bg.etf.pp1.ast.DoWhileDummyStart;
import rs.ac.bg.etf.pp1.ast.EqualOp;
import rs.ac.bg.etf.pp1.ast.ExprListAddOpTerm;
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
import rs.ac.bg.etf.pp1.ast.GreaterEqualOp;
import rs.ac.bg.etf.pp1.ast.GreaterOp;
import rs.ac.bg.etf.pp1.ast.IfDummyConditionEnd;
import rs.ac.bg.etf.pp1.ast.IfDummyConditionStart;
import rs.ac.bg.etf.pp1.ast.IndirectArrayNameDesignator;
import rs.ac.bg.etf.pp1.ast.InnerClassBodyDummyStart;
import rs.ac.bg.etf.pp1.ast.LessEqualOp;
import rs.ac.bg.etf.pp1.ast.LessOp;
import rs.ac.bg.etf.pp1.ast.MethodTypeName;
import rs.ac.bg.etf.pp1.ast.MulOpFactorList;
import rs.ac.bg.etf.pp1.ast.MultiplyOp;
import rs.ac.bg.etf.pp1.ast.NonIfElseBlockDummyStart;
import rs.ac.bg.etf.pp1.ast.NotEqualOp;
import rs.ac.bg.etf.pp1.ast.OrBlockDummyEnd;
import rs.ac.bg.etf.pp1.ast.PlusOp;
import rs.ac.bg.etf.pp1.ast.RelOpExprCondition;
import rs.ac.bg.etf.pp1.ast.SimpleDesignator;
import rs.ac.bg.etf.pp1.ast.SingleExprCondition;
import rs.ac.bg.etf.pp1.ast.SingleNegativeTerm;
import rs.ac.bg.etf.pp1.ast.SingleStatementMatch;
import rs.ac.bg.etf.pp1.ast.StatementBreak;
import rs.ac.bg.etf.pp1.ast.StatementContinue;
import rs.ac.bg.etf.pp1.ast.StatementDoWhile;
import rs.ac.bg.etf.pp1.ast.StatementIf;
import rs.ac.bg.etf.pp1.ast.StatementIfElse;
import rs.ac.bg.etf.pp1.ast.StatementPrintNoWidth;
import rs.ac.bg.etf.pp1.ast.StatementPrintWithWidth;
import rs.ac.bg.etf.pp1.ast.StatementRead;
import rs.ac.bg.etf.pp1.ast.StatementReturnEmpty;
import rs.ac.bg.etf.pp1.ast.StetementReturnExpression;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

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
	private Obj currentClassNode = null;
	
	private boolean superMethodCallFlag = false; // flag that determines if the method call is an ordinary method or this is super(...); call
	
	public int getFirstInstruction() {
		return _start;
	}
	
	
	private void createVirtualTable() {
		// virtual function table is set after global variables 
		
		int staticDataAreaTop = SemanticAnalyzer.getProgramVariablesNumber(); 
		
//		System.out.println("VIRTUAL TABLE CREATION: &" + staticDataAreaTop);

		for(Obj classNode: classNodesList) {
			
			mapClassVirtualFunctionsTableAddresses.put(classNode.getName(), staticDataAreaTop);
//			System.out.println("Class " + classNode.getName() + " &" + staticDataAreaTop);
			for (Obj classMemberNode: classNode.getType().getMembers()) {

				if(classMemberNode.getKind() == Obj.Meth) {
					
//					System.out.println(classMemberNode.getName() + " &" + classMemberNode.getAdr());
					
					for(int i = 0; i < classMemberNode.getName().length(); i++) {
						// functionName is broken into characters
						Code.loadConst(classMemberNode.getName().charAt(i));

						Code.put(Code.putstatic);
						Code.put2(staticDataAreaTop);
						staticDataAreaTop++;

					}
					
					// end of method name
					Code.loadConst(-1);

					Code.put(Code.putstatic);
					Code.put2(staticDataAreaTop);
					staticDataAreaTop++;
					
					// after 'mehtodName -1' there is method address:
					Code.loadConst(classMemberNode.getAdr());

					Code.put(Code.putstatic);
					Code.put2(staticDataAreaTop);
					staticDataAreaTop++;
										
				}
			
			}
			
			// end of virtual table for one class
			Code.loadConst(-2);

			Code.put(Code.putstatic);
			Code.put2(staticDataAreaTop);
			staticDataAreaTop++;
			
		}
		
		// update program size (global variable count + virtual table size)
		SemanticAnalyzer.setProgramVariablesNumber(staticDataAreaTop);

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
		
//		System.out.println("PC METODE: " + Code.pc);
		if(currentClassNode != null) {
			// this method belongs to the class context: it is virtual
//			System.out.println("Virtual method name: " + methodTypeName.obj.getName());
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
		// add the top most function call from stack
		functionNodesInInnerCallStack.push(functionCallName.obj);
		
		// entering the function call
		// if this is virtual function there is hidden argument "this" (&class)
		// it has already been set and in the following nodes all arguments will be set on exprStack
	 	// However &class is needed both as the argument "this" and as a getfield instruction argument
		// so the copu of &class has to be propagate during the argument adding
		
		
		if(checkIfMethodIsVirtual(functionCallName.obj)) {
			Code.put(Code.dup);
		}
		
	}
	
	/* function call */ 
		
	@Override
	public void visit(FactorFunctionCall factorFunctionCall) {

		Obj methodNode = factorFunctionCall.getFunctionCallName().obj;
//		System.out.println("Assgin fun call: " + factorFunctionCall.getFunctionCallName().obj.getName());

		if("len".equals(methodNode.getName())) {
			callLenMethod();
			return;
		}

		if("ord".equals(methodNode.getName())) {
			return;
		}

		if("chr".equals(methodNode.getName())) {
			return;
		}

		
		if(checkIfMethodIsVirtual(factorFunctionCall.getFunctionCallName().obj)) {

//			System.out.println("Virtual call - FACTOR!");

			// virtual method call is different from ordinary method call
			// call stack has to be:
			// &class (represents hidden argument this)
			// arguments
			// &class (represents class which virtual functions table has to be accessed)
			
			if(superMethodCallFlag == true) {
				// super(args); and that this is overridden method call
				// exprStack actually looks like:
				// &inheritClass 
				// arguments - if this super call is super constructor call there will be no arguments which will not affect further processing
				// &inheritClass ! - this is a problem because getfield 0 (which is meant to be the next instruction) will fetch &tvf for &inheritClass
				// this instruction will fetch the very same instruction not the overridden one, which will produce infinite recursion 
				// so in this situation overridden method from super class will be called on ordinary way
				// we do know its address hence it has already been declared in super class
				
				Code.put(Code.pop);	// firstly we has to remove one &inheritClass from stack (which was meant to be consumed by getfield 0)
				
				int overriddenMethodAddress = methodNode.getAdr();				
				int offset = overriddenMethodAddress - Code.pc;
				
				// then overridden method from superclass is called by address
				
				Code.put(Code.call); 
				Code.put2(offset); // pc relative: pc = pc + offset = pc + &method - pc = &method

//				System.out.println("SUPER- method");
			
			} else {
				// regular virtual function call
			
				Code.put(Code.getfield); // getfiled consume &class and return &tvf
				Code.put2(0); // &tvf is always at the position 0 in class
				
				Code.put(Code.invokevirtual); // invokevirtual functionName -1
				
				for(int i = 0; i < methodNode.getName().length(); i++) {
					// functionName is broken into characters
					Code.put4(methodNode.getName().charAt(i));
				}
				Code.put4(-1);
			}
		} else  {
			int offset = methodNode.getAdr() - Code.pc;
			
			Code.put(Code.call); 
			Code.put2(offset); // pc relative: pc = pc + offset = pc + &method - pc = &method
		}
		
		// remove latest function call from stack
		functionNodesInInnerCallStack.pop();
		superMethodCallFlag = false; // reset marking for super-call at the end of processing
	}
	
	@Override
	public void visit(DesignatorFunctionCall designatorFunctionCall) {
		// This is just function call and returned value will have never been used if it is non-void (Tab.noType)

		Obj methodNode = designatorFunctionCall.getFunctionCallName().obj;
//		System.out.println("fun call not assign: " + designatorFunctionCall.getFunctionCallName().obj.getName());
		
		if("len".equals(methodNode.getName())) {
			callLenMethod();
			return;
		}

		if(checkIfMethodIsVirtual(designatorFunctionCall.getFunctionCallName().obj)) {
//			System.out.println("Virtual call - DESIGNATOR!");
			
			// virtual method call is different from ordinary method call
			// call stack has to be:
			// &class (represents hidden argument this)
			// arguments
			// &class (represents class which virtual functions table has to be accessed)
			
			if(superMethodCallFlag == true) {
				// super(args); and that this is overridden method call
				// exprStack actually looks like:
				// &inheritClass 
				// arguments - if this super call is super constructor call there will be no arguments which will not affect further processing
				// &inheritClass ! - this is a problem because getfield 0 (which is meant to be the next instruction) will fetch &tvf for &inheritClass
				// this instruction will fetch the very same instruction not the overridden one, which will produce infinite recursion 
				// so in this situation overridden method from super class will be called on ordinary way
				// we do know its address hence it has already been declared in super class
				
				Code.put(Code.pop);	// firstly we has to remove one &inheritClass from stack (which was meant to be consumed by getfield 0)
				
				int overriddenMethodAddress = methodNode.getAdr();				
				int offset = overriddenMethodAddress - Code.pc;
				
				// then overridden method from superclass is called by address
				
				Code.put(Code.call); 
				Code.put2(offset); // pc relative: pc = pc + offset = pc + &method - pc = &method

//				System.out.println("SUPER- method");
			
			} else {
				// regular virtual function call
				
				Code.put(Code.getfield); // getfiled consume &class and return &tvf
				Code.put2(0); // &tvf is always at the position 0 in class
				
				Code.put(Code.invokevirtual); // invokevirtual functionName -1
				
				for(int i = 0; i < methodNode.getName().length(); i++) {
					// functionName is broken into characters
					Code.put4(methodNode.getName().charAt(i));
				}
				Code.put4(-1);
			}
			
			if(methodNode.getType() != Tab.noType) {
				// non-void method will left returned value on the exprStack so it needs to be removed without any usage
				Code.put(Code.pop);
			}
		} else {
			
			int offset = methodNode.getAdr() - Code.pc;
			
			Code.put(Code.call); 
			Code.put2(offset); // pc relative: pc = pc + offset = pc + &method - pc = &method
		
			if(methodNode.getType() != Tab.noType) {
				// non-void method will left returned value on the exprStack so it needs to be removed without any usage
				Code.put(Code.pop);
			}
		}
		
		// remove latest function call from stack
		functionNodesInInnerCallStack.pop();
		superMethodCallFlag = false; // reset marking for super-call at the end of processing

	}
	
	/* actual parameters */
	
	@Override
	public void visit(FirstActualParameter firstActualParameter) {

		if(checkIfMethodIsVirtual(functionNodesInInnerCallStack.peek())) {
			// the argument has already been set on the exprStack and it looks like:
			// &class &class firstArgument
//			System.out.println("First Actual Parameter");
			// this argument should be pushed into the stack before second &class
			Code.put(Code.dup_x1); // exprStack: &class firstArgument &class firstArgument
			Code.put(Code.pop);	// exprStack: &class firstArgument &class
		}

	}
	
	@Override
	public void visit(FurtherActualParameters furtherActualParameters) {

		if(checkIfMethodIsVirtual(functionNodesInInnerCallStack.peek())) {
			// the argument has already been set on the exprStack and it looks like:
			// &class arguments &class currentArgument
//			System.out.println("Further Actual Parameter");
			// this argument should be pushed into the stack before second &class
			Code.put(Code.dup_x1); // exprStack: &class arguments currentArgument &class currentArgument
			Code.put(Code.pop);	// exprStack: &class arguments currentArgument &class	
		}
	
	}
	
	
	/* single statement - finishing single line */
	
	@Override
	public void visit(SingleStatementMatch SingleStatementMatch) {
//		System.out.println("FINISH LINE " + SingleStatementMatch.getLine());
//		System.out.println();
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
		currentClassNode = classDeclNameOptionalExtend.obj;
	}
	
	@Override
	public void visit(ClassDecl classDecl) {
		// leave class context:
		// reset flag
		currentClassNode = null;
	}
	
	/* class constructor */
	
	private void createDummyConstructor() {
		
		// this is dummy constructor without any body
		// it will contain only entering (enter) and exiting (exit and return) instructions
		// Newertheless it has to be marked as virtual, and its address has to be set
		
		Obj constructorNode = null;
		for(Obj currentClassMemberNode: currentClassNode.getType().getMembers()) {
			if(currentClassMemberNode.getName().equals(currentClassNode.getName())) {
				constructorNode = currentClassMemberNode;
				break;
			}
		}

		virtualMethodNodesList.add(constructorNode);				
		constructorNode.setAdr(Code.pc);
		
		// entering in every method begins with the instruction
		// enter (parameters) (parameters + local variables)
		// so this is the case with the constructor 
		Code.put(Code.enter);
		Code.put(constructorNode.getLevel()); // level is number of formal arguments
		Code.put(constructorNode.getLocalSymbols().size());
		
		// exit from constructor is the same as every method
		Code.put(Code.exit);
		Code.put(Code.return_);
				
	}
	
	private void createEnterIntoDefinedConstructor() {
		
		
		// constructor has the same name as the class (and there is no other methods with the same name, due to semantic analyzer)
		// its object can be found using that fact
			
		Obj constructorNode = null;
		for(Obj currentClassMemberNode: currentClassNode.getType().getMembers()) {
			if(currentClassMemberNode.getName().equals(currentClassNode.getName())) {
				constructorNode = currentClassMemberNode;
				break;
			}
		}

		virtualMethodNodesList.add(constructorNode);				
		constructorNode.setAdr(Code.pc);
		
		// entering in every method begins with the instruction
		// enter (parameters) (parameters + local variables)
		// so this is the case with the constructor 
		Code.put(Code.enter);
		Code.put(constructorNode.getLevel()); // level is number of formal arguments
		Code.put(constructorNode.getLocalSymbols().size());
		
	}
	
	@Override
	public void visit(ConstructorDecl ConstructorDecl) {
		// exit from constructor is the same as every method
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(InnerClassBodyDummyStart innerClassBodyDummyStart) {

    	if(innerClassBodyDummyStart.getParent() instanceof ClassBodyNoConstructorNoMethod) {
    		// class has no body:
        	// -create dummy constructor
    		
//    		System.out.println("DUMMY CONSTRUCTOR - no body");
    		createDummyConstructor();

    	} else if(innerClassBodyDummyStart.getParent() instanceof ClassBodyBrackets) {
    		// class has empty body:
        	// -create dummy constructor
    		
//    		System.out.println("DUMMY CONSTRUCTOR - empty body");
    		createDummyConstructor();

    	} else if(innerClassBodyDummyStart.getParent() instanceof ClassBodyConstructor) {
    		// class has only constructor:
        	// -create actual constructor
    		
//    		System.out.println("REAL CONSTRUCTOR - has construcot");
    		createEnterIntoDefinedConstructor();
    		
    	} else if(innerClassBodyDummyStart.getParent() instanceof ClassBodyMethods) {
    		// class has only methods:
        	// -create dummy constructor

//    		System.out.println("DUMMY CONSTRUCTOR - NO constructor ");
    		createDummyConstructor();
    		
    	} else if(innerClassBodyDummyStart.getParent() instanceof ClassBodyFull) {
    		// class has both constructor and methods:
        	// -create actual constructor
    		
//    		System.out.println("REAL CONSTRUCTOR - has ALL");
    		createEnterIntoDefinedConstructor();
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
				
//				System.out.println("##");
//				System.out.println("Virtual function call");
				
			}
			
		}
		
		if("super".equals(simpleDesignator.getName())) {
			// if the designator is super then this call is overridden method call
			superMethodCallFlag = true;
			
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
//        System.out.println("ASSIGN");
		
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
	

	private boolean checkIfCreatedObjectIsClass(String classOrRecordName) {
		for(Obj classNode: classNodesList) {
			if(classNode.getName().equals(classOrRecordName)) {
				return true;
			}
		}
		return false;
	}

	
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
		
		if(mapClassVirtualFunctionsTableAddresses.get(factorClassNewOperator.getType().getTypeName()) == null) {
			Code.loadConst(0); // &vft			
		} else {
			Code.loadConst(mapClassVirtualFunctionsTableAddresses.get(factorClassNewOperator.getType().getTypeName())); //&vtf
		}
		
		

		Code.put(Code.putfield);
        Code.put2(0); // field at the position 0 (&vtf) in the class will be filled with &vtf
        
        if(checkIfCreatedObjectIsClass(factorClassNewOperator.getType().getTypeName()) == true) {
        	
	        // after the class creation and before the assignment to the object reference
	        // appropriate constructor has to be called
	        // there is only &class on the exprStack (due to Code.put(Code.dup) that has been added to preserve one &class for the store operation)
	        
			Code.put(Code.dup); // nevertheless calling the constructor (which will act as virutal function) will consume another &class so this address has to be copied
			
			// process of virtual method calling requires exprStack to be:
			// &class (represents hidden argument this)
			// arguments - which will be empty since constructor has no parameters
			// &class (represents class which virtual functions table has to be accessed)
			
			Code.put(Code.dup); // second &class from calling exprStack can be added as simple copy (because of missing parameters)
			
			Code.put(Code.getfield); // getfiled consume &class and return &tvf
			Code.put2(0); // &tvf is always at the position 0 in class
						
			Code.put(Code.invokevirtual); // invokevirtual functionName -1
			
			// constructor has the same name as the class
			// class can be assigned with the object of inherited class and its constructor has to be called
			// hence dynamic type (type in new operator) is used instead of static type (declared object type)
			
			for(int i = 0; i < factorClassNewOperator.getType().getTypeName().length(); i++) {
				// constructorName is broken into characters
				Code.put4(factorClassNewOperator.getType().getTypeName().charAt(i));
			}
			Code.put4(-1);

        }
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

	
	/* if */
	/* do-while loop */
	
	// terminology:
	// "non-if" - first instruction after if-else block
	// "than" - statements in if-else if the condition is true
	// "else" - statements in if-else if the condition is false
	
	// if-else structures can be nested so stack structure is used to access the current depth
	// there is a problem in mapping jump destination addresses in in-advance-referencing 
	// hence the top of the following stacks will contain place where addresses patching has to be fixed
	
	// condition is formed from OR-blocks: if the OR-block is true "than" statements would have to be executed, on the other hand "else"/"non-if" statements would have to be executed
	// places for patch with address of the "else"/"non-if" statements is stored in the following stack	
	private Stack<List<Integer>> destinationAddressPatchingFromORConditionBlockStack= new Stack<List<Integer>>(); 
	
	// OR-block is formed from AND-blocks: if AND-block is false whole OR-block is false
	// places for patch with address when to jump if single term from AND-block is false is stored in the following stack
	private Stack<List<Integer>> destinationAddressPatchingFromANDConditionBlockStack = new Stack<List<Integer>>(); 
	
	// the last instruction in the "then" block is unconditionally jump which will skip whole else part
	private Stack<List<Integer>> destinationAddressPatchingFromThenBlockStack = new Stack<List<Integer>>(); 
	
	// do-while start instruction (for the purpose of returning back to the beginning of the do-while
	private Stack<Integer> startDoWhileBlockAddressForPatchingStack = new Stack<Integer>(); 

	// break in do-while will immediately stop its execution and jump to the very first instruction after whole do-while statement
	// this will save addresses for patch when this address is found
	private Stack<List<Integer>> endDoWhileBlockAddressPatchingFromBreakStatementStack = new Stack<List<Integer>>(); 	
	
	// continue in do-while will immediately jump to the condition check (very first instruction of the condition check)
	// this will save addresses for patch when this address is found
	private Stack<List<Integer>> startDoWhileConditionBlockAddressPatchingFromContinueStatementStack = new Stack<List<Integer>>(); 	

	
	@Override
	public void visit(DoWhileDummyStart DoWhileDummyStart) {
		// open new do-while scope by pushing new list of addresses for patching on stack
		destinationAddressPatchingFromORConditionBlockStack.push(new ArrayList<Integer>());
		destinationAddressPatchingFromANDConditionBlockStack.push(new ArrayList<Integer>());
		
		// add "do" address
		startDoWhileBlockAddressForPatchingStack.push(Code.pc);

		endDoWhileBlockAddressPatchingFromBreakStatementStack.push(new ArrayList<Integer>());
		startDoWhileConditionBlockAddressPatchingFromContinueStatementStack.push(new ArrayList<Integer>());
		
	}
	
	@Override
	public void visit(DoWhileDummyConditionStart DoWhileDummyConditionStart) {
		
		// fix all jumps from continue to the conditions check
		
		for(int placeForPatch: startDoWhileConditionBlockAddressPatchingFromContinueStatementStack.peek()) {
			Code.fixup(placeForPatch);
		}
		
		startDoWhileConditionBlockAddressPatchingFromContinueStatementStack.peek().clear(); // all addresses has been patched so nothing should left
		
		
	}
	
	@Override
	public void visit(IfDummyConditionStart IfDummyConditionStart) {
		// open new if-else scope by pushing new list of addresses for patching on stack
		destinationAddressPatchingFromORConditionBlockStack.push(new ArrayList<Integer>());
		destinationAddressPatchingFromANDConditionBlockStack.push(new ArrayList<Integer>());
		destinationAddressPatchingFromThenBlockStack.push(new ArrayList<Integer>());
	}
	
	@Override
	public void visit(SingleExprCondition singleExprCondition) {
		
		// exprStack looks like:
		// boolean_value
		// this value can be either true (1) or false (0) 
		// to check if the value is true or false it can be compared with true and jump based on this comparison
		
		Code.loadConst(1); // true is represented as 1 on exprStack
		Code.putFalseJump(Code.eq, 0); // comparison on equality: if the value on the stack is true further conditions in this AND block will be tested
		// however if this value is false, current AND block is automatically false 
		// so condition processing has to be continued with next OR block or the complete condition is false so THAN statements will not be processed
		// address of this new processing is currently unknown so it needs to be saved for patching
		
		destinationAddressPatchingFromANDConditionBlockStack.peek().add(Code.pc - 2); // saved address for patching
				
	}
	
	@Override
	public void visit(RelOpExprCondition relOpExprCondition) {
		
		// exprStack looks like:
		// integer_value
		// integer_value
		
		// this is part of AND-block so inverse condition should be checked and jumped to the 

		if(relOpExprCondition.getRelop() instanceof EqualOp) {
			Code.putFalseJump(Code.eq, 0); 			
		} else if(relOpExprCondition.getRelop() instanceof NotEqualOp) {
			Code.putFalseJump(Code.ne, 0); 			
		} else if(relOpExprCondition.getRelop() instanceof LessOp) {
			Code.putFalseJump(Code.lt, 0); 			
		} else if(relOpExprCondition.getRelop() instanceof GreaterOp) {
			Code.putFalseJump(Code.gt, 0); 			
		} else if(relOpExprCondition.getRelop() instanceof GreaterEqualOp) {
			Code.putFalseJump(Code.ge, 0); 			
		} else if(relOpExprCondition.getRelop() instanceof LessEqualOp) {
			Code.putFalseJump(Code.le, 0); 			
		}
					
		// if the inverse condition is false (actual condition is true) further conditions in this AND block will be tested		
		// however if the inverse condition is true current AND block is automatically false 
		// so condition processing has to be continued with next OR block or the complete condition is false so THAN statements will not be processed
		// address of this new processing is currently unknown so it needs to be saved for patching
		
		destinationAddressPatchingFromANDConditionBlockStack.peek().add(Code.pc - 2); // saved address for patching
				
	}
	
	@Override
	public void visit(DoWhileDummyConditionEnd DoWhileDummyConditionEnd) {
		// this is the end of the last OR-block
		
		// hence this represents the point after all conditions
		// because of the representation of do-while, here all true OR-blocks has to jump in order to unconditionally jump to the beginning of the do-while
		
		for(int placeForPatch: destinationAddressPatchingFromORConditionBlockStack.peek()) {
			Code.fixup(placeForPatch);
		}
		
		destinationAddressPatchingFromORConditionBlockStack.peek().clear(); // all addresses has been patched so nothing should left
		
		// if the overall condition is true, it should be jumped at the beginning of the do-while (saved address)
		
		Code.putJump(this.startDoWhileBlockAddressForPatchingStack.peek());
		
		// after this unconditional jump (1B-opcode + 2B-address) this is the address after do-while where all false AND-blocks from the last OR-block has to jump
		
		for(int placeForPatch: destinationAddressPatchingFromANDConditionBlockStack.peek()) {
			Code.fixup(placeForPatch);
		}
		
		destinationAddressPatchingFromANDConditionBlockStack.peek().clear(); // all addresses has been patched so nothing should left

		
		// fix break destination addresses
		for(int placeForPatch: endDoWhileBlockAddressPatchingFromBreakStatementStack.peek()) {
			Code.fixup(placeForPatch);
		}
		
		endDoWhileBlockAddressPatchingFromBreakStatementStack.peek().clear(); // all addresses has been patched so nothing should left
				
	}
	
	@Override
	public void visit(IfDummyConditionEnd ifDummyConditionEnd) {
		// this is the end of the last OR-block

		// "then" block follows this, last, OR-block so there is no need for any immediately jump 
		// since "then" block follows this, its address is known here so addresses from unconditionally jumps at the end of the non-last OR-blocks shoul be patched here
		
		for(int placeForPatch: destinationAddressPatchingFromORConditionBlockStack.peek()) {
			Code.fixup(placeForPatch);
		}
		
		destinationAddressPatchingFromORConditionBlockStack.peek().clear(); // all addresses has been patched so nothing should left

		// false-conditions from AND-blocks from this OR-block will jump to the "else"/"non-if" address so there is nothing to patch here from AND-block
		
	}
	
	@Override
	public void visit(OrBlockDummyEnd orBlockDummyEnd) {
		// this is the end of OR-block (and this is not the last OR-block)

		// there should be jumped to the "then" unconditionally 

		Code.putJump(0); 		
		destinationAddressPatchingFromORConditionBlockStack.peek().add(Code.pc - 2); // saved address for patching
		
		// and address for the next OR-block is immediately after this unconditional jump
		
		for(int placeForPatch: destinationAddressPatchingFromANDConditionBlockStack.peek()) {
			Code.fixup(placeForPatch);
		}
		
		destinationAddressPatchingFromANDConditionBlockStack.peek().clear(); // all addresses has been patched so nothing should left
		
	}

	@Override
	public void visit(NonIfElseBlockDummyStart nonIfElseBlockDummyStart) {
		// this is the end of all statements from the "then" block

		// if there is "else" block, because this is "then" block, whole "else" block has to be skipped with unconditional jump
		if(nonIfElseBlockDummyStart.getParent() instanceof StatementIfElse) {
			Code.putJump(0); 		
			destinationAddressPatchingFromThenBlockStack.peek().add(Code.pc - 2); // saved address for patching
		}
		
		// there is "else" or "non-if" block, and no matter which block is following, it should be jumped at the beginning of it if any AND-block from the last OR-block is false		

		for(int placeForPatch: destinationAddressPatchingFromANDConditionBlockStack.peek()) {
			Code.fixup(placeForPatch);
		}
		
		destinationAddressPatchingFromANDConditionBlockStack.peek().clear(); // all addresses has been patched so nothing should left
				
	}
	
	@Override
	public void visit(StatementIf statementIf) {
		
		// close if context
		
		destinationAddressPatchingFromANDConditionBlockStack.pop();
		destinationAddressPatchingFromORConditionBlockStack.pop();
		destinationAddressPatchingFromThenBlockStack.pop();
			
	}
	
	@Override
	public void visit(StatementIfElse statementIfElse) {

		// close if-else context

		for(int placeForPatch: destinationAddressPatchingFromThenBlockStack.peek()) {
			Code.fixup(placeForPatch);
		}
		
		destinationAddressPatchingFromThenBlockStack.peek().clear(); // all addresses has been patched so nothing should left
		destinationAddressPatchingFromThenBlockStack.pop();

		destinationAddressPatchingFromANDConditionBlockStack.pop();
		destinationAddressPatchingFromORConditionBlockStack.pop();

	}
	
	@Override
	public void visit(StatementDoWhile StatementDoWhile) {

		// close do-while context
		
		
		destinationAddressPatchingFromANDConditionBlockStack.pop();
		destinationAddressPatchingFromORConditionBlockStack.pop();
		startDoWhileBlockAddressForPatchingStack.pop();
		endDoWhileBlockAddressPatchingFromBreakStatementStack.pop();
		startDoWhileConditionBlockAddressPatchingFromContinueStatementStack.pop();
	}
	
	
	/* break */

	@Override
	public void visit(StatementBreak StatementBreak) {

		// break represents unconditionally jump to the first instruction after the (deepest) do-while statement  
		
		Code.putJump(0); 		
		endDoWhileBlockAddressPatchingFromBreakStatementStack.peek().add(Code.pc - 2); // saved address for patching	
	
	}
	
	/* continue */
	
	@Override
	public void visit(StatementContinue StatementContinue) {
		
		// continue represents unconditionally jump to the first instruction of the condition in the (deepest) do-while statement

		Code.putJump(0); 		
		startDoWhileConditionBlockAddressPatchingFromContinueStatementStack.peek().add(Code.pc - 2); // saved address for patching	
	
	}
}


