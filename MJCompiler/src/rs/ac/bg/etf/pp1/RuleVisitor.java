package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;

public class RuleVisitor extends VisitorAdaptor{
	
	int printCallCount = 0;
	
	int varDeclCount = 0;
	
	Logger log = Logger.getLogger(getClass());
	
	@Override
	public void visit(PrintStmt PrintStmt) {		
		printCallCount++;
		log.info("Prepoznata naredba print!");
	}
	
	@Override
	public void visit(VarDeclarations VarDeclarations) {
		varDeclCount++;
	}
	
}
