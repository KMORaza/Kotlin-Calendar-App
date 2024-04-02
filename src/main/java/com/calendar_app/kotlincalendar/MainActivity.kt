package com.calendar_app.kotlincalendar
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.GridView
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
class MainActivity : AppCompatActivity() {
    private lateinit var monthTextView: TextView
    private lateinit var calendarGridView: GridView
    private lateinit var calendar: Calendar
    private lateinit var adapter: CalendarAdapter
    private val dateChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateCalendar()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        monthTextView = findViewById(R.id.monthTextView)
        calendarGridView = findViewById(R.id.calendarGridView)
        val prevMonthButton: ImageButton = findViewById(R.id.prevMonthButton)
        val nextMonthButton: ImageButton = findViewById(R.id.nextMonthButton)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.StatusBarColor)
        }
        prevMonthButton.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            updateCalendar()
        }
        nextMonthButton.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            updateCalendar()
        }
        registerReceiver(dateChangeReceiver, IntentFilter(Intent.ACTION_TIME_CHANGED))
        calendar = Calendar.getInstance()
        updateCalendar()
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(dateChangeReceiver)
    }
    private fun updateCalendar() {
        val sdf = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        monthTextView.text = sdf.format(calendar.time)
        val dates = ArrayList<Date>()
        val cal = calendar.clone() as Calendar
        cal.set(Calendar.DAY_OF_MONTH, 1)
        val monthBeginningCell = cal.get(Calendar.DAY_OF_WEEK) - 1
        cal.add(Calendar.DAY_OF_MONTH, -monthBeginningCell)
        while (dates.size < 42) {
            dates.add(cal.time)
            cal.add(Calendar.DAY_OF_MONTH, 1)
        }
        adapter = CalendarAdapter(this, dates, calendar)
        calendarGridView.adapter = adapter
    }
}
