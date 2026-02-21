package ds.content.planets;

import arc.graphics.Color;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import ds.content.blocks.piBlocks;
import ds.content.liquids.piLiquids;
import ds.content.units.piUnits;
import ds.world.meta.dsEnv;
import mindustry.content.Items;
import mindustry.content.Planets;
import mindustry.game.Team;
import mindustry.graphics.g3d.*;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.type.Planet;
import mindustry.type.UnitType;
import mindustry.world.meta.Env;

public class dsPlanets {
    public static Planet pi312;
    public static void loadContent(){
        //"Vmtkb2JFbEZWbWhqYmxKdg=="
        pi312 = new Planet("P-I-312", Planets.sun, 2f, 3){{
            generator = new piGenerator();
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
                r.fire = false;
                r.fog = false; //tru
                r.defaultTeam = Team.sharded;
                r.waveTeam = Team.crux;
                r.coreCapture = false;
                r.hideBannedBlocks = true;
                r.env = Env.terrestrial | dsEnv.underwaterWarm & ~(Env.groundOil | Env.scorching | Env.spores);
            };
            iconColor = Color.valueOf("96a4d6");
            atmosphereColor = Color.valueOf("a5b1f0");
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.3f;
            alwaysUnlocked = true;
            defaultCore = piBlocks.coreInfluence;
            defaultEnv = Env.terrestrial | dsEnv.underwaterWarm & ~(Env.groundOil | Env.scorching | Env.spores);
        }};

        unitWhiteList(piUnits.pi312units, pi312);
        addItemWhitelist(Seq.with(Items.graphite), pi312);
        addLiquidWhitelist(piLiquids.piLiquid, pi312);
    }

    protected static void unitWhiteList(Seq<UnitType>units, Planet planet){
        for (UnitType u : units){
            u.shownPlanets = ObjectSet.with(planet);
        }
    };
    protected static void addItemWhitelist(Seq<Item> ite, Planet planet){
        for (Item i : ite){
            i.shownPlanets.add(planet);
        }
    };
    protected static void addLiquidWhitelist(Seq<Liquid> liqst, Planet planet){
        for (Liquid li : liqst){
            li.shownPlanets.add(planet);
        }
    }
}
