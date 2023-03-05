package Simulation;

import java.awt.*;
import java.util.Random;

public class Simulation {

    private AgentMap[] agentMaps;
    private TrailMap[] trailMaps;

    public Simulation(int height, int width){
        agentMaps = new AgentMap[3];
        trailMaps = new TrailMap[3];
        Color[] colorArray = new Color[]{Color.BLUE, Color.CYAN, Color.DARK_GRAY};
        Random random = new Random();

        for(int i = 0; i < 3; i++){
            agentMaps[i] = new AgentMap(10000, height, width, random.nextDouble()*360,
                    random.nextDouble()*360,
                    random.nextDouble()*50,
                    random.nextDouble()*50,
                    random.nextDouble(), colorArray[i]);
            trailMaps[i] = new TrailMap(height, width, random.nextDouble());
        }

    }

    public void tick(){
        for (int i = 0; i < 3; i++) {
            agentMaps[i].tick(i, trailMaps);
            trailMaps[i].tick();
        }
    }

    public void render(Graphics graphics){
        for (int i = 0; i < 3; i++) {
            agentMaps[i].render(graphics);

            //TODO: render the trailmaps
        }
    }
}
