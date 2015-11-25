package checkout

import checkout.OfferQualifiers._
import org.scalatest.{MustMatchers, WordSpec}

class CheckoutSpec extends WordSpec with MustMatchers {
  private val checkout = Checkout(
    "Apple" -> "0.6",
    "Orange" -> "0.25"
  )

  "Unknown items are free" in {
    checkout.billFor("Kitten") mustEqual Price("0.00")
  }

  "We know apples from oranges" in {
    checkout.billFor("Apple") mustEqual Price("0.60")
    checkout.billFor("Orange") mustEqual Price("0.25")
  }

  "We can tally up the total cost of a purchase of known and unknown items" in {
    checkout.billFor("Apple", "Apple", "Orange", "Apple", "Lightsaber") mustEqual Price("2.05")
  }

  "Item names are case insensitive" in {
    Checkout(
      "aPPLe" -> "0.6",
      "OraNgE" -> "0.25"
    ).billFor("aPpLe", "APPLE", "OrAnGe", "applE") mustEqual Price("2.05")
  }

  "We can support special offers on single items" in {
    val discountCheckout = checkout.withOffers(
      Offer("Apple", Price("0.80"), buyOneGetOneFree),
      Offer("Orange", Price("0.50"), threeForTwo)
    )

    discountCheckout.billFor("Apple") mustEqual Price("0.60")
    discountCheckout.billFor("Apple", "Apple") mustEqual Price("0.80")
    discountCheckout.billFor("Apple", "Apple", "Apple") mustEqual Price("1.40")

    val eightOranges = List.fill(8)("Orange")
    discountCheckout.billFor(eightOranges:_*) mustEqual Price("1.50")
  }
}