package fr.univrouen.rss22.model;


import jakarta.xml.bind.annotation.*;


@XmlType(name = "ritem", namespace = "http://univrouen.fr/rss22")
@XmlEnum
@XmlAccessorType(XmlAccessType.FIELD)
public class ResumeItem {

    @XmlAttribute(name = "guid", required = true)
    private String guid;
    @XmlElement
    private String published;
    @XmlElement
    private String title;

    public ResumeItem() {
    }

    public ResumeItem(String guid, String published, String title) {
        this.guid = guid;
        this.published = published;
        this.title = title;
    }


    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
