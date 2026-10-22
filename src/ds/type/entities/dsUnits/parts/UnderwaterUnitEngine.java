package ds.type.entities.dsUnits.parts;

import arc.math.Mathf;
import arc.util.Log;
import arc.util.Time;
import arc.util.Tmp;
import deepsea.gen.SubmarineEnginec;
import ds.content.DSFx;
import mindustry.Vars;
import mindustry.entities.Effect;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

//Content class for UnitType
public class UnderwaterUnitEngine{
    public float x, y;
    public float rotation;
    public float engineEffectInterval = 2f;
    public float engineEffectChance = 1f;
    public float engineXRand = 0;
    public Effect engineEffect = DSFx.dsMoveEffect;


    public UnderwaterUnitEngine(float x, float y, float rotation){
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    public void update(Unit unit){
        if(!unit.inFogTo(Vars.player.team())){
            if(Mathf.random(1) <= engineEffectChance){
                Tmp.v1.trns(unit.rotation - 90f, x, y);
                engineEffect.at(Tmp.v1.x + unit.x, Tmp.v1.y + unit.y, rotation + unit.rotation, 1f);
            }
        }
    }
}
