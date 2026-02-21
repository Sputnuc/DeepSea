package ds.content.liquids;

import arc.graphics.Color;
import arc.struct.Seq;
import ds.world.type.dsLiquid;
import mindustry.type.Liquid;

public class piLiquids {
    public static Liquid hydrogenSulfide, sulfuricAcid, oxygen;
    public static Seq<Liquid> piLiquid = new Seq<>();
    public static void load(){
        hydrogenSulfide = new dsLiquid("hydrogen-sulfide", Color.valueOf("d7dba9")){{
            gas = true;
            explosiveness = 0.65f;
            reactivity = 0.75f;
            piLiquid.add(this);
        }};
        oxygen = new dsLiquid("oxygen", Color.valueOf("f2d5d3")){{
            gas = true;
            flammability = 1.75f;
            reactivity = 0.85f;
            piLiquid.add(this);
        }};
        sulfuricAcid = new dsLiquid("sulfuric-acid", Color.valueOf("d5fa84")){{
            exothermic = true;
            reactivity = 1.25f;
            piLiquid.add(this);
        }};
    }
}
