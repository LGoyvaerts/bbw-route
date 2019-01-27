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
import ch.ti8m.gol.bbw_route.domain.entity.opendata.Connection
import com.google.gson.Gson
import java.util.*

class ConnectionDetailActivity : AppCompatActivity() {

    lateinit var connectionDetailDataAdapter: ConnectionDetailDataAdapter
    private lateinit var currentConnection: Connection
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

        initRecyclerView()

        val gson = Gson()
        val connectionString = intent.extras?.getString("currentConnection")
        currentConnection = gson.fromJson(connectionString, Connection::class.java)

        connectionDetailDataAdapter.setSections(currentConnection.sections)

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
