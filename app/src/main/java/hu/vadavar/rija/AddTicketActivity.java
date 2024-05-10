package hu.vadavar.rija;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.function.Consumer;

import hu.vadavar.rija.models.Status;
import hu.vadavar.rija.models.boards.Board;
import hu.vadavar.rija.models.tickets.Ticket;
import hu.vadavar.rija.models.users.User;
import hu.vadavar.rija.tasks.tickets.CreateTicketTask;

public class AddTicketActivity extends AppCompatActivity {
    EditText ticketTitle;
    EditText ticketDescription;
    Board mBoard;
    User mUser;
    Status selectedStatus;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_ticket);

        ticketDescription = findViewById(R.id.ticket_description_input);
        ticketTitle = findViewById(R.id.ticket_title_input);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mBoard = extras.getParcelable("board");
            mUser = extras.getParcelable("user");
            selectedStatus = extras.getParcelable("status");
        } else {
            Toast.makeText(this, "Hiba történt a tábla betöltése közben!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void addTicket(View view) {
        String title = ticketTitle.getText().toString();
        String description = ticketDescription.getText().toString();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Minden mező kitöltése kötelező!", Toast.LENGTH_LONG).show();
            return;
        }

        String newTicketId = db.collection("Tickets").document().getId();

        Ticket ticket = new Ticket()
                .setId(newTicketId)
                .setTitle(title)
                .setDescription(description)
                .setAssignee(mUser.getId())
                .setComments(new ArrayList<>())
                .setCreated(new Date())
                .setUpdated(new Date())
                .setReporter(mUser.getId())
                .setStatus(selectedStatus);

        new CreateTicketTask(success -> {
            if (success) {
                Toast.makeText(this, "Sikeresen hozzáadva!", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Hiba történt a hozzáadás közben!", Toast.LENGTH_LONG).show();
            }
        }, mBoard).execute(ticket);
    }
}