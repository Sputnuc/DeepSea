package ds.world.type.entities.dsUnits;

import arc.audio.Sound;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Time;
import ds.content.dsFx;
import ds.world.ai.NodeAttackAi;
import ds.world.ai.PassiveAi;
import ds.world.type.entities.dsUnitType;
import mindustry.Vars;
import mindustry.entities.Effect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.gen.Mechc;
import mindustry.gen.Segmentc;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;

import static arc.graphics.g2d.Draw.*;
import static arc.math.Angles.randLenVectors;
import static mindustry.Vars.control;

public class EntityUnitType extends dsUnitType {

    public Sound loopSound;
    public Sound attackSound;
    public float loopSoundVolume = 0.3f;
    public float attackSoundVolume = 0.3f;
    public float loopShakeIntensity = 0;

    public Effect staticEffect = dsFx.staticBlackSmoke;

    public EntityUnitType(String name) {
        super(name);
        flying = true;
        playerControllable = false;
        speed = 6;
        killable = true;
        isEnemy = false;
        targetable = false;
        health = 9999999;
        omniMovement = true;
        strafePenalty = 1;
        controller = u -> new NodeAttackAi();
        drawCell = false;
        outlines = false;
        outlineRadius = 0;
    }

    @Override
    public void init(){
        super.init();
    }

    @Override
    public void update(Unit unit){
        super.update(unit);
        if(loopSound != null) control.sound.loop(loopSound, loopSoundVolume);
        if(attackSound != null) control.sound.loop(attackSound, new Vec2(unit.x, unit.y), attackSoundVolume);
        if(loopShakeIntensity > 0) Effect.shake(loopShakeIntensity, loopShakeIntensity, new Vec2(unit.x, unit.y));
        if(staticEffect != null) staticEffect.at(unit.x, unit.y);
    }

    @Override
    public void drawBody(Unit unit){
        applyColor(unit);
        Draw.rect(region, unit.x, unit.y, 0);
        Draw.reset();
    }
}
