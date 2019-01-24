package ch.ti8m.gol.bbw_route.presentation.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.ti8m.gol.bbw_route.databinding.FragmentHistoryBinding


class TabHistory : Fragment() {

    lateinit var binding: FragmentHistoryBinding

    companion object {

        fun newInstance(): TabHistory {
            val args: Bundle = Bundle()

            val fragment = TabHistory()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        //TODO init RecyclerView DataAdapter
        //TODO initViews()

        return binding.root
    }

}
