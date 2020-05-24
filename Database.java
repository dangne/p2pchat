//package Data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Database {
	
	private String DataPath;
	
	public Database(String DataPath){
        this.DataPath = DataPath;
    }
	
	public boolean checkUserExist(String username) {
		try {
			File xmlFile = new File(DataPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			// now XML is loaded as Document in memory, lets convert it to Object List
//			List<Users> userList = new ArrayList<Users>();
			NodeList nodeList = doc.getElementsByTagName("User");

			for (int i = 0; i < nodeList.getLength(); i++) {
//				userList.add(getUser(nodeList.item(i)));
				Node node = (Node) nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					System.out.println(getTagValue("username", element));
					if (getTagValue("username", element).equals(username)) {
						return true;
					}
				}
			}
			System.out.println("Login Failed");
			return false;
		} catch (SAXException | ParserConfigurationException | IOException e1) {
			e1.printStackTrace();
			return false;
		}
	}
	
	public boolean checkLogin(String username, String password) {
		
		if (!checkUserExist(username)) {
			return false;
		}
		
		try {
			File xmlFile = new File(DataPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			// now XML is loaded as Document in memory, lets convert it to Object List
//			List<Users> userList = new ArrayList<Users>();
			NodeList nodeList = doc.getElementsByTagName("User");

			for (int i = 0; i < nodeList.getLength(); i++) {
//				userList.add(getUser(nodeList.item(i)));
				Node node = (Node) nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					if (getTagValue("username", element).equals(username) && getTagValue("password", element).equals(password)) {
						return true;
					}
				}
			}
			System.out.println("Login Failed");
			return false;
		} catch (SAXException | ParserConfigurationException | IOException e1) {
			e1.printStackTrace();
			return false;
		}
	}
	
	public void addUser(String username, String password) {
		try {
			File xmlFile = new File(DataPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			Element rootElement = doc.getDocumentElement();
			Element user = doc.createElement("User");

	        // set username attribute
	        user.appendChild(createUserElements(doc, user, "username", username));

	        // create password element
	        user.appendChild(createUserElements(doc, user, "password", password));
	        rootElement.appendChild(user);
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(DataPath));
            transformer.transform(source, result);
			
		} catch (SAXException | ParserConfigurationException | IOException e1) {
			e1.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String getTagValue(String tag, Element element) {
		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodeList.item(0);
		return node.getNodeValue();
	}
	
	private static Node createUserElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}
