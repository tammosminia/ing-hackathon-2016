package hackathon.pi.api

import akka.actor.{Actor, ActorLogging}
import akka.event.LoggingReceive

object KnakenActor {
  case object GetKnaken
  case object ExtraKnaak
  case class Extra(cents: Int)
}
import KnakenActor._

class KnakenActor extends Actor with ActorLogging {
  val portName = System.getProperty("knakenPort", "/dev/ttyACM0")
  log.info(s"starting KnakenActor on port $portName")
  val arduino = new Arduino(receiveFromArduino, portName)

  val coinPrefix = "coin found. lowest pin="
  val pinToCents = Map(3 -> 200, 4 -> 50, 5 -> 100, 6 -> 20, 7 -> 5, 8 -> 10)

  def receiveFromArduino(s: String) = {
    log.info(s"got $s")
    if (s == "knaak") {
      self ! ExtraKnaak
    } else if (s.startsWith(coinPrefix)) {
      val lowestPin = s.stripPrefix(coinPrefix).toInt
      log.info(s"lowest pin $lowestPin")
      self ! Extra(pinToCents(lowestPin))
    }
  }

  var balance = 0

  def receive = LoggingReceive {
    case GetKnaken => sender ! balance
    case ExtraKnaak => balance = balance + 150
    case Extra(geld) =>
      balance = balance + geld
      Geluid.makeSound
  }

}
