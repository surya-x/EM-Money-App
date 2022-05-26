package `in`.emmoney.app.homeActivity.domain.models

import com.google.gson.annotations.SerializedName

//data class AllSchemes(
//
//	@field:SerializedName("AllSchemes")
//	val allSchemes: List<AllSchemesItem?>? = null
//)

data class AllSchemesItem(

	@field:SerializedName("schemeCode")
	val schemeCode: Int? = null,

	@field:SerializedName("schemeName")
	val schemeName: String? = null
)