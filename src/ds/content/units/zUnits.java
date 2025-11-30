package ds.content.units;

import ds.world.type.entities.dsUnits.FaunaUnitType;
import ds.world.type.entities.dsUnits.SubmarineUnitType;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;
import mindustry.type.Weapon;

import static mindustry.Vars.tilesize;

public class zUnits {
    public static UnitType
            //Core units
            moment,
            //fauna
            testfish;
    public static void loadUnits(){
        moment = new SubmarineUnitType("moment"){{
            constructor = UnitEntity::create;
            speed = 3;
            buildRange = 20 * tilesize;
            buildSpeed = 1;
            mineSpeed = 5.5f;
            mineFloor = true;
            mineWalls = true;
            mineTier = 3;
        }};


        testfish = new FaunaUnitType("test-fish"){{
            speed = 1;
            health = 30;
            constructor = UnitEntity::create;
            flying = true;
        }};
    }
}
