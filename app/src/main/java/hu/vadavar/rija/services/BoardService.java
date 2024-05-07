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
        boardProps.put("tickets", board.getTicketIds());

        return db.collection(COLLECTION)
                .add(boardProps);
    }

    public Task<Void> updateBoard(Board board) {
        Map<String, Object> boardProps = new HashMap<>();
        boardProps.put("id", board.getId());
        boardProps.put("name", board.getName());
        boardProps.put("team", board.getTeam());
        boardProps.put("statuses", board.getStatuses());
        boardProps.put("tickets", board.getTicketIds());

        return db.collection(COLLECTION)
                .document(board.getId())
                .update(boardProps);
    }

    public Task<Void> deleteBoard(Board board) {
        return db.collection(COLLECTION)
                .document(board.getId())
                .delete();
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
}
