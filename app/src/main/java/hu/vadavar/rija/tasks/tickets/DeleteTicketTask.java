package hu.vadavar.rija.tasks.tickets;

import android.os.AsyncTask;

import com.google.firebase.firestore.FirebaseFirestore;

import hu.vadavar.rija.models.tickets.Ticket;
import hu.vadavar.rija.services.TicketService;

public class DeleteTicketTask extends AsyncTask<Ticket, Void, Void>{
    private static final String TAG = "DeleteTicketTask";
    private final TicketService ticketService = new TicketService();


    @Override
    protected Void doInBackground(Ticket... tickets) {
        Ticket ticket = tickets[0];

        ticketService.deleteTicket(ticket);

        return null;
    }
}
