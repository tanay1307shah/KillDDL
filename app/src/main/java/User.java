import java.util.ArrayList;
import java.util.List;

public class User {
    private String _email;
    private String _password;
    private List<Event> _events;
    private Database _db;

    public User(String email, String password){
        _email = email;
        _password = password;
        _events = new ArrayList<Event>();
        _db = new Database();
    }

    //Get months Event, returns nothing but,
    public void getMonthsEvent() //TODO
    {
        List<Event> MonthlyEvents;
        MonthlyEvents=_db.getMonthlyEvents();
    }

    //Get Day event, returns nothing but
    public void getDayEvent() //TODO
    {
        List<Event> DayEvents;
        DayEvents =_db.getMonthlyEvents();

    }

    //Adds the event
    public void AddEvent(Event toAdd) //TODO
    {
        //Potentially "Would you like to add an event?"
        /*String eventName;
        String description;
        String dueDate;
        String frequency;
        String importance;
        //Call the database addEvent, which will convert the event into the proper form
        _db.addEvent(eventName,description,dueDate,frequency,importance);*/
    }

}
