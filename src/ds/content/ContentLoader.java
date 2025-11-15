package ds.content;

import ds.content.blocks.dsBlocksLoader;
import ds.content.items.dsItemLoader;

public class ContentLoader {
    public static void load(){
        dsItemLoader.load();
        dsBlocksLoader.load();
    }
}
