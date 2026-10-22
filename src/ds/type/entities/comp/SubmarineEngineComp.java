package ds.type.entities.comp;

import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Log;
import arc.util.Time;
import arc.util.Tmp;
import ds.type.entities.dsUnits.SubmarineUnitType;
import ds.type.entities.dsUnits.parts.EngineInstance;
import ent.anno.Annotations;
import mindustry.gen.*;
import mindustry.type.UnitType;

@Annotations.EntityComponent
abstract class SubmarineEngineComp implements Entityc, Physicsc, Drawc{

    EngineInstance[] engines;

    float enginesEfficiency;

    @Annotations.Import UnitType type;

    @Annotations.Import Vec2 tmp2, tmp1;

    @Annotations.Import float rotation, speedMultiplier;
    @Annotations.Import Vec2 vel;

    public void update(){
        updateEngines();
    }

    void updateEngines(){
        enginesEfficiency = Mathf.approach(enginesEfficiency, vel.len() / type.speed,  Time.delta);
        for(EngineInstance eng : engines){
            eng.update(self(), enginesEfficiency);
        }
    }

    void setEngines(UnitType type){
        if(type instanceof SubmarineUnitType subm){
            engines = new EngineInstance[subm.undEngines.size];
            for(int i = 0; i < subm.undEngines.size; i++){
                engines[i] = new EngineInstance(subm.undEngines.get(i));
            }
        }
    }

    public void setType(UnitType type){
        setEngines(type);
    }

    public void moveAt(Vec2 vector, float acceleration){
        Vec2 t = tmp1.set(vector); //target vector
        tmp2.set(t).sub(vel).limit(acceleration * vector.len() * Time.delta); //delta vector
        vel.add(tmp2);

        if(type instanceof SubmarineUnitType s){
            if(!s.undEngines.isEmpty()){
                for(var e : s.undEngines){
                    e.update(self());
                }
            }
        }

    }

    public void moveAt(Vec2 vector){
        moveAt(vector, type.accel);
    }

    public void rotateMove(Vec2 vec){
        moveAt(Tmp.v2.trns(rotation, vec.len()));

        if(!vec.isZero()){
            rotation = Angles.moveToward(rotation, vec.angle(), type.rotateSpeed * Time.delta * speedMultiplier);
        }
    }

    public void movePref(Vec2 movement){
        if(type.omniMovement){
            moveAt(movement);
        }else{
            rotateMove(movement);
        }
    }

    @Override
    public float clipSize(){
        return type.hitSize * 1.1f;
    }

    @Override
    public void draw(){
        type.draw(self());
    }
}
