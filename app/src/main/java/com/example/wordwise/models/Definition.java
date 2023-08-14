package com.example.wordwise.models;

public class Definition {
    private String definition;
    private String example;

    public Definition(){}
    public Definition(String definition, String example) {
        this.definition = definition;
        this.example = example;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
