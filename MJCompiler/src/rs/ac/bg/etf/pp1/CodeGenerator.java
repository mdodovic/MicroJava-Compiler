package rs.ac.bg.etf.pp1;

import java.util.HashMap;
import java.util.Map;

import rs.ac.bg.etf.pp1.ast.ArrayDesignator;
import rs.ac.bg.etf.pp1.ast.ClassFieldDesignator;
import rs.ac.bg.etf.pp1.ast.CorrectMethodDecl;
import rs.ac.bg.etf.pp1.ast.DesignatorAssignOperation;
import rs.ac.bg.etf.pp1.ast.DesignatorPostDecrement;
import rs.ac.bg.etf.pp1.ast.DesignatorPostIncrement;
import rs.ac.bg.etf.pp1.ast.DivideOp;
import rs.ac.bg.etf.pp1.ast.ExprListAddOpTerm;
import rs.ac.bg.etf.pp1.ast.FactorArrayNewOperator;
import rs.ac.bg.etf.pp1.ast.FactorBoolConst;
import rs.ac.bg.etf.pp1.ast.FactorCharConst;
import rs.ac.bg.etf.pp1.ast.FactorClassNewOperator;
import rs.ac.bg.etf.pp1.ast.FactorNumConst;
import rs.ac.bg.etf.pp1.ast.FactorVariable;
import rs.ac.bg.etf.pp1.ast.IndirectArrayNameDesignator;
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
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {
	
	private int _start = -1;	// program start (in x86 it is _start label)
	
	private Map<String, Integer> mapClassVirtualFunctionsTableAddresses = new HashMap<String, Integer>(); 
	
	public int getFirstInstruction() {
		return _start;
	}
	
	
	private void createVirtualTablePointer() {
		
	}
	
	public void visit(MethodTypeName methodTypeName){
		
		// address of the method is the current pc (address of the first instruction in method)
		methodTypeName.obj.setAdr(Code.pc);
		
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
		// - virtual table pointer:
		if(_start != -1) {
			createVirtualTablePointer();
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
	
	@Override
	public void visit(StatementRead statementRead) {
		
		if(statementRead.getDesignator().obj.getType() == Tab.charType) {
			Code.put(Code.bread);			
		} else {
			Code.put(Code.read);
		}		

		Code.store(statementRead.getDesignator().obj);
		
	}
	

	/* designator: class field */
	
	@Override
	public void visit(ClassFieldDesignator classFieldDesignator) {
		// &class or &record
		Code.load(classFieldDesignator.getDesignator().obj);
	}

	/* designator: array element */
	
	@Override
	public void visit(IndirectArrayNameDesignator indirectArrayNameDesignator) {
		// &array
		Code.load(indirectArrayNameDesignator.getDesignator().obj);
	}
		
	/* designator: simple name */

	@Override
	public void visit(SimpleDesignator simpleDesignator) {
		System.out.println(simpleDesignator.getName());
		System.out.println(simpleDesignator.getParent().getClass());
		//Code.load(simpleDesignator.obj);
		
		
		
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
			Code.load(designatorPostIncrement.getDesignator().obj);	// appropriate load value var(2)

		} else if(designatorPostIncrement.getDesignator().obj.getKind() == Obj.Elem) {
			// var(2) is Obj.Elem (the element of the array) 
			// exprStack requires &array, index and it has aready been set on exprStack
			// (*) visiting "array[index]" will load &array and index deeper in the tree but it will load it only once
			// (*) &array, index has to be duplicated because of store
			Code.put(Code.dup2); 
			Code.load(designatorPostIncrement.getDesignator().obj);	// appropriate load value var(2)
		} else if(designatorPostIncrement.getDesignator().obj.getKind() == Obj.Fld) {
			// TODO: fix this ... 
			// var(2) is Obj.Elem (the element of the array) 
			// exprStack requires &array, index and it has aready been set on exprStack
			// (*) visiting "array[index]" will load &array and index deeper in the tree but it will load it only once
			// (*) &array, index has to be duplicated because of store
			Code.put(Code.dup);
			Code.load(designatorPostIncrement.getDesignator().obj);	// appropriate load value var(2)
		}		

		Code.loadConst(1); // 1
		Code.put(Code.add);
		// var(2) + 1 will be on exprStack and it has to be stored in var(1):

		// if var(1) is Obj.Var (local or global variable) store (store_n or putstatic) does not requires anything on exprStack
		// if var(1) is Obj.Elem (the element of the array) store (astore) requires &array, index, var (*)
		// if var(1) is Obj.Fld (class field) store (putfield) 
		
		Code.store(designatorPostIncrement.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignatorPostDecrement designatorPostDecrement) {

		// TODO: processing fields or elements
		
		
		// increment is just addition by one and assign the very same variable 
		// var-- => var = var - 1;
		
		if(designatorPostDecrement.getDesignator().obj.getKind() == Obj.Fld) {
			Code.put(Code.dup2);
		}
		if(designatorPostDecrement.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
			
		
		Code.load(designatorPostDecrement.getDesignator().obj);	// var
		Code.loadConst(1); // 1
		Code.put(Code.sub);
		// var + 1 will be on exprStack
		Code.store(designatorPostDecrement.getDesignator().obj);
		// store var + 1 into var
		
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


