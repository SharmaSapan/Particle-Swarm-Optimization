import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

// @author: Sapan Sharma
// Student ID: 6352819
// This class includes the main function
// Run this class to run the program
// Enter your parameters and rastrigin function will be evaluated using PSO and random search
// The files will print best fitness of each particle and swarm. The random best file will contain random search result
// Particle Swarm Optimization
public class Swarm {

    int swarmSize, maxGen, dimension;
    double inertiaWeight, cognitiveValue, socialValue;
    double[] bestOverall; // to hold the global best position
    PersonalBest swarmPersonalBest; // to store values of global best values of fitness and postion
    Particle[] particles; // to initialize each particle in the swarm of size swarmSize by user
    boolean check = true; // to make boundary constraints

    public void setParameters() throws IOException {
        // takes the input from user through console
        Scanner sc = new Scanner(System.in);
        System.out.println("Input the swarm size: ");
        swarmSize = sc.nextInt();
        System.out.println("Input the maximum generation span: ");
        maxGen = sc.nextInt();
        System.out.println("Input the inertia weight: ");
        inertiaWeight = sc.nextDouble();
        System.out.println("Input the cognitive acceleration coefficient: ");
        cognitiveValue = sc.nextDouble();
        System.out.println("Input the social acceleration coefficient: ");
        socialValue = sc.nextDouble();
        System.out.println("Enter the dimension of the Problem: ");
        dimension = sc.nextInt();
        System.out.println("Your Selections: " + swarmSize + " " + maxGen + " " + inertiaWeight + " " +
                cognitiveValue + " " + socialValue);
    }

//    Boolean validPosition() {
//
//    }

    // creates the particles of swarmSize and initialize them with dimension of the probelm and random seed for calc
    public void particleGenerator(int swarmSize, int dimension, Random rnd) {
        particles = new Particle[swarmSize];
        for (int i = 0; i < particles.length; i++) {
           particles[i] = new Particle(dimension, rnd);
        }
    }

    // to evaluate each particle j's fitness stored locally in its own particle class
    public void evaluateRastriginFitness(Particle j) {
        j.calcrFitness();
    }

    public void evaluateSphereFitness(Particle j) { j.calcSphereFitness();}

    // this calculates the velocity of each particle after each iteration
    public void calculateVelocity(int dimension, Particle j, Random rnd) {
        // initialization of temp array to hold velocity
        double[] tempVel = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            tempVel[i] = j.getVelocity()[i];
        }

        // to calculate velocity using the given formula
        for (int i = 0; i < dimension; i++) {

            tempVel[i] = (inertiaWeight*tempVel[i]) +
                (cognitiveValue*(rnd.nextDouble())*((double)(Array.get(j.bestSoFar.getBestPosition(), i)) -
                        ((double)(Array.get(j.getCurrentPosition(), i))))) +
                (socialValue *(rnd.nextDouble())*((double)(Array.get(swarmPersonalBest.getBestPosition(), i)) -
                        ((double)(Array.get(j.getCurrentPosition(), i)))));
        }

