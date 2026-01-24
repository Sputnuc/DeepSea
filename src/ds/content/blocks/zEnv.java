package ds.content.blocks;

import arc.graphics.Color;
import ds.content.dsFx;
import ds.content.items.zItems;
import ds.world.blocks.environment.EffectFloor;
import ds.world.blocks.environment.GlowingSeaweed;
import ds.world.blocks.environment.TiledFloor;
import mindustry.world.Block;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.Attribute;

import static ds.content.dsAttributes.*;
import static mindustry.content.Blocks.*;

public class zEnv {
    public static Block
            //Ores
            aluminiumOre, silverOre,
            //Limestone
            limestoneFloor, limestoneWall,

            //Manganese biome
            manganeseHydroxideCrystals,

            // Quartz biome
            quartzFloor, quartzSlabs, quartsCrystalWall, quartsWall,

            // Sulfur biome
            sulfurFloor, sulfurSandFloor, sulfurSandWall, sulfurGeyser, sulfurVent, sulfurWall, sulfurCrystal,

            // Basalt
            basaltWall, geothermalFloor,

            // Deep sandstone
            deepSandstone, deepSandstoneWall,

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
            itemDrop = zItems.sulfur;
        }};
        sulfurWall = new StaticWall("sulfur-wall"){{
            variants = 3;
            itemDrop = zItems.sulfur;
        }};

        sulfurVent = new SteamVent("sulfur-vent"){{
            parent = blendGroup = sulfurSandFloor;
            variants = 3;
            effectColor = Color.valueOf("202411");
            effect = dsFx.sulfurVentSteam;
            attributes.set(sulfuric, 1f);
        }};

        sulfurGeyser = new SteamVent("sulfur-geyser"){{
            effectSpacing = 5;
            parent = blendGroup = sulfurSandFloor;
            variants = 1;
            effectColor = Color.valueOf("202411");
            effect = dsFx.geyserSteam;
            attributes.set(geyser, 1f);
            attributes.set(sulfuric, 1f);
        }};

        // Basalt
        basaltWall = new StaticWall("basalt-wall"){{
            variants = 3;
        }};
        geothermalFloor = new EffectFloor("geothermal-floor"){{
            variants = 4;
            emitLight = true;
            lightRadius = 40f;
            lightColor = hotrock.lightColor;
            attributes.set(Attribute.heat, 1.5f);
            effectChance = 0.04f;
        }};
        // Sandstone
        deepSandstone = new Floor("deep-sandstone"){{
            variants = 4;
        }};
        deepSandstoneWall = new StaticWall("deep-sandstone-wall"){{
            variants = 3;
        }};
        // Manganese hydroxide
        manganeseHydroxideCrystals = new StaticWall("manganese-hydroxide-crystals"){{
            itemDrop = zItems.manganeseHydroxide;
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

        seaweed = new GlowingSeaweed("seaweed"){{
            variants = 2;
            lightRadius = 9;
            seaweedFloor.asFloor().decoration = this;
        }};
    }
}
