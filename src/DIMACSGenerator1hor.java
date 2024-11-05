import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//////////////////////////////////////////////////////////
//                                                      //
//           Eviter les alignements horizontaux :       //
//                                                      //
//////////////////////////////////////////////////////////

public class DIMACSGenerator1hor {
    private final int n;
    private final int m;
    private final int numVariables;
    private final int numClauses;
    private final String fileName;

    public DIMACSGenerator1hor(int n, int m, String fileName) {
        this.n = n;
        this.m = m;
        this.numVariables = n * m;
        this.numClauses = m * (n - 2) * 2;
        this.fileName = fileName;
    }

    public void generateDIMACS() {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("p cnf " + numVariables + " " + numClauses + "\n");

            // Ajouter les clauses pour éviter les alignements horizontaux
            for (int x = 1; x <= n; x++) {
                for (int y = 1; y <= m - 2; y++) {
                    int var1 = getVariableNumber(x, y);
                    int var2 = getVariableNumber(x, y + 1);
                    int var3 = getVariableNumber(x, y + 2);

                    writeClause(writer, -var1, -var2, -var3);
                    writeClause(writer, var1, var2, var3);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeClause(FileWriter writer, int... literals) throws IOException {
        for (int i = 0; i < literals.length; i++) {
            writer.write(literals[i] + " ");
        }
        writer.write("0\n");
    }

    private int getVariableNumber(int x, int y) {
        return (x - 1) * m + y;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Demander à l'utilisateur de saisir n et m
        System.out.print("Entrez le nombre de lignes  : ");
        int n = scanner.nextInt();
        System.out.print("Entrez le nombre de colonnes : ");
        int m = scanner.nextInt();
        scanner.nextLine(); // Consommer la nouvelle ligne

        // Demander à l'utilisateur le nom du fichier de sortie
        System.out.print("Entrez le nom du fichier de sortie : ");
        String fileName = scanner.nextLine();

        // Générer le fichier DIMACS
        DIMACSGenerator1Ver generator = new DIMACSGenerator1Ver(n, m, fileName);
        generator.generateDIMACS();

        scanner.close();
    }
}
