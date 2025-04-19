package dev.engineeringmadness.hephaestus.plugins;

public class PluginExecutionException extends RuntimeException {

    public PluginExecutionException(String message) {super(message);}

    public PluginExecutionException(Throwable ex) {super(ex);}

    public PluginExecutionException(String message, Throwable ex) {super(message, ex);}
}
