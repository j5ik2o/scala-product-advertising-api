package com.github.j5ik2o.apa

import java.util

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{ Sink, Source }
import com.amazon.advertising.api.sample.SignedRequestsHelper
import shapeless.ops.record.ToMap
import shapeless.{ HList, LabelledGeneric }

import scala.collection.JavaConverters._
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.xml.XML
import scalaxb.ParserFailure

object ProductAdvertisingAPI {

  implicit class ToMapOps[A](val a: A) extends AnyVal {

    import shapeless._
    import ops.record._

    def toMap[L <: HList](implicit
      gen: LabelledGeneric.Aux[A, L],
      tmr: ToMap[L]): Map[String, String] = {
      val m: Map[tmr.Key, tmr.Value] = tmr(gen.to(a))
      m.filterNot {
        case (_: Symbol, v: Option[_]) => v == None
        case _ => false
      }.map {
        case (k: Symbol, v: List[_]) => k.name -> v.mkString(",")
        case (k: Symbol, v: Int) => k.name -> v.toString
        case (k: Symbol, v: String) => k.name -> v
        case (k: Symbol, v: Option[_]) => k.name -> v.map(_.toString).orNull[String]
        case _ => throw new AssertionError
      }
    }
  }

}

case class ProductAdvertisingAPIException(message: String, cause: Throwable)
  extends Exception(message, cause)

class ProductAdvertisingAPI(config: ProductAdvertisingConfig)(implicit system: ActorSystem) {

  import ProductAdvertisingAPI._
  import webservices.amazon.com.AWSECommerceService.n20110801._

  implicit val materializer = ActorMaterializer()

  private val poolClientFlow = Http().cachedHostConnectionPool[Int](config.endPoint)

  private val signedRequestsHelper = SignedRequestsHelper.getInstance(
    config.endPoint,
    config.awsAccessKeyId,
    config.awsSecretAccessKey
  )

  private val timeout: FiniteDuration = 10.seconds

  def itemSearch(
    request: ItemSearchRequest,
    associateTag: Option[String] = None,
    xmlEscaping: Option[String] = None,
    validate: Option[String] = None
  ): Future[ItemSearchResponse] = {
    val name = request.getClass.getSimpleName.split("Request")(0)
    val params = getParams(name, request, associateTag, xmlEscaping, validate)
    val url = signedRequestsHelper.sign(params)
    val future = Source.single(HttpRequest(uri = url) -> 1).via(poolClientFlow).runWith(Sink.head)
    future.flatMap {
      case (triedResponse, _) =>
        triedResponse.map { response =>
          response.entity.toStrict(timeout).map {
            _.data
          }.map(_.utf8String).map { s =>
            scalaxb.fromXML[ItemSearchResponse](XML.loadString(s))
          }.recoverWith {
            case ex: ParserFailure =>
              Future.failed(ProductAdvertisingAPIException("Occurred Error", ex))
          }
        }.get
    }
  }

  def itemLookup(
    request: ItemLookupRequest,
    associateTag: Option[String] = None,
    xmlEscaping: Option[String] = None,
    validate: Option[String] = None
  ): Future[ItemLookupResponse] = {
    val name = request.getClass.getSimpleName.split("Request")(0)
    val params = getParams(name, request, associateTag, xmlEscaping, validate)
    val url = signedRequestsHelper.sign(params)
    val future = Source.single(HttpRequest(uri = url) -> 1).via(poolClientFlow).runWith(Sink.head)
    future.flatMap {
      case (triedResponse, _) =>
        triedResponse.map { response =>
          response.entity.toStrict(timeout).map {
            _.data
          }.map(_.utf8String).map { s =>
            scalaxb.fromXML[ItemLookupResponse](XML.loadString(s))
          }.recoverWith {
            case ex: scalaxb.ParserFailure =>
              Future.failed(ProductAdvertisingAPIException("Occurred Error", ex))
          }
        }.get
    }
  }

