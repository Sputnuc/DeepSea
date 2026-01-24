package ds.world.type.entities.dsUnits;

import arc.graphics.Color;
import ds.content.dsFx;
import ds.world.graphics.DSPal;
import ds.world.meta.DSEnv;
import ds.world.type.entities.dsUnitType;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.abilities.MoveEffectAbility;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;

public class SubmarineUnitType extends dsUnitType{
    public float bubblesInterval = 2;
    public Effect bubbleEffect = dsFx.dsMoveEffect;

    public SubmarineUnitType(String name) {
        super(name);
        abilities.add(new MoveEffectAbility(0, -engineOffset, Color.white, bubbleEffect, bubblesInterval){{
            minVelocity = 0.5f;
        }});
        envRequired = DSEnv.underwaterWarm;
        outlineColor = DSPal.dsUnitOutline;
        flying = true;
        engineOffset = 3;
        engineSize = 0;
        omniMovement = false;
        drawCell = false;
    }
}
