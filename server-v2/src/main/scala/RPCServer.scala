package freestyle.rpc

import cats.effect.IO
import freestyle.rpc.protocol.legacy.AvroDecimalCompatUtils._
import freestyle.rpc.protocols._
import freestyle.rpc.server.{AddService, GrpcConfig, GrpcServer}
import shapeless.{Nat, tag}

class StockInfoServiceImpl extends StockInfoService[IO] {

  def getStockInfo(request: StockInfoRequest): IO[StockInfoResponse] =
    for {
      _ <- IO(println(s"Receiving request $request"))
      response =
        StockInfoResponse(
          request.stockId,
          AvroDecimalCompat(BigDecimal("30578.86")),
          AvroDecimalCompat(BigDecimal("4.342")),
          tag[((Nat._1, Nat._0), Nat._2)][BigDecimal](BigDecimal("30578.86")),
          tag[(Nat._5, Nat._4)][BigDecimal](BigDecimal("4.342"))
        )
      _ <- IO(println(s"Generating response $response"))
    } yield response

}

object RPCServer extends App {

  implicit val S: monix.execution.Scheduler =
    monix.execution.Scheduler.Implicits.global

  implicit val service: StockInfoService[IO] = new StockInfoServiceImpl

  val grpcConfigs: List[GrpcConfig] = List(
    AddService(StockInfoService.bindService[IO])
  )

  val runServer =
    GrpcServer.default[IO](8080, grpcConfigs).flatMap(GrpcServer.server[IO])
  runServer.unsafeRunSync()
}
