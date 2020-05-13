import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;


public class MyHashMap<K extends Comparable<K>,V> implements Map61B<K, V>{
    private int m;
    private double load_factor;
    private double empty_factor;
    private int size;
    private BSTMap<K,V>[] st;
    private double utility;
    public MyHashMap(){
        m  = 16;
        load_factor = 0.75;
        empty_factor = 0.25;
        st = new BSTMap[m];
        size = 0;
        for(int i = 0; i<m;i++){
            st[i] = new BSTMap<>();
        }
    }
    public MyHashMap(int InitialSize){
        m = InitialSize;
        load_factor = 0.75;
        empty_factor = 0.25;
        st = new BSTMap[m];
        size = 0;
        for(int i = 0; i<m;i++){
            st[i] = new BSTMap<>();
        }
    }
    public MyHashMap(int InitialSize,double loadFactor){
        m = InitialSize;
        load_factor = loadFactor;
        empty_factor = 0.25;
        st = new BSTMap[m];
        size = 0;
        for(int i = 0; i<m;i++){
            st[i] = new BSTMap<>();
        }
    }


    private void resize(int chains){
        MyHashMap<K,V> temp = new MyHashMap<>(chains);
        for (int i = 0; i < this.m; i++){
            if (this.st[i].keySet() == null){
                continue;
            }
            for(K key: this.st[i].keySet()){
                temp.put(key,this.st[i].get(key));
            }
        }
        this.m = temp.m;
        this.size = temp.size;
        this.st = temp.st;
        this.utility = temp.size/temp.m;

    }


    // hash method takes an input of a key and transform it into an integer which we can use as Array Index.
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    @Override
    public void clear(){
        m  = 16;
        load_factor = 0.75;
        empty_factor = 0.25;
        st = new BSTMap[m];
        for(int i = 0; i<m;i++){
            st[i] = new BSTMap<>();
        }
        size = 0;
    };

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key){
        return get(key) != null;
    };

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key){
        int index = hash(key);
        return st[index].get(key);
    };

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size(){
        return this.size;
    };

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value){
        if(key == null){
            throw new IllegalArgumentException();
        }
        utility = (double)size/(double)m;
        if (utility >= load_factor){
            this.resize(2*m);
        }
        int index = hash(key);
        if (!st[index].containsKey(key)){
            size += 1;
        }
        st[index].put(key,value);

    };

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet(){
        Set<K> keySet = new HashSet<>(size);
        for (int i = 0; i<m; i++){
            if(st[i].keySet() == null){
                continue;
            }
            keySet.addAll(st[i].keySet());
        }
        return keySet;
    };

    public class MyHashMapIter implements Iterator<K>{
        public ArrayList<K> arraylist;
        public int index;

        MyHashMapIter(Set<K> keySet){
            arraylist = new ArrayList<>(size);
            index = -1;
            for (K key: keySet){
                arraylist.add(key);
            }
        }
        public K next(){
            return this.arraylist.get(++this.index);

        }

        public boolean hasNext(){
            return this.index + 1 < this.arraylist.size();
        }

    }


    @Override
    public Iterator<K> iterator(){
        return new MyHashMapIter(this.keySet());
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key){
        if (key == null){
            throw new IllegalArgumentException();
        }
        utility = (double) size/ (double) m;
        if(utility <= empty_factor){
            resize(m/2);
        }
        int index = hash(key);
        if(!this.containsKey(key)){
            return null;
        }
        size -= 1;
        return st[index].remove(key);
    };


    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    public V remove(K key, V value){
         new UnsupportedOperationException();if (key == null){
            throw new IllegalArgumentException();
        }
        utility = (double) size/ (double) m;
        if(utility <= empty_factor){
            resize(m/2);
        }
        int index = hash(key);
        if(!this.containsKey(key)){
            return null;
        }
        if(!this.get(key).equals(value)){
            return null;
        }
        size -= 1;
        return st[index].remove(key,value);
    };
}
