#include <iostream>
#include <random>
#include <string>
using namespace std;

int player = 0;
int dealer = 0;
string playerMessage = "Las cartas del jugador son: ";
string dealerMessage = "Las cartas del dealer son: ";
int cards[52];
bool cardUsed[52];

void createDeck() {
    int cardValue = 2;
    int cardCount = 0;
    for (int figure = 1; figure <= 4; figure++) {
        for (int card = 1; card <= 13; card++) {
            if (card >= 10 && card <= 12) {
                cardValue = 10;
            } else if (card == 13) {
                cardValue = 11;
            } else {
                cardValue = card;
            }
            cards[cardCount] = cardValue;
            cardUsed[cardCount] = false;
            cardCount++;
        }
    }
}

int drawCard() {
    random_device rd;
    mt19937 gen(rd());
    uniform_int_distribution<int> dist(0, 51);
    int cardIndex;

    do {
        cardIndex = dist(gen);
    } while (cardUsed[cardIndex]);

    cardUsed[cardIndex] = true;
    return cards[cardIndex];
}

void dealToPlayer() {
    int card = drawCard();
    player += card;
    playerMessage += to_string(card) + " ";
}

void dealToDealer() {
    int card = drawCard();
    dealer += card;
    dealerMessage += to_string(card) + " ";
}

int main() {
    createDeck();

    dealToPlayer();
    dealToPlayer();

    dealToDealer();
    dealToDealer();

    cout << playerMessage << " -> Total: " << player << endl;
    cout << dealerMessage << " -> Total: " << dealer << endl;

    if (player > 21) {
        cout << "Perdiste, te pasaste de 21." << endl;
    } else if (dealer > 21) {
        cout << "Ganaste, el dealer se pasÃ³ de 21." << endl;
    } else if (player == 21) {
        cout << "Â¡BLACKJACK! Ganaste." << endl;
    } else if (player > dealer) {
        cout << "Ganaste." << endl;
    } else if (player == dealer) {
        cout << "Empate." << endl;
    } else {
        cout << "Perdiste." << endl;
    }

    return 0;
}