import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TESTUNSATI {

    public static void main(String[] args) {
        int size = 4; // Taille du tableau

        // Générer les variables et les clauses
        StringBuilder dimacsContent = generateDIMACS(size);

        // Écrire les clauses dans un fichier DIMACS
        writeDIMACS(dimacsContent.toString(), "UNSATI.dimacs");
    }

    private static StringBuilder generateDIMACS(int size) {
        StringBuilder dimacsContent = new StringBuilder();

        // Générer les variables
        int variableCount = size * size;
        dimacsContent.append("p cnf ").append(variableCount).append(" ").append(variableCount * 4).append("\n");

        // Générer les clauses pour chaque cellule
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                int variable = getVariableNumber(i, j, size);
                dimacsContent.append(variable).append(" 0\n");

                // Ajouter les clauses pour chaque cellule
                for (int k = 1; k <= size; k++) {
                    for (int l = 1; l <= size; l++) {
                        if (k != i || l != j) {
                            int variableK = getVariableNumber(k, l, size);
                            dimacsContent.append("-").append(variable).append(" -").append(variableK).append(" 0\n");
                        }
                    }
                }
            }
        }

        return dimacsContent;
    }

    private static int getVariableNumber(int row, int col, int size) {
        return (row - 1) * size + col;
    }

    private static void writeDIMACS(String content, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content);
            System.out.println("DIMACS file written successfully: " + filename);
        } catch (IOException e) {
            System.out.println("Error writing DIMACS file: " + e.getMessage());
        }
    }
}
