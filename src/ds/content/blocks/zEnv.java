package ds.content.blocks;

import arc.graphics.Color;
import arc.math.Interp;
import ds.content.dsAttributes;
import ds.content.dsFx;
import ds.content.items.zItems;
import ds.world.blocks.environment.TiledFloor;
import mindustry.entities.Effect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.blocks.environment.*;

import static ds.content.dsAttributes.*;

public class zEnv {
    public static Block
            //Ores
            aluminiumOre, silverOre,
            //Limestone
            limestoneFloor, limestoneWall,

            //Manganese biome
            manganeseCrystalFloor, manganeseCrystals, getManganeseCrystalWalls,

            // Quartz biome
            quartzFloor, quartzSlabs, quartsCrystalWall, quartsWall,

            // Sulfur biome
            sulfurFloor, sulfurSandFloor, sulfurSandWall, sulfurGeyser, sulfurVent, sulfurWall, sulfurCrystal,

            // Basalt
            basaltWall,

            // Nature
            // some sea bushes
            seaweedFloor, seaweedWall, seaweed;

    public static void load(){
        //Ores
        aluminiumOre = new OreBlock("ore-aluminium", zItems.aluminium){{
            variants = 3;
        }};
        silverOre = new OreBlock("ore-silver", zItems.silver){{
            variants = 3;
        }};

        //Other
        limestoneFloor = new Floor("limestone-floor"){{
            variants = 4;
        }};
        limestoneWall = new StaticWall("limestone-wall"){{
            variants = 3;
        }};

        // Quartz
        quartzSlabs = new TiledFloor("quartz-slabs") {{
            tilingVariants = 3;
            tilingSize = 4;
            //attributes.set(light, 0.3f);
        }};
        quartzFloor = new Floor("quartz-floor"){{
            variants = 8;
        }};
        quartsCrystalWall = new StaticWall("quarts-crystal-wall"){{
            variants = 3;
        }};

        // Sulfur
        sulfurSandFloor = new Floor("sulfur-sand-floor"){{
            variants = 3;
        }};
        sulfurSandWall = new StaticWall("sulfur-sand-wall"){{
            variants = 3;
        }};
        sulfurFloor = new Floor("sulfur-floor"){{
            variants = 3;
        }};
        sulfurWall = new StaticWall("sulfur-wall"){{
            variants = 3;
        }};

        sulfurVent = new SteamVent("sulfur-vent"){{
            parent = blendGroup = sulfurSandFloor;
            variants = 3;
            effectColor = Color.valueOf("202411");
            effect = dsFx.sulfurVentSteam;
            attributes.set(sulfuric, 1f);
        }};

        // Basalt
        basaltWall = new StaticWall("basalt-wall"){{
            variants = 3;
        }};

        //Nature
        seaweedFloor = new Floor("seaweed-floor"){{
            variants = 12;
            lightColor = Color.valueOf("a8ffd5").a(0.09f);
            emitLight = true;
            lightRadius = 40f;
        }};
        seaweedWall = new StaticWall("seaweed-wall"){{
            variants = 4;
            emitLight = true;
            lightRadius = 40f;
            lightColor = Color.valueOf("a8ffd5").a(0.09f);
        }};

        seaweed = new Seaweed("seaweed"){{
            variants = 2;
            seaweedFloor.asFloor().decoration = this;
        }};
    }
}
