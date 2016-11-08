package com.dhowe.geneticalgorithm3;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
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
        int runs = 10;

        for (int k = 0; k < runs; k++) {

            int max_fit, pop_fit;
            float average;
            int max_fitness = 120; // we train on 1200 data points to classify 800 unseen data points
            int pop_size = 100;
            int gene_size = 130; // this is for 6 pairs of values and a output so 13 genes per rule and we ar getting 10 rules.
            int prob = 25; // out of 1000 
            int max_gen = 5000;
            int target = max_fitness * pop_size;
            String graph_output = "";

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH,mm,ss");
            Calendar cal = Calendar.getInstance();
            String s = dateFormat.format(cal.getTime());
            s += "-run-" + k;

            GAEnviroment GA = new GAEnviroment(pop_size, gene_size, prob);

            int i = 0;

            try {
                graph_output += "Pop size " + pop_size + ",probability " + prob + " ,Gene size " + gene_size + ",dataset3 1200 train 800 test" + System.lineSeparator() + System.lineSeparator();
                graph_output += "GEN,BEST,AVERAGE,POP FIT,POP MAX FIT" + System.lineSeparator() + System.lineSeparator();

                while ((GA.main_population.getMax_fitness() < max_fitness) && (i <= max_gen)) { // keep going until max fitness is founf

                    GA.evolve_enviroment();

                    max_fit = GA.main_population.getMax_fitness();
                    average = (float) GA.main_population.getAverage_fitness();
                    pop_fit = GA.main_population.getPop_fitness();

                    System.out.println((i) + " , " + max_fit + " , " + average);

                    graph_output += (i + ", " + max_fit
                            + ", " + average
                            + ", " + pop_fit
                            + " ," + target + System.lineSeparator());

                    i++;
                }

                System.out.println("Solved : " + GA.main_population.getPopulation()[GA.main_population.getBest_indices()]);
                int classified = GA.main_population.getPopulation()[GA.main_population.getBest_indices()].check_rules();
                graph_output += System.lineSeparator() + "Classified : " + classified + "  solved : " + (float) ((classified * 100) / 800) + "%" + System.lineSeparator();
                graph_output += System.lineSeparator() + GA.main_population.getPopulation()[GA.main_population.getBest_indices()] + System.lineSeparator();
                System.out.println("Classified : " + classified + "  solved : " + (float) ((classified * 100) / 800) + "%");

                try (PrintWriter graphWriter = new PrintWriter("C:\\Users\\aphid\\Documents\\uniThirdYear\\Biocomputation\\Assignment\\results\\dataset3\\GAgraph+output-run" + s + ".csv", "UTF-8")) {

                    graphWriter.print(graph_output);
                    graphWriter.close();
                }

            } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                Logger.getLogger(GABioComp.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException ex) {
                Logger.getLogger(GABioComp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }// Main
} // Class
