package hash;

import java.util.ArrayList;

/**
 * Clase que describe una página
 */
public class Page {
  // La cantidad de elementos que puede contener una página
  public int elem = 1024*8/ Sizeof.sizeof(Long.class);
  // La página
  public ArrayList<Long> page;
  // Su página de rebalse asociada
  public ArrayList<Page> rebalse;

  /**
   * El constructor de una página
   */
  public Page() {
    page = new ArrayList<>(elem);
  }

  /**
   *
   */
  public void add_rebalse() {
    rebalse.add(this);
  }

  /**
   * @return
   */
  public ArrayList<Page> get_rebalse() {
    return rebalse;
  }

  /**
   * Añade un elemento a la página
   * Si aún no tiene rebalse, lo crea
   * Sino, busca la última página de rebalse y añade el elemento
   * @param value El valor añadido
   * @return La cantidad de I/Os realizadas
   */
  public int add_to_page(Long value) {
    int ios = 0;

    // Si no hay rebalse (mientras la cant elementos sea <= elem)
    // se inserta a la página
    if (page.size() <= elem) {
      page.add(value);

      // Como se escribe, aumenta en 1 las I/Os
      ios++;

    } else {
      // se inserta en la lista de rebalse (NO SE QUE ES EN NUESTRA TAREA AUN)

      // si no existe se crea


      // se suma a los accesos cuantas listas de rebalse se acceden

    }

    return ios;
  }
}
