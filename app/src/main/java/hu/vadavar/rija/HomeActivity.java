package hu.vadavar.rija;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hu.vadavar.rija.models.Status;
import hu.vadavar.rija.models.boards.Board;
import hu.vadavar.rija.models.teams.Team;
import hu.vadavar.rija.models.tickets.Ticket;
import hu.vadavar.rija.models.users.User;
import hu.vadavar.rija.tasks.boards.GetBoardForTeamTask;
import hu.vadavar.rija.tasks.teams.GetTeamsForUserTask;
import hu.vadavar.rija.tasks.tickets.DeleteTicketTask;
import hu.vadavar.rija.tasks.tickets.GetTicketsByTicketIdsTask;
import hu.vadavar.rija.tasks.users.GetUserByIdTask;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "HomeActivity";

    Spinner boardSelectorSpinner;
    Toolbar mToolbar;
    RecyclerView mRecycler;
    List<Ticket> mTickets;
    TicketAdapter mTicketAdapter;
    FloatingActionButton mAddTicketButton;
    Board mBoard;
    User mUser;
    Team mTeam;
    Status selectedStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        boardSelectorSpinner = findViewById(R.id.board_spinner);
        mToolbar = findViewById(R.id.menu_toolbar);
        mRecycler = findViewById(R.id.board_recycler);
        mAddTicketButton = findViewById(R.id.add_task_fab);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAddTicketButton.setOnClickListener(addTicketListener);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecycler);

        try {
            new GetUserByIdTask(user -> {
                mUser = user;
                Log.v(TAG, "onCreate: user: " + user);
            }).execute(FirebaseAuth.getInstance().getCurrentUser().getUid());
        } catch (Exception e) {
            Log.e(TAG, "onCreate: ", e);
        }

        boardSelectorSpinner.setOnItemSelectedListener(this);
        try {
            new GetTeamsForUserTask(teams -> {
                mTeam = teams.get(0);
                Log.v(TAG, "onCreate: team: " + teams);
                if (!teams.isEmpty()) {
                    mToolbar.setTitle(teams.get(0).getName());
                    updateBoard();
                }
            }).execute();
        } catch (Exception e) {
            Log.e(TAG, "onCreate: ", e);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateBoard();
    }

    private void updateBoard() {
        if (mTeam != null) {
            new GetBoardForTeamTask(board -> {
                mBoard = board;
                List<Board> boards = new ArrayList<>();
                boards.add(board);
                Log.v(TAG, "onCreate: boards: " + boards);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item,
                        boards.stream().map(Board::getStatuses)
                                .flatMap(List::stream)
                                .map(Status::getName)
                                .toArray(String[]::new));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                boardSelectorSpinner.setAdapter(adapter);

                Log.v(TAG, "onCreate: ticketIds: " + board.getTickets());
                updateTickets();
            }).execute(mTeam);
        }
    }

    private void updateTickets() {
        if (mBoard != null) {
            new GetTicketsByTicketIdsTask(tickets -> {
                mTickets = tickets;
                mTicketAdapter = new TicketAdapter(this, mTickets);
                mRecycler.setAdapter(mTicketAdapter);

                String f = selectedStatus == null ? mBoard.getStatuses().get(0).getName() : selectedStatus.getName();

                mTicketAdapter.getFilter().filter(f);
                mTicketAdapter.notifyDataSetChanged();
                Log.d(TAG, "updateTickets: tickets: " + mTickets);
            }).execute(mBoard.getTickets());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            Log.d(TAG, "onOptionsItemSelected: logout");
            FirebaseAuth.getInstance().signOut();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (mTicketAdapter != null) {
            Log.d(TAG, "onItemSelected: " + parent.getItemAtPosition(position));
            mTicketAdapter.getFilter().filter(parent.getItemAtPosition(position).toString());
            selectedStatus = mBoard.getStatuses().get(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //ignored
    }

    View.OnClickListener addTicketListener = v -> {
        Log.d(TAG, "addTicketListener: ");
        Intent intent = new Intent(this, AddTicketActivity.class);
        intent.putExtra("board", mBoard);
        intent.putExtra("user", mUser);
        intent.putExtra("status", selectedStatus == null ? mBoard.getStatuses().get(0) : selectedStatus);
        startActivity(intent);
    };

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Ticket swipedTicket = mTicketAdapter.getFilteredTickets().get(viewHolder.getAdapterPosition());

                int originalPosition = mTickets.indexOf(swipedTicket);

                Log.d(TAG, "Deleting: " + originalPosition);
                Log.d(TAG, "Deleting: " + mTickets.get(originalPosition));

                mTickets.remove(originalPosition);
                mTicketAdapter.notifyDataSetChanged();
        }
    };
}
