class MyTest {

    public static void main(String[] args) {
        final int uninited_value = 0;
        System.out.println("Class name of java.lang.String is {" + java.lang.String.class + "]\n");
        if(java.lang.String.class.equals("class java.lang.String")) {
            System.out.println("I can compare it");
        }
        else {
            System.out.println("I CANNOT compare it");
        }
        System.out.println(uninited_value);

    }
}
