package freestyle.rpc

import cats.effect.IO
import freestyle.rpc.protocols.{StockInfoRequest, StockInfoResponse, StockInfoService}
import freestyle.rpc.server.{AddService, GrpcConfig, GrpcServer}

class StockInfoServiceImpl extends StockInfoService[IO] {
  def getStockInfo(request: StockInfoRequest): IO[StockInfoResponse] =
    IO(StockInfoResponse(request.stockId, BigDecimal("30578.86"), BigDecimal("4.342")))
}

object RPCServer extends App {

  implicit val S: monix.execution.Scheduler = monix.execution.Scheduler.Implicits.global

  implicit val service: StockInfoService[IO] = new StockInfoServiceImpl

  val grpcConfigs: List[GrpcConfig] = List(
    AddService(StockInfoService.bindService[IO])
  )

  val runServer = GrpcServer.default[IO](8080, grpcConfigs).flatMap(GrpcServer.server[IO])
  runServer.unsafeRunSync()
}
