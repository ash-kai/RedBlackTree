/**
 * Created by Ash on 3/18/16.
 */
public class RedBlackBST {

    Node root;

    public RedBlackBST(Node root) {
        this.root = root;
    }

    //Function to check if the tree is Empty i.e. does not contain any nodes inside it with ID>0
    public boolean isTreeEmpty(Node root, Node none) {
        return root == none;
    }

    //Function to perform a left rotate around x
    //@param x, The node on which the left rotate is performed on.
    public void leftRotate(Node x, Node none) {
        Node y = x.right;
        x.right = y.left;
        if (y.left == none) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == none) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    //Function to perform a right rotate around x
    //@param x, The node on which the right rotate is performed on.
    public void rightRotate(Node x, Node none) {
        Node y = x.left;
        x.left = y.right;
        if (y.right == none) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == none) {
            root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    //Insert node z into the appropriate position at Red Black Tree
    //@param z, the node to be inserted into the tree
    public Node insertTree(Node z, Node none) {
        Node y = none;
        Node x = root;
        while (x != none) {
            y = x;
            if (z.ID < x.ID) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.parent = y;
        if (y == none) {
            root = z;
        } else if (z.ID < y.ID) {
            y.left = z;
        } else {
            y.right = z;
        }
        z.left = none;
        z.right = none;
        z.color = true;
        rbInsert(z, none);
        return z;
    }

    //Function is used to fix the violation of RedBlack Tree properties that might have been caused due to insertion of node
    //@param z, The node which was inserted and may have caused a violation.
    public void rbInsert(Node z, Node none) {
        Node y = none;
        while (z.parent.color) {
            if (z.parent == z.parent.parent.left) {
                y = z.parent.parent.right;
                if (y.color) {
                    z.parent.color = false;
                    y.color = false;
                    z.parent.parent.color = true;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z, none);
                    }
                    z.parent.color = false;
                    z.parent.parent.color = true;
                    rightRotate(z.parent.parent, none);
                }
            } else {
                y = z.parent.parent.left;
                if (y.color) {
                    z.parent.color = false;
                    y.color = false;
                    z.parent.parent.color = true;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z, none);
                    }
                    z.parent.color = false;
                    z.parent.parent.color = true;
                    leftRotate(z.parent.parent, none);
                }
            }
        }
        root.color = false;
    }

    //
    public void rbTransplant(Node u, Node v, Node none) {
        if (u.parent == none) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    //Function to remove the node z from the tree.
    //@param z, the node which is to be removed from the tree.
    public void rbDelete(Node z, Node none) {
        Node y = z;
        Node x = none;
        boolean yOriginalColor = y.color;
        if (z.left == none) {
            x = z.right;
            rbTransplant(z, z.right, none);
        } else if (z.right == none) {
            x = z.left;
            rbTransplant(z, z.left, none);
        } else {
            y = z.right.minimum();
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = z;
            } else {
                rbTransplant(y, y.right, none);
                y.right = z.right;
                y.right.parent = y;
            }
            rbTransplant(z, y, none);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == false) {
            rbDeleteFixup(x, none);
        }
    }

    //Function to restore the Red Black tree properties which may have been violated because of removal of node from the tree.
    //@param z, the child of the node which has been removed from the tree.
    public void rbDeleteFixup(Node x, Node none) {
        Node w = none;
        while (x != none && x.color == false) {
            if (x == x.parent.left) {
                w = x.parent.right;
                if (w.color) {
                    w.color = false;
                    w.parent.color = true;
                    leftRotate(x.parent, none);
                    w = x.parent.right;
                }
                if (w.left.color == false && w.right.color == false) {
                    w.color = true;
                    x = x.parent;
                } else {
                    if (w.right.color == false) {
                        w.left.color = false;
                        w.color = true;
                        rightRotate(w, none);
                        w = x.parent.right;
                    }
                    w.color = x.parent.color;
                    x.parent.color = false;
                    w.right.color = false;
                    leftRotate(x.parent, none);
                    x = root;
                }
            } else {
                w = x.parent.left;
                if (w.color) {
                    w.color = false;
                    w.parent.color = true;
                    rightRotate(x.parent, none);
                    w = x.parent.left;
                }
                if (w.right.color == false && w.left.color == false) {
                    w.color = true;
                    x = x.parent;
                } else {
                    if (w.left.color == false) {
                        w.right.color = false;
                        w.color = true;
                        leftRotate(w, none);
                        w = x.parent.left;
                    }
                    w.color = x.parent.color;
                    x.parent.color = false;
                    w.left.color = false;
                    rightRotate(x.parent, none);
                    x = root;
                }
            }
        }
    }

    //Function to obtain the root node of the tree.
    public Node getRootNode() {
        return root;
    }

    //Function to obtain the sum of the count of nodes which lie between the given parameters.
    //@param id1, integer which is used to denote the starting node ID value
    //@param id2, integer which is used to dentoed the ending node ID value
    public int inRange(Node root, int id1, int id2) {
        if (root.ID == 0) {
            return 0;
        }
        if (root.ID > id1) {
            return inRange(root.left, id1, id2);
        }
        if (root.ID >= id1 && root.ID <= id2) {
            return root.count + inRange(root.left, id1, id2) + inRange(root.right, id1, id2);
        }
        if (root.ID < id2) {
            return inRange(root.right, id1, id2);
        }
        return 0;
    }

    //Function to obtain the node having the given ID value.
    //@param id, the integer which is used to denote the ID value.
    public Node getNode(int id) {
        if (root.isEmpty()) {
            return null;
        }
        Node current = root;
        while (current.ID != 0) {
            if (current.ID == id) {
                return current;
            }
            if (current.ID < id) {
                current = current.right;
            } else {
                current = current.left;
            }
        }
        return null;
    }

    //Function which is used to increase the count value of respective node having ID as given.
    //@param id, the integer which is used to denote the ID value of respective node.
    //@param count, the integer by which the respective node's count has to be increased.
    public boolean increase(int id, int count) {
        Node node = getNode(id);
        if (node == null) {
            return false;
        }
        node.count += count;
        System.out.println(node.count);
        return true;
    }

    //Function which is used to decrease the count value of respective node having ID as given.
    //@param id, the integer which is used to denote the ID value of respective node.
    //@param count, the integer by which the respective node's count has to be decreased.
    public boolean reduce(int id, int count) {
        Node node = getNode(id);
        if (node == null) {
            System.out.println(0);
            return false;
        } else {
            node.count -= count;
            if (node.count <= 0) {
                System.out.println(0);
                return true;
            }
        }
        System.out.println(node.count);
        return false;
    }

    //Function to obtain the count value of respective node.
    //@param id, the integer which represents the ID of the node whose count is required.
    public void Count(int id) {
        Node node = getNode(id);
        if (node == null) {
            System.out.println(0);
        } else {
            System.out.println(node.count);
        }
    }

    //Function to get the Node whose ID's value is just successor of given id.
    //@param id, the integer represents the ID value for whom the successor is required.
    public Node nextNode(int id) {
        Node current = root;
        Node none = current.parent;
        if (current == none) {
            return none;
        }
        Node temp = none;
        while (current != none) {
            if (current.ID > id && temp == none) {
                temp = current;
            } else if (current.ID > id && temp != none && temp.ID > current.ID) {
                temp = current;
            }
            if (current.ID <= id) {
                current = current.right;
            } else {
                current = current.left;
            }
        }
        return temp;
    }

    //Function to get the Node whose ID's value is just predecessor of given id.
    //@param id, the integer represents the ID value for whom the predecessor is required.
    public Node previousNode(int id) {
        Node current = root;
        Node none = current.parent;
        if (current == none) {
            return none;
        }
        Node temp = none;
        while (current != none) {
            if (current.ID < id && temp == none) {
                temp = current;
            } else if (current.ID < id && temp != none && temp.ID < current.ID) {
                temp = current;
            }
            if (current.ID < id) {
                current = current.right;
            } else {
                current = current.left;
            }
        }
        return temp;
    }

    //Function to get the sum of counts of the nodes present between the respective ids.
    //@param id1, the integer, which is used to denote the starting ID value of nodes.
    //@param id2, the integer, which is used to denote the ending ID value of nodes.
    public int inRange(int id1, int id2) {
        int sum = 0;
        Node node = getNode(id1);
        if (node == null) {
            node = nextNode(id1);
        }
        if (node.ID == 0) {
            return sum;
        }
        Node current = node;
        while (current.ID != 0) {
            if (current.ID > id2) {
                break;
            }
            sum = sum + current.count;
            current = current.successor();
        }
        return sum;
    }
}
