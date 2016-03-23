package hackathon.pi.api

import java.io.File
import javax.sound.sampled.{AudioInputStream, Clip, AudioSystem}

import akka.actor.{Props, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{RouteResult, Route}
import akka.stream.ActorMaterializer
import akka.util.Timeout
import akka.pattern.ask

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.util.Success

object PiApiCan extends App {
  implicit val system = ActorSystem("PiApiSystem")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher
  implicit val timeout = Timeout(5.seconds)
  val port = 8089

//  val arduinoActor = system.actorOf(Props[ArduinoActor], "arduinoActor")
  val knakenActor = system.actorOf(Props[KnakenActor], "knakenActor")
  val motorActor = system.actorOf(Props[MotorActor], "motorActor")

  val route: Route = get {
    path("brul") {
      makeSound
      complete("Brul!")
//    } ~ pathPrefix("arduino") {
//      pathEndOrSingleSlash {
//        onComplete(arduinoActor ? ArduinoActor.GetInput) {
//          case Success(input: List[String]) => complete(input.mkString("\n"))
//        }
//      } ~ path(Segment) { input: String =>
//        arduinoActor ! ArduinoActor.SendToArduino(input)
//        complete(s"arduino - show $input")
//      }
    } ~ path("knaken") {
      onComplete(knakenActor ? KnakenActor.GetKnaken) {
        case Success(knaken: Int) => complete(s"Je hebt $knaken cent.")
      }
    } ~ path("motor" / Segment) { actie =>
      motorActor ! MotorActor.MotorActie(actie)
      complete("ok")
    }
  }

  // `route` will be implicitly converted to `Flow` using `RouteResult.route2HandlerFlow`
  val bindingFuture = Http().bindAndHandle(RouteResult.route2HandlerFlow(route), "0.0.0.0", port)

  println(s"Lion online at http://localhost:$port/\nPress RETURN to stop...")
  Console.readLine() // for the future transformations
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ ⇒ system.shutdown()) // and shutdown when done


  def makeSound: Unit = {
    val file: File = new File("/home/pi/sounds/lionroar.wav")
    var audioIn: AudioInputStream = null
    try {
      audioIn = AudioSystem.getAudioInputStream(file)
      var clip: Clip = null
      clip = AudioSystem.getClip
      clip.open(audioIn)
      clip.start
      Thread.sleep(clip.getMicrosecondLength / 3000)
    }
    catch {
      case e: Exception => {
        e.printStackTrace
      }
    }
  }
}