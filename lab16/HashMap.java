import java.util.Iterator;
import java.util.LinkedList;

public class HashMap<K, V> implements Map61BL<K, V> {

    /* TODO: Instance variables here */
    LinkedList[] map;
    int size;
    double loadFactor = 0.75;
    int capacity;

    public HashMap() {
        map = new LinkedList[16];
        for (int i = 0; i < map.length; i ++) {
            map[i] = new LinkedList<Entry>();
        }
        size = 0; /* might need to change */
        capacity = 16;
        loadFactor = 0.75;
    }

    public HashMap (int initialCapacity) {
        map = new LinkedList[initialCapacity];
        for (int i = 0; i < map.length; i ++) {
            map[i] = new LinkedList<Entry>();
        }
        size = 0;
        this.capacity = initialCapacity;
    }

    public HashMap (int initialCapacity, double loadFactor) {
        size = 0;
        map = new LinkedList[initialCapacity];
        for (int i = 0; i < map.length; i ++) {
            map[i] = new LinkedList<Entry>();
        }
        this.loadFactor = loadFactor;
        capacity = initialCapacity;
    }

    @Override
    /* Returns the number of items contained in this map. */
    public int size() {
        return size;
    }

    @Override
    /* Returns true if the map contains the KEY. */
    public boolean containsKey(K key) {
//        int index = Math.floorMod(hash(key), map.length);
//        for (int i = 0; i < map[index].size(); i++) {
//        }
//        return map[hash(key)].contains(key);
        LinkedList<Entry> letter = map[hash(key)];
        if (letter != null) {
            for (int i = 0; i < letter.size(); i++) {
                if (letter.get(i).key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    /* Returns the value for the specified KEY. If KEY is not found, return
       null. */
    public V get(K key) {
        if (containsKey(key)) {
//            int index = Math.floorMod(hash(key), map.length);
            LinkedList<Entry> letter = map[hash(key)];
            for (int i = 0; i < letter.size(); i++) {
                if (letter.get(i).key.equals(key)) {
                    return (V) letter.get(i).value;
                }
            }
        }
        return null;
    }

    public void resize() { /* copy elements?? */
        int oldLength = map.length;
        LinkedList[] newMap = new LinkedList[oldLength * 2];
        System.arraycopy(map, 0, newMap, 0, map.length);
        map = newMap;
        for (int i = oldLength; i < map.length; i ++) {
            map[i] = new LinkedList<Entry>();
        }
        capacity = capacity * 2;
    }

    @Override
    /* Puts a (KEY, VALUE) pair into this map. If the KEY already exists in the
       SimpleNameMap, replace the current corresponding value with VALUE. */
    public void put(K key, V value) {

        if (containsKey(key)) {
            LinkedList<Entry> letter = map[hash(key)];
            for (int i = 0; i < letter.size(); i++) {
                if (letter.get(i).key.equals(key)) {
                    letter.get(i).value = value;
                }
            }
        } else {
            map[hash(key)].add(new Entry(key, value)); /** possible **/
            size++;
        }

        double thisLoadFactor = ((double) size) / ((double) map.length);
        if (thisLoadFactor > loadFactor) {
            resize();
        }
    }


    @Override
    /* Removes a single entry, KEY, from this table and return the VALUE if
       successful or NULL otherwise. */
    public V remove(K key) {
        if (containsKey(key)) {
            V returnedValue = null;
            LinkedList<Entry> letter = map[hash(key)];
            for (int i = 0; i < letter.size(); i ++) {
                if (letter.get(i).key.equals(key)) {
                    returnedValue = (V) letter.get(i).value;
                    letter.remove(letter.get(i));
                    size --;
                }
            }
            return returnedValue;
        }
        return null;
    }

    public int hash(K key) {
        return Math.floorMod(key.hashCode(), map.length);
    }

    @Override
    public void clear() {
        map = new LinkedList[capacity];
        for (int i = 0; i < map.length; i ++) {
            map[i] = new LinkedList<Entry>();
        }
        size = 0;
    }

    public int capacity() {
        return capacity;
    }

    @Override
    public boolean remove(K key, V value) {
        if (containsKey(key)) {
            LinkedList<Entry> letter = map[hash(key)];
            for (int i = 0; i < letter.size(); i ++) {
                if (letter.get(i).key.equals(key)) {
                    letter.remove(letter.get(i));
                    return true;
                }
            }
        }
        return false;
    }

    public Iterator<K> iterator() {
        return new HashIterator();
    }

    private class HashIterator implements Iterator<K> {

        private int k = 0;
        private int v = 0;

        public K next() {
            if (hasNext()) {
                if (map[k] != null) {
                    if (v < map[k].size()) {
                        v ++;
                        Entry<K, V> uh = (Entry) map[k].get(v-1);
                        return uh.key;
                    } else {
                        v = 0;
                        k ++;
                        return next();
                    }
                }
            }
            return null;
        }

        public boolean hasNext() {
            return k < size();
        }
    }

    private static class Entry<K, V> {

        private K key;
        private V value;

        Entry(K key, V value) {
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