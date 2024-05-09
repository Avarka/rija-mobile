package hu.vadavar.rija;

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

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hu.vadavar.rija.models.Status;
import hu.vadavar.rija.models.boards.Board;
import hu.vadavar.rija.models.tickets.Ticket;
import hu.vadavar.rija.tasks.boards.GetBoardForTeamTask;
import hu.vadavar.rija.tasks.teams.GetTeamsForUserTask;
import hu.vadavar.rija.tasks.tickets.DeleteTicketTask;
import hu.vadavar.rija.tasks.tickets.GetTicketsByTicketIdsTask;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "HomeActivity";

    Spinner boardSelectorSpinner;
    Toolbar mToolbar;
    RecyclerView mRecycler;
    List<Ticket> mTickets;
    TicketAdapter mTicketAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        boardSelectorSpinner = findViewById(R.id.board_spinner);
        mToolbar = findViewById(R.id.menu_toolbar);
        mRecycler = findViewById(R.id.board_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecycler);

        boardSelectorSpinner.setOnItemSelectedListener(this);
        try {
            new GetTeamsForUserTask(teams -> {
                Log.v(TAG, "onCreate: team: " + teams);
                if (!teams.isEmpty()) {
                    mToolbar.setTitle(teams.get(0).getName());
                    new GetBoardForTeamTask(board -> {
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

                        new GetTicketsByTicketIdsTask(tickets -> {
                            mTickets = tickets;
                            mTicketAdapter = new TicketAdapter(this, mTickets);
                            mRecycler.setAdapter(mTicketAdapter);
                            //Prefilter for consistentcy on UI
                            mTicketAdapter.getFilter().filter(board.getStatuses().get(0).getName());
                            mTicketAdapter.notifyDataSetChanged();
                            Log.d(TAG, "onCreate: tickets: " + mTickets);
                        }).execute(board.getTickets());
                    }).execute(teams.get(0));
                }
            }).execute();
        } catch (Exception e) {
            Log.e(TAG, "onCreate: ", e);
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
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

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

                // Delete the ticket
                // new DeleteTicketTask().execute(mTickets.get(originalPosition));
                mTickets.remove(originalPosition);
                mTicketAdapter.notifyDataSetChanged();
        }
    };
}
