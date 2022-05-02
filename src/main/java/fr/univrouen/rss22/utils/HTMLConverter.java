package fr.univrouen.rss22.utils;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;

public class HTMLConverter {


    public String transformXSL(String xmlFlux, String type) throws TransformerException, IOException {

        Resource xsltRes;

        if(type == "resume") {
            xsltRes = new DefaultResourceLoader().
                    getResource("classpath:xsd/rss22_resume.xslt");
        } else {
            xsltRes = new DefaultResourceLoader().
                    getResource("classpath:xsd/rss22.xslt");

        }


        StreamSource xslt = new StreamSource(xsltRes.getInputStream());
        StreamSource xml = new StreamSource(new StringReader(xmlFlux));

        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);

//        StreamResult result =new StreamResult(new File("classpath:templates/fluxProjet.html"));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer( xslt );
        transformer.setOutputProperty( OutputKeys.ENCODING, "UTF-8" );
        transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
        transformer.transform( xml, result );

        System.out.println(writer.toString());
        System.out.println("Transformation faite avec succés!");

        return writer.toString();
    }


}
