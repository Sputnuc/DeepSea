package ds.world.ai;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import mindustry.entities.units.AIController;

import static mindustry.Vars.world;

public class NodeAttackAi extends AIController {

    public Vec2 Attack = null;

    public Vec2 findAttackPattern(){
        Vec2 target = new Vec2();
        int w = world.unitWidth();
        target.set(Mathf.random(w), 0);
        return target;
    }
    @Override
    public void updateMovement(){
        if(Attack == null) Attack = findAttackPattern();
        moveTo(Attack, 1);
    }
}
