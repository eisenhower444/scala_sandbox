import akka.actor.{Props, ActorSystem, Actor}
import akka.actor.Actor.Receive


class BadShakespeareanActor extends Actor {
  override def receive: Receive = {
    case "Good morning" =>
      println("I am the walrus")
    case "You're  terrible" =>
      println("No I am not...")
    case 42 =>
      println("Whatever")
  }
}

object BadShakespeareanMain {
  val system = ActorSystem("BadShakespearean")
  val actor = system.actorOf(Props[BadShakespeareanActor], "Shake")

  def send(msg: String): Unit = {
    println("Roger")
    actor ! msg
    Thread.sleep(100)
  }

  def main(args: Array[String]) {
    send("Good morning")
    send("You're  terrible")
    send("What?")
    actor ! 42
    Thread.sleep(100)
    system.shutdown()
  }
}