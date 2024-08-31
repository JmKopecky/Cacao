package dev.prognitio.cacao;

import android.content.Context;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MiscellaneousFactsCurator {

    public static final HashMap<String, String> factOption = new HashMap<>();

    static {
        factOption.put("Vocabulary", "http://numbersapi.com/random/math");
        factOption.put("Math Facts", "http://numbersapi.com/random/math");
    }

    public static HashMap<String, String> curateFeedTile(Context context) throws IOException {
        HashMap<String, String> map = new HashMap<>();

        int optionSpaceSize = factOption.size();

        Random random = new Random();

        Map.Entry<String, String> target = null;

        int targetIndex = (int) (random.nextDouble() * optionSpaceSize);

        int index = 0;
        for (Map.Entry<String, String> entry : factOption.entrySet()) {
            if (index == targetIndex) {
                target = entry;
                break;
            }
            index++;
        }

        map.put("title", target.getKey());

        String body = "";
        body += retrieveData(target.getValue(), target.getKey());
        map.put("body", body);

        return map;
    }


    public static String retrieveData(String link, String target) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        return result.toString();
    }
}
