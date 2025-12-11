package ds.world.type.entities.dsUnits;

import arc.graphics.Color;
import ds.world.type.entities.dsUnitType;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.abilities.MoveEffectAbility;

public class SubmarineUnitType extends dsUnitType {
    public float bubblesInterval = 5;
    public Effect bubbleEffect = Fx.bubble;

    public SubmarineUnitType(String name) {
        super(name);
        abilities.add(new MoveEffectAbility(0, -engineOffset, Color.white, bubbleEffect, bubblesInterval){{
            minVelocity = 0.5f;
        }});
        flying = true;
        engineOffset = 3;
        engineSize = 0;
        omniMovement = false;
        drawCell = false;
    }
}
