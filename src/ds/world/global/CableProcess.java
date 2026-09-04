package ds.world.global;

import arc.Events;
import arc.graphics.g2d.Draw;
import arc.struct.Seq;
import arc.util.Log;
import ds.type.entities.Cable;
import mindustry.game.EventType;

public class CableProcess extends  WorldProcess{
    public CableProcess(String name) {
        super(name);
    }

    public static Seq<Cable> cableHandler;

    @Override
    public void init(){
        eraseCables();
        super.init();
    }

    public static void eraseCables(){
        cableHandler = new Seq<>();
    }

    public static void initCable(Cable cable){
        cable.init();
    }

    public static void addCable(Cable cable){
        cableHandler.add(cable);
    }

    public static void addCables(Cable...cables){
        cableHandler.add(cables);
    }

    public static void removeCable(int idx){
        cableHandler.remove(idx);
    }

    public static void removeCable(Cable cable){
        cableHandler.remove(cable);
    }

    @Override
    public void reset(){
        eraseCables();
    }

    @Override
    public void update(){
        for(Cable c : cableHandler){
            c.update();
        }
    }

    @Override
    public void visualUpdate(){
        for(Cable c : cableHandler){
            c.draw();
        }
    }
}
