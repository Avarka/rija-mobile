package hu.vadavar.rija.tasks.teams;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import hu.vadavar.rija.models.teams.Team;

public class CreateTeamTask extends AsyncTask<Team, Void, Void> {
    private static final String TAG = "CreateTeamTask";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected Void doInBackground(Team... teams) {
        for (Team team : teams) {
            Map<String, Object> teamData = new HashMap<>();
            teamData.put("id", team.getId());
            teamData.put("name", team.getName());
            teamData.put("members", team.getMemberIds());
            teamData.put("boards", team.getBoardIds());

            db.collection("Teams")
                    .document(team.getId())
                    .set(teamData);
        }

        return null;
    }
}
