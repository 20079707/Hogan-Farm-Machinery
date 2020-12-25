package org.wit.hogan_farm_machinery.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.tractor_card.view.*
import org.wit.hogan_farm_machinery.R
import org.wit.hogan_farm_machinery.models.TractorModel

class TractorAdapter constructor(private var tractors: List<TractorModel>) :
    RecyclerView.Adapter<TractorAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.tractor_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val tractor = tractors[holder.adapterPosition]
        holder.bind(tractor)
    }

    override fun getItemCount(): Int = tractors.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(tractor: TractorModel) {
            itemView.tractorMake.text = tractor.make
            itemView.tractorModel.text = tractor.model
        }
    }
}
