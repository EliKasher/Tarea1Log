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
  public Page rebalse;

  /**
   * El constructor de una página
   */
  public Page() {
    page = new ArrayList<>(elem);
  }

  /**
   * @param value El valor buscado en la página
   * @return Si se encuentra o no
   */
  public boolean contains(long value){
    return page.contains(value);
  }

  /**
   * @param value EL valor buscado
   * @return La cantidad de listas de rebalse revisadas
   */
  public ArrayList<Integer> rebalseContains(long value) {
    ArrayList<Integer> res = new ArrayList<>(2);

    // Ya se está accediendo al primer rebalse
    int access = 1;

    // El rebalse inicial
    Page actRebalse = getRebalse();

    // Mientras no se encuentre o quedemos sin rebalse
    while(actRebalse != null) {
      // Si se encuentra se devuelven los accesos realizados
      if (actRebalse.contains(value)) {
        // se configura la respuesta
        res.add(0, 1);
        res.add(1, access);

        return res;
      }

      // sino se revisa el rebalse siguiente
      actRebalse = actRebalse.getRebalse();

      // Se suma 1 acceso
      access++;
    }
    // se configura la respuesta
    res.add(0, 0);
    res.add(1, access);

    return res;
  }

  /**
   *
   */
  public void addRebalse() {

  }

  /**
   * @return La página de rebalse de esta página
   */
  public Page getRebalse() {
    return rebalse;
  }

  /**
   * Añade un elemento a la página
   * Si aún no tiene rebalse, lo crea
   * Sino, busca la última página de rebalse y añade el elemento
   * @param value El valor añadido
   * @return La cantidad de I/Os realizadas
   */
  public int addToPage(Long value) {
    int accesos = 0;

    // Si no hay rebalse (mientras la cant elementos sea <= elem)
    // se inserta a la página
    if (page.size() <= elem) {
      page.add(value);

      // Como se escribe, aumenta en 1 las I/Os
      // se suma a los accesos cuantas listas de rebalse se acceden
      accesos++;

    } else {
      Page actReb = getRebalse();

      // se inserta en la lista de rebalse
      // si no existe se crea y se actualiza la profundidad
      if (actReb != null) {
        // se añade a su página
        actReb.addToPage(value);

      } else { // no existe el rebalse aún
        rebalse = new Page();

        rebalse.addToPage(value);
      }
    }

    return accesos;
  }
}
