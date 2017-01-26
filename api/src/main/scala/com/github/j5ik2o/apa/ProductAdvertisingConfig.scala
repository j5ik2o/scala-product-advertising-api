package com.github.j5ik2o.apa

import scala.concurrent.duration.FiniteDuration

case class ProductAdvertisingConfig(
  endPoint: String,
  awsAccessKeyId: String,
  awsSecretAccessKey: String,
  timeoutForToStrict: FiniteDuration
)

