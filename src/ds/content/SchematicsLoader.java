package ds.content;

import mindustry.game.Schematic;
import mindustry.game.Schematics;

public class SchematicsLoader {
    public static Schematic
            baseCoreInfluence;
    public static void load(){
        baseCoreInfluence = Schematics.readBase64("bXNjaAF4nF3MQQqAIBRF0VdKg4KG7aKltIJoYPYCwb6iNor2Xk2DyxleKCgNLeYgehsSRye7PymWaG2QQimTiaivG93GbJOLxQUB0Hiz0mfU81Jh2MiYacbfA6i+Xh5GWBz5");
    }
}
