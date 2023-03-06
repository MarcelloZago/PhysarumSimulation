package Simulation;

import java.util.Random;

/**
 * Implementation of a single agent of the whole simulation. This agent handles its own movement, transformation of
 * coordinates and the pheromone placement.
 */
@SuppressWarnings("SuspiciousNameCombination")
public class Agent {
    private final int MAX_ANGLE = 360;

    int height; // height of the overall simulation area
    int width; // width of the overall simulation area
    double x; // current x coordinate
    double y; // current y coordinate
    double heading; // angle in which the agent is currently heading

    /**
     * This constructor generates an agent with random starting coordinates and direction.
     *
     * @param height overall height of the simulation
     * @param width  overall width of the simulation
     */
    Agent(int height, int width) {
        Random random = new Random();

        this.x = random.nextDouble() * height;
        this.y = random.nextDouble() * width;
        this.heading = random.nextDouble() * MAX_ANGLE;
        this.height = height;
        this.width = width;

    }

    /**
     * This constructor generates an agent at the given coordinate.
     *
     * @param x      starting x coordinate
     * @param y      starting y coordinate
     * @param height overall height of the simulation
     * @param width  overall width of the simulation
     */
    Agent(double x, double y, int height, int width) {
        Random random = new Random();

        this.x = x;
        this.y = y;
        this.heading = random.nextDouble() * MAX_ANGLE;
        this.height = height;
        this.width = width;
    }

    /**
     * Getter of the x coordinate
     *
     * @return x coordinate of this agent
     */
    public double getX() {
        return x;
    }

    /**
     * Getter of the y coordinate
     *
     * @return y coordinate of this agent
     */
    public double getY() {
        return y;
    }

    /**
     * Getter of the direction angle
     *
     * @return direction angle of this agent
     */
    public double getHeading() {
        return heading;
    }

    /**
     * This function implements one time step of the agent. It includes the calculation of the new position, checking
     * for the simulation boundaries and the placement of a pheromone onto the trailMap.
     *
     * @param trailMap       grid containing all pheromone levels for all coordinates
     * @param sense_angle    angles for the left and right sensors
     * @param sense_distance distance of each sensor to the agent
     * @param step_angle     angle that is used, when the agent changes direction
     * @param step_size      step size of the agent in this simulation step
     * @param amount         amount of pheromone the agent drops onto the trail map
     */
    public void tick(TrailMap trailMap, double sense_angle,
                     double sense_distance, double step_angle, double step_size, double amount) {
        //get the three sensing positions
        int[] leftCoordinate = angleToDiscreteCoordinate(heading - sense_angle, sense_distance);
        int[] middleCoordinate = angleToDiscreteCoordinate(heading, sense_distance);
        int[] rightCoordinate = angleToDiscreteCoordinate(heading + sense_angle, sense_distance);

        // calculate pheromone value of the tree positions
        // add pheromone of current agent
        double leftPheromone = trailMap.getPheromoneValue(leftCoordinate);
        double middlePheromone = trailMap.getPheromoneValue(middleCoordinate);
        double rightPheromone = trailMap.getPheromoneValue(rightCoordinate);

        //identify the case for the next movement
        if (middlePheromone > leftPheromone && middlePheromone > rightPheromone) {
            heading += 0;
        } else if (leftPheromone > middlePheromone && rightPheromone > middlePheromone) {
            Random r = new Random();
            if (r.nextFloat() > 0.5) {
                heading += step_angle;
            } else {
                heading -= step_angle;
            }
        } else if (leftPheromone > rightPheromone) {
            heading -= step_angle;
        } else if (rightPheromone > leftPheromone) {
            heading += step_angle;
        }

        // update the coordinates
        this.x += angleToContinuousCoordinate(heading, step_size)[0];
        this.y += angleToContinuousCoordinate(heading, step_size)[1];

        // check whether the coordinates are still legitimate and correct them if needed
        boundCoordinates();

        // place pheromone onto the trailMap
        trailMap.deposit(x, y, amount);
    }

    /**
     * This function checks whether the current coordinates are in the bounding area or not. If not they will be
     * corrected (see {@link #boundCoordinates(double, double)}).
     */
    private void boundCoordinates() {
        // calculate new coordinates
        double[] newCoordinates = boundCoordinates(this.x, this.y);

        // update the current coordinates
        this.x = newCoordinates[0];
        this.y = newCoordinates[1];
    }

    /**
     * This function checks whether the given real (double) coordinates are in the bounding area of the simulation. If
     * they are not, the coordinates will be wrapped around to the other side.
     *
     * @param xCoordinate x coordinate
     * @param yCoordinate y coordinate
     * @return array of the new coordinates
     */
    private double[] boundCoordinates(double xCoordinate, double yCoordinate) {

        if (xCoordinate < 0)
            xCoordinate += height;
        if (xCoordinate >= height)
            xCoordinate -= height;

        if (yCoordinate < 0)
            yCoordinate += width;
        if (yCoordinate >= width)
            yCoordinate -= width;

        return new double[]{xCoordinate, yCoordinate};
    }

    /**
     * This function calculates for a given turning angle and step size and the current coordinates new discrete
     * coordinates returns them.
     *
     * @param angle    angle that is used for the calculation
     * @param distance step size
     * @return discrete coordinates for the given angle, step size and current coordinates
     */
    private int[] angleToDiscreteCoordinate(double angle, double distance) {
        double radian = angle * Math.PI / 180;

        // calculate discrete coordinates
        int discrete_x = (int) Math.round((distance * Math.cos(radian)) + x);
        int discrete_y = (int) Math.round((distance * Math.sin(radian)) + y);

        // check whether they are still in the bounds
        double[] legalCoordinates = boundCoordinates((double) discrete_x, (double) discrete_y);

        return new int[]{(int) legalCoordinates[0], (int) legalCoordinates[1]};
    }

    /**
     * This function receives an angle and a step size and calculates the respective difference in the x and the y
     * coordinate.
     *
     * ATTENTION: This function has a difference return than {@link #angleToDiscreteCoordinate(double, double)}!
     *
     * @param angle angle that the agents turns
     * @param distance step size
     * @return array of differences in the x and y coordinates
     */
    private double[] angleToContinuousCoordinate(double angle, double distance) {
        double radian = angle * Math.PI / 180;

        double continuous_x = distance * Math.cos(radian);
        double continuous_y = distance * Math.sin(radian);

        // check whether they are still in the bounds
        double[] legalCoordinates = boundCoordinates(continuous_x, continuous_y);

        return new double[]{legalCoordinates[0], legalCoordinates[1]};
    }
}
