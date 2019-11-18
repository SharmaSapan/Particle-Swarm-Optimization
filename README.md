# Particle-Swarm-Optimization
Particle Swarm Optimization (PSO) is a population-based, stochastic optimization technique
An implementation of swarm based Algorithm that tends to find the best position where it is most optimal in a function in accordance to n-dimensions (features)
Particle movement premised on two simple behaviours: Move towards own (personal) best position. Move towards the best position in a neighbourhood

Applications of PSO:
-Continuous optimization - e.g. Sphere function
-Discrete optimization - e.g. Travelling salesman problem
-Multi-objective optimization - e.g. Vehicle routing problem
-Neural network training
-Parameter selection for GA
-Clustering
-Scheduling

Goal is to have the particle move towards the optimal point in the search space
Vanilla Particle swarm optimization PSO is implemented in my java program. This algorithm tends to find the most fit position calculated using a fitness function. The PSO helps gain an edge as the particles achieve that by using their own cognition and also by what other particles in the search space has discovered. In this program, the effort was made to optimize the Rastrigin function and the fitness was calculated using: 
for (int i = 0; i < currentPosition.length; i++) {
    // for summation using the Rastrigin's function fourmula
    rFitness += ((currentPosition[i]*currentPosition[i]) - (10*Math.cos(2*Math.PI*(currentPosition[i]))));
}
rFitness = rFitness + (10*currentPosition.length);

Random search was also conducted to compare with the PSO performance. Multiple experiments were run using different seeds comparing parameters. Detailed results are in excel file in this folder.
Different given parameters were used Experiment 1. ω = 0.729844, c1 = c2 = 1.496180 |Experiment 2. ω = 0.4, c1 = c2 = 1.2 | Experiment 3. ω = 1.0, c1 = c2 = 2.0 | Experiment 4. ω = −1.0, c1 = c2 = 2.0 | Experiment 5. Randomized search
For each experiment 30 swarm particles, 500 iterations and 30 dimensions as a problem was used. In the calculations Inertia determines how much the particle will move after change in its velocity at each iteration. The cognitive value and social values are same for each experiment so that the same degree of personal best particle and neighbourhood best preference is given, and best ranks and performance as explained in class reaches. The boundary constraints are set in the program so that the particle best and global best are not considered if the particle is beyond +5.12 or -5.12. Particles are allowed to go outside that range but not allowed to update best positions. Random search calculates iterations according to the running PSO i.e. swarm size * pso iterations.
Results Obtained:

|Results 	        | PSO-1	      |   PSO-2	   |   PSO-3     |     PSO-4	| Randomized Search|
|Average            | 71.7060349  |	129.8438549|  431.9240568|   431.9240568|       331.720394 |
|Standard Deviation | 11.48147883 | 8.787874943|  48.8626463 |	  48.8626463|       4.843294794|
|Median|	        | 75.61707741 | 126.8979188|  446.1518272|   446.1518272|       332.4445966|

ANALYSIS:

The results show that the PSO highly depends upon the parameter configurations. The result can perform worse than the random search if not chosen properly. The experiment 1 performed best as they lead to convergence and perform well empirically as explained in class. This experiment has caused the particles to move to an optimal position and decreased its fitness drastically compared to random search in experiment 5. Best fitness conceived with experiment 1 is 56.715 and with random search was 325.98, hence PSO can perform more than 450% better than random search. The deviation received in these experiments were fairly low while some fitness was better some were not so. Random search gives the least deviation of 4.84 compared to 11.48 of PSO experiment 1.
In the second PSO experiment the average rose by approximately 80% although standard deviation was not much affected but decreased. It probably is due to the fact that the inertia value is decreased to 0.4 which makes the particle movement less chaotic. 
But when the inertia was made 1.0 and cognitive and social values at 2, PSO performed worst than the random search by about 30%.  It can be inferred that PSO can perform worse than 15000 iterations in random space if wrong values of parameters chosen. Experiment 3 and 4 performed same although they had different inertia value, being +1 and -1. This suggests that the negative inertia value will not change the results only the particles will move in opposite direction but no real effect in fitness calculation. The fitness obtained by experiment 3,4 and 5 were not too close to the optimal solution and the parameters are not recommended while optimizing. The social and cognitive values gave a high influence in finding the best position overall with increased inertia among particles, hence tempering the overall fitness. The standard deviation was very high compared to first two experiments also due to the same reason mentioned above.
We can conclude that how important are the parameter configurations when finding optimal solution using Particle Swarm Optimization. Higher the Inertia, higher the effect of velocity update on the particles throwing them out the given range. Even the excessive cognitive and social dependence increase the fitness.
