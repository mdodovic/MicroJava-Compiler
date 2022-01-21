package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.CorrectMethodDecl;
import rs.ac.bg.etf.pp1.ast.FactorBoolConst;
import rs.ac.bg.etf.pp1.ast.FactorCharConst;
import rs.ac.bg.etf.pp1.ast.FactorNumConst;
import rs.ac.bg.etf.pp1.ast.MethodTypeName;
import rs.ac.bg.etf.pp1.ast.StatementPrintNoWidth;
import rs.ac.bg.etf.pp1.ast.StatementPrintWithWidth;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {
	
	private int mainPc;	// program start (in x86 it is _start label)
	
	public int getMainPc() {
		return mainPc;
	}
	
	
	public void visit(MethodTypeName methodTypeName){
		
		if("main".equalsIgnoreCase(methodTypeName.getMethName())){
			mainPc = Code.pc;
		}

		methodTypeName.obj.setAdr(Code.pc);
		// Collect arguments and local variables
		//SyntaxNode methodNode = methodTypeName.getParent();
	
		//VarCounter varCnt = new VarCounter();
		//methodNode.traverseTopDown(varCnt);
		
		//FormParamCounter fpCnt = new FormParamCounter();
		//methodNode.traverseTopDown(fpCnt);
		
		// Generate the entry
		Code.put(Code.enter);
//		Code.put(fpCnt.getCount());
//		Code.put(fpCnt.getCount() + varCnt.getCount());
		Code.put(0);
		Code.put(0);
	}
	
	@Override
	public void visit(CorrectMethodDecl correctMethodDecl) {
		
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	
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
	
	
	/* integer, character or boolean constant in expression */
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
	
}