  def browseNodeLookup(
    request: BrowseNodeLookupRequest,
    associateTag: Option[String] = None,
    xmlEscaping: Option[String] = None,
    validate: Option[String] = None
  ): Future[BrowseNodeLookupResponse] = {
    val name = request.getClass.getSimpleName.split("Request")(0)
    val params = getParams(name, request, associateTag, xmlEscaping, validate)
    val url = signedRequestsHelper.sign(params)
    val future = Source.single(HttpRequest(uri = url) -> 1).via(poolClientFlow).runWith(Sink.head)
    future.flatMap {
      case (triedResponse, _) =>
        triedResponse.map { response =>
          response.entity.toStrict(timeout).map {
            _.data
          }.map(_.utf8String).map { s =>
            scalaxb.fromXML[BrowseNodeLookupResponse](XML.loadString(s))
          }.recoverWith {
            case ex: scalaxb.ParserFailure =>
              Future.failed(ProductAdvertisingAPIException("Occurred Error", ex))
          }
        }.get
    }
  }

  def similarityLookup(
    request: SimilarityLookupRequest,
    associateTag: Option[String] = None,
    xmlEscaping: Option[String] = None,
    validate: Option[String] = None
  ): Future[SimilarityLookupResponse] = {
    val name = request.getClass.getSimpleName.split("Request")(0)
    val params = getParams(name, request, associateTag, xmlEscaping, validate)
    val url = signedRequestsHelper.sign(params)
    val future = Source.single(HttpRequest(uri = url) -> 1).via(poolClientFlow).runWith(Sink.head)
    future.flatMap {
      case (triedResponse, _) =>
        triedResponse.map { response =>
          response.entity.toStrict(timeout).map {
            _.data
          }.map(_.utf8String).map { s =>
            scalaxb.fromXML[SimilarityLookupResponse](XML.loadString(s))
          }.recoverWith {
            case ex: scalaxb.ParserFailure =>
              Future.failed(ProductAdvertisingAPIException("Occurred Error", ex))
          }
        }.get
    }
  }

  def cartCreate(
    request: CartCreateRequest,
    associateTag: Option[String] = None,
    xmlEscaping: Option[String] = None,
    validate: Option[String] = None
  ): Future[CartCreateResponse] = {
    val name = request.getClass.getSimpleName.split("Request")(0)
    val params = getParams(name, request, associateTag, xmlEscaping, validate)
    val url = signedRequestsHelper.sign(params)
    val future = Source.single(HttpRequest(uri = url) -> 1).via(poolClientFlow).runWith(Sink.head)
    future.flatMap {
      case (triedResponse, _) =>
        triedResponse.map { response =>
          response.entity.toStrict(timeout).map {
            _.data
          }.map(_.utf8String).map { s =>
            scalaxb.fromXML[CartCreateResponse](XML.loadString(s))
          }.recoverWith {
            case ex: scalaxb.ParserFailure =>
              Future.failed(ProductAdvertisingAPIException("Occurred Error", ex))
          }
        }.get
    }
  }

  def cartGet(
    request: CartGetRequest,
    associateTag: Option[String] = None,
    xmlEscaping: Option[String] = None,
    validate: Option[String] = None
  ): Future[CartGetResponse] = {
    val name = request.getClass.getSimpleName.split("Request")(0)
    val params = getParams(name, request, associateTag, xmlEscaping, validate)
    val url = signedRequestsHelper.sign(params)
    val future = Source.single(HttpRequest(uri = url) -> 1).via(poolClientFlow).runWith(Sink.head)
    future.flatMap {
      case (triedResponse, _) =>
        triedResponse.map { response =>
          response.entity.toStrict(timeout).map {
            _.data
          }.map(_.utf8String).map { s =>
            scalaxb.fromXML[CartGetResponse](XML.loadString(s))
          }.recoverWith {
            case ex: scalaxb.ParserFailure =>
              Future.failed(ProductAdvertisingAPIException("Occurred Error", ex))
          }
        }.get
    }
  }

