package ds.type.entities.dsUnits;

import arc.math.Mathf;
import arc.struct.Seq;
import ds.content.DSFx;
import ds.world.graphics.DSPal;
import ds.world.meta.DSEnv;
import ds.type.entities.DSUnitType;
import ds.type.entities.dsUnits.parts.UnderwaterUnitEngine;
import mindustry.ai.types.CommandAI;
import mindustry.entities.Effect;
import mindustry.game.Team;
import mindustry.gen.TimedKillc;
import mindustry.gen.Unit;

public class SubmarineUnitType extends DSUnitType {
    public float bubblesInterval = 2;
    public Effect bubbleEffect = DSFx.dsMoveEffect;
    public Seq<UnderwaterUnitEngine> undEngines = new Seq<>();

    public SubmarineUnitType(String name) {
        super(name);
        envRequired = DSEnv.underwaterWarm;
        outlineColor = DSPal.dsUnitOutline;
        flying = true;
        engineOffset = 3;
        engineSize = 0;
        omniMovement = false;
        accel = 1 / (hitSize * 10);
        drag = accel * 0.9f;
    }

    public void setUndEngines(float x, float y, float rotation, boolean mirror){
        if(!mirror){
            undEngines.add(new UnderwaterUnitEngine(x, y, rotation));
        }else {
            for (int s : Mathf.signs){
                undEngines.add(new UnderwaterUnitEngine(x * s, y, rotation * s));
            }
        }
    }
    @Override
    public Unit create(Team team){
        Unit unit = constructor.get();
        unit.team = team;
        unit.setType(this);
        if(unit.controller() instanceof CommandAI command && defaultCommand != null){
            command.command = defaultCommand;
        }
        for(var ability : unit.abilities){
            ability.created(unit);
        }
        unit.elevation = flying ? 1f : 0;
        unit.heal();
        if(unit instanceof TimedKillc u){
            u.lifetime(lifetime);
        }
        return unit;
    }
}
