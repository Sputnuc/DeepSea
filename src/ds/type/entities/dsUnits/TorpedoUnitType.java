package ds.type.entities.dsUnits;

import ds.content.DSFx;
import ds.type.entities.dsUnits.comp.UnderwaterUnitEngine;
import ds.world.meta.DSEnv;
import mindustry.entities.abilities.MoveEffectAbility;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.unit.MissileUnitType;

public class TorpedoUnitType extends MissileUnitType {
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
        engines.add(new UnderwaterUnitEngine(0, -4, 180){{
            engineEffectChance = 0.45f;
        }});
    }
}
