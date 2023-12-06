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