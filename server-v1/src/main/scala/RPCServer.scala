package freestyle.rpc.protocols

import cats.effect.IO
import freestyle.rpc.protocols.protocol.StockInfoService
import freestyle.rpc.protocols.protocolModels.{StockInfoRequest, StockInfoResponse}
import freestyle.rpc.server.{AddService, GrpcConfig, ServerW}
import freestyle.rpc.server.implicits._

class StockInfoServiceImpl extends StockInfoService[IO] {
  def getStockInfo(request: StockInfoRequest): IO[StockInfoResponse] =
    for {
      _ <- IO(println(s"Receiving request $request"))
      response = StockInfoResponse(request.stockId, BigDecimal("30578.86"), BigDecimal("4.342"))
      _ <- IO(println(s"Generating response $response"))
    } yield response
}

object RPCServer extends App {

  implicit val S: monix.execution.Scheduler = monix.execution.Scheduler.Implicits.global

  implicit val service: StockInfoService[IO] = new StockInfoServiceImpl

  val grpcConfigs: List[GrpcConfig] = List(
    AddService(StockInfoService.bindService[IO])
  )

  implicit val serverW: ServerW = ServerW.default(8080, grpcConfigs)

  server[IO].unsafeRunSync()
}
