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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
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
                        // Itt generálódik egy ID a csapatnak, amit később létrehozok.
                        String[] teamId = {db.collection("Teams").document().getId()};
                        String[] boardId = {db.collection("Boards").document().getId()};
                        String[] statusIds = {
                                db.collection("Statuses").document().getId(),
                                db.collection("Statuses").document().getId(),
                                db.collection("Statuses").document().getId()
                        };

                        String userId = mAuth.getCurrentUser().getUid();

                        new CreateUserTask().execute(
                                new User()
                                        .setId(userId)
                                        .setEmail(email)
                                        .setUsername(username)
                                        .setTeamIds(List.of(teamId))
                        );
                        new CreateTeamTask().execute(
                                new Team()
                                        .setId(teamId[0])
                                        .setName("Személyes")
                                        .setMemberIds(List.of(new String[]{userId}))
                                        .setBoardIds(List.of(boardId))
                        );
                        Toast.makeText(RegisterActivity.this, R.string.register_success, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        showRegisterFailed(R.string.register_failed);
                    }
                });
    }

    private void showRegisterFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
