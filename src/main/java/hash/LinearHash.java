package hash;

import java.util.ArrayList;

/**
 * Clase que describe y cumple las funciones de un Hash Lineal
 */
public class LinearHash {
  // Cantidad actual de páginas
  public int pages;
  // La cantidad de bits significativos
  public int t = 0;
  // La tabla de hash se accede por val mod 2^t -> indice
  // A una lista, con 15 valores de rebalse máx
  public ArrayList<Page> tablaHash;
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
   * Un bloque/página tiene máximo 1024 bytes / 64 bits -> 16 values
   */
  public LinearHash(double maxAvg) {
    pages = 1;
    tablaHash = new ArrayList<>(pages);
    maxAvgAccess = maxAvg;
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
   * @param key Recibe un valor aleatorio para generar el hash
   * @return Devuelve un valor aleatorio entre 0 y 2^64-1 para cualquier elemento key
   * Es decir, entrega el índice de guardado del valor
   */
  private int hash(long key) {
    // El valor se divide en mod 2^t
    return (int) (key >> (int) Math.pow(2,t));
  }

  /**
   * @return True si encontró el elemento
   */
  public boolean get(long val) {
    // Calcula el indice donde debería estar el valor
    int k = hash(val);
    boolean res = false;

    // Costo de búsqueda = 1 + largo promedio de listas de rebalse
    // AUN NO SE QUE ES UNA LISTA DE REBALSE

    if (k < pages) { // la pag existe y se lee
      // se lee la página
      if (tablaHash.get(k).contains(val)) {
        // se lee 1 pag, por lo cual aumentan los accesos

        res = true;
      } else {
        // se lee su lista de rebalse FALTA PROGRAMAR
        // se suma la cantidad de páginas de rebalse leídas
        //actAccess += ;
      }

      actAccess++;

    } if (k >= pages) { // la pag. k aun no existe
      // se busca en k-2^t
      int index = (int) (k - Math.pow(2, t));

      // se lee 1 pag, por lo que aumentan los accesos
      if (tablaHash.get(index).contains(val)) {
        res = true;
      } else {
        // se busca en su lista de rebalse FALTA PROGRAMAR
        // se suma la cantidad de páginas de rebalse leídas
        //actAccess += ;
      }

      actAccess++;
    }

    return res;
  }

  /**
   * Inserta un elemento val a la tabla de Hash
   * @param val El valor a insertar
   */
  public void insert(long val) {
    // Calcula el indice donde se debería colocar el valor
    int k = hash(val);

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
        Page act_page = tablaHash.get(k);

        // Se añade el elemento
        actAccess += act_page.add_to_page(val);
      }

      // Si k >= p, se inserta en tablaHash[k-2^t]
      // k aún no existe
      if (k >= pages) {
        int index = (int) (k - Math.pow(2, t));

        // Extraemos la página donde debemos insertar
        Page act_page = tablaHash.get(index);

        // Añadimos el valor
        act_page.add_to_page(val);

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
   * @param expPage La página a expandir
   */
  public void expand(int expPage) {

    // expansion de expPage

    // compactar p - 2^t
    compact();

    // al finalizar la expansión
    pages = pages + 1;

    if (pages == (int) Math.pow(2,t+1)) {
      t++;
    }
  }

  /**
   * Compacta los elementos de pages - 2^t y se eliminan las listas de rebalse innecesarias
   */
  public void compact() {

  }
}
