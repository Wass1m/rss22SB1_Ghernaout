package fr.univrouen.rss22.utils;


import fr.univrouen.rss22.exceptions.FeedNotValidException;
//import fr.univrouen.rss22.model.Item;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.ws.http.HTTPException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class ValidatorXML {


    public boolean ValidateXML(String feed) throws ParserConfigurationException, SAXException, IOException, FeedNotValidException{

        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(false);
            factory.setNamespaceAware(true);

            SchemaFactory schemaFactory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            Resource resource = new DefaultResourceLoader().
                    getResource("classpath:xsd/rss22.xsd");

            factory.setSchema(schemaFactory.newSchema(
                    new Source[] {new StreamSource(resource.getInputStream())}));

//
//            factory.setSchema(schemaFactory.newSchema(
//                    new Source[] {new StreamSource("src/main/resources/xsd/rss22.xsd")}));


//            SAXParser parser = factory.newSAXParser();

//            XMLReader reader = parser.getXMLReader();
            //SimpleErrorHandler error = new SimpleErrorHandler();
            //reader.setErrorHandler(error);
//            reader.parse(new InputSource());

            javax.xml.validation.Validator validator = factory.getSchema().newValidator();

            validator.validate(new StreamSource(new StringReader(feed)));


            return true;

        }  catch (SAXException exp) {
            throw new FeedNotValidException(exp.getMessage()+exp);
        }   catch (IOException exp) {
            throw new FeedNotValidException(exp.getMessage()+exp);
        }





    }





//    public static void Validate(Item item) throws JAXBException {
//        try {
//            JAXBContext context = JAXBContext.newInstance(Item.class);
//            Marshaller marshaller = context.createMarshaller();
//            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//            Schema schema = sf.newSchema(new StreamSource(new File("./rss22.xsd")));
//            marshaller.setSchema(schema);
//            marshaller.setEventHandler(event -> false);
//            marshaller.marshal(item, System.out);
//        }
//        catch (SAXException e){
//            throw new HTTPException(500);
//        }
//    }

//    public boolean Validate_2(String stb20) throws FeedNotValidException {
//        try {
//
//            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//            Schema schema = factory.newSchema(PUTController.class.getResource("/xsd/stb20.xsd"));
//            javax.xml.validation.Validator validator = schema.newValidator();
//            validator.validate(new StreamSource(new StringReader(stb20)));
//
//            return true;
//
//        } catch (SAXException exp) {
//            throw new FeedNotValidException(exp.getMessage()+exp);
//        } catch (IOException exp) {
//            throw new FeedNotValidException(exp.getMessage()+exp);
//        }
//    }
}
