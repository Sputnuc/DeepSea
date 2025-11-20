package ds.content.units;

import ds.newContent.ai.PassiveAi;
import ds.newContent.type.entities.FaunaUnitType;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;

public class zUnits {
    public static UnitType testfish;
    public static void loadUnits(){
        testfish = new FaunaUnitType("test-fish"){{
            speed = 1;
            health = 30;
            constructor = UnitEntity::create;
            flying = true;
        }};
    }
}
