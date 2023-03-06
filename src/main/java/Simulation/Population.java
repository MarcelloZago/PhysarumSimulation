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
