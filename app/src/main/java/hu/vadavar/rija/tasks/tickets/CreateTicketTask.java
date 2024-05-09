package hu.vadavar.rija.tasks.tickets;

import android.os.AsyncTask;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import hu.vadavar.rija.models.tickets.Ticket;

public class CreateTicketTask extends AsyncTask<Ticket, Void, Void> {
    private static final String TAG = "CreateTicketTask";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected Void doInBackground(Ticket... tickets) {
        for (Ticket ticket : tickets) {
            Map<String, Object> ticketData = new HashMap<>();
            ticketData.put("id", ticket.getId());
            ticketData.put("title", ticket.getTitle());
            ticketData.put("description", ticket.getDescription());
            ticketData.put("status", ticket.getStatus());
            ticketData.put("assignee", ticket.getAssigneeId());
            ticketData.put("reporter", ticket.getReporterId());
            ticketData.put("createdAt", ticket.getCreated());
            ticketData.put("updatedAt", ticket.getUpdated());
            ticketData.put("comments", ticket.getComments());

            db.collection("Teams")
                    .document(ticket.getId())
                    .set(ticketData);
        }
        return null;
    }
}
