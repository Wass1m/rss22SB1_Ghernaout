package fr.univrouen.rss22.model;

//import jakarta.xml.bind.annotation.*;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "content")
@XmlAccessorType(XmlAccessType.FIELD)
public class Content {
    @JacksonXmlProperty(isAttribute = true)
    @XmlAttribute(name = "type", required = true)
    private String type;
    @JacksonXmlProperty(isAttribute = true)
    @XmlAttribute(name = "href", required = true)
    private String href;

    @JacksonXmlText
    @XmlValue
    private String value;



    public Content(String type, String href, String value) {
        this.type= "text";
        this.href= href;
        this.value = value;
    }

    public Content() {

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


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
