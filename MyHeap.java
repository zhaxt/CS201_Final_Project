import java.util.ArrayList;

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