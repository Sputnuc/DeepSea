package ds.content;

import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.SoundLoader;
import arc.audio.Sound;
import mindustry.Vars;

public class dsSounds {
    public static Sound
            //Shoot
            shootHarpoon = new Sound(),
            shootSmallWeapon = new Sound(),
            shootTank = new Sound(),
            //Mech steps
            dsMechStep = new Sound(),
            //Mics sounds
            loopAngler = new Sound(),
            loopAnglerAttack = new Sound();

    public static void load(){
        shootHarpoon = loadSound("harpoon-shoot");
        shootSmallWeapon = loadSound("shoot-small-weapons");
        shootTank = loadSound("shoot-tank-weapon");
        dsMechStep = loadSound("mech-step");
        loopAngler = loadSound("loopAngler");
        loopAnglerAttack  = loadSound("loopAnglerAttack");
    }

    //Just copy all this function
    public static Sound loadSound(String soundName){
        if(!Vars.headless){
            String name = "sounds/" + soundName;
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
