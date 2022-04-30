package fr.univrouen.rss22.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "author")
@XmlAccessorType(XmlAccessType.FIELD)
public class Author {


    @XmlElement
    private String name;
    @XmlElement
    private String email;
    @XmlElement
    private String uri;


    public Author(String name, String email, String uri) {
        super();
        this.name = name;
        this.email = email;
        this.uri = uri;
    }


    public Author() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


}