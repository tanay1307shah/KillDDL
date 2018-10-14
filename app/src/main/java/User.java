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
}
