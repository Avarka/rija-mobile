package hu.vadavar.rija.tasks.teams;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import hu.vadavar.rija.models.teams.Team;
import hu.vadavar.rija.services.TeamService;

public class CreateTeamTask extends AsyncTask<Team, Void, Boolean> {
    private static final String TAG = "CreateTeamTask";

    private final TeamService service;
    private Consumer<Boolean> callback;

    public CreateTeamTask(Consumer<Boolean> callback) {
        this.callback = callback;
        service = new TeamService();
    }

    @Override
    protected Boolean doInBackground(Team... teams) {
        Team team = teams[0];
        Task<DocumentReference> task = service.addTeam(team);

        while (!task.isComplete()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Log.d(TAG, "doInBackground: ", e);
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
