package Simulation;

import java.awt.*;

/**
 * This class implements the general handling of a simulation with one or more populations.
 */
public class Simulation {

    private final Population pop;

    /**
     * Basic constructor that generates one population with the given dimensions.
     *
     * @param height height of the simulation space
     * @param width  width of the simulation space
     */
    public Simulation(int height, int width) {
        this.pop = new Population(height, width);
    }

    /**
     * Constructor to generate a Simulation with a population with custom parameters. All parameters must be given.
     *
     * @param height          height of the simulation space
     * @param width           width of the simulation space
     * @param numberAgents    number of agents in the simulation
     * @param senseAngle      sense angle of each agent
     * @param senseDistance   sensing distance of each agent
     * @param stepAngle       step angle of each agent
     * @param stepSize        step size (distance) of each agent per simulation step
     * @param pheromoneAmount amount of pheromone each agent drops after a move
     * @param decayAmount     amount of decay of th trail map in each simulation step
     * @param color           color of the agents in the visualization
     */
    public Simulation(int height, int width, long numberAgents, double senseAngle, double senseDistance,
                      double stepAngle, double stepSize, double pheromoneAmount, double decayAmount, Color color) {
        this.pop = new Population(height, width, numberAgents, senseAngle, senseDistance, stepAngle, stepSize,
                pheromoneAmount, decayAmount, color);
    }

    /**
     * This method implements one simulation step.
     */
    public void tick() {
        pop.tick();
    }

    /**
     * This method calls all the objects that need to be rendered.
     *
     * @param graphics graphic onto it is renders
     */
    public void render(Graphics graphics) {
        pop.render(graphics);
    }
}
