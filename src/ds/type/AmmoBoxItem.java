package ds.type;

import arc.graphics.Color;
import mindustry.type.Item;

public class AmmoBoxItem extends Item {

    public int capacity = 10;

    public AmmoItem ammoItem;

    public AmmoBoxItem(String name, Color color) {
        super(name, color);
        ammoItem = null;
    }

    public AmmoBoxItem(String name) {
        super(name);
        ammoItem = null;
    }

    public AmmoBoxItem(String name, AmmoItem ammoItem){
        this(name);
        this.ammoItem = ammoItem;
    }

    public AmmoBoxItem(String name, AmmoItem ammoItem, int capacity){
        this(name);
        this.ammoItem = ammoItem;
        this.capacity = capacity;
    }
}
