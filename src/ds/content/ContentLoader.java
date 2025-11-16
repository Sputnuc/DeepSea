package ds.content;

import ds.content.blocks.dsBlocksLoader;
import ds.content.items.dsItemLoader;
import ds.content.planets.dsPlanets;

public class ContentLoader {
    public static void load(){
        dsItemLoader.load();
        dsBlocksLoader.load();

        //end load
        dsPlanets.loadContent();
    }
}
