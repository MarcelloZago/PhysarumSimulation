package Simulation;

import java.awt.*;
import java.util.LinkedList;

public class AgentMap {
    private LinkedList<Agent> agentList;

    //parameters
    private double sense_angle;
    private double sense_distance;
    private double step_angle;
    private double step_size;
    private double pheromoneAmount;
    private Color particleColor;

    /**
     * This constructor generates an {@link Agent} list with the given number.
     *
     * @param number Number of Agents used for this simulation.
     */
    public AgentMap(long number, int height, int width, double step_angle, double sense_angle, double sense_distance, double step_size,
                    double pheromoneAmount, Color particleColor) {
        agentList = new LinkedList<>();
        for (int i = 0; i < number; i++) {
            agentList.add(new Agent(500, 500, height, width));
        }

        this.sense_angle = sense_angle;
        this.sense_distance = sense_distance;
        this.step_angle = step_angle;
        this.step_size = step_size;
        this.pheromoneAmount = pheromoneAmount;
        this.particleColor = particleColor;
    }

    public void tick(int currentIndex, TrailMap[] trailMaps) {
        for (Agent agent : agentList) {
            agent.tick(currentIndex, trailMaps, sense_angle, sense_distance, step_angle, step_size, pheromoneAmount);
        }
    }

    public void render(Graphics graphics) {
        for (Agent agent : agentList) {
            graphics.setColor(particleColor);
            int x = (int) Math.round(agent.getX());
            int y = (int) Math.round(agent.getY());

            //graphics.drawLine(y, x, y, x);
            graphics.drawRect(y, x, 1, 1);
        }
    }
}
