package checkout

class Checkout(stockList: (String, String)*) {
  private val priceLookup: Map[String, Price] =
    stockList.map {
      case (item, price) => (item.toLowerCase, Price(price))
    }.toMap

  def billFor(items: String*): Price =
    items
      .groupBy(_.toLowerCase)
      .mapValues(seq => seq.length)
      .map(tup => priceLookup.getOrElse(tup._1, Price.zero) * tup._2)
      .reduce(_ + _)
}