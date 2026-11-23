package ds.type.dsUnitTypes.parts.patterns;

import arc.math.geom.Vec3;

public class BasicPattern extends FlightPattern{

    //Basic parabolic pattern
    @Override
    public void evaluate(float sx, float sy, float tx, float ty, float peak, float t, Vec3 out){
        out.set(sx + (tx - sx) * t, sy + (ty - sy) * t, peak * 4f * t * (1f - t));
    }
}
