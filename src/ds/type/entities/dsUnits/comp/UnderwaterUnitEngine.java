package ds.type.entities.dsUnits.comp;

import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import ds.content.DSFx;
import mindustry.Vars;
import mindustry.entities.Effect;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

public class UnderwaterUnitEngine extends UnitType.UnitEngine{
    public float engineEffectInterval = 0.65f;
    public float engineEffectChance = 0.75f;
    public float engineXRand = 0;
    public float zLayer = -1;
    public Effect engineEffect = DSFx.dsMoveEffect;
    protected float timer = 0;

    public UnderwaterUnitEngine(float x, float y, float radius, float rotation){
        super(x, y, radius, rotation);
    }

    public UnderwaterUnitEngine(float x, float y, float rotation){
        this(x, y, 1, rotation);
    }

    public UnderwaterUnitEngine(float x, float y, float rotation, float engineEffectInterval, float engineEffectChance, Effect engineEffect){
        this(x, y, rotation);
        this.engineEffectInterval = engineEffectInterval;
        this.engineEffectChance = engineEffectChance;
        this.engineEffect = engineEffect;
    }

    public UnderwaterUnitEngine(float x, float y, float rotation, float engineEffectInterval, float engineEffectChance, Effect engineEffect, float engineXRand){
        this(x, y, rotation);
        this.engineEffectInterval = engineEffectInterval;
        this.engineEffectChance = engineEffectChance;
        this.engineEffect = engineEffect;
        this.engineXRand = engineXRand;
    }

    protected float calcUnitSpeedEff(Unit unit){
        UnitType type = unit.type;
        return Mathf.curve(unit.vel.len2(), 0.001f, type.speed * type.speed);
    }

    @Override
    public void draw(Unit unit){
        float eff = calcUnitSpeedEff(unit);
        if(eff > 0.001f && !Vars.state.isPaused()){
            timer += Time.delta * eff;
            if(timer > engineEffectInterval){
                if(Mathf.chance(engineEffectChance)) {
                    float ex = unit.x + Angles.trnsx(unit.rotation - 90, x + engineXRand, y);
                    float ey = unit.y + Angles.trnsy(unit.rotation - 90, x + engineXRand, y);
                    engineEffect.at(ex, ey, unit.rotation+180);
                }
                timer = 0;
            }
        }
    }
}
