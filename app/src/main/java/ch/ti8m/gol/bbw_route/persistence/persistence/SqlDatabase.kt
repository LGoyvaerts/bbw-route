package ch.ti8m.gol.bbw_route.persistence.persistence

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class SqlDatabase(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "bbwrouteDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.createTable(
            SavedLocationDAO.TABLE_NAME, true,
            SavedLocationDAO.NAME to TEXT + NOT_NULL,
            SavedLocationDAO.TEMPERATURE to REAL + NOT_NULL,
            SavedLocationDAO.DATE to TEXT + NOT_NULL,
            SavedLocationDAO.LAT to REAL + NOT_NULL,
            SavedLocationDAO.LON to REAL + NOT_NULL
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Here each Upgrade could be handled
    }


}