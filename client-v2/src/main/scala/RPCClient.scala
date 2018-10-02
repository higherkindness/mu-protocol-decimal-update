package freestyle.rpc

import cats.effect.IO
import freestyle.rpc.protocols.{StockInfoRequest, StockInfoService}

object RPCClient extends App {

  implicit val S: monix.execution.Scheduler = monix.execution.Scheduler.Implicits.global

  val channelFor: ChannelFor = ChannelForAddress("localhost", 8080)

  val serviceClient: StockInfoService.Client[IO] =
    StockInfoService.client[IO](channelFor)

  val request: StockInfoRequest = StockInfoRequest("stockId")

  val app: IO[Unit] = for {
    _ <- IO(println(s"Calling server with request $request"))
    result <- serviceClient.getStockInfo(request)
    _ <- IO(println(s"Response $result"))
  } yield ()

  app.unsafeRunSync()

}
