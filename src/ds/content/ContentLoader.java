package ds.content;

import ds.content.blocks.dsBlocksLoader;
import ds.content.items.dsItemLoader;
import ds.content.liquids.zLiquids;
import ds.content.planets.dsPlanets;
import ds.content.planets.dsZTechTree;
import ds.content.units.zUnits;

public class ContentLoader {
    public static void load(){
        SchematicsLoader.load();
        dsSounds.load();
        dsItemLoader.load();
        zLiquids.load();
        zUnits.loadUnits();
        dsBlocksLoader.load();

        //end load
        dsPlanets.loadContent();
        dsZTechTree.load();
        dsMusicLoader.load();
    }
}
