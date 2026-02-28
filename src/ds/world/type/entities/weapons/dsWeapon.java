package ds.world.type.entities.weapons;

import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import ds.world.meta.dsStatValues;
import mindustry.type.UnitType;
import mindustry.type.Weapon;

public class dsWeapon extends Weapon {

    @Override
    public void addStats(UnitType u, Table t){
        dsStatValues.ammo(ObjectMap.of(u, bullet)).display(t);
    }

    public dsWeapon(String name, float damage){
        super(name);
    }

    public dsWeapon(String name){
        super(name);
    }

    public dsWeapon(float damage){
        super("");
    }

    public dsWeapon(){
        super("");
    }
}
