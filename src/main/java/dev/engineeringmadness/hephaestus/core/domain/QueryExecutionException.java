package dev.engineeringmadness.hephaestus.core.domain;

public class QueryExecutionException extends RuntimeException {

    public QueryExecutionException(String message) {super(message);}

    public QueryExecutionException(Throwable ex) {super(ex);}

    public QueryExecutionException(String message, Throwable ex) {super(message, ex);}
}