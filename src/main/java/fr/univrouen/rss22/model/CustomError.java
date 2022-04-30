package fr.univrouen.rss22.model;

public class CustomError {
    private String guid;
    private String status;

    public CustomError(String guid, String status) {
        this.guid = guid;
        this.status = status;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
