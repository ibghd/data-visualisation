package graph;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DBLPParser {

	/**
	 * Write a dblp_extract.txt file from <b>filepath</b> with only treatises' title, authors' names and date of publication separated by \t for treatises including <b>keyword</b> in the title written by multiple authors
	 * @param filepath
	 * @param keyword
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void readXML(String filepath, String keyword) throws ParserConfigurationException, SAXException, IOException {
		
		System.out.println("Writing a dblp_extract.txt file from " + filepath + " with only treatises' title, authors' names and date of publication separated by '\t' for treatises including " + keyword + " in the title written by multiple authors");
		FileOutputStream output = new FileOutputStream("dblp_extract.txt");
		
		System.setProperty("jdk.xml.entityExpansionLimit", "0");
	    SAXParserFactory parserFactory = SAXParserFactory.newInstance();
	    SAXParser parser = parserFactory.newSAXParser();
	    File file = new File(filepath);
	    DefaultHandler handler = new DBLPContentHandler(keyword, output);
	    parser.parse(file, handler);
	    
		System.out.println("Writing dblp_extract.txt done");
	    
	}
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		
		String keyword = "cryptography";
		
		/**
		 * Write a dblp_extract.txt file from dblp.xml 
		 * with only treatises' title, authors' names and date of publication separated by \t for treatises including keyword in the title written by multiple authors
		 * dblpextract.txt file structure: Title\tAuthor...\tYear\n
		 */
		DBLPParser.readXML("testInfo.xml", keyword);
		
	}
	
}