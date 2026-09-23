package ds.content;

import ds.content.blocks.DSBlocksLoader;
import ds.content.items.DSItemLoader;
import ds.content.liquids.PiLiquids;
import ds.content.planets.DSPlanets;
import ds.content.planets.DSSectorPresets;
import ds.content.units.PiUnits;
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
        DSSectorPresets.load();
        DSTechTree.load();
    }
}
