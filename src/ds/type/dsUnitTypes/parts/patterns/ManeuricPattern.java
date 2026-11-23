package ds.type.dsUnitTypes.parts.patterns;

import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Vec3;

public class ManeuricPattern extends FlightPattern{

    public float overshoot = 80f;
    public float hookSize = 1.2f;
    public float side = 1f;
    public float descentStart = 0.6f;
    public float riseEnd = 0.12f;

    @Override
    public void evaluate(float sx, float sy, float tx, float ty, float peak, float t, Vec3 out){
        float dx = tx - sx, dy = ty - sy;
        float d = Mathf.len(dx, dy);
        if(d < 1e-3f){
            out.set(sx, sy, 0f);
            return;
        }

        float ux = dx / d, uy = dy / d;
        float px = -uy * side, py = ux * side;
        float ox = tx + ux * overshoot, oy = ty + uy * overshoot;
        float k = overshoot * hookSize;
        float c1x = ox + ux * k, c1y = oy + uy * k;
        float c2x = tx + px * k, c2y = ty + py * k;
        float lenA = d + overshoot;
        float lenB = (Mathf.dst(ox, oy, c1x, c1y) + Mathf.dst(c1x, c1y, c2x, c2y) + Mathf.dst(c2x, c2y, tx, ty)
                + Mathf.dst(ox, oy, tx, ty)) / 2f;
        float a = lenA / (lenA + lenB);

        float x, y;
        if(t < a){
            float u = t / a;
            x = sx + (ox - sx) * u;
            y = sy + (oy - sy) * u;
        }else{
            float s = (t - a) / (1f - a), is = 1f - s;
            float b0 = is * is * is, b1 = 3f * is * is * s, b2 = 3f * is * s * s, b3 = s * s * s;
            x = b0 * ox + b1 * c1x + b2 * c2x + b3 * tx;
            y = b0 * oy + b1 * c1y + b2 * c2y + b3 * ty;
        }

        float rise = Interp.smooth.apply(Mathf.clamp(t / riseEnd));
        float fall = Interp.smooth.apply(Mathf.clamp((t - descentStart) / (1f - descentStart)));
        out.set(x, y, peak * rise * (1f - fall));
    }
}
