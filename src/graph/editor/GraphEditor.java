package graph.editor;

import java.awt.Dimension;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GraphEditor implements FramesController {

	public final static String MENU_FILE = "File";
	public final static String MENU_ITEM_NEW = "New";
        public final static String MENU_ITEM_SAVE = "Save";
        public final static String MENU_ITEM_LOAD = "Load";
	public final static String MENU_ITEM_CLOSE = "Close";
	public final static String MENU_ITEM_QUIT = "Quit";
	public final static String MENU_ITEM_CHECKSTRING = "Check String";

	public final static String DIALOG_QUIT_MSG = "Do you really want to quit ?";
	public final static String DIALOG_QUIT_TITLE = "Quit ?";

	public final static String TITLE = "Graph Automata";

	private static final List<JFrame> frames = new ArrayList<JFrame>();

	@Override
	public void quit() {
		int answer = JOptionPane.showConfirmDialog(null, DIALOG_QUIT_MSG, DIALOG_QUIT_TITLE, JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	@Override
	public JFrame createFrame() {
		JFrame frame = new GraphFrame(this);
		frame.setTitle(TITLE);
		int pos = 30 * (frames.size() % 5);
		frame.setLocation(pos, pos);
		frame.setPreferredSize(new Dimension(800, 600));
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frames.add(frame);
		return frame;
	}

	@Override
	public void deleteFrame(JFrame frame) {
		if (frames.size() > 1) {
			frames.remove(frame);
			frame.dispose();
		} else {
			quit();
		}
	}
        
        public void loadFrame(File f) {
            try {
			File inputFile = new File("D:\\temp\\res.xml");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(inputFile);

			// Read doc
			Node rootNode = doc.getFirstChild();
			System.out.println("root node = " + rootNode.getNodeName());

			// book nodes
			NodeList books = doc.getElementsByTagName("book");
			for (int i = 0; i < books.getLength(); i++) {
				Node book = books.item(i);
				System.out.println("Node " + book.getNodeName());

				// Attributes
				NamedNodeMap attrs = book.getAttributes();
				for (int j = 0; j < attrs.getLength(); j++) {
					Node attr = attrs.item(j);
					System.out.println(" -- attr " + attr.getNodeName() + " = " + attr.getNodeValue());
				}

				// Children
				NodeList children = book.getChildNodes();
				for (int j = 0; j < children.getLength(); j++) {
					Node child = children.item(j);
					System.out.println(
							" -- childnode " + child.getNodeName() + " = " + child.getFirstChild().getNodeValue());
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
        }
        
        public void saveFrame(File f,String pathFile,ArrayList<String> edgeList,ArrayList<String> vertexList){
            // Getting the size of the list
            int sizeListV = vertexList.size();
            System.out.println(sizeListV);
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.newDocument();
                
                // root element
                Element rootElement = doc.createElement("automatan");
                doc.appendChild(rootElement);
             
                
                for (int i = 0; i < sizeListV; i++) {                   
                        // book element
			Element book = doc.createElement("book");
			rootElement.appendChild(book);

			// setting attribute to element
			Attr attr = doc.createAttribute("id");
			attr.setValue("1");
			book.setAttributeNode(attr);

			// title element
			Element title = doc.createElement("title");
			title.appendChild(doc.createTextNode("Tu hoc lap trinh"));
			book.appendChild(title);

			Element author = doc.createElement("author");
			author.appendChild(doc.createTextNode("By Me"));
			book.appendChild(author);
                }  
                
           
                // write the content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                //StreamResult result = new StreamResult(new File("D:\\temp\\res.xml"));
                
                StreamResult result = new StreamResult(new File(pathFile+".xml"));
                transformer.transform(source, result);

                // Output to console for testing
                StreamResult consoleResult = new StreamResult(System.out);
                transformer.transform(source, consoleResult);
            } catch (ParserConfigurationException | TransformerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
                    
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GraphEditor().createFrame();
			}
		});
	}
}