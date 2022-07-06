package stay.example.fathersdayjesserating

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.core.Amplify
import stay.amplifyframework.datastore.generated.model.HotModel
import com.example.fathersdayjesserating.R
import kotlinx.android.synthetic.main.fragmentnewold_layout.*


class FragmentNewOld : Fragment() {

    private lateinit var rankAdapter: RankAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    val rankings: MutableList<Rank> = mutableListOf<Rank>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragmentnewold_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view_Frag.apply {
            layoutManager = LinearLayoutManager(context)
            rankAdapter = RankAdapter()
            adapter = rankAdapter
        }
        addDataSet(rankings)
        detect()
    }


    private fun detect() {
        Amplify.DataStore.query(
            HotModel::class.java,
            { items ->
                while (items.hasNext()) {
                    val item = items.next()
                    rankings.add(Rank(item.id, item.url, item.points, false, false))
                    Log.i("Amplify", "Queried item: " + item.id + " "+ item.url)
                }
                addDataSet(rankings)
            },
            { failure -> Log.e("Tutorial", "Could not query DataStore", failure) }
        )

}

    private fun addDataSet(Rank: MutableList<Rank>){
        activity?.runOnUiThread{
            rankAdapter.submitList(Rank)
            rankAdapter.notifyDataSetChanged()
        }
    }


}