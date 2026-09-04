package ds.type.entities.dsUnits;

import arc.math.Mathf;
import arc.struct.Seq;
import ds.content.DSFx;
import ds.type.entities.dsUnits.comp.UnderwaterUnitEngine;
import ds.world.meta.DSEnv;
import mindustry.entities.abilities.MoveEffectAbility;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.type.unit.MissileUnitType;

public class TorpedoUnitType extends MissileUnitType {
    public Seq<UnderwaterUnitEngine> undEngines = new Seq<>();
    public TorpedoUnitType(String name) {
        super(name);
        health = 40;
        envRequired = DSEnv.underwaterWarm;
        trailLength = 0;
        engineSize = 0;
        lowAltitude = true;
        engineOffset = 0;
        loopSound = Sounds.none;
        outlineColor = Pal.darkOutline;
        setUndEngines(0, -2, 180, false);
    }

    public void setUndEngines(float x, float y, float rotation, boolean mirror){
        if(!mirror){
            undEngines.add(new UnderwaterUnitEngine(x, y, rotation));
        }else {
            for (int s : Mathf.signs){
                undEngines.add(new UnderwaterUnitEngine(x * s, y, rotation * s));
            }
        }
    }

    @Override
    public void update(Unit unit){
        super.update(unit);
        if(!undEngines.isEmpty()){
            for(var e : undEngines){
                e.update(unit);
            }
        }
    }
}
