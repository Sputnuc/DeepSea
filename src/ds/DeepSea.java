package ds;

import ds.content.ContentLoader;
import mindustry.mod.*;

public class DeepSea extends Mod{
    public DeepSea(){}

    @Override
    public void loadContent(){
        ContentLoader.load();
    }
}