  def cartAdd(
    request: CartAddRequest,
    associateTag: Option[String] = None,
    xmlEscaping: Option[String] = None,
    validate: Option[String] = None
  ): Future[CartAddResponse] = {
    val name = request.getClass.getSimpleName.split("Request")(0)
    val params = getParams(name, request, associateTag, xmlEscaping, validate)
    val url = signedRequestsHelper.sign(params)
    val future = Source.single(HttpRequest(uri = url) -> 1).via(poolClientFlow).runWith(Sink.head)
    future.flatMap {
      case (triedResponse, _) =>
        triedResponse.map { response =>
          response.entity.toStrict(timeout).map {
            _.data
          }.map(_.utf8String).map { s =>
            scalaxb.fromXML[CartAddResponse](XML.loadString(s))
          }.recoverWith {
            case ex: scalaxb.ParserFailure =>
              Future.failed(ProductAdvertisingAPIException("Occurred Error", ex))
          }
        }.get
    }
  }

  def cartModify(
    request: CartModifyRequest,
    associateTag: Option[String] = None,
    xmlEscaping: Option[String] = None,
    validate: Option[String] = None
  ): Future[CartModifyResponse] = {
    val name = request.getClass.getSimpleName.split("Request")(0)
    val params = getParams(name, request, associateTag, xmlEscaping, validate)
    val url = signedRequestsHelper.sign(params)
    val future = Source.single(HttpRequest(uri = url) -> 1).via(poolClientFlow).runWith(Sink.head)
    future.flatMap {
      case (triedResponse, _) =>
        triedResponse.map { response =>
          response.entity.toStrict(timeout).map {
            _.data
          }.map(_.utf8String).map { s =>
            scalaxb.fromXML[CartModifyResponse](XML.loadString(s))
          }.recoverWith {
            case ex: scalaxb.ParserFailure =>
              Future.failed(ProductAdvertisingAPIException("Occurred Error", ex))
          }
        }.get
    }
  }

  def cartClear(
    request: CartClearRequest,
    associateTag: Option[String] = None,
    xmlEscaping: Option[String] = None,
    validate: Option[String] = None
  ): Future[CartClearResponse] = {
    val name = request.getClass.getSimpleName.split("Request")(0)
    val params = getParams(name, request, associateTag, xmlEscaping, validate)
    val url = signedRequestsHelper.sign(params)
    val future = Source.single(HttpRequest(uri = url) -> 1).via(poolClientFlow).runWith(Sink.head)
    future.flatMap {
      case (triedResponse, _) =>
        triedResponse.map { response =>
          response.entity.toStrict(timeout).map {
            _.data
          }.map(_.utf8String).map { s =>
            scalaxb.fromXML[CartClearResponse](XML.loadString(s))
          }.recoverWith {
            case ex: scalaxb.ParserFailure =>
              Future.failed(ProductAdvertisingAPIException("Occurred Error", ex))
          }
        }.get
    }
  }

  private def getParams[A, L <: HList](operation: String, request: A,
    associateTag: Option[String],
    xmlEscaping: Option[String],
    validate: Option[String])(implicit gen: LabelledGeneric.Aux[A, L], tmr: ToMap[L]): util.HashMap[String, String] = {
    val params = new util.HashMap[String, String]()
    val defaultMap = Map(
      "Service" -> "AWSECommerceService",
      "Operation" -> operation
    ) ++ request.toMap

    val map1 = associateTag.fold(defaultMap)(v => defaultMap + ("AssociateTag" -> v))
    val map2 = xmlEscaping.fold(map1) { v => map1 + ("XMLEscaping" -> v) }
    val map = validate.fold(map2) { v => map2 + ("Validate" -> v) }.asJava

    params.putAll(map)
    params
  }

}
