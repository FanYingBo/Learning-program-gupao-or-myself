package builder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @see com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl
 */
public class DocumentBuilderFactoryDemo {

    public static void main(String[] args) {
        // DocumentBuilderFactoryImpl
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setCoalescing(Boolean.TRUE);
        try {
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }


}
