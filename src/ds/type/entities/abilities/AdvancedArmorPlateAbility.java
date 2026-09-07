package ds.type.entities.abilities;

import arc.math.Mathf;
import arc.util.Time;
import mindustry.content.StatusEffects;
import mindustry.entities.abilities.ArmorPlateAbility;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;

public class AdvancedArmorPlateAbility extends ArmorPlateAbility {
    public StatusEffect plateEffect = StatusEffects.slow;
    public float endLagEffectTime = 20;

    public AdvancedArmorPlateAbility(StatusEffect plateEffect, float endLag){
        this.plateEffect = plateEffect;
        this.endLagEffectTime = endLag;
    }

    public AdvancedArmorPlateAbility(){}

    @Override
    public void update(Unit unit){
        warmup = Mathf.lerpDelta(warmup, unit.isShooting() ? 1f : 0f, 0.1f);
        unit.healthMultiplier += warmup * healthMultiplier;
        if(warmup >= 0.1f) unit.apply(plateEffect, endLagEffectTime);
    }
}
