package ds.content.planets;

import mindustry.type.SectorPreset;

import static ds.content.planets.dsPlanets.*;

public class piSectors {
    public static SectorPreset theBeginning, canyon;
    public static void load(){
        theBeginning = new SectorPreset("the-beginning", pi312, 10){{
            alwaysUnlocked = true;
            difficulty = 1;
            captureWave = 10;
            allDatabaseTabs = true;
            showSectorLandInfo = true;
        }};
        canyon = new SectorPreset("canyon", pi312, 16){{
            difficulty = 1;
            captureWave = 15;
        }};
    }
}
