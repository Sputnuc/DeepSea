package ds.content.liquids;

import arc.graphics.Color;
import mindustry.type.Liquid;

public class zLiquids {
    public static Liquid hydrogenSulfide, sulfuricAcid;
    public static void load(){
        hydrogenSulfide = new Liquid("hydrogen-sulfide", Color.valueOf("d7dba9")){{
            gas = true;
            alwaysUnlocked = false;
        }};
        sulfuricAcid = new Liquid("sulfuric-acid", Color.valueOf("d5fa84")){{
            alwaysUnlocked = false;
        }};
    }
}
