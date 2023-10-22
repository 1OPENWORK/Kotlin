import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stack.open_work_mobile.R

class Notify(private val itemList: MutableList<String>) : RecyclerView.Adapter<Notify.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_notify_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemText = itemList[position]
        holder.textItem.text = itemText

        // Configure outros elementos do layout aqui, se necessário
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textItem: TextView = itemView.findViewById(R.id.textItem)

        // Adicione outras referências a elementos do layout aqui, se necessário
    }

    // Método para adicionar um novo item à lista
    fun addItem(item: String) {
        itemList.add(item)
        notifyDataSetChanged() // Atualiza a RecyclerView para refletir a mudança
    }

    // Método para remover um item da lista
    fun removeItem(position: Int) {
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }
}
