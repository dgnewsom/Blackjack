package blackjack;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Blackjack {
	
	//Random and input
	static Random random = new Random();
	static Scanner in = new Scanner(System.in);
	
	//suits,nums and deck list
	static final String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
	static final String[] nums = {"Ace","2","3","4","5","6","7","8","9","10","Jack","Queen","King"};
	static ArrayList<String> cards = new ArrayList<String>();
	
	// player and dealer hands
	static ArrayList<String> playerCards = new ArrayList<String>();
	static ArrayList<String> dealerCards = new ArrayList<String>();
	
	//player name and score
	static int playerTotal;	
	static int[] score;
	static String name = "";
	
	public static void main(String[] args) {
		
		//Populate card list
		populateCardList();
		
		//Deal initial cards
		playerCards.add(cards.remove(random.nextInt(cards.size())));
		dealerCards.add(cards.remove(random.nextInt(cards.size())));
		playerCards.add(cards.remove(random.nextInt(cards.size())));
		dealerCards.add(cards.remove(random.nextInt(cards.size())));
		
		//set player name
		System.out.print("Please enter player name: ");
		name = in.next();
		
		printCurrent("Dealer",dealerCards,true);
		playerTurn();
		
		if(playerTotal <= 21) {
			dealerTurn();
		}
	}
	
	public static void playerTurn() {
		
		Random random = new Random();
		String stickTwist = "x";
		printCurrent(name,playerCards,false);
		
		
		while (stickTwist.charAt(0) != 's') {
			System.out.println("Would you like to Stick or Twist?\n");
			stickTwist = in.next().toLowerCase();
			
			if(stickTwist.charAt(0) == 's') {
				System.out.printf("%s stick's on %d\n\n",name,calculateScore(playerCards));
				break;
			}
			else if (stickTwist.charAt(0) == 't')
				System.out.printf("%s twists...\n",name);
				playerCards.add(cards.remove(random.nextInt(cards.size())));
				System.out.printf("%s draws..... \n",name);
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				
				System.out.printf("\nThe %s\n",playerCards.get(playerCards.size()-1));
				drawSingleCard(playerCards.get(playerCards.size()-1));
				printCurrent(name,playerCards,false);
				if (calculateScore(playerCards) > 21) {
					System.out.printf("%s has bust. Game over!",name);
					break;
				}
			}
		in.close();	
		playerTotal = calculateScore(playerCards);
		}
	
	public static void dealerTurn() {
		Random random = new Random();
		boolean cont = true;
		printCurrent("Dealer",dealerCards,false);
		while (cont == true) {
			
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
			if (playerTotal <= 21)
				if (calculateScore(dealerCards) < playerTotal) {
					System.out.println("Dealer twists");
					dealerCards.add(cards.remove(random.nextInt(cards.size())));
					System.out.printf("Dealer draws..... \n");

							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								
								e.printStackTrace();
							}
							
					System.out.printf("\nThe %s\n",dealerCards.get(dealerCards.size()-1));
					drawSingleCard(dealerCards.get(dealerCards.size()-1));
					if (calculateScore(dealerCards) > 21) {
						printCurrent("Dealer",dealerCards,false);
						System.out.printf("Dealer busts, %s wins!",name);
						break;
					}
					else {
						printCurrent("Dealer",dealerCards,false);
					}
				}
				else if(calculateScore(dealerCards) >= playerTotal && calculateScore(dealerCards) <= 21) {
					System.out.println("Dealer sticks on " + calculateScore(dealerCards) 
									+"\nDealer Wins!");
					cont = false;			
					break;
				}
			}		
	
	}
	
	
	public static void printCurrent(String name,ArrayList<String> cards,boolean isSecret) {
		if (isSecret) {
			System.out.println("\n\n--------------------------");
			System.out.printf("%-2s is Showing:\n",name);
			drawHandImage(cards,true);
			System.out.println("--------------------------");
		}
		else {
			System.out.println("\n--------------------------");
			System.out.printf("%-2s has:\n",name);
			drawHandImage(cards,false);
			calculateScore(cards);
			if (containsAce(cards) && score[0] <= 21 && score[1] <= 21) {
				
				System.out.printf("The total is %d or %d\n",score[0],score[1]);
				System.out.println("--------------------------");
			}
			else
			System.out.printf("The total is %d\n",calculateScore(cards));
			System.out.println("--------------------------");
		}
	}
	
	public static int calculateScore(ArrayList<String> cards) {
		int[] temp = {0,0};
		int result = 0;
		for (String card : cards) {
			temp[0] += getValue(card);
			temp[1] += getValue1(card);
		}
		
			if(temp[0] < 21) {
				result = temp[0];
			}
			else {
				result = temp[1];
			}
		score = temp;
		return result;
	}
	
	public static boolean containsAce(ArrayList<String> cards) {
		for (String card : cards) {
			if (card.contains("Ace")) {
			return true;
		}
		}
		return false;
	}
	
	public static int getValue(String cardName) {
		if(cardName.contains("Jack") || 
		   cardName.contains("Queen") ||
		   cardName.contains("King") ||
		   cardName.contains("10")) 
		{
			   return 10;
		   }
		else if (cardName.contains("Ace")){
			return 11;
		}
		else {
			return Integer.parseInt(cardName.substring(0, 1));
		}
	}
	
	public static int getValue1(String cardName) {
		if(cardName.contains("Jack") || 
		   cardName.contains("Queen") ||
		   cardName.contains("King") ||
		   cardName.contains("10")) 
		{
			   return 10;
		   }
		else if (cardName.contains("Ace")){
			return 1;
		}
		else {
			return Integer.parseInt(cardName.substring(0, 1));
		}
	}

	public static void populateCardList(){

		for(int y = 0; y < suits.length; y++) {
			for(int x = 0; x < nums.length; x++) {
				String cardString = nums[x] + " of "+ suits[y];
				cards.add(cardString);
	
				
			}
		}
	}
	
	public static void drawSingleCard(String card) {
		String[] cardSplit = card.split(" ");
		String val = getValueChar(cardSplit[0]);
		char suit = getSuit(cardSplit[2]);
		
		System.out.println("+---------+");
		System.out.printf ("|%-2s       |\n",val);
		System.out.println("|         |");
		System.out.printf ("|    %c    |\n",suit);
		System.out.println("|         |");
		System.out.printf ("|       %2s|\n",val);
		System.out.println("+---------+");
	}
	
	public static void drawHandImage(ArrayList<String> cards,boolean isSecret) {
		String[] card1;
		String[] card2;
		String[] card3;
		String[] card4;
		String[] card5;
		String[] card6;
		String[] card7;
		String val1;
		String val2;
		String val3;
		String val4;
		String val5;
		String val6;
		String val7;
		char suit1;
		char suit2;
		char suit3;
		char suit4;
		char suit5;
		char suit6;
		char suit7;
		
		if (isSecret) {
			
			card1 = cards.get(0).split(" ");
			val1 = getValueChar(card1[0]);
			suit1 = getSuit(card1[2]);
			
			System.out.println("+---------+");
			System.out.printf ("|%-2s       |\n",val1);
			System.out.println("|         |");
			System.out.printf ("|    %c    |\n",suit1);
			System.out.println("|         |");
			System.out.printf ("|       %2s|\n",val1);
			System.out.println("+---------+");
			
		}
		else {
			
			switch (cards.size()) {
						
			case 2:
			
				card1 = cards.get(0).split(" ");
				val1 = getValueChar(card1[0]);
				suit1 = getSuit(card1[2]);
				card2 = cards.get(1).split(" ");
				val2 = getValueChar(card2[0]);
				suit2 = getSuit(card2[2]);
				
				System.out.println("+---------+  +---------+");
				System.out.printf ("|%-2s       |  |%-2s       |\n"
						,val1,val2);
				System.out.println("|         |  |         |");
				System.out.printf ("|    %c    |  |    %c    |\n"
						,suit1,suit2);
				System.out.println("|         |  |         |");
				System.out.printf ("|       %-2s|  |       %-2s|\n"
						,val1,val2);
				System.out.println("+---------+  +---------+");
				break;
				
			case 3:
			
				card1 = cards.get(0).split(" ");
				val1 = getValueChar(card1[0]);
				suit1 = getSuit(card1[2]);
				card2 = cards.get(1).split(" ");
				val2 = getValueChar(card2[0]);
				suit2 = getSuit(card2[2]);
				card3 = cards.get(2).split(" ");
				val3 = getValueChar(card3[0]);
				suit3 = getSuit(card3[2]);
				
				System.out.println("+---------+  +---------+  +---------+");
				System.out.printf ("|%-2s       |  |%-2s       |  |%-2s       |\n"
						,val1,val2,val3);
				System.out.println("|         |  |         |  |         |");
				System.out.printf ("|    %c    |  |    %c    |  |    %c    |\n"
						,suit1,suit2,suit3);
				System.out.println("|         |  |         |  |         |");
				System.out.printf ("|       %-2s|  |       %-2s|  |       %-2s|\n"
						,val1,val2,val3);
				System.out.println("+---------+  +---------+  +---------+");
				break;
				
			case 4:
				
				card1 = cards.get(0).split(" ");
				val1 = getValueChar(card1[0]);
				suit1 = getSuit(card1[2]);
				card2 = cards.get(1).split(" ");
				val2 = getValueChar(card2[0]);
				suit2 = getSuit(card2[2]);
				card3 = cards.get(2).split(" ");
				val3 = getValueChar(card3[0]);
				suit3 = getSuit(card3[2]);
				card4 = cards.get(3).split(" ");
				val4 = getValueChar(card4[0]);
				suit4 = getSuit(card4[2]);
				
				System.out.println("+---------+  +---------+  +---------+  +---------+");
				System.out.printf ("|%-2s       |  |%-2s       |  |%-2s       |  |%-2s       |\n"
						,val1,val2,val3,val4);
				System.out.println("|         |  |         |  |         |  |         |");
				System.out.printf ("|    %c    |  |    %c    |  |    %c    |  |    %c    |\n"
						,suit1,suit2,suit3,suit4);
				System.out.println("|         |  |         |  |         |  |         |");
				System.out.printf ("|       %-2s|  |       %-2s|  |       %-2s|  |       %-2s|\n"
						,val1,val2,val3,val4);
				System.out.println("+---------+  +---------+  +---------+  +---------+");
				break;
				
			case 5:
				
				card1 = cards.get(0).split(" ");
				val1 = getValueChar(card1[0]);
				suit1 = getSuit(card1[2]);
				card2 = cards.get(1).split(" ");
				val2 = getValueChar(card2[0]);
				suit2 = getSuit(card2[2]);
				card3 = cards.get(2).split(" ");
				val3 = getValueChar(card3[0]);
				suit3 = getSuit(card3[2]);
				card4 = cards.get(3).split(" ");
				val4 = getValueChar(card4[0]);
				suit4 = getSuit(card4[2]);
				card5 = cards.get(4).split(" ");
				val5 = getValueChar(card5[0]);
				suit5 = getSuit(card5[2]);
				
				System.out.println("+---------+  +---------+  +---------+  +---------+  +---------+");
				System.out.printf ("|%-2s       |  |%-2s       |  |%-2s       |  |%-2s       |  |%-2s       |\n"
						,val1,val2,val3,val4,val5);
				System.out.println("|         |  |         |  |         |  |         |  |         |");
				System.out.printf ("|    %c    |  |    %c    |  |    %c    |  |    %c    |  |    %c    |\n"
						,suit1,suit2,suit3,suit4,suit5);
				System.out.println("|         |  |         |  |         |  |         |  |         |");
				System.out.printf ("|       %-2s|  |       %-2s|  |       %-2s|  |       %-2s|  |       %-2s|\n"
						,val1,val2,val3,val4,val5);
				System.out.println("+---------+  +---------+  +---------+  +---------+  +---------+");
				break;	
				
			case 6:
				
				card1 = cards.get(0).split(" ");
				val1 = getValueChar(card1[0]);
				suit1 = getSuit(card1[2]);
				card2 = cards.get(1).split(" ");
				val2 = getValueChar(card2[0]);
				suit2 = getSuit(card2[2]);
				card3 = cards.get(2).split(" ");
				val3 = getValueChar(card3[0]);
				suit3 = getSuit(card3[2]);
				card4 = cards.get(3).split(" ");
				val4 = getValueChar(card4[0]);
				suit4 = getSuit(card4[2]);
				card5 = cards.get(4).split(" ");
				val5 = getValueChar(card5[0]);
				suit5 = getSuit(card5[2]);
				card6 = cards.get(5).split(" ");
				val6 = getValueChar(card6[0]);
				suit6 = getSuit(card6[2]);
				
				System.out.println("+---------+  +---------+  +---------+  +---------+  +---------+  +---------+");
				System.out.printf ("|%-2s       |  |%-2s       |  |%-2s       |  |%-2s       |  |%-2s       |  |%-2s       |\n"
						,val1,val2,val3,val4,val5,val6);
				System.out.println("|         |  |         |  |         |  |         |  |         |  |         |");
				System.out.printf ("|    %c    |  |    %c    |  |    %c    |  |    %c    |  |    %c    |  |    %c    |\n"
						,suit1,suit2,suit3,suit4,suit5,suit6);
				System.out.println("|         |  |         |  |         |  |         |  |         |  |         |");
				System.out.printf ("|       %-2s|  |       %-2s|  |       %-2s|  |       %-2s|  |       %-2s|  |       %-2s|\n"
						,val1,val2,val3,val4,val5,val6);
				System.out.println("+---------+  +---------+  +---------+  +---------+  +---------+  +---------+");
				break;
				
			case 7:
				
				card1 = cards.get(0).split(" ");
				val1 = getValueChar(card1[0]);
				suit1 = getSuit(card1[2]);
				card2 = cards.get(1).split(" ");
				val2 = getValueChar(card2[0]);
				suit2 = getSuit(card2[2]);
				card3 = cards.get(2).split(" ");
				val3 = getValueChar(card3[0]);
				suit3 = getSuit(card3[2]);
				card4 = cards.get(3).split(" ");
				val4 = getValueChar(card4[0]);
				suit4 = getSuit(card4[2]);
				card5 = cards.get(4).split(" ");
				val5 = getValueChar(card5[0]);
				suit5 = getSuit(card5[2]);
				card6 = cards.get(5).split(" ");
				val6 = getValueChar(card6[0]);
				suit6 = getSuit(card6[2]);
				card7 = cards.get(6).split(" ");
				val7 = getValueChar(card7[0]);
				suit7 = getSuit(card7[2]);
				
				System.out.println("+---------+  +---------+  +---------+  +---------+  +---------+  +---------+  +---------+");
				System.out.printf ("|%-2s       |  |%-2s       |  |%-2s       |  |%-2s       |  |%-2s       |  |%-2s       |  |%-2s       |\n"
						,val1,val2,val3,val4,val5,val6,val7);
				System.out.println("|         |  |         |  |         |  |         |  |         |  |         |  |         |");
				System.out.printf ("|    %c    |  |    %c    |  |    %c    |  |    %c    |  |    %c    |  |    %c    |  |    %c    |\n"
						,suit1,suit2,suit3,suit4,suit5,suit6,suit7);
				System.out.println("|         |  |         |  |         |  |         |  |         |  |         |  |         |");
				System.out.printf ("|       %-2s|  |       %-2s|  |       %-2s|  |       %-2s|  |       %-2s|  |       %-2s|  |       %-2s|\n"
						,val1,val2,val3,val4,val5,val6,val7);
				System.out.println("+---------+  +---------+  +---------+  +---------+  +---------+  +---------+  +---------+");
				break;
				
				default:
					for (String card : cards) {
						System.out.println(card);
					}
			}
		}
	}
	
	public static char getSuit(String suit) {
		
		char club = '\u2663';
		char heart = '\u2665';
		char diamond = '\u2666';
		char spade = '\u2660';
		char result = ' ';
			
		switch (suit.charAt(0)) {
		case 'H':
			result = heart;
			break;
		case 'C':
			result = club;
			break;
		case 'S':
			result = spade;
			break;
		case 'D':
			result = diamond;
			break;
				
		}
		return result;
	}
	
	public static String getValueChar(String cardValue) {
		String value;
		
		if(cardValue.equalsIgnoreCase("10")) {
			value = "10";
		}
		else {
			value = "" + cardValue.charAt(0);
		}
		return value;
	}

}
/*
 * Test code
 * 
 * Test Card Layouts

 ArrayList<String> test1Card = new ArrayList<String>();
 ArrayList<String> test2Card = new ArrayList<String>();
 ArrayList<String> test3Card = new ArrayList<String>();
 ArrayList<String> test4Card = new ArrayList<String>();
 ArrayList<String> test5Card = new ArrayList<String>();
 ArrayList<String> test6Card = new ArrayList<String>();
 ArrayList<String> test7Card = new ArrayList<String>();
 
 test1Card.add(cards.get(random.nextInt(cards.size())));
 
 for(int x = 0;x <2; x++) { 
	 test2Card.add(cards.get(random.nextInt(cards.size())));
 }
 for(int x = 0;x <3; x++) { 
	 test3Card.add(cards.get(random.nextInt(cards.size())));
 }
 for(int x = 0;x <4; x++) { 
	 test4Card.add(cards.get(random.nextInt(cards.size())));
 }
 for(int x = 0;x <5; x++) { 
	 test5Card.add(cards.get(random.nextInt(cards.size())));
 }
 for(int x = 0;x <6; x++) { 
	 test6Card.add(cards.get(random.nextInt(cards.size())));
 }

 for(int x = 0;x <7; x++) { 
	 test7Card.add(cards.get(random.nextInt(cards.size())));
 }
 
drawHandImage(test1Card,true);
drawHandImage(test2Card,false);
drawHandImage(test3Card,false);
drawHandImage(test4Card,false);
drawHandImage(test5Card,false);
drawHandImage(test6Card,false);
drawHandImage(test7Card,false);

//Test contains ace
 
 		ArrayList<String> test1Card = new ArrayList<String>();
				 
		test1Card.add("Ace of Spades");
		
		System.out.println(containsAce(test1Card));
  */

