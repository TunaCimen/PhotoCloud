package Model.db;

import java.util.*;

public abstract class Database<K extends Comparable<K>,V extends List<?>> implements IDatabase<K,V> {

    Map<K,V> map = new TreeMap<>();

    /**
     * @param k
     * @return value of k
     */
    @Override
    public List<?> get(K k) {
        return map.get(k);
    }

    /**
     * return the getAll/kList
     * @param kList
     * @return
     */
    @Override
    public ArrayList<Map.Entry<K,V>> getExcept(List<K> kList) {
        ArrayList<Map.Entry<K,V>> result = new ArrayList<>();
        for(Map.Entry<K,V> entry:map.entrySet()){
            if(kList.contains(entry.getKey()))continue;
            result.add(entry);
        }
        return result;
    }

    @Override
    public V remove(K k) {
        return map.remove(k);
    }

    public V put(K k, V v){
        return map.put(k,v);
    }

    @Override
    public List<Map.Entry<K, V>> getAll() {
        return map.entrySet().stream().toList();
    }

    @Override
    public Iterable<K> getKeys() {
        return map.keySet();
    }

    @Override
    public Iterable<V> getValues() {
        return map.values();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public int sizeOf(K k) throws IllegalArgumentException{
        if(map.get(k) == null)throw new IllegalArgumentException("Key provided is not in the database!");
        return map.get(k).size();
    }

}
