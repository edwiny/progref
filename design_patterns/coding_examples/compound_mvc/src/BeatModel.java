import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;

public class BeatModel implements BeatModelInterface, Runnable {
    List<BeatObserver> beatObservers = new ArrayList<BeatObserver>();
    List<BPMObserver> bpmObservers = new ArrayList<BPMObserver>();

    int bpm = 90;
    Thread thread;
    boolean stop = false;
    Clip clip;

    public void initialise() {
        try {
            File resource = new File("clap.wav");
            clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
            clip.open(AudioSystem.getAudioInputStream(resource));
        } catch(Exception ex) { }
    }

    public void on() {
        bpm = 90;
        notifyBPMObservers();
        thread = new Thread(this);
        stop = false;
        thread.start();
    }

    @Override
    public void run() {
        while(!stop) {
            //playBeat()
            //notifyBeatObservers()
            try {
                Thread.sleep(60000/getBPM());
            } catch (Exception e) { }
        }

        
    }

    @Override
    public void off() {
        //stopBeat()
        stop = true;
    }

    @Override
    public void setBPM(int bpm) {
        this.bpm = bpm;
        notifyBPMObservers();
    }

    @Override
    public int getBPM() {
        return this.bpm;
    }

    @Override
    public void registerObserver(BeatObserver o) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeObserver(BeatObserver o) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void registerObserver(BPMObserver o) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeObserver(BPMObserver o) {
        // TODO Auto-generated method stub
        
    }

    public void notifyBPMObservers() {
        for(BPMObserver observer: bpmObservers) {
            observer.changeBPM(bpm);
        }
    }



    
}
