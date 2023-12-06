interface MyData<E, T> extends Comparable<E> {
    public int match(T e);
    public String toString();
}