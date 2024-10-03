package dev.prognitio.cacao;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MiscellaneousFactsCurator {

    public static final HashMap<String, String> factOption = new HashMap<>();

    public static void populateFactOption() {
        if (!factOption.containsKey("Vocabulary")) {
            factOption.put("Vocabulary", "https://random-word-api.herokuapp.com/word?lang=en" + "|" + "https://api.dictionaryapi.dev/api/v2/entries/en/");
        }
        if (!factOption.containsKey("Dog Facts")) {
            factOption.put("Dog Facts", "https://dogapi.dog/api/v2/facts?limit=1");
        }
        if (!factOption.containsKey("Math Facts")) {
            factOption.put("Math Facts", "http://numbersapi.com/random/math");
        }
        if (!factOption.containsKey("Programming Jokes")) {
            factOption.put("Programming Jokes", "https://v2.jokeapi.dev/joke/Programming?blacklistFlags=nsfw,religious,political,racist,sexist,explicit&format=txt");
        }
        populateHardCodedFacts();
    }

    public static HashMap<String, String> curateFeedTile(Context context, SharedPreferences sharedPref) throws IOException {
        populateFactOption();

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
        System.out.println("VALUE: " + value + " KEY: " + key);
        if (key.contains("_")) {
            body += parseHardCodedString(value);
        } else {
            body += retrieveData(value, key);
        }
        map.put("body", body);

        return map;
    }


    public static String parseHardCodedString(String value) {
        ArrayList<String> splitStrings = new ArrayList<>(Arrays.asList(value.split(";")));
        splitStrings.replaceAll(String::trim);
        System.out.println(splitStrings);
        System.out.println((int) (Math.random() * splitStrings.size()));
        return splitStrings.get((int) (Math.random() * splitStrings.size()));
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

        if (target.equals("Dog Facts")) {
            boolean hasSucceeded = false;
            String result = "";
            while (!hasSucceeded) {
                result = getDogFacts(link);
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

    public static String getDogFacts(String linkData) throws IOException {
        StringBuilder result = new StringBuilder();
        URL dogUrl = new URL(linkData);
        HttpURLConnection connectionDog = (HttpURLConnection) dogUrl.openConnection();
        connectionDog.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connectionDog.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        } catch (FileNotFoundException e) {
            return "filenotfound";
        }

        ArrayList<String> dogs = new ArrayList<>();
        boolean hasSkippedFirst = false;
        for (String defString : result.toString().split("body\":\"")) {
            if (!hasSkippedFirst) {
                hasSkippedFirst = true;
                continue;
            }
            dogs.add(defString.split("\"\\}")[0]);
        }
        String output = "";
        output += dogs.get(0);
        return output;
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



    public static void populateHardCodedFacts() {
        if (!factOption.containsKey("hardcoded_test")) {
            factOption.put("hardcoded_test", "hi;my;name;is;bob");
        }
    }


}
