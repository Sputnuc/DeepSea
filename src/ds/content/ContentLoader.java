package ds.content;

import ds.content.blocks.DSBlocksLoader;
import ds.content.items.DSItemLoader;
import ds.content.liquids.PiLiquids;
import ds.content.planets.DSPlanets;
import ds.content.planets.PiSectors;
import ds.content.units.PiUnits;
import ds.type.entities.Cable;
import ds.world.global.*;

public class ContentLoader {
    public static void load(){
        DSWorldProcessor.addProcess(new CableProcess("Cable processor"));

        DSStatusEffects.load();
        DSAttributes.load();
        SchematicsLoader.load();
        DSSounds.load();
        DSItemLoader.load();

        PiLiquids.load();
        PiUnits.loadUnits();
        DSBlocksLoader.load();

        //end load
        DSPlanets.loadContent();
        PiSectors.load();
        DSTechTree.load();
    }
}
