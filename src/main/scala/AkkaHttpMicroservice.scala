import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.javadsl.server.HttpServiceBase
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.stream.{ActorMaterializer, Materializer}
import com.typesafe.config.{Config, ConfigFactory}
import spray.json.{JsString, RootJsonFormat, DefaultJsonProtocol, JsonParser}

import scala.concurrent.ExecutionContextExecutor


trait Service extends HttpServiceBase with SprayJsonSupport {

  implicit val system: ActorSystem

  implicit def executor: ExecutionContextExecutor

  implicit val materializer: Materializer

  def config: Config

  val logger: LoggingAdapter

  var shipments = Shipment.shipments

  object MyJsonProtocol extends DefaultJsonProtocol {
    implicit val shipmentFormat = jsonFormat3(SimpleShipment)
  }

  import MyJsonProtocol._
  import spray.json._

  val routes = {

    get {
      path("alive") {
        complete("Hello to your shipments tracking API")
      }
    } ~
    get {
      path("shipments") {
        complete(Shipment.listToJson(shipments))
      }
    } ~
    get {
      path("shipment" / IntNumber / "details") { idx =>
        complete(Shipment.toJson(shipments(idx)))
      }
    } ~
    post {
      path("queryparameters" / "shipment" / "new") {
        parameters("description" ?, "price".as[Float], "size".as[Int]) { (description, price, size) =>
          val newSimpleShipment = SimpleShipment(description.getOrElse("Shipment default description"), price, size)
          shipments = newSimpleShipment :: shipments
          complete {
            "OK"
          }
        }
      }
    } ~
    post {
      path("shipment" / "new") {
        entity(as[String]) {
          content =>
            shipments = JsonParser(content).convertTo[SimpleShipment] :: shipments
            complete("OK")
        }
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
