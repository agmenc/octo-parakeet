package checkout

case class Offer(name: String, price: Price, qualifier: Int => (Int, Int))

object OfferQualifiers {
  def buyOneGetOneFree(count: Int) = (count / 2, count % 2)
  def threeForTwo(count: Int) = (count / 3, count % 3)
}