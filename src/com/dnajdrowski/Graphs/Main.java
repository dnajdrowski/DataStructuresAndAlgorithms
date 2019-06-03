package com.dnajdrowski.Graphs;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

    }

    public static void DFS(Graph graph) {
        for(Vertex vertex : graph.getVertexes()) {
            vertex.setColor("white");
            vertex.setPi(null);
        }
        for(Vertex vertex : graph.getVertexes()) {
            if(vertex.getColor().equalsIgnoreCase("white")) {
                DFS(graph, vertex);
            }
        }
    }

    public static void DFS(Graph graph, Vertex u) {
        u.setColor("grey");
        for(Vertex vertex : u.getVertexes()) {
            if(vertex.getColor().equalsIgnoreCase("white")) {
                vertex.pi = u;
                DFS(graph, vertex);
            }
        }
    }

    public static void BFS(Graph graph) {
        for(Vertex vertex : graph.getVertexes()) {
            vertex.setColor("white");
            vertex.setPi(null);
        }
        for(Vertex vertex : graph.getVertexes()) {
            if(vertex.getColor().equalsIgnoreCase("white")) {
                BFS(graph, vertex);
            }
        }
    }

    public static void BFS(Graph graph, Vertex s) {
        s.setColor("grey");

    }
}


class Graph {
    ArrayList<Vertex> vertexes;

    public Graph() {
    }

    public ArrayList<Vertex> getVertexes() {
        return vertexes;
    }

    public void setVertexes(ArrayList<Vertex> vertexes) {
        this.vertexes = vertexes;
    }
}

class Vertex {
    ArrayList<Vertex> vertexes;
    String color;
    Vertex pi;

    public Vertex() {
        vertexes = new ArrayList<>();
    }

    public ArrayList<Vertex> getVertexes() {
        return vertexes;
    }

    public void setVertexes(ArrayList<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Vertex getPi() {
        return pi;
    }

    public void setPi(Vertex pi) {
        this.pi = pi;
    }
}