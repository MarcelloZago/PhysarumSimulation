package Simulation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Agent {
    private final int MAX_ANGLE = 360;

    int height;
    int width;
    double x;
    double y;
    double heading;

    Agent(int height, int width) {
        Random random = new Random();

        this.x = random.nextDouble() * height;
        this.y = random.nextDouble() * width;
        this.heading = random.nextDouble() * MAX_ANGLE;
        this.height = height;
        this.width = width;

    }

    Agent(double x, double y, int height, int width) {
        Random random = new Random();

        this.x = x;
        this.y = y;
        this.heading = random.nextDouble() * MAX_ANGLE;
        this.height = height;
        this.width = width;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHeading() {
        return heading;
    }

    public void tick(TrailMap  trailMaps, double sense_angle,
                     double sense_distance, double step_angle, double step_size, double amount) {
        //get the three positions
        int[] leftCoordinate = angleToDiscreteCoordinate(heading-sense_angle, sense_distance);
        int[] middleCoordinate = angleToDiscreteCoordinate(heading, sense_distance);
        int[] rightCoordinate = angleToDiscreteCoordinate(heading+sense_angle, sense_distance);


        // calculate pheromone value of the tree positions
        // add pheromone of current agent
        double leftPheromone = trailMaps.getPheromoneValue(leftCoordinate);
        double middlePheromone = trailMaps.getPheromoneValue(middleCoordinate);
        double rightPheromone = trailMaps.getPheromoneValue(rightCoordinate);


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
        } else if (leftPheromone >  rightPheromone) {
            heading -= step_angle;
        } else if (rightPheromone > leftPheromone) {
            heading += step_angle;
        }

        this.x += angleToContinuousCoordinate(heading, step_size)[0];
        this.y += angleToContinuousCoordinate(heading, step_size)[1];

        boundCoordinates();
        trailMaps.deposit(x, y, amount);
    }

    private void boundCoordinates() {
        if (this.x < 0)
            this.x += height;
        if (this.x >= height)
            this.x -= height;

        if (this.y < 0)
            this.y += width;
        if (this.y >= width)
            this.y -= width;
    }

    private int[] angleToDiscreteCoordinate(double angle, double distance) {
        double radian = angle * Math.PI / 180;

        int discrete_x = (int) Math.round((distance * Math.cos(radian)) + x);
        int discrete_y = (int) Math.round((distance * Math.sin(radian)) + y);

        if (discrete_x < 0)
            discrete_x += height;
        if (discrete_x >= height)
            discrete_x -= height;

        if (discrete_y < 0)
            discrete_y += width;
        if (discrete_y >= width)
            discrete_y -= width;

        return new int[]{discrete_x, discrete_y};
    }

    private double[] angleToContinuousCoordinate(double angle, double distance) {
        double radian = angle * Math.PI / 180;

        double continuous_x = distance * Math.cos(radian);
        double continuous_y = distance * Math.sin(radian);

        if (continuous_x < 0)
            continuous_x += height;
        if (continuous_x >= height)
            continuous_x -= height;

        if (continuous_y < 0)
            continuous_y += width;
        if (continuous_y >= width)
            continuous_y -= width;

        return new double[]{continuous_x, continuous_y};
    }
}
