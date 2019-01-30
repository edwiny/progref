package collections;


import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class FindDups {
    public static void main(String[] args) {
        Set<String> distinctWords = Arrays.asList(args).stream()
                .collect(Collectors.toCollection(LinkedHashSet::new));

        System.out.println(distinctWords.size() + " distinct words: " + distinctWords);

    }
}
