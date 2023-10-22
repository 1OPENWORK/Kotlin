package com.stack.open_work_mobile.activities.Notify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stack.open_work_mobile.R
import com.stack.open_work_mobile.activities.lay_home.HomeActivity
import com.stack.open_work_mobile.adapters.NotifyAdapter
import com.stack.open_work_mobile.models.NotificationItem

class NotificationActivity : AppCompatActivity() {
    private lateinit var itemList: MutableList<NotificationItem>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotifyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val btn = findViewById<View>(R.id.arrow_back)
        btn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        itemList = mutableListOf(
            NotificationItem(
                "Seu pagamento no valor de R\$1.500 do projeto XPTO ja esta la na sua carteira, clique e confira.",
                R.drawable.cart
            ),
            NotificationItem(
                "Adicione algumas informações e melhore ainda mais seu perfil.",
                R.drawable.user
            ),
            NotificationItem(
                "Adicione algumas informações e melhore ainda mais seu perfil.",
                R.drawable.user
            )
        )


        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Crie e defina o adaptador para a RecyclerView
        adapter = NotifyAdapter(itemList)
        recyclerView.adapter = adapter

        // Crie um ItemTouchHelper para lidar com os gestos de deslizar
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false // Não precisamos disso para deslizar para excluir
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Chamado quando o item é deslizado para a esquerda ou direita
                val position = viewHolder.adapterPosition
                val removedItem = itemList.removeAt(position)
                adapter.notifyItemRemoved(position)

                // Você pode adicionar código aqui para fazer qualquer outra coisa com o item removido,
                // como exibir uma mensagem de desfazer ou excluí-lo permanentemente.
            }
        })

        // Vincule o ItemTouchHelper à RecyclerView
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}