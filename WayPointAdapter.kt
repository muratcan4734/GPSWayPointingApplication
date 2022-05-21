package com.murat.gps_assignment03

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WayPointAdapter(private val waypointList : List<WayPoint>) : RecyclerView.Adapter<WayPointAdapter.WayPointHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WayPointHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.waypoint_item,parent,false)

        return WayPointHolder(itemView)
    }

    override fun onBindViewHolder(holder: WayPointHolder, position: Int) {
        val currentItem = waypointList[position]

        holder.txtName.setText(currentItem.name_waypoint)
        holder.latitude.setText(currentItem.location_latitude)
        holder.longitude.setText(currentItem.location_longtude)
    }

    override fun getItemCount(): Int {
        return waypointList.size
    }

    class WayPointHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtName: TextView = itemView.findViewById(R.id.txtView_WayPointName)
        val latitude: TextView = itemView.findViewById(R.id.txtView_latitude)
        val longitude: TextView = itemView.findViewById(R.id.txtView_longitude)
    }
}