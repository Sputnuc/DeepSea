package ds.type;

import arc.graphics.Color;
import ds.world.meta.DSStats;
import mindustry.type.Liquid;

//Just liquid with more information
public class DSLiquid extends Liquid {


    public DSLiquid(String name, Color color) {
        super(name, color);
    }
    public DSLiquid(String name){
        super(name);
    }

    //If true it can be use in exothermic reactors.
    public boolean exothermic = false;
    public float reactivity = 0f;

    @Override
    public void setStats(){
        super.setStats();
        stats.addPercent(DSStats.reactivity, reactivity);
    }
}
