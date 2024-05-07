package hu.vadavar.rija.tasks.tickets;

import android.os.AsyncTask;

import com.google.firebase.firestore.FirebaseFirestore;

import hu.vadavar.rija.models.tickets.Ticket;

public class DeleteTicketTask extends AsyncTask<Ticket, Void, Void>{
    private static final String TAG = "DeleteTicketTask";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected Void doInBackground(Ticket... tickets) {
        for (Ticket ticket : tickets) {
            db.collection("Tickets")
                    .document(ticket.getId())
                    .delete();
        }
        return null;
    }
}
