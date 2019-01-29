package ch.ti8m.gol.bbw_route.persistence.persistence

import ch.ti8m.gol.bbw_route.domain.entity.SavedLocation
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class SavedLocationDAO(val db: SqlDatabase) {

    val savedLocationParser = SavedLocationParser()

    companion object {
        const val TABLE_NAME = "SavedLocation"
        const val ID = "ID"
        const val NAME = "NAME"
        const val TEMPERATURE = "TEMPERATURE"
        const val DATE = "DATE"
        const val LAT = "LAT"
        const val LON = "LON"
    }

    fun insertSavedLocation(savedLocation: SavedLocation) {
        db.use {
            insert(
                TABLE_NAME,
                ID to 1L,
                NAME to savedLocation.name,
                TEMPERATURE to savedLocation.temperature,
                DATE to savedLocation.date,
                LAT to savedLocation.lat,
                LON to savedLocation.lon
            )
        }
    }

    fun selectFirstSavedLocation(): SavedLocation? {
        return db.use {
            select(TABLE_NAME).parseOpt(savedLocationParser)
        }
    }

    fun clear() {
        db.use {
            delete(TABLE_NAME)
        }
    }

    class SavedLocationParser : MapRowParser<SavedLocation> {
        override fun parseRow(columns: Map<String, Any?>): SavedLocation {
            return SavedLocation(
                name = columns[NAME] as String,
                temperature = columns[TEMPERATURE] as Double,
                date = columns[DATE] as String,
                lat = columns[LAT] as Double,
                lon = columns[LON] as Double
            )
        }

    }
}