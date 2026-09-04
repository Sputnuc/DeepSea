package ds;

import arc.Core;
import mindustry.Vars;

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
        });
    }

    public static boolean getOnlyModMus(){
        return Core.settings.getBool("onlyDeepseaMusic", false);
    }
}
