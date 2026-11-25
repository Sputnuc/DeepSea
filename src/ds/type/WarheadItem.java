package ds.type;

import arc.graphics.Color;
import ds.type.dsUnitTypes.parts.IcbmWarhead;
import mindustry.type.Item;

public class WarheadItem extends Item implements  WarheadStorage{
    public IcbmWarhead warhead;

    public WarheadItem(String name, IcbmWarhead warhead) {
        super(name);
        this.warhead = warhead;
        this.warhead.name = name;
    }

    public WarheadItem(String name, Color color, IcbmWarhead warhead) {
        super(name, color);
        this.warhead = warhead;
        this.warhead.name = name;
    }

    @Override
    public IcbmWarhead getWarhead() {
        return warhead;
    }
}
