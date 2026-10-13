package ds.utilities.graphic;

import arc.math.geom.Vec2;
import arc.math.geom.Vec3;

//Not mine, by MeepOfFaith
public class Pseudo3D {
    private static final Vec2 offsetPos = new Vec2();
    private static final Vec3 scalingPos = new Vec3();

    private static final float groundTolerance = 0.001f;
    /** Viewport offset from the camera height in world units. */
    public static float viewportOffset = 80f;
    /** Minimum z value for the viewport. */
    public static float minViewportZ = 20f;

    private static float lastScale;
    private static float cameraZ;
    private static final Vec2 viewportSize = new Vec2();


}
