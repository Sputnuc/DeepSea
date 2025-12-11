package ds.world.type.entities.dsUnits;

import arc.audio.Sound;
import ds.content.dsFx;
import ds.world.meta.DSEnv;
import mindustry.entities.abilities.MoveEffectAbility;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.unit.MissileUnitType;

public class TorpedoUnitType extends MissileUnitType {
    public TorpedoUnitType(String name) {
        super(name);
        missileAccelTime = 60;
        health = 20;
        envRequired = DSEnv.underwaterWarm;
        trailLength = 0;
        engineSize = 0;
        engineOffset = 0;
        loopSound = Sounds.none;
        outlineColor = Pal.darkOutline;
        abilities.add(new MoveEffectAbility(){{
            effect = dsFx.torpedoTrail;
            minVelocity = 0.05f;
        }});
    }
}
