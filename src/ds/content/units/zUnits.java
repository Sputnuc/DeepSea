package ds.content.units;

import ds.newContent.type.entities.dsUnitType;
import ds.newContent.type.entities.dsUnits.FaunaUnitType;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;

import static mindustry.Vars.tilesize;

public class zUnits {
    public static UnitType
            //Core units
            moment,
            //fauna
            testfish;
    public static void loadUnits(){
        moment = new dsUnitType("moment"){{
            constructor = UnitEntity::create;
            speed = 4;
            buildRange = 20 * tilesize;
            buildSpeed = 1;
            mineSpeed = 2.5f;
            mineFloor = true;
            mineWalls = true;
        }};


        testfish = new FaunaUnitType("test-fish"){{
            speed = 1;
            health = 30;
            constructor = UnitEntity::create;
            flying = true;
        }};
    }
}
