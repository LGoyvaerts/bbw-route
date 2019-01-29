package ch.ti8m.gol.bbw_route.presentation

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import ch.ti8m.gol.bbw_route.R
import ch.ti8m.gol.bbw_route.data.ConnectionDetailDataAdapter
import ch.ti8m.gol.bbw_route.databinding.ActivityConnectionDetailBinding
import ch.ti8m.gol.bbw_route.domain.abstraction.connectiondetail.DetailPresenter
import ch.ti8m.gol.bbw_route.domain.abstraction.connectiondetail.DetailPresenterImpl
import ch.ti8m.gol.bbw_route.domain.abstraction.connectiondetail.DetailView
import ch.ti8m.gol.bbw_route.domain.entity.opendata.Connection
import ch.ti8m.gol.bbw_route.domain.entity.opendata.Section
import timber.log.Timber
import java.util.*

class ConnectionDetailActivity : AppCompatActivity(), DetailView {

    lateinit var connectionDetailDataAdapter: ConnectionDetailDataAdapter
    private lateinit var detailPresenter: DetailPresenter
    lateinit var binding: ActivityConnectionDetailBinding

    companion object {
        fun start(context: Context, currentConnection: String) {
            val starter = Intent(context, ConnectionDetailActivity::class.java)
            starter.putExtra("currentConnection", currentConnection)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_connection_detail)

        connectionDetailDataAdapter = ConnectionDetailDataAdapter(Collections.emptyList())
        detailPresenter = DetailPresenterImpl(this)

        initRecyclerView()

        val connectionString = intent.extras?.getString("currentConnection")
        if (connectionString != null) {
            detailPresenter.parseCurrentConnection(connectionString)
        } else {
            Timber.e("CurrentConnection not found.")
        }

    }

    override fun onLoadSections(sections: List<Section>) {
        connectionDetailDataAdapter.setSections(sections)
    }


    private fun initRecyclerView() {
        val recyclerView = binding.connectionDetailRecyclerview
        recyclerView.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = connectionDetailDataAdapter
    }


}
