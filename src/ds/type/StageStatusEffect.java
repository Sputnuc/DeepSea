package ds.type;

import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.units.StatusEntry;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;

public class StageStatusEffect extends StatusEffect {
    public StageStatusEffect(String name) {
        super(name);
    }
    public StageStatusEffect nextStage;

    public StageStatusEffect previousStage;

    public float defaultDuration = 60f * 5f;

    public void applyStatus(Unit unit, float duration){
        if(unit.hasEffect(this) && nextStage != null){
            unit.apply(nextStage, duration);
            unit.unapply(this);
        } else unit.apply(this, duration);
    }

    @Override
    public void onRemoved(Unit unit) {
        if(previousStage != null){
            unit.apply(previousStage, previousStage.defaultDuration);
        }
    }



}
