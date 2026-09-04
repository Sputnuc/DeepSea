package ds.type.entities.dsUnits.comp;

import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import ds.content.DSFx;
import mindustry.Vars;
import mindustry.entities.Effect;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

public class UnderwaterUnitEngine{
    public float x, y;
    public float rotation;
    public float engineEffectInterval = 2f;
    public float engineEffectChance = 1f;
    public float engineXRand = 0;
    public Effect engineEffect = DSFx.dsMoveEffect;
    private float counter = 0;
    private float efficiency;


    public UnderwaterUnitEngine(float x, float y, float rotation){
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    public void update(Unit unit){
        UnitType type = unit.type;
        efficiency = unit.moving() ? Mathf.approachDelta(efficiency, 1,1) : Mathf.approachDelta(efficiency, 0,1);

        if(efficiency > 0.05f && !unit.inFogTo(Vars.player.team())){
            counter += Time.delta * efficiency;
            if(counter > engineEffectInterval && Mathf.chanceDelta(engineEffectChance)){
                Tmp.v1.trns(unit.rotation - 90f, x, y);
                engineEffect.at(Tmp.v1.x + unit.x, Tmp.v1.y + unit.y, rotation + unit.rotation);
                counter %= engineEffectInterval;
            }
        }
    }
}
