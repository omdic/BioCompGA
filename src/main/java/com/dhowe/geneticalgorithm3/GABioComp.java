package com.dhowe.geneticalgorithm3;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author aphid
 */
public class GABioComp { // Class

    /*
    
    This class initilises the GA with initial params and prints the results 
    of the procss
     */
    public static void main(String[] args) {

        // Main
        int runs = 5;
        String header_output = "";
        String best_output = "Best Fitness" + System.lineSeparator();
        String average_output = "Population Average" + System.lineSeparator();
        String popfit_output = "Population Fitness" + System.lineSeparator();
        String classification_output = "Classification Performance" + System.lineSeparator();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy@HH-mm");
        Calendar cal = Calendar.getInstance();
        String s = dateFormat.format(cal.getTime());
        for (int k = 0; k < runs; k++) {

            int num_rules = 5;
            int max_fit, pop_fit;
            float average;
            int max_fitness = 1190; // we train on 1200 data points to classify 800 unseen data points
            int pop_size = 200;
            int gene_size = 13 * num_rules; // this is for 6 pairs of values and a output so 13 genes per rule and we ar getting 10 rules.
            float prob = (float)0.03; // out of 1000 
            int max_gen = 5000;
            //           int target = max_fitness * pop_size;

            best_output += k + " run" + System.lineSeparator();
            average_output += k + " run" + System.lineSeparator();
            popfit_output += k + " run" + System.lineSeparator();

            GAEnviroment GA = new GAEnviroment(pop_size, gene_size, prob);

            int i = 0;

            header_output = "Pop size " + pop_size + ",probability " + prob + " ,Gene size " + gene_size + ",dataset3 1200 train 800 test" + System.lineSeparator() + System.lineSeparator();

            while ((GA.main_population.getMax_fitness() < max_fitness) && (i <= max_gen)) { // keep going until max fitness is founf

                GA.evolve_enviroment();

                max_fit = GA.main_population.getMax_fitness();
                average = (float) GA.main_population.getAverage_fitness();
                pop_fit = GA.main_population.getPop_fitness();

                System.out.println((k) + " , " + (i) + " , " + max_fit + " , " + average);

                best_output += max_fit + "," + System.lineSeparator();
                average_output += average + "," + System.lineSeparator();
                popfit_output += pop_fit + "," + System.lineSeparator();

                i++;
            }

            best_output += System.lineSeparator();
            average_output += System.lineSeparator();
            popfit_output += System.lineSeparator();

            System.out.println("Solved : " + GA.main_population.getPopulation()[GA.main_population.getBest_indices()]);
            int classified = GA.main_population.getPopulation()[GA.main_population.getBest_indices()].check_rules();
            classification_output += k + System.lineSeparator() + " ," + classified + " ,";
            classification_output += System.lineSeparator() + GA.main_population.getPopulation()[GA.main_population.getBest_indices()] + System.lineSeparator();
            System.out.println("Classified : " + classified + "  solved : " + (float) ((classified * 100) / 800) + "%");
        }
        try (PrintWriter bestWriter = new PrintWriter("C:\\Users\\aphid\\Documents\\uniThirdYear\\Biocomputation\\Assignment\\results\\dataset3\\run_output_best_" + s + ".csv", "UTF-8")) {
            PrintWriter averageWriter = new PrintWriter("C:\\Users\\aphid\\Documents\\uniThirdYear\\Biocomputation\\Assignment\\results\\dataset3\\run_output_average_" + s + ".csv", "UTF-8");
            PrintWriter popfitWriter = new PrintWriter("C:\\Users\\aphid\\Documents\\uniThirdYear\\Biocomputation\\Assignment\\results\\dataset3\\run_output_popfit_" + s + ".csv", "UTF-8");
            PrintWriter classWriter = new PrintWriter("C:\\Users\\aphid\\Documents\\uniThirdYear\\Biocomputation\\Assignment\\results\\dataset3\\run_output_class_" + s + ".csv", "UTF-8");

            classWriter.print(header_output);
            classWriter.print(classification_output);
            classWriter.close();
            popfitWriter.print(header_output);
            popfitWriter.print(popfit_output);
            popfitWriter.close();
            averageWriter.print(header_output);
            averageWriter.print(average_output);
            averageWriter.close();
            bestWriter.print(header_output);
            bestWriter.print(best_output);
            bestWriter.close();

        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(GABioComp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }// Main

}// Class

