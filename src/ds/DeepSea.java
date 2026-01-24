package ds;

import arc.Events;
import ds.content.ContentLoader;
import ds.content.dsMusicLoader;
import ds.world.graphics.dsEnvRenderers;
import mindustry.game.EventType;
import mindustry.mod.*;

public class DeepSea extends Mod{
    public DeepSea(){}

    @Override
    public void loadContent(){
        ContentLoader.load();
        dsEnvRenderers.init();

        Events.on(EventType.ClientLoadEvent.class, e -> dsMusicLoader.attach());
    }
}