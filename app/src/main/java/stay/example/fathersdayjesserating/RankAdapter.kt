package stay.example.fathersdayjesserating

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.core.Amplify
import stay.amplifyframework.datastore.generated.model.HotModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.fathersdayjesserating.R
import kotlinx.android.synthetic.main.item_rank.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class RankAdapter: RecyclerView.Adapter<RankAdapter.RankViewHolder>() {

    private var items = mutableListOf<Rank>()

    class RankViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val image = itemView.recyclerPFP
        val btnLike = itemView.thumbUp
        val btnDislike = itemView.thumbDown

        fun bind(todo: Rank) {
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);

            Glide.with(itemView.context)
                .load(todo.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .useAnimationPool(true)
                .fitCenter()
                .into(image)

            itemView.score.setText(todo.score)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        return RankViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rank, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        when(holder){
            is RankViewHolder ->{
                holder.bind(items.get(position))
            }

        }
        val btnLike = holder.btnLike
        val btnDislike = holder.btnDislike
        btnLike.setOnClickListener{
            if (!items.get(position).likeOn) {
                btnDislike.setBackgroundResource(R.drawable.downthumboff)
                btnLike.setBackgroundResource(R.drawable.upthumbon)

                if (items.get(position).dislikeOn) {
                    CoroutineScope(IO).launch {
                        updateScore(items.get(position).id, +2)
                    }
                    val newScore = items.get(position).score.toInt() + 2
                    items.get(position).score = newScore.toString()
                } else {
                    CoroutineScope(IO).launch {
                        updateScore(items.get(position).id, +1)
                    }
                    val newScore = items.get(position).score.toInt() + 1
                    items.get(position).score = newScore.toString()
                }

                items.get(position).likeOn = true
                items.get(position).dislikeOn = false

                notifyDataSetChanged()
            }else if (items.get(position).likeOn) {
                holder.btnLike.setBackgroundResource(R.drawable.upthumboff)
                items.get(position).likeOn = false

                CoroutineScope(IO).launch {
                    updateScore(items.get(position).id, -1)
                }
                val newScore = items.get(position).score.toInt() - 1
                items.get(position).score = newScore.toString()

                notifyDataSetChanged()
            }
        }
        holder.btnDislike.setOnClickListener{
            if (!items.get(position).dislikeOn) {
                holder.btnDislike.setBackgroundResource(R.drawable.downthumbon)
                holder.btnLike.setBackgroundResource(R.drawable.upthumboff)

                if (items.get(position).likeOn) {
                    CoroutineScope(IO).launch {
                        updateScore(items.get(position).id, -2)
                    }
                    val newScore = items.get(position).score.toInt() - 2
                    items.get(position).score = newScore.toString()
                } else {
                    CoroutineScope(IO).launch {
                        updateScore(items.get(position).id, -1)
                    }
                    val newScore = items.get(position).score.toInt() - 1
                    items.get(position).score = newScore.toString()
                }

                items.get(position).dislikeOn = true
                items.get(position).likeOn = false

                notifyDataSetChanged()
            }else if (items.get(position).dislikeOn) {
                holder.btnDislike.setBackgroundResource(R.drawable.downthumboff)
                items.get(position).dislikeOn = false

                CoroutineScope(IO).launch {
                    updateScore(items.get(position).id, 1)
                }
                val newScore = items.get(position).score.toInt() + 1
                items.get(position).score = newScore.toString()

                notifyDataSetChanged()
            }
        }
    }

    fun submitList(todoList: MutableList<Rank>) {
        items = todoList
    }

    fun removeEverything(){
        items.clear()
    }

    override fun getItemCount(): Int {
        if (items != null) {
            return items.size
        }else {
            return 0
        }
    }


    private suspend fun updateScore(updatedItem: String, points: Int) {
        var hotModel = withContext(CoroutineScope(IO).coroutineContext) {readById(updatedItem)}

        val updatedPoints: Int = hotModel.points.toInt() + points

        println(hotModel.id)

        val updatedModel = hotModel.copyOfBuilder()
            .points("$updatedPoints")
            .build()
        Amplify.DataStore.save(
            updatedModel,
            { success -> Log.i("Amplify", "Updated item: " + success.item())},
            { error -> Log.e("Amplify", "Could not save item to DataStore", error) }
        )
    }

    private suspend fun readById(InputID: String): HotModel = suspendCoroutine { cont ->
        var model: HotModel? = null
        println(InputID)
        Amplify.DataStore.query(
            HotModel::class.java,
            { items ->
                while (items.hasNext()) {
                    val item = items.next()
                    if (item.id == InputID) {
                        Log.i("Amplify", "Queried item: " + item.id)
                        cont.resume(item)
                    }
                }
            },
            { failure -> Log.e("Tutorial", "Could not query DataStore", failure) }
        )
    }

}