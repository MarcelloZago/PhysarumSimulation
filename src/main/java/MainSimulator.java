import DisplayInterface.SimulationDisplay;

import java.util.Scanner;

/**
 * Main class that starts the display and run of the simulation.
 */
public class MainSimulator {

    public static void main(String[] args) {
        new SimulationDisplay(50000, // number of agents
                15, // sense angle
                3, // sensing distance
                15, // turn angle
                3, // step size per tick
                1, //pheromone amount
                0.99); // amount of multiplicative decay
    }
}
