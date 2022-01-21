package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

public class Compiler {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws Exception {

		Logger log = Logger.getLogger(MJParserTest.class);
		
		Reader br = null;
		try {
			
			String mjFileName = "program";
			
			File sourceCode = new File("test/" + mjFileName + ".mj");
//			File sourceCode = new File("test/class_extends.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();  //pocetak parsiranja
	        
	        // Symbol table initialization
	        Tab.init();
	        
	        Program prog = (Program)(s.value); 
			// ispis sintaksnog stabla
			log.info(prog.toString(""));
			log.info("===================================");

			// ispis prepoznatih programskih konstrukcija
			//RuleVisitor v = new RuleVisitor();
			
			SemanticAnalyzer v = new SemanticAnalyzer();
			prog.traverseBottomUp(v); 
	      
			//log.info(" Print count calls = " + v.printCallCount);

			//log.info(" Deklarisanih promenljivih ima = " + v.varDeclCount);
			
			log.info("===================================");
	        //Tab.dump();
	        
			v.tsdump();
			
	        if(!p.errorDetected && v.passed()) {
				log.info("Parsiranje uspesno zavrseno!");


				File objFile = new File("test/" + mjFileName + ".obj");
				if(objFile.exists()) objFile.delete();
				
				CodeGenerator codeGenerator = new CodeGenerator();
				prog.traverseBottomUp(codeGenerator);
				
				Code.dataSize = v.getProgramVariablesNumber();
				Code.mainPc = codeGenerator.getFirstInstruction();
				Code.write(new FileOutputStream(objFile));
	        	
				
	        }else{
				log.error("Parsiranje NIJE uspesno zavrseno!");
			}

			
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}
		
	}
	
	private static void semanticAnalysis() throws Exception {
		Logger log = Logger.getLogger(MJParserTest.class);
		
		Reader br = null;
		try {
			File sourceCode = new File("test/program.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();  //pocetak parsiranja
	        Tab.init();
	        Program prog = (Program)(s.value); 
			// ispis sintaksnog stabla
			log.info(prog.toString(""));
			log.info("===================================");

			// ispis prepoznatih programskih konstrukcija
			// RuleVisitor v = new RuleVisitor();
			
			SemanticPass v = new SemanticPass();
			prog.traverseBottomUp(v); 
	      
			log.info(" Print count calls = " + v.printCallCount);

			log.info(" Deklarisanih promenljivih ima = " + v.varDeclCount);
			
			log.info("===================================");
	        Tab.dump();
	        
	        if(!p.errorDetected && v.passed()) {
				log.info("Parsiranje uspesno zavrseno!");
			}else{
				log.error("Parsiranje NIJE uspesno zavrseno!");
			}
	        
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}
		
	}

	private static void codeGenerator() throws Exception {
		Logger log = Logger.getLogger(MJParserTest.class);
		
		String mjFileName = "program";
		
		Reader br = null;
		try {
			File sourceCode = new File("test/" + mjFileName + ".mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();  //pocetak parsiranja
	        Tab.init();
	        Program prog = (Program)(s.value); 
			// ispis sintaksnog stabla
			log.info(prog.toString(""));
			log.info("===================================");

			// ispis prepoznatih programskih konstrukcija
			// RuleVisitor v = new RuleVisitor();
			
			SemanticPass v = new SemanticPass();
			prog.traverseBottomUp(v); 
	      
			log.info(" Print count calls = " + v.printCallCount);

			log.info(" Deklarisanih promenljivih ima = " + v.varDeclCount);
			
			log.info("===================================");
	        Tab.dump();
	        
	        if(!p.errorDetected && v.passed()) {
	        	
				File objFile = new File("test/" + mjFileName + ".obj");
				if(objFile.exists()) objFile.delete();
				
				CodeGeneratorTutorial codeGenerator = new CodeGeneratorTutorial();
				prog.traverseBottomUp(codeGenerator);
				
				Code.dataSize = v.nVars;
				Code.mainPc = codeGenerator.getMainPc();
				Code.write(new FileOutputStream(objFile));
	        	
	        	log.info("Parsiranje uspesno zavrseno!");
			}else{
				log.error("Parsiranje NIJE uspesno zavrseno!");
			}
	        
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}
		
	}

	
}
