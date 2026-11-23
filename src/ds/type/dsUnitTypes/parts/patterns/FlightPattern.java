package ds.type.dsUnitTypes.parts.patterns;

import arc.math.Mathf;
import arc.math.geom.Vec3;

public abstract class FlightPattern {
    public abstract void evaluate(float sx, float sy, float tx, float ty, float peak, float t, Vec3 out);

    public float groundLength(float sx, float sy, float tx, float ty){
        Vec3 a = new Vec3(), b = new Vec3();
        evaluate(sx, sy, tx, ty, 0f, 0f, a);
        float len = 0f;
        for(int i = 1; i <= 32; i++){
            evaluate(sx, sy, tx, ty, 0f, i / 32f, b);
            len += Mathf.dst(a.x, a.y, b.x, b.y);
            a.set(b);
        }
        return len;
    }
}
