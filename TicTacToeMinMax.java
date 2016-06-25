import java.util.*;
import java.io.*;

class TicTacToeMinMax {

	static char board[][] = {{'1','2','3'}, {'4','5','6'}, {'7','8','9'}};
	static char winner;

	public static void main(String args[]) {
		Tree T = new Tree(null, board);
		int val = minimax(board, 'X', T);
		//System.out.println(val);
		System.out.println("Let's play a game of Tic-Tac-Toe\n");

		display_game_board(board);
		//System.out.println(!game_complete(board) + " " + Character.toChars(2+48)[0]);
		
		// function call for starting the game
		play('X', T);

		if(agent_won()) {
			System.out.println("\nComputer Wins!");
		}
		else if(tie()) {
			System.out.println("\nIt's a tie!");
		}
		else if(player_won()) {
			System.out.println("\nYou Win!");
		}
	}

	// play the game 
	static void play(char player, Tree T) {
		//display_game_board(board);
		//System.out.println(!game_complete(board));
		while(!game_complete(board)) {
			// agent's move
			if(player == 'X') {

				// generate list of available options
				ArrayList<Tree> options = new ArrayList();
				Iterator itr = T.children.iterator();
				while(itr.hasNext()) {
					Tree temp = (Tree)itr.next();
					if(T.value == temp.value) {
						options.add(temp);
					}
				}

				// generate a random best move from available options
				Random R = new Random();
				int ind = R.nextInt(options.size());

				// make the agent's move
				T = options.get(ind);
				board = copy_board(T.board_config);
				player = get_opposition(player);

				// display the altered config
				System.out.println("\nConfiguration after the computer agent's move:");
				display_game_board(board);

			}
			// player's move
			else {
				validate_accept_move(board, 'O');

				Iterator itr = T.children.iterator();
				while(itr.hasNext()) {
					Tree temp = (Tree)itr.next();
					if(match_config(board, temp.board_config)) {
						T = temp;
						break;
					}
				}

				// display the altered config
				System.out.println("\nConfiguration after your move:");
				display_game_board(board);
				player = get_opposition(player);
			}
		}

	}

