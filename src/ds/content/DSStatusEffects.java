package ds.content;

import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import ds.type.entities.dsUnits.DSMechUnitType;
import ds.type.entities.dsUnits.SubmarineUnitType;
import ds.world.meta.DSEnv;
import mindustry.Vars;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.units.StatusEntry;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;
import mindustry.world.meta.Env;

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
                if(!(Vars.state.rules.hasEnv(DSEnv.underwaterWarm) || Vars.state.rules.hasEnv(Env.underwater))) {
                    unit.unapply(this);
                    return;
                }
                    if (unit.type instanceof SubmarineUnitType) {
                        float mult = Math.max((1 - (2 * unit.health / unit.maxHealth)) * -1, 0);
                        float odamage = 10f / 60f * mult;
                        if(mult > 0.001f) unit.damageContinuous(odamage);
                        unit.speedMultiplier = Math.max(0.25f, unit.health / unit.maxHealth);
                    } else if (unit.type instanceof DSMechUnitType){
                        float mult = Math.max((1 - (2 * unit.health / unit.maxHealth)) * -1, 0);
                        float odamage = 20f / 60 * (mult * 0.5f);
                        if(mult > 0.001f) unit.damageContinuous(odamage);
                    } else unit.unapply(this);
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
