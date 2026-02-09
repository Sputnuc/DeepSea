package ds.content;

import arc.Events;
import arc.audio.Music;
import arc.struct.ArrayMap;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType;

//TODO remade this for advanced music control (for things......................))))))))
public class dsMusicLoader {
    public static Seq<Music> dsAmbientM = new Seq<>();
    public static Seq<Music> dsDarkM = new Seq<>();
    public static Seq<Music> dsBossM = new Seq<>();
    public static Seq<Music> dsEventsM = new Seq<>();

    public static String[] dsAmbientList = {"s-s-s"};
    public static String[] dsDarkList = {};
    public static String[] dsBossList = {};
    //TODO own theme
    public static String[] dsEventList = {"wait-of-the-world"};

    public static Seq<Music> vanillaAmbientMusic;
    public static Seq<Music> vanillaDarkMusic;
    public static Seq<Music> vanillaBossMusic;

    public static void load(){
        dsAmbientM = loadMultiple(dsAmbientList, "ambient");
        dsDarkM = loadMultiple(dsDarkList, "dark");
        dsBossM = loadMultiple(dsBossList, "boss");
        dsEventsM = loadMultiple(dsEventList, "event");
        vanillaAmbientMusic = Vars.control.sound.ambientMusic.copy();
        vanillaDarkMusic = Vars.control.sound.darkMusic.copy();
        vanillaBossMusic = Vars.control.sound.bossMusic.copy();
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

    //call this function in the main class when the client loads. Like this -> Events.on(EventType.ClientLoadEvent.class, e -> dsMusicLoader.attach());
    public static void attach(){
        Events.on(EventType.WorldLoadEvent.class, e -> {
            if (Vars.state.rules.planet != null && Vars.state.rules.planet.name.equals("deepsea-P-I-312")) {
                deepMusic();
            } else vanillaMusic();
        });
    }

    public static void deepMusic(){
        Vars.control.sound.ambientMusic = dsAmbientM;
    }

    public static void vanillaMusic(){
        Vars.control.sound.ambientMusic = vanillaAmbientMusic;
    }
}
