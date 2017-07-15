package com.mmeh.peal;

import android.annotation.TargetApi;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.icu.util.Calendar;

import java.sql.Time;

@RequiresApi(api = Build.VERSION_CODES.N)
public class WeekCalendar extends AppCompatActivity {
    public int currentWeek;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Intent intent = new Intent();
            int itemId = item.getItemId();
            switch (item.getItemId()) {
                case R.id.navigation_calendar:
                    intent = new Intent(findViewById(itemId).getContext(), WeekCalendar.class);
                    break;
                case R.id.navigation_recipes:
                    intent = new Intent(findViewById(itemId).getContext(), RecipeBook.class);
                    break;
                case R.id.navigation_shoppingList:
                    intent = new Intent(findViewById(itemId).getContext(), ShoppingList.class);
                    break;
            }
            startActivity(intent);
            return true;

        }

    };

    public int getWeek(){
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        return week;
    }

    public int nextWeek(int current_week){
        return current_week++;
    }

    public void setWeekDays(int week){

        DateFormat format = new SimpleDateFormat("dd");
        DateFormat dateFormatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        final String[] days = new String[7];
        final String[] daysFullDate = new String[7];
        for(int i = 0; i < 7; i++)
        {
            days[i] = format.format(calendar.getTime());
            daysFullDate[i] = dateFormatYYYYMMDD.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        final String month_name = month_date.format(calendar.getTime());

        Calendar currentDay = Calendar.getInstance();
        String currentDate = format.format(currentDay.getTime());
        SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("E");
        String weekDay = dayOfWeekFormat.format(currentDay.getTime());

        Button month = (Button)findViewById(R.id.currentWeek);
        month.setText(month_name);


        Button day_0 = (Button)findViewById(R.id.day0);
        day_0.setText(days[0] + "\nSUN");
        setCurrentDayL(days[0] + "Sun", day_0, currentDate + weekDay);

        Button day_1 = (Button)findViewById(R.id.day1);
        day_1.setText(days[1]+ "\nMON");
        setCurrentDayL(days[1] + "Mon", day_1, currentDate + weekDay);

        Button day_2 = (Button)findViewById(R.id.day2);
        day_2.setText(days[2]+ "\nTUE");
        setCurrentDayD(days[2] + "Tue", day_2, currentDate + weekDay);

        Button day_3 = (Button)findViewById(R.id.day3);
        day_3.setText(days[3]+ "\nWED");
        setCurrentDayD(days[3] + "Wed", day_3, currentDate + weekDay);

        Button day_4 = (Button)findViewById(R.id.day4);
        day_4.setText(days[4]+ "\nTHU");
        setCurrentDayL(days[4] + "Thu", day_4, currentDate + weekDay);

        Button day_5 = (Button)findViewById(R.id.day5);
        day_5.setText(days[5]+ "\nFRI");
        setCurrentDayL(days[5] + "Fri", day_5, currentDate + weekDay);

        Button day_6 = (Button)findViewById(R.id.day6);
        day_6.setText(days[6]+ "\nSAT");
        setCurrentDayD(days[6] + "Sat", day_6, currentDate + weekDay);


        day_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDay(days[0], month_name, daysFullDate[0]);
            }
        });
        day_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDay(days[1], month_name, daysFullDate[1]);
            }
        });
        day_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDay(days[2], month_name, daysFullDate[2]);
            }
        });
        day_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDay(days[3], month_name, daysFullDate[3]);
            }
        });
        day_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDay(days[4], month_name, daysFullDate[4]);
            }
        });
        day_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDay(days[5], month_name, daysFullDate[5]);
            }
        });
        day_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDay(days[6], month_name, daysFullDate[6]);
            }
        });


        Button next_week = (Button)findViewById(R.id.nextWeek);
        next_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentWeek++;
                setWeekDays(currentWeek);
            }
        });
        Button previous_week = (Button)findViewById(R.id.previousWeek);
        previous_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentWeek--;
                setWeekDays(currentWeek);
            }
        });
        Button current_week = (Button)findViewById(R.id.currentWeek);
        current_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar current = Calendar.getInstance();
                currentWeek = current.get(Calendar.WEEK_OF_YEAR);
                setWeekDays(currentWeek);
            }
        });
    }







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_calendar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        currentWeek = getWeek();
        setWeekDays(currentWeek);



    }

    public void goToDay(String day, String month, String fullDate){
        Intent intent;
        intent = new Intent(this, DayView.class);
        intent.putExtra("Month", month);
        intent.putExtra("Day", day);
        intent.putExtra(DayView.IN_DATE, fullDate);
        startActivity(intent);
    }

    public void setCurrentDayD(String day, Button button, String currentDate){
        if(day.equals(currentDate)){
            button.setBackgroundResource(R.drawable.round_button_current_day);
        }
        else {
            button.setBackgroundResource(R.drawable.round_button_dark);
        }
    }
    public void setCurrentDayL(String day, Button button, String currentDate){
        if(day.equals(currentDate)){
            button.setBackgroundResource(R.drawable.round_button_current_day_dark);
            button.setTextColor(getResources().getColor(R.color.lightBeige));
        }
        else {
            button.setBackgroundResource(R.drawable.round_button);
            button.setTextColor(getResources().getColor(R.color.darkGrey));
        }
    }


}
