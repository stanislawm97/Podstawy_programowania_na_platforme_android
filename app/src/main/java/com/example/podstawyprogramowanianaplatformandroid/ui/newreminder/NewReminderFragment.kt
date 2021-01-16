package com.example.podstawyprogramowanianaplatformandroid.ui.newreminder

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.podstawyprogramowanianaplatformandroid.R
import com.example.podstawyprogramowanianaplatformandroid.database.MyRoomDatabase
import com.example.podstawyprogramowanianaplatformandroid.database.dao.ReminderDao
import com.example.podstawyprogramowanianaplatformandroid.database.entity.ReminderEntity
import com.example.podstawyprogramowanianaplatformandroid.ui.reminder.AlarmReceiver
import com.example.podstawyprogramowanianaplatformandroid.ui.reminder.MyDatePickerDialog
import com.example.podstawyprogramowanianaplatformandroid.ui.reminder.MyTimePickerDialog
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_new_reminder.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class NewReminderFragment : Fragment(R.layout.fragment_new_reminder),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private lateinit var newReminderViewModel: NewReminderViewModel
    private lateinit var reminderDao: ReminderDao

    private var year = 0
    private var month = 0
    private var day = 0
    private var hour = 0
    private var minute = 0

    private lateinit var alarmManager: AlarmManager
    private lateinit var alarmIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = MyRoomDatabase.getInstance(requireActivity().applicationContext)
        reminderDao = database!!.reminderDao()

        setHasOptionsMenu(false)
        newReminderViewModel = ViewModelProvider(this).get(NewReminderViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (til_reminder_title as TextInputLayout).editText?.apply {
            setText(" ")
            addTextChangedListener {
                if (it.isNullOrEmpty())
                    setText(" ")
            }
        }

        (til_reminder_description as TextInputLayout).editText?.apply {
            setText(" ")
            addTextChangedListener {
                if (it.isNullOrEmpty())
                    setText(" ")
            }
        }

        (til_reminder_date as TextInputLayout).editText?.apply {
            keyListener = null
            setText(" ")

            setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    val dialog = MyDatePickerDialog(this@NewReminderFragment)
                    dialog.show(requireActivity().supportFragmentManager, "date_picker")
                    v.clearFocus()
                }
            }
        }

        bt_save.setOnClickListener {
            setAlarm()
            findNavController().navigate(R.id.action_newReminderFragment_to_nav_reminder)
        }

    }

    private fun setAlarm() {
        alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val date =
            Calendar.Builder().setDate(year, month, day).setTimeOfDay(this.hour, this.minute, 0)
                .build()

        val reminderEntity = ReminderEntity(
            Random().nextInt(99999),
            (til_reminder_title as TextInputLayout).editText?.text.toString(),
            (til_reminder_description as TextInputLayout).editText?.text.toString(),
            Random().nextInt(99999),
            0,
            (til_reminder_date as TextInputLayout).editText?.text.toString()
        )
        val alarmReceiverIntent =
            Intent(requireActivity().applicationContext, AlarmReceiver::class.java).apply {
                putExtras(
                    bundleOf(
                        "TITLE" to reminderEntity.title,
                        "TEXT" to reminderEntity.text,
                        "ID" to reminderEntity.id
                    )
                )
            }
        alarmIntent = PendingIntent.getBroadcast(
            requireActivity().applicationContext,
            reminderEntity.requestCode,
            alarmReceiverIntent,
            0
        )

        alarmManager.set(AlarmManager.RTC_WAKEUP, date.timeInMillis, alarmIntent)

        CoroutineScope(Dispatchers.IO).launch {
            reminderDao.insert(reminderEntity)
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        this.year = year
        this.month = month
        this.day = dayOfMonth

        val dialog = MyTimePickerDialog(this)
        dialog.show(requireActivity().supportFragmentManager, "time_picker")
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        this.hour = hourOfDay
        this.minute = minute

        val date = when {
            this.hour < 10 && this.minute < 10 -> "$year-${month + 1}-$day 0$hour:0${this.minute}"
            this.hour < 10 -> "$year-${month + 1}-$day 0$hour:${this.minute}"
            this.minute < 10 -> "$year-${month + 1}-$day $hour:0${this.minute}"
            else -> "$year-${month + 1}-$day $hour:${this.minute}"
        }
        (til_reminder_date as TextInputLayout).editText?.setText(date)
    }
}