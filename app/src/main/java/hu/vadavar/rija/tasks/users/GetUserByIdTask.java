package hu.vadavar.rija.tasks.users;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.tasks.Task;

import java.util.function.Consumer;

import hu.vadavar.rija.models.users.User;
import hu.vadavar.rija.services.UserService;

public class GetUserByIdTask extends AsyncTask<String, Void, User> {
    private static final String TAG = "GetUserByIdTask";
    private static final UserService userService = new UserService();
    private Consumer<User> callback;

    public GetUserByIdTask(Consumer<User> callback) {
        this.callback = callback;
    }

    @Override
    protected User doInBackground(String... strings) {
        String id = strings[0];
        Task<User> task = userService.getUserById(id);

        while (!task.isComplete()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Log.e(TAG, "doInBackground: ", e);
            }
        }

        return task.getResult();
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);
        callback.accept(user);
    }
}
