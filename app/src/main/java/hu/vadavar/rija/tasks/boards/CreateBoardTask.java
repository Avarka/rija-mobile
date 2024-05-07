package hu.vadavar.rija.tasks.boards;

import android.os.AsyncTask;
import android.util.Log;

import java.util.function.Consumer;

import hu.vadavar.rija.models.boards.Board;
import hu.vadavar.rija.services.BoardService;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class CreateBoardTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "CreateTeamTask";

    private final Board board;
    private final Consumer<Boolean> callback;

    private final BoardService service;

    public CreateBoardTask(Board board, Consumer<Boolean> callback) {
        this.board = board;
        this.callback = callback;

        service = new BoardService();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Task<DocumentReference> task = service.addBoard(board);

        while (!task.isComplete()) {
            try {
                Thread.sleep(1000);
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
