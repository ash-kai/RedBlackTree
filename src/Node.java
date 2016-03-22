/**
 * Created by Ash on 3/18/16.
 */
public class Node {
    int ID; // contains the ID value of respective node
    int count; //contains the Count value of the respective node
    Node left, right, parent; // Node objects denoting the right, left and parent of current node
    boolean color; // A boolean variable indicating whether the node is black or red.


    //Node class constructor
    public Node(int val, int count, Node none) {
        this.ID = val;
        this.left = none != null ? none : null;
        this.right = none != null ? none : null;
        this.parent = none != null ? none : null;
        this.color = false;
        this.count = count;
    }

    // Function to check if respective node's color property is Black or Red
    public boolean isBlack() {
        return this.color == false;
    }

    // Function to check if respective node is valid or null
    public boolean isEmpty() {
        return this.ID == 0;
    }

    /// Function to calculate the number of black nodes along a path.
    public int blackHeight() {
        if (isEmpty()) {
            return 0;
        }
        if (isBlack()) {
            return 1 + left.blackHeight();
        } else {
            return left.blackHeight();
        }
    }

    //Function to determine if all the paths from the current node have the same number of black nodes.
    public boolean consistentBlackHeight(int height) {
        if (isEmpty()) {
            return height == 0;
        }
        if (isBlack()) {
            height--;
        }
        return left.consistentBlackHeight(height) && right.consistentBlackHeight(height);
    }

    //Function to determine if all the paths from the root node have the same number of black nodes.
    public boolean checkBlackHeight() {
        int height = blackHeight();
        return consistentBlackHeight(height);
    }

    //Function to if the tree structure from the current node satisfies the red color property i.e. no two consecutive nodes are red in color.
    public boolean checkRedConsistency() {
        if (isEmpty()) {
            return true;
        }
        if (this.color && (left.color || right.color)) {
            return false;
        }
        return left.checkRedConsistency() && right.checkRedConsistency();
    }

    //Function to validate the tree structure on the basis of Red Color Consistency and Black Color Consistency
    public void validationCheck() {
        if (checkRedConsistency()) {
            System.out.println("The tree is Red Color consistent");
        } else {
            System.out.println("The tree is not Red Color consistent");
        }

        if (checkBlackHeight()) {
            System.out.println("The tree is Black Color Height consistent");
        } else {
            System.out.println("The tree is not Black color height consistent");
        }

    }

    //Function to calculate the next node of the respective node based on the ID value.
    public Node successor() {
        if (right.ID != 0) {
            return right.minimum();
        }
        Node x = this;
        Node y = x.parent;

        while (y.ID != 0 && x == y.right) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    //Function to calculate the previous node of the respective node based on ID value.
    public Node predecessor() {
        Node x = this;
        if (left.ID != 0) {
            return left.maximum();
        }
        Node y = x.parent;
        while (y.ID != 0 && x == y.left) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    //Function to calculate the next minimum value from the current node.
    public Node minimum() {
        Node x = this;
        while (x.left.ID != 0) {
            x = x.left;
        }
        return x;
    }

    public Node maximum() {
        Node x = this;
        while (x.right.ID != 0) {
            x = x.right;
        }
        return x;
    }

    //Traverse the node structure from the respective node in InOrder fashion i.e. left->center->right
    public void inOrderTraverse() {
        if (this.ID != 0) {
            left.inOrderTraverse();
            System.out.print(this.ID + " ");
            right.inOrderTraverse();
        }
        return;
    }

}
