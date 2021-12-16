public class BeatController implements ControllerInterface {
    BeatModelInterface model;
    DJView view;

    public BeatController(BeatModelInterface model) {
        this.model = model;
        view = new DJView(this, model);
        view.createView();
        model.initialise();
    }

    @Override
    public void start() {
        model.on();
        //update view
        //...
        
    }

    @Override
    public void stop() {
        model.off();
        //update view
        //...
        
    }

    @Override
    public void increaseBPM() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void decreaseBPM() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setBPM() {
        // TODO Auto-generated method stub
        
    }
    
}
