package org.example.bacteriaLifeLogicRefactor;

import java.util.Arrays;
import java.util.Random;

public class BacteriaLifeLogicRefactor {
    public final int DIMENSION;
    public int round = 0;
    public final int MAX_ROUNDS = 300; // Sin static
    public final int[][] DIRECTIONS = { // Sin static
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1}
    };

    // Main function, launching the algorithm
    public BacteriaLifeLogicRefactor(int dimension) {
        this.DIMENSION = dimension;
    }

    // Generate initial generation, random positions
    public int[][] generateInitialGen() {
        Random rand = new Random();
        int[][] genTable = new int[DIMENSION][DIMENSION];

        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                genTable[i][j] = rand.nextInt(2);
            }
        }
        return genTable;
    }

    // Generating a new gen until it's stable
    public int[][] generateNewGen(int[][] gen) {
        round++;
        // To prevent stack overflow
        if (round > MAX_ROUNDS) {
            System.out.println("Can't get a stable gen.");
            return gen;
        }

        int[][] newGen = new int[DIMENSION][DIMENSION];
        // Check each position
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {

                int bacteria = gen[i][j];
                int neighbours = checkNeighbours(gen, i, j);

                // Apply rules
                if (bacteria == 0 && neighbours == 3) {
                    newGen[i][j] = 1; // Nacimiento
                } else if (bacteria == 1 && (neighbours == 0 || neighbours == 1)) {
                    newGen[i][j] = 0; // Muerte por soledad
                } else if (bacteria == 1 && neighbours > 3) {
                    newGen[i][j] = 0; // Muerte por asfixia
                } else {
                    newGen[i][j] = bacteria; // Nothing changes, supervivencia
                }
            }
        }

        return newGen;
    }

    // Count the neighbours (Sin static)
    public int checkNeighbours(int[][] gen, int row, int col) {
        int neighbours = 0;
        // 8 possible positions of neighbours
        for (int[] d : DIRECTIONS) {
            int r = row + d[0];
            int c = col + d[1];
            // Make sure it's within bounds and if it's not empty increment the value
            if (inBounds(gen, r, c) && gen[r][c] == 1) {
                neighbours++;
            }
        }

        return neighbours;
    }

    // Makes sure coordinates are within bounds (Sin static)
    public boolean inBounds(int[][] gen, int row, int col) {
        return row >= 0 && row < gen.length &&
                col >= 0 && col < gen[0].length;
    }

    public int getRound() {
        return round;
    }

    // Check if the gen hasn't changed (if it's stable) (Sin static)
    public boolean checkStableGen(int[][] oldGen, int[][] newGen) {
        return Arrays.deepEquals(oldGen, newGen);
    }
}