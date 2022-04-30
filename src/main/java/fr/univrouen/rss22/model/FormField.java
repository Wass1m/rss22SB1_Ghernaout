package fr.univrouen.rss22.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class FormField {


    private String flux;
    private MultipartFile file;


    public String getFlux() {
        return flux;
    }

    public void setFlux(String flux) {
        this.flux = flux;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
