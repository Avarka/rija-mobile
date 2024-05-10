package hu.vadavar.rija.tasks.boards;

import android.os.AsyncTask;
import android.util.Log;

import java.util.function.Consumer;

import hu.vadavar.rija.models.boards.Board;
import hu.vadavar.rija.services.BoardService;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class CreateBoardTask extends AsyncTask<Board, Void, Boolean> {
    private static final String TAG = "CreateTeamTask";

    private final Consumer<Boolean> callback;

    private final BoardService service;

    public CreateBoardTask(Consumer<Boolean> callback) {
        this.callback = callback;

        service = new BoardService();
    }

    @Override
    protected Boolean doInBackground(Board... boards) {
        Board board = boards[0];
        Task<DocumentReference> task = service.addBoard(board);

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
