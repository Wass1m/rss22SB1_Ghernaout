package fr.univrouen.rss22.exceptions;

public class FeedNotValidException extends Exception {

    public FeedNotValidException(String message) {
        super(message);
    }

    public FeedNotValidException(){
        super("Erreur de validation XSD");
    }
}


