package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.CorrectMethodDecl;
import rs.ac.bg.etf.pp1.ast.DesignatorAssignOperation;
import rs.ac.bg.etf.pp1.ast.DesignatorPostDecrement;
import rs.ac.bg.etf.pp1.ast.DesignatorPostIncrement;
import rs.ac.bg.etf.pp1.ast.DivideOp;
import rs.ac.bg.etf.pp1.ast.ExprListAddOpTerm;
import rs.ac.bg.etf.pp1.ast.FactorBoolConst;
import rs.ac.bg.etf.pp1.ast.FactorCharConst;
import rs.ac.bg.etf.pp1.ast.FactorNumConst;
import rs.ac.bg.etf.pp1.ast.FactorVariable;
import rs.ac.bg.etf.pp1.ast.MethodTypeName;
import rs.ac.bg.etf.pp1.ast.MulOpFactorList;
import rs.ac.bg.etf.pp1.ast.MultiplyOp;
import rs.ac.bg.etf.pp1.ast.PlusOp;
import rs.ac.bg.etf.pp1.ast.SimpleDesignator;
import rs.ac.bg.etf.pp1.ast.SingleNegativeTerm;
import rs.ac.bg.etf.pp1.ast.SingleStatementMatch;
import rs.ac.bg.etf.pp1.ast.StatementPrintNoWidth;
import rs.ac.bg.etf.pp1.ast.StatementPrintWithWidth;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {
	
	private int _start = -1;	// program start (in x86 it is _start label)
	
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
	
	
	/* designator: */

	@Override
	public void visit(SimpleDesignator simpleDesignator) {
		System.out.println(simpleDesignator.getName());
		System.out.println(simpleDesignator.getParent().getClass());
		//Code.load(simpleDesignator.obj);
		
		
		
	}
	
	/* designator: increment and decrement */
	
	@Override
	public void visit(DesignatorPostIncrement designatorPostIncrement) {
		
		// TODO: processing fields or elements
		
		
		// increment is just addition by one and assign the very same variable 
		// var++ => var = var + 1;

		Code.load(designatorPostIncrement.getDesignator().obj);	// var
		Code.loadConst(1); // 1
		Code.put(Code.add);
		// var + 1 will be on exprStack
		Code.store(designatorPostIncrement.getDesignator().obj);
		// store var + 1 into var
	}
	
	@Override
	public void visit(DesignatorPostDecrement designatorPostDecrement) {

		// TODO: processing fields or elements
		
		
		// increment is just addition by one and assign the very same variable 
		// var-- => var = var - 1;

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
		Code.store(designatorAssignOperation.getDesignator().obj);
		
	}
	
	/* factor: variable (designator) */
	
	@Override
	public void visit(FactorVariable factorVariable) {
		// this is cumulative designator (includes access to the fields and elements of an array)
		Code.load(factorVariable.getDesignator().obj);
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


