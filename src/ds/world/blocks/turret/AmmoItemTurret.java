package ds.world.blocks.turret;

import arc.Events;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.io.Reads;
import arc.util.io.Writes;
import ds.type.AmmoItem;
import ds.world.meta.DSStatValues;
import mindustry.Vars;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.bullet.BulletType;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.gen.Teamc;
import mindustry.graphics.Pal;
import mindustry.logic.LAccess;
import mindustry.type.Item;
import mindustry.ui.Bar;
import mindustry.ui.MultiReqImage;
import mindustry.ui.ReqImage;
import mindustry.world.consumers.ConsumeItemFilter;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.Stats;

import static mindustry.Vars.content;

public class AmmoItemTurret extends DSTurret {
    public Seq<AmmoItem> ammoTypes = new Seq<>();

    public AmmoItemTurret(String name) {
        super(name);
        hasItems = true;
    }

    public void ammo(AmmoItem... types){
        ammoTypes.set(types);
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.remove(Stat.itemCapacity);
        stats.add(Stat.ammo, DSStatValues.ammo(getAmmoArray(), name));
        stats.add(Stat.ammoCapacity, (float) maxAmmo / Math.max(ammoPerShot, 1), StatUnit.shots);
    }

    private ObjectMap<Item, BulletType> getAmmoArray(){
        ObjectMap<Item, BulletType> out = new ObjectMap<>();
        for(AmmoItem i : ammoTypes){
            out.put(i, i.bullet);
        }
        return out;
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("ammo", (AmmoTurretBuild entity) ->
                new Bar(
                        "stat.ammo",
                        Pal.ammo,
                        () -> (float)entity.totalAmmo / maxAmmo
                )
        );
    }

    @Override
    public void init(){
        consume(new ConsumeItemFilter(i -> (i instanceof AmmoItem && ammoTypes.contains((AmmoItem) i))){
            @Override
            public void build(Building build, Table table){
                MultiReqImage image = new MultiReqImage();
                content.items().each(i -> filter.get(i) && i.unlockedNow(),
                        item -> image.add(new ReqImage(new Image(item.uiIcon),
                                () -> build instanceof AmmoTurretBuild at && !at.ammo.isEmpty() && ((AmmoItemEntry)at.ammo.peek()).item == item)));

                table.add(image).size(8 * 4);
            }

            @Override
            public float efficiency(Building build){
                //valid when it can shoot
                return build instanceof AmmoTurretBuild at && at.ammo.size > 0 && (at.ammo.peek().amount >= ammoPerShot || at.cheating()) ? 1f : 0f;
            }

            @Override
            public void display(Stats stats){
                //don't display
            }
        });

        if(targetGround){
            ammoTypes.each(amI -> placeOverlapRange = Math.max(placeOverlapRange, range + amI.bullet.rangeChange + placeOverlapMargin));
        }

        super.init();
    }

    public class AmmoTurretBuild extends DSTurretBuild{

            @Override
            public void onProximityAdded(){
                super.onProximityAdded();

                //add first ammo item to cheaty blocks so they can shoot properly
                if(!hasAmmo() && cheating() && ammoTypes.size > 0){
                    handleItem(this, ammoTypes.get(0));
                }
            }

            @Override
            public Object senseObject(LAccess sensor){
                return switch(sensor){
                    case currentAmmoType -> ammo.size > 0 ? ((AmmoItemEntry)ammo.peek()).item : null;
                    default -> super.senseObject(sensor);
                };
            }

        @Override
        public UnlockableContent getAmmoContent(){
            return ammo.size > 0 ? ((AmmoItemEntry)ammo.peek()).item : null;
        }

        @Override
        public float getAmmoFraction(){
            return (float)totalAmmo / maxAmmo;
        }

        @Override
        public int acceptStack(Item item, int amount, Teamc source){
            BulletType type;
            if(item instanceof AmmoItem j){
                type = j.bullet;
            } else return 0;

            if(type == null) return 0;

            return Math.min((int)((maxAmmo - totalAmmo) / j.bulletCapacity), amount);
        }

        @Override
        public void handleStack(Item item, int amount, Teamc source){
            for(int i = 0; i < amount; i++){
                handleItem(null, item);
            }
        }

        //currently can't remove items from turrets.
        @Override
        public int removeStack(Item item, int amount){
            return 0;
        }

        @Override
        public void handleItem(Building source, Item item){
            //TODO instead of all this "entry" crap, turrets could just accept only one type of ammo at a time - simpler for both users and the code

            if(item.flammability >= 1){
                Events.fire(EventType.Trigger.flameAmmo);
            }

            if(totalAmmo == 0){
                Events.fire(EventType.Trigger.resupplyTurret);
            }

            BulletType type;
            if(item instanceof AmmoItem am) {
                type = am.bullet;
                if(type == null) return;
            }else return;
            totalAmmo += am.bulletCapacity;

            //find ammo entry by type
            for(int i = 0; i < ammo.size; i++){
                AmmoItemEntry entry = (AmmoItemEntry) ammo.get(i);

                //if found, put it to the right
                if(entry.item == item){
                    entry.amount += entry.item.bulletCapacity;
                    ammo.swap(i, ammo.size - 1);
                    return;
                }
            }

            //must not be found
            ammo.add(new AmmoItemEntry(am, am.bulletCapacity));
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return item instanceof AmmoItem it && ammoTypes.contains(it) && totalAmmo + it.bulletCapacity <= maxAmmo;
        }

        @Override
        public byte version(){
            return 2;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.b(ammo.size);
            for(AmmoEntry entry : ammo){
                AmmoItemEntry i = (AmmoItemEntry) entry;
                write.s(i.item.id);
                write.s(i.amount);
            }
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            ammo.clear();
            totalAmmo = 0;
            int amount = read.ub();
            for(int i = 0; i < amount; i++){
                Item item = Vars.content.item(revision < 2 ? read.ub() : read.s());
                int itemAmount = Math.min(read.s(), maxAmmo);

                //only add ammo if this is a valid ammo type
                if(item != null && ammoTypes.contains((AmmoItem) item)){
                    totalAmmo += itemAmount;
                    ammo.add(new AmmoItemEntry((AmmoItem) item, itemAmount));
                }
            }
        }
    }

    public class AmmoItemEntry extends AmmoEntry{
        public AmmoItem item;

        public AmmoItemEntry(AmmoItem item, int amount){
            this.item = item;
            this.amount = amount;
        }

        @Override
        public BulletType type() {
            return item.bullet;
        }

        @Override
        public String toString(){
            return "ItemEntry{" +
                    "item=" + item +
                    ", amount=" + amount +
                    '}';
        }
    }
}
