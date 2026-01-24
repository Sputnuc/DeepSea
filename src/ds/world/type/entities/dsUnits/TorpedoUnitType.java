package ds.world.type.entities.dsUnits;

import ds.content.dsFx;
import ds.world.meta.dsEnv;
import mindustry.entities.abilities.MoveEffectAbility;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.unit.MissileUnitType;

public class TorpedoUnitType extends MissileUnitType {
    public TorpedoUnitType(String name) {
        super(name);
        health = 40;
        envRequired = dsEnv.underwaterWarm;
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
