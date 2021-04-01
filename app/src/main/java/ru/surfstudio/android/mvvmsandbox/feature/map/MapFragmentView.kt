package ru.surfstudio.android.mvvmsandbox.feature.map

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

class MapFragmentView : Fragment() {

    @Inject
    lateinit var viewModel: MapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapScreenConfigurator().inject(this)

        val resultLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                viewModel.permissionUpdated(isGranted)
            }
        resultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_map, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val statusTv = view.findViewById<TextView>(R.id.map_status_tv)
        lifecycleScope.launch {
            viewModel.permissionState.collect { status: PermissionStatus ->
                statusTv.text = status.name
            }
        }
    }
}