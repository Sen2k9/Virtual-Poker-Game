
import java.util.Scanner;

public class pokerGame {

	public static int kind_2 = 0; /* variable for pair kind */
	public static int kind_3 = 0;/* variable for 3 of a kind */
	public static int kind_4 = 0;/* variable for 4 of a kind */
	public static boolean isStraightLow = false; /* variable for checking rank of hand(Straight) with Ace low */
	public static boolean isStraightHigh = false;/* variable for checking rank of hand(Straight) with Ace height */
	public static boolean isFlush = false; /* variable for checking rank of hand Flush */
	public static int[] frequency = new int[18]; /* array variable to store frequency of all rank of hands */
	public static long n = 10000000; /* variable for generating random hands */

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		String suit[] = { "H", "D", "C", "S" };
		String rank[] = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
		int card[][] = new int[4][13]; /* represents deck of cards */
		int hand[][] = new int[5][2]; /* represents hand */
		String handclassifier[] = { "Seven high", "Eight high", "Nine high", "Ten high", "Jack high", "Queen high",
				"King high", "Ace high", "One Pair", "Two pair", "3 of a kind", "Straight", "Flush   ", "Full house",
				"4 of a kind", "Straight flush", "Royal Flush" }; /* rank of hands */

		int current_hand[][] = new int[5][2]; // represents current hand
		int row, column; /* loop counters */

		int return_hand = 0;/* variable to store value returned by classifyHand method */

		for (long i = 0; i < n; i++) {
			/* for every iteration the rank frequency need to be reassigned */
			kind_2 = 0;
			kind_3 = 0;
			kind_4 = 0;
			isStraightLow = false;
			isStraightHigh = false;
			isFlush = false;

			for (row = 0; row <= 3; row++) {
				for (column = 0; column <= 12; column++) {
					card[row][column] = 0; /* initialize all cards to 0 */

				}
			} /* end for */

			suffle(card); /* Random suffling of cards */

			current_hand = drawHand(card, hand, suit,
					rank); /* Drawing 5 cards randomly and returning to current_hand */

			return_hand = classifyHand(current_hand, suit, rank); /* returning rank of hand value as integer */

			frequency[return_hand]++; /* Storing each rank of hands frequency */

			if (i < 50) { /* display hand, rank of hand, and hand number */
				System.out.printf("Hand %-8d: ", (i + 1));
				display(current_hand, suit, rank);
			} else { /*
						 * display hand, rank of hand, and hand number which frequency is less or equal
						 * 10
						 */
				displayAbove50(current_hand, suit, rank, i);
			}

		} /* end for */
		/* calculating total hign card frequency */
		double highcard = (int) (((frequency[1] / 10000000.0) * 100) * 100000) / 100000.0
				+ (int) (((frequency[2] / 10000000.0) * 100) * 100000) / 100000.0
				+ ((int) (((frequency[3] / 10000000.0) * 100) * 100000) / 100000.0)
				+ (int) (((frequency[4] / 10000000.0) * 100) * 100000) / 100000.0
				+ (int) (((frequency[5] / 10000000.0) * 100) * 100000) / 100000.0
				+ (int) (((frequency[6] / 10000000.0) * 100) * 100000) / 100000.0
				+ (int) (((frequency[7] / 10000000.0) * 100) * 100000) / 100000.0
				+ (int) (((frequency[8] / 10000000.0) * 100) * 100000) / 100000.0;
		System.out.println();

