package ds.world.blocks.power;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.struct.Seq;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.world.Block;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Stat;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;
import static mindustry.input.Placement.calculateBridges;

public class PowerWire extends PowerNode {
    public TextureRegion connectorRegion = new TextureRegion();
    public TextureRegion connectorRegionExt = new TextureRegion();

    public Color glowColor = Color.valueOf("ffa6b0");

    public float glowWidth = 6;


    public PowerWire(String name) {
        super(name);
        hasPower = true;
        conductivePower = true;
        laserRange = 0f;
        maxNodes = 0;
        conveyorPlacement = true;
        solid = false;
        underBullets = true;
        configurable = false;
        enableDrawStatus = false;
        swapDiagonalPlacement = false;
        group = BlockGroup.power;
        squareSprite = false;
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.remove(Stat.powerRange);
        stats.remove(Stat.powerConnections);
    }

    @Override
    public void setBars() {
        super.setBars();
        removeBar("connections");
    }

    @Override
    public void load(){
        super.load();
        connectorRegion = Core.atlas.find(this.name + "-connector");
        connectorRegionExt = Core.atlas.find(this.name + "-connector-extended");
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        drawPotentialLinks(x, y);
        drawOverlay(x * tilesize + offset, y * tilesize + offset, rotation);
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        super.drawPlanRegion(plan, list);
        if(plan.tile() == null) return;

        list.each(other -> {
            if (other.breaking || other == plan || !(other.block.hasPower || other.block.consumesPower || other.block.conductivePower) || other.samePos(plan)) return;
            for(int i = 0; i < 4; i++){
                Point2 p = Geometry.d4(i);
                if(other.x == plan.x + p.x && other.y == plan.y + p.y){
                    Draw.rect(connectorRegion, plan.x, plan.y, Angles.angle(plan.x, plan.y, other.x, other.y));
                }
            }
        });

    }
    @Override
    public void changePlacementPath(Seq<Point2> points, int rotation) {
        //no
    }

    @Override
    public void drawLaser(float x1, float y1, float x2, float y2, int size1, int size2) {
        //no
    }

    public class WireBuild extends PowerNodeBuild{

        @Override
        public void draw() {
            int trns = block.size/2 + 1;
            for(int i = 0; i < 4; i++){
                Point2 p = Geometry.d4(i);
                Building accept = this.nearby(p.x * trns, p.y * trns);
                if(accept != null && accept.block.hasPower){
                    Drawf.light(x, y,x + p.x,y + p.y, glowWidth, glowColor, this.power.graph.getPowerBalance() > 0 ? 0.25f : 0.05f);
                    if(accept.block.squareSprite || accept.block == this.block){
                        Draw.rect(connectorRegion, x, y, Angles.angle(p.x, p.y));
                    } else Draw.rect(connectorRegionExt, x, y, Angles.angle(p.x, p.y));
                }
            }
            Draw.reset();
        }

        @Override
        public void drawSelect(){
            Lines.stroke(1f);
            int trns = block.size/2 + 1;
            for(int i = 0; i < 4; i++){
                Building accept = this.nearby(Geometry.d4(i).x * trns, Geometry.d4(i).y * trns);
                if(accept != null && accept.block.hasPower){
                    Drawf.square(accept.x, accept.y, accept.block.size * tilesize / 2f + 2, 90, Pal.place);
                }
            }
            Draw.reset();
        }
    }
}
