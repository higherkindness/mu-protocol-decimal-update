package higherkindness.mu.sample

import cats.effect._
import higherkindness.mu.rpc._
import higherkindness.mu.protocols.{StockInfoRequest, StockInfoService}

object RPCClient extends App {

  implicit val EC: scala.concurrent.ExecutionContext =
    scala.concurrent.ExecutionContext.Implicits.global

  implicit val timer: Timer[cats.effect.IO]     = IO.timer(EC)
  implicit val cs: ContextShift[cats.effect.IO] = IO.contextShift(EC)

  val channelFor: ChannelFor = ChannelForAddress("localhost", 8080)

  val serviceClient: Resource[IO, StockInfoService[IO]] =
    StockInfoService.client[IO](channelFor)

  val request: StockInfoRequest = StockInfoRequest("stockId")

  val app: IO[Unit] = for {
    _ <- IO(println(s"Calling server with request $request"))
    result <- serviceClient.use(_.getStockInfo(request))
    _ <- IO(println(s"Response $result"))
  } yield ()

  app.unsafeRunSync()

}
