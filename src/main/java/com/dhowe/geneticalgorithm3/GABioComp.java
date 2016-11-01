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

        try {
            // Main

            int max_fitness = 64;
            int bestpop = 0;
            int bestgen = 0;
            int pop_size = 100;
            int gene_size = 70; // fixed at this length for 10 solutions of 6 bits so make multiple of 6 and change individual
            int generations = 100;
            int prob = 45; // out of 1000 
            int target = pop_size * 64; // max fitness * pop_size

            String output = "";
            String graph_output = "";

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH,mm,ss");
            Calendar cal = Calendar.getInstance();
            String s = dateFormat.format(cal.getTime());

            output += ("GA run on " + s + " \n Solving dataset2" + System.lineSeparator());

            GAEnviroment GA = new GAEnviroment(pop_size, gene_size, prob);

            int i = 0;

            while (GA.main_population.getMax_fitness() < max_fitness) { // keep going until max fitness is founf
                //    for (int i = 0; i <= generations; i++) { // loop for i generations

                GA.evolve_enviroment();

                int max_fit = GA.main_population.getMax_fitness();
                float average = GA.main_population.getAverage_fitness();
                int pop_fit = GA.main_population.getPop_fitness();                
                
                output += ((i) + System.lineSeparator());
                
                System.out.println((i) + " , " + max_fit
                        + " , " + average);
                
                output += ("\t\tBest Fitness : " + max_fit + System.lineSeparator());
                output += ("\t\tAverage Fitness : " + average + System.lineSeparator());

                // keep a record of the best fitness and its generation
                if (bestpop < pop_fit) {
                    bestpop = pop_fit;
                    bestgen = i;
                }
                output += ("\t\tPopulation Fitness : " + pop_fit
                        + " Fitness Target : " + (target)
                        + " Population Fitness Percentage : " + ((float) (pop_fit * 100) / target) + "%" + System.lineSeparator());

                output += ("\t\tBest Population Fitness : " + bestpop + " Best Generation : " + bestgen + System.lineSeparator());

                output += (GA.main_population.printPopulation() + System.lineSeparator());
                output += (GA.main_population.toString() + System.lineSeparator());

                graph_output += (i + ", " + max_fit
                        + ", " + average
                        + ", " + pop_fit
                        + " ," + target + System.lineSeparator());

                i++;
            }
            
            System.out.println("Solved : " + GA.main_population.getPopulation()[GA.main_population.getBest_indices()]);
            
            PrintWriter graphWriter = new PrintWriter("C:\\Users\\aphid\\Documents\\uniThirdYear\\Biocomputation\\Assignment\\results\\dataset2\\GAgraph+output-" + s + ".csv", "UTF-8");

            try (PrintWriter writer = new PrintWriter("C:\\Users\\aphid\\Documents\\uniThirdYear\\Biocomputation\\Assignment\\results\\dataset2\\GAoutput-" + s + ".txt", "UTF-8")) {
                
                writer.print(output);
                graphWriter.print(graph_output);
                graphWriter.close();
                writer.close();
            }

        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(GABioComp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// Main
} // Class