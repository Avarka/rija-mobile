package hu.vadavar.rija.tasks.users;

import android.os.AsyncTask;

import com.google.firebase.firestore.FirebaseFirestore;

import hu.vadavar.rija.models.users.User;

public class DeleteUserTask extends AsyncTask<User, Void, Void> {
    private static final String TAG = "DeleteUserTask";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected Void doInBackground(User... users) {
        for (User user : users) {
            db.collection("Users")
                    .document(user.getId())
                    .delete();
        }
        return null;
    }
}
