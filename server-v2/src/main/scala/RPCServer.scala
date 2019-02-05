package higherkindness.mu.example

import cats.effect._
import higherkindness.mu.protocols._
import higherkindness.mu.rpc.protocol.legacy.AvroDecimalCompatUtils._
import higherkindness.mu.rpc.server.{AddService, GrpcConfig, GrpcServer}
import shapeless._

class StockInfoServiceImpl extends StockInfoService[IO] {

  def getStockInfo(request: StockInfoRequest): IO[StockInfoResponse] =
    for {
      _ <- IO(println(s"Receiving request $request"))
      response =
        StockInfoResponse(Coproduct[Foo3 :+: Foo2 :+: Foo1 :+: CNil](Foo1(Common(), Nil)), Left(Bar1(None, Common())))
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
