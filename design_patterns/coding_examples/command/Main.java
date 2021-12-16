public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Command");
        SimpleRemoteControl rc = new SimpleRemoteControl();
        Light light = new Light();
        LightOnCommand lightOn = new LightOnCommand(light);

        GarageDoor door = new GarageDoor();
        GarageDoorOpenCommand doorOpen = new GarageDoorOpenCommand(door);


        rc.setCommand(lightOn);
        rc.buttonWasPressed();

        rc.setCommand(doorOpen);
        rc.buttonWasPressed();

        //alternatively, if only one method in Command interface,
        //can use lambdas, so you can skip creation of command objects
        //rc.setCommand(() -> light.on());


    }
    
    
}
