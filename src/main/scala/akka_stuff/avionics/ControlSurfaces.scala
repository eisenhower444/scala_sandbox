package akka_stuff.avionics

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorRef}

object ControlSurfaces {
  case class StickBack(amount: Float)
  case class StickForward(amount: Float)
}

class ControlSurfaces(altimeter: ActorRef) extends Actor {
  import ControlSurfaces._
  import Altimeter._

  override def receive: Receive = {
    case StickBack(amount) =>
      altimeter ! RateOfChange(amount)
    case StickForward(amount) =>
      altimeter ! RateOfChange(-1 * amount)
  }
}
