package dev.prognitio.cacao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Notes {
private String title;
private String content;

public Notes(String title, String content) {
    this.title = title;
    this.content = content;
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

