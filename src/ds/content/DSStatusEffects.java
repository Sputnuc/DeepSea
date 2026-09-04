package ds.content;

import arc.struct.Seq;
import arc.util.Time;
import ds.type.entities.dsUnits.SubmarineUnitType;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.units.StatusEntry;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;

public class DSStatusEffects {
    public static StatusEffect waterLeak;
    public static void load(){
        waterLeak = new dsStatusEffect("water-leak"){{
            effect = new ParticleEffect(){{
                colorFrom = Pal.water;
                colorTo = Pal.water;
                sizeFrom = 1;
                sizeTo = 0;
                lifetime = 30;
            }};
        }
            @Override
            public void update(Unit unit, StatusEntry entry){
                super.update(unit, entry);
                    if (unit.type instanceof SubmarineUnitType) {
                        unit.damageContinuousPierce((unit.health / (unit.maxHealth * 2)) / 2.5f * Time.delta);
                        unit.speedMultiplier = Math.max(0.25f, unit.health / unit.maxHealth);
                    } else unit.damageContinuousPierce((unit.health / (unit.maxHealth * 2)) / 3.4f * Time.delta);
            }
        };
    }

    public static class dsStatusEffect extends StatusEffect {
        public Seq<StatusEffect> override = new Seq<>();
        public dsStatusEffect(String name) {
            super(name);
            outline = false;
        }

        @Override
        public void update(Unit unit, StatusEntry entry) {
            super.update(unit, entry);
            override.each(unit::unapply);
        }

    }
}
