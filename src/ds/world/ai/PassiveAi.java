package ds.world.ai;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Time;
import mindustry.entities.units.AIController;

import static mindustry.Vars.*;

public class PassiveAi extends AIController {
    private final float targetMoveInterval = 600f;
    private final Vec2 targetCoord = new Vec2();
    private float switchInterval = 0;
    @Override
    public void init(){
        findNewTargetPos();
    };
    @Override
    public void updateMovement(){
        updateTimer();
        if(checkTarget()){
            findNewTargetPos();
        }
        moveToPos();
    }
    private void updateTimer(){
        if(switchInterval < targetMoveInterval) switchInterval += Time.delta;
    }
    private boolean checkTarget(){
        return ((unit.within(targetCoord, 10) && switchInterval >= targetMoveInterval) || switchInterval >= targetMoveInterval);
    }
    private void findNewTargetPos() {
        if (unit == null)return;
        switchInterval = 0;
        int w = world.unitWidth();
        int h = world.unitHeight();
        targetCoord.set(
                Mathf.random(10, w - 10),
                Mathf.random(10, h - 10)
        );
    }
    private void moveToPos(){
        moveTo(targetCoord, 3);
    }
}
