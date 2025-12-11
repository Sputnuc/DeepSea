package ds.content.blocks;

import ds.content.items.zItems;
import ds.world.blocks.environment.TiledFloor;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.environment.StaticWall;

public class zEnv {
    public static Block
            //Ores
            aluminiumOre, silverOre,
            //Limestone
            limestoneFloor, limestoneWall,

            //Manganese biome
            manganeseCrystalFloor, manganeseCrystals, getManganeseCrystalWalls,

            // Quartz biome
            quartzFloor, quartzSlabs, quartsCrystals, quartsWall,

            // Sulfur biome
            sulfurFloor, sulfurCrackedFloor, sulfurGeyser, sulfurWall, sulfurCrystals, sulfurDust,

            // Basalt
            basaltWall;

            // Nature
            // some sea bushes

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
            variants = 3;
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
        quartzFloor = new Floor("quartz-floor"){{variants = 8;}};
        quartsCrystals = new StaticWall("quarts-crystals"){{
            variants = 3;
        }};

        // Sulfur
        sulfurFloor = new Floor("sulfur-floor"){{
            variants = 4;
        }};
        sulfurWall = new StaticWall("sulfur-wall"){{
            variants = 3;
        }};

        // Basalt
        basaltWall = new StaticWall("basalt-wall"){{
            variants = 3;
        }};
    }
}
