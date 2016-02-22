import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.javadsl.server.HttpServiceBase
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.server.Directives._
import akka.stream.{ActorMaterializer, Materializer}
import com.typesafe.config.{Config, ConfigFactory}
import spray.json.DefaultJsonProtocol

import scala.concurrent.ExecutionContextExecutor


trait Service extends HttpServiceBase with SprayJsonSupport {

  implicit val system: ActorSystem

  implicit def executor: ExecutionContextExecutor

  implicit val materializer: Materializer

  def config: Config

  val logger: LoggingAdapter

  var shipments = Shipment.shipments

  val routes = {

    get {
      path("alive") {
        complete("Hello to your shipments tracking API")
      }
    } ~
      get {
        path("list" / "all") {
          complete(Shipment.listToJson(shipments))
        }
      }
  }
}

object AkkaHttpMicroservice extends App with Service {
  override implicit val system = ActorSystem()
  override implicit val executor = system.dispatcher
  override implicit val materializer = ActorMaterializer()

  override val config = ConfigFactory.load()
  override val logger = Logging(system, getClass)
  Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))
}
