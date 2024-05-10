package hu.vadavar.rija.tasks.tickets;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import hu.vadavar.rija.models.tickets.Ticket;
import hu.vadavar.rija.services.TicketService;

public class GetTicketsByTicketIdsTask extends AsyncTask<List<String>, Void, List<Ticket>> {
    private static final String TAG = "GetTicketsByTicketIdsTask";
    private final Consumer<List<Ticket>> callback;
    private final TicketService service = new TicketService();

    public GetTicketsByTicketIdsTask(Consumer<List<Ticket>> callback) {
        this.callback = callback;
    }

    @SafeVarargs
    @Override
    protected final List<Ticket> doInBackground(List<String>... lists) {
        List<String> ticketIds = lists[0];
        Task<List<Ticket>> task = service.getTicketsByTicketIds(ticketIds);

        if (ticketIds == null || ticketIds.isEmpty()) {
            return new ArrayList<>();
        }

        while (!task.isComplete()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Log.d(TAG, "doInBackground: ", e);
            }
        }

        return task.getResult();
    }

    @Override
    protected void onPostExecute(List<Ticket> tickets) {
        super.onPostExecute(tickets);

        callback.accept(tickets);
    }
}
