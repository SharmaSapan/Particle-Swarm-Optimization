import java.util.Random;
// @author: Sapan Sharma
// Student ID: 6352819

public class Particle {
    private double[] currentPosition, velocity;
    private double rFitness, sphereFitness;
    PersonalBest bestSoFar; // holds the best so far position and fitness of this particle
    private boolean shouldUpdate = true; // to establish boundary constraints
    // for sphere fitness function
    //double min = -(Double.MAX_VALUE), max = (Double.MAX_VALUE);
    double min = -5.12, max = 5.12;
    final double MAXVELOCITY = (max-min)/2; // to be used for velocity clamping but not used

    Particle(int dimension, Random rnd){

        this.currentPosition = new double[dimension];
//        this.personalBest = new double[dimension]; //initialized as empty
        this.velocity = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            // initalize randomly between +5.12 and -5.12
            currentPosition[i] = ((rnd.nextDouble()*(max - min)) + min);
            // initalize each particles' velocity as zero
            velocity[i] = 0;
        }
        bestSoFar = new PersonalBest(new double[dimension], Double.MAX_VALUE);
    }

    public void calcrFitness() {
        rFitness = 0.0; // initialized fitness zero so that it does not take old fitness into account
        for (int i = 0; i < currentPosition.length; i++) {
            // for summation using the Rastrigin's function fourmula
            rFitness += ((currentPosition[i]*currentPosition[i]) - (10*Math.cos(2*Math.PI*(currentPosition[i]))));
        }

        rFitness = rFitness + (10*currentPosition.length);

        updatePersonalBest();
    }

    public void updatePersonalBest() {
        // to check if any element is outside the boundary
        // Particles will have position outside the boundary but they can't update personal best
        for (int i = 0; i < currentPosition.length; i++) {
            if (currentPosition[i] > max || currentPosition[i] < min) {
                shouldUpdate = false;
            }
        }
        // if not outside the boundary update best so far
        if (shouldUpdate) {
            // if rFitness better than previous best then update current best's position
            if (bestSoFar.getBestFitness() > rFitness) {
                bestSoFar.setBestFitness(rFitness);
                bestSoFar.setBestPosition(currentPosition);
            }
        }
        shouldUpdate = true;
    }

    public double[] getCurrentPosition() {
        return currentPosition;
    }

    public void updateCurrentPosition(double[] currentPosition) {
        this.currentPosition = currentPosition;
    }

    public double[] getVelocity() {
        return velocity;
    }

    public void setVelocity(double[] velocity) {
        this.velocity = velocity;
    }

    public double getrFitness() {
        return rFitness;
    }
    // to use PSO on sphere fitness function only for testing purposes

    public void calcSphereFitness() {
        sphereFitness = 0.0;
        for (int i = 0; i < currentPosition.length; i++) {
            sphereFitness += (currentPosition[i]*currentPosition[i]);
        }
        for (int i = 0; i < currentPosition.length; i++) {
            if (currentPosition[i] > max || currentPosition[i] < min)
                shouldUpdate = false;
        }
        // if not outside the boundary update best so far
        if (shouldUpdate) {

            // if rFitness better than previous best then update current best's position
            if (bestSoFar.getBestFitness() > sphereFitness) {
                bestSoFar.setBestFitness(sphereFitness);
                bestSoFar.setBestPosition(currentPosition);
            }
        }
        shouldUpdate = true;
    }
}