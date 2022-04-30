package fr.univrouen.rss22.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
//import fr.univrouen.rss22.model.Feed;
//import fr.univrouen.rss22.model.Item;
import fr.univrouen.rss22.model.*;
import fr.univrouen.rss22.repositories.FeedRepository;
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
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
    public class GetController {

    @Autowired
    private FeedRepository feedRepository;
    JAXBContext jaxbContext;
    Marshaller jaxbItemMarshaller;

    @RequestMapping(value = "/rss22/resume/xml", method = RequestMethod.GET, produces="application/xml")
    @ResponseBody
    public List<ResumeItem> getResumeItem() throws JsonProcessingException {
        List<Item> itemList = feedRepository.findAll();
        List<ResumeItem> itemResumeList = new ArrayList<ResumeItem>();
        for (Item item : itemList) {
            ResumeItem resumeItem = new ResumeItem(item.getGuid(), item.getPublished(), item.getTitle());
            itemResumeList.add(resumeItem);
        }
        return itemResumeList;
    }



    @RequestMapping(value = "/rss22/feed/xml", method = RequestMethod.GET, produces="application/xml")
    @ResponseBody
    public Feed getFeedItem() throws JsonProcessingException {
        List<Item> itemList = feedRepository.findAll();
        List<ResumeItem> itemResumeList = new ArrayList<ResumeItem>();
        List<Item> newList = new ArrayList<Item>();
        for (Item item : itemList) {
            newList.add(item);
        }
        Feed myFeed = new Feed(itemList);
        System.out.println(itemList);
        return myFeed;
    }

    @RequestMapping(value = { "/rss22/resume/html" }, method = RequestMethod.GET)
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

//
//    @RequestMapping(value = "/rss22/resume/html", method = RequestMethod.GET, produces="text/html")
//    @ResponseBody
//    public ModelAndView getResumeItemHTML(Model model) throws JsonProcessingException {
//        List<Item> itemList = feedRepository.findAll();
//        List<ResumeItem> itemResumeList = new ArrayList<ResumeItem>();
//        for (Item item : itemList) {
//            ResumeItem resumeItem = new ResumeItem(item.getGuid(), item.getPublished(), item.getTitle());
//            itemResumeList.add(resumeItem);
//        }
//
//        ModelAndView modelAndView = new ModelAndView();
//        model.addAttribute("articles", itemResumeList);
//        modelAndView.setViewName("resume");
//        return modelAndView;
//    }
//

    @GetMapping(value = "/rss22/resume/xml/{guid}", produces="application/xml")
    public String getItemById(@PathVariable String guid) throws JsonProcessingException, JAXBException {
        Optional<Item> item = feedRepository.findById(guid);
        JSONObject obj = new JSONObject();

        Item returnedItem = null;


        // Afin de formatter seulement l'item en XML
        jaxbContext = JAXBContext.newInstance(Item.class);

        // Instanciatin du marshaller from object to xml
        jaxbItemMarshaller = jaxbContext.createMarshaller();

        jaxbItemMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

        if (item.isPresent()) {

            returnedItem = item.get();

            StringWriter stringWriter = new StringWriter();

            jaxbItemMarshaller.marshal(returnedItem, stringWriter);

            return stringWriter.toString();

        } else {
            JSONObject errorObj = new JSONObject();
            errorObj.append("guid", guid);
            errorObj.append("status", "ERROR");
            obj.append("error", errorObj);
            return XML.toString(obj);
        }

    }

    @GetMapping(value = "/rss22/resume/html/{guid}", produces="application/xml")
    public ModelAndView getItemByIdHtml(@PathVariable String guid, Model model) throws JsonProcessingException {
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



    }


