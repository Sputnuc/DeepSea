package ds.world.blocks.effect;

import mindustry.*;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.blocks.storage.StorageBlock;

import static mindustry.Vars.*;


public class OutpostBlock extends StorageBlock {

    public int storageCapacity = 200;
    public boolean incinerateNonBuildable = false;

    public OutpostBlock(String name) {
        super(name);
        update = true;
    }


    public class OutpostBlockBuilding extends StorageBuild{

        @Override
        public boolean acceptItem(Building source, Item item){
            return state.rules.coreIncinerates || items.get(item) < getMaximumAccepted(item);
        }

        @Override
        public int getMaximumAccepted(Item item){
            return state.rules.coreIncinerates ? Integer.MAX_VALUE/2 : storageCapacity;
        }

        @Override
        public void updateTile(){
            super.updateTile();
        }

        @Override
        public void itemTaken(Item item){
            if(state.isCampaign() && team == state.rules.defaultTeam){
                //update item taken amount
                state.rules.sector.info.handleCoreItem(item, -1);
            }
        }


        @Override
        public void handleStack(Item item, int amount, Teamc source){
            boolean incinerate = incinerateNonBuildable && !item.buildable;
            int realAmount = incinerate ? 0 : Math.min(amount, storageCapacity - items.get(item));
            super.handleStack(item, realAmount, source);

            if(team == state.rules.defaultTeam && state.isCampaign()){
                if(!incinerate){
                    state.rules.sector.info.handleCoreItem(item, amount);
                }

                if(realAmount == 0 && wasVisible){
                    Fx.coreBurn.at(x, y);
                }
            }
        }

        @Override
        public int removeStack(Item item, int amount){
            int result = super.removeStack(item, amount);

            if(team == state.rules.defaultTeam && state.isCampaign()){
                state.rules.sector.info.handleCoreItem(item, -result);
            }

            return result;
        }

        @Override
        public void handleItem(Building source, Item item){
            boolean incinerate = incinerateNonBuildable && !item.buildable;

            if(team == state.rules.defaultTeam){
                state.stats.coreItemCount.increment(item);
            }

            if(Vars.net.server() || !Vars.net.active()){
                if(team == state.rules.defaultTeam && state.isCampaign() && !incinerate){
                    state.rules.sector.info.handleCoreItem(item, 1);
                }

                if(items.get(item) >= storageCapacity || incinerate){
                    incinerateEffect(this, source);
                }else{
                    super.handleItem(source, item);
                }
            }else if(((state.rules.coreIncinerates && items.get(item) >= storageCapacity) || incinerate)){
                //create item incineration effect at random intervals
                incinerateEffect(this, source);
            }
        }
        @Override
        public byte version(){
            return 1;
        }
    }
}
