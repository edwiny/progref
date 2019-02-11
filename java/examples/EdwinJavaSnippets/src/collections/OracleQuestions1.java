package collections;

import java.util.*;
import java.util.stream.Stream;

public class OracleQuestions1 {

    public static void main(String[] args) {
        System.out.println("Input args: " + Arrays.asList(args));
        Question1(args);
        FindDupsAnswer(args);
        TrimStrings(Arrays.asList(args));
        System.out.println("Input args at end: " + Arrays.asList(args));


    }

    /* Write a program that prints its arguments in random order. Do not make a copy of the argument array.
       Demonstrate how to print out the elements using both streams and the traditional enhanced for statement.
      */
    public static void Question1(String[] args) {
        List<String> l = Arrays.asList(args);
        Collections.shuffle(l);

        System.out.println("Method 1: enhanced for loop:");
        for (String s : l) {
            System.out.println(s);

        }

        System.out.println("Method 1: using Streams");
        l
                .stream()
                .forEach(e -> System.out.println(e));

    }

    /* Take the FindDupsexample and modify it to use a SortedSet instead of a Set.
        Specify a Comparator so that case is ignored when sorting and identifying set elements.

     */

    public static void FindDups(String[] args) {
        Set<String> s = new HashSet<String>();
        for (String a : args)
            s.add(a);
        System.out.println(s.size() + " distinct words: " + s);
    }

    public static void FindDupsAnswer(String[] args) {
        SortedSet<String> s = new TreeSet<String>(
                new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return(o1.compareToIgnoreCase(o2));
                    }
                }
        );


        for (String a : args)
            s.add(a);

        System.out.println(s.size() + " distinct words: " + s);
    }

    /* Write a method that takes a List<String> and applies String.trim to each element.

    Key learning point: mutating collection items while iterating.
    Enhanced for loop doesn't work as the Strings it returns are immutable.

     */
    public static void TrimStrings(List<String> l) {

        System.out.println("Trim list of Strings:");
        int i = 0;
        for(ListIterator<String> lit = l.listIterator(); lit.hasNext();) {

            lit.set(lit.next().toUpperCase());
        }
        System.out.println(l);
    }


}


