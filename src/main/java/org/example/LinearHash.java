package org.example;

import java.util.ArrayList;

/**
 *
 */
public class LinearHash {
  // Cantidad actual de páginas
  public int pages;
  // La cantidad de bits significativos
  public int t = 0;
  // Cantidad máxima de páginas
  public int maxPages = (int) Math.floor(Math.pow(2, t));
  // La tabla de hash se accede por val mod 2^t -> indice
  // A una lista, con 15 valores de rebalse máx
  public ArrayList<ArrayList<Long>> tablaHash;
  // El máximo de accesos promedio que puede realizarse
  public double maxAvgAccess;
  // El valor actual de accesos promedio realizados
  public double actAvgAccess = 0;
  // La cantidad de elementos que puede contener una página
  public int elem = 1024/ Sizeof.sizeof(Long.class);


  /**
   * Crea una nueva tabla de Hash Lineal
   * @param capacity El máximo de páginas que pueda tener la tabla de hash
   * Un bloque/página tiene máximo 1024 bytes / 64 bits -> 16 values
   */
  public LinearHash(int capacity, double maxAvg, int bytes) {
    pages = 1;
    maxPages = capacity;
    tablaHash = new ArrayList<>(maxPages);
    maxAvgAccess = maxAvg;
  }

  /**
   * @return La cantidad de páginas/bloques que tiene el hash
   */
  public int getSize() { return pages; }

  /**
   * @return Si se llegó al máximo de llenado del hash
   */
  public boolean isFull() {
    return pages == maxPages;
  }

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
    // El valor se divide en mod 2^t -> 5 % 2^1 = 1 -> arr[1] = 5
    return (int) (key % Math.pow(2,t));
  }

  /**
   * Inserta un elemento val a la tabla de Hash
   * @param val El valor a insertar
   */
  public void insert(long val) {
    // Calcula el indice donde se debería colocar el valor
    int k = hash(val);

    // Si se supera el avgAccess se debe expandir
    if (actAvgAccess >= maxAvgAccess) {
        expand();
    } else {
        // Si k < pages, se inserta en tablaHash[k]
        // Si hay rebalse (mientras la cant elementos sea <= elem)
        // se inserta de todas formas a la lista de rebalses
        if (k < pages) {
          if (tablaHash.get(k).size() <= elem) {
            tablaHash.get(k).add(val);
          } else { // sino, se debe realizar una expansión
            // expandir tabla hash
            expand();
          }
        }

        // Si k >= p, se inserta en tablaHash[k-2^t]
        // k aún no existe
        if (k >= pages) {
          int index = (int) (k - Math.pow(2, t));

          tablaHash.get(index).add(val);
        }

        // al finalizar la expansión
        // pages = pages + 1;

        // if (pages == (long) Math.pow(2,t+1)) {
          //t++;
        //}
      }
    }

  /**
   * Realiza el proceso de expansión del Hash
   */
  public void expand() {

  }

}
