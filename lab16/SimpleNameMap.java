import java.util.ArrayList;
import java.util.LinkedList;

public class SimpleNameMap {

    /* TODO: Instance variables here */
    public LinkedList<Entry>[] map;
    public int size;

    public SimpleNameMap() {
        map = new LinkedList[10];
        size = 0; /* might need to change */
    }

    /* Returns the number of items contained in this map. */
    public int size() {
        return size;
    }

    /* Returns true if the map contains the KEY. */
    public boolean containsKey(String key) {
        int index = Math.floorMod(hash(key), map.length);
//        for (int i = 0; i < map[index].size(); i++) {
//        }
        return map[index].contains(key);
    }

    /* Returns the value for the specified KEY. If KEY is not found, return
       null. */
    public String get(String key) {
        if (containsKey(key)) {
            int index = Math.floorMod(hash(key), map.length);
            LinkedList<Entry> letter = map[index];
            if (letter != null) {
                for (int i = 0; i < letter.size(); i++) {
                    if (letter.get(i).key.equals(key)) {
                        return letter.get(i).value;
                    }
                }
            }
            return null;
        }
        else {
            return null;
        }
    }

    public void resize() { /* copy elements?? */
        int newLength = map.length * 2;
        LinkedList<Entry>[] newMap = (LinkedList<Entry>[]) new LinkedList[newLength];
        for (int i = 0; i < map.length; i ++) {
            LinkedList<Entry> letter = map[i];
            for (int j = 0; j < letter.size(); j ++) {
                int index = Math.floorMod(hash(letter.get(j).key), newLength);
                newMap[index].add(letter.get(j));
            }
        }
    }

    /* Puts a (KEY, VALUE) pair into this map. If the KEY already exists in the
       SimpleNameMap, replace the current corresponding value with VALUE. */
    public void put(String key, String value) {
        int index = Math.floorMod(hash(key), map.length);
        if (containsKey(key)) {
            LinkedList<Entry> letter = map[index];
            for (int i = 0; i < letter.size(); i++) {
                if (letter.get(i).key.equals(key)) {
                    letter.get(i).value = value;
                }
            }
        } else {
            double loadFactor = size / (double) map.length;
            if (loadFactor >= 0.75) {
                resize();
            } else {
                map[index].addLast(new Entry(key, value));
                size ++;
            }
        }
    }

    /* Removes a single entry, KEY, from this table and return the VALUE if
       successful or NULL otherwise. */
    public String remove(String key) {
        int index = Math.floorMod(hash(key), map.length);
        if (containsKey(key)) {
            LinkedList<Entry> letter = map[index];
            for (int i = 0; i < letter.size(); i ++) {
                if (letter.get(i).key.equals(key)) {
                    String returnedV = letter.get(i).value;
                    letter.remove(letter.get(i));
                    return returnedV;
                }
            }
        }
        return null;
    }

    public int hash(String key) {
        return (int) (key.charAt(0) - 'A');
    }

    private static class Entry {

        private String key;
        private String value;

        Entry(String key, String value) {
            this.key = key;
            this.value = value;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry
                    && key.equals(((Entry) other).key)
                    && value.equals(((Entry) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
}