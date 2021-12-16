public class LightOnCommand implements Command {
    Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();       
    }

    //because the command is created very specfically, we
    //know how to undo it.
    @Override
    public void undo() {
        light.off();        
    }

    
}