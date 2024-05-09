package hu.vadavar.rija.services;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import hu.vadavar.rija.models.tickets.Ticket;

public class TicketService {
    private static final String TAG = "TicketService";
    private static final String COLLECTION_NAME = "Tickets";
    private final FirebaseFirestore db;

    public TicketService() {
        this.db = FirebaseFirestore.getInstance();
    }

    public Task<List<Ticket>> getTicketsForBoard(String boardId) {
        return db.collection(COLLECTION_NAME)
                .whereEqualTo("boardId", boardId)
                .get()
                .continueWith(task -> {
                    if (!task.isSuccessful()) {
                        Log.e(TAG, "getTicketsForBoard: ", task.getException());
                        return null;
                    } else {
                        return task.getResult().toObjects(Ticket.class);
                    }
                });
    }

    public Task<List<Ticket>> getTicketsByTicketIds(List<String> ticketIds) {
        return db.collection(COLLECTION_NAME)
                .whereIn("id", ticketIds)
                .get()
                .continueWith(task -> {
                    if (!task.isSuccessful()) {
                        Log.e(TAG, "getTicketsByTicketIds: ", task.getException());
                        return null;
                    } else {
                        return task.getResult().toObjects(Ticket.class);
                    }
                });
    }

    public Task<Void> deleteTicket(Ticket ticket) {
        Log.d(TAG, "deleteTicket: " + ticket.getId());

        BoardService boardService = new BoardService();
        boardService.removeTicketFromBoard(ticket.getId());

        return db.collection(COLLECTION_NAME)
                .document(ticket.getId())
                .delete();
    }
}
