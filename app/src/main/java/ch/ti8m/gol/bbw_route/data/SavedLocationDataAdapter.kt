package ch.ti8m.gol.bbw_route.data

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ch.ti8m.gol.bbw_route.R
import ch.ti8m.gol.bbw_route.domain.entity.SavedLocation

class SavedLocationDataAdapter(private var savedLocations: List<SavedLocation>) :
    RecyclerView.Adapter<SavedLocationDataAdapter.ViewHolder>() {

    fun setSavedLocations(savedLocations: List<SavedLocation>) {
        this.savedLocations = savedLocations
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.saved_location_card_row, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return savedLocations.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentSavedLocation = savedLocations[position]
        viewHolder.location_name.text = currentSavedLocation.name

        val locationLatText = "Latitude: ${currentSavedLocation.lat}"
        viewHolder.location_lat.text = locationLatText

        val locationLonText = "Longitude: ${currentSavedLocation.lon}"
        viewHolder.location_lon.text = locationLonText

        val locationTemperatureText = "Temperature: ${currentSavedLocation.temperature}"
        viewHolder.location_temperature.text = locationTemperatureText

        val locationDateText = "Date: ${currentSavedLocation.date}"
        viewHolder.location_date.text = locationDateText
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal val location_name: TextView
        internal val location_lat: TextView
        internal val location_lon: TextView
        internal val location_temperature: TextView
        internal val location_date: TextView


        init {
            location_name = view.findViewById(R.id.saved_location_name_textview)
            location_lat = view.findViewById(R.id.saved_location_lat_textview)
            location_lon = view.findViewById(R.id.saved_location_lon_textview)
            location_temperature = view.findViewById(R.id.saved_location_temperature_textview)
            location_date = view.findViewById(R.id.saved_location_date_textview)
        }
    }
}