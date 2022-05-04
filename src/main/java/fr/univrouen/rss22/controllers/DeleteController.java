package fr.univrouen.rss22.controllers;

import fr.univrouen.rss22.model.Feed;
import fr.univrouen.rss22.model.FormField;
import fr.univrouen.rss22.model.Item;
import fr.univrouen.rss22.model.Response;
import fr.univrouen.rss22.repositories.FeedRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class DeleteController {
    @Autowired
    private FeedRepository feedRepository;
    JAXBContext jaxbContext;
    Marshaller jaxbItemMarshaller;

    //   les formulaires en JSP ne supportant pas la methode DELETE, un POST a ete fait en bas (deleteArticleIHMById)
    @DeleteMapping(value = "/rss22/delete/{guid}", produces = MediaType.APPLICATION_XML_VALUE)
    public Response deleteArticleById(@PathVariable String guid, Model model) throws JAXBException {
        Optional<Item> item = feedRepository.findById(guid);


        Response response;

        // Afin de formatter seulement l'item en XML
        jaxbContext = JAXBContext.newInstance(Response.class);

        // Instanciatin du marshaller from object to xml
        jaxbItemMarshaller = jaxbContext.createMarshaller();

        jaxbItemMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

        StringWriter stringWriter = new StringWriter();

        if (item.isPresent()) {

            Item itemDeleted = item.get();

            feedRepository.delete(itemDeleted);

            response = new Response(itemDeleted.getGuid(), "DELETED", "SUPPRESSION DE L'ARTICLE");


        } else {

            response = new Response("ERR_DEL", "ERROR", "ERREUR SUPPRESSION DE L'ARTICLE " + guid);
        }


        jaxbItemMarshaller.marshal(response, stringWriter);


        return response;


    }

    //   les formulaires en JSP ne supportant pas la methode DELETE, un POST a ete fait.
    @PostMapping(value = "/rss22/delete/ihm/{guid}")
    public Response deleteArticleIHMById(@PathVariable String guid, Model model) {

        Optional<Item> item = feedRepository.findById(guid);

        Response response;

        if (item.isPresent()) {
            Item itemDeleted = item.get();
            feedRepository.delete(itemDeleted);
            response = new Response(itemDeleted.getGuid(), "DELETED", "SUPPRESSION DE L'ARTICLE");
        } else {
            response = new Response("ERR_DEL", "ERROR", "ERREUR SUPPRESSION DE L'ARTICLE " + guid);


        }
        return response;
    }


}