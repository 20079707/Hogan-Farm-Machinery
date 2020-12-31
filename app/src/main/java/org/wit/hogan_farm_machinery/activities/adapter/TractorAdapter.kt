package org.wit.hogan_farm_machinery.activities.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.advert_card.view.*
import org.wit.hogan_farm_machinery.R
import org.wit.hogan_farm_machinery.helpers.readImageFromPath
import org.wit.hogan_farm_machinery.models.TractorModel

interface TractorListener {
    fun onTractorClick(tractor: TractorModel)
}

class TractorAdapter constructor(private var tractors: List<TractorModel>, private val listener: TractorListener) : RecyclerView.Adapter<TractorAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.advert_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val tractor = tractors[holder.adapterPosition]
        holder.bind(tractor, listener)
    }

    override fun getItemCount(): Int = tractors.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(tractor: TractorModel, listener: TractorListener) {
            itemView.tractorMake.text = tractor.make
            itemView.tractorPrice.text = tractor.price
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, tractor.image))
            itemView.setOnClickListener { listener.onTractorClick(tractor) }
        }
    }
}

class TractorDisplayAdapter constructor(private var tractors: List<TractorModel>, private val listener: TractorListener) : RecyclerView.Adapter<TractorDisplayAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.advert_home_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val tractor = tractors[holder.adapterPosition]
        holder.bind(tractor, listener)
    }

    override fun getItemCount(): Int = tractors.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(tractor: TractorModel, listener: TractorListener) {
            itemView.tractorMake.text = tractor.make
            itemView.tractorPrice.text = tractor.price
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, tractor.image))
            itemView.setOnClickListener { listener.onTractorClick(tractor) }
        }
    }
}
