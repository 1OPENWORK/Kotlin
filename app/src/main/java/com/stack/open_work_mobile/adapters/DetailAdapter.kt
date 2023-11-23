import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.stack.open_work_mobile.R
import com.stack.open_work_mobile.models.ProjectDetailsModel

class DetailAdapter(private val context: Context, private var item: ProjectDetailsModel) : BaseAdapter() {

    override fun getCount(): Int {
        return 1
    }

    override fun getItem(position: Int): Any {
        return item
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_detail, parent, false)

        val imageCompany: ShapeableImageView = view.findViewById(R.id.image_company)
        val titleProject: TextView = view.findViewById(R.id.view_project_name)
        val nameCompany: TextView = view.findViewById(R.id.company_name)
        val descriptionProject: TextView = view.findViewById(R.id.view_detail_company)
        val maxDevelopers: TextView = view.findViewById(R.id.view_contribu)
        val timeExpected: TextView = view.findViewById(R.id.date_end)
        val value: TextView = view.findViewById(R.id.value)


        Glide.with(view)
            .load(item.imageCompany)
            .into(imageCompany)
        titleProject.text = item.title
        nameCompany.text = item.nameCompany
        descriptionProject.text = item.description
        maxDevelopers.text = item.maxDevelopers.toString()
        timeExpected.text = item.timeExpected.toString()
        value.text = item.value.toString()

        val gridView: GridView = view.findViewById(R.id.grid_tools)
        val toolsAdapter = ToolAdapter(context, item.tools)
        gridView.adapter = toolsAdapter
        notifyDataSetChanged()
        return view
    }
}