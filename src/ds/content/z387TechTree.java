package ds.content;

import arc.struct.Seq;
import ds.content.planets.zSectors;
import mindustry.game.Objectives;
import mindustry.type.ItemStack;

import static ds.content.blocks.zBlocks.*;
import static ds.content.items.zItems.*;
import static ds.content.liquids.zLiquids.*;
import static ds.content.units.zUnits.*;
import static ds.content.planets.dsPlanets.z387;
import static mindustry.content.Liquids.*;
import static mindustry.content.TechTree.*;

public class z387TechTree {
    public static void load(){
        z387.techTree = nodeRoot("z387", coreInfluence, false, ()->{
            //Items
            nodeProduce(aluminium, ()->{
                nodeProduce(silver, ()->{
                    nodeProduce(sulfur, ()->{
                        nodeProduce(hydrogenSulfide, ()->{
                            nodeProduce(hydrogen, ()->{});
                            nodeProduce(sulfuricAcid, ()->{});
                        });
                        nodeProduce(manganeseHydroxide, ()->{
                            nodeProduce(manganese, ()->{
                                nodeProduce(magnesium, ()->{
                                    nodeProduce(lithium, ()->{
                                        nodeProduce(ironstone, ()->{
                                            nodeProduce(ferrum, ()->{});
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
            //Turrets
            node(cutoff, ItemStack.with(aluminium, 100, silver, 90),()->{
                node(irritation, Seq.with(new Objectives.SectorComplete(zSectors.theBeginning)), ()->{});
            });
            //Defence
            node(aluminiumWall, ItemStack.with(aluminium, 10), ()->{
                node(aluminiumWallLarge);
            });
            //Logistic
            node(isolatedConveyor, ItemStack.with(aluminium, 10),()->{
                node(isolatedRouter, ItemStack.with(aluminium, 80), ()->{
                    node(isolatedJunction, ItemStack.with(aluminium, 100), ()->{});
                    node(isolatedBridge, ItemStack.with(aluminium, 120, silver, 90), ()->{});
                });
                node(pipe);
            });

            //Production
            node(hydrogenSulfideCollector, ItemStack.with(aluminium, 80, silver, 60), ()->{
                node(hydrogenSulfideDiffuser, ItemStack.with(aluminium, 180, silver, 90), ()->{});
            });

            //Drills
            node(hydraulicDrill, ItemStack.with(aluminium, 20), ()->{});

            //Power
            node(powerTransmitter, ItemStack.with(aluminium, 90, silver, 20),() ->{});
            node(hydroTurbineGenerator, ItemStack.with(aluminium, 120, silver, 90), ()->{});
        });
    }
}
