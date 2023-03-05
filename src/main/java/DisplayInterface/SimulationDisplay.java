package DisplayInterface;

import Simulation.Simulation;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class SimulationDisplay extends Canvas implements Runnable {

    private static final int HEIGHT = 1000; //Y
    private static final int WIDTH = 1000; //X

    private Thread thread;
    private boolean running;

    private Simulation simulation;

    private int globalCounter = 0;

    public SimulationDisplay(long numberAgents, int sense_angel, double sense_distance, double step_angle, double step_size,
                             double pheromoneAmount, double decayAmount) {
        simulation = new Simulation(HEIGHT, WIDTH);

        new Window(HEIGHT, WIDTH, "Physarum Simulation", this);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Classic game-loop for the agent-based simulation and the visualisation.
     */
    @Override
    public void run(){
        this.requestFocus(); //sets the window to be in focus

        while(running){
            tick();
            render();
        }
    }

    /**
     * This function performs one whole simulation step, when called.
     */
    private void tick() {
        simulation.tick();
    }

    /**
     * This function is used to render the result of the current simulation.
     */
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        BufferedImage bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        //Graphics graphics = bi.getGraphics();
        Graphics graphics = bs.getDrawGraphics();

        //draw in the background
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        simulation.render(graphics);

        graphics.dispose();
        bs.show();

        /*
        try {
            ImageIO.write(bi, "gif", new File("Images\\Img_" + globalCounter + ".gif"));
            System.out.println("Saved image:" + globalCounter);
            globalCounter++;
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

}
