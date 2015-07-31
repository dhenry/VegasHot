package com.vegashot.vegashot.activity

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView

import com.vegashot.vegashot.R
import com.vegashot.vegashot.http.VegasHotHttpClient
import com.vegashot.vegashot.model.ClassSchedule
import com.vegashot.vegashot.model.Clazz
import com.vegashot.vegashot.model.DailySchedule

import java.util.Vector

import butterknife.bindView

public class ScheduleActivity : Activity() {
    private var mClassSchedule: ClassSchedule? = null

    val mScheduleViewPager: ViewPager by bindView(R.id.schedule_view_pager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        if (null != savedInstanceState) {
            mClassSchedule = savedInstanceState.getSerializable(SCHEDULE) as ClassSchedule?
        }

        if (mClassSchedule == null) {
            DownloadFilesTask().execute()
        } else {
            bindData(mClassSchedule!!)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mClassSchedule != null) {
            outState.putSerializable(SCHEDULE, mClassSchedule)
        }
    }

    private inner class DownloadFilesTask : AsyncTask<String, Int, ClassSchedule?>() {
        override fun doInBackground(vararg params: String): ClassSchedule? {
            return VegasHotHttpClient().getClassSchedule()
        }

        override fun onPostExecute(result: ClassSchedule?) {
            mClassSchedule = result

            if (result != null)
                bindData(result)
        }
    }

    private fun bindData(schedule: ClassSchedule) {

        val pages = Vector<View>()

        for (dailySchedule in schedule.dailySchedules) {
            val listView = ListView(this)
            val headerView = getLayoutInflater().inflate(R.layout.daily_schedule_header, listView, false) as ViewGroup

            val title: TextView? = headerView.findViewById(R.id.header_title) as? TextView
            title?.setText(dailySchedule.date)

            listView.setAdapter(DailyScheduleAdapter(dailySchedule, this@ScheduleActivity))
            listView.addHeaderView(headerView)
            pages.add(listView)
        }

        val adapter = SchedulePagerAdapter(this, pages)
        mScheduleViewPager.setAdapter(adapter)
    }

    inner class SchedulePagerAdapter(private val mContext: Context, private val pages: Vector<View>) : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val page = pages.get(position)
            container.addView(page)
            return page
        }

        override fun getCount(): Int {
            return pages.size()
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

    }

    inner class DailyScheduleAdapter(private val schedule: DailySchedule, activity: Activity) : BaseAdapter() {
        private val inflater: LayoutInflater

        init {
            this.inflater = activity.getLayoutInflater()
        }

        override fun getCount(): Int {
            return schedule.classes.size()
        }

        override fun getItem(position: Int): Clazz {
            return schedule.classes.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, view: View?, parent: ViewGroup): View {
            val returnView: View
            if (view == null) {
                returnView = inflater.inflate(R.layout.schedule_list_row, parent, false)
            } else {
                returnView = view
            }

            val clazz = getItem(position)

            val time: TextView? = returnView.findViewById(R.id.time) as? TextView
            time?.setText(clazz.time)

            val name: TextView? = returnView.findViewById(R.id.name) as? TextView
            name?.setText(clazz.name)

            val instructor: TextView? = returnView.findViewById(R.id.instructor) as? TextView
            instructor?.setText(clazz.instructor)

            return returnView
        }
    }

    companion object {

        private val SCHEDULE = "schedule"
    }

}
