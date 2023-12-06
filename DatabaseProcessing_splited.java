import java.util.*;
import java.io.*;

public class DatabaseProcessing_splited {
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