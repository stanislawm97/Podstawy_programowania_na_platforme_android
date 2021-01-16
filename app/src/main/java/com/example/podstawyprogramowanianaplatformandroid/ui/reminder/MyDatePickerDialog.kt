package com.example.podstawyprogramowanianaplatformandroid.ui.reminder

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

class MyDatePickerDialog(private val onDateSetListener: DatePickerDialog.OnDateSetListener) : DialogFragment() {
    private val calendar = Calendar.getInstance()
    private val year = calendar.get(Calendar.YEAR)
    private val month = calendar.get(Calendar.MONTH)
    private val day = calendar.get(Calendar.DAY_OF_MONTH)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return DatePickerDialog(requireActivity(), onDateSetListener, year, month, day)
    }
}