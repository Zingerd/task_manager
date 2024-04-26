package com.example.task_manager.dto;

public enum Status {
    NEW("NEW"),
    PROCESSING("PROCESSING"),
    TESTING("TESTING"),
    COMPLEAT("COMPLEAT");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getStatus() {
        return name;
    }
}
