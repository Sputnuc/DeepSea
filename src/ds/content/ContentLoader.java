package ds.content;

import deepsea.gen.EntityRegistry;
import ds.content.blocks.DSBlocksLoader;
import ds.content.items.DSItemLoader;
import ds.content.liquids.PiLiquids;
import ds.content.planets.DSPlanets;
import ds.content.planets.DSSectorPresets;
import ds.content.units.DSUnits;
import ds.wprocesses.CableProcess;
import ds.wprocesses.CasingProcess;
import ds.wprocesses.DSWorldProcessHandler;
import ds.wprocesses.UnitBarProcess;

public class ContentLoader {
    public static void load(){
        DSWorldProcessHandler.addProcess(new CableProcess("Cable process"));
        DSWorldProcessHandler.addProcesses(new UnitBarProcess("UnitBar process"));
        DSWorldProcessHandler.addProcess(new CasingProcess("Casing process"));

        DSStatusEffects.load();
        DSAttributes.load();
        SchematicsLoader.load();
        DSSounds.load();
        DSBullets.load();
        DSItemLoader.load();

        PiLiquids.load();
        DSUnits.loadUnits();
        EntityRegistry.registerUnits();
        DSBlocksLoader.load();

        //end load
        DSPlanets.loadContent();
        DSSectorPresets.load();
        DSTechTree.load();
    }
}
