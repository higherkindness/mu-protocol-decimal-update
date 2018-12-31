package higherkindness.mu.example

import cats.effect._
import higherkindness.mu.protocols.{StockInfoRequest, StockInfoResponse, StockInfoService}
import higherkindness.mu.rpc.protocol.legacy.AvroDecimalCompatUtils._
import higherkindness.mu.rpc.server.{AddService, GrpcConfig, GrpcServer}
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

  implicit val EC: scala.concurrent.ExecutionContext =
    scala.concurrent.ExecutionContext.Implicits.global

  implicit val timer: Timer[cats.effect.IO]     = IO.timer(EC)
  implicit val cs: ContextShift[cats.effect.IO] = IO.contextShift(EC)

  implicit val service: StockInfoService[IO] = new StockInfoServiceImpl

  (for {
    grpcConfig <- StockInfoService.bindService[IO].map(AddService(_))
    grpcServer <- GrpcServer.default[IO](8080, List(grpcConfig))
    _          <- GrpcServer.server[IO](grpcServer)
  } yield ()).unsafeRunSync()
}
