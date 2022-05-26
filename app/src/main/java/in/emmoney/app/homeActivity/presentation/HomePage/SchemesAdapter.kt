package `in`.emmoney.app.homeActivity.presentation.HomePage

import `in`.emmoney.app.R
import `in`.emmoney.app.homeActivity.domain.models.AllSchemesEntity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SchemesAdapter(val context: Context) :
    RecyclerView.Adapter<SchemesAdapter.SchemeViewHolder>() {

    val allSchemes = ArrayList<AllSchemesEntity>()

    inner class SchemeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.fund_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchemeViewHolder {
        val viewHolder = SchemeViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_scheme, parent, false)
        )
        return viewHolder
    }

    override fun onBindViewHolder(holder: SchemeViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return allSchemes.size
    }
}

interface ISchemesAdapter {
    fun onItemClicked(scheme: AllSchemesEntity)
}