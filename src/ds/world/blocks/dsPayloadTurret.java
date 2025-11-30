package ds.world.blocks;

import mindustry.world.blocks.defense.turrets.PayloadAmmoTurret;

public class dsPayloadTurret extends PayloadAmmoTurret {
    public dsPayloadTurret(String name) {
        super(name);
        consumeAmmoOnce = false;
    }
}
