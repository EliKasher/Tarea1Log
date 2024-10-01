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
}
