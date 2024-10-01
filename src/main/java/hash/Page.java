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
  public ArrayList<Page> rebalse;

  public Page() {
    page = new ArrayList<>(elem);
    rebalse = new ArrayList<>();
  }

  /**
   * 
   */
  public void add_rebalse() {
    rebalse.add(this);
  }
}
