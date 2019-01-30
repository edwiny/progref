import java.util.*;


public class IteratorDemo {
    public static void main(String[] args) {

        System.out.println("hello world!");
        List list = new ArrayList();

        list.add(25);
        list.add("John");
        list.add(true);

        Iterator iterator = list.iterator();
        while ( iterator.hasNext() ) {
            Object next = iterator.next();
            System.out.println(next);



        }





    }



}
