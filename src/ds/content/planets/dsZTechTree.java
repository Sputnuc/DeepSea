package ds.content.planets;

import ds.content.blocks.zBlocks;

import static mindustry.content.TechTree.nodeRoot;

public class dsZTechTree {
    public static void load(){
        dsPlanets.z378.techTree = nodeRoot("@planet.z378.name", zBlocks.coreInfluence, false, ()->{});
    }
}
