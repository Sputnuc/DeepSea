package ds;

import arc.Events;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import ds.content.ContentLoader;
import ds.content.DSMusicLoader;
import ds.type.entities.Cable;
import ds.world.global.CableProcess;
import ds.world.global.DSWorldProcessor;
import ds.world.graphics.DSEnvRenderers;
import mindustry.Vars;
import mindustry.core.GameState;
import mindustry.game.EventType;
import mindustry.gen.Posc;
import mindustry.mod.*;

public class DeepSea extends Mod{
    public DeepSea(){
        ClassMap.classes.put("WorldProcessor", DSWorldProcessor.class);
    }

    @Override
    public void loadContent(){
        DSMusicLoader.load();
        ContentLoader.load();
        DSWorldProcessor.load();
        DSEnvRenderers.init();
        Events.on(EventType.ClientLoadEvent.class, e -> {
            IconLoader.loadIcons();
            DSWorldProcessor.init();
        });
    }

    @Override
    public void init() {
        Cable.load();
        //DSMusicLoader.attach();
        if (!Vars.headless && Vars.ui != null) {
            Events.on(EventType.ClientLoadEvent.class, e -> DSSetting.init());
            /*
            Events.on(EventType.ClientLoadEvent.class, e -> {

                DSSoundControl.loadSoundControl();
            });
             */
        }

        Events.run(EventType.Trigger.update, ()->{
            DSWorldProcessor.update();
        });
        //Test
        /*
        Events.on(EventType.WorldLoadEvent.class, e->{
            for(int i = 0; i < 970; i++){
                CableProcess.addCable(new Cable(
                        new Vec2(Mathf.random()*200 * 8, Mathf.random()*200 * 8),
                        new Vec2(Mathf.random()*200 * 8, Mathf.random()*200 * 8)
                ));
            };
        });
         */
        Events.on(EventType.StateChangeEvent.class, e ->{
            if (e.from != GameState.State.menu && e.to == GameState.State.menu) {
                DSWorldProcessor.resetProcesses();
            }
        });
    }
}