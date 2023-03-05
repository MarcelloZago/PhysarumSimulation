package Simulation;

import java.awt.*;
import java.util.Random;

public class Simulation {

    private AgentMap agentMap;
    private TrailMap trailMap;

    public Simulation(int height, int width){
        agentMap = new AgentMap(30000, height, width, 60,
                60,
                5,
                5,
                1,
                Color.DARK_GRAY);;
        trailMap = new TrailMap(height, width, 0.8);
    }

    public void tick(){
        agentMap.tick(trailMap);
        trailMap.tick();
    }

    public void render(Graphics graphics){
        agentMap.render(graphics);
    }
}
