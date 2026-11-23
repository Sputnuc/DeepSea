package ds.type.dsUnitTypes;

import arc.graphics.Color;
import mindustry.gen.MechUnit;

public class DSMechUnitType extends DSUnitType {
    public DSMechUnitType(String name) {
        super(name);
        mechLegColor = Color.valueOf("131623");
        constructor = MechUnit::create;
    }
}
