package ds.world.type.entities;

import ds.world.graphics.dsPal;
import ds.world.meta.dsEnv;
import mindustry.type.UnitType;

public class dsUnitType extends UnitType {

    public dsUnitType(String name) {
        super(name);
        envRequired = dsEnv.underwaterWarm;
        outlineColor = dsPal.dsUnitOutline;
    }

    @Override
    public void setStats(){
        super.setStats();
    }
}
