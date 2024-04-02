package com.calendar_app.kotlincalendar
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*
class CalendarAdapter(
    private val context: Context,
    private val dates: ArrayList<Date>,
    private val currentCalendar: Calendar
) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_item_calendar, parent, false)
        }
        val dateTextView: TextView = view!!.findViewById(R.id.dateTextView)
        val date = dates[position]
        val cal = Calendar.getInstance().apply { time = date }
        if (cal.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH)) {
            val day = cal.get(Calendar.DAY_OF_MONTH)
            dateTextView.text = day.toString()
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                dateTextView.setTextColor(context.resources.getColor(android.R.color.holo_red_dark))
            } else {
                dateTextView.setTextColor(context.resources.getColor(android.R.color.holo_green_dark))
            }
        } else {
            dateTextView.text = ""
        }
        return view
    }
    override fun getItem(position: Int): Any {
        return dates[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getCount(): Int {
        return dates.size
    }
}
