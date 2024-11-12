package com.example.bottom_main;

import java.time.LocalTime;
import java.util.ArrayList;

public class HourEvent
{
    LocalTime time;
    ArrayList<Event_calendar> events;

    public HourEvent(LocalTime time, ArrayList<Event_calendar> events)
    {
        this.time = time;
        this.events = events;
    }

    public LocalTime getTime()
    {
        return time;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }

    public ArrayList<Event_calendar> getEvents()
    {
        return events;
    }

    public void setEvents(ArrayList<Event_calendar> events)
    {
        this.events = events;
    }
}
