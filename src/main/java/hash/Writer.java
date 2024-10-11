package hash;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * La clase que escribe en un archivo de texto
 */
public class Writer {
  /**
   * Escribe el contenido dado en el archivo de texto filename
   * @param filename El nombre del archivo de texto
   * @param content El contenido escrito
   */
  public void write(String filename, String content) {
    // Crear el archivo y sus directorios si no existen
    File file = new File(filename);
    File dir = file.getParentFile();

    // Verificamos si el directorio existe, si no, lo creamos
    if (dir != null && !dir.exists()) {
      dir.mkdirs(); // Crea el directorio y cualquier directorio padre que no exista
    }

    try (FileWriter fw = new FileWriter(filename, true); // El 'true' es para añadir datos sin sobrescribir
         BufferedWriter bw = new BufferedWriter(fw)) {

      // Escribe el contenido en el archivo
      bw.write(content);
      bw.newLine(); // Añade una nueva línea

    } catch (IOException e) {
      System.err.println("Ocurrió un error al escribir en el archivo: " + e.getMessage());
    }
  }
}
