package `in`.emmoney.app.homeActivity.domain.models

import com.google.gson.annotations.SerializedName

data class SchemeDetailed(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("nav")
	val nav: String? = null
)

data class Meta(

	@field:SerializedName("scheme_category")
	val schemeCategory: String? = null,

	@field:SerializedName("scheme_name")
	val schemeName: String? = null,

	@field:SerializedName("fund_house")
	val fundHouse: String? = null,

	@field:SerializedName("scheme_code")
	val schemeCode: Int? = null,

	@field:SerializedName("scheme_type")
	val schemeType: String? = null
)
