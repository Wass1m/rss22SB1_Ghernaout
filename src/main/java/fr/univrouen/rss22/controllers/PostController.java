package fr.univrouen.rss22.controllers;

import fr.univrouen.rss22.exceptions.FeedNotValidException;
import fr.univrouen.rss22.model.*;
import fr.univrouen.rss22.repositories.FeedRepository;
import fr.univrouen.rss22.utils.XMLConverter;
import fr.univrouen.rss22.utils.ValidatorXML;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class PostController {

    @Autowired
    private FeedRepository feedRepository;

    JAXBContext jaxbContext;
    JAXBContext jaxbUnMarshallContext;
    Unmarshaller jaxbUnmarshaller;
    Marshaller jaxbMarshaller;
    Marshaller jaxbItemMarshaller;


    /**
     *  POST Requests
     */


    /**
     *  REST POST Requests
     *  PREND UN FLUX XML EN ENTREE (RAW XML)
     */

    @PostMapping(value = "/rss22/insert", produces = MediaType.APPLICATION_XML_VALUE)
    public Response insertArticle(@RequestBody String flux, Model model) throws FeedNotValidException, JAXBException, ParserConfigurationException, IOException, SAXException {


        ModelAndView modelAndView = new ModelAndView();


        ValidatorXML validator = new ValidatorXML();
        XMLConverter conv = new XMLConverter();

        // Nous allons convertir notre flux xml string en document xml
        Document doc = conv.convertStringToXMLDocument(flux);
        //Normalize the XML Structure; It's just too important !!
        doc.getDocumentElement().normalize();
        //Here comes the root node
        Element root = doc.getDocumentElement();

        System.out.println(root.getNodeName());

        // Nous avons converti ce dernier pour recuperer son premier noeud
        // afin de savoir si nous avons un flux commencant par le root tag <item> ou  <feed>


        // si nous avons <item> .... </item>
        if(root.getNodeName() == "item") {

            // Afin de formatter seulement l'item en XML
            jaxbContext = JAXBContext.newInstance(Item.class);
            // Afin de formatter tous le Feed en XML
            jaxbUnMarshallContext = JAXBContext.newInstance(Feed.class, Item.class, Author.class, Content.class,
                    Image.class, Link.class, Category.class);

            // Instanciatin du unmarshaller from xml to object
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // Instanciatin du marshaller fron object to xml
            jaxbMarshaller = jaxbUnMarshallContext.createMarshaller();

            // Instanciatin du marshaller from object to xml
            jaxbItemMarshaller = jaxbContext.createMarshaller();

            // propriete pour formater XML
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbItemMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);


            // recuperation du xml et le caster en object item
            Item itemObject = (Item) jaxbUnmarshaller.unmarshal(new StringReader(flux));


            // cas ou un seul element
            // <item> .... </item>
            // en entree (fichier xml ou flux)

            // nous l'ajoutons dans un feed temporaire valide pour notre XSD afin de ne tester que l'item
            String feedFlux =
                    "<rss22:feed lang=\"fr-007\" xmlns:rss22=\"http://univrouen.fr/rss22\">\n" +
                            "    <title>Projet Annuel</title>\n" +
                            "    <pubDate>2022-04-28T09:50:20</pubDate>\n" +
                            "    <copyright>Binome Ghernaout Meghouche</copyright>\n" +
                            "    <link rel=\"self\" type=\"\" href=\"\"/>\n" +
                            flux
                            +
                            "</rss22:feed>";

            // System.out.println(feedFlux);

            boolean valid = false;


            // Validation du flux en entree
            try {

                valid = validator.ValidateXML(feedFlux);

            } catch(FeedNotValidException e) {
                Response response = new Response("VALID_0", "ERROR", "FLUX NON VALIDE : " + e.getMessage());
//                modelAndView.setViewName("info");
//                model.addAttribute("response", response);
//                return modelAndView;
                return response;
            }


            // Si flux valide
            if(valid) {

                // Si article comporte un <published>
                if(itemObject.getPublished() != null) {
                    // Si article existe par titre et date published
                    Item exist =  feedRepository.findByTitleAndPublished(itemObject.getTitle(), itemObject.getPublished());
                    //Si existe par GUID
                    Optional<Item> ifItem = feedRepository.findById(itemObject.getGuid());

                    if(ifItem.isPresent()) {
                        exist = ifItem.get();
                    }
//                    System.out.println(exist);

                    if(exist == null ){
                        Item newItem =  feedRepository.insert(itemObject);
                        Response response = new Response(newItem.getGuid(), "INSERTED", "ARTICLE AJOUTE");
//                        System.out.println(response.getId());
//                        model.addAttribute("response", response);
//                        modelAndView.setViewName("ajoutInfo");
//                        return modelAndView;
                        return response;

                    } else {
//                        System.out.println("Already Exist Published");

                        Response response = new Response("ERR_EXST", "ERROR", "ARTICLE EXISTANT");
//                        modelAndView.setViewName("ajoutInfo");
//                        model.addAttribute("response", response);
//                        return modelAndView;
                        return response;
                    }

                } else {
                    // Si article existe par titre et date updated, comporte <updated>
                    Item exist =  feedRepository.findByTitleAndUpdated(itemObject.getTitle(), itemObject.getUpdated());
                    System.out.println(exist);
                    //Si present par GUID
                    Optional<Item> ifItem = feedRepository.findById(itemObject.getGuid());

                    if(ifItem.isPresent()) {
                        exist = ifItem.get();
                    }

                    if(exist == null ){
                        Item newItem =  feedRepository.insert(itemObject);
                        Response response = new Response(newItem.getGuid(), "INSERTED", "ARTICLE AJOUTE");
//                        System.out.println(response.getId());
//                        model.addAttribute("response", response);
//                        modelAndView.setViewName("ajoutInfo");
//                        return modelAndView;
                        return response;
                    } else {
//                        System.out.println("Already Exist Updated");
                        Response response = new Response("ERR_EXST", "ERROR", "ARTICLE EXISTANT");
//                        modelAndView.setViewName("ajoutInfo");
//                        model.addAttribute("response", response);
//                        return modelAndView;
                        return response;
                    }
                }

            }


        }
        // plusieurs articles(item) dans un feed :
        // <feed>
        // <item> .... </item>
        // <item> .... </item>
        // </feed>
        else {

            jaxbContext = JAXBContext.newInstance(Feed.class, Item.class, Author.class, Content.class,
                    Image.class, Link.class, Category.class);

            jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            Feed feedObject = (Feed) jaxbUnmarshaller.unmarshal(new StringReader(flux));


            boolean valid = false;


            try {

                valid = validator.ValidateXML(flux);

            } catch(FeedNotValidException e) {
                Response response = new Response("VALID_1", "ERROR", "FLUX NON VALIDE : " + e.getMessage());
//                modelAndView.setViewName("info");
//                model.addAttribute("response", response);
//                return modelAndView;
                return response;
            }

            if(valid) {

                // On les items du flux en les castant en Item
                List<Item> newItems = feedObject.getItems();

                // Listes qui vont contenir les GUIDS des articles qui vont etre ajoutes / refuses (si existes deja)
                List<String> addedGuids = new ArrayList<String>();
                List<String> refusedGuids = new ArrayList<String>();

                for(Item item : newItems) {
                    // si contient element published (pour tester existence avec title et published)
                    if(item.getPublished() != null) {

                        // Find by title or published
                        Item exist =  feedRepository.findByTitleAndPublished(item.getTitle(), item.getPublished());


                        Optional<Item> ifItem = feedRepository.findById(item.getGuid());

                        if(ifItem.isPresent()) {
                            exist = ifItem.get();
                        }


                        // si n'existe pas on sert et on ajoute le guid a la liste
                        // Si present par GUID
                        if(exist == null ){
                            Item newItem =  feedRepository.insert(item);
                            addedGuids.add(newItem.getGuid());
//                                return new Response(newItem.getGuid(), "INSERTED", "ARTICLE AJOUTE");
                        } else {
                            // si il existe on sert pas et on ajoute le guid a la liste des refuses
                            System.out.println("Already Exist Published");
//                                return new Response(null, "ERROR", createLongResponse(addedGuids,exist.getGuid(),"error"));
                            refusedGuids.add(exist.getGuid());
                        }

                    } else {
                        // si contient element updated (pour tester existence avec title et updated)
                        // Find by title or updated
                        Item exist =  feedRepository.findByTitleAndUpdated(item.getTitle(), item.getUpdated());

                        // Si present par GUID
                        Optional<Item> ifItem = feedRepository.findById(item.getGuid());

                        if(ifItem.isPresent()) {
                            exist = ifItem.get();
                        }

                        if(exist == null ){
                            Item newItem =  feedRepository.insert(item);
                            addedGuids.add(newItem.getGuid());
//                                return new Response(newItem.getGuid(), "INSERTED", "ARTICLE AJOUTE");
                        } else {
                            System.out.println("Already Exist Updated");
//                                return new Response(null, "ERROR", createLongResponse(addedGuids,exist.getGuid(),"error"));
                            refusedGuids.add(exist.getGuid());
                        }
                    }

                }


                String statusIns = (addedGuids.size() == 0 && refusedGuids.size() > 0) ? "REFUSED" : (addedGuids.size() > 0 && refusedGuids.size() > 0)  ? "INSERTED and REFUSED" : "INSERTED" ;


                // A la fin on affiche la listes des GUIDs ajoutes et ceux refuses
                Response response = new Response("INSRT_FEED", statusIns, "INSERTION FEED", addedGuids, refusedGuids);
//                modelAndView.setViewName("multipleAjoutInfo");
//                model.addAttribute("response", response);
//                return modelAndView;
                return response;

            }

        }


        Response response = new Response("null", "EMPTY", "EMPTY");
//        modelAndView.setViewName("ajoutInfo");
//        model.addAttribute("response", response);
//        return modelAndView;
        return response;
    }



    /**
     *  IHM REST POST Requests
     *  PREND UN FLUX XML OU UN FICHIER XML EN ENTREE
     *  REACT CLIENT
     */

    @PostMapping(value = "/rss22/react/insert", produces = MediaType.APPLICATION_XML_VALUE)
    public Response insertArticleReact(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "flux", required = false) String myFlux) throws FeedNotValidException, JAXBException, ParserConfigurationException, IOException, SAXException {


        ModelAndView modelAndView = new ModelAndView();

        String flux = file != null ? new String(file.getBytes(), StandardCharsets.UTF_8) : myFlux ;


        ValidatorXML validator = new ValidatorXML();
        XMLConverter conv = new XMLConverter();

        // Nous allons convertir notre flux xml string en document xml
        Document doc = conv.convertStringToXMLDocument(flux);
        //Normalize the XML Structure; It's just too important !!
        doc.getDocumentElement().normalize();
        //Here comes the root node
        Element root = doc.getDocumentElement();

        System.out.println(root.getNodeName());

        // Nous avons converti ce dernier pour recuperer son premier noeud
        // afin de savoir si nous avons un flux commencant par le root tag <item> ou  <feed>


        // si nous avons <item> .... </item>
        if(root.getNodeName() == "item") {

            // Afin de formatter seulement l'item en XML
            jaxbContext = JAXBContext.newInstance(Item.class);
            // Afin de formatter tous le Feed en XML
            jaxbUnMarshallContext = JAXBContext.newInstance(Feed.class, Item.class, Author.class, Content.class,
                    Image.class, Link.class, Category.class);

            // Instanciatin du unmarshaller from xml to object
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // Instanciatin du marshaller fron object to xml
            jaxbMarshaller = jaxbUnMarshallContext.createMarshaller();

            // Instanciatin du marshaller from object to xml
            jaxbItemMarshaller = jaxbContext.createMarshaller();

            // propriete pour formater XML
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbItemMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);


            // recuperation du xml et le caster en object item
            Item itemObject = (Item) jaxbUnmarshaller.unmarshal(new StringReader(flux));


            // cas ou un seul element
            // <item> .... </item>
            // en entree (fichier xml ou flux)

            // nous l'ajoutons dans un feed temporaire valide pour notre XSD afin de ne tester que l'item
            String feedFlux =
                    "<rss22:feed lang=\"fr-007\" xmlns:rss22=\"http://univrouen.fr/rss22\">\n" +
                            "    <title>Projet Annuel</title>\n" +
                            "    <pubDate>2022-04-28T09:50:20</pubDate>\n" +
                            "    <copyright>Binome Ghernaout Meghouche</copyright>\n" +
                            "    <link rel=\"self\" type=\"\" href=\"\"/>\n" +
                            flux
                            +
                            "</rss22:feed>";

            // System.out.println(feedFlux);

            boolean valid = false;


            // Validation du flux en entree
            try {

                valid = validator.ValidateXML(feedFlux);

            } catch(FeedNotValidException e) {
                Response response = new Response("VALID_0", "ERROR", "FLUX NON VALIDE : " + e.getMessage());
//                modelAndView.setViewName("info");
//                model.addAttribute("response", response);
//                return modelAndView;
                return response;
            }


            // Si flux valide
            if(valid) {

                // Si article comporte un <published>
                if(itemObject.getPublished() != null) {
                    // Si article existe par titre et date published
                    Item exist =  feedRepository.findByTitleAndPublished(itemObject.getTitle(), itemObject.getPublished());
                    //Si existe par GUID
                    Optional<Item> ifItem = feedRepository.findById(itemObject.getGuid());

                    if(ifItem.isPresent()) {
                        exist = ifItem.get();
                    }
//                    System.out.println(exist);

                    if(exist == null ){
                        Item newItem =  feedRepository.insert(itemObject);
                        Response response = new Response(newItem.getGuid(), "INSERTED", "ARTICLE AJOUTE");
//                        System.out.println(response.getId());
//                        model.addAttribute("response", response);
//                        modelAndView.setViewName("ajoutInfo");
//                        return modelAndView;
                        return response;

                    } else {
//                        System.out.println("Already Exist Published");

                        Response response = new Response("ERR_EXST", "ERROR", "ARTICLE EXISTANT");
//                        modelAndView.setViewName("ajoutInfo");
//                        model.addAttribute("response", response);
//                        return modelAndView;
                        return response;
                    }

                } else {
                    // Si article existe par titre et date updated, comporte <updated>
                    Item exist =  feedRepository.findByTitleAndUpdated(itemObject.getTitle(), itemObject.getUpdated());
                    System.out.println(exist);
                    //Si present par GUID
                    Optional<Item> ifItem = feedRepository.findById(itemObject.getGuid());

                    if(ifItem.isPresent()) {
                        exist = ifItem.get();
                    }

                    if(exist == null ){
                        Item newItem =  feedRepository.insert(itemObject);
                        Response response = new Response(newItem.getGuid(), "INSERTED", "ARTICLE AJOUTE");
//                        System.out.println(response.getId());
//                        model.addAttribute("response", response);
//                        modelAndView.setViewName("ajoutInfo");
//                        return modelAndView;
                        return response;
                    } else {
//                        System.out.println("Already Exist Updated");
                        Response response = new Response("ERR_EXST", "ERROR", "ARTICLE EXISTANT");
//                        modelAndView.setViewName("ajoutInfo");
//                        model.addAttribute("response", response);
//                        return modelAndView;
                        return response;
                    }
                }

            }


        }
        // plusieurs articles(item) dans un feed :
        // <feed>
        // <item> .... </item>
        // <item> .... </item>
        // </feed>
        else {

            jaxbContext = JAXBContext.newInstance(Feed.class, Item.class, Author.class, Content.class,
                    Image.class, Link.class, Category.class);

            jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            Feed feedObject = (Feed) jaxbUnmarshaller.unmarshal(new StringReader(flux));


            boolean valid = false;


            try {

                valid = validator.ValidateXML(flux);

            } catch(FeedNotValidException e) {
                Response response = new Response("VALID_1", "ERROR", "FLUX NON VALIDE : " + e.getMessage());
//                modelAndView.setViewName("info");
//                model.addAttribute("response", response);
//                return modelAndView;
                return response;
            }

            if(valid) {

                // On les items du flux en les castant en Item
                List<Item> newItems = feedObject.getItems();

                // Listes qui vont contenir les GUIDS des articles qui vont etre ajoutes / refuses (si existes deja)
                List<String> addedGuids = new ArrayList<String>();
                List<String> refusedGuids = new ArrayList<String>();

                for(Item item : newItems) {
                    // si contient element published (pour tester existence avec title et published)
                    if(item.getPublished() != null) {

                        // Find by title or published
                        Item exist =  feedRepository.findByTitleAndPublished(item.getTitle(), item.getPublished());


                        Optional<Item> ifItem = feedRepository.findById(item.getGuid());

                        if(ifItem.isPresent()) {
                            exist = ifItem.get();
                        }


                        // si n'existe pas on sert et on ajoute le guid a la liste
                        // Si present par GUID
                        if(exist == null ){
                            Item newItem =  feedRepository.insert(item);
                            addedGuids.add(newItem.getGuid());
//                                return new Response(newItem.getGuid(), "INSERTED", "ARTICLE AJOUTE");
                        } else {
                            // si il existe on sert pas et on ajoute le guid a la liste des refuses
                            System.out.println("Already Exist Published");
//                                return new Response(null, "ERROR", createLongResponse(addedGuids,exist.getGuid(),"error"));
                            refusedGuids.add(exist.getGuid());
                        }

                    } else {
                        // si contient element updated (pour tester existence avec title et updated)
                        // Find by title or updated
                        Item exist =  feedRepository.findByTitleAndUpdated(item.getTitle(), item.getUpdated());

                        // Si present par GUID
                        Optional<Item> ifItem = feedRepository.findById(item.getGuid());

                        if(ifItem.isPresent()) {
                            exist = ifItem.get();
                        }

                        if(exist == null ){
                            Item newItem =  feedRepository.insert(item);
                            addedGuids.add(newItem.getGuid());
//                                return new Response(newItem.getGuid(), "INSERTED", "ARTICLE AJOUTE");
                        } else {
                            System.out.println("Already Exist Updated");
//                                return new Response(null, "ERROR", createLongResponse(addedGuids,exist.getGuid(),"error"));
                            refusedGuids.add(exist.getGuid());
                        }
                    }

                }


                String statusIns = (addedGuids.size() == 0 && refusedGuids.size() > 0) ? "REFUSED" : (addedGuids.size() > 0 && refusedGuids.size() > 0)  ? "INSERTED and REFUSED" : "INSERTED" ;


                // A la fin on affiche la listes des GUIDs ajoutes et ceux refuses
                Response response = new Response("INSRT_FEED", statusIns, "INSERTION FEED", addedGuids, refusedGuids);
//                modelAndView.setViewName("multipleAjoutInfo");
//                model.addAttribute("response", response);
//                return modelAndView;
                return response;

            }

        }


        Response response = new Response("null", "EMPTY", "EMPTY");
//        modelAndView.setViewName("ajoutInfo");
//        model.addAttribute("response", response);
//        return modelAndView;
        return response;
    }


}

