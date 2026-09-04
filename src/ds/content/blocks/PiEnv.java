package ds.content.blocks;

import arc.graphics.Color;
import ds.content.DSFx;
import ds.content.items.PiItems;
import ds.world.blocks.environment.EffectFloor;
import ds.world.blocks.environment.GlowingSeaweed;
import ds.world.blocks.environment.RockBlock;
import ds.world.blocks.environment.TiledFloor;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.BuildVisibility;

import static ds.content.DSAttributes.*;
import static mindustry.content.Blocks.*;
import static mindustry.type.ItemStack.with;

public class PiEnv {
    public static Block
            //Ores
            aluminiumOre, silverOre, potasiumOre,
            //Limestone
            limestoneFloor, limestoneWall, roughLimestone, limestoneMountain, limestoneBoulder,

            //Manganese biome
            manganeseHydroxideFloor,   manganeseHydroxideCrystals,

            // Quartz biome
            quartzFloor, quartzSlabs, quartsCrystalWall, quartsWall, quartzCrystal,

            // Sulfur biome
            sulfurFloor, sulfurSandFloor, sulfurSandWall, sulfurGeyser, sulfurVent, sulfurWall, sulfurCrystal,

            // Others
            geothermalRift,

            // Basalt
            basaltWall, geothermalFloor,

            // Deep sandstone
            deepSandstone, deepSandstoneWall, deepRoughSandstone,
            // Ironstone
            ironstoneFloor, ironstoneWall,
            // Nature
            glowingCoral, bigGlowingCoral, deadYellowCoralFloor,
            //Deep spores
            deepSpore, deepSporeWall,
            // some sea bushes
            seaweedFloor, seaweedWall, seaweed, deadYellowcoral, deadYellowcoralAlt, fernAlphared, fernYellow, underwaterRedGrass, underwaterRegGrassAlt, underwaterRedWall;

    public static void load(){
        //Ores
        aluminiumOre = new OreBlock("ore-aluminium", PiItems.aluminium){{
            variants = 3;
        }};
        silverOre = new OreBlock("ore-silver", PiItems.silver){{
            variants = 3;
        }};

        //Limestone
        limestoneFloor = new Floor("limestone-floor"){{
            variants = 4;
            wall = limestoneWall;
        }};
        limestoneWall = new StaticWall("limestone-wall"){{
            variants = 3;
        }};
        roughLimestone = new Floor("rough-limestone-floor"){{
            variants = 2;
            wall = limestoneWall;
        }};
        limestoneBoulder = new Prop("limestone-boulder"){{
            variants = 1;
            limestoneFloor.asFloor().decoration = roughLimestone.asFloor().decoration = this;
        }};
        limestoneMountain = new RockBlock("limestone-mountain"){{
            requirements(Category.effect, with(PiItems.aluminium, 900, PiItems.silver, 700));
            buildTime = 1900;
            size = 4;
            buildVisibility = BuildVisibility.editorOnly;
        }};

        // Quartz
        quartzSlabs = new TiledFloor("quartz-slabs") {{
            tilingVariants = 3;
            tilingSize = 4;
            wall = quartsCrystalWall;
            //attributes.set(light, 0.3f);
        }};
        quartzFloor = new Floor("quartz-floor"){{
            variants = 8;
            wall = quartsCrystalWall;
        }};
        quartsCrystalWall = new StaticWall("quarts-crystal-wall"){{
            variants = 3;
        }};
        quartzCrystal = new Prop("quartz-crystal"){{
            variants = 2;
            quartzFloor.asFloor().decoration = this;
        }};

        // Sulfur
        sulfurSandFloor = new Floor("sulfur-sand-floor"){{
            variants = 4;
            wall = sulfurSandWall;
        }};
        sulfurSandWall = new StaticWall("sulfur-sand-wall"){{
            variants = 3;
        }};
        sulfurFloor = new Floor("sulfur-floor"){{
            variants = 4;
            itemDrop = PiItems.sulfur;
            wall = sulfurWall;
        }};
        sulfurWall = new StaticWall("sulfur-wall"){{
            variants = 3;
            itemDrop = PiItems.sulfur;
        }};

        sulfurCrystal = new Prop("sulfur-crystal"){{
            variants = 2;
            sulfurFloor.asFloor().decoration = this;
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
        geothermalRift = new EffectFloor("geothermal-rift"){{
            variants = 0;
            effectChance = 0.0001f;
            canShadow = false;
            placeableOn = false;
            solid = true;
            drawEdgeOut = false;
        }};
        // Sandstone
        deepSandstone = new Floor("deep-sandstone"){{
            variants = 2;
            wall = deepSandstoneWall;
        }};
        deepRoughSandstone = new Floor("deep-rough-sandstone"){{
            variants = 3;
            wall = deepSandstoneWall;
        }};
        deepSandstoneWall = new StaticWall("deep-sandstone-wall"){{
            variants = 3;
        }};
        // Manganese hydroxide
        manganeseHydroxideFloor = new Floor("manganese-hydroxide-floor"){{
            variants = 2;
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

        glowingCoral = new Floor("glowing-coral"){{
            variants = 4;
            emitLight = true;
            lightRadius = 40f;
            lightColor = Color.valueOf("85ffe0").a(0.2f);
        }};

        bigGlowingCoral = new TreeBlock("big-glowing-coral"){{
            variants = 1;
            emitLight = true;
            lightRadius = 70f;
            lightColor = Color.valueOf("85ffe0").a(0.45f);
        }};

        underwaterRedGrass = new Floor("underwater-red-grass"){{
            variants = 5;
        }};

        underwaterRegGrassAlt = new Floor("underwater-red-grass-alt"){{
            variants = 4;
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
        fernYellow = new TreeBlock("fern-yellow"){{
            variants = 4;
        }};
    }
}
