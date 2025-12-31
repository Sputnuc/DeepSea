package ds.world.type.entities.dsUnits;

import mindustry.type.UnitType;

public class EntityUnitType extends UnitType {
    public EntityUnitType(String name) {
        super(name);
        killable = false;
        isEnemy = false;
        targetable = false;
        health = 9999999;
    }
}
