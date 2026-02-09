package ds;

import arc.Events;
import ds.content.ContentLoader;
import ds.content.dsMusicLoader;
import ds.world.graphics.dsEnvRenderers;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.mod.*;

public class DeepSea extends Mod{
    public DeepSea(){}

    @Override
    public void loadContent(){
        ContentLoader.load();
        dsEnvRenderers.init();
    }

    /*
    TO DO - Write music for mod
    @Override
    public void init() {
        if (!Vars.headless && Vars.ui != null) {
            Events.on(EventType.MusicRegisterEvent.class, e -> dsMusicLoader.load());
            Events.on(EventType.ClientLoadEvent.class, e -> dsMusicLoader.attach());
        }
    }
     */
}