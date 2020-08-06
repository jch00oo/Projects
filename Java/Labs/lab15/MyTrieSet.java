import java.util.*;

public class MyTrieSet implements TrieSet61BL {

    private Node root;

    public MyTrieSet() {
        root = new Node('0', false);
    }

    private static class Node {
        private Map<Character, Node> map;
        private boolean isKey;
        private char currChar;

        private Node(char c, boolean isItAKey) {
            isKey = isItAKey;
            map = new HashMap<Character,Node>();
            currChar = c;
        }
    }

    @Override
    public void clear() {
        root = new Node('0', false);
    }

    @Override
    public boolean contains(String key) {
        if (key == null) {
            return false;
        }
        Node curr = root;
        for (int i = 0; i < key.length(); i += 1) {
            char c = key.charAt(i);
            if (! curr.map.containsKey(c)) {
                return false;
            }
            curr = curr.map.get(c);
        }
        return curr.isKey;
    }

    private void keysWithPrefixHelper(List<String> results, String word, Node curr) {
        if (curr.isKey) {
            results.add(word + curr.currChar);
        }
        for (Node c: curr.map.values()) {
            if (c != null) {
                keysWithPrefixHelper(results, word + curr.currChar, c);
            }
        }
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> results = new ArrayList<>();
        Node curr = root;
        for (int i = 0; i < prefix.length(); i += 1) {
            char c = prefix.charAt(i);
            if (! curr.map.containsKey(c)) {
                return null;
            }
            curr = curr.map.get(c);
        }
        for (Node c: curr.map.values()) {
            keysWithPrefixHelper(results, prefix, c);
        }
        return results;
    }

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
        curr.isKey = true; }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }
}
