package com.dhowe.geneticalgorithm3;

import java.util.Arrays;

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
    int classified;

    // test data for fitness function vars
    float[][] solutions;
    int[] output;

    int train_num = 800;
    int test_num = 1200;

    float[][] lower;
    float[][] upper;
    int[] results;

    int num_of_rules = 10; // number of rules (num0fgenes / 13)

    public individual(float[] genes, float[][] sol, int[] out) { // param constructor

        this.genes = genes;
        this.solutions = sol; // solutions from dataset
        this.output = out; // outputs from dataset

        this.lower = new float[num_of_rules][];
        this.upper = new float[num_of_rules][];
        this.results = new int[num_of_rules];

        this.splitgenes();

        this.fitnessFunction();
    }

    public int getClassified() {
        return classified;
    }

    public void setClassified(int classified) {
        this.classified = classified;
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

        float[][] low = new float[num_of_rules][];
        float[][] up = new float[num_of_rules][];
        int[] sol = new int[num_of_rules];

        for (int i = 0; i < this.genes.length; i += 13) {

            float[] lower_temp = new float[6];
            float[] upper_temp = new float[6];

            // split the genes
            lower_temp[0] = this.genes[i];
            upper_temp[0] = this.genes[i + 1];

            lower_temp[1] = this.genes[i + 2];
            upper_temp[1] = this.genes[i + 3];

            lower_temp[2] = this.genes[i + 4];
            upper_temp[2] = this.genes[i + 5];

            lower_temp[3] = this.genes[i + 6];
            upper_temp[3] = this.genes[i + 7];

            lower_temp[4] = this.genes[i + 8];
            upper_temp[4] = this.genes[i + 9];

            lower_temp[5] = this.genes[i + 10];
            upper_temp[5] = this.genes[i + 11];

            // set the output bit to 1 or 0
            if (Math.round(this.genes[i + 12]) == 1) {
                float b = (float) 1;
                sol[j] = 1;
            } else {
                float a = (float) 0;
                sol[j] = 0;
            }
            low[j] = lower_temp;
            up[j] = upper_temp;

            j++;
        }
        this.lower = low;
        this.upper = up;
        this.results = sol;
    }

    /*    
        loop through each attempt and compare it to all solutions, compare 
        coresponding result to output, mark solution as solved. return number of solutions 
        solved upto a max of 32    
     */
    public final void fitnessFunction() {

        int newFitness = 0;

        for (int i = 0; i < train_num; i++) { // each solution in training set

            for (int j = 0; j < num_of_rules; j++) { // each attempt  

                if (compare_to_data(lower[j], upper[j], solutions[i])) {
                    if (output[i] == results[j]) {
                        newFitness++;
                    }
                    break; // found a matching rule break
                }
            }
        }
        this.setFitness(newFitness);
    }

    public int check_rules() {

        int cla = 0;

        for (int i = train_num + 1; i < solutions.length; i++) { // each solution in training set

            for (int j = 0; j < num_of_rules; j++) { // each attempt  

                if (compare_to_data(lower[j], upper[j], solutions[i])) {
                    if (output[i] == results[j]) {
                        cla++;
                    }
                    break; // found a matching rule break
                }
            }
        }
        this.setClassified(cla);
        return cla;
    }

    /*
       
     */
    private boolean compare_to_data(float[] lower, float[] upper, float[] guess) {

        int match = 0;
        for (int i = 0; i < lower.length; i++) {
            if (lower[i] >= upper[i]) { //if the lower bound is greater than the upper break
                return false;
            }
            if ((guess[i] >= lower[i]) && (guess[i] <= upper[i])) {
                match++;
            }
        }
        return (match == lower.length);
    }

    @Override
    public String toString() {
        String s = "genes=" + Arrays.toString(genes) + "\n fitness=" + fitness + '}' + "\n";

        for (int i = 0; i < lower.length; i++) {
            s += "Lower bounds : " + Arrays.toString(lower[i]) + "\t";
            s += "Output : " + results[i] + "\n";
            s += "Upper bounds : " + Arrays.toString(upper[i]) + "\n\n";

        }
        return s;
    }
}
