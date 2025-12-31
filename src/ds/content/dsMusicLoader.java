package ds.content;

import arc.Events;
import arc.audio.Music;
import arc.struct.ArrayMap;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType;

public class dsMusicLoader {
    public static Seq<Music> dsAmbientM = new Seq<>();
    public static Seq<Music> dsDarkM = new Seq<>();
    public static Seq<Music> dsBossM = new Seq<>();

    public static String[] dsAmbientList = {"s-s-s"};
    public static String[] dsDarkList = {};
    public static String[] dsBossList = {};

    public static Seq<Music> vanillaAmbientMusic;
    public static Seq<Music> vanillaDarkMusic;
    public static Seq<Music> vanillaBossMusic;

    public static boolean customMusic = true;
    public static class MusicInfo {
        public String name;
        public String author;

        MusicInfo(String name, String author) {
            this.name = name;
            this.author = author;
        }
    }
    public static String anuke = "Anuken";
    public static String roc = "RoccoW";
    public static ArrayMap<String, MusicInfo> musics = new ArrayMap<>();
    static {
        musics.put("game1", new MusicInfo("Game 1", anuke));
        musics.put("game2", new MusicInfo("Game 2", anuke));
        musics.put("game3", new MusicInfo("Game 3", anuke));
        musics.put("game4", new MusicInfo("Game 4", anuke));
        musics.put("game5", new MusicInfo("Game 5", anuke));
        musics.put("game6", new MusicInfo("Game 6", anuke));
        musics.put("game7", new MusicInfo("Game 7", anuke));
        musics.put("game8", new MusicInfo("Game 8", anuke));
        musics.put("game9", new MusicInfo("Game 9", anuke));
        musics.put("fine", new MusicInfo("Fine", anuke));
        musics.put("boss1", new MusicInfo("Boss 1", anuke));
        musics.put("boss2", new MusicInfo("Boss 2", anuke));
    }

    public static void load(){
        dsAmbientM = loadMultiple(dsAmbientList, "ambient");
        dsDarkM = loadMultiple(dsDarkList, "dark");
        dsBossM = loadMultiple(dsBossList, "boss");
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
            if (Vars.state.rules.planet.parent != null && Vars.state.rules.planet.parent.name.equals("deepsea-z387")) {
                deepMusic();
            } else if (customMusic) {
                vannilaMusic();
            }
        });
    }

    public static void deepMusic(){
        Vars.control.sound.ambientMusic.addAll(dsAmbientM);
        customMusic = true;
    }

    public static void vannilaMusic(){
        Vars.control.sound.ambientMusic = vanillaAmbientMusic;
        customMusic = false;
    }
}
