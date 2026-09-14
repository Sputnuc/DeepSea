package ds.world.graphics;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.graphics.Shaders;

public class DSDrawf {
    public static void blockBuild(float x, float y, TextureRegion region, float rotation, float progress){
        blockBuild(x, y, region, Pal.accent, rotation, progress);
    }

    public static void blockBuild(float x, float y, TextureRegion region, Color color, float rotation, float progress){
        Shaders.blockbuild.region = region;
        Shaders.blockbuild.progress = progress;

        Draw.color(color);
        Draw.shader(Shaders.blockbuild);
        Draw.rect(region, x, y, rotation);
        Draw.shader();
        Draw.color();
    }

    public static void blockBuildCenter(float x, float y, TextureRegion region, float rotation, float progress){
        blockBuildCenter(x, y, region, Pal.accent, rotation, progress);
    }

    public static void blockBuildCenter(float x, float y, TextureRegion region, Color color, float rotation, float progress){
        Draw.color(color);
        Draw.rect(region, x, y, rotation, region.width * progress, region.height * progress);
        Draw.shader();
        Draw.color();
    }
}
