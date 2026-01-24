package ds.content.items;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.type.Item;

public class zItems {
    public static Item aluminium, silver, manganeseHydroxide, manganese, sulfur, ironstone, ferrum, magnesium, lithium, potassium;
    public static void load(){
        aluminium = new Item("aluminium"){{
            color = Color.valueOf("d7c0e0");
            cost = 1.15f;
            hardness = 3;
            alwaysUnlocked = false;
        }};
        silver = new Item("silver"){{
            color = Color.valueOf("edfdff");
            cost = 1.45f;
            hardness = 4;
            alwaysUnlocked = false;
        }};
        manganeseHydroxide = new Item("manganese-hydroxide", Color.valueOf("e3bad7")){{
            cost = 1.75f;
            hardness = 4;
            alwaysUnlocked = false;
        }};
        manganese = new Item("manganese", Color.valueOf("cfa7af")){{
            cost = 1.5f;
            hardness = 3;
            alwaysUnlocked = false;
        }};
        sulfur = new Item("sulfur", Color.valueOf("ebf296")){{
            cost = 1.15f;
            hardness = 3;
            flammability = 0.5f;
            explosiveness = 0.95f;
            alwaysUnlocked = false;
        }};
        ironstone = new Item("ironstone", Color.valueOf("c79484")){{
            cost = 2;
            hardness = 3;
            alwaysUnlocked = false;
        }};
        ferrum = new Item("ferrum", Color.valueOf("ab7272")){{
            cost = 2.2f;
            hardness = 4;
            alwaysUnlocked = false;
        }};
        magnesium = new Item("magnesium", Color.valueOf("7de8c1")){{
            cost = 2;
            hardness = 4;
            charge = 0.2f;
            alwaysUnlocked = false;
        }};
        lithium = new Item("lithium", Color.valueOf("ebc4bc")){{
            cost = 1.25f;
            hardness = 3;
            explosiveness = 0.95f;
            flammability = 0.75f;
            alwaysUnlocked = false;
        }};
        potassium = new Item("potassium", Color.valueOf("e8bdc5")){{
            cost = 1.45f;
            hardness = 3;
            explosiveness = 0.95f;
            flammability = 0.8f;
            alwaysUnlocked = false;
        }};
    }
}
