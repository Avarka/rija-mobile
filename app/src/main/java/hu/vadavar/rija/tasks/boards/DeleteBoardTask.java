package hu.vadavar.rija.tasks.boards;

import android.os.AsyncTask;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import hu.vadavar.rija.models.boards.Board;

public class DeleteBoardTask extends AsyncTask<Board, Void, Void> {
    private static final String TAG = "DeleteBoardTask";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected Void doInBackground(Board... boards) {
        for (Board board : boards) {
            db.collection("Boards")
                    .document(board.getId())
                    .delete();
        }
        return null;
    }
}
