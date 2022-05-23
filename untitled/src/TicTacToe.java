import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    public static final int GRID_SIZE = 3;
    public static int winner;
    public static int result =0;
    public static int[][] tempBoard;

    public static void main(String[] args) {

        int[][] board = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };

        while (true) {
            userTurn(board);
            if (isGameFinished(board)) {
                break;
            }
            aiTurn(board);
            if (isGameFinished(board)) {
                break;
            }
            printBoard(board);
        }
    }

    private static void printBoard(int[][] board) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (i != 0) {
                System.out.println("");
                System.out.println("---");
            }
            for (int j = 0; j < GRID_SIZE; j++) {
                if (board[i][j] == 1) {
                    System.out.print("X");
                } else if (board[i][j] == 2) {
                    System.out.print("O");
                } else {
                    System.out.print(" ");
                }
            }
        }
    }

    private static boolean isSpotTaken(int[][] board, int spot) {
        int spotNumber;
        switch (spot) {
            case 1 -> spotNumber = board[0][0];
            case 2 -> spotNumber = board[0][1];
            case 3 -> spotNumber = board[0][2];
            case 4 -> spotNumber = board[1][0];
            case 5 -> spotNumber = board[1][1];
            case 6 -> spotNumber = board[1][2];
            case 7 -> spotNumber = board[2][0];
            case 8 -> spotNumber = board[2][1];
            case 9 -> spotNumber = board[2][2];
            default -> {
                System.out.println("not a valid spot");
                spotNumber = 3;
            }
        }
        return spotNumber == 0;
    }

    private static void aiTurn(int[][] board) {
        Random random = new Random();


        int aiMove = bestMove(board);
        String aiInput = Integer.toString(aiMove);
        if (isSpotTaken(board, aiMove)) {
            Move(board, aiInput, 2);
            System.out.println("ai made the move: " + aiInput);
        } else {
            aiTurn(board);
        }
    }

    private static void userTurn(int[][] board) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("");
        System.out.print("Next Move: ");
        String userInput = scanner.next();
        int userNumber = Integer.parseInt(userInput);

        if (isSpotTaken(board, userNumber)) {
            Move(board, userInput, 1);
            System.out.println("user made the move: " + userNumber);
        } else {
            System.out.println("Spot already taken");
            userTurn(board);
        }
    }

    private static void Move(int[][] board, String userInput, int player) {
        int number = Integer.parseInt(userInput);
        switch (number) {
            case 1 -> board[0][0] = player;
            case 2 -> board[0][1] = player;
            case 3 -> board[0][2] = player;
            case 4 -> board[1][0] = player;
            case 5 -> board[1][1] = player;
            case 6 -> board[1][2] = player;
            case 7 -> board[2][0] = player;
            case 8 -> board[2][1] = player;
            case 9 -> board[2][2] = player;
            default -> System.out.println("not a valid spot");
        }
    }


    private static boolean rowIsComplete(int[][] board, int player, int row) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[row][i] != player) {
                return false;
            }
        }
        return true;
    }

    private static boolean columnIsComplete(int[][] board, int player, int column) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[i][column] != player) {
                return false;
            }
        }
        return true;
    }

    private static boolean diagnalIsComplete(int[][] board, int player) {
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        } else if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isGameWon(int[][] board, int player) {

        for (int i = 0; i < GRID_SIZE; i++) {
            if (rowIsComplete(board, player, i)) {
                return true;
            } else if (columnIsComplete(board, player, i)) {
                return true;
            } else if (diagnalIsComplete(board, player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isBoardFull(int[][] board) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isGameFinished(int[][] board) {
        if (isBoardFull(board)) {
            printBoard(board);
            System.out.println("");
            System.out.print("game ended in a tie");
            return true;
        }
        if (isGameWon(board, 1)) {
            printBoard(board);
            System.out.println("");
            System.out.print("User Won!!");
            return true;
        }
        if (isGameWon(board, 2)) {
            printBoard(board);
            System.out.println("");
            System.out.print("Ai Won!!");
            return true;
        }
        return false;
    }

    private static boolean AIisGameFinished(int[][] board) {
        if (isBoardFull(board)) {
            winner = 0;
            return true;
        }
        if (isGameWon(board, 1)) {
            winner = 1;
            return true;
        }
        if (isGameWon(board, 2)) {
            winner = 2;
            return true;
        }
        return false;
    }

    private static int bestMove(int[][] board) {
        int[][] finalBoard = board;
        double bestScore = Double.NEGATIVE_INFINITY;
        int[] move = new int[0];


        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (isSpotTaken(finalBoard, moveToNumber(new int[]{i, j}))) {
                    finalBoard[i][j] = 2;
                    result = 0;
                    double score = minimax(finalBoard, false);
                    System.out.println(score);
                    finalBoard[i][j] = 0;
                    if (score > bestScore) {
                        bestScore = score;
                        move = new int[]{i, j};
                    }
                }
            }
        }
        return moveToNumber(move);
    }

    private static double minimax(int[][] board, boolean aiTurn) {
        tempBoard = board;
            if (aiTurn) {
                if (AIisGameFinished(tempBoard)) {
                    if (winner == 1) {
                        result -= 1;
                    } else if (winner == 2) {
                        return Double.POSITIVE_INFINITY;
                    } else {
                        minimax(tempBoard, false);
                    }
                } else {
                    for (int i = 0; i < GRID_SIZE; i++) {
                        for (int j = 0; j < GRID_SIZE; j++) {
                            if (isSpotTaken(tempBoard, moveToNumber(new int[]{i, j}))) {
                                tempBoard[i][j] = 2;
                                minimax(tempBoard, false);
                                tempBoard[i][j] = 0;
                            }
                        }
                    }
                }
            } else {
                if (AIisGameFinished(tempBoard)) {
                    if (winner == 1) {
                        result -= 1;
                    } else if (winner == 2) {
                        return Double.POSITIVE_INFINITY;
                    }
                } else {
                    for (int i = 0; i < GRID_SIZE; i++) {
                        for (int j = 0; j < GRID_SIZE; j++) {
                            if (isSpotTaken(tempBoard, moveToNumber(new int[]{i, j}))) {
                                tempBoard[i][j] = 1;
                                minimax(tempBoard, true);
                                tempBoard[i][j] = 0;
                            }
                        }
                    }
                }
        }
        return result;
    }
    private static int moveToNumber(int[] move) {
        return move[0] * 3 + (move[1] + 1);
    }
}