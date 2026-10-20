package ds.world.wprocess.instructions;

import arc.Core;
import arc.util.Log;
import mindustry.Vars;
import mindustry.logic.LExecutor;
import mindustry.logic.LVar;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.type.UnitType;
import mindustry.world.Block;

public class UCUnlockInstruciton implements LExecutor.LInstruction {
    public LVar LType;

    public LVar LcontentName;

    public UCUnlockInstruciton(LVar type ,LVar content){
        this.LcontentName = content;
        this.LType = type;
    }

    public UCUnlockInstruciton(){}

    @Override
    public void run(LExecutor lExecutor) {
        if(LcontentName.obj() instanceof  String cname){
            if(LType.obj() instanceof  String ctype){
                if(Vars.state.isCampaign()){
                    switch (ctype) {
                        case "item": {
                            Item var = Vars.content.items().find(i -> i.name.equals(cname));
                            if(var != null && !var.unlocked()) var.unlock();
                        }
                        case "liquid": {
                            Liquid var = Vars.content.liquids().find(i -> i.name.equals(cname));
                            if(var != null && !var.unlocked()) var.unlock();
                        }
                        case "block": {
                            Block var = Vars.content.blocks().find(i -> i.name.equals(cname));
                            if(var != null && !var.unlocked()) var.unlock();
                        }
                        case "unit": {
                            UnitType var = Vars.content.units().find(i -> i.name.equals(cname));
                            if(var != null && !var.unlocked()) var.unlock();
                        }
                        default: Log.warn("Deep sea. World processor UCUnlockInstruction :: FAIL (Cannot unlock... content isn't in provided types)");
                    }
                } else Log.warn("Deep sea. World processor UCUnlockInstruction :: FAIL (Cannot unlock... isn't in campaign now)");
            }
        } else Log.warn("Deep sea. World processor UCUnlockInstruction ::FAIL (Cannot unlock... content isn't exist, or invalid content name)");
    }
}
