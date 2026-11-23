package ds.type;

import arc.graphics.Color;
import ds.type.dsUnitTypes.parts.IcbtWarhead;
import mindustry.type.Item;

public class WarheadItem extends Item implements  WarheadStorage{
    public IcbtWarhead warhead;

    public WarheadItem(String name, IcbtWarhead warhead) {
        super(name);
        this.warhead = warhead;
        this.warhead.name = name;
    }

    public WarheadItem(String name, Color color, IcbtWarhead warhead) {
        super(name, color);
        this.warhead = warhead;
        this.warhead.name = name;
    }

    @Override
    public IcbtWarhead getWarhead() {
        return warhead;
    }
}
