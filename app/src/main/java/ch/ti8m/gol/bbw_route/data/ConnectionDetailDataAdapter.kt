package ch.ti8m.gol.bbw_route.data

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ch.ti8m.gol.bbw_route.R
import ch.ti8m.gol.bbw_route.domain.entity.opendata.Journey
import ch.ti8m.gol.bbw_route.domain.entity.opendata.Section
import java.text.SimpleDateFormat
import java.util.*

class ConnectionDetailDataAdapter(private var sections: List<Section>) :
    RecyclerView.Adapter<ConnectionDetailDataAdapter.ViewHolder>() {

    fun setSections(sections: List<Section>) {
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

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentSection: Section = sections[position]
        val localDateFormat = SimpleDateFormat("HH:mm:ss", Locale.GERMANY)

        val departureTimeDate = DateUtils.parse(currentSection.departure.departure!!)
        val departureTime = localDateFormat.format(departureTimeDate)
        viewHolder.section_departure_time.text = departureTime

        val departureStationName = currentSection.departure.station.name
        viewHolder.section_departure_name.text = departureStationName

        if (currentSection.departure.platform != null) {
            viewHolder.section_departure_platform.text = currentSection.departure.platform
        } else {
            viewHolder.section_departure_platform.text = ""
        }

        if (currentSection.journey != null) {
            val currentJourney: Journey = currentSection.journey
            if (currentJourney.category == "BUS") {
                val movementType = currentJourney.category + " " + currentJourney.number
                viewHolder.section_movement_type.text = movementType
            } else {
                viewHolder.section_movement_type.text = currentJourney.name
            }
        } else {
            val walk = "Walk"
            viewHolder.section_movement_type.text = walk
        }

        val arrivalTimeDate = DateUtils.parse(currentSection.arrival.arrival!!)
        val arrivalTime = localDateFormat.format(arrivalTimeDate)
        viewHolder.section_arrival_time.text = arrivalTime

        val arrivalStationName = currentSection.arrival.station.name
        viewHolder.section_arrival_name.text = arrivalStationName

        if (currentSection.arrival.platform != null) {
            viewHolder.section_arrival_platform.text = currentSection.arrival.platform
        } else {
            viewHolder.section_arrival_platform.text = ""
        }
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