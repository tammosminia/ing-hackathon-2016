package hackathon.pi.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{RouteResult, Route}
import akka.stream.ActorMaterializer
import akka.util.Timeout

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

object PiApiCan extends App {
  implicit val system = ActorSystem("PiApiSystem")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher
  implicit val timeout = Timeout(5.seconds)
  val port = 8081

  val route: Route = get {
    path("brul") {
      complete("Brul!")
    } ~ pathPrefix("arduino") {
      pathEndOrSingleSlash {
        complete("arduino")
      } ~ path(Segment) { input: String =>
        Arduino.toArduino(input)
        complete(s"arduino - show $input")
      }
    }

  }

  // `route` will be implicitly converted to `Flow` using `RouteResult.route2HandlerFlow`
  val bindingFuture = Http().bindAndHandle(RouteResult.route2HandlerFlow(route), "localhost", port)

  println(s"Lion online at http://localhost:$port/\nPress RETURN to stop...")
  Console.readLine() // for the future transformations
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ ⇒ system.shutdown()) // and shutdown when done
}