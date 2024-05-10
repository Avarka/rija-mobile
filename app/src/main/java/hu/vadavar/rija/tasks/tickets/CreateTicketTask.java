package hu.vadavar.rija.tasks.tickets;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import hu.vadavar.rija.models.boards.Board;
import hu.vadavar.rija.models.tickets.Ticket;
import hu.vadavar.rija.services.TicketService;

public class CreateTicketTask extends AsyncTask<Ticket, Void, Boolean> {
    private static final String TAG = "CreateTicketTask";
    private final TicketService ticketService = new TicketService();
    Board board;
    Consumer<Boolean> callback;

    public CreateTicketTask(Consumer<Boolean> callback, Board board) {
        this.callback = callback;
        this.board = board;
    }

    @Override
    protected Boolean doInBackground(Ticket... tickets) {
        Ticket ticket = tickets[0];
        Task task = ticketService.createTicket(ticket, board);

        while (!task.isComplete()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Log.d(TAG, "doInBackground: ", e);
            }
        }

        return task.isSuccessful();
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        callback.accept(success);
    }
}
