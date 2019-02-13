package collections.support;

public class Track {
    public String name;
    public int rating;

    public Track(String name, int rating) {
        this.name = name;
        this.rating = rating;
    }

    public String toString() {
        return(this.name + " rating " + rating + ";");
    }
}
