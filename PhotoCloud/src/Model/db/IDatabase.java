package Model.db;

import java.util.List;
import java.util.Map;

public interface IDatabase<K extends Comparable<K>,V extends List<?>> {
    /**
     * get the values of k
     * @param k
     * @return
     */
    List<?> get(K k);

    /**
     *
     * @param kList
     * @return getAll/kList
     */
    Iterable<Map.Entry<K,V>> getExcept(List<K> kList);

    /**
     * @param k key to remove
     * @return if removed old value if not null
     */
    V remove(K k);

    /**
     * @return all entries.
     */
    Iterable<Map.Entry<K,V>> getAll();

    /**
     * @return
     */
    Iterable<K> getKeys();

    /**
     * @return all values
     */
    Iterable<V> getValues();

    /**
     * @return size == 0
     */
    boolean isEmpty();

    /**
     * @return size
     */
    int size();

    /**
     * @param k
     * @return the size of the List associated with key k.
     */
    int sizeOf(K k);




}
