package freestyle.rpc.protocols

import java.nio.ByteBuffer

import com.sksamuel.avro4s._
import freestyle.rpc.internal.util.BigDecimalUtil
import freestyle.rpc.protocol._
import org.apache.avro.Schema
import protocolModels._

object protocol {

  implicit object bigDecimalToSchema extends ToSchema[BigDecimal] {
    override val schema: Schema = Schema.create(Schema.Type.BYTES)
  }
  implicit object bigDecimalFromValue extends FromValue[BigDecimal] {
    def apply(value: Any, field: Schema.Field): BigDecimal =
      BigDecimalUtil.byteToBigDecimal(value.asInstanceOf[ByteBuffer].array())
  }
  implicit object bigDecimalToValue extends ToValue[BigDecimal] {
    override def apply(value: BigDecimal): ByteBuffer =
      ByteBuffer.wrap(BigDecimalUtil.bigDecimalToByte(value))
  }

  @service
  trait StockInfoService[F[_]] {

    @rpc(Avro) def getStockInfo(request: StockInfoRequest): F[StockInfoResponse]

  }
}
