package ds.world.blocks.environment;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.meta.BuildVisibility;

public class RockBlock extends Block {
    public RockBlock(String name) {
        super(name);
        breakable = true;
        solid = true;
        breakEffect = Fx.breakProp;
        update = true;
        breakSound = Sounds.rockBreak;
        destroySound = Sounds.rockBreak;
        forceDark = true;
        buildVisibility = BuildVisibility.editorOnly;
    }
}
