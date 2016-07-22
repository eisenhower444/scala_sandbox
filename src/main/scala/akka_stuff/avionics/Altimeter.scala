package akka_stuff.avionics

import akka.actor.Actor.Receive
import akka.actor.{ActorLogging, Actor}
import scala.concurrent.duration._

object Altimeter {
  case class RateOfChange(amount: Float)
}

class Altimeter extends Actor with ActorLogging {
  import Altimeter.RateOfChange

  implicit val ec = context.dispatcher

  val ceiling = 43000

  val maxRateOfClimb = 5000

  var rateOfClimb = 0f
  var altitude = 0d

  var lastTick = System.currentTimeMillis

  val ticker = context.system.scheduler.schedule(100.millis, 100.millis, self, Tick)

  case object Tick

  override def receive: Receive = {
    case RateOfChange(amount) =>
      rateOfClimb = amount.min(1.0f).max(-1.0f)*maxRateOfClimb

      log info s"Altimeter changed rate of climb to $rateOfClimb."

    case Tick =>
      val tick = System.currentTimeMillis()
      altitude = altitude + ((tick - lastTick) / 60000.0) * rateOfClimb
      lastTick = tick
  }

  @throws[Exception](classOf[Exception])
  override def postStop(): Unit = ticker.cancel
}
