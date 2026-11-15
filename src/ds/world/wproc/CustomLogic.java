package ds.world.wproc;

import ds.world.wproc.statemets.UCUnlockStatement;
import mindustry.gen.LogicIO;
import mindustry.logic.LAssembler;

public class CustomLogic {
    public static void init(){
        LAssembler.customParsers.put("unlockcontent", UCUnlockStatement::new);
        LogicIO.allStatements.addUnique(UCUnlockStatement::new);
    }
}
