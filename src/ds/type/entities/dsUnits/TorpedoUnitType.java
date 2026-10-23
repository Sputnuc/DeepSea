package ds.type.entities.dsUnits;

import arc.math.Mathf;
import arc.struct.Seq;
import deepsea.gen.TorpedoUnitc;
import ds.type.entities.dsUnits.parts.UnderwaterUnitEngine;
import ds.world.meta.DSEnv;
import ent.anno.Annotations;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
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

    public void setUndEngines(float x, float y, float rotation, boolean mirror) {
        if (!mirror) {
            undEngines.add(new UnderwaterUnitEngine(x, y, rotation) {{
                engineEffectChance = 0.25f;
            }});
        } else {
            for (int s : Mathf.signs) {
                undEngines.add(new UnderwaterUnitEngine(x * s, y, rotation * s) {{
                    engineEffectChance = 0.25f;
                }});
            }
        }
    }
}
