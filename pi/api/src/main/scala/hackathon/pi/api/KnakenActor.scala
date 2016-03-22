package hackathon.pi.api

import akka.actor.Actor

object KnakenActor {
  case object GetKnaken
  case object ExtraKnaak
}
import KnakenActor._

class KnakenActor extends Actor {
  val arduino = new Arduino(receiveFromArduino)
  def receiveFromArduino(s: String) = {
    if (s == "knaak") {
      self ! ExtraKnaak
    }
  }

  var aantalKnaken = 0

  def receive = {
    case GetKnaken => sender ! aantalKnaken
    case ExtraKnaak => aantalKnaken = aantalKnaken + 1
  }

}
