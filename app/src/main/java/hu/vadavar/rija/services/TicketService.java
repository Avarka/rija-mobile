package hu.vadavar.rija.services;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.vadavar.rija.models.boards.Board;
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
        if (ticketIds.isEmpty()) {
            return null;
        }

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

    public Task<DocumentReference> createTicket(Ticket ticket, Board board) {
        Log.d(TAG, "createTicket: " + ticket.getId());
        Map<String, Object> ticketData = new HashMap<>();
        ticketData.put("id", ticket.getId());
        ticketData.put("title", ticket.getTitle());
        ticketData.put("description", ticket.getDescription());
        ticketData.put("status", ticket.getStatus());
        ticketData.put("assignee", ticket.getAssigneeId());
        ticketData.put("reporter", ticket.getReporterId());
        ticketData.put("created", ticket.getCreated());
        ticketData.put("updated", ticket.getUpdated());
        ticketData.put("comments", ticket.getComments());

        BoardService boardService = new BoardService();
        Task<Void> t = boardService.addTicketToBoard(ticket.getId(), board.getId());

        while (!t.isComplete()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Log.d(TAG, "createTicket: ", e);
            }
        }

        return db.collection(COLLECTION_NAME)
                .add(ticketData);
    }

    public void deleteTicket(Ticket ticket) {
        Log.d(TAG, "deleteTicket: " + ticket.getId());

        BoardService boardService = new BoardService();
        boardService.removeTicketFromBoard(ticket.getId());

        db.collection(COLLECTION_NAME)
                .document(ticket.getId())
                .delete();
    }
}
