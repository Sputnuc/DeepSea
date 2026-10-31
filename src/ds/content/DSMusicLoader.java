package ds.content;

import arc.Events;
import arc.audio.Music;
import arc.struct.Seq;
import arc.util.Log;
import ds.DSSetting;
import ds.content.planets.DSPlanets;
import mindustry.Vars;
import mindustry.core.GameState;
import mindustry.game.EventType;
import mindustry.gen.Musics;

import static mindustry.Vars.state;

public class DSMusicLoader {
    public static Seq<Music> dsCalmM = new Seq<>();
    public static Seq<Music> dsActM = new Seq<>();
    public static Seq<Music> dsDarkM = new Seq<>();
    public static Seq<Music> dsBossM = new Seq<>();
    public static Seq<Music> dsEventsM = new Seq<>();

    public static Seq<Music> origAmbientMusic;
    public static Seq<Music> origDarkMusic;
    public static Seq<Music> origBossMusic;

    public static  String[] dsCalmList = {"quiet-odes"};
    public static String[] dsActList = {"in-the-depth"};
    public static String[] dsDarkList = {"surrounded-by-water"};
    public static String[] dsBossList = {};
    //TODO own theme
    public static String[] dsEventList = {"wait-of-the-world"};


    public static boolean deepSeaMusic = true;
    public static boolean vanillaMusic = true;

    public static void load(){
        origAmbientMusic = Vars.control.sound.ambientMusic.copy();
        origDarkMusic = Vars.control.sound.darkMusic.copy();
        origBossMusic = Vars.control.sound.bossMusic.copy();

        dsCalmM = loadMultiple(dsCalmList, "calm");
        dsActM = loadMultiple(dsActList, "act");
        dsDarkM = loadMultiple(dsDarkList, "dark");
        dsBossM = loadMultiple(dsBossList, "boss");
        dsEventsM = loadMultiple(dsEventList, "event");
    }

    public static Seq<Music> loadMultiple(String[] filenames, String folder) {
        Seq<Music> result = new Seq<>();

        for (String filename : filenames) {
            Music music = Vars.tree.loadMusic(folder + "/" + filename);
            if (music != null) {
                result.add(music);
            } else {
                Log.warn("Failed to load music: " + filename);
            }
        }
        return result;
    }

    public static boolean isDeepSeaMusic(){
        return deepSeaMusic;
    }

    public static boolean bothMusic(){
        return deepSeaMusic && vanillaMusic;
    }

    public static void attach() {
        Events.on(EventType.WorldLoadEvent.class, e -> {
            if (Vars.state.rules.planet != null && Vars.state.rules.planet == DSPlanets.obj312) {
                enableCustomMusic();
            } else {
                disableCustomMusic();
            }
        });

        Events.on(EventType.StateChangeEvent.class, e -> {
            if (e.from != GameState.State.menu && e.to == GameState.State.menu) {
                disableCustomMusic();
            }
        });

        Events.on(EventType.SectorCaptureEvent.class, e->{
            if (Vars.state.rules.planet != null && Vars.state.rules.planet == DSPlanets.obj312) {
                enableCustomMusic();
            } else {
                disableCustomMusic();
            }
        });
    }

    public static void disableCustomMusic() {
        if (origAmbientMusic != null) Vars.control.sound.ambientMusic = Seq.with(origAmbientMusic);
        if (origDarkMusic != null) Vars.control.sound.darkMusic = Seq.with(origDarkMusic);
        if (origBossMusic != null) Vars.control.sound.bossMusic = Seq.with(origBossMusic);
        deepSeaMusic = false;
    }

    public static void enableCustomMusic() {
        if (!DSSetting.getOnlyModMus()) {
            Vars.control.sound.darkMusic = Seq.with(dsDarkM).addAll(origDarkMusic);
            Vars.control.sound.bossMusic = Seq.with(dsBossM).addAll(origBossMusic);
            Vars.control.sound.ambientMusic = isAct() ? Seq.with(dsActM).addAll(origAmbientMusic) : Seq.with(dsCalmM).add(Musics.fine);
        } else {
            Vars.control.sound.ambientMusic = dsActM.isEmpty() ? Seq.with(origAmbientMusic) : isAct() || dsCalmM.isEmpty() ? Seq.with(dsActM) : Seq.with(dsCalmM);
            Vars.control.sound.darkMusic = dsDarkM.isEmpty() ? Seq.with(origDarkMusic) : Seq.with(dsDarkM);
            Vars.control.sound.bossMusic = dsBossM.isEmpty() ? Seq.with(origBossMusic) : Seq.with(dsBossM);
        }
        deepSeaMusic = true;
    }

    public static boolean isAct(){
        if(!state.rules.waveTimer || state.rules.infiniteResources) return false;
        if(state.isCampaign() & state.getSector() != null){
            return !state.getSector().isCaptured();
        }
        return true;
    }

}
