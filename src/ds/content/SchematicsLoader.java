package ds.content;

import mindustry.game.Schematic;
import mindustry.game.Schematics;

public class SchematicsLoader {
    public static Schematic
            baseCoreInfluence;
    public static void load(){
        baseCoreInfluence = Schematics.readBase64("bXNjaAF4nF3MSwoCMRBF0dfdfqAFh+6il+IKxEFZeWIgqYSuciTuXZ0KlzO8mDBtsTGpxFHbyiXbvTxpSszaLGhxlo7x9cbeg1Jzwuz6YJXI6jgkuq65R24GYFfkxuIYL9cBp0R2pyx/Y2D49eUDUe4kFg==");
    }
}
