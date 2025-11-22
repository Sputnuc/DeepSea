package ds.content.items;

import arc.graphics.Color;
import mindustry.type.Item;

public class zItems {
    public static Item aluminium, silver, manganeseHydroxide, manganese, sulfur, magnesium;
    public static void load(){
        aluminium = new Item("aluminium"){{
            color = Color.valueOf("b6e5f0");
            cost = 1;
            hardness = 3;
        }};
        silver = new Item("silver"){{
            color = Color.valueOf("f7f7f7");
            cost = 2.5f;
            hardness = 4;
        }};
        manganeseHydroxide = new Item("manganese-hydroxide", Color.valueOf("e3bad7")){{
            cost = 1;
            hardness = 3;
        }};
        manganese = new Item("manganese", Color.valueOf("cfa7af")){{
            cost = 2.5f;
            hardness = 3;
        }};
        sulfur = new Item("sulfur", Color.valueOf("ebf296")){{
            cost = 1.35f;
            hardness = 3;
            flammability = 0.5f;
            explosiveness = 0.95f;
        }};
        magnesium = new Item("magnesium", Color.valueOf("7de8c1")){{
            cost = 2;
            hardness = 4;
            charge = 0.2f;
        }};
    }
}
