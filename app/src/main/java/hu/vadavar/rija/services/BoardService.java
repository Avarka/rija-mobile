package hu.vadavar.rija.services;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import hu.vadavar.rija.models.boards.Board;

public class BoardService {
    private static final String TAG = "BoardService";
    private static final String COLLECTION = "Boards";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Task<DocumentReference> addBoard(Board board) {
        Map<String, Object> boardProps = new HashMap<>();
        boardProps.put("id", board.getId());
        boardProps.put("name", board.getName());
        boardProps.put("team", board.getTeam());
        boardProps.put("statuses", board.getStatuses());
        boardProps.put("tickets", board.getTickets());

        return db.collection(COLLECTION)
                .add(boardProps);
    }

    public void updateBoard(Board board) {
        Map<String, Object> boardProps = new HashMap<>();
        boardProps.put("id", board.getId());
        boardProps.put("name", board.getName());
        boardProps.put("team", board.getTeam());
        boardProps.put("statuses", board.getStatuses());
        boardProps.put("tickets", board.getTickets());

        db.collection(COLLECTION)
                .document(board.getId())
                .update(boardProps);
    }

    public Task<Board> getBoard(String boardId) {
        return db.collection(COLLECTION)
                .document(boardId)
                .get()
                .continueWith(task -> {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        return document.toObject(Board.class);
                    } else {
                        return null;
                    }
                });
    }

    public Task<Board> getBoardByTeam(String teamId) {
        return db.collection(COLLECTION)
                .whereEqualTo("team", teamId)
                .get()
                .continueWith(task -> {
                    DocumentSnapshot document = task.getResult().getDocuments().get(0);
                    if (document.exists()) {
                        return document.toObject(Board.class);
                    } else {
                        return null;
                    }
                });
    }

    public void removeTicketFromBoard(String ticketId) {
        db.collection(COLLECTION)
                .whereArrayContains("tickets", ticketId)
                .get()
                .continueWith(task -> {
                    DocumentSnapshot document = task.getResult().getDocuments().get(0);
                    if (document.exists()) {
                        Board board = document.toObject(Board.class);
                        board.getTickets().remove(ticketId);
                        updateBoard(board);
                    }
                    return null;
                });
    }

    public Task<Void> addTicketToBoard(String ticketId, String boardId) {
        return db.collection(COLLECTION)
                .document(boardId)
                .get()
                .continueWith(task -> {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Board board = document.toObject(Board.class);
                        board.getTickets().add(ticketId);
                        updateBoard(board);
                    }
                    return null;
                });
    }
}
