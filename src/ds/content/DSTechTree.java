package ds.content;

import arc.struct.Seq;
import mindustry.game.Objectives;
import mindustry.type.ItemStack;

import static ds.content.blocks.PiBlocks.*;
import static ds.content.items.PiItems.*;
import static ds.content.liquids.PiLiquids.*;
import static ds.content.planets.DSPlanets.*;
import static ds.content.planets.DSSectorPresets.*;
import static ds.content.units.PiUnits.*;
import static mindustry.content.Items.*;
import static mindustry.content.Liquids.*;
import static mindustry.content.TechTree.*;

public class DSTechTree {
    public static void load(){
        obj312.techTree = nodeRoot("P-I-312", coreInfluence, false, ()->{
            //Sectors
            node(theBeginning, ()->{
                node(crevice, Seq.with(new Objectives.SectorComplete(theBeginning)), ()->{
                });
            });

            //Items
            nodeProduce(aluminium, ()->{
                nodeProduce(silver, ()->{
                    nodeProduce(sulfur, ()->{
                        nodeProduce(hydrogenSulfide, ()->{
                            nodeProduce(hydrogen, ()->{});
                            nodeProduce(sulfuricAcid, ()->{
                                nodeProduce(oxygen, ()->{});
                            });
                        });
                        nodeProduce(graphite, ()->{});
                        nodeProduce(manganeseHydroxide, ()->{
                            nodeProduce(manganese, ()->{
                                nodeProduce(ironstone, ()->{
                                    nodeProduce(steel, ()->{});
                                });
                                nodeProduce(magnesium, ()->{
                                    nodeProduce(lithium, ()->{});
                                });
                            });
                        });
                    });
                });
            });
            //Unit blocks
            node(deepUnitFactory, ()->{
                //Units
                node(condition, ()->{
                    node(oversight, Seq.with(new Objectives.Research(deepUnitReconstructor)), ()->{});
                });
                node(note, ()->{
                    node(sound, Seq.with(new Objectives.Research(deepUnitReconstructor)), ()->{});
                });
                node(complicity, ()->{
                    node(consequences, Seq.with(new Objectives.Research(deepUnitReconstructor)), ()->{});
                });

                node(deepUnitReconstructor, ()->{});
            });
            //Effect
            node(pressuredContainer, ()->{
                node(pressuredUnloader);
            });
            node(lightProjector, ItemStack.with(aluminium, 100, silver, 20), ()->{});
            node(repairModule);
            //Turrets
            node(cutoff, ItemStack.with(aluminium, 100, silver, 90),()->{
                node(irritation, ItemStack.with(aluminium, 150, silver, 120), ()->{
                    node(discharge);
                });
            });
            //Defence
            node(aluminiumWall, ItemStack.with(aluminium, 10), ()->{
                node(aluminiumWallLarge, ItemStack.with(aluminium, 80), ()->{});
            });
            //Logistic
            node(isolatedConveyor, ItemStack.with(aluminium, 10),()->{
                node(isolatedRouter, ItemStack.with(aluminium, 20), ()->{
                    node(isolatedJunction, ItemStack.with(aluminium, 20), ()->{});
                    node(isolatedBridge, ItemStack.with(aluminium, 30, silver, 20), ()->{});
                    node(isolatedSorter, ItemStack.with(aluminium, 20, silver, 10), ()->{
                        node(isolatedInvertedSorter, ItemStack.with(aluminium, 20, silver, 10), ()->{});
                    });
                    node(isolatedOverflowGate, ()->{
                        node(isolatedUnderflowGate);
                    });
                });
                node(pipe, ItemStack.with(silver, 20), ()->{
                    node(liquidDistributor, ItemStack.with(silver, 70), ()->{});
                    node(pipeBridge, ItemStack.with(silver, 80), ()->{});
                    node(pressuredLiquidContainer, ItemStack.with(silver, 190), ()->{});
                });
            });

            //Production
            node(hydrogenSulfideCollector, ItemStack.with(aluminium, 80, silver, 60, graphite, 20), ()->{
                node(hydrogenSulfideDiffuser, ItemStack.with(aluminium, 180, silver, 90, graphite, 40), ()->{
                    node(manganeseSynthesizer, ItemStack.with(aluminium, 420, silver, 390), Seq.with(new Objectives.Research(manganeseHydroxide)), ()->{
                    });
                });
            });

            //Drills
            node(hydraulicDrill, ItemStack.with(aluminium, 20), ()->{
                node(hydraulicWallDrill, ItemStack.with(aluminium, 150, silver, 100), ()->{
                });
                node(detonateDrill);
            });

            //Power
            node(powerWire, ItemStack.with(aluminium, 20),() ->{
                node(powerDistributor, ItemStack.with(aluminium, 125, silver, 90, manganese, 75), ()->{});
                node(condensator, ItemStack.with(aluminium, 80, silver, 40, manganese, 10), ()->{});
            });
            node(fuelGenerator, ItemStack.with(aluminium, 30, silver, 20), ()->{
                node(hydroTurbineGenerator, ItemStack.with(aluminium, 120, silver, 90, graphite, 50), ()->{
                    node(geothermalGenerator, ItemStack.with(aluminium, 500, silver, 400, manganese, 120, steel, 200), ()->{});
                });
            });
        });
    }
}
