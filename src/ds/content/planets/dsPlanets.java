package ds.content.planets;

import arc.graphics.Color;
import mindustry.content.Planets;
import mindustry.graphics.g3d.*;
import mindustry.maps.planet.SerpuloPlanetGenerator;
import mindustry.type.Planet;
import mindustry.world.meta.Env;

public class dsPlanets {
    public static Planet z378;
    public static void loadContent(){
        z378 = new Planet("Z-378", Planets.sun, 1.9f){{
            generator = new SerpuloPlanetGenerator();
            meshLoader = () -> new HexMesh(z378, 6);
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(z378, 34, 1.21f, 0.08f, 6, Color.valueOf("99a3a2").a(0.42f), 3, 0.5f, 1f, 0.58f),
                    new HexSkyMesh(z378, 32, -0.71f, 0.12f, 6, Color.valueOf("8e9699").a(0.54f), 4, 0.56f, 0.89f, 0.5f),
                    new HexSkyMesh(z378, 30, 1.33f, 0.19f, 6, Color.valueOf("8b898f").a(0.27f), 4, 0.6f, 1f, 0.61f)
            );
            alwaysUnlocked = true;
            accessible = true;
            orbitRadius = 129;
            orbitOffset = 78;
            visible = true;
            atmosphereColor = Color.valueOf("cbd8f2");
            iconColor = Color.valueOf("93abd9");
            clearSectorOnLose = true;
            allowLaunchToNumbered = false;
            allowCampaignRules = true;
            defaultEnv = Env.terrestrial | Env.underwater & ~(Env.groundOil | Env.scorching | Env.spores);
        }};
    }
}
