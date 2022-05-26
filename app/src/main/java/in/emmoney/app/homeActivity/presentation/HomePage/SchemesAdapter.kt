package `in`.emmoney.app.homeActivity.presentation.homePage

import `in`.emmoney.app.R
import `in`.emmoney.app.homeActivity.domain.models.AllSchemesEntity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class SchemesAdapter(val context: Context, val listener: ISchemesAdapter) :
    RecyclerView.Adapter<SchemesAdapter.SchemeViewHolder>() {

    private val allSchemes = ArrayList<AllSchemesEntity>()

    inner class SchemeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.fund_title)
        val cardView: MaterialCardView = itemView.findViewById(R.id.material_card_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchemeViewHolder {
        val viewHolder = SchemeViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_scheme, parent, false)
        )
        viewHolder.cardView.setOnClickListener {
            listener.onItemClicked(allSchemes[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: SchemeViewHolder, position: Int) {
        holder.textView.text = allSchemes[position].schemeName
    }

    override fun getItemCount(): Int {
        return allSchemes.size
    }

    fun updateList(newList: List<AllSchemesEntity>){
        allSchemes.clear()
        allSchemes.addAll(newList)

        notifyDataSetChanged()
    }
}

interface ISchemesAdapter {
    fun onItemClicked(scheme: AllSchemesEntity)
}