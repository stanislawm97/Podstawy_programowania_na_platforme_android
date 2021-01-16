package com.example.podstawyprogramowanianaplatformandroid.ui.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.podstawyprogramowanianaplatformandroid.R
import com.example.podstawyprogramowanianaplatformandroid.database.dao.ReminderDao
import com.example.podstawyprogramowanianaplatformandroid.database.database.MyRoomDatabase
import com.example.podstawyprogramowanianaplatformandroid.database.entity.ReminderEntity
import kotlinx.android.synthetic.main.fragment_reminder.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ReminderFragment : Fragment(R.layout.fragment_reminder) {

    private lateinit var reminderViewModel: ReminderViewModel
    private lateinit var reminderDao: ReminderDao
    private lateinit var reminderEntityLiveData: LiveData<List<ReminderEntity>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = MyRoomDatabase.getInstance(requireActivity().applicationContext)
        reminderDao = database!!.reminderDao()

        setHasOptionsMenu(false)
        reminderViewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reminderEntityLiveData = getAllReminder()
        reminderEntityLiveData.observe(viewLifecycleOwner,
            Observer<List<ReminderEntity>> {
                rv_reminders.layoutManager = LinearLayoutManager(requireContext())
                rv_reminders.adapter = ReminderAdapter().apply {
                    setDeleteAction {
                        val alarmReceiverIntent =
                            Intent(
                                requireActivity().applicationContext,
                                AlarmReceiver::class.java
                            ).apply {
                                putExtras(
                                    bundleOf(
                                        "TITLE" to it.title,
                                        "TEXT" to it.text,
                                        "ID" to 1
                                    )
                                )
                            }

                        (requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager).cancel(
                            PendingIntent.getBroadcast(
                                requireActivity().applicationContext,
                                it.requestCode,
                                alarmReceiverIntent,
                                0
                            )
                        )

                        CoroutineScope(Dispatchers.IO).launch {
                            reminderDao.delete(it)
                        }
                    }
                    submitList(it.sortedBy {
                        it.time
                    })
                }
            })

        fab_add_reminder.setOnClickListener {
            findNavController().navigate(R.id.action_nav_reminder_to_newReminderFragment)
        }
    }

    private fun getAllReminder() = runBlocking {
        reminderDao.getAllReminder()
    }
}