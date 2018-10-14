import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class database {
    FirebaseDatabase database;
    DatabaseReference myRef;
    public database(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
    }

    public add

myRef.setValue("Hello, World!");
}
