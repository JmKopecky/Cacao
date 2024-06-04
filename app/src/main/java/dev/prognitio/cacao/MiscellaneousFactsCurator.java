package dev.prognitio.cacao;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MiscellaneousFactsCurator {

    public static final ArrayList<String> factOption = new ArrayList<>();

    static {
        factOption.add("Random Definitions and Vocab");
    }

    public static HashMap<String, String> curateFeedTile(Context context) {
        HashMap<String, String> map = new HashMap<>();

        int optionSpaceSize = factOption.size();

        Random random = new Random();

        int targetOption = (int) (random.nextDouble() * optionSpaceSize);

        map.put("title", factOption.get(targetOption));

        return map;
    }
}
