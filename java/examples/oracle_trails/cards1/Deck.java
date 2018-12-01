public class Deck {
    protected Card[] cards;

    public Deck() {
        int cardCount = 0;
        cards = new Card[52];
        for(int suite = Card.SPADES; suite <= Card.DIAMONDS; suite++) {
            for(int rank = Card.ACE; rank <= Card.KING; rank++) {
                cards[cardCount++] = new Card(suite, rank);
            }
        }
    }

    public String toString() {
        for(int i = 0; i < 52; i++) {
            System.out.println(cards[i]);
        }
        return("bla");
    }
}
