package ds.content.items;

import arc.struct.Seq;
import mindustry.type.Item;

public class dsItemLoader {
    public static Seq<Item>modItems = new Seq<>();
    public static void load(){
        piItems.load();

        modItems.add(piItems.piItems);
    }
}
