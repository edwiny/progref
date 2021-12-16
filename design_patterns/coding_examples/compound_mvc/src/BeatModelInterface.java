
public interface BeatModelInterface {
    //user interaction
    void initialise();
    void on();
    void off();
    void setBPM(int bpm);
    int getBPM();

    //observer management
    void registerObserver(BeatObserver o);
    void removeObserver(BeatObserver o);
    void registerObserver(BPMObserver o);
    void removeObserver(BPMObserver o);

}
