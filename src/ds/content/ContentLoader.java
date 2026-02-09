package ds.content;

import ds.content.blocks.dsBlocksLoader;
import ds.content.items.dsItemLoader;
import ds.content.liquids.piLiquids;
import ds.content.planets.dsPlanets;
import ds.content.planets.piSectors;
import ds.content.units.piUnits;

public class ContentLoader {
    public static void load(){
        dsAttributes.load();
        SchematicsLoader.load();
        dsSounds.load();
        dsItemLoader.load();
        piLiquids.load();
        piUnits.loadUnits();
        dsBlocksLoader.load();

        //end load
        dsPlanets.loadContent();
        piSectors.load();
        pi312TechTree.load();
    }
}
