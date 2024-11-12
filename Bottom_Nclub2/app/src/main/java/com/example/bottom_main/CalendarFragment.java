package com.example.bottom_main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.example.bottom_main.CalendarUtils.daysInMonthArray;
import static com.example.bottom_main.CalendarUtils.monthYearFromDate;

import com.example.bottom_main.databinding.FragmentCalendarBinding;

public class CalendarFragment extends Fragment implements CalendarAdapter.OnItemListener {

    private TextView monthYearText;
    private FragmentCalendarBinding binding;
    private RecyclerView calendarRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Initialize binding
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        initWidgets(view);
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

        // Initialize WeekView button functionality
        initWeekViewActivityFunctionality();
        initButtonFunctionality();

        return view;
    }

    private void initWidgets(View view) {
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray();

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonthAction() {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction() {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        if (date != null) {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    private void initWeekViewActivityFunctionality() {
        // Set onClickListener for Weekbtn
        Button Weekbtn = binding.Weekbtn;
        Weekbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WeekViewActivity.class);
                startActivity(intent); // Navigate to WeekViewActivity
            }
        });
    }
    private void initButtonFunctionality() {
        // 設置 "上一月" 按鈕點擊事件
        Button PreviousMonthButton = binding.PreviousMonthbtn;
        PreviousMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousMonthAction();
            }
        });

        // 設置 "下一月" 按鈕點擊事件
        Button NextMonthbtn = binding.NextMonthbtn;
        NextMonthbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextMonthAction();
            }
        });
    }
}
