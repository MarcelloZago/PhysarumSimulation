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
