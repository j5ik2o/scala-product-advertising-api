package com.github.j5ik2o.apa

import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalatest.FunSpecLike
import org.scalatest.concurrent.ScalaFutures
import webservices.amazon.com.AWSECommerceService.n20110801.ItemSearchRequest
import scala.concurrent.duration._

class ProductAdvertisingAPITest
  extends TestKit(ActorSystem("ProductAdvertisingAPITest"))
    with FunSpecLike with ScalaFutures {

  describe("ProductAdvertisingAPI") {
    it("should be able to get response") {
      val config = ProductAdvertisingConfig(
        endPoint = "webservices.amazon.co.jp",
        awsAccessKeyId = sys.env("AWS_ACCESS_KEY_ID"),
        awsSecretAccessKey = sys.env("AWS_SECRET_ACCESS_KEY"),
        10 seconds
      )
      val api = new ProductAdvertisingAPI(config)
      val request = ItemSearchRequest(SearchIndex = Some("PCHardware"), Keywords = Some("macbook"), ResponseGroup = Seq("Images", "ItemAttributes", "Offers"), Sort = Some("price"))
      val f = api.itemSearch(request, Some("hatenaj5ik2o-22"))
      val r = f.futureValue
      r.Items.foreach { items4 =>
        items4.Item.foreach { item4 =>
          println((item4.ASIN, item4.Offers))
          item4.ItemAttributes.foreach { itemAttribute =>
            println((itemAttribute.Title, itemAttribute.Size))
          }
        }
      }
      println(r)
    }
  }
}
