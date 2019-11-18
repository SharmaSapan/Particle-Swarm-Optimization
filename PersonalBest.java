import java.util.Arrays;
// @author: Sapan Sharma
// Student ID: 6352819

// this class holds the personal best for every particle, each swarm and random search operation
public class PersonalBest {
    private double[] bestPosition;
    private double bestFitness;

    public double[] getBestPosition() {
        return bestPosition;
    }

    public void setBestPosition(double[] bestPos) {
        //this.bestPosition = bestPos;

        for (int i = 0; i < bestPosition.length;i++) {
            bestPosition[i] = bestPos[i];
        }

       // System.out.println("setting " + Arrays.toString(bestPosition));
    }

    public double getBestFitness() {
        return bestFitness;
    }

    public void setBestFitness(double bestFitness) {
        this.bestFitness = bestFitness;
    }

    PersonalBest(double[] bestPosition, double bestFitness) {
        this.bestPosition = new double[bestPosition.length];
        for (int i = 0; i < bestPosition.length; i++) {
            this.bestPosition[i] = bestPosition[i];
        }
        this.bestFitness = bestFitness;
    }
}