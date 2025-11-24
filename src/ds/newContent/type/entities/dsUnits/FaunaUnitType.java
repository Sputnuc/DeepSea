package ds.newContent.type.entities.dsUnits;

import ds.newContent.ai.PassiveAi;
import ds.newContent.type.entities.dsUnitType;

public class FaunaUnitType extends dsUnitType {
    public FaunaUnitType(String name) {
        super(name);
        aiController = PassiveAi::new;
        canAttack = false;
        isEnemy = false;
        targetable = false;
        drawCell = false;
        engineSize = 0;
        engineOffset = 0;
        faceTarget = false;
        controller = u -> new PassiveAi();
    }
}
