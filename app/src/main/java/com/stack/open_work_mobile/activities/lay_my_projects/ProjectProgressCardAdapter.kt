package com.stack.open_work_mobile.activities.lay_my_projects

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.stack.open_work_mobile.R
import com.bumptech.glide.Glide

class ProjectProgressCardAdapter(private val projectProcessList: ArrayList<ProjectProgressCard>) :
    RecyclerView.Adapter<ProjectProgressCardAdapter.MyViewHolder>(),
    Collection<ProjectProgressCard> {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_item_project_progress, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = projectProcessList[position]

        Glide.with(holder.itemView)
            .load(currentItem.imageCompany)
            .into(holder.image)
        holder.title.text = currentItem.titleProject
        holder.subTitle.text = currentItem.nameCompany
        holder.describe.text = currentItem.descriptionProject
        holder.progress.progress = currentItem.progress.toInt()
    }

    override fun getItemCount(): Int {
        return projectProcessList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ShapeableImageView = itemView.findViewById(R.id.profileImageView)
        val title: TextView = itemView.findViewById(R.id.title_project_process)
        val subTitle: TextView = itemView.findViewById(R.id.sub_title_process)
        val describe: TextView = itemView.findViewById(R.id.describe_porcess)
        var progress: ProgressBar = itemView.findViewById(R.id.progressBar)
    }

    override val size: Int
        get() = TODO("Not yet implemented")

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun iterator(): Iterator<ProjectProgressCard> {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<ProjectProgressCard>): Boolean {
        TODO("Not yet implemented")
    }

    override fun contains(element: ProjectProgressCard): Boolean {
        TODO("Not yet implemented")
    }
}