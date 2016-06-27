package shapeless_experiment

import shapeless._

/**
  * Inspired by http://www.cakesolutions.net/teamblogs/solving-problems-in-a-generic-way-using-shapeless
  *
  * Experiment with creating generic typeclasses using shapeless. Here we want to create a type class which
  * returns the arity of a structure, i.e. the total number of arguments to specify to obtain this particular value.
  * E.g.
  *         Int                                                             => 1
  *         String                                                          => 1
  *         case class Whatever(wut: String, meuh: Int)                     => 2
  *         case class Classy(walrus: String, age: Int, whatever: Whatever) => 4
  */

trait Arity[A] {
  def arity(a: A): Int
}


object Arity extends TypeClassCompanion[Arity] {

  override val typeClass: TypeClass[Arity] = new TypeClass[Arity] {

    override def coproduct[L, R <: Coproduct](cl: => Arity[L], cr: => Arity[R]): Arity[:+:[L, R]] = new Arity[:+:[L, R]] {
      override def arity(a: :+:[L, R]): Int = a match {
        case Inl(l) => cl.arity(l) + 1
        case Inr(r) => cr.arity(r)
      }
    }

    override def emptyCoproduct: Arity[CNil] = new Arity[CNil] {
      override def arity(a: CNil): Int = 0
    }

    override def emptyProduct: Arity[HNil] = new Arity[HNil] {
      override def arity(a: HNil): Int = 0
    }

    override def product[H, T <: HList](ch: Arity[H], ct: Arity[T]): Arity[::[H, T]] = new Arity[::[H, T]] {
      override def arity(a: ::[H, T]): Int = ch.arity(a.head) + ct.arity(a.tail)
    }

    override def project[F, G](instance: => Arity[G], to: (F) => G, from: (G) => F): Arity[F] = new Arity[F] {
      override def arity(a: F): Int = instance.arity(to(a))
    }

  }

  implicit def intArity = new Arity[Int] {
    override def arity(a: Int): Int = 1
  }

  implicit def doubleArity = new Arity[Double] {
    override def arity(a: Double): Int = 1
  }

  implicit def stringArity = new Arity[String] {
    override def arity(a: String): Int = 1
  }
}


object test extends App {
  import Arity._

  sealed abstract class Nationality(val country: String, coolness: Int)

  object French extends Nationality("France", 3)
  object English extends Nationality("England", 5)
  object German extends Nationality("Germany", 122)

  case class Address(street: String, stringNumber: Int)

  case class Person(name: String, age: Int, nationality: Nationality, address: Address)

  // expect 1
  println(implicitly[Arity[Int]].arity(32))

  // expect 1
  println(implicitly[Arity[String]].arity("walrus"))

  // expect 5
  println(implicitly[Arity[Person]].arity(Person("Thomas", 29, French, Address("Rue de la procession", 55))))

  // Life is good...
}