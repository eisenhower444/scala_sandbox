
import shapeless._

case class MyClass[H <: HList](hs: H)

object MyClass {
  def apply[P <: Product, L <: HList](p: P)(implicit gen: Generic.Aux[P, L]) =
    new MyClass[L](gen.to(p))  // First create an HList from a Tuple and then stick that into MyClass constructor argument
}

val x = MyClass(1, "Hello")


println(x)

val y = MyClass(1, "Hello", 123.2)

println(y)