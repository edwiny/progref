public class GarageDoorOpenCommand implements Command {
    GarageDoor door;

    public GarageDoorOpenCommand(GarageDoor door) {
        this.door = door;
    }

    public void execute() {
        door.up();
    }

    @Override
    public void undo() {
        door.down();        
    }
}