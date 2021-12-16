public class MenuItem extends MenuComponent {
    String name;

    public MenuItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void print() {
        System.out.println("MenuItem: " + name);
    }
 }
