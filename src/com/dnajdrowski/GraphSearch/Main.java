package com.dnajdrowski.GraphSearch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Main {

    private static final String FILEPATH = "matrix.txt";

    public static void main(String[] args) throws IOException {

        Graph graph = setGraph(getMatrix());
        graph.DFS(3);
        System.out.println("\n\n\n\n");
        graph.BFS(5);
        System.out.println("\n\n\n\n");
        graph.print();
    }

    private static Graph setGraph(int[][] matrix) throws IOException {
        int matrixSize = getSize();
        Graph graph = new Graph(matrixSize + 1);
        for(int i = 0; i < matrixSize; i++) {
            for(int j = 0; j < matrixSize; j++) {
                if(matrix[i][j] == 1) {
                    graph.addEdge(i + 1, j + 1);
                }
            }
        }
        return graph;
    }

    private static int[][] getMatrix() throws IOException {

        int matrixSize = getSize();
        int j = 0;
        int[][] matrix = new int[matrixSize][matrixSize];
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(FILEPATH))) {
            String line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            while (line != null) {
                for(int i = 0; i < matrixSize; i++) {
                    matrix[i][j] = Character.getNumericValue(line.charAt(i));
                }
                j++;
                line = bufferedReader.readLine();
            }
        }
        return matrix;
    }

    private static int getSize() throws IOException {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(FILEPATH))) {
            return Integer.parseInt(bufferedReader.readLine());
        }
    }
}

class Graph {
    private int V;
    private ArrayList<ArrayList<Integer>> adj;

    public Graph(int v) {
        V = v;
        adj = new ArrayList<>();
        for(int i = 0; i < v; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int v, int w) {
        adj.get(v).add(w);
    }

    public void DFS(int v, boolean visited[], StringBuilder sb) {
        visited[v] = true;
        System.out.println(v + "");
        Iterator<Integer> integerIterator = adj.get(v).listIterator();

        int i = 0;
        while(integerIterator.hasNext()) {
            i++;
            int n = integerIterator.next();
            if(!visited[n]) {
                sb.append("[ " + v + "," + n + " ] ");
                DFS(n, visited, sb);
            }
        }
    }

    public void DFS(int v) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean visited[] = new boolean[V];
        DFS(v, visited, stringBuilder);
        System.out.println("\n\n\n" + stringBuilder.toString());
    }


    public void BFS(int v) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean visited[] = new boolean[V];
        LinkedList<Integer> que = new LinkedList<>();
        visited[v] = true;

        que.add(v);

        while (que.size() != 0) {
            v = que.poll();
            System.out.println(v + "");
            Iterator<Integer> i = adj.get(v).listIterator();
            while (i.hasNext())
            {
                int n = i.next();
                if (!visited[n])
                {
                    visited[n] = true;
                    que.add(n);
                    stringBuilder.append("[ " + v + ", " + n + " ] ");
                }
            }
        }
        System.out.println("\n\n\n" + stringBuilder.toString());
    }

    public void print() {
        for (int i = 1; i < adj.size(); i++) {
            System.out.println(i + ": ");
            for(int j = 0; j < adj.get(i).size(); j++) {
                System.out.println(adj.get(i).get(j) + ", ");
            }
            System.out.println();
        }
    }

}

