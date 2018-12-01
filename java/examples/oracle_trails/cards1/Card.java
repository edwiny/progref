public class Card {


    private final int rank;
    private final int suite;


    public static final int SPADES = 1;
    public static final int CLUBS = 2;
    public static final int HEARTS = 3;
    public static final int DIAMONDS = 4;


    public static final int ACE   = 1;
    public static final int DUECE = 2;
    public static final int THREE = 3;
    public static final int FOUR  = 4;
    public static final int FIVE  = 5;
    public static final int SIX   = 6;
    public static final int SEVEN = 7;
    public static final int EIGHT = 8;
    public static final int NINE  = 9;
    public static final int TEN   = 10;
    public static final int JACK  = 11;
    public static final int QUEEN = 12;
    public static final int KING  = 13;


    public Card(int suite, int rank) {
        assert(isValidSuite(suite));
        assert(isValidRank(suite));
        this.rank = rank;
        this.suite = suite;
    }

    public int getRank() {
        return(rank);
    }

    public int getSuite() {
        return(suite);
    }

    public boolean isValidSuite(int suite) {
        if(suite < SPADES || suite > DIAMONDS) {
            return(false);
        }
        return(true);
    }

    public boolean isValidRank(int rank) {
        if(rank < ACE || rank > KING) {
            return(false);
        }
        return(true);
    }

    public String rankToString(int rank) {
        switch(rank) {
            case ACE : return("Ace"); 
            case DUECE : return("Duece"); 
            case THREE : return("Three"); 
            case FOUR : return("Four"); 
            case FIVE : return("Five"); 
            case SIX : return("Six"); 
            case SEVEN : return("Seven"); 
            case EIGHT : return("Eight"); 
            case NINE : return("Nine"); 
            case TEN : return("Ten"); 
            case JACK : return("Jack"); 
            case QUEEN : return("Queen"); 
            case KING : return("King"); 
            default: return(null);
        }
    }

    public String suiteToString(int suite) {
        switch(suite) {
            case SPADES  : return("Spades"); 
            case CLUBS  : return("Clubs"); 
            case HEARTS  : return("Hearts"); 
            case DIAMONDS  : return("Diomands"); 
            default: return(null);
        }
    }

    public String toString() {
        return(rankToString(this.rank) + " of " + suiteToString(this.suite));
    }
}
