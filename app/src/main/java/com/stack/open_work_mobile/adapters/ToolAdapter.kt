import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.stack.open_work_mobile.R
import com.stack.open_work_mobile.models.ToolsModel

class ToolAdapter(private val context: Context, private val toolsList: List<ToolsModel>) : BaseAdapter() {

    override fun getCount(): Int {
        return toolsList.size
    }

    override fun getItem(position: Int): Any {
        return toolsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_tool, parent, false)
        val toolName: TextView = view.findViewById(R.id.tool_name)
        val currentTool = toolsList[position]
        toolName.text = currentTool.name
        return view
    }
}