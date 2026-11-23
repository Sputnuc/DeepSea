package ds.content.planets;

import mindustry.type.SectorPreset;

import static ds.content.planets.DSPlanets.*;

public class DSSectorPresets {
    public static SectorPreset diveIntoTheDarkness;
    public static void load(){
        diveIntoTheDarkness = new SectorPreset("dive-into-the-darkness", obj312, 10){{
            alwaysUnlocked = true;
            difficulty = 1;
            captureWave = 10;
            allDatabaseTabs = true;
            showSectorLandInfo = true;
        }};
    }
}
