package com.example.podstawyprogramowanianaplatformandroid.ui.reminder

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.DialogFragment
import java.util.*

class MyTimePickerDialog(private val onTimeSetListener: TimePickerDialog.OnTimeSetListener) : DialogFragment() {
    private val calendar = Calendar.getInstance()
    private val hour = calendar.get(Calendar.HOUR_OF_DAY)
    private val minute = calendar.get(Calendar.MINUTE)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(requireActivity(), onTimeSetListener, hour, minute, DateFormat.is24HourFormat(requireActivity()))
    }
}