package com.dhowe.geneticalgorithm3;

import java.util.Random;

/**
 *
 * @author aphid
 */
public class individual {

    /*
    This class describes individuals in a GA population.
    
    Each Individual has the properties of an array of genes[] and a fitness.
    Upon creation an Individul clculates its fitness
    
    This particular Idividual uses a counting ones fitness function. 
        i.e its fitness is the number of {1}'s in its genes array
    
     */
    float[] genes; // array of genes will be a binary integers {0,1}
    int fitness; // fitness of the individual

    // test data for fitness function vars

    float[][] solutions;
    int[] output;

    float[][] attempt;
    int[] results;

    int num_of_rules = 10;

    public individual(float[] genes, float[][] sol, int[] out) { // param constructor
        
        this.genes = genes;
        //    this.countingOnesFitnessFunction();
        // these are size 10 as the gene size is 60 and the solutions are 6 bits, thus 10 solutions
        this.attempt = new float[num_of_rules][];
        this.results = new int[num_of_rules];
        this.solutions = sol;
        this.output = out; // output of each data variation
        this.splitgenes();

        this.fitnessFunction();
    }

    private void setGene(int ind, int val) {
        this.genes[ind] = val;
    }

    public float[] getGenes() {
        return genes;
    }

    public void setGenes(float[] genes) {
        this.genes = genes;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    /*
    
        split the length 60 genes into 10 arrays of 5 bits and another
        array with one bit.

        this coresponds to the solutions and output from the test_data
        and creates attempts and results arrays with the individuals set of solutions

     */
    private void splitgenes() { // need to convert this to split bare in mind the new structure
        
        int j = 0;
        
        float[][] att = new float[num_of_rules][];
        int[] sol = new int[num_of_rules];

        for (int i = 0; i < this.genes.length; i += 7) {

            float[] temp = new float[6];
            
            // split the genes
            temp[0] = this.genes[i];
            temp[1] = this.genes[i + 1];
            temp[2] = this.genes[i + 2];
            temp[3] = this.genes[i + 3];
            temp[4] = this.genes[i + 4];
            temp[5] = this.genes[i + 5];

            // set the output bit to 1 or 0
            if (this.genes[i + 6] <= 1) {
                sol[j] = (int)this.genes[i + 6];
            } else {
                int a = new Random().nextInt(2);
                sol[j] = a;
                this.genes[i + 6] = a;
            }
            att[j] = temp;
            j++;
        }
        this.attempt = att;
        this.results = sol;
    }

    /*
        counting ones fitness function
     */
    public final void countingOnesFitnessFunction() {

        int newFitness = 0;
        for (int j = 0; j < this.genes.length; j++) {
            if (this.genes[j] == 1) {
                newFitness++;
            }
        }
        this.setFitness(newFitness);
    }

    /*    
        loop through each attempt and compare it to all solutions, compare 
        coresponding result to output, mark solution as solved. return number of solutions 
        solved upto a max of 32    
     */
    public final void fitnessFunction() {

        int newFitness = 0;

        for (int i = 0; i < solutions.length; i++) { // each solution

            for (int j = 0; j < attempt.length; j++) { // each attempt  

                if (compare_to_data(attempt[j], solutions[i])) {
                    if (output[i] == results[j]) { 
                        newFitness++;
                    }
                break; // found a matching rule break
                }
            }
        }
        this.setFitness(newFitness);
    }

    /*
     using this method instead of the java compare so I ca
     include wild cards and limit the munber of wild cards
     per solution    
     */
    private boolean compare_to_data(float[] attempt, float[] solution) {

        int match = 0;
        for (int i = 0; i < attempt.length; i++) {
            if ((attempt[i] == solution[i]) || (attempt[i] == 2)) {                             
                match++;
            }
        }
        return (match == attempt.length);
    }

    /*
        prints genes with {2} displayed as {#} to make solutions easier
        to read
     */
    private String print_genes() {

        String s = "{\n";   
        int i = 1;
        for (float gene : genes) {                 
            if ((i % 7 == 1) && (i != 1)) {
                s += "\n";
            }
            if (gene == 2) {
                s += "#, ";
            } else {
                s += gene + ", ";
            }
            i++;
        }
        s += "}";
        return s;
    }

    @Override
    public String toString() {
        return "genes=" + print_genes() + "\n fitness=" + fitness + '}';
    }
}
