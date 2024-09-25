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
  public double avgAccess;

  /**
   * Crea una nueva tabla de Hash Lineal
   * @param capacity El máximo de páginas que pueda tener la tabla de hash
   * Un bloque/página tiene máximo 1024 bytes / 64 bits -> 16 values
   */
  public LinearHash(int capacity, double avg) {
    pages = 1;
    maxPages = capacity;
    tablaHash = new ArrayList<>(maxPages);
    avgAccess = avg;
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
   * @param key
   * @return
   */
  public boolean contains(int key) {
    return get(key) != null;
  }

  /**
   * @param key Recibe un valor aleatorio para generar el hash
   * @return Devuelve un valor aleatorio entre 0 y 2^64-1 para cualquier elemento key
   */
  private long hash(long key) {
    // El valor se divide en mod 2^t -> 5 % 2^1 = 1 -> arr[1] = 5
    return key % (long) Math.pow(2,t);
  }

  /**
   * Inserta un elemento val a la tabla de Hash
   * @param val El valor a insertar
   */
  public void insert(long val) {
    // Calcula el indice donde se debería colocar el valor
    long k = hash(val);

    do {
      // Si k < pages, se inserta en tablaHash[k]
      // Si hay rebalse se inserta de todas formas
      // Si se supera el avgAccess se debe expandir
      if (k < pages) {
        tablaHash.get((int) k).add(val);
      }

      // Si k >= p, se inserta en tablaHash[k-2^t]
      if (k >= pages) {
        long index = k - (long) Math.pow(2, t);
        tablaHash.get((int) index).add(val);
      }

      pages = pages + 1;

      if (pages == (long) Math.pow(2,t+1)) {
        t++;
      }
    }

    // Do-while loop
    // while part for condition check
    while (i != tmp);
  }
}
