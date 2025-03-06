import java.util.*;

/**
 * Реализация HashMap для выполнения домашнего задания.
 *
 * @param <K> key
 * @param <V> value
 * @author Valentin Baklanov
 * @version 1.0.0
 */

public class MyHashMap<K, V> {

    private static final double LOAD = 0.75;

    private MyNode<K, V>[] elements;
    private int capacity = 16;
    private int nEl;


    static class MyNode<K, V> {

        final int hash;
        final K key;
        V value;
        MyNode<K, V> next;

        MyNode(K key, V value) {
            this.hash = Math.abs(Objects.hash(key));
            this.key = key;
            this.value = value;
            this.next = null;
        }


        @Override
        public String toString() {
            MyNode<K, V> printNode = this;
            StringBuilder print = new StringBuilder();
            while (printNode != null) {
                print.append("{" + "key=").append(printNode.key).append(", value=").append(printNode.value).append('}');
                printNode = printNode.next;
            }
            return print.toString();
        }
    }

    MyHashMap() {
        elements = new MyNode[capacity];
    }

    MyHashMap(int capacity) {
        elements = new MyNode[capacity];
        this.capacity = capacity;
    }

    /**
     * Возвращает количество элементов в hashMap
     *
     * @return int
     */
    public int size() {
        return nEl;
    }

    /**
     * Функция позволяет положить пару ключ значение в hashMap
     *
     * @param key   значение ключа
     * @param value объект доступный по вышеуказанному ключу
     */
    public void put(K key, V value) {
        MyNode<K, V> newNode, oldNode;

        if (nEl >= (int) (capacity * LOAD)) {
            resize();
        }
        newNode = new MyNode<>(key, value);
        if ((oldNode = elements[newNode.hash % capacity]) == null) {
            elements[newNode.hash % capacity] = newNode;
            nEl++;
        } else {
            MyNode<K, V> prev = null;
            while (oldNode != null &&
                    (oldNode.hash == newNode.hash && !oldNode.key.equals(newNode.key))) {
                prev = oldNode;
                oldNode = oldNode.next;
            }
            if (prev == null) {
                newNode.next = oldNode;
                elements[newNode.hash % capacity] = newNode;
                nEl++;
            } else {
                prev.next = newNode;
            }
            if (oldNode == null) {
                nEl++;
            }
        }
    }

    private void resize() {
        MyNode<K, V>[] newElements = new MyNode[capacity * 2];
        capacity *= 2;
        nEl = 0;
        for (MyNode<K, V> myNode : elements) {
            MyNode<K, V> newNode;
            while (myNode != null) {
                newNode = new MyNode<>(myNode.key, myNode.value);
                if ((newElements[newNode.hash % capacity]) == null) {
                    newElements[newNode.hash % capacity] = newNode;
                    nEl++;
                } else {
                    MyNode<K, V> prev = null;
                    while (myNode != null &&
                            (myNode.hash == newNode.hash && !myNode.key.equals(newNode.key))) {
                        prev = myNode;
                        myNode = myNode.next;
                    }
                    if (prev == null) {
                        newNode.next = newElements[newNode.hash % capacity];
                        newElements[newNode.hash % capacity] = newNode;
                        nEl++;
                    } else {
                        prev.next = newNode;
                    }
                    if (myNode == null) {
                        nEl++;
                    }
                }
                myNode = myNode.next;
            }
        }
        this.elements = newElements;
    }

    /**
     * Функция позволяет найти значение по ключу в hashMap.
     *
     * @param key ключ по которому нужно выполнить поиск
     * @return value значение, найденное по ключу, или null если такого значения нет
     */
    public V get(K key) {
        MyNode<K, V> node;
        int hash = Objects.hash(key);
        if ((node = elements[hash % capacity]) != null) {
            do {
                if (node.hash == hash && node.key.equals(key)) {
                    return node.value;
                }
                node = node.next;
            } while (node != null);
        }
        return null;
    }

    /**
     * Функция удаления элемента по ключу
     *
     * @param key ключ по которому нужно удалить элемент
     * @return value возвращает значение в случае успеха и null в случае отсутствия элемента с таким ключом
     */
    public V delete(K key) {
        MyNode<K, V> node;
        int hash = Objects.hash(key);
        if ((node = elements[hash % capacity]) != null) {
            MyNode<K, V> prev = null;
            do {
                if (node.hash == hash && node.key.equals(key)) {
                    if (prev == null) {
                        elements[hash % capacity] = elements[hash % capacity].next;
                    } else {

                        prev.next = node.next;
                    }
                    nEl--;
                    return node.value;
                }
                prev = node;
                node = node.next;
            } while (node != null);
        }
        return null;
    }

    /**
     * Функция возвращает все значения, порядок не гарантирован.
     *
     * @return List<V> в котором все значения присутствующие в hashMap
     */
    public List<V> values() {
        List<V> list = new ArrayList<>();
        for (MyNode<K, V> myNode : elements) {
            while (myNode != null) {
                list.add(myNode.value);
                myNode = myNode.next;
            }
        }
        return list;
    }

    /**
     * Функция возвращает все ключи, порядок не гарантирован.
     *
     * @return Set<K> в котором все ключи присутствующие в hashMap
     */
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (MyNode<K, V> myNode : elements) {
            while (myNode != null) {
                set.add(myNode.key);
                myNode = myNode.next;
            }
        }
        return set;
    }

    /**
     * Функция возвращает все пары ключ-значения, порядок не гарантирован.
     *
     * @return Set<MyEntry < K, V>> в котором все пары ключ-значение присутствующие в hashMap
     */
    public Set<MyEntry<K, V>> entrySet() {
        Set<MyEntry<K, V>> set = new HashSet<>();
        for (MyNode<K, V> myNode : elements) {
            while (myNode != null) {
                set.add(new MyEntry<K, V>(myNode.key, myNode.value));
                myNode = myNode.next;
            }
        }
        return set;
    }

    /**
     * Пары ключ-значения
     *
     * @param <K> ключ
     * @param <V> значение
     */
    static class MyEntry<K, V> {

        private final K key;
        private final V value;

        MyEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MyEntry<?, ?> entry = (MyEntry<?, ?>) o;
            return Objects.equals(key, entry.key) && Objects.equals(value, entry.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }

    @Override
    public String toString() {
        StringBuilder print = new StringBuilder("MyHashMap{");
        for (int i = 0; i < capacity; i++) {
            if (elements[i] != null) {
                print.append(elements[i].toString()).append(", ");
            }
        }
        print.delete(print.length() - 2, print.length());
        print.append("}");
        return print.toString();
    }
}
