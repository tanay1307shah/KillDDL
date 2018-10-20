package com.map524s1a.killddl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Event implements Serializable {
    private String _eventName;
    private String _description;
    private Date _dueDate;
    private int _frequency; // number represents frequency type
    private int _importance; // importance is from 1 to 3
    private int _id;

    public Event(String eventName, String description, Date dueDate, int frequency, int importance, int id){
        _eventName = eventName;
        _description = description;
        _dueDate = dueDate;
        _frequency = frequency;
        _importance = importance;
        _id = id;
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
