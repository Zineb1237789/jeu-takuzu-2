import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/////////////////////////////////////////////////////////////
//                                                         //
//           Diversité des lignes et des colonnes     :    //
//                                                         //
/////////////////////////////////////////////////////////////

public class DIMACSGenerator3 {
    private final int n;
    private final int m;
    private final int numVariables;
    private final int numClauses;
    private final String fileName;

    public DIMACSGenerator3(int n, int m, String fileName) {
        this.n = n;
        this.m = m;
        this.numVariables = n * m;
        this.numClauses = computeNumClauses();
        this.fileName = fileName;
    }

    public void generateDIMACS() {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("p cnf " + numVariables + " " + numClauses + "\n");

            // Ajouter les clauses
            for (int y = 1; y <= m; y++) {
                for (int x = 1; x < n; x++) {
                    int var1 = getVariableNumber(x, y);
                    int var2 = getVariableNumber(x + 1, y);
                    writeClause(writer, -var1, -var2);
                    writeClause(writer, var1, var2);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int computeNumClauses() {
        return n * (n - 1) * m * 2;
    }

    private void writeClause(FileWriter writer, int... literals) throws IOException {
        for (int literal : literals) {
            writer.write(literal + " ");
        }
        writer.write("0\n");
    }

    private int getVariableNumber(int x, int y) {
        return (x - 1) * m + y;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez le nombre de lignes : ");
        int n = scanner.nextInt();
        System.out.print("Entrez le nombre de colonnes : ");
        int m = scanner.nextInt();
        System.out.print("Entrez le nom du fichier de sortie : ");
        String fileName = scanner.next();

        DIMACSGenerator3 generator = new DIMACSGenerator3(n, m, fileName);
        generator.generateDIMACS();
        System.out.println("Fichier DIMACS généré avec succès !");
        scanner.close();
    }
}
