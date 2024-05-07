package hu.vadavar.rija;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import hu.vadavar.rija.models.boards.Board;
import hu.vadavar.rija.models.teams.Team;
import hu.vadavar.rija.services.TeamService;
import hu.vadavar.rija.tasks.boards.GetBoardForTeamTask;
import hu.vadavar.rija.tasks.teams.GetTeamsForUserTask;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "HomeActivity";

    Spinner boardSelectorSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        boardSelectorSpinner = findViewById(R.id.board_spinner);

        boardSelectorSpinner.setOnItemSelectedListener(this);
        try {
            Log.v(TAG, "onCreate: right before GetTeamsForUserTask");
            List<Team> teams = new ArrayList<>();
            new GetTeamsForUserTask(teams::addAll).execute().get();
            Log.v(TAG, "onCreate: team: " + teams);

            Log.v(TAG, "onCreate: right before GetBoardsForTeamTask");
            List<Board> boards = new ArrayList<>();
            new GetBoardForTeamTask(boards::add).execute(teams.get(0)).get();
            Log.v(TAG, "onCreate: boards: " + boards);

            Log.d(TAG, "onCreate: boards: " + boards);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item,
                    boards.stream().map(Board::getName).toArray(String[]::new));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            boardSelectorSpinner.setAdapter(adapter);
        } catch (Exception e) {
            Log.e(TAG, "onCreate: ", e);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