		for (int i = 0; i < handclassifier.length; i++) {/* classifying frequency percentage of each rank of hands */

			System.out.println(handclassifier[i] + "\t" + ": "
					+ (int) (((frequency[i + 1] / 10000000.0) * 100) * 100000) / 100000.0 + "%");
			if (i + 1 == 8) {
				System.out.println();
				System.out.println("High Card " + "\t" + ": " + (int) (highcard * 100000) / 100000.0 + "%");
			}
		} /* end for */

	}

	private static void display_hand(int[][] current_hand, String[] suit, String[] rank, long i) {
		System.out.printf("Hand %-8d: ", (i + 1));
		for (int r = 0; r < 5; r++) {
			System.out.print(
					rank[current_hand[r][1]] + suit[current_hand[r][0]] + "\t"); /* displaying cards in one hand */

		} /* end for */
		System.out.print(":");

	}

	private static void displayAbove50(int[][] current_hand, String[] suit, String[] rank, long i) {

		int r_pair;
		int max = 0;
		boolean ace_high = false;
		for (r_pair = 0; r_pair < 5; r_pair++) {
			if (current_hand[r_pair][1] == 0)
				ace_high = true;
			if (current_hand[r_pair][1] > max)
				max = current_hand[r_pair][1];
		} /* end for */
		/* rank of hand calculation */
		if (isStraightHigh == true && isFlush == true) {
			if (frequency[17] <= 10) {

				display_hand(current_hand, suit, rank, i);
				System.out.print(" Royalflush \n");
			}

		} else if (isStraightLow == true && isFlush == true) {
			if (frequency[16] <= 10) {

				display_hand(current_hand, suit, rank, i);
				System.out.print(" Straightflush \n");
			}

		} else if (kind_4 == 1) {
			if (frequency[15] <= 10) {

				display_hand(current_hand, suit, rank, i);
				System.out.print(" 4 of a kind \n");
			}

		} else if (kind_3 == 1 && kind_2 == 1) {
			if (frequency[14] <= 10) {
				display_hand(current_hand, suit, rank, i);
				System.out.print(" Full house\n");
			}

		} else if (isFlush == true) {
			if (frequency[13] <= 10) {
				display_hand(current_hand, suit, rank, i);
				System.out.print(" Flush  \n");
			}

		}

		else if (isStraightLow == true) {
			if (frequency[12] <= 10) {
				display_hand(current_hand, suit, rank, i);

				System.out.print(" Straight \n");
			}

		}

		else if (kind_3 == 1) {
			if (frequency[11] <= 10) {
				display_hand(current_hand, suit, rank, i);

				System.out.print(" 3 of kind \n");
			}

		} else if (kind_2 == 2) {
			if (frequency[10] <= 10) {
				display_hand(current_hand, suit, rank, i);

				System.out.print(" Two pair \n");
			}

		} else if (kind_2 == 1) {
			if (frequency[9] <= 10) {

				display_hand(current_hand, suit, rank, i);
				System.out.print(" One pair \n");

			}
		}

		else if (ace_high == true) {
			if (frequency[8] <= 10) {

				display_hand(current_hand, suit, rank, i);
				System.out.print(" Ace high\n");

			}

		} else {
			if (frequency[max - 5] <= 10) {
				display_hand(current_hand, suit, rank, i);
				if (ace_high == true) {
					System.out.print(" Ace high\n");

				} else {
					System.out.printf(" %s high\n", rank[max]);

				}

			} else
				System.out.print("");

		}

	}

	private static void display(int[][] current_hand, String[] suit, String[] rank) {
		for (int r = 0; r < 5; r++) {
			System.out.print(rank[current_hand[r][1]] + suit[current_hand[r][0]] + "\t");

		} /* end for */
		System.out.print(":");
		int r;
		int max = 0;
		boolean isAceHigh = false;
		for (r = 0; r < 5; r++) {
			if (current_hand[r][1] == 0)
				isAceHigh = true;
			if (current_hand[r][1] > max)
				max = current_hand[r][1];
		} /* end for */
		/* rank of hand calculation */
		if (isStraightHigh == true && isFlush == true) {
			System.out.print(" Royalflush \n");

		} else if (isStraightLow == true && isFlush == true) {
			System.out.print(" Straightflush \n");

		} else if (kind_4 == 1) {
			System.out.print(" 4 of a kind \n");

		} else if (kind_3 == 1 && kind_2 == 1) {
			System.out.print(" Full house\n");

		} else if (isFlush == true) {
			System.out.print(" Flush  \n");

		}

		else if (isStraightLow == true) {
			System.out.print(" Straight \n");

		}

		else if (kind_3 == 1) {
			System.out.print(" 3 of kind \n");

		} else if (kind_2 == 2) {
			System.out.print(" Two pair \n");

		} else if (kind_2 == 1) {
			System.out.print(" One pair \n");

		}

		else if (isAceHigh == true) {
			System.out.print(" Ace high\n");

		} else {
			System.out.printf(" %s high\n", rank[max]);

		}

	}

	private static int classifyHand(int[][] current_hand, String[] suit, String[] rank) {

		/* counter that records how many cards of each rank are in the hand */
		int counter[] = new int[13];
		int r; /* loop counters */
		/* record how many cards of each rank are in the hand */
		for (r = 0; r < 5; r++) {
			counter[current_hand[r][1]]++;
		} /* end for */
		for (int s = 0; s <= 8; s++) {
			if (counter[s] == 1 && counter[s + 1] == 1 && counter[s + 2] == 1 && counter[s + 3] == 1
					&& counter[s + 4] == 1) {

				isStraightLow = true;
			}

		} /* end for */
		if (counter[9] == 1 && counter[10] == 1 && counter[11] == 1 && counter[12] == 1 && counter[0] == 1) { // ace

			// high

			isStraightHigh = true;

		}
		/* counter that records how many cards of each rank are in the hand */
		int counter_checking_flush[] = new int[13];
		int i, j; /* loop counters */
		/* record how many cards of each suit are in the hand */
		for (i = 0; i < 5; i++) {
			counter_checking_flush[current_hand[i][0]]++;
		} /* end for */
		/* print result if there is a five of a same suit */
		for (j = 0; j < 5; j++) {
			if (counter_checking_flush[j] == 5) {

				isFlush = true;
			}
		} /* end for */

		/* counter that records how many cards of each rank are in the hand */
		int counter_checking_pair[] = new int[13];

		int k, l; /* loop counters */
		/* record how many cards of each rank are in the hand */
		for (k = 0; k < 5; k++) {
			counter_checking_pair[current_hand[k][1]]++;
		} /* end for */
		int max = 0;
		boolean isAceHigh = false;
		for (k = 0; k < 5; k++) {
			if (current_hand[k][1] == 0)
				isAceHigh = true;
			if (current_hand[k][1] > max)
				max = current_hand[k][1];
		} /* end for */

		for (l = 0; l < 13; l++) {
			if (counter_checking_pair[l] == 2) { /* for a pair */

				kind_2++;
			}
			if (counter_checking_pair[l] == 3) { /* for 3 of a kind */

				kind_3++;
			}
			if (counter_checking_pair[l] == 4) { /* for 4 of a kind */

				kind_4++;
			}

		} /* end for */

		if (isStraightHigh == true && isFlush == true) {

			return 17;
		} else if (isStraightLow == true && isFlush == true) {

			return 16;
		} else if (kind_4 == 1) {

			return 15;
		} else if (kind_3 == 1 && kind_2 == 1) {

			return 14;
		} else if (isFlush == true) {

			return 13;
		}

		else if (isStraightLow == true) {

			return 12;
		}

		else if (kind_3 == 1) {

			return 11;
		} else if (kind_2 == 2) {

			return 10;
		} else if (kind_2 == 1) {

			return 9;
		}

		else if (isAceHigh == true) {

			return 8;
		} else {

			return (max - 5);
		}

	}

	private static int[][] drawHand(int[][] card, int[][] hand, String[] suit, String[] rank) {
		int r = 0; /* counter for position in the hand */
		int numberOfCards, row, column; /* loop counters */

		/* loop to distrubute the cards */
		for (numberOfCards = 1; numberOfCards < 6; numberOfCards++) {
			for (row = 0; row <= 3; row++) {
				for (column = 0; column <= 12; column++) {
					if (card[row][column] == numberOfCards) {
						hand[r][0] = row;
						hand[r][1] = column;

						r++;
					} /* end if */

				} /* end for */
			} /* end for */

		} /* end for */

		return hand;

	}/* end function */

	private static void suffle(int[][] card) {
		int row; /* represents suit value of card */
		int column; /* represents face value of card */
		int numberOfCards; /* loop counter */
		int[] draw = new int[2];
		/* for each of the 52 cards, choose a slot of the deck randomly */
		for (numberOfCards = 1; numberOfCards <= 52; numberOfCards++) {
			/* choose a new random location until unoccupied slot is found */
			draw = drawcard();

			row = draw[0];
			column = draw[1];
			while (card[row][column] != 0) /* This loop ensuring that no card appears twice in the same hand */

			{
				draw = drawcard();

				row = draw[0];
				column = draw[1];

			}
			/* place card number in chosen slot of deck */
			card[row][column] = numberOfCards;
		} /* end for */
	}/* end function shuffle */

	private static int[] drawcard() {
		int[] result = new int[2];
		int row; /* represents suit value of card */
		int column; /* represents rank value of card */
		row = (int) (Math.random() * 4); /* randomly select the row 0 to 3 */
		column = (int) (1 + (Math.random() * 13)); /* randomly select the column from 1 to 13 */
		result[0] = row;
		result[1] = column - 1;
		return result;
	}

}
