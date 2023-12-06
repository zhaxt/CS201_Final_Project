class Tuple implements Comparable<Tuple>{ //sort the string
    String word;
    int Count;
    public Tuple(){word = "";Count = 0;}

    @Override
    public int compareTo(Tuple other) {
        return Count == other.Count ? word.compareTo(other.word) : Count - other.Count;
    }
}