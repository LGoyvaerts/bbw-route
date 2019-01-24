package ch.ti8m.gol.bbw_route.presentation.fragments


import android.os.Bundle
import android.support.v4.app.Fragment


class TabHistory : Fragment() {

    companion object {

        fun newInstance(): TabHistory {
            val args: Bundle = Bundle()

            val fragment = TabHistory()
            fragment.arguments = args
            return fragment
        }
    }


}
