package hash;


import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {
  public void read(String filename, ArrayList<Long> list, int limite) {
    File fichero = new File(filename);
    Scanner s = null;
    int index = 0;
    try {
      // Leemos el contenido del fichero
      s = new Scanner(fichero);

      // Leemos linea a linea el fichero
      while (s.hasNextLine() && index<limite) {
        String linea = s.nextLine(); 	// Guardamos la linea en un String
        long number = Long.parseLong(linea);
        list.add(number);
        index++;
      }
    } catch (Exception e) {
      System.out.println("Mensaje: " + e.getMessage());
    } finally {
      // Cerramos el fichero tanto si la lectura ha sido correcta o no
      try {
        if (s != null)
          s.close();
      } catch (Exception e2) {
        System.out.println("Mensaje 2: " + e2.getMessage());
      }
    }
  }
}