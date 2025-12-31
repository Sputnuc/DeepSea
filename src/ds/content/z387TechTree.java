package ds.content;

import static ds.content.blocks.zBlocks.*;
import static ds.content.units.zUnits.*;
import static ds.content.planets.dsPlanets.z387;
import static mindustry.content.TechTree.*;

public class z387TechTree {
    public static void load(){
        z387.techTree = nodeRoot("z387", coreInfluence, false, ()->{
            node(cutoff);
        });
    }
}
