package ds.newContent.type.entities;

import ds.newContent.ai.PassiveAi;
import mindustry.entities.units.AIController;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;

public class FaunaUnitType extends UnitType {
    public FaunaUnitType(String name) {
        super(name);
        aiController = PassiveAi::new;
        canAttack = false;
        isEnemy = false;
        targetable = false;
        drawCell = false;
        engineSize = 0;
        engineOffset = 0;
    }
}
