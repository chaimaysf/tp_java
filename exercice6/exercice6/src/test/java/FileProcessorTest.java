import fr.hetic.FileProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FileProcessorTest {

    @TempDir
    static Path tempDir;

    @Test
    void processFile_withValidInputFile_shouldWriteResultsToFile() throws IOException {
        // Créer un fichier d'entrée avec des opérations valides
        File inputFile = new File(tempDir.toFile(), "test.op");
        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));
        writer.write("5;2;+"); // 5 + 2
        writer.newLine();
        writer.write("10;3;-"); // 10 - 3
        writer.newLine();
        writer.close();

        // Appeler la méthode à tester
        FileProcessor.processFile(inputFile);

        // Vérifier que le fichier de résultats a été créé
        File outputFile = new File(tempDir.toFile(), "test.res");
        assertTrue(outputFile.exists());

        // Lire les résultats du fichier de résultats
        BufferedReader reader = new BufferedReader(new FileReader(outputFile));
        List<String> results = reader.lines().toList();
        reader.close();

        // Vérifier que les résultats sont corrects
        assertEquals("7.0", results.get(0)); // Résultat de 5 + 2
        assertEquals("7.0", results.get(1)); // Résultat de 10 - 3
    }

    @Test
    void processFile_withInvalidInputFile_shouldWriteErrorResultsToFile() throws IOException {
        // Créer un fichier d'entrée avec une opération invalide
        File inputFile = new File(tempDir.toFile(), "test.op");
        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));
        writer.write("5;a;+"); // Opérande invalide
        writer.close();

        // Appeler la méthode à tester
        FileProcessor.processFile(inputFile);

        // Vérifier que le fichier de résultats a été créé
        File outputFile = new File(tempDir.toFile(), "test.res");
        assertTrue(outputFile.exists());

        // Lire les résultats du fichier de résultats
        BufferedReader reader = new BufferedReader(new FileReader(outputFile));
        List<String> results = reader.lines().toList();
        reader.close();

        // Vérifier que les résultats d'erreur ont été écrits
        assertEquals("ERROR", results.get(0));
    }
}
