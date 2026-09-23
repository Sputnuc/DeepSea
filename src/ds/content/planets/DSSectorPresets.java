package ds.content.planets;

import mindustry.type.SectorPreset;

import static ds.content.planets.DSPlanets.*;

public class DSSectorPresets {
    public static SectorPreset theBeginning, crevice;
    public static void load(){
        theBeginning = new SectorPreset("the-beginning", obj312, 10){{
            alwaysUnlocked = true;
            difficulty = 1;
            captureWave = 10;
            allDatabaseTabs = true;
            showSectorLandInfo = true;
        }};

        crevice = new SectorPreset("crevice", obj312, 194){{
            difficulty = 1.5f;
            captureWave = 15;
            allDatabaseTabs = true;
            showSectorLandInfo = true;
        }};
    }
}
