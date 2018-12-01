public class TestCards {

    public static void main(String[] args) {

        Card c1 = new Card(Card.CLUBS, Card.ACE);

        Deck d1 = new Deck();
        System.out.println(c1);
        d1.toString();
    }
}
