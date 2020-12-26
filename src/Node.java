import java.awt.*;

public class Node{
    /**false - red, true - black */
    public static boolean RED = false;
    public static boolean BLACK = true;

    private boolean color = RED;
    private Node left=null;
    private Node right=null;
    private Node parent=null;
    private int data;


    public Node(){};
    public Node(int data){this.data=data;}
    public void setData(int data) {
        this.data = data;
    }
    public void setColor(boolean color) {
        this.color = color;
    }
    public void toggleColor() { color = !color; }
    public void setParent(Node parent){this.parent=parent;}
    public void setLeft(Node child) {

        // Re-arranging the parents is required
        if (getLeft() != null)
            getLeft().setParent(null);

        if (child != null) {
            child.removeFromParent();
            child.setParent(this);
        }

        this.left = child;
    }
    public void setRight(Node child) {

        // Re-arranging the parents is required
        if (getRight() != null) {
            getRight().setParent(null);
        }

        if (child != null) {
            child.removeFromParent();
            child.setParent(this);
        }

        this.right = child;
    }

    public Node getParent() { return parent; }
    public Node getLeft(){return left;}
    public Node getRight(){return right;}
    public int getData(){return data; }
    public boolean getColor() { return color; }

    /**RBt fun*/
    public Node getGrandparent() {
        Node parent = getParent();
        return (parent == null) ? null : parent.getParent();
    }
    public Node getSibling() {
        Node parent = getParent();
        if (parent != null) { // No sibling of root node
            if (this == parent.getRight())
                return (Node) parent.getLeft();
            else
                return (Node) parent.getRight();
        }
        return null;
    }
    public Node getUncle() {
        Node parent = getParent();
        if (parent != null) { // No uncle of root
            return parent.getSibling();
        }
        return null;
    }
    /**Node services*/
    public static boolean getColor(Node node) { return node == null ? BLACK : node.getColor(); }
    public static Node getLeft(Node node) { return node == null ? null : node.getLeft(); }
    public static Node getRight(Node node) { return node == null ? null : node.getRight(); }
    public static Node  getParent(Node node) { return (node == null) ? null : node.getParent(); }
    public static Node getGrandparent(Node node) { return (node == null) ? null : node.getGrandparent(); }
    public static Node getSibling(Node node) { return (node == null) ? null : node.getSibling(); }
    public static Node getUncle(Node node) { return (node == null) ? null : node.getUncle(); }
    public static int compare(Node node, Comparable b) { return ((Comparable) node.getData()).compareTo(b); }
    public static boolean isRed(Node node) { return getColor(node) == RED; }
    public static boolean isBlack(Node  node) { return !isRed(node); }
    public static void setColor(Node node, boolean color) {
        if (node == null)
            return;
        node.setColor(color);
    }
    public static void toggleColor(Node node) {
        if (node == null)
            return;

        node.setColor(!node.getColor());
    }

    public boolean hasLeft() { return left != null; }
    public boolean hasRight() { return right != null; }

    @Override
    public String toString() { return String.valueOf(data); }





    public void removeFromParent() {
        if (getParent() == null)
            return;

        // Remove current node's links from the parent
        if (parent.getLeft() == this)
            parent.setLeft(null);
        else if (parent.getRight() == this)
            parent.setRight(null);

        this.parent = null;
    }

    public boolean isRed() { return getColor() == RED; }
    public boolean isBlack() { return !isRed(); }



    public Color getActualColor() {
        if (isRed())
            return new Color(250, 70, 70);
        else
            return new Color(70, 70, 70);

    }


}
