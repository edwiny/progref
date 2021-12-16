import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DJView implements ActionListener, BeatObserver, BPMObserver {

    BeatModelInterface model;
    ControllerInterface controller;
    JFrame viewFrame;
    JPanel viewPanel;
    BeatBar beatBar;
    JLabel bpmOutLabel;

    public DJView(ControllerInterface controller, BeatModelInterface model) {
        this.controller = controller;
        this.model = model;
        model.registerObserver((BPMObserver)this);
        model.registerObserver((BeatObserver)this);
    }

    public void createView() {
        //create all the Swing components here
    }

    //bpm observer update method
    public void changeBPM(int bpm) {
        bpmOutLabel.setText("BPM: " + 
    }
    //same for beat obsrver
    //...
    
}
