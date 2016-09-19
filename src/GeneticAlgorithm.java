package src;

import java.util.Arrays;
import java.util.Random;


public class GeneticAlgorithm {

	int populationSize = 500;
	int ValidFolding;
	static src.GeneType population[] = new src.GeneType[500];
	static src.GeneType newpopulation[] = new src.GeneType[500];
	String proteinStructure = "hhhpphphphpphphphpph";
	int proteinLength;
	int[] HPModel;
	int HPCount;
	double eliteRate = 0.05;
	double crossOverRate = 0.6;
	double mutationRate = 0.2;
	int CurrentPosNewPopulation;
	int mutationPositionInNewPopulation;
	int totalFitness = 0;
	int generation = 0;
	public static Random rand = new Random();
	int Mutation;
	public static Graph chart = null;
	int bestFitness=2000;

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String args[]) throws InterruptedException {

        try {
                src.GeneticAlgorithm obj = new src.GeneticAlgorithm();
            
                // Finds the HydroPhobic Positions in the given Protein Structure
                obj.FetchHydroPhobicPositions();

                // Generating the Intial Population
                obj.Initialization();

                // Sorting the Population based on each gene Fitness
                Arrays.sort(population);

                // Elite Calculation
                obj.DetermineElitePopulation();

                // CrossOver Population
                obj.GenerateCrossOverPopulation();

                // Fill Remaining Population
                obj.FillRemainingNewPopulation();

                // Performing Mutation
                obj.PerformMutation();

                // ComupteNextGeneration
                obj.ComupteNextGeneration();

        } catch (Exception e) {
                System.err.println("Exception in main block" + e);
        }

    }
        public void FetchHydroPhobicPositions() {

            int hIndex = 0;
            proteinLength = proteinStructure.length();
            HPModel = new int[proteinLength];
            HPCount = 0;
            char[] hOccurence = proteinStructure.toCharArray();
            for (int index = 0; index < proteinLength; index++) {
                    if (hOccurence[index] == 'h') {
                            HPModel[hIndex] = index;
                            hIndex = hIndex + 1;
                            HPCount = HPCount + 1;
                    }

            }
    }
        /*Initializating population*/

    /**
     * @throws InterruptedException
     */
    public void Initialization() throws InterruptedException {

		for (int i = 0; i < populationSize; i++) {
			ValidFolding = 0;
			RandomOrientation(i);
			while (ValidFolding == 0) {
				RandomOrientation(i);
			}
			population[i].Fitness = ComputeFitness(i);
			totalFitness = totalFitness + population[i].Fitness;
		}
	}

	
        /*Computing Fitness for population*/

    /**
     * @param n
     * @return
     */
    public int ComputeFitness(int n) {
		int isSequential;
		int Fitness = 0;
		int latticeDistance;
		for (int i = 0; i < HPCount - 1; i++) {
			for (int j = i + 1; j < HPCount; j++) {
				isSequential = Math.abs(HPModel[i] - HPModel[j]);
				if (isSequential != 1) {
					latticeDistance = Math.abs(population[n].X[HPModel[i]] - population[n].X[HPModel[j]])
							+ Math.abs(population[n].Y[HPModel[i]] - population[n].Y[HPModel[j]]);
					if (latticeDistance == 1) {
						Fitness = Fitness - 1;
					}
				}
			}
		}
		return Fitness;

	}

        /*Randomising the population*/

    /**
     * @param m
     */
    public void RandomOrientation(int m) {

		int PreviousDirection, PresentDirection, temp1, temp2, temp3, X, Y, Flag, Step2;
		int a[] = new int[4];
		int Ax[] = new int[4];
		int Ay[] = new int[4];
		population[m] = new src.GeneType();
		ValidFolding = 1;

		population[m].X[1] = 0;
		population[m].Y[1] = 0;
		population[m].X[2] = 1;
		population[m].Y[2] = 0;
		PreviousDirection = 1;

		for (int i = 3; i <= proteinLength; i++) {

			switch (PreviousDirection) {
			case 1:
				a[1] = 1;
				Ax[1] = 1;
				Ay[1] = 0;
				a[2] = 3;
				Ax[2] = 0;
				Ay[2] = 1;
				a[3] = 4;
				Ax[3] = 0;
				Ay[3] = -1;
				break;
			case 2:
				a[1] = 2;
				Ax[1] = -1;
				Ay[1] = 0;
				a[2] = 3;
				Ax[2] = 0;
				Ay[2] = 1;
				a[3] = 4;
				Ax[3] = 0;
				Ay[3] = -1;
				break;
			case 3:
				a[1] = 1;
				Ax[1] = 1;
				Ay[1] = 0;
				a[2] = 2;
				Ax[2] = -1;
				Ay[2] = 0;
				a[3] = 3;
				Ax[3] = 0;
				Ay[3] = 1;
				break;
			case 4:
				a[1] = 1;
				Ax[1] = 1;
				Ay[1] = 0;
				a[2] = 2;
				Ax[2] = -1;
				Ay[2] = 0;
				a[3] = 4;
				Ax[3] = 0;
				Ay[3] = -1;
				break;
			}

			temp1 = rand.nextInt(3) + 1;
			PresentDirection = temp1;
			temp2 = 0;
			temp3 = 0;
			X = population[m].X[i - 1] + Ax[temp1];
			Y = population[m].Y[i - 1] + Ay[temp1];
			Flag = 0;
			MyFirstJump: for (int j = 1; j <= i - 1; j++) {
				if (X == population[m].X[j] && Y == population[m].Y[j]) {
					Flag = 1;
					break MyFirstJump;
				}
			}

			MyJump1: if (Flag == 1) {
				Flag = 0;
				Step2 = 6 - temp1;
				switch (Step2) {

				case 3:
					if ((rand.nextInt(2) + 1) == 1) {
						temp2 = 1;
					} else {
						temp2 = 2;
					}
					break;
				case 4:
					if ((rand.nextInt(2) + 1) == 1) {
						temp2 = 1;
					} else {
						temp2 = 3;
					}
					break;
				case 5:
					if ((rand.nextInt(2) + 1) == 1) {
						temp2 = 2;
					} else {
						temp2 = 3;
					}
					break;
				}

				PresentDirection = temp2;
				temp3 = 6 - (temp1 + temp2);
				X = population[m].X[i - 1] + Ax[temp2];
				Y = population[m].Y[i - 1] + Ay[temp2];

				for (int j = 1; j <= i - 1; j++) {
					if ((X == population[m].X[j]) && (Y == population[m].Y[j])) {
						Flag = 1;
						break MyJump1;
					}
				}

				// MyJump2:

				if (Flag == 1) {
					Flag = 0;
					PresentDirection = temp3;
					X = population[m].X[i - 1] + Ax[temp3];
					Y = population[m].Y[i - 1] + Ay[temp3];
					for (int j = 1; j <= i - 1; j++) {
						if (X == population[m].X[j] && Y == population[m].Y[j]) {
							Flag = 1;
							ValidFolding = 0;
							// break MyJump2;
						}

					}
				}
				PreviousDirection = a[PresentDirection];
				population[m].X[i] = population[m].X[i - 1] + Ax[PresentDirection];
				population[m].Y[i] = population[m].Y[i - 1] + Ay[PresentDirection];
			}
		}
		
	}

        public void DetermineElitePopulation() {

            newpopulation = new src.GeneType[populationSize];
            int elitePopulation = (int) (eliteRate * populationSize);
            System.arraycopy(population, 0, newpopulation, 0, elitePopulation);
    }

        public void GenerateCrossOverPopulation() {

            int crossOverStartIndex = (int) (eliteRate * populationSize);
            int crossOverPopulationCount = (int) (crossOverRate * populationSize + crossOverStartIndex);
            int crossOverPoint;
            int i, j;
            int maxEndPoint = proteinLength - 3;
            for (int sIndex = crossOverStartIndex; sIndex < crossOverPopulationCount; sIndex++) {
                    CurrentPosNewPopulation = sIndex;
                    i = GetChromosomeIndvidualsUsingRoulettewheelSelection();
                    while (i == 0) {
                            i = GetChromosomeIndvidualsUsingRoulettewheelSelection();
                    }
                    j = GetChromosomeIndvidualsUsingRoulettewheelSelection();
                    while (j == 0) {

                            j = GetChromosomeIndvidualsUsingRoulettewheelSelection();
                    }
                    newpopulation[CurrentPosNewPopulation] = new src.GeneType();
                    crossOverPoint = rand.nextInt(maxEndPoint) + 2;
                    long Success = CrossOver(i, j, crossOverPoint);
                    while (Success == 0) {
                            i = GetChromosomeIndvidualsUsingRoulettewheelSelection();
                            while (i == 0) {
                                    i = GetChromosomeIndvidualsUsingRoulettewheelSelection();
                            }
                            j = GetChromosomeIndvidualsUsingRoulettewheelSelection();
                            while (j == 0) {
                                    j = GetChromosomeIndvidualsUsingRoulettewheelSelection();
                            }
                            crossOverPoint = rand.nextInt(maxEndPoint) + 2;
                            Success = CrossOver(i, j, crossOverPoint);
                    }

            }
    }

    /**
     * @return
     */
    public int GetChromosomeIndvidualsUsingRoulettewheelSelection() {
            int rt = 0;
            try {
                    int rndVar = rand.nextInt(Math.abs(totalFitness));
                    for (int index = 0; index < populationSize; index++) {
                            rndVar = rndVar - Math.abs(population[index].Fitness);
                            if (rndVar < 0) {
                                    rt = index - 1;
                                    return rt;
                            }
                    }
            } catch (Exception e) {
                    System.err.println("Exception in GetChromosomeIndvidualsUsingRoulettewheelSelection.." + e);
            }
            return rt;
    }


    /**
     * @param i
     * @param j
     * @param n
     * @return
     */
    public int CrossOver(int i, int j, int n) {
		int CrossOver = 0;
		try {
			int PrevDirection, p;
			int temp1, temp2 = 0, temp3, Collision, dx, dy, Step2;
			int id;
			int a[] = new int[4];
			int Ax[] = new int[4];
			int Ay[] = new int[4];
			id = CurrentPosNewPopulation;

			if (population[i].X[n] == population[i].X[n - 1]) {
				p = population[i].Y[n - 1] - population[i].Y[n];
				if (p == 1)
					PrevDirection = 3;
				else
					PrevDirection = 4;
			} else {
				p = population[i].X[n - 1] - population[i].X[n];
				if (p == 1)
					PrevDirection = 1;
				else
					PrevDirection = 2;
			}

			switch (PrevDirection) {

			case 1:
				Ax[1] = -1;
				Ay[1] = 0;
				Ax[2] = 0;
				Ay[2] = 1;
				Ax[3] = 0;
				Ay[3] = -1;
				break;
			case 2:
				Ax[1] = 1;
				Ay[1] = 0;
				Ax[2] = 0;
				Ay[2] = 1;
				Ax[3] = 0;
				Ay[3] = -1;
				break;
			case 3:
				Ax[1] = 1;
				Ay[1] = 0;
				Ax[2] = -1;
				Ay[2] = 0;
				Ax[3] = 0;
				Ay[3] = -1;
				break;
			case 4:
				Ax[1] = 1;
				Ay[1] = 0;
				Ax[2] = -1;
				Ay[2] = 0;
				Ax[3] = 0;
				Ay[3] = 1;
				break;

			}

			temp1 = rand.nextInt(3) + 1;

			newpopulation[id].X[n + 1] = population[i].X[n] + Ax[temp1];
			newpopulation[id].Y[n + 1] = population[i].Y[n] + Ay[temp1];
			Collision = 0;

			dx = newpopulation[id].X[n + 1] - population[j].X[n + 1];
			dy = newpopulation[id].Y[n + 1] - population[j].Y[n + 1];
			MyOut0: for (int k = n + 1; k <= proteinLength; k++) {
				newpopulation[id].X[k] = population[j].X[k] + dx;

				newpopulation[id].Y[k] = population[j].Y[k] + dy;

				for (int z = 1; z <= n; z++) {
					if ((newpopulation[id].X[k] == population[i].X[z]) && (newpopulation[id].Y[k] == population[i].Y[z])) {
						Collision = 1;
						break MyOut0;
					}
				}
			}

			MyOut1: if (Collision == 1) {
				Collision = 0;
				Step2 = 6 - temp1;
				switch (Step2) {
				case 3:
					if ((rand.nextInt(2) + 1) == 1) {
						temp2 = 1;
					} else {
						temp2 = 2;
					}
					break;
				case 4:
					if ((rand.nextInt(2) + 1) == 1) {

						temp2 = 1;

					} else {
						temp2 = 3;
					}
					break;
				case 5:

					if ((rand.nextInt(2) + 1) == 1) {
						temp2 = 2;
					} else {
						temp2 = 3;
					}
					break;
				}

				temp3 = 6 - (temp1 + temp2);
				newpopulation[id].X[n + 1] = population[i].X[n] + Ax[temp2];
				newpopulation[id].Y[n + 1] = population[i].Y[n] + Ay[temp2];
				dx = newpopulation[id].X[n + 1] - population[j].X[n + 1];
				dy = newpopulation[id].Y[n + 1] - population[j].Y[n + 1];

				for (int k = n + 1; k <= proteinLength; k++) {

					newpopulation[id].X[k] = population[j].X[k] + dx;
					newpopulation[id].Y[k] = population[j].Y[k] + dy;

					for (int z = 1; z <= n; z++) {
						if ((newpopulation[id].X[k] == population[i].X[z]) && (newpopulation[id].Y[k] == population[i].Y[z])) {
							Collision = 1;
							break MyOut1;
						}
					}
				}

				MyOut2:

				if (Collision == 1) {
					Collision = 0;
					newpopulation[id].X[n + 1] = population[i].X[n] + Ax[temp3];
					newpopulation[id].Y[n + 1] = population[i].Y[n] + Ay[temp3];
					dx = newpopulation[id].X[n + 1] - population[j].X[n + 1];
					dy = newpopulation[id].Y[n + 1] - population[j].Y[n + 1];
					for (int k = n + 1; k <= proteinLength; k++) {
						newpopulation[id].X[k] = population[j].X[k] + dx;
						newpopulation[id].Y[k] = population[j].Y[k] + dy;
						for (int z = 1; z <= n; z++) {
							if ((newpopulation[id].X[k] == population[i].X[z]) && (newpopulation[id].Y[k] == population[i].Y[z])) {
								Collision = 1;						
								break MyOut2;
							}
						}
					}
				}

			}
			MyOut3: if (Collision == 0) {

				for (int k = 1; k <= n; k++) {

					newpopulation[id].X[k] = population[i].X[k];
					newpopulation[id].Y[k] = population[i].Y[k];
				}

				CrossOver = 1;
			} else {}
		} catch (Exception e) {
			System.out.println("Exception...." + e);
                        }
                return CrossOver;
	}

	public void FillRemainingNewPopulation() {
		try {
			int remainingNewPopulationStartIndex = (int) (eliteRate * populationSize + crossOverRate * populationSize);
			int size = populationSize - remainingNewPopulationStartIndex;
			System.arraycopy(population, remainingNewPopulationStartIndex, newpopulation, remainingNewPopulationStartIndex, size);
			} catch (Exception e) {
			System.out.println("Exception...." + e);
		}

	}

        /* Performing Mutation*/
	public void PerformMutation() {
		int mutationPopulation = (int) (mutationRate * populationSize);
		int geneToBeMutated = rand.nextInt(198) + 1;

		int maxEndPoint = proteinLength - 3;
		int mutationPoint = rand.nextInt(maxEndPoint) + 2;
		try {

			mutationPositionInNewPopulation = rand.nextInt(188) + 11;
			for (int index = 0; index < mutationPopulation; index++) {
				mutationPositionInNewPopulation = mutationPositionInNewPopulation;
				long MutationStatus = Mutation(geneToBeMutated, mutationPoint);
				while (MutationStatus == 0) {
					geneToBeMutated = rand.nextInt(198) + 1;
					mutationPoint = rand.nextInt(maxEndPoint) + 2;
					MutationStatus = Mutation(geneToBeMutated, mutationPoint);
				}
			}
		} catch (Exception ex) {

			System.err.println("Exception in Perform Mutation Block..." + ex);
		}

	}

    /**
     * @param i
     * @param n
     * @return
     */
    public int Mutation(int i, int n) {

		int id, a, b, A_Limit, Collision, p;
		int choice;
		int Ary[] = new int[4];
		id = mutationPositionInNewPopulation;

		Ary[1] = 1;
		Ary[2] = 2;
		Ary[3] = 3;
		A_Limit = 3;

		a = population[i].X[n]; /* (a, b) rotating point */
		b = population[i].Y[n];

		do {
			Collision = 0;
			if (A_Limit > 1) {
				choice = rand.nextInt(A_Limit) + 1;
			} else {
				choice = A_Limit;
			}
			p = Ary[choice];
			for (int k = choice; k <= A_Limit - 1; k++) {
				Ary[k] = Ary[k + 1];
			}
			A_Limit = A_Limit - 1;

			for (int k = n + 1; k <= proteinLength; k++) {

				switch (p) {

				case 1:
					newpopulation[id].X[k] = a + b - population[i].Y[k];
					newpopulation[id].Y[k] = population[i].X[k] + b - a;
					break;
				case 2:
					newpopulation[id].X[k] = 2 * a - population[i].X[k];
					newpopulation[id].Y[k] = 2 * b - population[i].Y[k];
					break;
				case 3:
					newpopulation[id].X[k] = population[i].Y[k] + a - b;
					newpopulation[id].Y[k] = a + b - population[i].X[k];
					break;
				}

				MyJump1: for (int z = 1; z <= n; z++) {

					if ((newpopulation[id].X[k] == population[i].X[z]) && (newpopulation[id].Y[k] == population[i].Y[z])) {
						Collision = 1;
						// 'MutationInternalFailCount =
						// MutationInternalFailCount + 1
						// 'MutationCollisionCount = MutationCollisionCount + 1
						break MyJump1;
					}
				}
			}

			if (Collision == 0) {
				A_Limit = 0;
			}

			// MyJump2:
		} while (A_Limit != 0); // loop until A_Limit == 0

		if (Collision == 0) {

			for (int k = 1; k <= n; k++) {
				newpopulation[id].X[k] = population[i].X[k];
				newpopulation[id].Y[k] = population[i].Y[k];
			}
			Mutation = 1;
		} else {
			Mutation = 0;
		}
		return Mutation;

	}

    /**
     * @throws InterruptedException
     */
    public void ComupteNextGeneration() throws InterruptedException {
        totalFitness = 0;
        System.arraycopy(newpopulation, 0, population, 0, populationSize);

        for (int index = 0; index < populationSize; index++) {
                population[index].Fitness = 0;
                population[index].Fitness = ComputeFitness(index);
                totalFitness = totalFitness + population[index].Fitness;
        }
        // Sorting the Population based on the each gene Fitness
        Arrays.sort(population);

        // Determing Elite Population
        DetermineElitePopulation();

        // Generating CrossOver Population
        GenerateCrossOverPopulation();

        // Fill Remaining Population
        FillRemainingNewPopulation();

        // Performing Mutation
        PerformMutation();

        generation = generation + 1;            
        if (generation < 2000) {
                        if(bestFitness > population[0].Fitness){
                        bestFitness = population[0].Fitness;
                        drawChromoseFold(generation, population[0].Fitness, population[0], HPModel, proteinLength, proteinStructure);
                }
                Thread.sleep(1000);
                ComupteNextGeneration();
        }
    }
	
        /*Calling Linechart Program to plot the population*/
	public static void drawChromoseFold(int iteration, int fitness, src.GeneType p, int[] HPModel, int txtProteinLength, String txtProteinStructure) {
		if (chart == null) {
			chart = new Graph(p, HPModel, txtProteinLength, txtProteinStructure,"Iteration = " + iteration + " Fitness = " + fitness);
			chart.setVisible(true);
		} else {
			if (chart.imageFile.exists())
				chart.imageFile.delete();
			chart.refresh(p, HPModel, txtProteinLength, txtProteinStructure, "Iteration = " + iteration + " Fitness = " + fitness);
		}
	}
        
       /*Program entry point*/    
      
       
}