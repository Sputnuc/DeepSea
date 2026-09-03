package ds.type.entities;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Tmp;
import ds.draw.DrawWire;
import ds.world.global.CableProcess;
import mindustry.Vars;
import mindustry.gen.Posc;
import mindustry.graphics.Layer;

public class Cable {

    private Seq<CableSegment> cableSegments;

    //Main owner (like a harpoon turret)
    private Posc owner1;
    //Second owner (like a harpoon projectile)
    private Posc owner2;

    //Physical cable position
    private Vec2 point1;
    private Vec2 point2;

    //Drawing region
    public static TextureRegion baseRegion;

    public static TextureRegion baseNodeRegion;

    public TextureRegion region;

    public TextureRegion nodeRegion;


    public float elasticity = 1;

    //the max extent to which cables can expand under the influence of force
    public float maxExpandthreshold = 1.15f;

    public float initialSegmentLen = 25;

    public float height = 10;

    public float width = 4;

    public float clipSize = 50;

    public Cable(Vec2 pos1, Vec2 pos2){
        this.point1 = pos1;
        this.point2 = pos2;
        this.owner1 = null;
        this.owner2 = null;
        createCable(pos1, pos2);
    }

    public Cable(Posc owner1, Posc owner2){
        this.owner1 = owner1;
        this.owner2 = owner2;
        this.point1 = (Vec2) owner1;
        this.point2 = (Vec2) owner2;
        createCable(point1, point2);
        baseRegion = Core.atlas.find("deepsea-harpoon-wire");
        Log.debug(baseRegion.found() ? "Deep sea log: Wire region is found" : "Deep sea log: Wire region isn't found" );
        baseNodeRegion = Core.atlas.find("deepsea-harpoon-wire-end");
        Log.debug(baseNodeRegion.found() ? "Deep sea log: Wire start region is found" : "Deep sea log: Wire start region isn't found" );
    }

    public void createCable(Vec2 pos1, Vec2 pos2){
        cableSegments = new Seq<>();
        float len = pos1.dst(pos2);
        int segmentsAmount = Math.round(len / initialSegmentLen);
        Vec2 tmp = pos1;
        Vec2 tmp2;
        CableSegment segment = null;
        for(int i = 0; i < segmentsAmount; i++){
            float progress = (float) i / segmentsAmount;
            tmp2 = new Vec2(Mathf.lerp(pos1.x, pos2.x, progress), Mathf.lerp(pos1.y, pos2.y, progress));
            CableSegment curSegment = new CableSegment(tmp, tmp2, segment, null);
            Log.info("Creating cable segment -> " + i + " Coordinates -> {" + curSegment.pos1.x + " ; " + curSegment.pos1.y + " to " + curSegment.pos2.x + " ; " + curSegment.pos2.y + "}");
            if(i > 0){
                cableSegments.get(i - 1).nextSegment = curSegment;
            }
            cableSegments.add(curSegment);
            segment = curSegment;
            tmp = new Vec2(tmp2);
        }
        CableProcess.initCable(this);
    }

    public void update(){

    }

    public void draw(){
        for(CableSegment c : cableSegments){
            c.draw();
        }
    }

    public static void load(){
        baseRegion = Core.atlas.find("deepsea-harpoon-wire");
        Log.info(baseRegion.found() ? "Deep sea log: Wire region is found" : "Deep sea log: Wire region isn't found" );
        baseNodeRegion = Core.atlas.find("deepsea-harpoon-wire-end");
        Log.info(baseNodeRegion.found() ? "Deep sea log: Wire start region is found" : "Deep sea log: Wire start region isn't found" );
    }

    public void init(){

    }

    public class CableSegment {

        private Vec2 pos1, pos2;

        private CableSegment nextSegment, previousSegment;

        protected Seq<Vec2> forces;

        public CableSegment(Vec2 point1, Vec2 point2, CableSegment previousSegment, CableSegment nextSegment){
            pos1 = new Vec2(point1);
            pos2 = new Vec2(point2);
            this.previousSegment = previousSegment;
            this.nextSegment = nextSegment;
        }

        private boolean isVisible(){
            return Core.camera.bounds(Tmp.r1).overlaps(Tmp.r2.setCentered((pos1.x + pos2.x) / 2, (pos1.y + pos2.y) / 2, clipSize));
        }

        public void draw(){
            if(!isVisible()) return;
            Draw.z(Layer.turret - 1);
            Lines.stroke(width);
            Lines.line(baseRegion, pos1.x, pos1.y, pos2.x, pos2.y, true);

            Draw.z(Layer.turret);
            if(previousSegment == null){
                Draw.rect(baseNodeRegion, pos1.x, pos1.y, baseNodeRegion.width * width / 15, baseNodeRegion.width * width / 15, Angles.angle(pos1.x, pos1.y, pos2.x, pos2.y));
            }
            Draw.rect(baseNodeRegion, pos2.x, pos2.y, baseNodeRegion.width * width / 15, baseNodeRegion.width * width / 15, Angles.angle(pos1.x, pos1.y, pos2.x, pos2.y));
            Draw.reset();
        }


        public void update(){

        }

        private void updatePhysics(){

        }

        public void removeThisSegment(){
            nextSegment.previousSegment = null;
            cableSegments.remove(this);
        }
    }
}
