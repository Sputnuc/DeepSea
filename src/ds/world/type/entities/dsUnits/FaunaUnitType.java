package ds.world.type.entities.dsUnits;

import arc.audio.Sound;
import ds.content.dsFx;
import ds.world.ai.PassiveAi;
import ds.world.type.entities.dsUnitType;
import mindustry.gen.Sounds;

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
        deathExplosionEffect = dsFx.waterBlood;
        deathShake = 0;
        deathSound = Sounds.none;
        fallSpeed = 999;
        crushDamage = 0;
        crashDamageMultiplier = 0;
        useUnitCap = false;
        allowedInPayloads = false;
        playerControllable = false;
        logicControllable = false;
        drawMinimap = false;
        createWreck = false;
        createScorch = false;
        targetPriority = -1f;
        hidden = true;
        hoverable = false;
    }
}
