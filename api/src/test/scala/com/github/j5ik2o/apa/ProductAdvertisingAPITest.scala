package com.github.j5ik2o.apa

import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalatest.FunSpecLike
import webservices.amazon.com.AWSECommerceService.n20110801.ItemSearchRequest

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class ProductAdvertisingAPITest extends TestKit(ActorSystem("ProductAdvertisingAPITest")) with FunSpecLike {

  describe("ProductAdvertisingAPI") {
    it("should be able to get result") {
      val config = ProductAdvertisingConfig(
        "webservices.amazon.co.jp",
        "AKIAINIBFHW3V2SFIUIQ",
        "aZG8NO/wGC3HqFVUuPXI4dy9xt0uIM+myK6D/JTE"
      )
      val api = new ProductAdvertisingAPI(config)
      val request = ItemSearchRequest(SearchIndex = Some("PCHardware"), Keywords = Some("macbook"), ResponseGroup = Seq("Images", "ItemAttributes", "Offers"), Sort = Some("price"))
      val f = api.itemSearch(request, Some("hatenaj5ik2o-22"))
      val r = Await.result(f, Duration.Inf)
      r.Items.foreach { items4 =>
        items4.Item.foreach { item4 =>
          println(item4.ASIN, item4.Offers)
          item4.ItemAttributes.foreach { itemAttribute =>
            println(itemAttribute.Title, itemAttribute.Size)
          }
        }
      }
      println(r)
    }
    it("test") {
    }
  }
}
