package ds;

import arc.Core;
import arc.Events;
import arc.audio.Filters;
import arc.audio.Music;
import arc.audio.Sound;
import arc.files.Fi;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Nullable;
import arc.util.Time;
import ds.content.DSMusicLoader;
import mindustry.Vars;
import mindustry.Vars.*;
import mindustry.audio.SoundControl;
import mindustry.game.EventType;
import mindustry.gen.Musics;

import static mindustry.Vars.*;

public class DSSoundControl extends SoundControl {
    public static Seq<Music> modAmbientMusic, modDarkMusic, modBossMusic = Seq.with();
    public static Seq<Music> dsAmbientM, dsDarkM, dsCalmM, dsBossM = Seq.with();
    public static Seq<Music> ambientM, darkM, bossM = Seq.with();

    protected @Nullable Music current;

    public static void loadSoundControl() {
        control.sound.stop();
        control.sound = new DSSoundControl();

        //Vannila music
        modAmbientMusic = Seq.with(Musics.game1, Musics.game3, Musics.game6, Musics.game8, Musics.game9, Musics.fine);
        modDarkMusic = Seq.with(Musics.game2, Musics.game5, Musics.game7, Musics.game4);
        modBossMusic= Seq.with(Musics.boss1, Musics.boss2);

        //Mod music
        dsAmbientM = DSMusicLoader.dsAmbientM;
        dsDarkM = DSMusicLoader.dsDarkM;
        dsBossM = DSMusicLoader.dsBossM;
        dsCalmM = DSMusicLoader.dsCalmM;

        //Both music arrays
        ambientM = new Seq<>(modAmbientMusic).addAll(dsAmbientM);
        darkM  = new Seq<>(modDarkMusic).addAll(dsDarkM);
        bossM  = new Seq<>(modBossMusic).addAll(dsBossM);
    }

    @Override
    protected void reload(){
        current = null;
        fade = 0;

        for(var sound : Core.assets.getAll(Sound.class, new Seq<>())) {
            var file = Fi.get(Core.assets.getAssetFileName(sound));
            if (file.parent().name().equals("ui")) {
                sound.setBus(uiBus);
            }
        }
        Events.fire(new EventType.MusicRegisterEvent());
    }

    @Override
    public void update(){
        boolean paused = state.isGame() && Core.scene.hasDialog();
        boolean playing = state.isGame();

        //check if current track is finished
        if(current != null && !current.isPlaying()){
            current = null;
            fade = 0f;
        }

        //fade the lowpass filter in/out, poll every 30 ticks just in case performance is an issue
        if(timer.get(1, 30f)){
            Core.audio.soundBus.fadeFilterParam(0, Filters.paramWet, paused ? 1f : 0f, 0.4f);
        }

        //play/stop ordinary effects
        if(playing != wasPlaying){
            wasPlaying = playing;

            if(playing){
                Core.audio.soundBus.play();
                setupFilters();
            }else{
                //stopping a single audio bus stops everything else, yay!
                Core.audio.soundBus.stop();
                //play music bus again, as it was stopped above
                Core.audio.musicBus.play();

                Core.audio.soundBus.play();
            }
        }

        Core.audio.setPaused(Core.audio.soundBus.id, state.isPaused());

        if(state.isMenu()){
            silenced = false;
            if(ui.planet.isShown()){
                play(ui.planet.state.planet.launchMusic);
            }else if(ui.editor.isShown()){
                play(Musics.editor);
            }else{
                play(Musics.menu);
            }
        }else if(state.rules.editor){
            silenced = false;
            play(Musics.editor);
        }else{
            //this just fades out the last track to make way for ingame music
            silence();

            if(Core.settings.getBool("alwaysmusic")){
                if(current == null){
                    playRandom();
                }
            }else if(Time.timeSinceMillis(lastPlayed) > 1000 * musicInterval / 60f){
                //chance to play it per interval
                if(Mathf.chance(musicChance)){
                    lastPlayed = Time.millis();
                    playRandom();
                }
            }
        }

        updateLoops();
    }
    protected boolean isCalmNow(){
        if(!state.rules.waveTimer || state.rules.infiniteResources) return true;
        if(state.isCampaign() & state.getSector() != null){
            return state.getSector().isCaptured();
        }
        return  false;
    }

    @Override
    public void playRandom(){
        if(!DSMusicLoader.isDeepSeaMusic()){
            if (state.boss() != null) {
                playOnce(modBossMusic.random(lastRandomPlayed));
            } else if (isDark()) {
                playOnce(modDarkMusic.random(lastRandomPlayed));
            } else {
                playOnce(modAmbientMusic.random(lastRandomPlayed));
            }
        } else {
            if(DSMusicLoader.bothMusic()){
                if (state.boss() != null) {
                    playOnce(bossM.random(lastRandomPlayed));
                } else if (isDark()) {
                    playOnce(darkM.random(lastRandomPlayed));
                } else if(!isCalmNow()){
                    playOnce(ambientM.random(lastRandomPlayed));
                } else {
                    playOnce(dsCalmM.random(lastRandomPlayed));
                }
            }else {
                if (state.boss() != null) {
                    playOnce(dsBossM.random(lastRandomPlayed));
                } else if (isDark()) {
                    playOnce(dsDarkM.random(lastRandomPlayed));
                } else if (!isCalmNow()){
                    playOnce(dsAmbientM.random(lastRandomPlayed));
                } else {
                    playOnce(dsCalmM.random(lastRandomPlayed));
                }
            }
        }
    }

}
