package hu.vadavar.rija.tasks.teams;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

import hu.vadavar.rija.models.boards.Board;
import hu.vadavar.rija.models.teams.Team;
import hu.vadavar.rija.services.BoardService;
import hu.vadavar.rija.services.TeamService;

public class GetTeamsForUserTask extends AsyncTask<Void, Void, List<Team>> {
    private static final String TAG = "GetTeamsForUserTask";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final Consumer<List<Team>> callback;
    private final TeamService service;

    public GetTeamsForUserTask(Consumer<List<Team>> callback) {
        this.callback = callback;

        service = new TeamService();
    }


    @Override
    protected List<Team> doInBackground(Void... voids) {
        Task<List<Team>> task = service.getTeamForUser(mAuth.getCurrentUser().getUid());

        while (!task.isComplete()) {
            try {
                Thread.sleep(1000);
                Log.d(TAG, "------- doInBackground: waiting for task to complete");
            } catch (InterruptedException e) {
                Log.d(TAG, "doInBackground: ", e);
            }
        }

        return task.getResult();
    }

    @Override
    protected void onPostExecute(List<Team> team) {
        super.onPostExecute(team);

        callback.accept(team);
    }
}
