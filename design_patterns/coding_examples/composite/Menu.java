import java.util.ArrayList;
import java.util.List;

//this is the composite object that can contain other menus or menu items (leaf nodes)

public class Menu extends MenuComponent {
    List<MenuComponent> components = new ArrayList<MenuComponent>();
    String name;
    String description;
    public Menu(String name, String description) {
        this.name = name;
        this.description = description;
    }
    @Override
    public void add(MenuComponent menuComponent) {
        components.add(menuComponent);
    }
    @Override
    public MenuComponent getChild(int i) {
        return components.get(i);
    }
    @Override
    public void remove(MenuComponent menuComponent) {
        components.remove(menuComponent);
    }

    public void print() {
        System.out.println("Menu: " + name + ": " + description);
        for(MenuComponent component: components) {
            component.print();
        }
    }

    



    
    
    
}
