package collections.support;

import java.util.List;

public class Album {
    public String name;
    public List<Track> tracks;

    public Album(String name, List<Track> tracks) {
        this.name = name;
        this.tracks = tracks;
    }

    public String toString() {
        return("Album: " + name + " Tracks: " + tracks);
    }
}
