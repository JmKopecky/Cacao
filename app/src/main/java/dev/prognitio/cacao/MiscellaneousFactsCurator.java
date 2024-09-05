package dev.prognitio.cacao;

import static android.provider.Settings.System.getString;

import android.content.Context;
import android.content.SharedPreferences;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MiscellaneousFactsCurator {

    public static final HashMap<String, String> factOption = new HashMap<>();

    static {
        //Vocab: supplied link finds word, extra link finds dictionary entry for word.
        factOption.put("Vocabulary", "https://random-word-api.herokuapp.com/word?lang=en" + "|" + "https://api.dictionaryapi.dev/api/v2/entries/en/");
        factOption.put("Math Facts", "http://numbersapi.com/random/math");
    }

    public static HashMap<String, String> curateFeedTile(Context context, SharedPreferences sharedPref) throws IOException {
        HashMap<String, String> map = new HashMap<>();

        Random random = new Random();

        ArrayList<String> selected = new ArrayList<>();
        for (Map.Entry<String, String> entry : factOption.entrySet()) {
            if (sharedPref.getBoolean("preferences_" + entry.getKey(), false)) {
                selected.add(entry.getKey());
            }
        }

        int optionSpaceSize = selected.size();
        int targetIndex = (int) (random.nextDouble() * optionSpaceSize);
        Map.Entry<String, String> target = null;
        String key = "";
        String value = "";

        int index = 0;
        for (String option : selected) {
            if (index == targetIndex) {
                key = option;
                value = factOption.get(key);
                break;
            }
            index++;
        }

        map.put("title", key);

        String body = "";
        body += retrieveData(value, key);
        map.put("body", body);

        return map;
    }


    public static String retrieveData(String link, String target) throws IOException {

        if (target.equals("Vocabulary")) {
            boolean hasSucceeded = false;
            String result = "";
            while (!hasSucceeded) {
                result = getVocabWord(link);
                if (!result.equals("filenotfound")) {
                    hasSucceeded = true;
                }
            }
            return result;
        }

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


    public static String getVocabWord(String linkData) throws IOException {
        StringBuilder wordBuilder = new StringBuilder();
        URL url = new URL(linkData.split("\\|")[0]);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                wordBuilder.append(line);
            }
        }
        String word = wordBuilder.substring(2, wordBuilder.length() - 2);

        StringBuilder result = new StringBuilder();
        URL dictionaryUrl = new URL(linkData.split("\\|")[1] + word);
        HttpURLConnection connectionDictionary = (HttpURLConnection) dictionaryUrl.openConnection();
        connectionDictionary.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connectionDictionary.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        } catch (FileNotFoundException e) {
            return "filenotfound";
        }
        System.out.println(result);
        ArrayList<String> definitions = new ArrayList<>();
        boolean hasSkippedFirst = false;
        for (String defString : result.toString().split("definitions\":\\[\\{")) {
            if (!hasSkippedFirst) {
                hasSkippedFirst = true;
                continue;
            }
            definitions.add(defString.split("definition\":\"")[1].split("\",\"")[0]);
        }
        String output = "";
        output += "Word: " + word + "\n";
        for (String def : definitions) {
            output += def + "\n";
        }
        return output;
    }
}
