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

    /**
     * This variable determines whether the simulation runs in debug mode. This means that the simulation will never
     * stop nor be displayed.
     */
    private final boolean debug = true;

    public SimulationDisplay(long numberAgents, double senseAngle, double senseDistance, double stepAngle,
                             double stepSize, double pheromoneAmount, double decayAmount) {
        simulation = new Simulation(HEIGHT, WIDTH, numberAgents, senseAngle, senseDistance, stepAngle, stepSize,
                pheromoneAmount, decayAmount, Color.BLACK);

        if(! debug) {
            new Window(HEIGHT, WIDTH, "Physarum Simulation", this);
        } else {
            this.start();
        }
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
            if(! debug)
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
        graphics.setColor(Color.WHITE);
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
