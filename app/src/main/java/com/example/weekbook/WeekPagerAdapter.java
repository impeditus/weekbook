package com.example.weekbook;

        import android.content.Context;
        import android.content.res.Resources;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentManager;
        import androidx.fragment.app.FragmentPagerAdapter;

        import java.text.SimpleDateFormat;
        import java.util.Calendar;

public class WeekPagerAdapter extends FragmentPagerAdapter {
    private String[] weeksTitles;

    public WeekPagerAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        Resources resources = context.getResources();
        weeksTitles = resources.getStringArray(R.array.weeksTitles);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle arguments = new Bundle();
        WeekFragment weekFragment=new WeekFragment();
        getToday(position,arguments);
        weekFragment.setArguments(arguments);
        return weekFragment;
    }

    @Override
    public int getCount() {
        return 13;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return weeksTitles[position];
    }


    public void getToday(int position,Bundle arguments)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(" dd.MM.yy");
        Calendar calendar =Calendar.getInstance();
        // Calendar calendar = new GregorianCalendar(2020,4,3,13,24,56);
        calendar.add(Calendar.DAY_OF_MONTH, -(calendar.get(Calendar.DAY_OF_WEEK)==1? 6:calendar.get(Calendar.DAY_OF_WEEK)-2));
        calendar.add(Calendar.DAY_OF_MONTH,(position-4)*7);
        arguments.putString(WeekFragment.MONDAY, "Monday "+ sdf.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH,  1);
        arguments.putString(WeekFragment.TUESDAY, "Tuesday "+sdf.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        arguments.putString(WeekFragment.WEDNESDAY, "Wednesday "+sdf.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        arguments.putString(WeekFragment.THURSDAY, "Thursday "+sdf.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        arguments.putString(WeekFragment.FRIDAY, "Friday "+sdf.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        arguments.putString(WeekFragment.SATURDAY, "Saturday "+sdf.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        arguments.putString(WeekFragment.SUNDAY, "Sunday "+sdf.format(calendar.getTime()));
    }
}
