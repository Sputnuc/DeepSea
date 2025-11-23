package ds.content.blocks;


import arc.Core;
import arc.graphics.Color;
import ds.content.dsSounds;
import ds.content.items.zItems;
import ds.content.units.zUnits;
import ds.newContent.type.entities.HarpoonBulletType;
import mindustry.graphics.Layer;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.storage.CoreBlock;


import static mindustry.type.ItemStack.with;

public class zBlocks {
    public static Block
            //Production
            hydraulicDrill, hydraulicWallDrill,
            //Cores
            coreInfluence, coreEnforcement, coreEminence,
            //Turrets
            testTurret;
    public static void load(){
        coreInfluence = new CoreBlock("core-influence"){{
            requirements(Category.effect, with(zItems.aluminium, 790, zItems.manganese, 680));
            size = 3;
            isFirstTier = true;
            alwaysUnlocked = true;
            health = 2100;
            itemCapacity = 5300;
            buildCostMultiplier = 3;
            unitCapModifier = 12;
            unitType = zUnits.moment;
        }};
        testTurret = new ItemTurret("test"){{
            requirements(Category.turret, with());
            size = 2;
            reload = 180;
            range = 300;
            shake = 2;
            shootSound = dsSounds.harpoon;
            ammo(
                    zItems.sulfur, new HarpoonBulletType(10, 60){{
                        layer = Layer.bullet - 3;
                        frontColor = Color.valueOf("d4d4d4");
                        backColor = Color.valueOf("929aa8");
                        wireStroke = 1.75f;
                        width = 13;
                        height = 18;
                    }}
            );
        }};
    }
}
