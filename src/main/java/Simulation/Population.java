package Simulation;

import java.awt.*;
import java.util.Random;

/**
 * This class groups a set of agents to their respective trailMap and color. It is a wrapper to handle the simulation
 * more easily.
 */
public class Population {

    private final AgentMap agentMap; // agent map
    private final TrailMap trailMap; // respective trailMap
    private final Color color; // and respective color

    /**
     * Standard constructor that generates a AgentMap with random parameters and the color black and a TrailMap with
     * a high decay.
     */
    public Population(int height, int width) {
        Random random = new Random();

        this.agentMap = new AgentMap(10000,
                height, width,
                random.nextDouble() * 360,
                random.nextDouble() * 360,
                random.nextDouble() * 5,
                random.nextDouble() * 5,
                random.nextDouble());
        this.trailMap = new TrailMap(height, width, 0.99);
        this.color = Color.BLACK;
    }

    /**
     * Constructor to generate a population with custom parameters.
     *
     * @param height          height of the simulation space
     * @param width           width of the simulation space
     * @param numberAgents    number of agents in the population
     * @param senseAngle      sense angle of each agent
     * @param senseDistance   sensing distance of each agent
     * @param stepAngle       step angle of each agent
     * @param stepSize        step size (distance) of each agent per simulation step
     * @param pheromoneAmount amount of pheromone each agent drops after a move
     * @param decayAmount     amount of decay of th trail map in each simulation step
     * @param color           color of the agents in the visualization
     */
    public Population(int height, int width, long numberAgents, double senseAngle, double senseDistance,
                      double stepAngle, double stepSize, double pheromoneAmount, double decayAmount, Color color) {
        this.agentMap = new AgentMap(numberAgents, height, width, stepAngle, senseAngle, stepSize, senseDistance,
                pheromoneAmount);
        this.trailMap = new TrailMap(height, width, decayAmount);
        this.color = color;
    }

    /**
     * This function manages a new time step of the simulation for both the agent map
     * and the trail map.
     */
    public void tick() {
        this.agentMap.tick(this.trailMap);
        this.trailMap.tick();
    }

    /**
     * This function implements the rendering of the current state of the simulation.
     *
     * @param graphics graphic that will be needed to render everything
     */
    public void render(Graphics graphics) {
        agentMap.render(graphics, this.color);
    }
}
