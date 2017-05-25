package com.wfy.server.utils.mymap;

/**
 * Created by weifeiyu on 2017/5/25.
 */
public class Entry<K,V> {

    final K key;
    V value;
    Entry<K,V> next;//下一个结点

    //构造函数
    public Entry(K k, V v, Entry<K,V> n) {
        key = k;
        value = v;
        next = n;
    }

    public final K getKey() {
        return key;
    }

    public final V getValue() {
        return value;
    }

    public final V setValue(V newValue) {
        V oldValue = value;
        value = newValue;
        return oldValue;
    }

    public final boolean equals(Object o) {
        if (!(o instanceof Entry))
            return false;
        Entry e = (Entry)o;
        Object k1 = getKey();
        Object k2 = e.getKey();
        if (k1 == k2 || (k1 != null && k1.equals(k2))) {
            Object v1 = getValue();
            Object v2 = e.getValue();
            if (v1 == v2 || (v1 != null && v1.equals(v2)))
                return true;
        }
        return false;
    }

    public final int hashCode() {
        return (key==null   ? 0 : key.hashCode()) ^ (value==null ? 0 : value.hashCode());
    }

    public final String toString() {
        return getKey() + "=" + getValue();
    }
}
