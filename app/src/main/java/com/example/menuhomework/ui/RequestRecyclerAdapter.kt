package com.example.menuhomework.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.menuhomework.R
import com.example.menuhomework.databinding.ItemBinding
import com.example.menuhomework.model.database.WeatherEntity

class RequestRecyclerAdapter(
    private val activity: Activity,
    var itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<RequestRecyclerAdapter.ViewHolder>() {

    var weathers: MutableList<WeatherEntity> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var menuPosition = 0
        private set

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(weathers[holder.adapterPosition])

        holder.setOnClickListener(
            itemClickListener,
            weathers[holder.adapterPosition]
        )

        holder.cardView.setOnLongClickListener { view: View? ->
            menuPosition = holder.adapterPosition
            false
        }
        activity.registerForContextMenu(holder.cardView)
    }

    class ViewHolder(var cardView: View) : RecyclerView.ViewHolder(cardView) {

        private var ui: ItemBinding = ItemBinding.bind(itemView)

        fun setOnClickListener(listener: OnItemClickListener, weather: WeatherEntity) {
            this.cardView.setOnClickListener {
                val adapterPosition = adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION)
                    listener.onItemClick(it, weather)
            }
        }

        fun bind(weather: WeatherEntity) {
            ui.textRequestCity.text = weather.city
            ui.textRequestDate.text = weather.date.toString()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, element: WeatherEntity)
    }

    override fun getItemCount(): Int = weathers.size
}
