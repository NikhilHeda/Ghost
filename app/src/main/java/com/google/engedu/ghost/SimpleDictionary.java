package com.google.engedu.ghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;
    private Random r = new Random();

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
                words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {

        int l = 0;
        int r = words.size() - 1;
        int m;

        while (l < r) {
            m = (l + r) / 2;
            if (words.get(m).startsWith(prefix))
                return words.get(m).substring(prefix.length());
            else if (words.get(m).compareTo(prefix) > 0)
                r = m - 1;
            else
                l = m + 1;
        }

        return "";
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        return null;
    }

}
