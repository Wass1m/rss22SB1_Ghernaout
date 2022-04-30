package fr.univrouen.rss22.model;

//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
//
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.*;

//import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "image")
@XmlAccessorType(XmlAccessType.FIELD)
public class Image {

    @JacksonXmlProperty(isAttribute = true)
        @XmlAttribute(name = "type", required = true)
    private String type;

    @JacksonXmlProperty(isAttribute = true)
        @XmlAttribute(name = "href", required = true)
    private String href;

    @JacksonXmlProperty(isAttribute = true)
        @XmlAttribute(name = "alt", required = true)
    private String alt;

    @JacksonXmlProperty(isAttribute = true)
        @XmlAttribute(name = "length", required = false)
    private String length;


    public Image(String type, String href, String alt, String length) {
        this.alt = alt;
        this.type= type;
        this.href= "";
        this.length= length;
    }

    public Image() {

    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}
