package ds.type.dsUnitTypes;

import ds.world.graphics.DSPal;
import mindustry.gen.TankUnit;
import mindustry.type.unit.TankUnitType;

public class DSTankUnitType extends TankUnitType {
    public DSTankUnitType(String name) {
        super(name);
        outlineColor = DSPal.dsUnitOutline;
        constructor = TankUnit::create;
    }
}
