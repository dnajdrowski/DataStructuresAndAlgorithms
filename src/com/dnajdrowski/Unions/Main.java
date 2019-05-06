package com.dnajdrowski.Unions;

import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
//        UnionNode z1 = MakeSet(1);
//        UnionNode z2 = MakeSet(2);
//        UnionNode z3 = MakeSet(3);
//        UnionNode z4 = MakeSet(4);
//        UnionNode z5 = MakeSet(5);
//        UnionNode z6 = MakeSet(6);
//        UnionNode z7 = MakeSet(7);
//        UnionNode z8 = MakeSet(8);
//        UnionNode z9 = MakeSet(9);
//        Union(FindSet(z1), FindSet(z2));
//        Union(FindSet(z3), FindSet(z4));
//        Union(FindSet(z5), FindSet(z4));
//        Union(FindSet(z1), FindSet(z5));
//        Union(FindSet(z6), FindSet(z7));
//        Union(FindSet(z8), FindSet(z9));
//        Union(FindSet(z6), FindSet(z8));
//        Union(FindSet(z7), FindSet(z4));
//        PrintUnionNode(z1);

        Graph graph = new Graph(4,5);
        ArrayList<UnionNode> vertices = graph.getVertices();

        //dodanie 0-1
        graph.getEdges().get(0).setSrc(vertices.get(0));
        graph.getEdges().get(0).setDestination(vertices.get(1));
        graph.getEdges().get(0).setWeight(10);

        //dodanie 0-2
        graph.getEdges().get(1).setSrc(vertices.get(0));
        graph.getEdges().get(1).setDestination(vertices.get(2));
        graph.getEdges().get(1).setWeight(6);

        //dodanie 0-3
        graph.getEdges().get(2).setSrc(vertices.get(0));
        graph.getEdges().get(2).setDestination(vertices.get(3));
        graph.getEdges().get(2).setWeight(5);

        //dodanie 1-3
        graph.getEdges().get(3).setSrc(vertices.get(1));
        graph.getEdges().get(3).setDestination(vertices.get(3));
        graph.getEdges().get(3).setWeight(15);

        //dodanie 2-3
        graph.getEdges().get(4).setSrc(vertices.get(2));
        graph.getEdges().get(4).setDestination(vertices.get(3));
        graph.getEdges().get(4).setWeight(4);


        graph.KruskalAgorithm();

    }

    public static UnionNode MakeSet(int key) {
        UnionNode x = new UnionNode(key, 0);
        x.setParent(x);
        return x;
    }

    public static UnionNode FindSet(UnionNode x) {
        if( x != x.getParent()) {
            x.setParent(FindSet(x.getParent()));
        }
        return x.getParent();
    }

    public static void Union(UnionNode x, UnionNode y) {
        if( x.getRank() > y.getRank()) {
            y.setParent(x);
        } else {
            x.setParent(y);
            if(x.getRank() == y.getRank()) {
                y.setRank(y.getRank() + 1);
            }
        }
    }

    public static void PrintUnionNode(UnionNode node) {
        while(node != node.getParent()) {
            System.out.println(node);
            node = node.getParent();
        }
        System.out.println(node);

    }

}

class UnionNode {
    private int key;
    private UnionNode parent;
    private int rank;


    public UnionNode(int key, int rank) {
        this.key = key;
        this.rank = rank;
    }

    public int getKey() {
        return key;
    }

    public UnionNode getParent() {
        return parent;
    }

    public void setParent(UnionNode parent) {
        this.parent = parent;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return " " + getKey();
    }
}

class Graph {
    private int numberV; // liczba wierzchołkow
    private int numberE; // liczba krawedzi
    private ArrayList<Edge> edges;
    private ArrayList<UnionNode> vertices;

    public Graph(int numberV, int numberE) {
        this.numberV = numberV;
        this.numberE = numberE;
        edges = new ArrayList<>();
        vertices = new ArrayList<>();
        setEdges();
        setVertices();
    }


    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges() {
        for (int i = 0; i < numberE; i++) {
            edges.add(new Edge());
        }
    }

    public void setVertices() {
        for (int i = 0; i < numberV; i++) {
            vertices.add(Main.MakeSet(i));
        }
    }

    public ArrayList<UnionNode> getVertices() {
        return vertices;
    }

    public void KruskalAgorithm() {
        Collections.sort(edges);
        edges.forEach(edge -> {
            UnionNode ru = Main.FindSet(edge.getSrc());
            UnionNode rv = Main.FindSet(edge.getDestination());
            if (ru != rv) {
                System.out.println(edge);
                Main.Union(ru,rv);
            }
        });
    }
}

class Edge implements Comparable<Edge> {

    private UnionNode src;
    private UnionNode destination;
    private int weight;


    public Edge() {
    }

    public UnionNode getSrc() {
        return src;
    }

    public void setSrc(UnionNode src) {
        this.src = src;
    }

    public UnionNode getDestination() {
        return destination;
    }

    public void setDestination(UnionNode destination) {
        this.destination = destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge compareEdge) {
        return this.weight - compareEdge.getWeight();
    }

    @Override
    public String toString() {
        return this.getSrc().getKey() + " - - - - - - - - - " + this.getDestination().getKey() + " === " + this.getWeight();
    }
}







