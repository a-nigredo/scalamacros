package scala.macros.internal
package trees

import java.util.concurrent.atomic._
import scala.macros.Universe

trait Gensym { self: Universe =>
  private val atomicInteger = new AtomicInteger()

  def gensym(prefix: String): String = {
    val normalizedPrefix = if (!prefix.endsWith("$")) prefix + "$" else prefix
    normalizedPrefix + atomicInteger.incrementAndGet()
  }
}
