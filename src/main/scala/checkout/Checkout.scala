package checkout

class Checkout(private val stockList: Seq[(String, String)], private val offers: Seq[Offer]) {

  def withOffers(offers: Offer*): Checkout = new Checkout(stockList, offers)

  private val priceLookup: Map[String, Price] =
    stockList.map {
      case (item, price) => (item.toLowerCase, Price(price))
    }.toMap

  def billFor(items: String*): Price =
    items
      .groupBy(_.toLowerCase)
      .mapValues(seq => seq.length)
      .map(tup => priceFor(tup._2, tup._1))
      .reduce(_ + _)

  private def priceFor(count: Int, item: String): Price = {
    def simplePrice(includedCount: Int) = priceLookup.getOrElse(item, Price.zero) * includedCount
    def offerPrice(offer: Offer, includedCount: Int): Price = offer.price * includedCount

    def compoundPrice(offer: Offer): Price = offer.qualifier(count) match {
      case (inOffer, notInOffer) => offerPrice(offer, inOffer) + simplePrice(notInOffer)
    }

    offers
      .find(_.name.toLowerCase == item)
      .fold(simplePrice(count))(compoundPrice)
  }
}

object Checkout {
  def apply(stockList: (String, String)*): Checkout = new Checkout(stockList, Seq.empty[Offer])
}