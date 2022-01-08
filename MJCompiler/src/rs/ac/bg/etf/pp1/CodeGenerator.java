package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.Assignment;
import rs.ac.bg.etf.pp1.ast.Const;
import rs.ac.bg.etf.pp1.ast.Designator;
import rs.ac.bg.etf.pp1.ast.FuncCall;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodTypeName;
import rs.ac.bg.etf.pp1.ast.PrintStmt;
import rs.ac.bg.etf.pp1.ast.ProcCall;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor{

	private int mainPc;
	
	public int getMainPc() {
		return mainPc;
	}
	
	@Override
	public void visit(PrintStmt printStmt) {	
		if(printStmt.getExpr().struct == Tab.intType) {
			Code.loadConst(5);
			Code.put(Code.print);
		} else {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}
	
	@Override
	public void visit(Const cnst) {
		Obj con = Tab.insert(Obj.Con, "$", cnst.struct);
		con.setLevel(0);
		con.setAdr(cnst.getN1());
		
		Code.load(con);
	}
	
	
	public void visit(MethodTypeName methodTypeName){
		
		if("main".equalsIgnoreCase(methodTypeName.getMethName())){
			mainPc = Code.pc;
		}

		methodTypeName.obj.setAdr(Code.pc);
		// Collect arguments and local variables
		SyntaxNode methodNode = methodTypeName.getParent();
	
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);
		
		// Generate the entry
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());
	
	}
	
	@Override
	public void visit(MethodDecl methodDecl) {
		
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(Assignment assignment) {
		Code.store(assignment.getDesignator().obj);
	}
	
	@Override
	public void visit(Designator designator) {
		SyntaxNode parent = designator.getParent();
		
		if(Assignment.class != parent.getClass() && FuncCall.class != parent.getClass() && ProcCall.class != parent.getClass()) {
			// if designator is variable that is in expressions
			// if it is global or local variable getstatic or load will be generated
			Code.load(designator.obj);
		}
	}

	@Override
	public void visit(FuncCall funcCall) {
		Obj functionObj = funcCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;

		Code.put(Code.call);
		Code.put2(offset);

	}
	
	
}
