package ds.world.blocks.payload;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Geometry;
import arc.util.*;
import mindustry.gen.Building;
import mindustry.graphics.*;
import mindustry.world.blocks.payloads.PayloadConveyor;

import static mindustry.Vars.*;

public class ClosedPayloadConveyor extends PayloadConveyor {

    public TextureRegion overRegion;

    public ClosedPayloadConveyor(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();
        overRegion = Core.atlas.find(name + "-over");
        uiIcon = fullIcon = Core.atlas.find(name + "-full");
    }

    public class ClosedPayloadConveyorBuild extends PayloadConveyorBuild {

        @Override
        public void draw(){
            super.draw();


            Draw.z(Layer.groundUnit + 1);
            Draw.rect(overRegion, x, y, 0);
        }

        @Override
        protected boolean blends(int direction){
            if(direction == rotation){
                return next != null;
            }

            return blends(this, direction);
        }

        private   boolean blends(Building build, int direction){
            int size = build.block.size;
            int trns = build.block.size/2 + 1;
            Building accept = build.nearby(Geometry.d4(direction).x * trns, Geometry.d4(direction).y * trns);
            return accept != null &&
                    accept.block.outputsPayload &&

                    //if size is the same, block must either be facing this one, or not be rotating
                    ((accept.block.size == size
                            && Math.abs(accept.tileX() - build.tileX()) % size == 0 //check alignment
                            && Math.abs(accept.tileY() - build.tileY()) % size == 0
                            && ((accept.block.rotate && accept.tileX() + Geometry.d4(accept.rotation).x * size == build.tileX() && accept.tileY() + Geometry.d4(accept.rotation).y * size == build.tileY())
                            || !accept.block.rotate
                            || !accept.block.outputFacing)) ||

                            //if the other block is smaller, check alignment
                            (accept.block.size != size &&
                                    (accept.rotation % 2 == 0 ? //check orientation; make sure it's aligned properly with this block.
                                            Math.abs(accept.y - build.y) <= Math.abs(size * tilesize - accept.block.size * tilesize)/2f : //check Y alignment
                                            Math.abs(accept.x - build.x) <= Math.abs(size * tilesize - accept.block.size * tilesize)/2f   //check X alignment
                                    )) && (!accept.block.rotate || accept.front() == build || !accept.block.outputFacing) //make sure it's facing this block
                    );
        }


    }
}
