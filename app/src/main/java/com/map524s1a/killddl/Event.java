package com.map524s1a.killddl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Event implements Serializable {
    private String _eventName;
    private String color;
    private String _description;
    private Date _dueDate;
    private int _frequency; // number represents frequency type
    private int _importance; // importance is from 1 to 3
    private int _id;
    private Date time;
    private String timeStr;

    public Event(String eventName, String description,String timeStr, Date dueDate, Date time, int frequency, int importance, int id,String color){
        _eventName = eventName;
        this.time = time;
        this.timeStr = timeStr;
        _description = description;
        _dueDate = dueDate;
        _frequency = frequency;
        _importance = importance;
        _id = id;
        this.color = color;
    }

    public String getTimeStr(){
        return this.timeStr;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int id) {
        this._id=id;
    }

    public String get_eventName() {
        return _eventName;
    }

    public void set_eventName(String _eventName) {
        this._eventName = _eventName;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String getColor() { return color; }

    public void setColor(String color) { this.color = color; }

    public Date getTime() { return time; }

    public void setTime(Date time) { this.time = time; }

    public void setTimeStr(String timeStr) { this.timeStr = timeStr; }


    public Date get_dueDate() {
        return _dueDate;
    }

    public void set_dueDate(Date _dueDate) {
        this._dueDate = _dueDate;
    }

    public int get_frequency() {
        return _frequency;
    }

    public void set_frequency(int _frequency) {
        this._frequency = _frequency;
    }

    public int get_importance() {
        return _importance;
    }

    public void set_importance(int _importance) {
        this._importance = _importance;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("event_name", _eventName);
        result.put("decription", _description);
        result.put("due_date", _dueDate);
        result.put("frequency", _frequency);
        result.put("importance", _importance);
        return result;
    }
}
