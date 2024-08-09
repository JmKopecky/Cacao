package dev.prognitio.cacao;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Notes {
    private String title;
    private String content;
    private int weight;

    public Notes(String title, String content, int weight) {
        this.title = title;
        this.content = content;
        this.weight = weight;
    }

    public String toString() {
        String result;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        result = gson.toJson(this);
        return result;
    }

    public static Notes fromString(String str) {
        Notes output;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        output = gson.fromJson(str, Notes.class);
        return output;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
