package Simulation;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class implements the trail map which basically contains all the handling of the pheromones. This includes the
 * deposit of pheromones onto the map, the diffusion process and the decay of pheromones.
 */
@SuppressWarnings("SuspiciousNameCombination")
public class TrailMap {
    private double[][] trailMap; // array containing all the pheromone values
    private final int height;
    private final int width;
    private final double decayAmount; // amount of decay after each step (between 0 and 1)


    /**
     * Constructor that generates a new trail map for given dimensions and with a given amount of decay.
     *
     * @param height height of the trail map to be created
     * @param width width of the trail map to be created
     * @param decayAmount amount of decay that will happen in each simulation step
     */
    public TrailMap(int height, int width, double decayAmount) {
        this.trailMap = new double[height][width];
        this.height = height;
        this.width = width;
        this.decayAmount = decayAmount;
    }

    /**
     * Getter for a pheromone level of a given discrete coordinate.
     *
     * @param discreteCoordinates Discrete coordinate which is an integer array contain the x and the y coordinate
     * @return pheromone level for the given coordinate
     */
    public double getPheromoneValue(int[] discreteCoordinates) {
        return trailMap[discreteCoordinates[0]][discreteCoordinates[1]];
    }

    /**
     * This function implements the deposit of an amount of pheromone onto the  trail map at the given coordinates.
     * @param x x coordinate
     * @param y y coordinate
     * @param amount amount of pheromone that is deposited
     */
    public void deposit(double x, double y, double amount) {
        // TODO: fix the rounding of the coordinates to make the usage of this class more consitend
        trailMap[(int) Math.floor(x)][(int) Math.floor(y)] += amount;
    }

    /**
     * This function implements the diffusion of the pheromones and the decay in one step. It basically implements a
     * convolution over the image with wrapping to the other side of the image in the case if the edges. The filter
     * that is used in the convolution is a basic 3x3 mean filter.
     */
    private void diffuseDecay() {
        // create new trailmap in which the newly calculated values will be put in
        double[][] newTrailMap = new double[height][width];

        // loop over all the pixels of the old trail map
        for (int x = 0; x < trailMap.length; x++) {
            for (int y = 0; y < trailMap[0].length; y++) {
                double sum = 0; // sum of the neighboring pixels

                // loop over the 3x3 neighborhood of this pixels and sum up the values
                for (int filter_x = -1; filter_x <= 1; filter_x++) {
                    for (int filter_y = -1; filter_y <= 1; filter_y++) {
                        int current_x = x + filter_x;
                        int current_y = y + filter_y;

                        // handle the wrapping around for the edge pixels
                        if (current_x < 0) {
                            current_x += height;
                        } else if (current_x >= height) {
                            current_x -= height;
                        }

                        if (current_y < 0) {
                            current_y += width;
                        } else if (current_y >= width) {
                            current_y -= width;
                        }

                        sum += trailMap[current_x][current_y];
                    }
                }

                // calculate the mean and put the decayed amount into the new trail map
                sum /= 9;
                newTrailMap[x][y] = sum * decayAmount;
            }
        }

        // replace the old trail map with the new one
        trailMap = newTrailMap;
    }

    /**
     * Implements one step of the simulation. It is a wrapper for {@link #diffuseDecay()}.
     */
    public void tick() {
        diffuseDecay();
    }

    /**
     * This function implements the rendering of the trail map. TODO: finish it up
     * @param graphics
     */
    public void render(Graphics graphics) {
        var image = new BufferedImage(trailMap.length, trailMap[0].length, BufferedImage.TYPE_INT_RGB);

        for (var row = 0; row < trailMap.length; row++) {
            for (var col = 0; col < trailMap[row].length; col++) {
                image.setRGB(col, row, doubleToRGB(trailMap[row][col]));
                //image.setRGB(col, row, doubleToRGB(trailMap[row][col]));
            }
        }

        graphics.drawImage(image, 0, 0, null);
    }

    /**
     *
     * @param d
     * @return
     */
    private static int doubleToRGB(double d) {
        float hue = (float) (d / 1.5);
        float saturation = 1;
        float brightness = 1;

        //return Color.HSBtoRGB(hue, saturation, brightness);
        if (d < 0.5)
            return Color.BLACK.getRGB();
        else
            return Color.WHITE.getRGB();
    }
}
