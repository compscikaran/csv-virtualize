package dev.engineeringmadness.hephaestus.plugins.validator;

public class QueryValidationException extends RuntimeException {

    public QueryValidationException(String message) {super(message);}

    public QueryValidationException(Throwable ex) {super(ex);}

    public QueryValidationException(String message, Throwable ex) {super(message, ex);}
}
