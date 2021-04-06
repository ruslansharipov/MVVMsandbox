package ru.surfstudio.android.mvvmsandbox.feature.cities

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.surfstudio.android.mvvmsandbox.R
import javax.inject.Inject

class CitiesFragmentView : Fragment() {

    @Inject
    lateinit var viewModel: CitiesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CitiesScreenConfigurator().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_map, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val statusTv = view.findViewById<TextView>(R.id.map_status_tv)
        lifecycleScope.launch {

        }
    }
}