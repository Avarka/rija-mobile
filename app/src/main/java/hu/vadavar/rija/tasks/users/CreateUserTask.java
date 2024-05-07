package hu.vadavar.rija.tasks.users;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import hu.vadavar.rija.models.users.User;

public class CreateUserTask extends AsyncTask<User, Void, Void> {
    private static final String TAG = "CreateUserTask";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected Void doInBackground(User... users) {
        for (User user : users) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", user.getId());
            userMap.put("username", user.getUsername());
            userMap.put("email", user.getEmail());
            userMap.put("teams", user.getTeamIds());
            db.collection("Users").document(user.getId()).set(userMap);
        }

        return null;
    }
}
