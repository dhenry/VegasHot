package com.vegashot.vegashot.activity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.vegashot.vegashot.R;
import com.vegashot.vegashot.http.VegasHotHttpClient;
import com.vegashot.vegashot.model.ClassSchedule;
import com.vegashot.vegashot.model.Clazz;
import com.vegashot.vegashot.model.DailySchedule;

import java.util.Vector;

import butterknife.ButterKnife;
import butterknife.Bind;

public class ScheduleActivity extends Activity {

    private static final String SCHEDULE = "schedule";
    private ClassSchedule mClassSchedule;

    @Bind(R.id.schedule_view_pager) ViewPager mScheduleViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        ButterKnife.bind(this);

        if (null != savedInstanceState) {
            mClassSchedule = (ClassSchedule) savedInstanceState.getSerializable(SCHEDULE);
        }

        if (mClassSchedule == null) {
            new DownloadFilesTask().execute();
        } else {
            bindData(mClassSchedule);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mClassSchedule != null) {
            outState.putSerializable(SCHEDULE, mClassSchedule);
        }
    }

    private class DownloadFilesTask extends AsyncTask<String, Integer, ClassSchedule> {
        protected ClassSchedule doInBackground(String... params) {
            return new VegasHotHttpClient().getClassSchedule();
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(@Nullable ClassSchedule result) {
            mClassSchedule = result;
            bindData(result);
        }
    }

    class HeaderViewHolder {
        @Bind(R.id.header_title) TextView title;

        HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private void bindData(ClassSchedule schedule) {

        Vector<View> pages = new Vector<>();

        for (DailySchedule dailySchedule : schedule.dailySchedules) {
            ListView listView = new ListView(this);
            ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.daily_schedule_header, listView, false);
            HeaderViewHolder holder = new HeaderViewHolder(headerView);

            holder.title.setText(dailySchedule.date);

            listView.setAdapter(new DailyScheduleAdapter(dailySchedule, ScheduleActivity.this));
            listView.addHeaderView(headerView);
            pages.add(listView);
        }

        SchedulePagerAdapter adapter = new SchedulePagerAdapter(this, pages);
        mScheduleViewPager.setAdapter(adapter);
    }

    class SchedulePagerAdapter extends PagerAdapter {

        private Context mContext;
        private Vector<View> pages;

        public SchedulePagerAdapter(Context context, Vector<View> pages) {
            this.mContext = context;
            this.pages = pages;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View page = pages.get(position);
            container.addView(page);
            return page;
        }

        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    class DailyScheduleAdapter extends BaseAdapter {

        private final DailySchedule schedule;
        private final LayoutInflater inflater;

        public DailyScheduleAdapter(DailySchedule schedule, Activity activity) {
            this.schedule = schedule;
            this.inflater = activity.getLayoutInflater();
        }

        @Override
        public int getCount() {
            return schedule.getClasses().size();
        }

        @Override
        public Clazz getItem(int position) {
            return schedule.getClasses().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder;
            if (view != null) {
                holder = (ViewHolder) view.getTag();
            } else {
                view = inflater.inflate(R.layout.schedule_list_row, parent, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }

            final Clazz clazz = getItem(position);

            holder.time.setText(clazz.time);
            holder.name.setText(clazz.name);
            holder.instructor.setText(clazz.instructor);

            return view;
        }

        class ViewHolder {
            @Bind(R.id.time) TextView time;
            @Bind(R.id.name) TextView name;
            @Bind(R.id.instructor) TextView instructor;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
