import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/////////////////////////////////////////////////////////////
//                                                         //
//           Equilibrer les valeurs dans les colonnes :    //
//                                                         //
/////////////////////////////////////////////////////////////

public class DIMACSGenerator2Col {
    private final int n;
    private final int numVariables;
    private final int numClauses;
    private final String fileName;

    public DIMACSGenerator2Col(int n, String fileName) {
        this.n = n;
        this.numVariables = n * n * 2;
        this.numClauses = n * n * 2;
        this.fileName = fileName;
    }

    public void generateDIMACS() {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("p cnf " + numVariables + " " + numClauses + "\n");

            // Ajouter les clauses
            for (int y = 1; y <= n; y++) {
                for (int x = 1; x <= n; x++) {
                    int var1 = getVariableNumber(x, y, 0);
                    int var2 = getVariableNumber(x, y, 1);

                    writeClause(writer, var1, var2);
                    writeClause(writer, -var1, -var2);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeClause(FileWriter writer, int... literals) throws IOException {
        for (int literal : literals) {
            writer.write(literal + " ");
        }
        writer.write("0\n");
    }

    private int getVariableNumber(int x, int y, int value) {
        return (x - 1) * n * 2 + (y - 1) * 2 + value + 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez le nombre de lignes : ");
        int n = scanner.nextInt();
        System.out.print("Entrez le nom du fichier de sortie : ");
        String fileName = scanner.next();

        DIMACSGenerator2Col generator = new DIMACSGenerator2Col(n, fileName);
        generator.generateDIMACS();
        System.out.println("Fichier DIMACS généré avec succès !");
        scanner.close();
    }
}
