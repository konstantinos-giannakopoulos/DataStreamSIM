package util.handlefile;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
 
/**
 * Parse an XML file, with an XML DOM parser.
 * 
 * @version 1.0
 * @author kostas
 *
 */
public class ReadXMLFile {
	
	private String filepath;
	
	/**
	 * Constructor of class <class>ReadXMLFile</class>.
	 */
	public ReadXMLFile(String path) {
		this.filepath = path;
	}//end constructor ReadXMLFile()
	
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
 
	/**
	 * Parse the configuration file.
	 * 
	 * @return The list of the configured parameters.
	 */
	public ArrayList<String> parseConfFile() {
		ArrayList<String> values = new ArrayList<String>();
		try {
			File fXmlFile = new File(getFilepath());
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.parse(fXmlFile);
		    doc.getDocumentElement().normalize();
		 
//		    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		    NodeList nList = doc.getElementsByTagName("configuration");
//		    System.out.println("-----------------------");
		 
		    for (int temp = 0; temp < nList.getLength(); temp++) {		 
		    	Node nNode = nList.item(temp);	    
		    	if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		    		Element eElement = (Element) nNode;	 
//		    		System.out.println("System Mode : "  + getTagValue("mode",eElement));
//		    		System.out.println("System Clock : "  + getTagValue("clock",eElement));	 
		    		values.add(getTagValue("mode",eElement));
		    		values.add(getTagValue("clock",eElement));
		        }
		    }//end for-loop	
		    return values;
		  } catch (Exception e) {
			  e.printStackTrace();
		  }//end try-catch
		  return null;
	 }//end method parseConfFile()
	 
	 private static String getTagValue(String sTag, Element eElement){
	    NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0); 
	 
	    return nValue.getNodeValue();    
	 }//end method getTagValue
	 
}//end class ReadXMLFile
