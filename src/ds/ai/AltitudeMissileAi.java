package ds.ai;

import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import deepsea.gen.BallisticMovementc;
import deepsea.gen.KineticImpactc;
import ds.type.dsUnitTypes.IcbmUnitType;
import mindustry.ai.types.MissileAI;
import mindustry.gen.TimedKillc;
import mindustry.gen.Velc;

public class AltitudeMissileAi extends MissileAI {
    @Override
    protected void resetTimers(){
        timer.reset(timerTarget, 5f);
    }

    /**
     * Точный расчёт оставшегося времени полёта (в тиках) с учётом:
     * 1. Нелинейного разгона (t / missileAccelTime)^2
     * 2. Текущей физической скорости unit.vel().len() (инерции Mindustry)
     */
    private float estimateTimeLeft(float dist, float time, float maxSpeed, float accelTime){
        if(dist <= 0.01f) return 0f;
        float currentRealSpeed = unit.vel().len();

        // Если фаза разгона уже прошла или не задана
        if(accelTime <= 0f || time >= accelTime){
            // Учитываем, что реальная скорость unit.vel() может ещё догонять maxSpeed из-за unit.accel
            float effectiveSpeed = Mathf.lerp(currentRealSpeed, maxSpeed, 0.5f);
            return dist / Math.max(effectiveSpeed, 0.1f);
        }

        float ratio = Mathf.clamp(time / accelTime);
        float remainingAccelTime = accelTime - time;

        // Интеграл пути, который ракета пройдёт до конца фазы разгона: (Vmax * T / 3) * (1 - (t/T)^3)
        float distDuringAccel = (maxSpeed * accelTime / 3f) * (1f - ratio * ratio * ratio);

        // Вариант А: цель дальше, чем дистанция разгона (ракета успеет выйти на крейсерскую скорость)
        if(dist >= distDuringAccel){
            float cruiseDist = dist - distDuringAccel;
            return remainingAccelTime + (cruiseDist / Math.max(maxSpeed, 0.1f));
        }
        // Вариант Б: цель ближе дистанции разгона (удар произойдёт ещё на этапе набора скорости)
        else {
            // Решаем обратное уравнение интеграла: (t_impact / T)^3 = (t / T)^3 + 3 * dist / (Vmax * T)
            float impactRatioCubed = (ratio * ratio * ratio) + (3f * dist) / Math.max(maxSpeed * accelTime, 0.001f);
            float impactTime = accelTime * (float)Math.cbrt(impactRatioCubed);
            return Math.max(impactTime - time, 0.1f);
        }
    }

    @Override
    public void updateMovement(){
        unloadPayloads();
        float time = unit instanceof TimedKillc t ? t.time() : 1000000f;

        if(!(unit.type() instanceof IcbmUnitType t) || !(unit instanceof BallisticMovementc c)){
            super.updateMovement();
            return;
        }
        float tx = shooter != null && !shooter.dead() ? shooter.aimX : c.targetX();
        float ty = shooter != null && !shooter.dead() ? shooter.aimY : c.targetY();
        float targetAltitude = 0f;
        float targetVx = 0f, targetVy = 0f, targetVz = 0f;

        if(target != null){
            tx = target.getX();
            ty = target.getY();
            if(target instanceof Velc v){
                targetVx = v.vel().x;
                targetVy = v.vel().y;
            }
            if(target instanceof BallisticMovementc targetBallistic){
                targetAltitude = targetBallistic.altitude();
                targetVz = targetBallistic.altVel();
            }
        }
        float maxSpeed = unit.speed();
        float accelTime = unit.type.missileAccelTime;
        float rawDist2D = Mathf.dst(unit.x, unit.y, tx, ty);
        float timeLeft = estimateTimeLeft(rawDist2D, time, maxSpeed, accelTime);
        float aimX = tx + targetVx * timeLeft;
        float aimY = ty + targetVy * timeLeft;
        float aimZ = Math.max(0f, targetAltitude + targetVz * timeLeft);

        // Уточняем дистанцию и время до точки упреждения
        float dist2D = Mathf.dst(unit.x, unit.y, aimX, aimY);
        timeLeft = estimateTimeLeft(dist2D, time, maxSpeed, accelTime);

        c.setTargetPos(Tmp.v31.set(tx, ty, targetAltitude));

        if(time >= unit.type.homingDelay){
            unit.lookAt(aimX, aimY);
        }
        float currentTargetSpeed = accelTime <= 0f
                ? maxSpeed
                : Mathf.pow(Math.min(time / accelTime, 1f), 2f) * maxSpeed;
        unit.moveAt(vec.trns(unit.rotation, currentTargetSpeed));
        boolean isRaised = unit instanceof KineticImpactc k && k.isRaised();
        if(dist2D < t.alignmentDistance && isRaised){
            if(timeLeft > 0.1f){
                float deltaZ = aimZ - c.altitude();
                float requiredAltVel = (deltaZ / timeLeft) + (0.5f * t.gravity * timeLeft);
                requiredAltVel = Mathf.clamp(requiredAltVel, -t.descendSpeed, t.raiseSpeed);

                float nextAltVel = c.altVel() - t.gravity * Time.delta;
                nextAltVel = Mathf.approachDelta(nextAltVel, requiredAltVel, t.ballisticAccel);
                c.altVel(nextAltVel);
            }
        } else {
            c.moveAltitude(t.peakAltitude, t.raiseSpeed);
        }
    }

    @Override
    public boolean retarget(){
        return timer.get(timerTarget, 4f);
    }
}
