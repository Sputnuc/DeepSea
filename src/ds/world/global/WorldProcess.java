package ds.world.global;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.world.Tile;

import static mindustry.Vars.tilesize;

public abstract class WorldProcess {

    public static void logStateProcess(Object obj){
            Log.info((String) obj);
    }

    public static void logErrors(Object obj){
        Log.err((String) obj);
    }

    public void logProcess(Object obj, boolean error){
        if(!error){
            logStateProcess("Deep sea. World process: " + name + " -> " + obj);
            return;
        }
        logErrors("Deep sea. World process: " + name + " -> " + obj);
    }

    public void logProcess(Object obj){
        logProcess(obj, false);
    }

    public WorldProcess(String name){
        this.name = name;
    }

    public String name;

    public float[] worldSize(){
        if(!Vars.headless && Vars.world != null){
            return new float[]{Vars.world.width(), Vars.world.height()};
        }
        return null;
    }

    public float[] getRandomPos(){
        return new float[]{Mathf.randomSeed((long) Vars.state.tick, worldSize()[0]), Mathf.randomSeed((long) Vars.state.tick * 2, worldSize()[1])};
    }

    public float[] getRandomPosInUnits(){
        return new float[]{Mathf.randomSeed((long) Vars.state.tick, worldSize()[0] * tilesize), Mathf.randomSeed((long) Vars.state.tick * 2, worldSize()[1] * tilesize)};
    }


    public float getRandomValue(){
        return Mathf.random(Mathf.randomSeed((long) Vars.state.tick + 1));
    }

    public float getRandomValue(long seed){
        return Mathf.random(Mathf.randomSeed(seed));
    }

    public float getRandomValue(long seed, float max){
        return Mathf.random(Mathf.randomSeed(seed, max));
    }

    public Seq<Tile> getTiles(Vec2 startPosition, int w, int h){
        Seq<Tile> output = new Seq<>();
        for(int i = 0; i < w; i++){
            for(int j = 0; j < h; j++){
                output.add(Vars.world.tileWorld(startPosition.x + i, startPosition.y + j));
            }
        }
        return output;
    }

    public void update(){
    }

    public void visualUpdate(){

    }

    public void init(){
        logProcess("Initialization complete");
    }

    public void load(){
        logProcess("Loading complete");
    }

    public void reset(){

    }


}
