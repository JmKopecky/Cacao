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

    public static Course fromString(String str) {
        Course output;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        output = gson.fromJson(str, Course.class);
        return output;
    }
}
