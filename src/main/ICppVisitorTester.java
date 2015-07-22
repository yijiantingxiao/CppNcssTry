package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import visitors.ICppMetricExtractionVisitor;
import cppast.AstTranslationUnit;
import cppast.ParseException;
import cppast.Parser;
/*import cppncss.CppNcss;
import cpptools.ConsoleLogger;
import cpptools.Options;*/

public class ICppVisitorTester {

	public static void main(String[] args) {
		try {	
			/*String[] paras = {"D:/git/lprog/include/matrix.cpp"};
			final Options options = new Options(paras);
	        new CppNcss( options, new ConsoleLogger(options)).run();*/
	        
			BufferedReader reader = new BufferedReader(new FileReader("D:/git/lprog/include/matrix.cpp"));
			Parser parser = new Parser(reader);
			AstTranslationUnit unit = parser.translation_unit();
			ICppMetricExtractionVisitor metricExtractionVisitor = new ICppMetricExtractionVisitor();
			unit.jjtAccept(metricExtractionVisitor, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
