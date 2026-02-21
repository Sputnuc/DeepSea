package ds.world.type;

import arc.graphics.Color;
import ds.world.meta.dsStats;
import mindustry.type.Liquid;

//Just liquid with more information
public class dsLiquid extends Liquid {

    public dsLiquid(String name, Color color) {
        super(name, color);
    }
    public dsLiquid(String name){
        super(name);
    }

    //If yes it can be use in exothermic reactor.
    public boolean exothermic = false;
    public float reactivity = 0f;

    @Override
    public void setStats(){
        super.setStats();
        stats.addPercent(dsStats.reactivity, reactivity);
    }
}
