package com.dnajdrowski.HuffmanCoding;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws  IOException{

        System.out.println("Please type a file path: ");
        String filePath = scanner.nextLine();
        String[] text = readFile(filePath).split("");

        ArrayList<HuffmanNode> nodes = new ArrayList<>();

        for(String c : text) {
            HuffmanNode node = new HuffmanNode(c);
            if(nodes.contains(node)) {
                nodes.get(nodes.indexOf(node)).incrementQuantity();
            } else {
                nodes.add(node);
            }
        }

        printCode(Huffman(nodes), "");

    }

    private static String readFile(String filePath) {
        String text = "";
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String textLine = bufferedReader.readLine();
            do {
                text +="" + textLine;
                textLine = bufferedReader.readLine();

            } while (textLine != null);
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.trim();
    }

    private static HuffmanNode Huffman(ArrayList<HuffmanNode> nodes) {
        int counter = 1;
        int size = nodes.size();
        for (int i = 2; i <= size; i++) {
            HuffmanNode x = ExtractMin(nodes);
            HuffmanNode y = ExtractMin(nodes);
            HuffmanNode z = new HuffmanNode("z" + counter, x.getQuantity() + y.getQuantity());
            counter++;
            z.setLeft(x);
            z.setRight(y);
            nodes.add(z);
        }
        return ExtractMin(nodes);
    }

    private static HuffmanNode ExtractMin(ArrayList<HuffmanNode> nodes) {
        HuffmanNode minNode = nodes.get(0);
        for(int i = 1; i < nodes.size(); i++) {
            if ((nodes.get(i).compareTo(minNode)) == -1) {
                minNode = nodes.get(i);
            }
        }
        nodes.remove(minNode);
        return minNode;
    }

    public static void printCode(HuffmanNode root, String s)
    {
        if (root.getLeft()
                == null
                && root.getRight()
                == null
                && !root.getC().isEmpty()) {
            System.out.println(root + " CODE = " + s);
            return;
        }
        printCode(root.getLeft(), s + "0");
        printCode(root.getRight(), s + "1");
    }
}


class HuffmanNode implements Comparable<HuffmanNode> {
    private int quantity;
    private final String c;
    private HuffmanNode right;
    private HuffmanNode left;

    public HuffmanNode(String c) {
        this.quantity = 1;
        this.c = c;
        this.right = null;
        this.left = null;
    }

    public HuffmanNode(String c, int quantity) {
        this.quantity = quantity;
        this.c = c;
        this.right = null;
        this.left = null;
    }

    public void incrementQuantity() {
        this.quantity += 1;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }



    public int getQuantity() {
        return quantity;
    }


    public String getC() {
        return c;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }
        String objChar = ((HuffmanNode) obj).getC();
        return this.c.equals(objChar);
    }


    @Override
    public int compareTo(HuffmanNode o) {
        if(this == o) {
            return 0;
        }

        if(o != null) {
            if(o.getQuantity() == this.quantity) {
                return 0;
            } else if(this.quantity > o.getQuantity()) {
                return 1;
            } else {
                return -1;
            }
        }

        throw new NullPointerException();
    }

    @Override
    public int hashCode() {
        return this.c.hashCode() + 30;
    }

    @Override
    public String toString() {
        return "Char '" + this.c + "' quantity : " + this.quantity + ".";
    }
}
