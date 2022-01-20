package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CodeGenerator extends VisitorAdaptor {
	
	private int mainPc;	// program start (in x86 it is _start label)
	
	public int getMainPc() {
		return mainPc;
	}
	
}


