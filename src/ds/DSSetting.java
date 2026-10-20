package ds;

import arc.Core;
import arc.util.Log;
import mindustry.Vars;
import mindustry.ctype.UnlockableContent;

public class DSSetting {
    public static float rayAmountMultiplier;

    public static float[] raysMultipliers = {1, 1.5f, 2};

    public static void init(){
        Vars.ui.settings.addCategory("Deep sea",  root ->{
            root.checkPref("@setting.onlyDeepseaMusic", false);
            root.sliderPref("@setting.rayQuality", 0,0, 2,1,i -> {
                 rayAmountMultiplier = raysMultipliers[i];
                return (i + 1) + "x";
            });
            root.button("@button.unlockContent", ()->{
                Log.info("DeepSea - Unlocking all blocks");
                for(UnlockableContent c : Vars.content.blocks()){
                    c.quietUnlock();
                }

                Log.info("DeepSea - Unlocking all items");
                for(UnlockableContent c : Vars.content.items()){
                    c.quietUnlock();
                }

                Log.info("DeepSea - Unlocking all liquids");
                for(UnlockableContent c : Vars.content.liquids()){
                    c.quietUnlock();
                }

                Log.info("DeepSea - Unlocking all units");
                for(UnlockableContent c : Vars.content.units()){
                    c.quietUnlock();
                }
            });
        });
    }

    public static boolean getOnlyModMus(){
        return Core.settings.getBool("onlyDeepseaMusic", false);
    }
}
