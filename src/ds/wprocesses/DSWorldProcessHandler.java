package ds.wprocesses;

import arc.Events;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType;

public class DSWorldProcessHandler {

    public static Seq<WorldProcess> processes = new Seq<>();

    public static void addProcesses(WorldProcess... objects){
        processes.add(objects);
    }

    public static void addProcess(WorldProcess process){
        processes.add(process);
    }

    public static void load(){
        Log.info("Deep sea. World processor loading. \n");
        for (WorldProcess process : processes){
            process.load();
        }
    }

    public static void init(){
        Log.info("Deep sea. World processor initialization. \n");
        for (WorldProcess process : processes){
            process.init();
        }

        Log.info("Deep sea. World processor -> visual updater starting");
        Events.run(EventType.Trigger.draw, ()->{
            for(WorldProcess process : processes) {
                process.visualUpdate();
            }
        });
    }

    public static void update(){
        forceUpdate();
        if(Vars.world != null && Vars.state.isGame() && !Vars.state.isPaused() && !processes.isEmpty()){
            for(WorldProcess procces : processes){
                procces.update();
            }
        }
    }

    public static void forceUpdate(){
        if(Vars.world != null && Vars.state.isGame() && !processes.isEmpty()){
            for(WorldProcess procces : processes){
                procces.forceUpdate();
            }
        }
    }

    public static void resetProcesses(){
        for (WorldProcess process : processes){
            process.reset();
        }
    }
}
