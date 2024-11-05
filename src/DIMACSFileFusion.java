import java.io.*;

public class DIMACSFileFusion {
    // Méthode pour fusionner plusieurs fichiers DIMACS en un seul
    public static void mergeDIMACSFiles(String[] inputFiles, String outputFile) {
        int totalVars = 0;
        int totalClauses = 0;

        // Calculer le nombre total de variables et de clauses
        for (String inputFile : inputFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("p cnf")) {
                        String[] parts = line.split("\\s+");
                        totalVars += Integer.parseInt(parts[2]);
                        totalClauses += Integer.parseInt(parts[3]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileWriter writer = new FileWriter(outputFile)) {
            // Écrire la ligne "p cnf" avec le nombre total de variables et de clauses
            writer.write("p cnf " + totalVars + " " + totalClauses + "\n");

            // Fusionner les fichiers DIMACS en un seul
            for (String inputFile : inputFiles) {
                appendFileContents(inputFile, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour ajouter le contenu d'un fichier à un écrivain
    private static void appendFileContents(String inputFile, FileWriter writer) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("p cnf")) { // Ignorer les lignes commençant par "p cnf"
                    writer.write(line + "\n");
                }
            }
        }
    }

    // Main pour tester la fusion des fichiers DIMACS
    public static void main(String[] args) {
        String[] inputFiles = {
                "output1H.dimacs",
                "output1V.dimacs",
                "output2L.dimacs",
                "output2C.dimacs",
                "output3.dimacs"
        }; // Noms des fichiers d'entrée
        String outputFile = "outputFusion.dimacs"; // Nom du fichier de sortie

        mergeDIMACSFiles(inputFiles, outputFile);
        System.out.println("Fusion des fichiers DIMACS terminée !");
}
}
