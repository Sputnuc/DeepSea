package ds.content.blocks;

import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.StaticWall;

public class zEnv {
    public static Block
            //Limestone
            limestoneWall, limestoneFloor,
            //Manganese biome
            manganeseCrystals, manganeseCrystalFloor, getManganeseCrystalWalls,
            //Quarts biome
            quartsCrystals, quartsSand, quartsSandWall,
            //Sulfur biome
            sulfurWall, sulfurFloor, sulfurGeyser, sulfurCrystals, sulfurCrackedFloor, sulfurDust,
            //Basalt
            basaltWall;
    public static void load(){
        limestoneWall = new StaticWall("limestone-wall"){{
            variants = 3;
        }};
        limestoneFloor = new Floor("limestone-floor"){{
            variants = 3;
        }};
        quartsCrystals = new StaticWall("quarts-crystals"){{
            variants = 3;
        }};
        quartsSand = new Floor("quarts-sand"){{
            variants = 3;
        }};
        sulfurWall = new StaticWall("sulfur-wall"){{
            variants = 3;
        }};
        sulfurFloor = new Floor("sulfur-floor"){{
            variants = 4;
        }};
        basaltWall = new StaticWall("basalt-wall"){{
            variants = 3;
        }};
    }
}
