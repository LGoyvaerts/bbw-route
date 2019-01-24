package ch.ti8m.gol.bbw_route.presentation.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.ti8m.gol.bbw_route.databinding.FragmentOverviewBinding


class TabOverview : Fragment() {

    lateinit var binding: FragmentOverviewBinding

    companion object {

        fun newInstance(): TabOverview {
            val args: Bundle = Bundle()

            val fragment = TabOverview()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentOverviewBinding.inflate(inflater, container, false)

        //TODO init RecyclerView DataAdapter
        //TODO initViews()

        return binding.root
    }
}
