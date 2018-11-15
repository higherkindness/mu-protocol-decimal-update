package freestyle.rpc.protocols

import freestyle.rpc.protocol._
import shapeless.{:+:, CNil}

object protocolModels {

  final case class Temp()
  final case class Hard()

  final case class StockInfoRequest(stockId: String, status: Temp :+: Hard :+: CNil)

  final case class StockInfoResponse(stockId: String, price: BigDecimal, rate: BigDecimal)

}
