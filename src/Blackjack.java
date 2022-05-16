import java.util.Scanner;
public class Blackjack {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        int balance = 1000;
        int wager = 0;
        boolean playAgain = true;
        System.out.println("Welcome to Blackjack!");

        // Create the playing deck
        Deck playingDeck = new Deck();
        playingDeck.createFullDeck();
        playingDeck.shuffleDeck();

        Deck burnDeck = new Deck();

        // Create hands for the player and the dealer - hands are created from methods that are made in the deck class
        Deck playerHand = new Deck();
        Deck dealerHand = new Deck();

        //Game loops
        while(playAgain) {
            boolean lost = false;
            boolean won = false;
            boolean playerCanHit = true;

            //check to see if deck is running low and reshuffle
            if(playingDeck.deckSize() < 11) {
                burnDeck.moveAllToDeck(playingDeck);
                playingDeck.shuffleDeck();
            }

            System.out.println("Your current balance is $" + balance);
            while(true) {
                System.out.println("How much would you like to wager? Please chose an increment of 5: ");
                wager = Integer.parseInt(in.nextLine());
                if(wager % 5 == 0) {
                    break;
                }
            }
            if(wager > balance || balance == 0) {
                System.out.println("Sorry, you do not have sufficient funds in your account.");
                break;
            }
            //loop until player busts or holds
            playerHand.draw(playingDeck);
            playerHand.draw(playingDeck);
            System.out.println("You are betting $" + wager + ".");
            System.out.println("\nYour Hand value: " + playerHand.getValue()+ "\nYour hand: " + playerHand.toString());
            if(playerHand.getValue() == 21) {
                System.out.println("BlackJack, you win!");
                playerCanHit = false;
                won = true;
            }
            dealerHand.draw(playingDeck);
            dealerHand.draw(playingDeck);
            System.out.println("\nDealer's hand value: " + dealerHand.getFirstCardValue()+ "\nDealer's hand: " + dealerHand.getFirstCard() + "HIDDEN");
            System.out.println("would you like to double down? 1.Yes 2.No");
            if(Integer.parseInt(in.nextLine()) == 1) {
                if(wager * 2 > balance) {
                    System.out.println("Sorry, not enough funds to double down");
                } else {
                    playerCanHit = false;
                    wager *= 2;
                    playerHand.draw(playingDeck);
                    System.out.println("\nYour Hand value: " + playerHand.getValue()+ "\nYour hand: " + playerHand.toString());
                    System.out.println("\nDealer's hand value: " + dealerHand.getValue()+ "\nDealer's hand: " + dealerHand.toString());
                }
            }
            while(true && playerCanHit) {
                System.out.println("Would you like to 1.Hit or 2.Stay?");
                int choice = Integer.parseInt(in.nextLine());
                if(choice == 1) {
                    playerHand.draw(playingDeck);
                    System.out.println("\nYour Hand value: " + playerHand.getValue()+ "\nYour hand: " + playerHand.toString());
                    System.out.println("\nDealer's hand value: " + dealerHand.getFirstCardValue()+ "\nDealer's hand: " + dealerHand.getFirstCard() + "HIDDEN");
                    if(playerHand.getValue() > 21) {
                        System.out.println("BUST! Better luck next time");
                        lost = true;
                        break;
                    } else if(playerHand.getValue() == 21) {
                        System.out.println("BlackJack, you win!");
                        won = true;
                        break;
                    }
                } else {
                    break;
                }
            }

            if(!lost && !won) {
                while(true) {
                    if(dealerHand.getValue() < 17) {
                        dealerHand.draw(playingDeck);
                        System.out.println("Dealer Hits");
                        System.out.println("\nYour Hand value: " + playerHand.getValue()+ "\nYour hand: " + playerHand.toString());
                        System.out.println("\nDealer's hand value: " + dealerHand.getValue()+ "\nDealer's hand: " + dealerHand.toString());
                        if(dealerHand.getValue() > 21) {
                            won = true;
                            System.out.println("Dealer Busted, you Won!");
                            break;
                        }
                    }  else {
                        System.out.println("\nYour Hand value: " + playerHand.getValue()+ "\nYour hand: " + playerHand.toString());
                        System.out.println("\nDealer's hand value: " + dealerHand.getValue()+ "\nDealer's hand: " + dealerHand.toString());
                        break;
                    }
                }
            }

            if(dealerHand.getValue() > playerHand.getValue() && dealerHand.getValue() <= 21) {
                System.out.println("You lost, better luck next time!");
                lost = true;
            } else if(playerHand.getValue() > dealerHand.getValue() && playerHand.getValue() <= 21) {
                System.out.println("You won!");
                won = true;
            }

            //add cards to burn deck
            playerHand.moveAllToDeck(burnDeck);
            dealerHand.moveAllToDeck(burnDeck);


            if(lost) {
                balance -= wager;
            } else if(won) {
                balance += wager*2;
            } else {
                System.out.println("Push!");
            }
            System.out.println("Your new balance is $" + balance);
            System.out.println("Would you like to play again? 1.Yes 2.No");
            if(Integer.parseInt(in.nextLine()) == 2) {
                break;
            }
        }
        in.close();
    }
}
