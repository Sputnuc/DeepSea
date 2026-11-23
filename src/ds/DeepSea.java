package ds;

import arc.Events;
import deepsea.gen.EntityRegistry;
import ds.content.ContentLoader;
import ds.content.DSMusicLoader;
import ds.type.entities.Cable;
import ds.wprocesses.DSWorldProcessHandler;
import ds.world.graphics.DSEnvRenderers;
import ds.world.wproc.CustomLogic;
import mindustry.Vars;
import mindustry.core.GameState;
import mindustry.game.EventType;
import mindustry.mod.*;

public class DeepSea extends Mod{
    public DeepSea(){
        ClassMap.classes.put("WorldProcessor", DSWorldProcessHandler.class);
    }

    @Override
    public void loadContent(){
        EntityRegistry.register();

        ContentLoader.load();
        DSWorldProcessHandler.load();
        DSEnvRenderers.init();
        Events.on(EventType.ClientLoadEvent.class, e -> {
            IconLoader.loadIcons();
            DSWorldProcessHandler.init();
        });
    }

    @Override
    public void init() {
        Cable.load();
        //DSMusicLoader.attach();
        if (!Vars.headless && Vars.ui != null) {
            Events.on(EventType.ClientLoadEvent.class, e -> {
                UI.init();
                DSSetting.init();
                CustomLogic.init();
                DSMusicLoader.load();
                DSMusicLoader.attach();
            });
        }

        Events.run(EventType.Trigger.update, DSWorldProcessHandler::update);
        Events.on(EventType.StateChangeEvent.class, e ->{
            if (e.from != GameState.State.menu && e.to == GameState.State.menu) {
                DSWorldProcessHandler.resetProcesses();
            }
        });
    }
}