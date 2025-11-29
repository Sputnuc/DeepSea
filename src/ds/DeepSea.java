package ds;

import arc.Events;
import ds.content.ContentLoader;
import ds.content.dsMusicLoader;
import ds.world.draw.DrawWire;
import ds.world.graphics.DSEnvRenderers;
import mindustry.entities.Effect;
import mindustry.game.EventType;
import mindustry.mod.*;

public class DeepSea extends Mod{
    public DeepSea(){}

    @Override
    public void loadContent(){
        ContentLoader.load();
        DSEnvRenderers.init();

        Events.on(EventType.ClientLoadEvent.class, e -> dsMusicLoader.attach());
    }
}