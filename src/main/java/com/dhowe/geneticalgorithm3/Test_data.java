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
    int num_of_records = 2000;

    float[][] solution;
    int[] output;
    String path = "C:\\Users\\aphid\\Documents\\uniThirdYear\\Biocomputation\\Assignment\\data3.txt";

    public Test_data() {

        this.output = new int[num_of_records];
        this.solution = new float[num_of_records][];

        try {
            this.readData();
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }

    }

    public void readData() throws IOException {

        Scanner fileScanner = new Scanner(new File(path));
        int j = 0;
        int[] out = new int[this.num_of_records];

        while (fileScanner.hasNext()) {

            String line = fileScanner.nextLine();
            String[] part = line.split("\\s+");

            float[] temp = new float[data_length];
            int i = 0;

            for (String s : part) {
                if (i == 6) {
                    break;
                }
                float a = (float) Float.parseFloat(s);
                temp[i] = a;
                i++;
            }

            this.solution[j] = temp;

            String o = part[6];
            if (o.contains("1")) {
                out[j] = 1;
            } else {
                out[j] = 0;
            }
            j++;
        }
        this.output = out;
    }

    public float[][] getSolution() {
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
        for (float[] is : solution) {
            System.out.println(Arrays.toString(is) + "  :  " + output[i]);
            i++;
        }
    }
}
