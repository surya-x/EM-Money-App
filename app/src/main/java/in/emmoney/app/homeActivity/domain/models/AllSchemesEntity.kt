package `in`.emmoney.app.homeActivity.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "all_schemes_table")
class AllSchemesEntity(

    @field:SerializedName("schemeCode")
    @PrimaryKey(autoGenerate = false)
    val schemeCode: Int,

    @field:SerializedName("schemeName")
    val schemeName: String) {
}