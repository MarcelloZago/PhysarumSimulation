package Simulation;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

/**
 * This class resembles the agent map. It basically groups all single agents and conducts them.
 */
public class AgentMap {
    private final LinkedList<Agent> agentList;

    //parameters
    private final double sense_angle;
    private final double sense_distance;
    private final double step_angle;
    private final double step_size;
    private final double pheromoneAmount;

    /**
     * This constructor generates a list of {@link Agent} for the given number of agents and creates them with the
     * given set of parameters.
     *
     * @param number          Number of Agents used for this simulation.
     * @param height          height of the simulation space
     * @param width           width of the simulation space
     * @param step_angle      angle of the steps of an agent
     * @param sense_angle     angle of the sensor of an agent
     * @param step_size       step size of an agent in one simulation step
     * @param sense_distance  sensor distance of an agent
     * @param pheromoneAmount amount of pheromone each agent leaves behind after a step
     */
    public AgentMap(long number, int height, int width, double step_angle, double sense_angle, double step_size,
                    double sense_distance, double pheromoneAmount) {
        agentList = new LinkedList<>();
        Random random = new Random();

        // fill in the list with newly generated agents
        for (int i = 0; i < number; i++) {
            agentList.add(new Agent(random.nextInt(height), random.nextInt(width), height, width));
        }

        // set all the parameters
        this.sense_angle = sense_angle;
        this.sense_distance = sense_distance;
        this.step_angle = step_angle;
        this.step_size = step_size;
        this.pheromoneAmount = pheromoneAmount;
    }

    /**
     * This function orchestrates one step of the simulation for all the agents of this map.
     *
     * @param trailMap the trail map on which the pheromones of each agent will be placed on
     */
    public void tick(TrailMap trailMap) {
        for (Agent agent : agentList) {
            agent.tick(trailMap, sense_angle, sense_distance, step_angle, step_size, pheromoneAmount);
        }
    }

    /**
     * This function manages the rendering of all agents. It first converts the continuous coordinates of each agent
     * to discrete ones and then draws them onto the graphic.
     *
     * @param graphics      graphic on which the agents will be drawn
     * @param particleColor color of the particles
     */
    public void render(Graphics graphics, Color particleColor) {
        for (Agent agent : agentList) {
            graphics.setColor(particleColor);
            int x = (int) Math.round(agent.getX());
            int y = (int) Math.round(agent.getY());

            //graphics.drawLine(y, x, y, x);
            graphics.drawRect(y, x, 1, 1);
        }
    }
}
