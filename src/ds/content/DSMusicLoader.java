package ds.content;

import arc.Events;
import arc.audio.Music;
import arc.struct.Seq;
import arc.util.Log;
import ds.DSSetting;
import ds.DSSoundControl;
import ds.content.planets.DSPlanets;
import mindustry.Vars;
import mindustry.core.GameState;
import mindustry.game.EventType;
import mindustry.gen.Musics;

//TODO remade this for advanced music control (for things......................))))))))
public class DSMusicLoader {
    public static Seq<Music> dsCalmM = new Seq<>();
    public static Seq<Music> dsAmbientM = new Seq<>();
    public static Seq<Music> dsDarkM = new Seq<>();
    public static Seq<Music> dsBossM = new Seq<>();
    public static Seq<Music> dsEventsM = new Seq<>();

    public static  String[] dsCalmList = {"rimworld-dayandnight"};
    public static String[] dsAmbientList = {};
    public static String[] dsDarkList = {};
    public static String[] dsBossList = {};
    //TODO own theme
    public static String[] dsEventList = {"wait-of-the-world"};


    public static boolean deepSeaMusic = true;
    public static boolean vannilaMusic = true;

    public static void load(){
        dsCalmM = loadMultiple(dsCalmList, "calm");
        dsAmbientM = loadMultiple(dsAmbientList, "ambient");
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
        return deepSeaMusic && vannilaMusic;
    }

    public static void attach() {
        Events.on(EventType.WorldLoadEvent.class, e -> {
            if (Vars.state.rules.planet != null && Vars.state.rules.planet == DSPlanets.pi312) {
                vannilaMusic = !DSSetting.getOnlyModMus();
                deepSeaMusic = true;
            } else {
                vannilaMusic = true;
                deepSeaMusic = false;
            }
        });

        Events.on(EventType.StateChangeEvent.class, e -> {
            if (e.from != GameState.State.menu && e.to == GameState.State.menu) {
                deepSeaMusic = false;
            }
        });
    }
}
