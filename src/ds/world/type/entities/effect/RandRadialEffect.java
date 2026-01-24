package ds.world.type.entities.effect;

import arc.graphics.Color;
import arc.math.Angles;
import arc.math.Mathf;
import mindustry.entities.effect.RadialEffect;

public class RandRadialEffect extends RadialEffect {
    public float effectSpacingRndMultiplier = 0.25f;

    @Override
    public void create(float x, float y, float rotation, Color color, Object data){
        if(!shouldCreate()) return;

        rotation += rotationOffset;

        for(int i = 0; i < amount; i++){
            effect.create(x + Angles.trnsx(rotation, lengthOffset), y + Angles.trnsy(rotation, lengthOffset), rotation + effectRotationOffset, color, data);
            rotation += rotationSpacing + rotationSpacing * Mathf.random(effectSpacingRndMultiplier);
        }
    }
}
