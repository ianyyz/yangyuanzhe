import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MyTrieSet implements TrieSet61B{
    private Node root;

    private class Node{
        public char character;
        public HashMap<Character,Node> map;
        public boolean isKey;

        public Node(){
            map = new HashMap<>();
        }

        public Node(char c, boolean endChar){
            this.character = c;
            this.isKey = endChar;
            map = new HashMap<>();
        }

    }

    public MyTrieSet(){
        root = new Node();
    }

    /** Clears all items out of Trie */
    @Override
    public void clear(){
        root = new Node();
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    @Override
    public boolean contains(String key){
        return !keysWithPrefix(key).isEmpty();
    };

    /** Inserts string KEY into Trie */
    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }

    /** Returns a list of all words that start with PREFIX */
    @Override
    public List<String> keysWithPrefix(String prefix){
        List<String> keySet = new ArrayList<>();
        if (prefix == null || prefix.length() < 1){
            return keySet;
        }
        Node curr = root;
        String result = prefix;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (!curr.map.containsKey(c)){
                return keySet;
            }
            curr = curr.map.get(c);
        }
        if (curr.isKey){
            keySet.add(result);
        }
        keySet.addAll(recursive(result,curr.map));
        return keySet;
    }

    private ArrayList<String> recursive(String result, HashMap<Character,Node> map){
        ArrayList<String> keySet = new ArrayList<>();
        if(map.isEmpty()){
            return keySet;
        }
        for(char key: map.keySet()){
            String newResult = result + key;
            if (map.get(key).isKey){
                keySet.add(newResult);
            }
            keySet.addAll(recursive(newResult,map.get(key).map));
        }
        return keySet;
    }

    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public String longestPrefixOf(String key){
        throw new UnsupportedOperationException();
    };


    public static void main(String[] args){
        String[] saStrings = new String[]{"same", "sam", "sad", "sap"};
        String[] otherStrings = new String[]{"a", "awls", "hello"};

        MyTrieSet t = new MyTrieSet();
        for (String s: saStrings) {
            t.add(s);
        }
        for (String s: otherStrings) {
            t.add(s);
        }

        List<String> keys = t.keysWithPrefix("sa");
        for (String a:keys){
            System.out.println(a);
        }
    }
}
