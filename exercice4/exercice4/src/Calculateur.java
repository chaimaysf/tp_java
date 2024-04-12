public class Calculateur {
    public static double calculate(String[] args) throws NumberFormatException {
        if (args.length != 3) {
            throw new IllegalArgumentException("Usage: java Calculateur <nombre> <nombre> <opérateur>");
        }

        double nombre1, nombre2, resultat;
        String operateur;
        try {
            nombre1 = Double.parseDouble(args[0]);
            nombre2 = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Les deux premiers paramètres doivent être numériques.");
        }

        operateur = args[2];
        resultat = switch (operateur) {
            case "+" -> nombre1 + nombre2;
            case "-" -> nombre1 - nombre2;
            case "x" -> nombre1 * nombre2;
            default -> throw new IllegalArgumentException("Opérateur invalide.");
        };
        return resultat;
    }
}