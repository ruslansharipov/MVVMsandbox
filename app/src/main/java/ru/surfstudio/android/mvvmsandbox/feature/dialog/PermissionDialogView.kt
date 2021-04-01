package ru.surfstudio.android.mvvmsandbox.feature.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.surfstudio.android.mvvmsandbox.R
import ru.surfstudio.android.mvvmsandbox.app.App
import ru.surfstudio.android.navigation.observer.command.EmitScreenResult

class PermissionDialogView: BottomSheetDialogFragment() {

    private var result: SimpleResult = SimpleResult.DISMISS
    private val route = PermissionDialogRoute()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = layoutInflater.inflate(R.layout.dialog_permissions, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val negativeBtn = view.findViewById<Button>(R.id.permission_dialog_negative_btn)
        val positiveBtn = view.findViewById<Button>(R.id.permission_dialog_positive_btn)

        negativeBtn.setOnClickListener { closeWithResult(SimpleResult.NEGATIVE) }
        positiveBtn.setOnClickListener { closeWithResult(SimpleResult.POSITIVE) }
    }

    override fun dismiss() {
        (activity?.application as App).appComponent.commandExecutor().execute(EmitScreenResult(route, result))
        super.dismiss()
    }

    private fun closeWithResult(resultToEmit: SimpleResult) {
        result = resultToEmit
        dismiss()
    }
}