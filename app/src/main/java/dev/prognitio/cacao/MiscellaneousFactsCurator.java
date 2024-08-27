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
import java.util.Random;

public class MiscellaneousFactsCurator {

    public static final ArrayList<String> factOption = new ArrayList<>();

    static {
        factOption.add("Vocabulary");
        factOption.add("Math Facts");
    }

    public static HashMap<String, String> curateFeedTile(Context context) {
        HashMap<String, String> map = new HashMap<>();

        int optionSpaceSize = factOption.size();

        Random random = new Random();

        int targetOption = (int) (random.nextDouble() * optionSpaceSize);

        map.put("title", factOption.get(targetOption));

        String body = "";
        try {
            body += retrieveData("http://numbersapi.com/random/math");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(body);

        return map;
    }


    public static String retrieveData(String link) throws IOException {
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
