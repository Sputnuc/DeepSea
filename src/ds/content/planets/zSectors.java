package ds.content.planets;

import mindustry.type.SectorPreset;

import static ds.content.planets.dsPlanets.*;

public class zSectors {
    public static SectorPreset theBeginning;
    public static void load(){
        theBeginning = new SectorPreset("the-beginning", z387, 10){{
            alwaysUnlocked = true;
            difficulty = 1;
            captureWave = 10;
        }};
    }
}
