package ch.ti8m.gol.bbw_route.presentation.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.ti8m.gol.bbw_route.data.SavedLocationDataAdapter
import ch.ti8m.gol.bbw_route.databinding.FragmentHistoryBinding
import ch.ti8m.gol.bbw_route.persistence.repository.SavedLocationRepository
import ch.ti8m.gol.bbw_route.presentation.App
import java.util.*


class TabHistory : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var binding: FragmentHistoryBinding
    lateinit var savedLocationDataAdapter: SavedLocationDataAdapter
    private lateinit var savedLocationRepository: SavedLocationRepository
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    companion object {

        fun newInstance(): TabHistory {
            val args: Bundle = Bundle()

            val fragment = TabHistory()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedLocationRepository = App.savedLocationRepository
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        savedLocationDataAdapter = SavedLocationDataAdapter(Collections.emptyList())

        initViews()

        return binding.root
    }

    private fun initViews() {
        initRecyclerView()
        dispatchRefresh()
    }

    private fun initRecyclerView() {
        val recyclerView = binding.historySavedLocationsRecyclerview
        recyclerView.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = savedLocationDataAdapter

        //Set SwipeRefreshLayout
        swipeRefreshLayout = binding.historyRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.post {
            run{
                dispatchRefresh()
            }
        }

    }

    private fun loadSavedLocations() {
        val savedLocations = savedLocationRepository.getSavedLocations()
        if (!savedLocations.isEmpty()) {
            savedLocationDataAdapter.setSavedLocations(savedLocations)
        } else {
            savedLocationDataAdapter.setSavedLocations(Collections.emptyList())
        }
    }

    private fun dispatchRefresh() {
        binding.historyRefreshLayout.isRefreshing = true

        loadSavedLocations()

        binding.historyRefreshLayout.isRefreshing = false
    }

    override fun onRefresh() {
        dispatchRefresh()
    }


}
