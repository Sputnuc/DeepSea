package ds.world.graphics;

import arc.*;
import arc.graphics.*;
import arc.graphics.Texture.*;
import arc.graphics.g2d.*;
import arc.math.*;
import ds.world.meta.dsEnv;
import mindustry.graphics.Layer;
import mindustry.graphics.Shaders;
import mindustry.type.*;

import static mindustry.Vars.*;

public class dsEnvRenderers {

    public static void init(){

        Color waterColor = Color.valueOf("363159"); //First ver — 353982. Second ver — 30626e. Third ver — 363159.
        Color darknessColor = Color.valueOf("28252b"); //First ver — 2e2d40.
        Rand rand = new Rand();

        Core.assets.load("sprites/rays.png", Texture.class).loaded = t -> {
            t.setFilter(TextureFilter.linear);
        };

        Color particleWaterColor = Color.valueOf("c48d94"); //a7c1fa
        float windSpeed = 0.07f, windAngle = 45f;
        float darkWindSpeed = 0.56f, darkWindAngle = 170;
        float windx = Mathf.cosDeg(windAngle) * windSpeed, windy = Mathf.sinDeg(windAngle) * windSpeed;

        renderer.addEnvRenderer(dsEnv.underwaterWarm, () -> {
            Draw.draw(Layer.light + 1, () -> {
                Draw.color(waterColor, 0.45f);
                Fill.rect(Core.camera.position.x, Core.camera.position.y, Core.camera.width, Core.camera.height);
                Draw.reset();

                Blending.additive.apply();
                Draw.blit(Shaders.caustics);
                Blending.normal.apply();
            });

            Draw.z(Layer.light + 2);

            /* Suspended particles */
            Draw.draw(Layer.weather, () -> {
                Weather.drawParticles(
                    Core.atlas.find("particle"), particleWaterColor,
                    0.6f, 6f, //minmax size
                    10000f, 1.2f, 1f, //density
                    windx, windy, //wind vectors
                    0.1f, 0.7f, //minmax alpha
                    45f, 90f, //sinscl
                    1f, 8f, //sinmag
                    false
                );
            });

            Draw.blend();
        });

        /*Core.assets.load("sprites/distortAlpha.png", Texture.class);

        renderer.addEnvRenderer(Env.scorching, () -> {
            Texture tex = Core.assets.get("sprites/distortAlpha.png", Texture.class);
            if(tex.getMagFilter() != TextureFilter.linear){
                tex.setFilter(TextureFilter.linear);
                tex.setWrap(TextureWrap.repeat);
            }

            //TODO layer looks better? should not be conditional
            Draw.z(state.rules.fog ? Layer.fogOfWar + 1 : Layer.weather - 1);
            Weather.drawNoiseLayers(tex, Color.scarlet, 1000f, 0.24f, 0.4f, 1f, 1f, 0f, 4, -1.3f, 0.7f, 0.8f, 0.9f);
            Draw.reset();
        });*/
    }

}
