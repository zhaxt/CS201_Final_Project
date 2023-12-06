import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

interface MyData<E, T> extends Comparable<E> {
    public int match(T e);
    public String toString();
}

class PeopleRecord implements MyData<PeopleRecord, String[]> {
    /*
     * this is a read-only data class once constructed
     */

    private String givenName, familyName, companyName, address, city,
            county, state, zip, phone1, phone2, email, web, birthday;

    PeopleRecord() { // record.length = 13
        givenName = "";
        familyName = "";
        companyName = "";
        address = "";
        city = "";
        county = "";
        state = "";
        zip = "";
        phone1 = "";
        phone2 = "";
        email = "";
        web = "";
        birthday = "";
    }

    PeopleRecord(String[] record) { // record.length = 13
        givenName = record[0];
        familyName = record[1];
        companyName = record[2];
        address = record[3];
        city = record[4];
        county = record[5];
        state = record[6];
        zip = record[7];
        phone1 = record[8];
        phone2 = record[9];
        email = record[10];
        web = record[11];
        birthday = record[12];
    }

    public String getGivenName() { // getters * 13
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCounty() {
        return county;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getPhone1() {
        return phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public String getEmail() {
        return email;
    }

    public String getWeb() {
        return web;
    }

    public String getBirthday() {
        return birthday;
    }

    public int compareTo(PeopleRecord other) {
        if (familyName.compareTo(other.familyName) > 0) // family name first
            return 1;
        else if (familyName.compareTo(other.familyName) < 0)
            return -1;
        else {
            if (givenName.compareTo(other.givenName) > 0) // given name second
                return 1;
            else if (givenName.compareTo(other.givenName) < 0)
                return -1;
            else { // Support additional ways of comparing
                if (other.email == null)
                    return 0;
                if (email.compareTo(other.email) > 0) // email in case
                    return 1;
                else if (email.compareTo(other.email) < 0)
                    return -1;
                else
                    return 0;
            }
        }
    }

    public int match(String[] names) {
        PeopleRecord people = new PeopleRecord(Arrays.copyOf(names, 13));
        return compareTo(people);
    }

    public String toString() {
        return "[" + givenName + ", " + familyName + ", " + companyName + ", "
                + address + ", " + city + ", " + county + ", " + state + ", "
                + zip + ", " + phone1 + ", " + phone2 + ", " + email + ", "
                + web + ", " + birthday + "]";
    }
}


class MyBST<E extends MyData<E, String[]>> {
    private TreeNode<E> root;

    private static class TreeNode<E> {
        E data;
        TreeNode<E> left;
        TreeNode<E> right;

        TreeNode(E data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    public void getInfo() {
        int[] result = getInfoHelper(root);
        System.out.println("Total number of nodes: " + result[0]);
        System.out.println("Height of the tree: " + result[1]);
    }

    // returns the total number of nodes and the height of the tree
    private int[] getInfoHelper(TreeNode<E> root) {
        if (root == null) {
            return new int[] { 0, -1 };
        }
        int[] leftInfo = getInfoHelper(root.left);
        int[] rightInfo = getInfoHelper(root.right);

        int totalNodes = leftInfo[0] + rightInfo[0] + 1;
        int height = Math.max(leftInfo[1], rightInfo[1]) + 1;

        return new int[] { totalNodes, height };
    }

    public void insert(E record) {
        root = insertHelper(root, record);
    }

    private TreeNode<E> insertHelper(TreeNode<E> root, E record) {
        if (root == null) {
            return new TreeNode<E>(record);
        }
        if (record.compareTo(root.data) < 0) {
            root.left = insertHelper(root.left, record);
        } else if (record.compareTo(root.data) > 0) {
            root.right = insertHelper(root.right, record);
        }
        return root;
    }

    public void search(String[] names) {
        if (names.length == 2) {
            System.out.println("Matching Record: ");
            searchHelper(root, names);
        }
    }

    private void searchHelper(TreeNode<E> root, String[] names) {
        if (root != null) {
            int compare = root.data.match(names);
            if (compare == 0) {
                System.out.println(root.data.toString());
                if (root.left.data.match(names) == 0)
                    searchHelper(root.left, names);
                if (root.right.data.match(names) == 0)
                    searchHelper(root.right, names);
            }
            if (compare >= 0) {
                searchHelper(root.left, names);
            }
            if (compare <= 0) {
                searchHelper(root.right, names);
            }
        }
    }

    // print the BST in order
    static int displayNum;

    public void display(int num) {
        displayNum = num;
        recursionDisplay(root);
    }

    public void recursionDisplay(TreeNode<E> root) {
        if (root.left != null)
            recursionDisplay(root.left);
        if (displayNum > 0) {
            System.out.println(root.data);
            displayNum--;
        }
        if (root.right != null && displayNum > 0)
            recursionDisplay(root.right);

    }
}


class MyHeap<E extends Comparable<E>> {
    private ArrayList<E> list;

    MyHeap() {
        list = new ArrayList<E>();
    }

    public void insert(E element) {
        list.add(element);
        int index = list.size() - 1; // parent index (i-1)/2
        E parent = list.get((index - 1) / 2);
        while (element.compareTo(parent) < 0 && index != 0) {
            list.set(index, parent);
            index = (index - 1) / 2;
            parent = list.get((index - 1) / 2);
        }
        list.set(index, element);
    }

    private E Child(int index, E last, int i) {
        if (index * 2 + i < list.size())
            return list.get(index * 2 + i);
        else
            return last;
    }

    public E pop() {
        if (list.isEmpty()) {
            return null;
        } else {
            E answer = list.get(0);

            int index = list.size() - 1;
            E last = list.get(index); // last element
            list.set(0, last); // shift to top
            list.remove(index); // delete to update size

            if (!list.isEmpty()) {
                index = 0; // update index, child i*2+1 or +2
                E child1, child2;
                child1 = Child(index, last, 1); // update child
                child2 = Child(index, last, 2);

                while (last.compareTo(child1) > 0 || last.compareTo(child2) > 0) {
                    if (child1.compareTo(child2) < 0) { // shift down
                        list.set(index, child1);
                        index = index * 2 + 1;
                    } else {
                        list.set(index, child2);
                        index = index * 2 + 2;
                    }

                    child1 = Child(index, last, 1); // update child
                    child2 = Child(index, last, 2);
                }
                list.set(index, last);
            }
            return answer;
        }
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void display(int num) {
        for (int i = 0; i < num; i++) {
            System.out.println(list.get(i));
        }
    }
}


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


class Tuple implements Comparable<Tuple>{ //sort the string
    String word;
    int Count;
    public Tuple(){word = "";Count = 0;}

    @Override
    public int compareTo(Tuple other) {
        return Count == other.Count ? word.compareTo(other.word) : Count - other.Count;
    }
}


public class DatabaseProcessing {
    private MyBST<PeopleRecord> bst = new MyBST<>();
    private MyHeap<PeopleRecord> heap = new MyHeap<>();
    private MyHashmap<String, Integer> wordFrequencyMap = new MyHashmap<>();
    private PeopleRecord PeopleInfo[] = new PeopleRecord[100005];
    private int peopletotal = 0;

    public void loadData(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] record = line.split(";");
                PeopleRecord peopleRecord = new PeopleRecord(record);
                PeopleInfo[peopletotal] = peopleRecord;
                ++peopletotal;
                bst.insert(peopleRecord);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void search(String firstName, String familyName) {
        String[] names = { firstName, familyName };
        bst.search(names);
    }

    public void sort(int display) {
        for (int i = 0; i < peopletotal; i++)
            heap.insert(PeopleInfo[i]);
        System.out.println("Heap sorted data:");
        if (peopletotal > display) {
            for (int i = 0; i < display; i++)
                System.out.println(heap.pop());
            System.out.println("...");
        }else{
            for (int i = 0; i < peopletotal; i++)
                System.out.println(heap.pop());
        }

    }

    public void InsertOneWord(String word, int len) {
        if (word.length() < len)
            return;
        int now = 0;
        if (wordFrequencyMap.get(word) == null)
            now = 1;
        else
            now = wordFrequencyMap.get(word) + 1;
        wordFrequencyMap.put(word, now);
    }

    public boolean check(String S) {
        for (int i = 0; i < S.length(); i++)
            if (S.charAt(i) < 65 || S.charAt(i) > 97 + 25 || (S.charAt(i) > 65 + 25 && S.charAt(i) < 97))
                return false;
        return true;
    }

    public void InsertWord(String word, int len) {
        String[] words = word.split(" ");
        for (int i = 0; i < words.length; i++)
            if (words[i].length() >= len && check(words[i]))
                InsertOneWord(words[i], len);
    }

    public void getMostFrequentWords(int count, int len) {
        if (len < 3) {
            System.err.println("Error. 'len' is smaller than 3");
            throw new ShortLengthException("Length should be 3 or greater.");
        }
        for (int i = 0; i < peopletotal; i++) {
            InsertWord(PeopleInfo[i].getGivenName(), len);
            InsertWord(PeopleInfo[i].getFamilyName(), len);
            InsertWord(PeopleInfo[i].getCompanyName(), len);
            InsertWord(PeopleInfo[i].getAddress(), len);
            InsertWord(PeopleInfo[i].getCity(), len);
            InsertWord(PeopleInfo[i].getCounty(), len);
            InsertWord(PeopleInfo[i].getState(), len);
        }

        Tuple[] WordCounts = new Tuple[100005];
        String[] names = new String[100005];
        Integer[] counts = new Integer[100005];
        int size = wordFrequencyMap.Size(), tuplesize = 0;
        wordFrequencyMap.getAllValue(names, counts);
        for (int i = 0; i < size; i++) {
            if (names[i] == null)
                continue;
            WordCounts[tuplesize] = new Tuple();
            WordCounts[tuplesize].word = names[i];
            WordCounts[tuplesize].Count = counts[i];
            tuplesize++;
        }

        Arrays.sort(WordCounts, 0, tuplesize);
        int counter = 0;
        System.out.println("The most frequent used words are:");
        for (int i = tuplesize - 1; i >= 0; i--) {
            System.out.println(WordCounts[i].word + " :" + WordCounts[i].Count);
            ++counter; // add 1 to counter
            if (counter == count)
                break; // print 5 most frequent words when counter reaches count(limit)
        }
    }

    class ShortLengthException extends RuntimeException {
        public ShortLengthException(String message) {
            super(message);
        }
    }

    // print the first "num" people in BST and Heap
    public void printBST(int num){
        bst.display(num);
    }

    public void printHeap(int num){
        heap.display(num);
    }

    public static void main(String[] args) {
        DatabaseProcessing databaseProcessing = new DatabaseProcessing();

        // Load data from a file (replace "filename.txt" with your actual file name)
        databaseProcessing.loadData("people.txt");

        // Perform a search (replace "John" and "Doe" with your desired names)
        databaseProcessing.search("Simona", "Morasca");
        System.out.println();

        // Sort and print records
        databaseProcessing.sort(3);
        System.out.println();

        // Get and print the most frequent words
        databaseProcessing.getMostFrequentWords(3, 3);
        System.out.println();
        // databaseProcessing.getMostFrequentWords(5, 2);

        databaseProcessing.printBST(6);
        System.out.println();
        databaseProcessing.printHeap(3);
        System.out.println();
    }

}