        // velocity clamping if above +- velocity max change to velocity max +-
//        for (int i = 0; i < dimension; i++) {
//            if (tempVel[i] > (j.MAXVELOCITY)) {
//                tempVel[i] = j.MAXVELOCITY;
//            }
//
//            if (tempVel[i] < (-(j.MAXVELOCITY))) {
//                tempVel[i] = (-(j.MAXVELOCITY));
//            }
//        }
       // sets the velocity of each particle to the calculated one
        j.setVelocity(tempVel);
    }

    // it updates the position of the particle j with new velocity
    public void updateParticlePosition(int dimension, Particle j) {
        // to initialize, cannot copy an array to an array perfectly in java
        double[] tempPosition = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            tempPosition[i] = j.getCurrentPosition()[i];
        }

        for (int i = 0; i < dimension; i++) {
            tempPosition[i] = tempPosition[i] + (double)(Array.get(j.getVelocity(), i));
        }
        j.updateCurrentPosition(tempPosition);
    }

    // main function which has the PSO algorithm
    public static void main(String[] args) throws IOException {

        // to initialize swarm object to be used in main class
        Swarm min = new Swarm();
        // to assign random seed value
        Random rnd = new Random(1000);
        // change file name according to user directory
        String fileName = "C:\\Users\\Sapan\\Downloads\\Lectures\\Artificial Intelligence\\Assign3\\src\\swarmBest4.txt";
        FileWriter writer = new FileWriter(fileName);
        BufferedWriter toFile = new BufferedWriter(writer);
        String fileName1 = "C:\\Users\\Sapan\\Downloads\\Lectures\\Artificial Intelligence\\Assign3\\src\\particleBest4.txt";
        FileWriter w = new FileWriter(fileName1);
        BufferedWriter toFile1 = new BufferedWriter(w);

        // Parameters from the users are stored locally in min object
        min.setParameters();

        // to perform random search
        String fileName2 = "C:\\Users\\Sapan\\Downloads\\Lectures\\Artificial Intelligence\\Assign3\\src\\randomBest1.txt";
        FileWriter wr = new FileWriter(fileName2);
        BufferedWriter toFile2 = new BufferedWriter(wr);
        int iterations = min.swarmSize * min.maxGen;
        randomSearch rS = new randomSearch(min.dimension, rnd, iterations);
        double randomSear = rS.performSearch();
        System.out.println("Best random search fitness: "+randomSear);
        toFile2.write(Double.toString(randomSear));

        // Vanilla PSO algorithm

        System.out.println("Particle Swarm Optimization is running....");
        min.particleGenerator(min.swarmSize, min.dimension, rnd);
        //initialize swarm personal best with dimensions given and max value that will be updated at the first particle
        min.swarmPersonalBest = new PersonalBest(new double[min.dimension], Double.MAX_VALUE );
        int i = 1;
        // runs until fitness value reaches 0 or max iterations reached
        while(i <= min.maxGen && min.swarmPersonalBest.getBestFitness() != 0 ) {
            for (Particle j : min.particles) {
                // it evaluates the fitness as well as update each particle's best position so far
                min.evaluateRastriginFitness(j);
             // remove comments below to calculate sphere fitness and change min and max values to +- infinity
             // add comments to line 138 as well to calculate sphere fitness
                //min.evaluateSphereFitness(j);
            }

            for (Particle j : min.particles) {
                // for all particles if best fitness is less than swarm best so far, update swarm best
                // to check if the position is not outside the max min to establish boundary constraints
                for (int x = 0; x < min.dimension; x++) {
                    if ((double)(Array.get(j.getCurrentPosition(), x)) > j.max ||
                            (double)(Array.get(j.getCurrentPosition(), x)) < j.min)
                        min.check = false;
                }
                // if not outside the boundary update best so far
                if (min.check) { // if true run
                    if (min.swarmPersonalBest.getBestFitness() > j.bestSoFar.getBestFitness()) {
                        min.swarmPersonalBest.setBestFitness(j.bestSoFar.getBestFitness());
                        min.swarmPersonalBest.setBestPosition(j.bestSoFar.getBestPosition());
                    }
                }
                min.check = true; // changes to true so that the next iteration of particle gets checked again
            }

            for (Particle j : min.particles) {

                min.calculateVelocity(min.dimension, j, rnd);
                min.updateParticlePosition(min.dimension, j);
                toFile1.newLine();
                toFile1.write(Double.toString(j.bestSoFar.getBestFitness()));
                toFile1.newLine();
                // remove comments below to print the best position of each particle as well after each iteration
//                toFile1.write(Arrays.toString(j.bestSoFar.getBestPosition()));
//                toFile1.newLine();

            }

            i++;
            toFile1.write("Iteration: "+i+ "Complete");
            toFile.newLine();
            toFile.write(Double.toString(min.swarmPersonalBest.getBestFitness()));
            toFile.newLine();
            // remove comments below to print best position of swarm best position after each iteration
//            toFile.write(Arrays.toString(min.swarmPersonalBest.getBestPosition()));
//            toFile.newLine();
        }
        min.bestOverall = min.swarmPersonalBest.getBestPosition();
        System.out.println("Global best position: "+Arrays.toString(min.bestOverall));
        System.out.println("Global best fitness: "+min.swarmPersonalBest.getBestFitness());
        toFile.close();
        writer.close();
        toFile1.close();
        w.close();
        toFile2.close();
        wr.close();
        System.out.println("File printed successfully");
    }
}

// Parameter Configuration
//1. ω = 0.729844, c1 = c2 = 1.496180
//2. ω = 0.4, c1 = c2 = 1.2
//3. ω = 1.0, c1 = c2 = 2.0
//4. ω = −1.0, c1 = c2 = 2.0
//5. Randomized search