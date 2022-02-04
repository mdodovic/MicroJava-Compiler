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
			
			String mjFileName = args[0];
			
			File sourceCode = new File("test/" + mjFileName + ".mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();
	        
	        Tab.init();
	        
	        Program prog = (Program)(s.value); 

	        if(!p.errorDetected) {

				log.info(prog.toString(""));
				log.info("===================================");
			

				SemanticAnalyzer v = new SemanticAnalyzer();
				prog.traverseBottomUp(v); 
		      
				log.info("===================================");
		        
				v.tsdump();
				
		        if(!p.errorDetected && v.passed()) {
					log.info("Parsiranje uspesno zavrseno!");
	
	
					File objFile = new File("test/" + mjFileName + ".obj");
					if(objFile.exists()) objFile.delete();
					
					CodeGenerator codeGenerator = new CodeGenerator();
					prog.traverseBottomUp(codeGenerator);
					
					Code.dataSize = SemanticAnalyzer.getProgramVariablesNumber();
					Code.mainPc = codeGenerator.getFirstInstruction();
					Code.write(new FileOutputStream(objFile));
		        	
					log.info("Generisanje koda uspesno zavrseno!");				
					
		        }else{
					log.error("Parsiranje NIJE uspesno zavrseno!");
				}
	
			}
			
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}
		
	}
	
	
}
