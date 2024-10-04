package hash;

import java.util.ArrayList;
import java.util.Random;

/**
 * Clase que describe y cumple las funciones de un Hash Lineal
 */
public class LinearHash {
  // Random
  Random random = new Random();
  // Cantidad actual de páginas
  public int pages;
  // La cantidad de bits significativos
  public int t = 0;
  // La tabla de hash se accede por val mod 2^t -> indice
  // A una lista, con 15 valores de rebalse máx
  public ArrayList<Page> tablaHash;
  // La lista de llaves
  public ArrayList<Integer> keys;
  // El máximo de accesos promedio que puede realizarse
  public double maxAvgAccess;
  // La cantidad de elementos que puede contener una página
  public int elem = 1024*8/ Sizeof.sizeof(Long.class);

  // La cantidad de accesos a memoria realizados
  public int actAccess = 0;
  // La cantidad de inserciones realizadas hasta el momento
  public int inserts = 0;
  // El valor actual de accesos promedio realizados
  // actAvgAccess = actAccess/inserts
  public int actAvgAccess = 0;
  // El porcentaje de llenado promedio de las páginas
  public int avgFillPct = 0;

  /**
   * Crea una nueva tabla de Hash Lineal
   * @param maxAvg El costo promedio máximo permitido para el Hash
   * @param N El número máximo de elementos que se insertan
   * Un bloque/página tiene máximo 1024*8 bits / 64 bits
   */
  public LinearHash(double maxAvg, int N) {
    pages = 1;
    tablaHash = new ArrayList<>(pages);
    maxAvgAccess = maxAvg;
    keys = new ArrayList<>(N);

    // Se crea el hash
    for (int i = 0; i < N; i++) {
      keys.add(i,random.nextInt((int) Math.pow(2, 64) - 1));
    }
  }

  /**
   * @return La cantidad de páginas/bloques que tiene el hash
   */
  public int getSize() { return pages; }

  /**
   * @return True si está vacía
   */
  public boolean isEmpty() { return getSize() == 0; }

  /**
   * @param value Recibe el valor a acomodar para darle un hash
   * @return La casilla keys[value] que contiene un número aleatorio entre
   * 0 y 2^64-1, que señala la página a la que irá el valor.
   */
  private int hash(long value) {
    // h[value] -> hash de value
    return keys.get((int) value);
  }

  /**
   * @return True si encontró el elemento
   */
  public boolean get(long val) {
    // Calcula el indice donde debería estar el valor
    int k = hash(val) % (int) Math.pow(2,t);

    // Costo de búsqueda = 1 + largo lista de rebalse
    if (k < pages) { // la pag existe y se lee
      return valueExists(val, k);
    }

    // la pag. k aun no existe
    // se busca en k-2^t
    int index = (int) (k - Math.pow(2, t));

    return valueExists(val, index);
  }

  /**
   * @param val El valor buscado
   * @param index El índice donde se busca
   * @return Si existe el valor val en el lugar del hash que le corresponde.
   */
  private boolean valueExists(long val, int index) {
    boolean res = false;

    // Si está en la página
    if (tablaHash.get(index).contains(val)) {
      res = true;
    } else {
      // se busca en su lista de rebalse
      // se suma la cantidad de páginas de rebalse leídas
      ArrayList<Integer> rebAccess = tablaHash.get(index).rebalseContains(val);

      actAccess += rebAccess.get(1);

      // Se revisa si fue encontrado el elemento
      if (rebAccess.get(0) == 1) {
        res = true;
      }
    }

    actAccess++;
    return res;
  }

  /**
   * Inserta un elemento val a la tabla de Hash
   * @param val El valor a insertar
   */
  public void insert(long val) {
    // Calcula el indice donde se debería colocar el valor
    int k = hash(val) % (int) Math.pow(2, t);

    // Se verifica la existencia del elemento en la tabla de Hash
    boolean exists = get(val);

    // Si el elemento no está, se inserta
    if (!exists) {
      //Aumenta en 1 las inserciones
      inserts++;

      // Si k < pages, se inserta en tablaHash[k]
      // k existe
      if (k < pages) {
        // Extraemos la página donde debemos insertar
        Page actPage = tablaHash.get(k);

        // Se añade el elemento
        actAccess += actPage.addToPage(val);
      }

      // Si k >= p, se inserta en tablaHash[k-2^t]
      // k aún no existe
      if (k >= pages) {
        int index = (int) (k - Math.pow(2, t));

        // Extraemos la página donde debemos insertar
        Page actPage = tablaHash.get(index);

        // Añadimos el valor
        actPage.addToPage(val);

        // Como se escribe, aumenta en 1 las I/Os
        actAccess++;
      }
    }

    actAvgAccess = actAccess / inserts;

    // Finalmente, se recalculan los accesos promedios
    // Si pasa el costo promedio, hay expansión
    if (actAvgAccess > maxAvgAccess) {
      int nextPage = (int) (pages - Math.pow(2, t));

      expand(nextPage);
    }
  }

  /**
   * Realiza el proceso de expansión del Hash
   * Lee la página expPage-2^t con sus elementos y
   * Los re-inserta en hash(2^t+1)
   * Aumenta el tamaño de la tabla a 2^t+1
   * @param expPage La página a expandir
   */
  public void expand(int expPage) {
    // se recorren los elementos de la pág. y sus rebalses
    // y se insertan en h(x) % 2^t+1 -> p y p-2^t

    Page exp = tablaHash.get(expPage);
    Page newPage = new Page();

    for (int i=0; i<exp.getPage().size(); i++) {
      exp.getPage().get(i);
    }

    // Calcula el indice donde se debería colocar el valor
    int k = hash(val) % (int) Math.pow(2, t+1);


    // compactar p - 2^t
    compact(expPage);

    // al finalizar la expansión
    pages = pages + 1;

    if (pages == (int) Math.pow(2,t+1)) {
      t++;
    }
  }

  /**
   * Compacta los elementos de compPage y se eliminan las listas de rebalse innecesarias
   */
  public void compact(int compPage) {
  }
}
