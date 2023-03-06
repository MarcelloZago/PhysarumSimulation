import DisplayInterface.SimulationDisplay;

import java.util.Scanner;

/**
 * Main class that starts the display and run of the simulation.
 */
public class MainSimulator {

    public static void main(String[] args) {
        new SimulationDisplay(2000, // number of agents
                123, // sense angle
                12, // sensing distance
                6, // turn angle
                5, // step size per tick
                1, //pheromone amount
                0.99); // amount of multiplicative decay
    }
}
