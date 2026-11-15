package ds.wprocesses;

import arc.Events;
import arc.math.Mathf;
import arc.struct.Seq;
import deepsea.gen.TestRadialBarc;
import mindustry.core.GameState;
import mindustry.game.EventType;
import mindustry.gen.Unit;

public class UnitBarProcess extends WorldProcess{

    public static final Seq<BarData> activeBars = new Seq<>();

    public UnitBarProcess(String name) {
        super(name);
    }

    public static void addBar(Unit u){
        if(u instanceof TestRadialBarc) activeBars.add(new BarData(u));
    }

    public void applyBarDraw(){
        for(BarData d : activeBars){
            if(d.u instanceof TestRadialBarc c) c.drawRadial(d.visualHealth);
        }
    }

    @Override
    public void forceUpdate(){
        for(BarData d : activeBars){
            if(d.u.dead() || !d.u.isValid()) activeBars.remove(d);
            d.updateHealth();
        }
    }

    @Override
    public void visualUpdate(){
        applyBarDraw();
    }

    public static class BarData{
        private final Unit u;
        public float visualHealth;
        public float barSpeed = 0.01f;

        public BarData(Unit unit){
            u = unit;
            visualHealth = 1;
        }

        public void updateHealth(){
            if(!u.isValid() || u.dead()) return;
            visualHealth = Mathf.approachDelta(visualHealth, u.health() / u.maxHealth(), 0.01f);
        }
    }

    public void init(){
        Events.on(EventType.StateChangeEvent.class, e ->{
            if (e.from != GameState.State.menu && e.to == GameState.State.menu) {
                activeBars.clear();
            }
        });
    }
}
