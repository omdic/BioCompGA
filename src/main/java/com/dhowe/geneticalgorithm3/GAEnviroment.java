package com.dhowe.geneticalgorithm3;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author aphid
 */
public class GAEnviroment {

    /*
    This class describes the enviroment to perform a GA.
    
    The class contains 3 populations, the main, offspring and parent populations.
    
    The object is constructed with a population size, size of the genes and a mutation probability.
    
    Upon construction the 3 populations are initialised along with the generation of the 
    initial random population and updates the population information.
    
    The primary function of this class is to perform selectin, crossover and mutation on 
    the individuals of the main population. The parent and offspring populations are temporary 
    to each generation and are overwritten by each subsequent generation
    
    The best individual from each generation is copied into the next generation in the evolve method.
    
    By calling the evolve_enviroment() selection, crossover and mutation are performed
    on the current main population, to evolve the population call this method in a loop for the 
    desired number of generations.
    
    We always make new idividuals rather than copy them from one population to another.
    
    We also make temp populations and then set them to the required population once a fuction completes.
    
    I did this do avoid copy by reference problems.
     */
    // Populations used in GA process
    population main_population;
    population parent_population;
    population offspring_population;

    individual pop_best;

    Test_data test_data;

    float[][] solutions;
    int[] output;
    ;

    // Size of Populations, genes and Mutation probability
    int pop_size;
    int gene_size;
    int prob;

    public GAEnviroment(int pop_size, int gene_size, int prob) {

        // population initial properties.
        this.pop_size = pop_size;
        this.gene_size = gene_size;
        this.prob = prob;
        
        // get the data from the test_data object
        this.test_data = new Test_data();
        this.solutions = test_data.getSolution();
        this.output = test_data.getOutput();
        
        // initialise the populations
        this.main_population = new population(pop_size, gene_size , this.test_data);
        this.parent_population = new population(pop_size, gene_size, this.test_data);
        this.offspring_population = new population(pop_size, gene_size, this.test_data);

        // create an initial random main population,
        this.main_population.createRandomPopulation();
        this.main_population.update_population();

    }

    public individual getPop_best() {
        return pop_best;
    }

    /*
    Randomly select 2 Individuals and place the best into the parents population    
     */
    private void tournament_selection() {

        individual[] temp_pop = new individual[this.pop_size];

        for (int i = 0; i < this.pop_size; i++) {

            // pick 2 random ints to for indices of parents
            int parent1 = new Random().nextInt(this.pop_size);
            int parent2 = new Random().nextInt(this.pop_size);

            individual p1 = new individual(main_population.getPopulation()[parent1].getGenes(), solutions, output);
            individual p2 = new individual(main_population.getPopulation()[parent2].getGenes(), solutions, output);

            if (p1.getFitness() >= p2.getFitness()) {
                temp_pop[i] = p1;
            } else {
                temp_pop[i] = p2;
            }
        }
        this.main_population.setPopulation(temp_pop);
        this.main_population.update_population();
    }

    public void roulette_selection() {

        individual[] temp_pop = new individual[this.pop_size];

        final ArrayList<individual> wheel = createRouletteWheel();

        for (int i = 0; i < temp_pop.length; i++) {

            int a = new Random().nextInt(wheel.size());
            temp_pop[i] = wheel.get(a);
        }
        this.parent_population.setPopulation(temp_pop);
        this.parent_population.update_population();
    }

    public ArrayList<individual> createRouletteWheel() {

        double totalFitness = this.main_population.getPop_fitness();
        ArrayList<individual> rouletteWheel = new ArrayList<>();

        for (individual individual : this.main_population.getPopulation()) {
            long rouletteCount = (long) Math.ceil((individual.getFitness() / totalFitness) * 100);
            for (int i = 0; i < rouletteCount; i++) {
                rouletteWheel.add(individual);
            }
        }
        return rouletteWheel;
    }

    /*
        loop through the parents population and create 2 new children
        crossing over the tails of the genes of the parents. Then
        create the offspring population with the result       
     */
    private void crossover() {

        individual[] temp_pop = new individual[this.pop_size];

        for (int i = 0; i < pop_size; i++) { // go through population

            int a = new Random().nextInt(this.pop_size);
            int b = new Random().nextInt(this.pop_size);

            int split_point = new Random().nextInt(this.gene_size);

            float[] p1_genes = this.parent_population.getPopulation()[a].getGenes();
            float[] p2_genes = this.parent_population.getPopulation()[b].getGenes();

            float[] c1_genes = new float[gene_size];
            float[] c2_genes = new float[gene_size];

            for (int j = 0; j < gene_size; j++) {

                if (j < split_point) {
                    c1_genes[j] = p1_genes[j];
                    c2_genes[j] = p2_genes[j];
                } else {
                    c2_genes[j] = p1_genes[j];
                    c1_genes[j] = p2_genes[j];
                }
            }
            individual child1 = new individual(c1_genes, solutions, output);
            individual child2 = new individual(c2_genes, solutions, output);

            if (child1.getFitness() > child2.getFitness()) {
                temp_pop[i] = child1;
            } else {
                temp_pop[i] = child2;
            }
        }
        this.offspring_population.setPopulation(temp_pop);
        this.offspring_population.update_population();
    }

    /*
       Loop through the offspring population and based on the 
       mutation probability flip a gene, then add the new child to a temp_pop.
       finaly copy the new population to  become the new main_population   
     */
    private void mutate(int probability) {

        int multiplier = 1001;

        individual[] temp_pop = new individual[this.pop_size];

        for (int i = 0; i < this.pop_size; i++) {

            float temp_genes[] = this.offspring_population.getPopulation()[i].getGenes();

            // mutation can now mutate wild cards
            for (int j = 0; j < temp_genes.length; j++) {
                int k = new Random().nextInt(multiplier);

                if (k <= probability) {                    
                    temp_genes[j] = new Random().nextFloat(); // just mutate a new float
                }
            }
            individual child = new individual(temp_genes, solutions, output);
            temp_pop[i] = child;
        }
        this.main_population.setPopulation(temp_pop);
        this.main_population.update_population();
    }

    /*
        perfrom all population operations to evolve the solutions.
        
        first get the best individual of the population, next perform 
        tournament selection, then crossover, then mutation.
    
        finally replace the worst individual of the new population with the
        best from the old population
    
     */
    public void evolve_enviroment() {

        //    this.main_population.update_population();
        // get the indices of the best individual in the main population
        int main_pop_best = this.main_population.getBest_indices();

        // create a new individua with the genes of the best individual
        this.pop_best = new individual(this.main_population.getPopulation()[main_pop_best].getGenes(), solutions, output); // best individual in population

        // perform selection, crossover and mutation
        this.roulette_selection();
        this.crossover();
        this.mutate(this.prob);
        this.tournament_selection(); // survivor selection

        // find the indices of the worst individual and replace it with the best from the last generation
        this.main_population.setIndividual(pop_best, this.main_population.find_Worst_indicies());
        this.main_population.update_population();

    }

    public void printPopulations() {
        System.out.println(this.main_population.toString());
    }
}
