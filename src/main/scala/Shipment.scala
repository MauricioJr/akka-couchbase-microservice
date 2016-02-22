import akka.http.scaladsl.server.Route
import org.json4s.native.Serialization
import org.json4s.native.Serialization._
import org.json4s.ShortTypeHints

/**
  * Created by mjcarvalho on 2/22/16.
  */

  trait Shipment

  case class SimpleShipment(description: String, price: Float, size: Int) extends Shipment

  case class ComplexShipment(description: String, fromCountry: String, connectionCountry: String, destinationCountry: String, price: Float, size: Int) extends Shipment

  object Shipment {
    def toJson(shipments: List[Shipment]): Route = ???

    val shipments = List[Shipment](
      SimpleShipment(description = "Soya", price = 100.00f, size = 100),
      SimpleShipment(description = "Soya", price = 400.00f, size = 200),
      ComplexShipment(description = "Premmmium Soya", fromCountry = "Brazil", connectionCountry = "India", destinationCountry = "China", price = 500.012F, size = 500),
      ComplexShipment(description = "Camaro SS", fromCountry = "EUA", connectionCountry = "Panama", destinationCountry = "Brazil", price = 1500.012F, size = 2000)
    )

    private implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[SimpleShipment], classOf[ComplexShipment])))
    def listToJson(shipments: List[Shipment]): String = writePretty(shipments)
    def toJson(shipment: Shipment): String = writePretty(shipment)

  }

