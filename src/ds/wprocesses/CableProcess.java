package ds.wprocesses;

import arc.struct.Seq;
import ds.type.entities.Cable;

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
