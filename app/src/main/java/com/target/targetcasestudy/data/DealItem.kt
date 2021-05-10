package com.target.targetcasestudy.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Products(
  @Json(name = "products")
  val listOfProducts: List<DealItem>
)

@JsonClass(generateAdapter = true)
data class DealItem(
  @Json(name = "id")
  val id: Int,

  @Json(name = "title")
  val title: String,

  @Json(name = "aisle")
  val aisle: String,

  @Json(name = "description")
  val description: String,

  @Json(name = "image_url")
  val image_url: String,

  @Json(name = "regular_price")
  val regular_price: DealItemPrice,

  @Json(name = "sale_price")
  val sale_price: DealItemPrice?
  )

@JsonClass(generateAdapter = true)
data class DealItemPrice(
  @Json(name = "amount_in_cents")
  val amount_in_cents: Int,

  @Json(name = "currency_symbol")
  val currency_symbol: String,

  @Json(name = "display_string")
  val display_string: String
)