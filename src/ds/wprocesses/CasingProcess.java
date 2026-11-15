package ds.wprocesses;

import arc.Events;
import ds.type.entities.Casing;
import mindustry.core.GameState;
import mindustry.game.EventType;

public class CasingProcess extends WorldProcess{
    public CasingProcess(String name) {
        super(name);
    }

    @Override
    public void init(){
        super.init();
        Events.on(EventType.StateChangeEvent.class, e ->{
            if (e.from != GameState.State.menu && e.to == GameState.State.menu) {
                Casing.activeCasings.clear();
            }
        });
    }

    @Override
    public void update(){
        Casing.updateCasings();
    }

    @Override
    public void visualUpdate(){
        Casing.drawCasings();
    }
}