	// checking whether the state and current configuration match
	static boolean match_config(char[][] current_config, char[][] state_config) {
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if(current_config[i][j] != state_config[i][j])
					return false;
			}
		}
		return true;
	}

	// validate and accept the players move
	static void validate_accept_move(char[][] current_board, char player) {
		int m;
		Scanner sc = new Scanner(System.in);
		boolean flag = false;
		
		do {
			// check if the user accepts a valid input else throw exception
			try {
				System.out.print("\nEnter a number on the board indicating your move:");
				m = sc.nextInt();
				char mchar = Character.toChars(m+48)[0];
				for(int i=0; i<3; i++) {
					for(int j=0; j<3; j++) {
						if(mchar == current_board[i][j]) {
							flag = true;
							current_board[i][j] = player;
							break;
						}
					}
					if(flag == true)
						break;
				}
				if(flag == false)
					throw new Exception();
			}
			catch(Exception E) {
				System.out.print("Invalid Move. Try again: ");
			}
		}while(flag == false);
	
	}

	// generate the minimax state space tree
	static int minimax(char[][] current_board, char player, Tree P) {
		// assign the values to leaf nodes
		if(game_complete(current_board)) {
			if(agent_won())
				return 1;
			else if(tie())
				return 0;
			else if(player_won())
				return -1;
		}

		// check for any possible moves and generate children
		for (int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if(current_board[i][j] != 'X' && current_board[i][j] != 'O') {
					char[][] temp = copy_board(current_board);
					temp[i][j] = player;
					Tree t = new Tree(P, temp);
					t.value = minimax(temp, get_opposition(player), t);
					P.addChildren(t);
				}
			}
		}
		//display_game_board(current_board);

		// calculate the value for the parent node and return it
		if(player == 'X') {
			int max = -99999;
			Iterator itr = P.children.iterator();
			while(itr.hasNext()) {
				Tree t = (Tree)itr.next();
				if(t.value > max) 
					max  = t.value;
			}
			//System.out.println(max);
			return max;
		} else {
			int min = 99999;
			Iterator itr = P.children.iterator();
			while(itr.hasNext()) {
				Tree t = (Tree)itr.next();
				if(t.value < min) 
					min  = t.value;
			}
			//System.out.println(min);
			return min;
		}


	}

	// make a copy of the current configuration of board
	static char[][] copy_board(char[][] current_board) {
		char[][] copy = new char[3][3];
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				copy[i][j] = current_board[i][j];
			}
		}
		return copy;
	}

	// get the opposite enemy
	static char get_opposition(char player) {
		if(player == 'X')
			return 'O';
		else
			return 'X';
	}

	// get the number of possible moves
	static int get_number_of_possible_moves(char[][] matrix) {
		int count = 0;
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if(matrix[i][j] != 'X' && matrix[i][j] != 'O')
					count += 1;
			}
		}
		return count;
	}

	// display the game board
	static void display_game_board(char[][] current_board) {
		System.out.println();
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				System.out.print(current_board[i][j] + " ");		
			}
			System.out.println();
		}
	}

	// determine if the game is complete or not
	static boolean game_complete(char[][] matrix) {
		for(int i=0; i<3; i++) {
			// Check whether a row has been completely filled by the same player
			if(matrix[i][0] == matrix[i][1] && matrix[i][0] == matrix[i][2]) {
				update_winner(matrix[i][0]);
				//System.out.println("-1");
				return true;
			}

			// Check whether a column has been completely filled by the same player
			if(matrix[0][i] == matrix[1][i] && matrix[0][i] == matrix[2][i]) {
				update_winner(matrix[0][i]);
				//System.out.println("1");
				return true;
			}		
		}

		// Check whether the major diagonal has been completely filled by the same player
		if(matrix[0][0] == matrix[1][1] && matrix[0][0] == matrix[2][2]) {
			update_winner(matrix[0][0]);
			//System.out.println("2");
			return true;
		}

		// Check whether the major diagonal has been completely filled by the same player
		if(matrix[0][2] == matrix[1][1] && matrix[0][2] == matrix[2][0]) {
			update_winner(matrix[0][2]);
			//System.out.println("3");
			return true;
		}

		// if game has been completed but its a tie
		if(get_number_of_possible_moves(matrix) == 0) {
			update_winner('T');
			//System.out.println("4");
			return true;
		}

		// if game is still incomplete
		return false;
	}

	// Set the winner, called only when the game has been completed
	static void update_winner(char V) {
		winner = V;
	}

	// has the agent won
	static boolean agent_won() {
		return winner == 'X';
	}

	// has the player won
	static boolean player_won() {
		return winner == 'O';
	}

	// if its a tie
	static boolean tie() {
		return winner == 'T';
	}
}

// class for state space tree
class Tree
{
    char board_config[][] = new char[3][3];
    int value;
    Tree parent;
    ArrayList<Tree> children = new ArrayList();
    
    Tree(Tree p, char current_board[][]) {
        for(int i=0; i<3; i++) {
        	for(int j=0; j<3; j++) {
        		board_config[i][j] = current_board[i][j];
        	}
        }
        parent = p;
    }
    
    public void addChildren(Tree c) {
        children.add(c);
    }
    
    public Tree getChild(int i) {
        return children.get(i);
    }

    public boolean noChild() {
        if(children.size() == 0) {
            return true;
        }
        else {
            return false;
        }
    }
}

/*

Output:

C:\Users\Raj\Downloads>java TicTacToeMinMax
Let's play a game of Tic-Tac-Toe


1 2 3
4 5 6
7 8 9

Configuration after the computer agent's move:

1 2 3
4 5 6
7 8 X

Enter a number on the board indicating your move:5

Configuration after your move:

1 2 3
4 O 6
7 8 X

Configuration after the computer agent's move:

1 2 X
4 O 6
7 8 X

Enter a number on the board indicating your move:6

Configuration after your move:

1 2 X
4 O O
7 8 X

Configuration after the computer agent's move:

1 2 X
X O O
7 8 X

Enter a number on the board indicating your move:2

Configuration after your move:

1 O X
X O O
7 8 X

Configuration after the computer agent's move:

1 O X
X O O
7 X X

Enter a number on the board indicating your move:1

Configuration after your move:

O O X
X O O
7 X X

Configuration after the computer agent's move:

O O X
X O O
X X X

Computer Wins!

C:\Users\Raj\Downloads>

*/