package ds.content.planets;

import arc.graphics.Color;
import arc.graphics.Mesh;
import arc.struct.Seq;
import ds.content.blocks.zBlocks;
import ds.world.meta.DSEnv;
import mindustry.content.Blocks;
import mindustry.content.Planets;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.graphics.g3d.*;
import mindustry.maps.planet.AsteroidGenerator;
import mindustry.maps.planet.SerpuloPlanetGenerator;
import mindustry.maps.planet.TantrosPlanetGenerator;
import mindustry.type.ItemStack;
import mindustry.type.Planet;
import mindustry.world.meta.Env;

import static ds.content.items.zItems.aluminium;

public class dsPlanets {
    public static Planet z387;
    public static void loadContent(){
        //"Vmtkb2JFbEZWbWhqYmxKdg=="
        z387 = new Planet("z387", Planets.sun, 2f, 3){{
            generator = new zGenerator();
            meshLoader = () -> new HexMesh(this, 6);
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 3, 0.13f, 0.11f, 5, Color.valueOf("c4ebed").a(0.75f), 2, 0.18f, 1.2f, 0.3f),
                    new HexSkyMesh(this, 5, 0.7f, 0.09f, 5, Color.valueOf("edfeff").a(0.65f), 3, 0.12f, 1.5f, 0.32f),
                    new HexSkyMesh(this, 8, 0.3f, 0.08f, 5, Color.valueOf("d3cad7").a(0.55f), 2, 0.08f, 1.6f, 0.35f)
            );
            sectorSeed = 4;
            allowWaves = true;
            allowLaunchSchematics = false;
            alwaysUnlocked = true;
            accessible = true;
            allowLaunchToNumbered = false;
            allowLaunchLoadout = false;
            allowSectorInvasion = false;
            startSector = 10;
            updateLighting = false;
            clearSectorOnLose = true;
            ruleSetter = r -> {
                r.lighting = true;
                r.ambientLight = Color.valueOf("010205ec");
                r.loadout = new Seq<>();
                r.fire = false;
                r.fog = false; //tru
                r.defaultTeam = Team.sharded;
                r.waveTeam = Team.crux;
                r.coreCapture = false;
                r.hideBannedBlocks = true;
                r.env = Env.terrestrial | DSEnv.underwaterWarm & ~(Env.groundOil | Env.scorching | Env.spores);
            };
            iconColor = Color.valueOf("96a4d6");
            atmosphereColor = Color.valueOf("a5b1f0");
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.3f;
            alwaysUnlocked = true;
            defaultCore = zBlocks.coreInfluence;
            defaultEnv = Env.terrestrial | DSEnv.underwaterWarm & ~(Env.groundOil | Env.scorching | Env.spores);
        }};
    }
}
