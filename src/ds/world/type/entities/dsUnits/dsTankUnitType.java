package ds.world.type.entities.dsUnits;

import ds.world.graphics.dsPal;
import ds.world.meta.dsEnv;
import mindustry.gen.TankUnit;
import mindustry.type.unit.TankUnitType;

public class dsTankUnitType extends TankUnitType {
    public dsTankUnitType(String name) {
        super(name);
        outlineColor = dsPal.dsUnitOutline;
        constructor = TankUnit::create;
        envRequired = dsEnv.underwaterWarm;
    }
}
