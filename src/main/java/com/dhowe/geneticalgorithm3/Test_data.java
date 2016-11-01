/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhowe.geneticalgorithm3;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author aphid
 */
public final class Test_data {

    int data_length = 6;
        
    int[][] solution;
    int[] output;
    String path = "C:\\Users\\aphid\\Documents\\uniThirdYear\\Biocomputation\\Assignment\\data2.txt";

    public Test_data() {

        this.output = new int[64];
        this.solution = new int[64][];

        try {
            this.readData();
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }

    }

    public void readData() throws IOException {

        Scanner fileScanner = new Scanner(new File(path));
        int j = 0;

        while (fileScanner.hasNext()) {

            String line = fileScanner.nextLine();
            int[] temp = new int[data_length];

            for (int i = 0; i < data_length; i++) {
                char s = line.charAt(i);
                if (s == '1') {
                    temp[i] = 1;
                } else {
                    temp[i] = 0;
                }
            }
            this.solution[j] = temp;

            char o = line.charAt(7);
            if (o == '1') {
                this.output[j] = 1;
            } else {
                 this.output[j] = 0;
            }
            j++;
        }

    }

    public int[][] getSolution() {
        return solution;
    }

    public int[] getOutput() {
        return output;
    }

    public int getData_length() {
        return data_length;
    }

    public void printSolutions() {
        
        int i = 0;
        for (int[] is : solution) {
            System.out.println(Arrays.toString(is)+ "  :  " + output[i] );
            i++;
        }
    }
}
