package `in`.emmoney.app.homeActivity.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "all_schemes_table")
class AllSchemesEntity(
    @PrimaryKey(autoGenerate = false) val schemeCode: Int,
    val schemeName: String) {
}