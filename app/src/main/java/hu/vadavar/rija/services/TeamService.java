package hu.vadavar.rija.services;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import hu.vadavar.rija.models.teams.Team;

public class TeamService {
    private static final String TAG = "TeamService";
    private static final String COLLECTION_NAME = "Teams";
    private final FirebaseFirestore db;

    public TeamService() {
        db = FirebaseFirestore.getInstance();
    }

    public Task<Team> getTeam(String teamId) {
        return db.collection(COLLECTION_NAME)
                .document(teamId)
                .get()
                .continueWith(task -> {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        return document.toObject(Team.class);
                    } else {
                        return null;
                    }
                });
    }

    public Task<List<Team>> getTeamForUser(String userId) {
        Log.v(TAG, "----- getTeamForUser: userId: " + userId);
        var teamsTask = db.collection(COLLECTION_NAME)
                .whereArrayContains("members", userId)
                .get();

        return teamsTask
                .continueWith(task -> {
                            Log.v(TAG, "----- getTeamForUser: task: " + task.getResult().toObjects(Team.class));
                            if (!task.isSuccessful()) {
                                Log.e(TAG, "getTeamForUser: ", task.getException());
                                return null;
                            } else {
                                return task.getResult().toObjects(Team.class);
                            }
                        }
                );
    }

    public Task<DocumentReference> addTeam(Team team) {
        return db.collection(COLLECTION_NAME)
                .add(team);
    }
}
