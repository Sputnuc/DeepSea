package ds.content.items;

import arc.graphics.Color;
import arc.struct.Seq;
import ds.content.DSBullets;
import ds.type.AmmoItem;
import mindustry.type.Item;

public class PiItems {
    public static Item
            //Items in progression

            //1-st tier
            aluminium, silver, sulfur, sandstone, /*graphite*/ manganeseHydroxide, manganese,
            //2-nd tier
            ironstone, steel, /*silicon*/potassium, magnesium,
            //3-st tier
            strontium, molybdenum, lithium, sapphire,
            //4-st tier
            molybdenumSilicate, uranium, enrichedUranium, depletedUranium,
            //5-st tier
            denseAlloy, reinforsedMetalPlasticStructure;

    public static AmmoItem
            //AmmoItems

            //Small calibre
            smallApAmmo, smallExAmmo, smallSdAmmo,
            //Small shotguns calibre
            smallBackshotAmmo, smallClusterAmmo,

            //Medium AA calibre
            aaApAmmo, aaUfAmmo, aaSdAmmo,
            //Mortar
            mediumMortarHeAmmo, mediumMortarClaserAmmo
            ;

    public static Seq<Item> piItems = new Seq<>();

    public static void load(){
        aluminium = new Item("aluminium"){{
            color = Color.valueOf("d7c0e0");
            cost = 0.75f;
            hardness = 3;
            alwaysUnlocked = true;
            piItems.add(this);
        }};
        silver = new Item("silver"){{
            color = Color.valueOf("edfdff");
            cost = 1.15f;
            hardness = 4;
            alwaysUnlocked = true;
            piItems.add(this);
        }};
        sulfur = new Item("sulfur", Color.valueOf("ebf296")){{
            cost = 1.15f;
            hardness = 3;
            flammability = 1f;
            explosiveness = 0.95f;
            alwaysUnlocked = true;
            piItems.add(this);
        }};
        manganeseHydroxide = new Item("manganese-hydroxide", Color.valueOf("e3bad7")){{
            cost = 1.75f;
            hardness = 4;
            alwaysUnlocked = false;
            piItems.add(this);
        }};
        manganese = new Item("manganese", Color.valueOf("cfa7af")){{
            cost = 1.5f;
            hardness = 3;
            alwaysUnlocked = false;
            piItems.add(this);
        }};
        ironstone = new Item("ironstone", Color.valueOf("c79484")){{
            cost = 2;
            hardness = 5;
            alwaysUnlocked = false;
            piItems.add(this);
        }};
        steel = new Item("steel", Color.valueOf("ab7272")){{
            cost = 2.5f;
            hardness = 5;
            alwaysUnlocked = false;
            piItems.add(this);
        }};
        magnesium = new Item("magnesium", Color.valueOf("7de8c1")){{
            cost = 2;
            hardness = 4;
            charge = 0.2f;
            alwaysUnlocked = false;
            piItems.add(this);
        }};
        potassium = new Item("potassium", Color.valueOf("e8bdc5")){{
            cost = 1.45f;
            hardness = 3;
            explosiveness = 0.95f;
            flammability = 0.75f;
            alwaysUnlocked = false;
            piItems.add(this);
        }};
        molybdenum = new Item("molybdenum", Color.valueOf("c0bdff")){{
            cost = 2f;
            hardness = 5;
            alwaysUnlocked = false;
            piItems.add(this);
        }};
        lithium = new Item("lithium", Color.valueOf("ebc4bc")){{
            cost = 1.25f;
            hardness = 3;
            explosiveness = 0.95f;
            flammability = 0.85f;
            alwaysUnlocked = false;
            piItems.add(this);
        }};
        sapphire = new Item("sapphire"){{
            cost = 2;
            hardness = 6;
            alwaysUnlocked = false;
            piItems.add(this);
        }};

        loadAmmo();
    }

    protected static void loadAmmo(){
        smallApAmmo = new AmmoItem("small-ap-ammo", DSBullets.smallApBullet, 3);
        smallExAmmo = new AmmoItem("small-ex-ammo", DSBullets.smallExBullet, 3);
        smallSdAmmo = new AmmoItem("small-sd-ammo", DSBullets.smallSdBullet, 3);
        smallBackshotAmmo = new AmmoItem("small-backshot-ammo", DSBullets.smallBackshotBullet, 3);
        smallClusterAmmo = new AmmoItem("small-cluster-ammo", DSBullets.smallClusterBullet, 3);
        aaApAmmo = new AmmoItem("aa-ap-ammo", DSBullets.aaApBullet, 2);
        aaUfAmmo = new AmmoItem("aa-uf-ammo", DSBullets.aaUfBullet, 2);
        aaSdAmmo = new AmmoItem("aa-sd-ammo", DSBullets.aaSdBullet, 2);
        mediumMortarHeAmmo = new AmmoItem("medium-mortar-he-ammo", DSBullets.mediumMortarHe, 1);
    }
}
