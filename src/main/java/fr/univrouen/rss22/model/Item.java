package fr.univrouen.rss22.model;

//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
//import jakarta.xml.bind.annotation.*;
import javax.xml.bind.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
@Document(collection="rss22")
public class Item  {

    @Id
    @XmlElement
    private String guid;
    @XmlElement
    private String title;
    @XmlElement
    private Category category;
    @XmlElement
    private String published;
    @XmlElement
    private String updated;
    @XmlElement
    private Image image;
    @XmlElement
    private Content content;
    @XmlElement
    private Author author;
    @XmlElement
    private Author contributor;


    public Item(String guid, String title, String published, String updated, Category category, Author author, Author contributor, Image image, Content content) {
        this.guid = guid;
        this.title = title;
        this.published = published;
        this.category = category;
        this.author = author;
        this.content = content;
        this.image = image;
        this.updated = updated;
        this.contributor = contributor;
    }




    public Item() {
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public Author getContributor() {
        return contributor;
    }

    public void setContributor(Author contributor) {
        this.contributor = contributor;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }


//    @Override
//    public String toString() {
//        return ("Article : " + title + "\n(" + guid
//                + ") Le = " + published );
//    }
}


