package ds.world.type.entities.dsUnits;

import ds.world.graphics.DSPal;
import ds.world.meta.DSEnv;
import mindustry.gen.TankUnit;
import mindustry.type.unit.TankUnitType;

public class dsTankUnitType extends TankUnitType {
    public dsTankUnitType(String name) {
        super(name);
        outlineColor = DSPal.dsUnitOutline;
        constructor = TankUnit::create;
        envRequired = DSEnv.underwaterWarm;
    }
}
