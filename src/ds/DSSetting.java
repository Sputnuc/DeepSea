package ds;

import arc.Core;
import mindustry.Vars;

public class DSSetting {
    public static float rayAmountMultiplier;
    public static void init(){
        Vars.ui.settings.addCategory("Deep sea",  root ->{
            root.checkPref("@setting.onlyDeepseaMusic", false);
            root.sliderPref("@setting.rayQuality", 1,1, 5,1,i -> {
                 rayAmountMultiplier = i;
                return i + "x";
            });
        });
    }

    public static boolean getOnlyModMus(){
        return Core.settings.getBool("onlyDeepseaMusic", false);
    }
}
