package me.hexsook.dcc.utils;

public class InterruptStartupSignal extends Error {

    private final String message;

    public InterruptStartupSignal(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
