package main.com.ubb.postuniv.farmacy.Domain;

public class DuplicateException extends Exception{
    public DuplicateException() {
    }

    public DuplicateException(String message) {
        super(message);
    }
}
