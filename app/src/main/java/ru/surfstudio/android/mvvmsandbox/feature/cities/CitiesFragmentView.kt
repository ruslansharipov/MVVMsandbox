package ru.surfstudio.android.mvvmsandbox.feature.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.surfstudio.android.mvvmsandbox.R
import ru.surfstudio.android.mvvmsandbox.feature.cities.data.PlaceholderState
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
        val errorTv = view.findViewById<TextView>(R.id.cities_error_tv)
        val retryBtn = view.findViewById<Button>(R.id.cities_retry_btn)
        val errorContainer = view.findViewById<View>(R.id.cities_error_container)
        val progressBar = view.findViewById<ProgressBar>(R.id.cities_pb)
        val citiesTv = view.findViewById<TextView>(R.id.cities_tv)

        retryBtn.setOnClickListener { viewModel.retry() }

        viewModel.cities.bindTo { citiesRequest ->
            errorTv.text = when (citiesRequest.load) {
                PlaceholderState.NoInternet -> getString(R.string.error_no_internet)
                PlaceholderState.Error -> getString(R.string.error_other)
                else -> null
            }
            errorContainer.isVisible = citiesRequest.load is PlaceholderState.Error || citiesRequest.load is PlaceholderState.NoInternet
            progressBar.isVisible = citiesRequest.load is PlaceholderState.MainLoading
            citiesTv.isVisible = citiesRequest.load is PlaceholderState.None
            citiesTv.text = citiesRequest.data?.joinToString(
                    separator = ",\n",
                    transform = { it.cityName }
            )
        }
        viewModel.toast.bindTo {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    // TODO вынести в отдельный класс
    fun <T> Channel<T>.bindTo(action: suspend (T) -> Unit) {
        lifecycleScope.launch {
            receiveAsFlow().collect(action)
        }
    }

    // TODO вынести в отдельный класс
    fun <T> Flow<T>.bindTo(action: suspend (T) -> Unit) {
        lifecycleScope.launch {
            collect(action)
        }
    }
}