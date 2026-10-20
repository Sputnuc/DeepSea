package ds;

import arc.Events;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import deepsea.gen.EntityRegistry;
import ds.content.ContentLoader;
import ds.content.DSMusicLoader;
import ds.type.entities.Cable;
import ds.world.global.CableProcess;
import ds.world.global.DSWorldProcessor;
import ds.world.graphics.DSEnvRenderers;
import ds.world.wprocess.CustomLogic;
import mindustry.Vars;
import mindustry.core.GameState;
import mindustry.game.EventType;
import mindustry.gen.EntityMapping;
import mindustry.gen.Posc;
import mindustry.mod.*;

public class DeepSea extends Mod{
    public DeepSea(){
        ClassMap.classes.put("WorldProcessor", DSWorldProcessor.class);
    }

    @Override
    public void loadContent(){
        EntityRegistry.register();

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
            Events.on(EventType.ClientLoadEvent.class, e -> {
                DSSetting.init();
                CustomLogic.init();
            });
        }

        Events.run(EventType.Trigger.update, DSWorldProcessor::update);
        Events.on(EventType.StateChangeEvent.class, e ->{
            if (e.from != GameState.State.menu && e.to == GameState.State.menu) {
                DSWorldProcessor.resetProcesses();
            }
        });
    }
}