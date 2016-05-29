package com.google.engedu.ghost;

import java.util.HashMap;

class HashTrie {
    private HashMap<Character, HashMap> root;

    public HashTrie() {
        root = new HashMap<Character, HashMap>();
    }

    public HashTrie(String[] arr) {
        root = new HashMap<Character, HashMap>();
        for (String s : arr)
            add(s);
    }

    public void add(String str) {
        HashMap<Character, HashMap> node = root;
        for (int i = 0; i < str.length(); i++) {
            if (node.containsKey(str.charAt(i)))
                node = node.get(str.charAt(i));
            else {
                node.put(str.charAt(i), new HashMap<Character, HashMap>());
                node = node.get(str.charAt(i));
            }
        }
        node.put('\0', new HashMap<Character, HashMap>(0));
    }

    public boolean isWord(String str) {
        HashMap<Character, HashMap> currentNode = root;
        for (int i = 0; i < str.length(); i++) {
            if (currentNode.containsKey(str.charAt(i)))
                currentNode = currentNode.get(str.charAt(i));
            else
                return false;
        }
        return currentNode.containsKey('\0') ? true : false;
    }

    String getAnyWordStartingWith(String prefix) {

        HashMap<Character, HashMap> currentNode = root;
        boolean flag = true;

        for (int i = 0; i < prefix.length(); i++) {
            if (currentNode.containsKey(prefix.charAt(i))) {
                currentNode = currentNode.get(prefix.charAt(i));
                flag = true;
            } else {
                flag = false;
            }
        }

        if (flag) {
            String result = prefix;

            for (Character c : currentNode.keySet()) {
                if (c != '\0') {
                    result += Character.toString(c);
                    result += getAnyWordStartingWith(result);
                    currentNode.get(c);
                }
                return result.substring(prefix.length());
            }
        }

        return "";
    }

    public String getGoodWordStartingWith(String prefix) {
        return null;
    }

}