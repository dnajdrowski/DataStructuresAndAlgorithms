package com.dnajdrowski.BlackRedTrees;



public class Main {

    public static void main(String[] args) {
        Tree T = new Tree();
        T.RedBlackInsert(new Node("38"));
        T.RedBlackInsert(new Node("31"));
        T.RedBlackInsert(new Node("22"));
        T.RedBlackInsert(new Node("8"));
        T.RedBlackInsert(new Node("20"));
        T.RedBlackInsert(new Node("5"));
        T.RedBlackInsert(new Node("10"));
        T.RedBlackInsert(new Node("9"));
        T.RedBlackInsert(new Node("21"));
        T.RedBlackInsert(new Node("27"));
        T.RedBlackInsert(new Node("29"));
        T.RedBlackInsert(new Node("25"));
        T.RedBlackInsert(new Node("28"));
        T.Print(T.getRoot());
        System.out.println("\n\nQuantity of Red Leafs: " + T.RedLeafs(T.getRoot()));
        System.out.println("MaxDeep = " + T.MaxDeep(T.getRoot()));
        System.out.println("MinDeep = " + T.MinDeep(T.getRoot()));
    }
}

class Node {
    private String ANSI_BLACK_BACKGROUND = "\033[0;100m";
    private String ANSI_RED_BACKGROUND = "\u001B[41m";
    private String ANSI_RESET = "\u001B[0m";
    Node parent;
    Node left;
    Node right;
    String color;
    String number;

    public Node() {
        this.parent = new Node("nil");
        this.left = new Node("nil");
        this.right = new Node("nil");
        this.color = "BLACK";
        this.number = "nil";
    }

    public Node(String number) {
        if(!number.equals("nil")) {
            this.parent = new Node("nil");
            this.left = new Node("nil");
            this.right = new Node("nil");
            this.color = "BLACK";
            this.number = number;
        } else {
            this.color = "BLACK";
            this.number = number;
        }
    }

    @Override
    public String toString() {
//        if(color.equals("RED"))
//            return ANSI_RED_BACKGROUND + this.number + ANSI_RESET;
//        else
//            return ANSI_BLACK_BACKGROUND + this.number + ANSI_RESET;
        return this.number + " " + this.color;
    }

}

class Tree {
    private Node nil = new Node();
    private Node root = nil;

    public Tree() {
        root.parent = nil;
        root.right = nil;
        root.left = nil;
    }

    public Node getRoot() {
        return root;
    }

    public void Insert(Node z) {
        Node x = root;
        Node y = new Node();
        while(!x.number.equals("nil")) {
            y = x;
            if (Integer.parseInt(z.number) < Integer.parseInt(x.number))
                x = x.left;
            else
                x = x.right;
        }
        z.parent = y;
        if(y.number.equals("nil"))
            root = z;
        else if(Integer.parseInt(z.number) < Integer.parseInt(y.number))
            y.left = z;
        else if(Integer.parseInt(z.number) > Integer.parseInt(y.number))
            y.right = z;
        z.left = new Node();
        z.right = new Node();
        z.color = "RED";
    }

    public void RedBlackInsert(Node z) {
        Insert(z);
        Node y;
        while(z != root && z.parent.color.equals("RED")) {
            if(z.parent == z.parent.parent.left) {
                y = z.parent.parent.right;
                if((y.color.equals("RED"))) {
                    z.parent.color = "BLACK";
                    y.color = "BLACK";
                    z.parent.parent.color = "RED";
                    z = z.parent.parent;
                } else if(z == z.parent.right) {
                    z = z.parent;
                    LeftRotate(z);
                } else {
                    z.parent.color = "BLACK";
                    z.parent.parent.color = "RED";
                    RightRotate(z.parent.parent);
                }
            } else {
                y = z.parent.parent.left;
                if (y.color.equals("RED")){
                    z.parent.color = "BLACK";
                    y.color = "BLACK";
                    z.parent.parent.color = "RED";
                    z = z.parent.parent;
                } else if (z == z.parent.left) {
                    z = z.parent;
                    RightRotate(z);
                } else {
                    z.parent.color = "BLACK";
                    z.parent.parent.color = "RED";
                    LeftRotate(z.parent.parent);
                }
            }
        }
        root.color = "BLACK";
    }

    private void LeftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if(!y.number.equals("nil"))
            y.left.parent = x;
        y.parent = x.parent;

        if(x.parent.number.equals("nil"))
            root = y;
        else if(x.parent.left == x)
            x.parent.left = y;
        else
            x.parent.right = y;

        y.left = x;
        x.parent = y;

    }

    private void RightRotate(Node y) {
        Node x = y.left;
        y.left = x.right;

        if(!x.right.number.equals("nil"))
            x.right.parent = y;
        x.parent = y.parent;

        if(y.parent.number.equals("nil"))
            root = x;
        else if(y.parent.right == y)
            y.parent.right = x;
        else
            y.parent.left = x;

        x.right = y;
        y.parent = x;


    }

    public void Print(Node root) {
        if(!root.number.equals("nil")) {
            if(root == root.parent.right)
                System.out.println(root + " [right son of " + root.parent + "]");
            else if(root == root.parent.left)
                System.out.println(root + " [left son of " + root.parent + "]");
            else
                System.out.println(root + " [is a root]");
            Print(root.left);
            Print(root.right);
        }
    }

    public int MaxDeep(Node root) {
        int left = 0;
        int right = 0;
        if(!root.number.equals("nil")) {
            left = MaxDeep(root.left);
            right = MaxDeep(root.right);
            if(left > right)
                return 1 + left;
            else
                return 1 + right;
        } else
            return 0;
    }

    public int MinDeep(Node root) {
        int left = 0;
        int right = 0;
        if(!root.number.equals("nil")) {
            left = MinDeep(root.left);
            right = MinDeep(root.right);
            return (1+(left <= right ? left : right));
        } else
            return 0;
    }

    public int RedLeafs(Node root) {
        int redLeafs = 0;
        if(root.number.equals("nil"))
            return 0;
        redLeafs += RedLeafs(root.left);
        redLeafs += RedLeafs(root.right);

        if(root.color.equals("RED"))
            redLeafs++;
        return redLeafs;
    }
}