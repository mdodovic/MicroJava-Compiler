package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticPass extends VisitorAdaptor {

	int printCallCount = 0;
	int varDeclCount = 0;
	
	Obj currentMethod = null;
	boolean returnFound = false;
	
	boolean errorDetected = false;
	
	int nVars;
	
	Logger log = Logger.getLogger(getClass());
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}

	
	public void visit(VarDecl varDecl){
		varDeclCount++;
		// Access to the declared variable name!
		// report_info("Deklarisana promenljiva "+ varDecl.getVarName(), varDecl);
		// Obj varNode = Tab.insert(Obj.Var, varDecl.getVarName(), varDecl.getVarDeclarationType().getType().struct);
		// Obj varNode = Tab.insert(Obj.Var, varDecl.getVarName(), varDecl.getType().struct);
	}
	
	@Override
	public void visit(StatementPrintNoWidth print) {
    	if(print.getExpr().struct != Tab.intType && print.getExpr().struct != Tab.charType) 
    		report_error ("Semanticka greska na liniji " + print.getLine() + ": Operand instrukcije PRINT mora biti char ili int tipa", null);
    	
    	printCallCount++;			
	}

	@Override
	public void visit(StatementPrintWithWidth print) {
    	if(print.getExpr().struct != Tab.intType && print.getExpr().struct != Tab.charType) 
    		report_error ("Semanticka greska na liniji " + print.getLine() + ": Operand instrukcije PRINT mora biti char ili int tipa", null);
    	
    	printCallCount++;			
	}

    
    @Override
    public void visit(ProgName progName) {
    	progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
    	Tab.openScope();
    }
    
    @Override
    public void visit(Program program) {
    	nVars = Tab.currentScope.getnVars();
    	Tab.chainLocalSymbols(program.getProgName().obj);
    	Tab.closeScope();
    }
    
    @Override
    public void visit(Type type) {
    	Obj typeNode = Tab.find(type.getTypeName());
    	if(typeNode == Tab.noObj) {
    		report_error("Tip " + type.getTypeName() + " nije pronadjen u tabeli simbola", null);
    		type.struct = Tab.noType;
    	} else {
    		if(Obj.Type == typeNode.getKind()) {
    			type.struct = typeNode.getType();
    		} else {
    			report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
        		type.struct = Tab.noType;
    		}
    	}
    }
    
    @Override
    public void visit(MethodTypeName methodTypeName) {    	
    	//currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethName(), methodTypeName.getType().struct);
    	//methodTypeName.obj = currentMethod;
    	//Tab.openScope();
		//report_info("Obradjuje se funkcija " + methodTypeName.getMethName(), methodTypeName);
    }

    @Override
    public void visit(MethodDecl methodDecl) {
    	if(!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Semanticka greska na liniji " + methodDecl.getLine() + ": funkcija " + currentMethod.getName() + " nema return iskaz!", null);    		
    	}
    	
    	Tab.chainLocalSymbols(currentMethod);
    	Tab.closeScope();

    	returnFound = false;
    	
    	currentMethod = null;
    }
    
    @Override
    public void visit(SimpleDesignator simpleDesignator) {
    	Obj obj = Tab.find(simpleDesignator.getName());
    	if(obj == Tab.noObj){
			report_error("Greska na liniji " + simpleDesignator.getLine() + " : ime " + simpleDesignator.getName() + " nije deklarisano! ", null);
    	}
    	simpleDesignator.obj = obj;

    }

    @Override
    public void visit(FactorFunctionCall funcCall) {
    	Obj func = funcCall.getFunctionCallName().getDesignator().obj;
    	if(Obj.Meth == func.getKind()){
    		if(Tab.noType == func.getType()) {
				report_error("Semanticka greska " + func.getName() + " ne moze se koristiti u izrazima jer nema povratnu vrednost ", funcCall);    			
    		} else {
				report_info("Pronadjen poziv funkcije " + func.getName() + " na liniji " + funcCall.getLine(), null);
				funcCall.struct = func.getType();
    		}
    	}else{
			report_error("Greska na liniji " + funcCall.getLine() + " : ime " + func.getName() + " nije funkcija!", null);
			funcCall.struct = Tab.noType;
    	}
    	
    }
    
    /*
    @Override
    public void visit(Term term) {
    	term.struct = term.getFactorList()  .struct;
    }
    */
    /*
    @Override
    public void visit(TermExpr termExpr) {
    	termExpr.struct = termExpr.getTerm().struct;
    }
    
    @Override
    public void visit(AddExpt addExpt) {
    	Struct te = addExpt.getExpr().struct;
    	Struct t = addExpt.getTerm().struct;
    		
    	if(te.equals(t) && te == Tab.intType){
    		addExpt.struct = te;
    	}else{
			report_error("Greska na liniji "+ addExpt.getLine()+" : nekompatibilni tipovi u izrazu za sabiranje.", null);
			addExpt.struct = Tab.noType;
    	}
    }
	*/
    
    @Override
    public void visit(FactorNumConst factorNumConst) {
    	factorNumConst.struct = Tab.intType;
    }
    
    @Override
    public void visit(FactorVariable factorVariable) {
    	factorVariable.struct = factorVariable.getDesignator().obj.getType();
    }

    @Override
    public void visit(StetementReturnExpression returnExpr) {
    	returnFound = true;
    	Struct currMethType = currentMethod.getType();
    	if(!currMethType.compatibleWith(returnExpr.getExpr().struct)){
			report_error("Greska na liniji " + returnExpr.getLine() + " : " + "tip izraza u return naredbi ne slaze se sa tipom povratne vrednosti funkcije " + currentMethod.getName(), null);
    	}
    }
    
    @Override
    public void visit(DesignatorAssignOperation assignment) {
    	if(!assignment.getExpr().struct.assignableTo(assignment.getDesignator().obj.getType()))
    		report_error("Greska na liniji " + assignment.getLine() + " : " + " nekompatibilni tipovi u dodeli vrednosti! ", null);    	
    }

    public boolean passed(){
    	return !errorDetected;
    }
    
}
