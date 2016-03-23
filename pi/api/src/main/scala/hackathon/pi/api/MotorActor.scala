package hackathon.pi.api

import akka.actor.{Actor, ActorLogging}

object MotorActor {
  case class MotorActie(s: String)
}
import MotorActor._

class MotorActor extends Actor with ActorLogging {
  val portName = System.getProperty("motorPort", "/dev/ttyACM0")
  log.info(s"starting MotorActor on port $portName")
  val arduino = new Arduino(receiveFromArduino, portName)
  def receiveFromArduino(s: String) = {}

  def receive = {
    case MotorActie(s) => arduino.sendToArduino(s)
  }

}
