import java.util.ArrayList;
import java.util.Random;

enum Suite {
    HEARTS("Corazones", "♥"),
    DIAMONDS("Diamantes", "♦"),
    CLUBS("Tréboles", "♣"),
    SPADES("Picas", "♠");

    final String suiteName;
    final String suiteSymbol;

    Suite(String suiteName, String suiteSymbol) {
        this.suiteName = suiteName;
        this.suiteSymbol = suiteSymbol;
    }

    public String getSuiteName() {
        return suiteName;
    }

    public String getSuiteSymbol() {
        return suiteSymbol;
    }
}

enum Figure {
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("J", 10),
    QUEEN("Q", 10),
    KING("K", 10),
    ACE("A", 11);  // El valor del As puede ser 1 o 11 en Blackjack

    final String figureName;
    final int figureValue;

    Figure(String figureName, int figureValue) {
        this.figureName = figureName;
        this.figureValue = figureValue;
    }

    public String getFigureName() {
        return figureName;
    }

    public int getFigureValue() {
        return figureValue;
    }
}

class Card {
    private final Suite suite;
    private final Figure figure;
    private boolean isTaken = false;

    public Card(Suite suite, Figure figure) {
        this.suite = suite;
        this.figure = figure;
    }

    public String getCardName() {
        return figure.getFigureName() + suite.getSuiteSymbol();
    }

    public int getCardValue() {
        return figure.getFigureValue();
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void takeCard() {
        isTaken = true;
    }
}

class Deck {
    private final Card[] cards = new Card[52];
    private int currentCardIndex = 0;  // Control para saber cuál carta es la siguiente en ser tomada

    public Deck() {
        int index = 0;
        for (Suite suite : Suite.values()) {
            for (Figure figure : Figure.values()) {
                cards[index] = new Card(suite, figure);
                index++;
            }
        }
        shuffle();
    }

    public void shuffle() {
        Random random = new Random();
        for (int i = 0; i < cards.length; i++) {
            int randomIndex = random.nextInt(cards.length);
            Card temp = cards[i];
            cards[i] = cards[randomIndex];
            cards[randomIndex] = temp;
        }
    }

    public Card drawCard() {
        if (currentCardIndex < cards.length) {
            return cards[currentCardIndex++];
        }
        return null; // No hay más cartas en la baraja
    }
}

class Player {
    private final String name;
    private final ArrayList<Card> hand = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public void takeCard(Card card) {
        hand.add(card);
    }

    public void showHand() {
        System.out.println(name + "'s hand:");
        for (Card card : hand) {
            System.out.println(card.getCardName());
        }
    }

    public int getHandScore() {
        int score = 0;
        int aceCount = 0;

        for (Card card : hand) {
            score += card.getCardValue();
            if (card.getCardValue() == 11) {
                aceCount++;
            }
        }

        while (score > 21 && aceCount > 0) {
            score -= 10;
            aceCount--;
        }

        return score;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
}

class Blackjack {
    private final Deck deck;
    private final Player player;
    private final Player dealer;

    public Blackjack(String playerName) {
        deck = new Deck();
        player = new Player(playerName);
        dealer = new Player("Dealer");
    }

    public void startGame() {

        player.takeCard(deck.drawCard());
        player.takeCard(deck.drawCard());
        dealer.takeCard(deck.drawCard());
        dealer.takeCard(deck.drawCard());

        player.showHand();
        System.out.println("Score: " + player.getHandScore());

        System.out.println("Dealer's visible card:");
        System.out.println(dealer.getHand().get(0).getCardName());
    }

    public void getWinner() {
        int playerScore = player.getHandScore();
        int dealerScore = dealer.getHandScore();

        System.out.println("\nFinal Scores:");
        System.out.println(player.getName() + ": " + playerScore);
        System.out.println("Dealer: " + dealerScore);

        if (playerScore > 21) {
            System.out.println("Player busts! Dealer wins.");
        } else if (dealerScore > 21) {
            System.out.println("Dealer busts! Player wins.");
        } else if (playerScore > dealerScore) {
            System.out.println("Player wins with " + playerScore + " points!");
        } else if (dealerScore > playerScore) {
            System.out.println("Dealer wins with " + dealerScore + " points!");
        } else {
            System.out.println("It's a tie!");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Blackjack game = new Blackjack("Player");
        game.startGame();

        game.getWinner();
    }
}