package com.example.wordwise.models;

import java.util.ArrayList;

public class Meaning {
    private String partOfSpeech;

    private ArrayList<Definition> definitions;

    private ArrayList<String> synonyms;

    private ArrayList<String> antonyms;

    public Meaning() {}

    public Meaning(String partOfSpeech, ArrayList<Definition> definitions, ArrayList<String> synonyms, ArrayList<String> antonyms) {
        this.partOfSpeech = partOfSpeech;
        this.definitions = definitions;
        this.synonyms = synonyms;
        this.antonyms = antonyms;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public ArrayList<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(ArrayList<Definition> definitions) {
        this.definitions = definitions;
    }

    public ArrayList<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(ArrayList<String> synonyms) {
        this.synonyms = synonyms;
    }

    public ArrayList<String> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(ArrayList<String> antonyms) {
        this.antonyms = antonyms;
    }
}
