package ds.type;

import arc.graphics.Color;
import arc.struct.ObjectMap;
import ds.world.meta.DSStatValues;
import mindustry.content.Bullets;
import mindustry.entities.bullet.BulletType;
import mindustry.type.Item;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

//Item that stores the internal BulletType
public class AmmoItem extends Item {
    public BulletType bullet;

    public int bulletCapacity = 1;

    public AmmoItem(String name, Color color, BulletType bullet) {
        super(name, color);
        this.bullet = bullet;
    }

    public AmmoItem(String name, Color color, BulletType bullet, int bulletCapacity){
        this(name, color, bullet);
        this.bulletCapacity = bulletCapacity;
    }

    public AmmoItem(String name, BulletType bullet){
        super(name, Color.white);
        this.bullet = bullet;
    }

    public AmmoItem(String name, BulletType bullet, int bulletCapacity){
        this(name, bullet);
        this.bulletCapacity = bulletCapacity;
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.ammo, DSStatValues.ammo(ObjectMap.of(this, bullet)));
        stats.add(Stat.ammoCapacity, bulletCapacity, StatUnit.shots);
    }
}
