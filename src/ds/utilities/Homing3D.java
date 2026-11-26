package ds.utilities;

import arc.math.Mathf;
import arc.math.geom.Vec3;

public class Homing3D {
    private static final Vec3 A = new Vec3(), B = new Vec3(),
            K = new Vec3(), KA = new Vec3();

    public static void rotateTowards(Vec3 vel,
                                     float x, float y, float z,
                                     float tx, float ty, float tz,
                                     float accel, float gravity,
                                     float homingPowerDeg, float dt){
        float v2 = vel.len2();
        if(v2 < 1e-6f) return;

        float pitch = homingPitch(x, y, z, tx, ty, v2, accel, gravity);
        float polar = Mathf.pi - (pitch + Mathf.halfPi);
        float yaw   = Mathf.atan2(ty - y, tx - x);        // радианы

        B.setFromSpherical(yaw, polar).setLength2(v2);    // целевой вектор

        float cosA = Mathf.clamp(vel.dot(B) / v2, -1f, 1f); // делим на v2, не sqrt(v2)!
        float angleDeg = (float)Math.acos(cosA) * Mathf.radDeg;

        float h = homingPowerDeg * dt;
        if(angleDeg <= h){
            vel.set(B);
            return;
        }
        K.set(vel).crs(B).nor();
        KA.set(K).crs(vel);

        vel.scl(Mathf.cosDeg(h)).add(KA.scl(Mathf.sinDeg(h)));
    }

    public static float homingPitch(float x1, float y1, float z1, float x2, float y2, float v2, float a, float g){
        float dst = Mathf.dst(x1, y1, x2, y2);

        float p1 = 2 * g * z1;
        float p2 = 2 * a * dst;
        float p3 = -a * z1 - g * dst;
        float p4 = (p3 * p3 - p1 * p2) / v2; //v2 = v * v
        float A = p2 - p1;
        float B = p3;
        float C = p4 - p1;

        float D = Mathf.sign(B) * Mathf.sqrt(0.25f * A * A + B * B);
        float E = (float)(Math.atan(0.5f * A / B)) / 2f;

        return (float)(-Math.asin((C - 0.5f * A) / D)) / 2 - E;
    }
}
