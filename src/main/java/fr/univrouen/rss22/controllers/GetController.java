package fr.univrouen.rss22.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
//import fr.univrouen.rss22.model.Feed;
//import fr.univrouen.rss22.model.Item;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.univrouen.rss22.model.*;
import fr.univrouen.rss22.repositories.FeedRepository;
import fr.univrouen.rss22.utils.HTMLConverter;
import fr.univrouen.rss22.utils.XMLConverter;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.crypto.dsig.XMLObject;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
    public class GetController {

    @Autowired
    private FeedRepository feedRepository;
    JAXBContext jaxbContext;
    Marshaller jaxbItemMarshaller;

    /**
     *  GET Requests
     */


    /**
     *
     */


    @RequestMapping(value = "/rss22/resume/xml", method = RequestMethod.GET, produces="application/xml")
    @ResponseBody
    public List<ResumeItem> getResumeItem() throws JsonProcessingException {
         // Recuperations des articles
        List<Item> itemList = feedRepository.findAll();
        List<ResumeItem> itemResumeList = new ArrayList<ResumeItem>();
        // Transformations des articles en articles resume (guid,title,date)
        for (Item item : itemList) {
            ResumeItem resumeItem = new ResumeItem(item.getGuid(), item.getPublished(), item.getTitle());
            itemResumeList.add(resumeItem);
        }
        return itemResumeList;
    }


    // Meme chose que getResumeItem sauf que les ajoute des un feed rss22
    @RequestMapping(value = "/rss22/resume/html", method = RequestMethod.GET, produces="application/xml")
    @ResponseBody
    public String getFeedItem() throws IOException, JAXBException, TransformerException {
        List<Item> itemList = feedRepository.findAll();
        List<ResumeItem> itemResumeList = new ArrayList<ResumeItem>();
        List<Item> newList = new ArrayList<Item>();
        for (Item item : itemList) {
            newList.add(item);
        }
        Feed myFeed = new Feed(itemList);

        // Afin de formatter seulement l'item en XML
        jaxbContext = JAXBContext.newInstance(Feed.class, Item.class, Author.class, Content.class,
                Image.class, Link.class, Category.class);

        // Instanciatin du marshaller from object to xml
        jaxbItemMarshaller = jaxbContext.createMarshaller();

        jaxbItemMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);


        StringWriter stringWriter = new StringWriter();

        jaxbItemMarshaller.marshal(myFeed, stringWriter);

        HTMLConverter htmlConverter = new HTMLConverter();

        System.out.println(stringWriter.toString());

//            UTILISATION DE XSLT POUR TRANSFORMER LE XML EN HTML
        String html = htmlConverter.transformXSL(stringWriter.toString(), "resume");

        System.out.println(itemList);


        return html;
    }



    // Retourne un article dans un flux xml rss22   (Feed)
    @GetMapping(value = "/rss22/resume/xml/{guid}", produces="application/xml")
    public String getItemById(@PathVariable String guid) throws JsonProcessingException, JAXBException {
        Optional<Item> item = feedRepository.findById(guid);
        JSONObject obj = new JSONObject();

        Item returnedItem = null;


        // Afin de formatter seulement l'item en XML
        jaxbContext = JAXBContext.newInstance(Feed.class);

        // Instanciatin du marshaller from object to xml
        jaxbItemMarshaller = jaxbContext.createMarshaller();

        jaxbItemMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

        if (item.isPresent()) {


            returnedItem = item.get();

            List<Item> myItems = new ArrayList<Item>();

            myItems.add(returnedItem);

            Feed myFeed = new Feed(myItems);

            StringWriter stringWriter = new StringWriter();

            jaxbItemMarshaller.marshal(myFeed, stringWriter);

            return stringWriter.toString();

        } else {
            JSONObject errorObj = new JSONObject();
            errorObj.append("guid", guid);
            errorObj.append("status", "ERROR");
            obj.append("error", errorObj);
            return XML.toString(obj);
        }

    }



    // Retourne un article dans une page HTML pour une petite IHM JSP
    @GetMapping(value = "/rss22/html/{guid}", produces="application/xml")
    public String getItemByIdHtml(@PathVariable String guid, Model model) throws IOException, JAXBException, TransformerException {
        Optional<Item> item = feedRepository.findById(guid);
        ModelAndView modelAndView = new ModelAndView();
        Response response;

        if (item.isPresent()) {
            Item returnedItem = item.get();
//            response = new Response(returnedItem.getGuid(), "GET", "ARTICLE");


            List<Item> newList = new ArrayList<Item>();
            newList.add(returnedItem);
            Feed myFeed = new Feed(newList);

            // Afin de formatter seulement l'item en XML
            jaxbContext = JAXBContext.newInstance(Feed.class, Item.class, Author.class, Content.class,
                    Image.class, Link.class, Category.class);

            // Instanciatin du marshaller from object to xml
            jaxbItemMarshaller = jaxbContext.createMarshaller();

            jaxbItemMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);


            StringWriter stringWriter = new StringWriter();

            jaxbItemMarshaller.marshal(myFeed, stringWriter);

            HTMLConverter htmlConverter = new HTMLConverter();

            System.out.println(stringWriter.toString());

//            UTILISATION DE XSLT POUR TRANSFORMER LE XML EN HTML
            String html = htmlConverter.transformXSL(stringWriter.toString(), "total");


            return html;

        } else {
            // RENVOI D'une REPONSE EN HTML
            String html = "<!DOCTYPE html\n" +
                    "  PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" +
                    "<html>\n" +
                    "    <head>\n" +
                    "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                    "            <title>\n" +
                    "\t\t\t\t\tdocument\n" +
                    "\t\t\t\t</title>\n" +
                    "            <link href=\"style.css\" rel=\"stylesheet\">\n" +
                    "   \n" +
                    "            </head>\n" +
                    "            <body>\n" +
                    "                <h1>\n" +
                    "         \t\t\t\t   PROJET ANNUEL - Flux RSS22\n" +

                    "                <h2> ARTICLE NON EXISTANT</h2>\n" +
                    "                <ul>\n" +
                    "                    <li>Status : ERROR </li>\n" +
                    "                    <li>ID : " + guid + "</li>\n" +
                    "                </ul>" +

                    "            </body>\n" +
                    "        </html>";

            return html;
        }



    }


    // Retourne la liste des articles resume dans une page HTML comme REST
    @RequestMapping(value = { "/rss22/resume/ihm/html" }, method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getArticleResume(Model model) {
        List<Item> itemList = feedRepository.findAll();
        List<ResumeItem> itemResumeList = new ArrayList<ResumeItem>();
        for (Item item : itemList) {
            ResumeItem resumeItem = new ResumeItem(item.getGuid(), item.getPublished(), item.getTitle());
            itemResumeList.add(resumeItem);
        }

        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("articles", itemResumeList);
        modelAndView.setViewName("resume");
        return modelAndView;
    }



    // Retourne la liste des articles resume dans une page HTML pour l'IHM
    @RequestMapping(value = { "/rss22/resume/react/html" }, method = RequestMethod.GET)
    @ResponseBody
    public String getArticleResumeIHM() throws JsonProcessingException {
        List<Item> itemList = feedRepository.findAll();
        List<ResumeItem> itemResumeList = new ArrayList<ResumeItem>();
        for (Item item : itemList) {
            ResumeItem resumeItem = new ResumeItem(item.getGuid(), item.getPublished(), item.getTitle());
            itemResumeList.add(resumeItem);
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(itemResumeList);
        return json;
    }


    // Retourne un article dans une page HTML pour une petite IHM JSP
    @GetMapping(value = "/rss22/html/ihm/{guid}", produces="application/xml")
    public ModelAndView getItemByIdHtmlIHM(@PathVariable String guid, Model model) throws JsonProcessingException {
        Optional<Item> item = feedRepository.findById(guid);
        ModelAndView modelAndView = new ModelAndView();
        Response response;

        if (item.isPresent()) {
            Item returnedItem = item.get();
            response = new Response(returnedItem.getGuid(), "GET", "ARTICLE");
            model.addAttribute("myItem", returnedItem);
            modelAndView.setViewName("item");
        } else {
            response = new Response("ERR_GET", "ERROR", "ARTICLE : " + guid);
            model.addAttribute("response", response);
            modelAndView.setViewName("info");
        }

        return modelAndView;

    }



    // Retourne un article dans une page HTML pour notre service principal (sera servi au client)
    @GetMapping(value = "/rss22/html/react/{guid}", produces="application/xml")
    public String getItemByIdHtmlRest(@PathVariable String guid, Model model) throws JsonProcessingException {
        Optional<Item> item = feedRepository.findById(guid);
        Response response;
        String json = "";
        ObjectMapper mapper = new ObjectMapper();

        if (item.isPresent()) {
            Item returnedItem = item.get();
            response = new Response(returnedItem.getGuid(), "GET", "ARTICLE");
            json = mapper.writeValueAsString(returnedItem);
        } else {
            response = new Response("ERR_GET", "ERROR", "ARTICLE : " + guid);
            json = mapper.writeValueAsString(response);
        }
        return json;
    }



    }


