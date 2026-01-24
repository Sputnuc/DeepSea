package ds.world.type.entities.dsUnits;

import ds.world.type.entities.dsUnitType;
import mindustry.gen.MechUnit;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;

public class dsMechUnitType extends dsUnitType {
    public dsMechUnitType(String name) {
        super(name);
        constructor = MechUnit::create;
    }
}
