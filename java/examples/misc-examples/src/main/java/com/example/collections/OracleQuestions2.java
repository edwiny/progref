package com.example.collections;

import com.example.collections.support.Album;
import com.example.collections.support.Person;
import com.example.collections.support.Track;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OracleQuestions2 {
     public static void main(String[] args) {
         Question1();
         Question2();



    }


    /*
    Write the following enhanced for statement as a pipeline with lambda expressions.

    for (Person p : roster) {
    if (p.getGender() == Person.Sex.MALE) {
        System.out.println(p.getName());
    }
}
     */

    public static void Question1() {
        List<Person> roster = new ArrayList<Person>(
                Arrays.asList(
                        new Person("Mary", Person.Sex.FEMALE),
                        new Person("John", Person.Sex.MALE),
                        new Person( "Sue", Person.Sex.FEMALE),
                        new Person("Ian", Person.Sex.MALE)
                )
        );

        roster.stream()
                .filter(p -> p.getSex() == Person.Sex.FEMALE)
                .forEach(p -> System.out.println(p));
    }

    /*

    Question 2:
    Convert the following code into a new implementation that uses lambda expressions and
    aggregate operations instead of nested for loops.

    List<Album> favs = new ArrayList<>();
        for (Album a : albums) {
            boolean hasFavorite = false;
            for (Track t : a.tracks) {
                if (t.rating >= 4) {
                    hasFavorite = true;
                    break;
                }
            }
            if (hasFavorite)
                favs.add(a);
        }
        Collections.sort(favs, new Comparator<Album>() {
                                   public int compare(Album a1, Album a2) {
                                       return a1.name.compareTo(a2.name);
                                   }});

     */

    public static void Question2() {

        List<Album> library = new ArrayList<Album>(
                Arrays.asList(
                        new Album("Back in Black",
                                    Arrays.asList(
                                            new Track("Hells Bells", 4),
                                            new Track( "Shoot to Thrill", 3),
                                            new Track("What do you do for money honey", 1)
                                    )
                            ),
                        new Album("Black Ice",
                                    Arrays.asList(
                                            new Track("Rock'n' Roll Train", 1),
                                            new Track("Skies on Fire", 1),
                                            new Track("Big Jack", 4)
                                    )
                                ),
                        new Album("T.N.T",
                                Arrays.asList(
                                        new Track("T.N.T", 1),
                                        new Track("Rocker", 1),
                                        new Track("High Voltage", 1)
                                )
                        )

                )
        );

        System.out.println("Library consists of: " + library);

        List<Album> favs = library.stream()
                .filter(a -> a.tracks.stream().mapToInt(t -> t.rating).max().getAsInt() >= 4)
                .sorted((a, b) -> a.name.compareTo(b.name))
                .collect(Collectors.toList());

        System.out.println("Fav albums are: " + favs);

        /* Oracle answer:

        List<Album> sortedFavs =
            albums.stream()
            .filter(a -> a.tracks.anyMatch(t -> (t.rating >= 4)))
            .sorted(Comparator.comparing(a -> a.name))
            .collect(Collectors.toList());
        */

    }


}
