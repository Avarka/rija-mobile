package hu.vadavar.rija.tasks.teams;

import android.os.AsyncTask;

import com.google.firebase.firestore.FirebaseFirestore;

import hu.vadavar.rija.models.teams.Team;

public class DeleteTeamTask extends AsyncTask<Team, Void, Void> {
    private static final String TAG = "DeleteTeamTask";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected Void doInBackground(Team... teams) {
        for (Team team : teams) {
            db.collection("Teams")
                    .document(team.getId())
                    .delete();
        }
        return null;
    }
}
