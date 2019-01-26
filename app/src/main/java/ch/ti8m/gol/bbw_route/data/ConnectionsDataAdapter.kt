package ch.ti8m.gol.bbw_route.data

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import ch.ti8m.gol.bbw_route.R

class ConnectionsDataAdapter {

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