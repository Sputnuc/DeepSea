package ds.world.global;

import arc.Events;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Time;
import ds.content.units.PiUnits;
import mindustry.Vars;
import mindustry.entities.Units;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

public class FishSpawnProcess extends WorldProcess{

    public FishSpawnProcess(String name){
        super(name);
    }

    public Seq<UnitType> spawnUnits = new Seq<>();

    public Team team;

    public int maxUnits = 20;

    public float interval = 300;

    protected float timer;

    @Override
    public void init(){
        super.init();
        Events.on(EventType.WorldLoadEvent.class, e->{
            team = Vars.state.rules.waveTeam;
            timer = 0;
        });
    }

    @Override
    public void load(){
        super.load();
        spawnUnits.add(PiUnits.untitledFish);
    }

    @Override
    public void update(){
        timer += Time.delta;
        if(timer >= interval) {
            timer = 0;
            if(team != null) {
                for (UnitType unit : spawnUnits) {
                    Unit u = null;
                    if (getRandomValue() < 0.2f && team.data().countType(unit) < maxUnits) {
                        u = unit.create(team);
                        logProcess("spawned: " + u.type.name);
                        u.set(new Vec2(getRandomPos()[0] * 8, getRandomPos()[1] * 8));
                        u.add();
                    }
                    return;
                }
            }
        }
    }
}
