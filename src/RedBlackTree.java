public class RedBlackTree {
    protected Node root;
    protected int size = 0;

    private void insert(Node node, int item) {
        if (this.contains(item)) {
            return;
        }
        if (node.getData() > item) {
            if (node.hasLeft()) {
                this.insert(node.getLeft(), item);
            } else {
                Node inserted = new Node(item);
                node.setLeft(inserted);
                this.balanceAfterInsert(inserted);
            }
        } else if (node.hasRight()) {
            this.insert(node.getRight(), item);
        } else {
            Node inserted = new Node(item);
            node.setRight(inserted);
            this.balanceAfterInsert(inserted);
        }
    }
    private void balanceAfterInsert(Node node) {
        if (node == null || node == this.root || Node.isBlack(node.getParent())) {
            return;
        }
        if (Node.isRed(node.getUncle())) {
            Node.toggleColor(node.getParent());
            Node.toggleColor(node.getUncle());
            Node.toggleColor(node.getGrandparent());
            this.balanceAfterInsert(node.getGrandparent());
        } else if (this.hasLeftParent(node)) {
            if (this.isRightChild(node)) {
                node = node.getParent();
                this.rotateLeft(node);
            }
            Node.setColor(node.getParent(), Node.BLACK);
            Node.setColor(node.getGrandparent(), Node.RED);
            this.rotateRight(node.getGrandparent());
        } else if (this.hasRightParent(node)) {
            if (this.isLeftChild(node)) {
                node = node.getParent();
                this.rotateRight(node);
            }
            Node.setColor(node.getParent(), Node.BLACK);
            Node.setColor(node.getGrandparent(), Node.RED);
            this.rotateLeft(node.getGrandparent());
        }
    }
    private Node find(Node root, int data) {
        if (root == null) {
            return null;
        }
        if (root.getData()> data) {
            return this.find(root.getLeft(), data);
        }
        if (root.getData()< data) {
            return this.find(root.getRight(), data);
        }
        return root;
    }
    private Node getSuccessor(Node root) {
        Node leftTree = root.getLeft();
        if (leftTree != null) {
            while (leftTree.getRight() != null) {
                leftTree = leftTree.getRight();
            }
        }
        return leftTree;
    }
    private boolean contains(Node root, int data) {
        if (root == null) {
            return false;
        }
        if (root.getData() > data) {
            return this.contains(root.getLeft(), data);
        }
        if (root.getData() < data) {
            return this.contains(root.getRight(), data);
        }
        return true;
    }
    private void balanceAfterDelete(Node node) {
        while (node != this.root && node.isBlack()) {
            Node sibling = node.getSibling();
            if (node == Node.getLeft(node.getParent())) {
                if (Node.isRed(sibling)) {
                    Node.setColor(sibling, Node.BLACK);
                    Node.setColor(node.getParent(), Node.RED);
                    this.rotateLeft(node.getParent());
                    sibling = (Node) Node.getRight(node.getParent());
                }
                if (Node.isBlack(Node.getLeft(sibling)) && Node.isBlack(Node.getRight(sibling))) {
                    Node.setColor(sibling, Node.RED);
                    node = node.getParent();
                    continue;
                }
                if (Node.isBlack(Node.getRight(sibling))) {
                    Node.setColor(Node.getLeft(sibling), Node.BLACK);
                    Node.setColor(sibling, Node.RED);
                    this.rotateRight(sibling);
                    sibling = (Node) Node.getRight(node.getParent());
                }
                Node.setColor(sibling, Node.getColor(node.getParent()));
                Node.setColor(node.getParent(), Node.BLACK);
                Node.setColor(Node.getRight(sibling), Node.BLACK);
                this.rotateLeft(node.getParent());
                node = this.root;
                continue;
            }
            if (Node.isRed(sibling)) {
                Node.setColor(sibling, Node.BLACK);
                Node.setColor(node.getParent(), Node.RED);
                this.rotateRight(node.getParent());
                sibling = (Node) Node.getLeft(node.getParent());
            }
            if (Node.isBlack(Node.getLeft(sibling)) && Node.isBlack(Node.getRight(sibling))) {
                Node.setColor(sibling, Node.RED);
                node = node.getParent();
                continue;
            }
            if (Node.isBlack(Node.getLeft(sibling))) {
                Node.setColor(Node.getRight(sibling), Node.BLACK);
                Node.setColor(sibling, Node.RED);
                this.rotateLeft(sibling);
                sibling = (Node) Node.getLeft(node.getParent());
            }
            Node.setColor(sibling, Node.getColor(node.getParent()));
            Node.setColor(node.getParent(), Node.BLACK);
            Node.setColor(Node.getLeft(sibling), Node.BLACK);
            this.rotateRight(node.getParent());
            node = this.root;
        }
        Node.setColor(node, Node.BLACK);
    }
    private boolean hasRightParent(Node node) {
        if (Node.getRight(node.getGrandparent()) == node.getParent()) {
            return true;
        }
        return false;
    }

    private void rotateRight(Node node) {
        if (node.getLeft() == null) {
            return;
        }
        Node leftTree = node.getLeft();
        node.setLeft(leftTree.getRight());
        if (node.getParent() == null) {
            this.root = leftTree;
        } else if (node.getParent().getLeft() == node) {
            node.getParent().setLeft(leftTree);
        } else {
            node.getParent().setRight(leftTree);
        }
        leftTree.setRight(node);
    }
    private void rotateLeft(Node node) {
        if (node.getRight() == null) {
            return;
        }
        Node rightTree = node.getRight();
        node.setRight(rightTree.getLeft());
        if (node.getParent() == null) {
            this.root = rightTree;
        } else if (node.getParent().getLeft() == node) {
            node.getParent().setLeft(rightTree);
        } else {
            node.getParent().setRight(rightTree);
        }
        rightTree.setLeft(node);
    }

    private boolean hasLeftParent(Node node) {
        if (Node.getLeft(node.getGrandparent()) == node.getParent()) {
            return true;
        }
        return false;
    }
    private boolean isRightChild(Node node) {
        if (Node.getRight(node.getParent()) == node) {
            return true;
        }
        return false;
    }
    private boolean isLeftChild(Node node) {
        if (Node.getLeft(node.getParent()) == node) {
            return true;
        }
        return false;
    }
    private int getDepth(Node node) {
        if (node != null) {
            int right_depth;
            int left_depth = this.getDepth(node.getLeft());
            return left_depth > (right_depth = this.getDepth(node.getRight())) ? left_depth + 1 : right_depth + 1;
        }
        return 0;
    }


    public Node getRoot() {
        return this.root;
    }
    public Node find(int data) { return this.find(this.root, data); }
    public void clear() {
        this.root = null;
    }
    public int getSize() {
        return this.size;
    }
    public void insert(int item) {
        if (this.isEmpty()) {
            this.root = new Node (item);
        } else {
            this.insert(this.root, item);
        }
        this.root.setColor(Node.BLACK);
        ++this.size;
    }
    public boolean isEmpty() {
        if (this.root == null) {
            return true;
        }
        return false;
    }
    public boolean contains(int data) {
        return this.contains(this.root, data);
    }
    public int getDepth() {
        return this.getDepth(this.root);
    }
    public void delete(int data) {
        if (!this.contains(data)) {
            return;
        }

        Node node = this.find(data);
        if (node.getLeft() != null && node.getRight() != null) {
            Node successor = this.getSuccessor(node);
            node.setData(successor.getData());
            node = successor;
        }

        Node pullUp = node.getLeft() == null ? node.getRight() : node.getLeft();
        if (pullUp != null) {
            if (node == this.root) {
                node.removeFromParent();
                this.root = node;
            } else if (Node.getLeft(node.getParent()) == node) {
                node.getParent().setLeft(pullUp);
            } else {
                node.getParent().setRight(pullUp);
            }
            if (Node.isBlack(node)) {
                this.balanceAfterDelete(pullUp);
            }
        } else if (node == this.root) {
            this.root = null;
        } else {
            if (Node.isBlack(node)) {
                this.balanceAfterDelete(node);
            }
            node.removeFromParent();
        }
    }


    private void printHelper(Node root, String indent, boolean last) {
        // print the tree structure on the screen
        if (root != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|    ";
            }

            String sColor = (root.getColor() == false)?"RED":"BLACK";
            System.out.println(root.getData() + "(" + sColor + ")");
            printHelper(root.getLeft(), indent, false);
            printHelper(root.getRight(), indent, true);
        }
    }


    public void prettyPrint() {
        printHelper(this.root, "", true);
    }

    public static void main(String arg[])
    {
        RedBlackTree bst = new RedBlackTree();
        bst.insert(8);
        bst.insert(18);
        bst.insert(5);
        bst.insert(15);
        bst.insert(17);
        bst.insert(25);
        bst.insert(40);
        bst.insert(80);
        bst.prettyPrint();
    }


}
