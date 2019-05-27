package com.dnajdrowski.KnuthMorrisPratt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static final int ALPHABET_SIZE = 128;
    private static final int PRIME_NUMBER = 27077;


    public static void main(String[] args) {
        int patternLen = arrayLenght("wzorzec.txt");
        int textLen = arrayLenght("tekst.txt");
        char[] pattern = new char[patternLen];
        char[] text = new char[textLen];

        loadArray("wzorzec.txt", pattern);
        loadArray("tekst.txt", text);

        long millisActualTime = System.currentTimeMillis();
        Simple(pattern, text);
        long executionTime = System.currentTimeMillis() - millisActualTime;
        System.out.println((double) executionTime / 1000 + " s");
        millisActualTime = System.currentTimeMillis();
        Rabin_Karp(pattern, text);
        executionTime = System.currentTimeMillis() - millisActualTime;
        System.out.println((double) executionTime / 1000 + " s");
        millisActualTime = System.currentTimeMillis() ;
        Knuth_Morris_Pratt(pattern, text);
        executionTime = System.currentTimeMillis()  - millisActualTime;
        System.out.println((double) executionTime / 1000 + " s");

    }

    private static int arrayLenght(String fileName) {
        int counter = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            char currentChar;
            int result;
            while ((result = bufferedReader.read()) != -1) {
                currentChar = (char) result;
                if (currentChar != '\n') {
                    counter++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Nie ma takiego pliku " + fileName + ".");
        } catch (IOException e) {
            System.out.println("Błąd przy w czytaniu z pliku " + fileName + ".");
        }
        return counter;
    }

    private static void loadArray(String fileName, char[] array) {
        int counter = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            char currentChar;
            int result;
            while ((result = bufferedReader.read()) != -1) {
                currentChar = (char) result;
                if (currentChar != '\n') {
                    array[counter] = currentChar;
                    counter++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Nie ma takiego pliku " + fileName + ".");
        } catch (IOException e) {
            System.out.println("Błąd przy w czytaniu z pliku " + fileName + ".");
        }
    }


    private static void Simple(char[] pattern, char[] text) {
        System.out.println("\nSimple algorithm:\n");

        boolean result = false;
        for (int s = 0; s <= text.length - pattern.length; s++) {
            for (int j = 0; j < pattern.length; j++) {
                if (!(pattern[j] == text[s + j]))
                    break;
                if (j == pattern.length - 1) {
                    System.out.println("Pattern found: starts on i = " + (s + 1) + ", ends on i = " + (s + pattern.length) + ".");
                    result = true;
                }
            }
        }
        if (!result) {
            System.out.println("Pattern not found.");
        }
    }

    private static void Rabin_Karp(char[] pattern, char[] text) {
        System.out.println("\nRabin-Karp algorithm:\n");
        boolean result = false;
        int h = 1, j, s;
        for(int i = 1; i < pattern.length; i++) {
            h = (h * ALPHABET_SIZE) % PRIME_NUMBER;
        }
        int p = 0;
        int t = 0;
        for(int i = 0; i < pattern.length; i++) {
            p = ((ALPHABET_SIZE * p) + pattern[i]) % PRIME_NUMBER;
            t = ((ALPHABET_SIZE * t) + text[i]) % PRIME_NUMBER;
        }
        for(s = 0; s <= text.length - pattern.length; s++) {
            if(p == t) {
                    for (j = 0; j < pattern.length; j++) {
                        if (!(pattern[j] == text[s + j])) {
                            break;
                        }
                    }
                    if(j == pattern.length) {
                        System.out.println("Pattern found: starts on i = " + (s + 1) + ", ends on i = " + (s + pattern.length) + ".");
                        result = true;
                    }
            }
            if(s < text.length - pattern.length) {
                int t1= (text[s] * h) % PRIME_NUMBER;
                if(t < t1) {
                    t += PRIME_NUMBER;
                }
                t = (ALPHABET_SIZE * (t - t1) + text[s + pattern.length]) % PRIME_NUMBER;
            }
        }
        if(!result) {
            System.out.println("Pattern not found.");
        }
    }

    private static void Knuth_Morris_Pratt(char[] pattern, char[] text) {
        System.out.println("\nKnuth-Morris-Pratt algorithm:\n");
        boolean result = false;
        int pi[] = prefixFunction(pattern);
        int q = 0;
        for (int i = 1; i <= text.length; i++) {
            while((q > 0) && (pattern[q] != text[i-1])) {
                q = pi[q];
            }
            q++;
            if (q == pattern.length) {
                System.out.println("Pattern found: starts on i = " + (i - pattern.length + 1) + ", ends on i = " + i + ".");
                q = pi[q];
                result = true;
            } else {
                if((i == text.length - 1) && (!result)) {
                    System.out.println("Pattern not found.");
                }
            }
        }

    }

    private static int[] prefixFunction(char[] pattern) {
        int[] pi = new int[pattern.length + 1];
        int k = -1;
        pi[0] = k;
        for(int q = 1; q < pattern.length; q++) {
            while ((k >= 0) && (pattern[k + 1] != pattern[q])) {
                k = pi[k];
            }
            k++;
            pi[q + 1] = k;
        }
        return pi;
    }
}




