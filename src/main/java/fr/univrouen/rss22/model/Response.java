package fr.univrouen.rss22.model;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class Response {

    @XmlElement
    private String id;
    @XmlElement
    private String status;
    @XmlElement
    private String description;

    private List<String> addedGuids;

    private List<String> refusedGuids;

    public Response() {
    }

    public Response(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public Response(String id, String status, String description) {
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public Response(String id, String status, String description, List<String> addedGuids, List<String> refusedGuids ) {
        this.description = description;
        this.id = id;
        this.status = status;
        this.addedGuids = addedGuids;
        this.refusedGuids = refusedGuids;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAddedGuids() {
        return addedGuids;
    }

    public void setAddedGuids(List<String> addedGuids) {
        this.addedGuids = addedGuids;
    }

    public List<String> getRefusedGuids() {
        return refusedGuids;
    }

    public void setRefusedGuids(List<String> refusedGuids) {
        this.refusedGuids = refusedGuids;
    }
}
