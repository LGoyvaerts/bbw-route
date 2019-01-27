package ch.ti8m.gol.bbw_route.data

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ch.ti8m.gol.bbw_route.R
import ch.ti8m.gol.bbw_route.domain.entity.opendata.Section

class ConnectionDetailDataAdapter(private var sections:List<Section>): RecyclerView.Adapter<ConnectionDetailDataAdapter.ViewHolder>() {

    fun setSections(sections: List<Section>){
        this.sections = sections
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ConnectionDetailDataAdapter.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.section_card_row, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sections.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal val section_departure_time: TextView
        internal val section_departure_name: TextView
        internal val section_departure_platform: TextView
        internal val section_movement_type: TextView
        internal val section_arrival_time: TextView
        internal val section_arrival_name: TextView
        internal val section_arrival_platform: TextView


        init {
            section_departure_time = view.findViewById(R.id.section_departure_time_textview)
            section_departure_name = view.findViewById(R.id.section_departure_name_textview)
            section_departure_platform = view.findViewById(R.id.section_departure_platform_textview)
            section_movement_type = view.findViewById(R.id.section_movement_type_textview)
            section_arrival_time = view.findViewById(R.id.section_arrival_time_textview)
            section_arrival_name = view.findViewById(R.id.section_arrival_name_textview)
            section_arrival_platform = view.findViewById(R.id.section_arrival_platform_textview)
        }
    }
}