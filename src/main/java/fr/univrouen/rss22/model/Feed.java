package fr.univrouen.rss22.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
//import jakarta.xml.bind.annotation.*;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
//import jakarta.xml.bind.annotation.*;


import javax.xml.bind.annotation.*;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "feed", namespace = "http://univrouen.fr/rss22")
@XmlAccessorType(XmlAccessType.FIELD)
public class Feed {

    @XmlElement
    private String title;

    @XmlElement
    private String pubDate;

    @XmlElement
    private String copyright;

    @XmlElement
    private Link link;

    @XmlElement(name = "item")
    @JsonProperty("item")
    private List<Item> items;

    @JacksonXmlProperty(isAttribute = true)
    @XmlAttribute(name = "lang", required = true)
    private String lang;


    public Feed(List<Item> item) {
        this.title = "Articles";
        this.copyright = "Binome Ghernaout Meghouche";
        this.pubDate = "2022-04-28T09:50:20";
        this.link = new Link("self","", "");
        this.items = item;
        this.lang = "fr-007";
    }

    public Feed() {

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> item) {
        this.items = item;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
