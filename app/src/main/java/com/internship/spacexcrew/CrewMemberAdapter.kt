package com.internship.spacexcrew

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row.view.*

class CrewMemberAdapter(var crewMembers : ArrayList<CrewMember>) : RecyclerView.Adapter<CrewMemberAdapter.CrewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CrewViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_row, parent, false))

    override fun onBindViewHolder(holder: CrewViewHolder, position: Int) {
        holder.bind(crewMembers[position])
    }

    override fun getItemCount() = crewMembers.size

    public fun updateData(memberslist : ArrayList<CrewMember>){
        crewMembers = memberslist
        notifyDataSetChanged()
    }

    class CrewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(crewMember: CrewMember){
            itemView.tvname?.text = crewMember.name
            itemView.tvagency?.text = crewMember.agency
            itemView.tvwikipedia?.text = crewMember.wikipedia
            itemView.tvstatus?.text = crewMember.status
            Picasso.get().load(crewMember.image).into(itemView.imageViewProfile)
        }

    }
}