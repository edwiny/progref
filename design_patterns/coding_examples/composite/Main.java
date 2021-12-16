public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world");

        MenuComponent allMenus = new Menu("All Menus", "every menu");

        MenuComponent burgerJointBreakFastMenu = 
            new Menu("Burget Joint Breakfast Menu", "burgers for brekkie!");

        MenuComponent burgerJointLunchMenu = 
            new Menu("Burget Joint Lunch Menu", "burgers for lunch!");

        MenuComponent burgerJointLunchVegetarianMenu = 
            new Menu("Burget Joint Lunch Vegetarian Menu", "mushroom for lunch!");


        allMenus.add(burgerJointBreakFastMenu);
        allMenus.add(burgerJointLunchMenu);

        burgerJointBreakFastMenu.add(new MenuItem("Breakfast burger"));
        burgerJointBreakFastMenu.add(new MenuItem("Continental burger"));
        burgerJointLunchMenu.add(new MenuItem("Cheese Burger"));
        burgerJointLunchMenu.add(new MenuItem("American Burger"));
        burgerJointLunchVegetarianMenu.add(new MenuItem("Mushroom Burger"));
        burgerJointLunchVegetarianMenu.add(new MenuItem("Eggplant Burger"));
        burgerJointLunchMenu.add(burgerJointLunchVegetarianMenu);

        allMenus.print();











    }
    
}
