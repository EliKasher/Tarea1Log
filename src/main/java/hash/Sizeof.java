package hash;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Sizeof {
  /**
   * Clase para calcular el tamaño de cada tipo de dato.
   */
  public static int sizeof(Class dataType) {
    if (dataType == null) {
      throw new NullPointerException();
    }
    if (dataType == byte.class || dataType == Byte.class) {
      return Byte.SIZE;
    }
    if (dataType == short.class || dataType == Short.class) {
      return Short.SIZE;
    }
    if (dataType == char.class || dataType == Character.class) {
      return Character.SIZE;
    }
    if (dataType == int.class || dataType == Integer.class) {
      return Integer.SIZE;
    }
    if (dataType == long.class || dataType == Long.class) {
      return Long.SIZE;
    }
    if (dataType == float.class || dataType == Float.class) {
      return Float.SIZE;
    }
    if (dataType == double.class || dataType == Double.class) {
      return Double.SIZE;
    }
    return 4;
  }
}
