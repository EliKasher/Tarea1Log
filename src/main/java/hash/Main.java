package hash;

import java.util.ArrayList;
import java.util.Random;

/**
 * Clase Principal donde se realizan los testeos.
 */
public class Main {
  //Objetos para poder leer los inputs y queries, además de escribir los resultados
  static final Writer write = new Writer();
  static final Reader reader = new Reader();
  static final Random random = new Random();
  // Los valores de c_max que se utilizarán para testear cada N=2^i
  static final ArrayList<Integer> c_max_test = new ArrayList<>();
  // La lista de numeros que se utilizarán para testear insert
  static final ArrayList<Long> numbers = new ArrayList<>();
  // La lista de hashes
  static final ArrayList<LinearHash> hashes = new ArrayList<>();

  /**
   * Genera una secuencia de N números de 64 bits con |N| = 2^i
   * y los guarda en un archivo de texto inputs/numbers_i.txt
   * @param i Es la potencia a la que se eleva
   */
  public static void generateTestingNumbers(int i) {
    int size = (int) Math.pow(2,i);

    //Hacemos un for sobre el size y agregamos numeros distintos random
    for (int j = 1; j < size+1; j++) {
      long x = (long) random.nextInt(size-1) + 1;
      write.write("inputs/numbers.txt", Long.toString(x));
    }
  }

  /**
   * Crea un Hashing Lineal para cada c_max en la lista de c_max
   */
  public static ArrayList<LinearHash> createHashes(int N) {
    ArrayList<LinearHash> hashes = new ArrayList<>();

    for (Integer integer : c_max_test) {
      hashes.add(new LinearHash(integer, N));
    }

    return hashes;
  }

  /**
   * Crea la lista de c_max a probar
   */
  public static void createCMax() {
    // Definir c_max según los tamaños de prueba
    c_max_test.add(1);
    c_max_test.add(2);
    c_max_test.add(3);
    c_max_test.add(4);
    c_max_test.add(5);
    c_max_test.add(8);
    c_max_test.add(10);
    c_max_test.add(20);
    c_max_test.add(40);
    c_max_test.add(60);
    c_max_test.add(500);
    c_max_test.add(1000);
    c_max_test.add(10000);
    c_max_test.add(1000000);
    c_max_test.add(1000000000);
  }



  /**
   * Toma la lista de 2^i números y los inserta al Hash Lineal para
   * determinados c_max. Escribe los resultados a un archivo de texto
   * en la carpeta filename
   * @param filename El nombre del archivo
   */
  public static void insertTesting(String filename) {

    // Para cada c_max experimentamos la inserción en su hash correspondiente y recopilamos resultados
    for (int i = 0; i < c_max_test.size(); i++) {
      // El hash actual para c_max
      LinearHash act_hash = hashes.get(i);

      // Insertamos los N números 1 por 1
      for (Long number : numbers) {
        act_hash.insert(number);
      }
      // Luego de las inserciones se escriben los resultados
      // Se considera el c_max vs actAvgAccess
      // Costo promedio = Costo Real Inserción / Inserciones
      // Se considera % llenado de páginas vs actAvgAccess
      write.write(filename, "C_max " + c_max_test.get(i));
      write.write(filename, "Accesos Promedio Reales: " + act_hash.actAvgAccess);
      write.write(filename, "Porcentaje Promedio Llenado: " + act_hash.calculateAvgFillPct() + "%");
      write.write(filename, "+++++++++++++++++++++++++++++++++++++++++");

      System.out.println("Inserciones c_max" + c_max_test.get(i) + "listas");
    }
  }


  /**
   * Ejecuta los tests para generar los árboles desde 2^10 hasta 2^25 y realizar 100 queries
   * sobre cada uno.
   */
  public static void main(String[] args) {
    // Esto no se debe volver a ejecutar si es que ya se tiene el archivo numbers_potencia.txt, pues
    // el proceso es bastante largo y sería innecesario (por esto se deja comentado)
    // Generamos los numeros para 2^potencia (Se realiza 1 vez por potencia testeada)
    // Basta crear un archivo para 24 y para testear el resto, se lee hasta la línea 2^i
    // generateTestingNumbers(24);

    //-----TESTEO PARA INPUT DE TAMAÑO 2^potencia-----
    // Creamos los c_max
    createCMax();

    // Se lee el listado de números del archivo 'numbers_i.txt' para obtener los 2^i
    // puntos de testeo, se guardarán en la variable numbers

    for (int potencia = 10; potencia <= 24; potencia++) {
      int N = (int) Math.pow(2, potencia);

      // Asignamos la lista generada por createHashes a la variable global hashes
      hashes.clear(); // Limpiamos la lista global de hashes antes de añadir nuevos
      hashes.addAll(createHashes(N)); // Añadimos los hashes generados

      reader.read("inputs/numbers.txt", numbers, (int) Math.pow(2,potencia));
      System.out.println("Números creados");

      // Creamos el nombre del archivo donde se guardarán los resultados de la potencia
      String filename = "results/inserts_" + potencia + ".txt";

      //Realizamos las inserciones
      System.out.println("Inicio inserciones para potencia: " + potencia);
      insertTesting(filename);
      System.out.println("Inserciones listas para potencia: " + potencia);



  }
  }
}

