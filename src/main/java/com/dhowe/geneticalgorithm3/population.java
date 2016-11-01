package com.dhowe.geneticalgorithm3;

import java.util.Random;

/**
 *
 * @author aphid
 */
public final class population {

    /*   
    This class describes a GA population.
    
    A Population has a population size and a gene size for each individul
    
    A Population object knows its Max fitness, Overall Fitness and average fitness.
    
    It also has a best and worst indices and an array of individuals 
    
    A population can also be created at random using the create_random_population() method.
    
    A population has an update method that calculates the properties of the Population and should be run after 
    each generation.
     */
    // all population size and the size of the genes for each individual
    int pop_size;
    int gene_size;

    // populations max and averagve fitness vars
    int max_fitness = 0;
    int pop_fitness = 0;
    float average_fitness = 0;

    // indices of best and worst individual in rach population
    int best_indices = 0;
    int worst_indicies = 0;

    individual[] population; // create an array of individuals

    Test_data test_data;

    int[][] solutions;
    int[] output;

    ;

    public population(int pop_size, int gene_size) {
        this.test_data = new Test_data();
        this.pop_size = pop_size;
        this.gene_size = gene_size;
        this.population = new individual[pop_size];
        this.solutions = test_data.getSolution();
        this.output = test_data.getOutput();
    }

    // GETTERS & SETTERS
    public int getPop_size() {
        return pop_size;
    }

    public void setPop_size(int pop_size) {
        this.pop_size = pop_size;
    }

    public int getGene_size() {
        return gene_size;
    }

    public void setGene_size(int gene_size) {
        this.gene_size = gene_size;
    }

    public int getMax_fitness() {
        return max_fitness;
    }

    public void setMax_fitness(int max_fitness) {
        this.max_fitness = max_fitness;
    }

    public int getPop_fitness() {
        return pop_fitness;
    }

    public void setPop_fitness(int pop_fitness) {
        this.pop_fitness = pop_fitness;
    }

    public float getAverage_fitness() {
        return average_fitness;
    }

    public void setAverage_fitness(float average_fitness) {
        this.average_fitness = average_fitness;
    }

    public int getBest_indices() {
        return best_indices;
    }

    public void setBest_indices(int best_indices) {
        this.best_indices = best_indices;
    }

    public int getWorst_indicies() {
        return worst_indicies;
    }

    public void setWorst_indicies(int worst_indicies) {
        this.worst_indicies = worst_indicies;
    }

    public individual[] getPopulation() {
        return population;
    }

    public void setPopulation(individual[] population) {
        this.population = population;
    }

    public void setIndividual(individual ind, int i) {
        this.population[i] = ind;
    }

    //////////////////////////////////////////////////////////
    // Population properties methods
    private int find_Max_fitness() {

        int temp = 0;
        for (individual i : this.population) {
            if (i.getFitness() >= temp) {
                temp = i.getFitness();
            }
        }
        this.max_fitness = temp;
        return temp;
    }

    private float find_Average_fitness() {

        int sum = 0;
        int size = this.pop_size;

        for (individual p : this.population) {
            sum += p.getFitness();
        }
        float average = sum / size;
        this.average_fitness = average;
        return average;
    }

    private int find_Best_indices() {

        int j = 0;
        int temp = 0;
        int max = 0;
        for (individual i : this.population) {
            if (i.getFitness() >= max) {
                max = i.getFitness();
                temp = j;
            }
            j++;
        }
        this.best_indices = temp;
        return temp;
    }

    public int find_Worst_indicies() {
        int j = 0;
        int temp = 0;
        int min = 99999999;
        for (individual i : this.population) {
            if (i.getFitness() <= min) {
                min = i.getFitness();
                temp = j;
            }
            j++;
        }
        this.worst_indicies = temp;
        return temp;
    }

    private int find_Pop_fitness() {
        int temp = 0;
        for (individual i : population) {
            temp += i.getFitness();
        }
        this.pop_fitness = temp;
        return temp;
    }

    public void createRandomPopulation() {

        for (int i = 0; i < this.pop_size; i++) { // generate the population   

            int[] new_gene_array = new int[this.gene_size]; // create an array of ints to use as individual param            

            for (int j = 0; j < this.gene_size; j++) {// generate genes randomly

                new_gene_array[j] = new Random().nextInt(3); // add a third random number to act as wild card in this case {2}

            }
            individual tempIndv = new individual(new_gene_array, solutions, output);    // create temp individual to add to population
            this.population[i] = tempIndv;            // add the individual to the population array 
        }
        this.update_population();
    }

    public void update_population() {

        int temp_max = 0;
        int sum = 0;
        int j = 0;
        int best_ind_temp = 0;
        int best_ind_max = 0;
        int min_ind_temp = 0;
        int min = 99999999;

        for (individual i : this.population) {

            sum += i.getFitness();

            if (i.getFitness() >= temp_max) {
                temp_max = i.getFitness();
            }

            if (i.getFitness() >= best_ind_max) {
                best_ind_max = i.getFitness();
                best_ind_temp = j;
            }
            if (i.getFitness() <= min) {
                min = i.getFitness();
                min_ind_temp = j;
            }
            j++;
        }
        float average = sum / this.pop_size;

        this.max_fitness = temp_max;
        this.pop_fitness = sum;
        this.average_fitness = average;
        this.best_indices = best_ind_temp;
        this.worst_indicies = min_ind_temp;

    }

    public String printPopulation() { // print out population for debugging   

        int i = 0;
        String s = "";

        for (individual x : this.population) {
            s += "\t\t\tIndividual  " + i + " : " + x.toString() + System.lineSeparator();
            i++;
        }
        return s;
    }

    @Override
    public String toString() {
        //  this.printPopulation(); // uncomment to print the individuals in a population
        return "population{" + "pop_size=" + pop_size + ", gene_size=" + gene_size + ", max_fitness=" + max_fitness + ", pop_fitness=" + pop_fitness + ", average_fitness=" + average_fitness + ", best_indices=" + best_indices + ", worst_indicies=" + worst_indicies + '}' + "\n";
    }
} // class
