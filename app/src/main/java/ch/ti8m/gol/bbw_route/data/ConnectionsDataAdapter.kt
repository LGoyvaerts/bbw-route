package ch.ti8m.gol.bbw_route.data

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ch.ti8m.gol.bbw_route.R
import ch.ti8m.gol.bbw_route.domain.entity.opendata.Connection
import java.text.SimpleDateFormat
import java.util.*

class ConnectionsDataAdapter(private var connections: List<Connection>) :
    RecyclerView.Adapter<ConnectionsDataAdapter.ViewHolder>() {

    fun setConnections(connections: List<Connection>){
        this.connections = connections
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.connection_card_row, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return connections.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentConnection = connections[position]
        val fromText = "From: ${currentConnection.from!!.station.name}"
        viewHolder.connection_from.text = fromText

        val toText = "To: ${currentConnection.to!!.station.name}"
        viewHolder.connection_to.text = toText

        val localDateFormat = SimpleDateFormat("HH:mm:ss", Locale.GERMANY)

        val starttimeDate = DateUtils.parse(currentConnection.from.departure!!)
        val starttime = localDateFormat.format(starttimeDate)
        val starttimeText = "Start: $starttime"
        viewHolder.connection_starttime.text = starttimeText

        val endtimeDate = DateUtils.parse(currentConnection.from.departure)
        val endtime = localDateFormat.format(endtimeDate)
        val endtimeText = "Arrival: $endtime"
        viewHolder.connection_endtime.text = endtimeText

        val durationText = "Duration: ${DateUtils.formatDuration(currentConnection.duration)}"
        viewHolder.connection_duration.text = durationText

        val transfersText = "Transfers: ${currentConnection.products.size}"
        viewHolder.connection_transfers.text = transfersText
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal val connection_from: TextView
        internal val connection_to: TextView
        internal val connection_starttime: TextView
        internal val connection_endtime: TextView
        internal val connection_duration: TextView
        internal val connection_transfers: TextView


        init {
            connection_from = view.findViewById(R.id.connection_card_row_from_textview)
            connection_to = view.findViewById(R.id.connection_card_row_to_textview)
            connection_starttime = view.findViewById(R.id.connection_card_row_starttime_textview)
            connection_endtime = view.findViewById(R.id.connection_card_row_endtime_textview)
            connection_duration = view.findViewById(R.id.connection_card_row_duration_textview)
            connection_transfers = view.findViewById(R.id.connection_card_row_transfers_textview)
        }
    }
}