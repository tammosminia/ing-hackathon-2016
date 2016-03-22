package hackathon.pi.api

import akka.actor.{ActorLogging, Actor}

object ArduinoActor {
  case class SendToArduino(x: String)
  case class GotFromArduino(s: String)
  case object GetInput
}
import ArduinoActor._

class ArduinoActor extends Actor with ActorLogging {
  val arduino = new Arduino(receiveFromArduino)

  var input: List[String] = Nil

  def receive = {
    case SendToArduino(x) => arduino.sendToArduino(x)
    case GotFromArduino(s) => input = s :: input
    case GetInput => sender ! input
  }

  def receiveFromArduino(s: String) = {
    self ! GotFromArduino(s)
  }
}
