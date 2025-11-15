package ds.utilities;

import arc.math.Mathf;

public class dsMath {
    //for FX
    public static float degCos(float rotation, float offset){
        float angleRad = rotation * Mathf.degRad;
        return Mathf.cos(angleRad) * offset;
    }

    public static float degSin(float rotation, float offset){
        float angleRad = rotation * Mathf.degRad;
        return Mathf.sin(angleRad) * offset;
    }
}
