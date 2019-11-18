import java.util.Random;

// @author: Sapan Sharma
// Student ID: 6352819

// it will calculate random search among the search space of particle*iteration of PSO
public class randomSearch {
    int iterations, dimension;
    double[] position;
    double fitness;
    double min = -5.12, max = 5.12;
    PersonalBest bestInRandom;
    Random rnd;

    randomSearch(int dimension, Random rnd, int iterations) {
        // since it will get random seed will produce same result as other particles
        this.dimension = dimension;
        this.position = new double[dimension];
        this.rnd = rnd;
        this.iterations = iterations;
        bestInRandom = new PersonalBest(new double[dimension], Double.MAX_VALUE);
    }

    // calculates random postions in each dimension
    // then calculates fitness of formed particle
    // then updates the personal best
    // then returns the best fitness in the random search space
    double performSearch() {

        int i = 1;
        while (i <= iterations) {
            for (int j = 0; j < dimension; j++) {
                position[j] = ((rnd.nextDouble()*(max - min)) + min);
            }
            fitness = 0;
            for (int j = 0; j < dimension; j++) {
                fitness += ((position[j]*position[j]) - (10*Math.cos(2*Math.PI*(position[j]))));
            }
            fitness = fitness + (10*position.length);
            if (bestInRandom.getBestFitness() > fitness){
                bestInRandom.setBestFitness(fitness);
                bestInRandom.setBestPosition(position);
            }
            i++;
        }
        return bestInRandom.getBestFitness();
    }
}