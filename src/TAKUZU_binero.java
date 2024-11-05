import java.util.Random;
import java.util.Scanner;

public class TAKUZU_binero {
    private char[][] board;
    private int size;
    private Random random;

    // Constructeur
    public TAKUZU_binero(int size) {
        this.size = size;
        this.board = new char[size][size];
        this.random = new Random();
        initializeBoard();
    }

    // Initialisation de la grille
    private void initializeBoard() {
        // Remplir certaines cases aléatoirement
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '-';
            }
        }
        for (int i = 0; i < size * size / 2; i++) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);
            char symbol = random.nextBoolean() ? '1' : '0';
            if (placeSymbol(row, col, symbol)) {
                System.out.println("Case aléatoire remplie : " + (row + 1) + ", " + (col + 1) + " : " + symbol);
            }
        }
    }

    // Afficher la grille
    public void displayBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Placer un symbole (1 ou 0) sur la grille
    public boolean placeSymbol(int row, int col, char symbol) {
        if (row < 0 || row >= size || col < 0 || col >= size || (symbol != '1' && symbol != '0')) {
            System.out.println("Coordonnées invalides ou symbole invalide !");
            return false; // Coordonnées invalides ou symbole invalide
        }
        if (board[row][col] != '-') {
            System.out.println("Case déjà occupée !");
            return false; // Case déjà occupée
        }
        board[row][col] = symbol;
        return true;
    }

    // Vérifier si la grille est pleine
    public boolean isBoardFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    // Vérifier si une ligne, une colonne ou une diagonale est complétée
    public boolean isLineCompleted(int row, int col, char symbol) {
        // Vérifier la ligne
        boolean rowCompleted = true;
        for (int i = 0; i < size; i++) {
            if (board[row][i] != symbol) {
                rowCompleted = false;
                break;
            }
        }
        if (rowCompleted) return true;

        // Vérifier la colonne
        boolean colCompleted = true;
        for (int i = 0; i < size; i++) {
            if (board[i][col] != symbol) {
                colCompleted = false;
                break;
            }
        }
        if (colCompleted) return true;

        // Vérifier la diagonale (si applicable)
        if (row == col) {
            boolean diagCompleted = true;
            for (int i = 0; i < size; i++) {
                if (board[i][i] != symbol) {
                    diagCompleted = false;
                    break;
                }
            }
            if (diagCompleted) return true;
        }

        // Vérifier l'anti-diagonale (si applicable)
        if (row + col == size - 1) {
            boolean antiDiagCompleted = true;
            for (int i = 0; i < size; i++) {
                if (board[i][size - 1 - i] != symbol) {
                    antiDiagCompleted = false;
                    break;
                }
            }
            if (antiDiagCompleted) return true;
        }

        return false;
    }

    // Vérifier si le jeu est terminé (l'un des joueurs a gagné ou la grille est pleine)
    public boolean isGameFinished() {
        // Vérifier les lignes, colonnes et diagonales pour les symboles '1'
        for (int i = 0; i < size; i++) {
            if (isLineCompleted(i, i, '1') || isLineCompleted(i, size - 1 - i, '1')) {
                return true;
            }
            for (int j = 0; j < size; j++) {
                if (isLineCompleted(i, j, '1')) return true;
            }
        }

        // Vérifier les lignes, colonnes et diagonales pour les symboles '0'
        for (int i = 0; i < size; i++) {
            if (isLineCompleted(i, i, '0') || isLineCompleted(i, size - 1 - i, '0')) {
                return true;
            }
            for (int j = 0; j < size; j++) {
                if (isLineCompleted(j, i, '0')) return true;
            }
        }

        // Vérifier si la grille est pleine
        if (isBoardFull()) {
            return true;
        }

        return false;
    }

    // Main pour jouer au jeu
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Demander à l'utilisateur la taille de la grille
        System.out.print("Entrez la taille de la grille Takuzu : ");
        int size = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne

        // Créer une nouvelle instance du jeu
        TAKUZU_binero game = new TAKUZU_binero(size);

        // Continuer à remplir la grille tant que le jeu n'est pas terminé
        while (!game.isGameFinished()) {
            // Afficher la grille
            System.out.println("Grille Takuzu :");
            game.displayBoard();

            // Demander à l'utilisateur de remplir une case
            System.out.print("Entrez les coordonnées de la case (ligne colonne) et le symbole (1 ou 0), séparés par un espace : ");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            if (parts.length != 3) {
                System.out.println("Format invalide ! Entrez les coordonnées et le symbole séparés par un espace.");
                continue;
            }
            int row = Integer.parseInt(parts[0]) - 1;
            int col = Integer.parseInt(parts[1]) - 1;
            char symbol = parts[2].charAt(0);

            // Placer le symbole sur la grille
            if (!game.placeSymbol(row, col, symbol)) {
                System.out.println("Impossible de placer le symbole à cet emplacement !");
            }
        }

        // Afficher le résultat final
        System.out.println("Grille finale :");
        game.displayBoard();
        System.out.println("Game Over!");
        scanner.close();}
}
