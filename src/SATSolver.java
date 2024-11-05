import java.io.*;
import java.util.*;

public class SATSolver {

    // Méthode principale
    public static void main(String[] args) {
        String dimacsFilename = "output1H.dimacs"; // Nom du fichier DIMACS
        try {
            List<Set<Integer>> clauses = readDIMACSFile(dimacsFilename);
            boolean satisfiable = solveSAT(clauses);
            if (satisfiable) {
                System.out.println("Le fichier DIMACS est satisfaisable.");
            } else {
                System.out.println("Le fichier DIMACS n'est pas satisfaisable.");
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier: " + e.getMessage());
        }
    }

    // Méthode pour lire le fichier DIMACS
    private static List<Set<Integer>> readDIMACSFile(String filename) throws IOException {
        List<Set<Integer>> clauses = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("c") || line.startsWith("p"))
                    continue;

                Set<Integer> clause = new HashSet<>();
                String[] literals = line.split("\\s+");
                for (String literal : literals) {
                    int value = Integer.parseInt(literal);
                    if (value != 0)
                        clause.add(value);
                }
                clauses.add(clause);
            }
        }

        return clauses;
    }

    // Méthode pour résoudre le problème SAT
    private static boolean solveSAT(List<Set<Integer>> clauses) {
        Map<Integer, Boolean> assignment = new HashMap<>();
        Random random = new Random();

        int MAX_ITERATION = 10000; // Nombre maximal d'itérations
        double P = 0.5; // Probabilité P
        int i = 0; // Compteur d'itérations

        while (!isModel(clauses, assignment) && i < MAX_ITERATION) {
            // Choisir au hasard une clause non satisfaite
            Set<Integer> unsatisfiedClause = clauses.get(random.nextInt(clauses.size()));

            // Choisir une variable de manière aléatoire ou déterministe en fonction de la probabilité P
            int variable;
            if (random.nextDouble() <= P) {
                variable = unsatisfiedClause.iterator().next();
            } else {
                List<Integer> literalList = new ArrayList<>(unsatisfiedClause);
                variable = literalList.get(random.nextInt(literalList.size()));
            }

            // Inverser la valeur de la variable dans l'assignation
            assignment.put(Math.abs(variable), !assignment.getOrDefault(Math.abs(variable), false));

            i++; // Incrémenter le compteur d'itérations
        }

        return isModel(clauses, assignment); // Vérifier si l'assignation est un modèle
    }

    // Méthode pour vérifier si une assignation est un modèle de la formule
    private static boolean isModel(List<Set<Integer>> clauses, Map<Integer, Boolean> assignment) {
        for (Set<Integer> clause : clauses) {
            boolean clauseSatisfied = false;
            for (int literal : clause) {
                if (literal > 0 && assignment.getOrDefault(literal, false)
                        || literal < 0 && !assignment.getOrDefault(-literal, false)) {
                    clauseSatisfied = true;
                    break;
                }
            }
            if (!clauseSatisfied) {
                return false; // Une clause n'est pas satisfaite, donc l'assignation n'est pas un modèle
            }
        }
        return true; // Toutes les clauses sont satisfaites, donc l'assignation est un modèle
    }

}
