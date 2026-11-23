package ds.type.dsUnitTypes;

import ds.world.graphics.DSPal;
import mindustry.type.UnitType;

public class DSUnitType extends UnitType {

    public DSUnitType(String name) {
        super(name);
        outlineColor = DSPal.dsUnitOutline;
    }

    @Override
    public void setStats(){
        super.setStats();
    }
}
