package hu.vadavar.rija.tasks.users;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import hu.vadavar.rija.models.users.User;
import hu.vadavar.rija.services.UserService;

public class CreateUserTask extends AsyncTask<User, Void, Boolean> {
    private static final String TAG = "CreateUserTask";
    private final UserService userService;
    private Consumer<Boolean> callback;
    public CreateUserTask(Consumer<Boolean> callback) {
        this.userService = new UserService();
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(User... users) {
        User user = users[0];
        Task<Void> task = userService.createUser(user);

        while (!task.isComplete()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Log.e(TAG, "Error while waiting for task to complete", e);
            }
        }

        return task.isSuccessful();
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        callback.accept(success);
    }
}
