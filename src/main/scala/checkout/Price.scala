package checkout

case class Price(private val underlyingPrice: BigDecimal) {
  def +(that: Price): Price = Price(underlyingPrice + that.underlyingPrice)
  def *(that: Price): Price = Price(underlyingPrice * that.underlyingPrice)
  def *(that: Long): Price = Price(underlyingPrice * that)
}

object Price {
  def apply(price: String): Price = Price(BigDecimal(price))

  def zero = Price(BigDecimal(0))
}