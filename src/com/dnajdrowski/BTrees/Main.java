package com.dnajdrowski.BTrees;

public class Main {

    public static void main(String[] args) {
        BTree t = new BTree(3);
        t.insert(10);
        t.insert(20);
        t.insert(5);
        t.insert(6);
        t.insert(12);
        t.insert(30);
        t.insert(7);
        t.insert(17);
        t.traverse();
    }

}

class Node {
    int n;
    int t;
    int keys[];
    boolean leaf;
    Node childs[];

    public Node(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        keys = new int[2*t-1];
        childs = new Node[2*t];
    }

    public Node search(int key) {
        int i = 0;
        while((i < n) && (key > keys[i])) {
            i++;
        }
        if (keys[i] == key) {
            return this;
        }

        if (leaf == true) {
            return null;
        }
        return childs[i].search(key);
    }

    public void traverse() {
        int i = 0;
        for(i = 0; i < n; i++) {
            if(leaf == false) {
                childs[i].traverse();
            }
            System.out.print(" " + keys[i]);
        }
        if(leaf == false) {
            childs[i].traverse();
        }
    }

    public void insertNonFull(int k) {
        int i = n - 1;
        if(leaf == true) {
            while( i>= 0 && keys[i] > k) {
                keys[i+1] = keys[i];
                i--;
            }
            keys[i+1] = k;
            n = n + 1;
        }
        else
        {
            while(i >= 0 && keys[i] > k)
                i--;

            if(childs[i+1].n == 2*t-1) {
                splitChild(i+1, childs[i+1]);

                if(keys[i+1] < k)
                    i++;
            }
            childs[i+1].insertNonFull(k);
        }
    }

    public void splitChild(int i, Node y) {
        Node z = new Node(y.t, y.leaf);
        z.n = t-1;

        for(int j = 0; j<t-1; j++) {
            z.keys[j] = y.keys[j+t];
        }

        if(y.leaf == false) {
            for(int j = 0; j < t; j++) {
                z.childs[j] = y.childs[j+t];
            }
        }
        y.n = t-1;

        for(int j = n; j>= i+1; j--) {
            childs[j+1] = childs[j];
        }
        childs[i+1] = z;

        for(int j = n-1; j>=i;j--)
            keys[j+1] = keys[j];

        keys[i] = y.keys[t-1];

        n=n+1;
    }
}

class BTree {
    Node root;
    int t;

    public BTree(int t) {
        root = null;
        this.t = t;
    }

    public void traverse() {
        if(root != null)
            root.traverse();
    }

    public Node search(int k) {
        return (root == null) ? null : root.search(k);
    }

    public void insert(int k) {
        if(root == null) {
            root = new Node(t, true);
            root.keys[0] = k;
            root.n = 1;
        }
        else
        {
            if(root.n == 2*t-1) {
                Node s = new Node(t, false);
                s.childs[0] = root;
                s.splitChild(0,root);

                int i = 0;
                if(s.keys[0] < k) {
                    i++;
                }
                s.childs[i].insertNonFull(k);

                root = s;

            } else {
                root.insertNonFull(k);
            }
        }
    }
}
