package ds.world.type.entities.dsUnits;

import arc.graphics.Color;
import ds.content.dsFx;
import ds.world.graphics.dsPal;
import ds.world.meta.dsEnv;
import ds.world.type.entities.dsUnitType;
import mindustry.entities.Effect;
import mindustry.entities.abilities.MoveEffectAbility;

public class SubmarineUnitType extends dsUnitType{
    public float bubblesInterval = 2;
    public Effect bubbleEffect = dsFx.dsMoveEffect;

    public SubmarineUnitType(String name) {
        super(name);
        abilities.add(new MoveEffectAbility(0, -engineOffset, Color.white, bubbleEffect, bubblesInterval){{
            minVelocity = 0.5f;
        }});
        envRequired = dsEnv.underwaterWarm;
        outlineColor = dsPal.dsUnitOutline;
        flying = true;
        engineOffset = 3;
        engineSize = 0;
        omniMovement = false;
        drawCell = false;
    }
}
