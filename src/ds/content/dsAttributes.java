package ds.content;

import mindustry.world.blocks.Attributes;
import mindustry.world.meta.Attribute;

public class dsAttributes {
    public static Attribute sulfuric, geyser;

    public static void load(){{
        sulfuric = Attribute.add("sulfuric");
        geyser = Attribute.add("geyser");
    }}
}
