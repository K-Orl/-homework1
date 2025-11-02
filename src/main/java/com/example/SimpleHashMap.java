package com.example;

import java.util.Objects;

public class SimpleHashMap<K, V> {

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node<K, V>[] table;
    private int size;

    public SimpleHashMap() {
        table = new Node[16];
    }

    private int getIndex(Object key) {
        if (key == null) return 0;
        return Math.abs(key.hashCode() % table.length);
    }

    public void put(K key, V value) {
        int index = getIndex(key);
        Node<K, V> current = table[index];

        if (current == null) {
            table[index] = new Node<>(key, value);
            size++;
            return;
        }

        while (true) {
            if (Objects.equals(current.key, key)) {
                current.value = value;
                return;
            }
            if (current.next == null) {
                current.next = new Node<>(key, value);
                size++;
                return;
            }
            current = current.next;
        }
    }

    public V get(K key) {
        int index = getIndex(key);
        Node<K, V> current = table[index];

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public V remove(K key) {
        int index = getIndex(key);
        Node<K, V> current = table[index];
        Node<K, V> previous = null;

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                if (previous == null) {
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }
        return null;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Node<K, V> node : table) {
            while (node != null) {
                if (!first) sb.append(", ");
                sb.append(node.key).append("=").append(node.value);
                first = false;
                node = node.next;
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) {
        SimpleHashMap<String, Integer> map = new SimpleHashMap<>();

        map.put("apple", 10);
        map.put("banana", 20);
        map.put("orange", 30);

        System.out.println(map);
        System.out.println("apple -> " + map.get("apple"));
        System.out.println("remove banana -> " + map.remove("banana"));
        System.out.println("После удаления: " + map);
        System.out.println("size = " + map.size());
    }
}
