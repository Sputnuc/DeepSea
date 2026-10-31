package ds.type.dsUnitTypes;

import ds.content.DSFx;
import ds.world.ai.PassiveAi;
import ds.type.entities.DSUnitType;
import mindustry.gen.Sounds;

public class FaunaUnitType extends DSUnitType {
    public FaunaUnitType(String name) {
        super(name);
        lightRadius = 0;
        lightOpacity = 0;
        aiController = PassiveAi::new;
        canAttack = false;
        isEnemy = false;
        targetable = false;
        drawCell = false;
        engineSize = 0;
        engineOffset = 0;
        faceTarget = false;
        controller = u -> new PassiveAi();
        deathExplosionEffect = DSFx.waterBlood;
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
        lowAltitude = true;
    }
}
