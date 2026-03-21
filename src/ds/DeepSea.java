package ds;

import arc.Events;
import ds.content.ContentLoader;
import ds.content.DSMusicLoader;
import ds.world.graphics.DSEnvRenderers;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.mod.*;

public class DeepSea extends Mod{
    public DeepSea(){}

    @Override
    public void loadContent(){
        DSMusicLoader.load();
        ContentLoader.load();
        DSEnvRenderers.init();
        Events.on(EventType.ClientLoadEvent.class, e -> {
            IconLoader.loadIcons();
        });
    }

    @Override
    public void init() {
        DSMusicLoader.attach();
        if (!Vars.headless && Vars.ui != null) {
            Events.on(EventType.ClientLoadEvent.class, e -> DSSetting.init());
            Events.on(EventType.ClientLoadEvent.class, e -> {

                DSSoundControl.loadSoundControl();
            });
        }
    }
}