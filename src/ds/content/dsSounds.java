package ds.content;

import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.SoundLoader;
import arc.audio.Sound;
import mindustry.Vars;

public class dsSounds {
    public static Sound
            harpoon = new Sound();
    public static void load(){
        harpoon = loadSound("harpoon-shoot");
    }

    //Just copy all this function
    public static Sound loadSound(String sName){
        if(!Vars.headless){
            String name = "sounds/" + sName;
            String path = Vars.tree.get(name + ".ogg").exists() ? name + ".ogg" : name + ".mp3";

            Sound sound = new Sound();

            AssetDescriptor<?> desc = Core.assets.load(path, Sound.class, new SoundLoader.SoundParameter(sound));
            desc.errored = Throwable::printStackTrace;
            return sound;
        }else{
            return new Sound();
        }
    }
}
