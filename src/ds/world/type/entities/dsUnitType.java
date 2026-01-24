package ds.world.type.entities;

import arc.graphics.Color;
import ds.world.graphics.DSPal;
import ds.world.meta.DSEnv;
import ds.world.meta.dsStats;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.world.meta.StatUnit;

public class dsUnitType extends UnitType {

    public dsUnitType(String name) {
        super(name);
        envRequired = DSEnv.underwaterWarm;
        outlineColor = DSPal.dsUnitOutline;
    }

    @Override
    public void setStats(){
        super.setStats();
    }
}
