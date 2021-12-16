public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        BeatModelInterface model = new BeatModel();
        ControllerInterface controller = new BeatController(model);
    }
}
