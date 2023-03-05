package Simulation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TrailMap {
    private double[][] trailMap;
    private int height;
    private int width;
    private double decayAmount;


    public double[][] getTrailMap() {
        return trailMap;
    }

    public TrailMap(int height, int width, double decayAmount) {
        this.trailMap = new double[height][width];
        this.height = height;
        this.width = width;
        this.decayAmount = decayAmount;
    }

    public double getPheromoneValue(int[] discreteCoordinates){
        return trailMap[discreteCoordinates[0]][discreteCoordinates[1]];
    }

    public void deposit(double x, double y, double amount) {
        trailMap[(int) Math.floor(x)][(int) Math.floor(y)] += amount;
    }

    private void diffuseDecay() {
        double[][] newTrailMap = new double[height][width];

        for (int x = 0; x < trailMap.length; x++) {
            for (int y = 0; y < trailMap[0].length; y++) {
                double sum = 0;

                for (int filter_x = -1; filter_x <= 1; filter_x++) {
                    for (int filter_y = -1; filter_y <= 1; filter_y++) {
                        int current_x = x + filter_x;
                        int current_y = y + filter_y;

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

                sum /= 9;
                newTrailMap[x][y] = sum * decayAmount;
            }
        }

        trailMap = newTrailMap;
    }

    public void tick() {
        diffuseDecay();
    }

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

    private static int doubleToRGB(double d) {
        float hue = (float) (d / 1.5);
        float saturation = 1;
        float brightness = 1;

        //return Color.HSBtoRGB(hue, saturation, brightness);
        if(d < 0.5)
            return Color.BLACK.getRGB();
        else
            return Color.WHITE.getRGB();
    }
}
