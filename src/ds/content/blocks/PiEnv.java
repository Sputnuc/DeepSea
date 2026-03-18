package ds.content.blocks;

import arc.graphics.Color;
import ds.content.DSFx;
import ds.content.items.PiItems;
import ds.world.blocks.environment.EffectFloor;
import ds.world.blocks.environment.GlowingSeaweed;
import ds.world.blocks.environment.TiledFloor;
import mindustry.world.Block;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.Attribute;

import static ds.content.DSAttributes.*;
import static mindustry.content.Blocks.*;

public class PiEnv {
    public static Block
            //Ores
            aluminiumOre, silverOre,
            //Limestone
            limestoneFloor, limestoneWall,

            //Manganese biome
            manganeseHydroxideFloor,    manganeseHydroxideCrystals,

            // Quartz biome
            quartzFloor, quartzSlabs, quartsCrystalWall, quartsWall,

            // Sulfur biome
            sulfurFloor, sulfurSandFloor, sulfurSandWall, sulfurGeyser, sulfurVent, sulfurWall, sulfurCrystal,

            // Basalt
            basaltWall, geothermalFloor,

            // Deep sandstone
            deepSandstone, deepSandstoneWall,
            // Ironstone
            ironstoneFloor, ironstoneWall,

            // Nature
            deadYellowCoralFloor,
            // some sea bushes
            seaweedFloor, seaweedWall, seaweed, deadYellowcoral, deadYellowcoralAlt, fernAlphared, fernYellow;

    public static void load(){
        //Ores
        aluminiumOre = new OreBlock("ore-aluminium", PiItems.aluminium){{
            variants = 3;
        }};
        silverOre = new OreBlock("ore-silver", PiItems.silver){{
            variants = 3;
        }};

        //Other
        limestoneFloor = new Floor("limestone-floor"){{
            variants = 4;
            mapColor = Color.valueOf("74675a");
        }};
        limestoneWall = new StaticWall("limestone-wall"){{
            variants = 3;
            mapColor = Color.valueOf("ada8a1");
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
            itemDrop = PiItems.sulfur;
        }};
        sulfurWall = new StaticWall("sulfur-wall"){{
            variants = 3;
            itemDrop = PiItems.sulfur;
        }};

        sulfurVent = new SteamVent("sulfur-vent"){{
            parent = blendGroup = sulfurSandFloor;
            variants = 3;
            effectColor = Color.valueOf("202411");
            effect = DSFx.sulfurVentSteam;
            attributes.set(sulfuric, 1f);
        }};

        sulfurGeyser = new SteamVent("sulfur-geyser"){{
            effectSpacing = 5;
            parent = blendGroup = sulfurSandFloor;
            variants = 1;
            effectColor = Color.valueOf("202411");
            effect = DSFx.geyserSteam;
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
            blendGroup = basalt;
            lightRadius = 40f;
            lightColor = hotrock.lightColor;
            mapColor = hotrock.mapColor;
            attributes.set(Attribute.heat, 1.5f);
            effectChance = 0.04f;
        }};
        // Sandstone
        deepSandstone = new Floor("deep-sandstone"){{
            variants = 3;
            mapColor = Color.valueOf("5a4a3a");
        }};
        deepSandstoneWall = new StaticWall("deep-sandstone-wall"){{
            variants = 3;
            mapColor = Color.valueOf("736251");
        }};
        // Manganese hydroxide
        manganeseHydroxideFloor = new Floor("manganese-hydroxide-floor"){{
            variants = 3;
            itemDrop = PiItems.manganeseHydroxide;
        }};
        manganeseHydroxideCrystals = new StaticWall("manganese-hydroxide-crystals"){{
            itemDrop = PiItems.manganeseHydroxide;
            variants = 3;
        }};
        // Ironstone
        ironstoneFloor = new Floor("ironstone-floor"){{
            itemDrop = PiItems.ironstone;
            variants = 7;
        }};
        ironstoneWall = new StaticWall("ironstone-wall"){{
            itemDrop = PiItems.ironstone;
            variants = 2;
        }};

        //Nature
        seaweedFloor = new Floor("seaweed-floor"){{
            variants = 12;
            lightColor = Color.valueOf("a8ffd5").a(0.09f);
            emitLight = true;
            lightRadius = 40f;
        }};
        seaweedWall = new StaticWall("seaweed-wall"){{
            variants = 3;
            emitLight = true;
            lightRadius = 40f;
            lightColor = Color.valueOf("a8ffd5").a(0.09f);
        }};

        seaweed = new GlowingSeaweed("seaweed"){{
            variants = 2;
            lightRadius = 9;
            seaweedFloor.asFloor().decoration = this;
        }};
        deadYellowCoralFloor = new Floor("dead-yellow-coral-floor"){{variants = 5;}};
        deadYellowcoral = new SeaBush("dead-yellowcoral"){{
            lobesMin = 2; lobesMax = 3;
            magMin = 2f; magMax = 8f;
            origin = 0.3f;
            spread = 40f;
            sclMin = 60f; sclMax = 100f;
        }};
        deadYellowcoralAlt = new SeaBush("dead-yellowcoral-alt"){{
            lobesMin = 2; lobesMax = 3;
            magMin = 1.5f; magMax = 5f;
            origin = 0.2f;
            spread = 40f;
            sclMin = 40f; sclMax = 80f;
        }};
        fernAlphared = new TreeBlock("fern-alphared"){{
            variants = 4;
        }};
        fernYellow = new TreeBlock("dead-yellowcoral-alt"){{
            variants = 4;
        }};
    }
}
