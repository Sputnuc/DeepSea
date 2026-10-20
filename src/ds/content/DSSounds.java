package ds.content;

import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.SoundLoader;
import arc.audio.Sound;
import arc.util.Log;
import mindustry.Vars;

public class DSSounds {
    public static Sound
            //Shoot
            shootHarpoon,
            shootSmallWeapon,
            shootMediumTank,
            shootImpulse,
            shootMortar ,
            //Mech steps
            dsMechStep,
            //Mics sounds
            loopAngler ,
            loopAnglerAttack
            ;
            //Blocks

    public static void load(){
        shootHarpoon = loadSound("harpoonShoot");
        shootSmallWeapon = loadSound("shootSmallWeaponAlt");
        shootMediumTank = loadSound("shootTank");
        shootImpulse = loadSound("shootImpulse");
        shootMortar = loadSound("shootMortar");
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
