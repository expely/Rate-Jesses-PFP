package stay.example.fathersdayjesserating

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fathersdayjesserating.R
import kotlinx.android.synthetic.main.item_todo.*
import kotlinx.android.synthetic.main.item_todo.view.*
import kotlinx.android.synthetic.main.stylelogin.view.*
import okhttp3.internal.notifyAll

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var items = mutableListOf<Todo>()


    class TodoViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val image = itemView.tvTodoImage
        val btnClose = itemView.btncloseViewer

        fun bind(todo: Todo) {
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);

            Glide.with(itemView.context)
                .load(todo.image)
                .into(image)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        when(holder){
            is TodoViewHolder ->{
                holder.bind(items.get(position))
            }

        }
        holder.btnClose.setOnClickListener{
            items.removeAt(position)
            notifyDataSetChanged()
        }
    }

    fun removeEverything(){
        items.clear()
        notifyDataSetChanged()
    }

    fun submitList(todoList: MutableList<Todo>) {
        items = todoList
    }

    override fun getItemCount(): Int {
        return items.size
    }


}
