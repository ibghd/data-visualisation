package graph;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DBLPContentHandler extends DefaultHandler {

	private int depth;
	private String content;
	private HashMap<String, ArrayList<String>> data;
	private String keyword;
	private FileOutputStream outputStream;
	
	public DBLPContentHandler(String keyword, FileOutputStream outputStream) {
		super();
		this.depth = 0;
		this.content = "";
		this.data = new HashMap<String, ArrayList<String>>();
		data.put("title", new ArrayList<String>());
		data.put("author", new ArrayList<String>());
		data.put("year", new ArrayList<String>());
		this.keyword = keyword;
		this.outputStream = outputStream;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		String info = String.valueOf(ch, start, length);
		if (!data.equals("\n")) {
			content += info;
		}
	}

	@Override
	public void startElement(String uri, String localName, String qualifiedName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qualifiedName, attributes);
		depth += 1;
		if (depth == 5) {
			data = new HashMap<String, ArrayList<String>>();
			data.put("dc:title", new ArrayList<String>());
			data.put("dc:creator", new ArrayList<String>());
			data.put("dc:source", new ArrayList<String>());
			data.put("dc:subject", new ArrayList<String>());
		}
        if (depth >= 6) {
        	content = "";
        }
	}

	@Override
	public void endElement(String uri, String localName, String qualifiedName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qualifiedName);
		if (depth == 5) {
			String line = "";
			int authorsCount = 0;
			String authorsNames = "";
			String date = "";
			/*for (String title : data.get("dc:title")) {
				line += title;
			}*/
			/*for (String creator : data.get("dc:creator")) {
				line += ","+ creator;
			}
			for (String source : data.get("dc:source")) {
				line += ","+source;
			}*/
			for (String source : data.get("dc:subject")) {
				line +=  "\""+source+"\"" + ",";
			}
			/*for (String author : data.get("author")) {
				authorsCount += 1;
				authorsNames += "\t" + author;
			}
			for (String year : data.get("year")) {
				date = year;
			}*/
			if (line.contains(keyword)) {
				line +="\n";
				try {
					outputStream.write(line.getBytes("UTF-8"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (depth >= 6) {
			if (data.containsKey(qualifiedName)) {
				data.get(qualifiedName).add(content);
			}
		}
		depth -= 1;
	}	
	
}
