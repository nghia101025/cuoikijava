package Server;

import java.io.File;
import java.io.FileOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MessageHistory {

    // Phương thức thêm phần tử vào XML
    public void addElement(String name, String content) {
        try {
            File inputFile = new File("C:\\Users\\ADMIN\\Documents\\NetBeansProjects\\LTJV\\src\\Server\\MessageHistory.xml");
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);

            Element rootElement = doc.getDocumentElement();
            Element newElement = doc.createElement(name);
            newElement.setTextContent(content);
            rootElement.appendChild(newElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(inputFile));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức để thêm tin nhắn vào JTextArea
    private void addMessageToUI(String id, String message, String time, int role) {
        if (role == 1) {
            user.Notification.jTextArea1.append(id + message + "\n" + time + "\n");
        } else {
            admin.AdminNotification.jTextArea1.append("You: " + message + "\n" + time + "\n");
        }
    }

    // Phương thức ghi dữ liệu vào XML
    public void writeXML(String clientName, String msgToSend, String stringTime) {
        addElement("name", clientName);
        addElement("content", msgToSend);
        addElement("time", stringTime);
    }

    // Phương thức đọc dữ liệu từ XML
    public void readXML(int role) {
        String name = null;
        String msg = null;
        String stringTime = null;

        try {
            File inputFile = new File("C:\\Users\\ADMIN\\Documents\\NetBeansProjects\\LTJV\\src\\Server\\MessageHistory.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputFile);
            Element rootElement = document.getDocumentElement();
            NodeList nodeList = rootElement.getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i).getNodeType() == Element.ELEMENT_NODE) {
                    Element element = (Element) nodeList.item(i);
                    String tagName = element.getTagName();
                    String textContent = element.getTextContent();

                    if (tagName.equals("name")) {
                        name = textContent;
                    } else if (tagName.equals("content")) {
                        msg = textContent;
                    } else if (tagName.equals("time")) {
                        stringTime = textContent;
                    }

                    if (name != null && msg != null && stringTime != null) {
                        addMessageToUI(name, msg, stringTime, role);
                        name = null;
                        msg = null;
                        stringTime = null;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
