package hu.vadavar.rija;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import hu.vadavar.rija.models.Status;
import hu.vadavar.rija.models.boards.Board;
import hu.vadavar.rija.models.teams.Team;
import hu.vadavar.rija.models.users.User;
import hu.vadavar.rija.tasks.boards.CreateBoardTask;
import hu.vadavar.rija.tasks.teams.CreateTeamTask;
import hu.vadavar.rija.tasks.users.CreateUserTask;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    EditText emailField;
    EditText passwordField;
    EditText passwordConfirmField;
    EditText usernameField;
    Button registerButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        passwordConfirmField = findViewById(R.id.confirm_password);
        usernameField = findViewById(R.id.username);

        registerButton = findViewById(R.id.register);

        registerButton.setOnClickListener(v -> register(emailField.getText().toString(), passwordField.getText().toString(), passwordConfirmField.getText().toString(), usernameField.getText().toString()));
    }

    private void register(String email, String password, String passwordConfirm, String username) {
        //log email, password, passwordConfirm and username
        Log.d(TAG, "register: " + email + " password: " + password + " passwordConfirm: " + passwordConfirm + " username: " + username);

        if (!password.equals(passwordConfirm)) {
            showRegisterFailed(R.string.passwords_not_match);
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        String[] teamId = {db.collection("Teams").document().getId()};
                        String[] boardId = {db.collection("Boards").document().getId()};
                        String[] statusIds = {
                                db.collection("Statuses").document().getId(),
                                db.collection("Statuses").document().getId(),
                                db.collection("Statuses").document().getId()
                        };

                        Status status1 = new Status()
                                .setId(statusIds[0])
                                .setName("Új")
                                .setColor("#FF0000")
                                .setNextStatusIds(new ArrayList<>())
                                .setPreviousStatusIds(List.of(statusIds[1]));
                        Status status2 = new Status()
                                .setId(statusIds[1])
                                .setName("Folyamatban")
                                .setColor("#00FF00")
                                .setNextStatusIds(List.of(statusIds[2]))
                                .setPreviousStatusIds(List.of(statusIds[0]));
                        Status status3 = new Status()
                                .setId(statusIds[2])
                                .setName("Kész")
                                .setColor("#0000FF")
                                .setNextStatusIds(new ArrayList<>())
                                .setPreviousStatusIds(List.of(statusIds[1]));

                        String userId = mAuth.getCurrentUser().getUid();

                        new CreateUserTask(success -> {
                            if (!success) {
                                showRegisterFailed(R.string.register_failed);
                                return;
                            }

                            new CreateTeamTask(success1 -> {
                                if (!success1) {
                                    showRegisterFailed(R.string.register_failed);
                                    return;
                                }

                                new CreateBoardTask(success2 -> {
                                    if (!success2) {
                                        showRegisterFailed(R.string.register_failed);
                                        return;
                                    }
                                    Toast.makeText(RegisterActivity.this, R.string.register_success, Toast.LENGTH_SHORT).show();
                                    finish();
                                }).execute(
                                        new Board()
                                                .setId(boardId[0])
                                                .setName("Személyes")
                                                .setTeam(teamId[0])
                                                .setTickets(new ArrayList<>())
                                                .setStatuses(List.of(status1, status2, status3))
                                );
                            }).execute(
                                    new Team()
                                            .setId(teamId[0])
                                            .setName("Személyes")
                                            .setMembers(List.of(new String[]{userId}))
                                            .setBoards(List.of(boardId))
                            );
                        }).execute(
                                new User()
                                        .setId(userId)
                                        .setEmail(email)
                                        .setUsername(username)
                                        .setTeamIds(List.of(teamId))
                        );
                    } else {
                        showRegisterFailed(R.string.register_failed);
                    }
                })
        .addOnFailureListener(e -> {
            Log.e(TAG, "register: ", e);
            showRegisterFailed(R.string.register_failed);
        });
    }

    private void showRegisterFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
