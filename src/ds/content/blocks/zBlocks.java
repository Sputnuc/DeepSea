package ds.content.blocks;


import ds.content.dsSounds;
import ds.content.items.zItems;
import ds.newContent.type.entities.HarpoonBulletType;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
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
        testTurret = new ItemTurret("test"){{
            requirements(Category.turret, with());
            size = 2;
            reload = 120;
            shake = 2;
            shootSound = dsSounds.harpoon;
            ammo(
                    zItems.sulfur, new HarpoonBulletType(10, 60){{
                        wireStroke = 0.75f;
                        width = 13;
                        height = 18;
                    }}
            );
        }};
    }
}
