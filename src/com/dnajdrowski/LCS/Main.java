package com.dnajdrowski.LCS;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Podaj pierszy ciąg: ");
        String x = s.nextLine();
        System.out.print("Podaj drugi ciąg: ");
        String y = s.nextLine();
        int[][] c = new int[x.length() + 1][y.length() + 1];
        String[][] b = new String[x.length() + 1][y.length() + 1];
        LCS_LENGTH(x, y, c, b);
        System.out.print("LCS = ");
        PrintLCS(x, y, b, x.length(), y.length());
    }

    public static void LCS_LENGTH(String x, String y, int[][] c, String[][] b) {
        int m = x.length();
        int n = y.length();

        for (int i = 0; i <= m; i++)
            c[i][0] = 0;
        for (int j = 0; j <= n; j++)
            c[0][j] = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (x.charAt(i-1) == y.charAt(j-1)) {
                    c[i][j] = c[i-1][j-1] + 1;
                    b[i][j] = "\\";
                } else {
                    if(c[i][j-1] > c[i-1][j]) {
                        c[i][j] = c[i][j-1];
                        b[i][j] = "-";
                    } else {
                        c[i][j] = c[i-1][j];
                        b[i][j] = "|";
                    }
                }
            }
        }
    }


    public static void PrintLCS(String x, String y, String[][] b, int i, int j) {
        if(i == 0 || j == 0) {
            return;
        }
        if(b[i][j].equals("\\")) {
            PrintLCS(x, y, b, i-1, j-1);
            System.out.print(x.charAt(i-1));
        }else if(b[i][j].equals("|"))
            PrintLCS(x, y, b, i-1, j);
        else
            PrintLCS(x, y, b, i, j-1);
    }
}