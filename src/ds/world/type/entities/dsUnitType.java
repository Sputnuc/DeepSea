package ds.world.type.entities;

import arc.graphics.Color;
import ds.world.graphics.DSPal;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;

public class dsUnitType extends UnitType {

    public dsUnitType(String name) {
        super(name);
        outlineColor = DSPal.dsUnitOutline;
    }
}
