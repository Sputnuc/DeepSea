package ds.world.blocks.turret;

import mindustry.world.blocks.defense.turrets.PayloadAmmoTurret;

public class dsPayloadTurret extends PayloadAmmoTurret {
    public dsPayloadTurret(String name) {
        super(name);
        consumeAmmoOnce = false;
    }
}
