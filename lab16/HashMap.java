import java.util.Iterator;
import java.util.LinkedList;

public class HashMap<K, V> implements Map61BL<K, V> {

    /* Instance variables here? */
    //String[] keyArray;
    LinkedList<Entry<K,V>>[] keyArray; //https://augmentedcode.io/2018/08/12/singly-linked-list-with-generics-in-swift/#:~:text=Defining%20a%20generic%20linked%20list,list%2C%20next%20property%20is%20updated.
    int size;
    int capacity;
    double load = 0.75;

    /* Constructor */
    HashMap() {
        //keyArray = new T[26];
        keyArray = new LinkedList[16];
        size = 0;
        capacity = 16;
    }

    HashMap(int initialCapacity) {
        //keyArray = new T[26];
        keyArray = new LinkedList[initialCapacity];
        size = 0;
        capacity = initialCapacity;
    }

    HashMap(int initialCapacity, float loadFactor) {
        //keyArray = new T[26];
        keyArray = new LinkedList[initialCapacity];
        size = 0;
        capacity = initialCapacity;
        load = loadFactor;
    }

    /* Returns true if the given KEY is a valid name that starts with A - Z. */
//    private static boolean isValidName(K key) {
//        return 'A' <= key.charAt(0) && key.charAt(0) <= 'Z';
//    }


    int hash(K key) {
        return Math.floorMod(key.hashCode(), keyArray.length);
        //for(int i = 0; i<key.length(); i++){
        //    c = c + (10*i)*(key.charAt(i));
        //}
    }
    @Override
    public void clear() {
        for (int i =0; i<capacity;i++){
            keyArray[i]=null;
        }
        size = 0;
    }

    @Override
    //WARNING WARNING
    /* Returns true if the map contains the KEY. */
    public boolean containsKey(K key) {
        int index = key.hashCode() % capacity;
        if (keyArray[index] == null) {
            return false;
        }
        for (Entry e : keyArray[index]) {
            if (e.key.equals(key)) {
                return true;
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

    /* Returns the value for the specified KEY. If KEY is not found, return
       null. */
    //WARNING WARNING
    public V get(K key) {
        int index = (key.hashCode() & 0x7FFFFFFF) % capacity;
        for (Entry e : keyArray[index]) {
            if (e.key.equals(key)) {
                return (V) e.value;
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

    /* Puts a (KEY, VALUE) pair into this map. If the KEY already exists in the
       SimpleNameMap, replace the current corresponding value with VALUE. */
    public void put(K key, V value) {
        //if (isValidName(key)) {
        LinkedList<Entry<K, V>> t = keyArray[hash(key)];
        int code = hash(key);

        if (containsKey(key)) {
            for (int i = 0; i < t.size(); i++) {
                if (t.get(i).key.equals(key)) {
                    t.get(i).value = value;
                }
            }
        } else {
            Entry n = new Entry(key, value);
            t.add(n);
            size++;
        }

        if (((double) size / ((double) keyArray.length)) > load) {
            resize();
        }


        //}
    }

    void resize() {
        int l = keyArray.length;
        LinkedList[] newKey = new LinkedList[keyArray.length * 2];
        System.arraycopy(keyArray, 0, newKey, 0, keyArray.length);
        keyArray = newKey;
        capacity = capacity * 2;
    }

    /* Removes a single entry, KEY, from this table and return the VALUE if
       successful or NULL otherwise. */
    public V remove(K key) {
        //if (isValidName(key)) {
        if (!containsKey(key)) {
            return null;
        }
        //String rem = keyArray[hash(key)];
        //keyArray[hash(key)] = null;
        //return rem;
        V rem = null;
        LinkedList<Entry<K, V>> t = keyArray[hash(key)];
        for (int i = 0; i < t.size(); i++) {
            if (t.get(i).key.equals(key)) {
                rem = (V) t.get(i).value;
                t.remove(t.get(i));
                size--;
            }
        }
        return rem;

        //}
        //return null;
    }

    public boolean remove(K key, V value) {
        //if (isValidName(key)) {
        if (!containsKey(key)) {
            return false;
        }
        //String rem = keyArray[hash(key)];
        //keyArray[hash(key)] = null;
        //return rem;
        LinkedList<Entry<K, V>> t = keyArray[hash(key)];
        Entry<K, V> n = new Entry<>(key, value);
        for (int i = 0; i < t.size(); i++) {
            if (t.get(i).equals(n)) {
                t.remove(t.get(i));
                size--;
                return true;
            }
        }
        return false;

        //}
        //return null;
    }

    public int size() {
        //double x = size;
        return size;
    }

    int capacity() {
        return capacity;
    }

    public Iterator<K> iterator() {
        return new HashIterator();
    }


    private class HashIterator implements Iterator<K> {

        private int kCount = 0;
        private int vCount = 0;

        public K next() {
            if (hasNext()) {
                if (keyArray[kCount] != null) {
                    if (vCount < keyArray[kCount].size()) {
                        vCount++;
                        Entry<K, V> n = (Entry) keyArray[kCount].get(vCount - 1);
                        return n.key;
                    } else {
                        vCount = 0;
                        kCount++;
                        return next();
                    }
                }

            }
            return null;
        }

        public boolean hasNext() {
            return kCount < size();
        }
    }


    static class Entry<K, V> {
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

    public Iterator<K> iterator() throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

//    private class HashMapIterator<K> implements Iterator<K> {
//
//        int m;
//        int l;
//
//        public HashMapIterator() {
//            m = 0;
//            l = 0;
//        }
//
//        public K next() {
//            LinkedList<Entry> letter = map[m];
//            K nextKey = (K) letter.get(l).key;
//            l ++;
//            if (l >= map[m].size()) {
//                l = 0;
//                m ++;
//            }
//            return nextKey;
//        }
//
//        public boolean hasNext() {
//            return (size != 0 && m < map.length);
//        }
//    }

    private class Entry<K, V> {

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