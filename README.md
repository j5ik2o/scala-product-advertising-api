# scala-product-advertising-api

This product is Scala library for Amazon Product Advertising API. 

## Installation

Add the following to your sbt build (Scala 2.10.x, and Scala 2.11.x, and Scala 2.12.x):

### Release Version

```scala
resolvers += "Sonatype OSS Release Repository" at "https://oss.sonatype.org/content/repositories/releases/"

libraryDependencies += "com.github.j5ik2o" %% "scala-product-advertising-api-core" % "1.0.0"
```

### Snapshot Version

```scala
resolvers += "Sonatype OSS Snapshot Repository" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies += "com.github.j5ik2o" %% "scala-product-advertising-api-core" % "1.0.1-SNAPSHOT"
```

## Usage

```scala
implicit val system = ActorSytem()

val config = ProductAdvertisingConfig(
endPoint = "webservices.amazon.co.jp",
awsAccessKeyId = ???, // set your awsAccessKeyId
awsSecretAccessKey = ??? // set your  awsSecretKey
)

val productAdvertisingAPI = new ProductAdvertisingAPI(config)

val request = ItemSearchRequest(
  SearchIndex = Some("PCHardware"),
  Keywords = Some("macbook"),
  ResponseGroup = Seq("Images", "ItemAttributes", "Offers"),
  Sort = Some("price")
)
val future = productAdvertisingAPI.itemSearch(request)
// If the value is obtained from Future, call Await.result. 
// You should call it as necessary as minimum to avoid frequent blocking.
val result = Await.result(future, Duration.Inf) 
```
