package ds.type;

import mindustry.ctype.ContentType;
import mindustry.mod.Mods;
import mindustry.type.Planet;
import mindustry.type.SectorPreset;

public class DSSectorPreset extends SectorPreset {
    public DSSector sector;

    public DSSectorPreset(String name, Planet planet, int sector) {
        super(name, planet, sector);
    }
}
