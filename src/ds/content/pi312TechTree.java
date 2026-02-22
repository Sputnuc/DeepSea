package ds.content;

import arc.struct.Seq;
import ds.content.planets.piSectors;
import mindustry.game.Objectives;
import mindustry.type.ItemStack;

import static ds.content.blocks.piBlocks.*;
import static ds.content.items.piItems.*;
import static ds.content.liquids.piLiquids.*;
import static ds.content.planets.dsPlanets.*;
import static ds.content.planets.piSectors.*;
import static ds.content.units.piUnits.*;
import static mindustry.content.Liquids.*;
import static mindustry.content.TechTree.*;

public class pi312TechTree {
    public static void load(){
        pi312.techTree = nodeRoot("P-I-312", coreInfluence, false, ()->{
            //Sectors
            node(theBeginning, ()->{
                node(canyon, Seq.with(new Objectives.SectorComplete(theBeginning)), ()->{
                    node(plate, Seq.with(new Objectives.SectorComplete(canyon)),()->{});
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
                        nodeProduce(manganeseHydroxide, ()->{
                            nodeProduce(manganese, ()->{
                                nodeProduce(magnesium, ()->{
                                    nodeProduce(lithium, ()->{
                                        nodeProduce(ironstone, ()->{
                                            nodeProduce(steel, ()->{});
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
            //Unit blocks
            node(shadeUnitFactory, Seq.with(new Objectives.SectorComplete(plate)), ()->{
                //Units
                node(condition);
                node(note);
                node(complicity);
            });
            //Effect
            node(lightProjector);
            node(repairModule);
            //Turrets
            node(cutoff, ItemStack.with(aluminium, 100, silver, 90),()->{
                node(irritation, Seq.with(new Objectives.SectorComplete(piSectors.theBeginning)), ()->{
                    node(discharge);
                });
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
                node(pipe, ()->{
                    node(liquidDistributor);
                    node(pipeBridge);
                });
            });

            //Production
            node(hydrogenSulfideCollector, ItemStack.with(aluminium, 80, silver, 60), ()->{
                node(hydrogenSulfideDiffuser, ItemStack.with(aluminium, 180, silver, 90), ()->{
                    node(manganeseSynthesizer, ItemStack.with(aluminium, 420, silver, 390), Seq.with(new Objectives.Research(manganeseHydroxide)), ()->{});
                    node(decompositionChamber, Seq.with(new Objectives.SectorComplete(plate)), ()->{});
                });
            });

            //Drills
            node(hydraulicDrill, ItemStack.with(aluminium, 20), ()->{
                node(hydraulicWallDrill, ItemStack.with(aluminium, 200, silver, 100), Seq.with(new Objectives.OnSector(canyon)),()->{
                    node(gasBore, Seq.with(new Objectives.SectorComplete(plate)), ()->{});
                });
            });

            //Power
            node(powerTransmitter, ItemStack.with(aluminium, 90, silver, 20),() ->{
                node(powerDistributor);
                node(condensator);
            });
            node(hydroTurbineGenerator, ItemStack.with(aluminium, 120, silver, 90), ()->{
                node(geothermalGenerator, ItemStack.with(aluminium, 500, silver, 400, manganese, 120), ()->{});
            });
        });
    }
}
