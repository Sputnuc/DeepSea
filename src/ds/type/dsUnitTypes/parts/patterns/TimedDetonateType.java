package ds.type.dsUnitTypes.parts.patterns;

public class TimedDetonateType extends DetonateType{
    public float time;

    public TimedDetonateType(){
        this.time = 120;
    }

    public TimedDetonateType(float time){
        this.time = time;
    }

    @Override
    public boolean detonate(float dist, float time, float alt) {
        return time > this.time;
    }
}
