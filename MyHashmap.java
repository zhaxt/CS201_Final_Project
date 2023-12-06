import java.util.*;

class MyHashmap<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private HashEntry<K, V>[] data;
    private int size;

    MyHashmap() {
        data = new HashEntry[INITIAL_CAPACITY];
        size = 0;
    }

    private static class HashEntry<K, V> {
        K key;
        V value;

        HashEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % data.length;
    }

    private int quadraticProbe(int hash, int i) {
        return (hash + i * i) % data.length;
    }

    private void resize() {
        data = Arrays.copyOf(data, data.length * 2);
    }

    public void put(K key, V value) {
        if (size >= data.length / 2) {
            resize();
        }

        int hash = hash(key);
        int index = hash;

        for (int i = 0; data[index] != null; i++) {
            index = quadraticProbe(hash, i);
            if (data[index] != null && data[index].key.equals(key)) {
                data[index].value = value;
                return;
            }
        }

        data[index] = new HashEntry<>(key, value);
        size++;
    }

    public V get(K key) {
        int hash = hash(key);
        int index = hash;

        for (int i = 0; data[index] != null; i++) {
            if (key.equals(data[index].key)) {
                return data[index].value;
            }
            index = quadraticProbe(hash, i);
        }

        return null; // if Key is not found
    }

    public void search(K key) {
        int hash = hash(key);
        int index = hash;

        for (int i = 0; data[index] != null; i++) {
            if (key.equals(data[index].key)) {
                System.out.println("Matching Record: " + data[index].value);
                return;
            }
            index = quadraticProbe(hash, i);
        }

        System.out.println("Record not found");
    }

    public void delete(K key) {
        int hash = hash(key);
        int index = hash;

        for (int i = 0; data[index] != null; i++) {
            if (key.equals(data[index].key)) {
                data[index] = null;
                size--;
                return;
            }
            index = quadraticProbe(hash, i);
        }
    }

    public int Size() {
        return size;
    }

    public void getAllValue(K[] names, V[] counts) {
        for (int i = 0; i < size; i++) {
            if (data[i] == null)
                continue;
            names[i] = data[i].key;
            counts[i] = data[i].value;
        }
    }
}