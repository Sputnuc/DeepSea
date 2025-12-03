package ds.content.blocks;


import arc.Core;
import arc.audio.Sound;
import arc.graphics.Color;
import arc.math.Interp;
import ds.content.dsFx;
import ds.content.dsSounds;
import ds.content.items.zItems;
import ds.content.units.zUnits;
import ds.world.blocks.dsHarpoonTurret;
import ds.world.meta.DSEnv;
import ds.world.type.entities.HarpoonBulletType;
import mindustry.graphics.Layer;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.draw.*;
import mindustry.world.meta.Env;


import static ds.content.items.zItems.*;
import static mindustry.Vars.tilesize;
import static mindustry.type.ItemStack.with;

public class zBlocks {
    public static Block
            //Production
            hydraulicDrill, hydraulicWallDrill,
            //Cores
            coreInfluence, coreEnforcement, coreEminence,
            //Turrets
            cutoff, testTurretAlternate, testTorpedoTurret;
    public static void load(){
        coreInfluence = new CoreBlock("core-influence"){{
            requirements(Category.effect, with(aluminium, 790, zItems.manganese, 680));
            size = 3;
            isFirstTier = true;
            alwaysUnlocked = true;
            health = 2100;
            itemCapacity = 5300;
            buildCostMultiplier = 3;
            unitCapModifier = 12;
            unitType = zUnits.moment;
            envEnabled |= Env.terrestrial | DSEnv.underwaterWarm;
            envDisabled = Env.none;
        }};
        cutoff = new dsHarpoonTurret("cutoff"){{
            requirements(Category.turret, with(aluminium, 75, silver, 45));
            size = 2;
            reload = 300;
            range = 200;
            shake = 2;
            shootSound = dsSounds.harpoon;
            fuelItem = sulfur;
            drawer = new DrawTurret("ds-turret-");
            shootType = new HarpoonBulletType(16, 45){{
                lifetime = 45;
                pierceDrag = 0.25f;
                shootEffect = dsFx.dsInclinedWave;
                layer = Layer.bullet - 3;
                drag = 0.05f;
                frontColor = Color.valueOf("d4d4d4");
                backColor = Color.valueOf("929aa8");
                wireStroke = 3.75f;
                width = 10;
                height = 25;
                returnSpeed = 3;
            }};
        }};
    }
}
