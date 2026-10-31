package ds.type.entities.parts;

import arc.math.Interp;
import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.entities.part.RegionPart;

public class KeyFramePart extends RegionPart {
    public Seq<KeyFrameMove> keyMoves = new Seq<>();

    public KeyFramePart(String name) {
        super(name);
    }

    public void moves(KeyFrameMove...moves){
        keyMoves.set(moves);
    }

    public static class KeyFrameMove extends PartMove{
        public float progressStart = 0f;
        public float progressEnd = 1f;

        public float moveX = 0f;
        public float moveY = 0f;
        public float moveRot = 0f;

        public Interp interp = Interp.linear;

        public KeyFrameMove() {}
    }

    @Override
    public void draw(PartParams params) {
        float currentProg = progress.get(params);
        float totalX = 0f;
        float totalY = 0f;
        float totalRot = 0f;
        float lastX = 0f;
        float lastY = 0f;
        float lastRot = 0f;
        for (int i = 0; i < keyMoves.size; i++) {
            KeyFrameMove move = keyMoves.get(i);
            if (currentProg >= move.progressStart) {
                if (currentProg <= move.progressEnd) {
                    float range = move.progressEnd - move.progressStart;
                    float localProg = range <= 0.001f ? 1f : (currentProg - move.progressStart) / range;
                    float alpha = move.interp.apply(localProg);
                    totalX = Mathf.lerp(lastX, move.moveX, alpha);
                    totalY = Mathf.lerp(lastY, move.moveY, alpha);
                    totalRot = Mathf.lerp(lastRot, move.moveRot, alpha);
                    break;
                } else {
                    lastX = move.moveX;
                    lastY = move.moveY;
                    lastRot = move.moveRot;
                    totalX = lastX;
                    totalY = lastY;
                    totalRot = lastRot;
                }
            } else {
                totalX = lastX;
                totalY = lastY;
                totalRot = lastRot;
                break;
            }
        }
        float backupX = this.moveX;
        float backupY = this.moveY;
        float backupRot = this.moveRot;
        this.moveX = totalX;
        this.moveY = totalY;
        this.moveRot = totalRot;
        super.draw(params);
        this.moveX = backupX;
        this.moveY = backupY;
        this.moveRot = backupRot;
    }
}
