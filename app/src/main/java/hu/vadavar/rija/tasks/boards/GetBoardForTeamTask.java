package hu.vadavar.rija.tasks.boards;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.function.Consumer;

import hu.vadavar.rija.models.boards.Board;
import hu.vadavar.rija.models.teams.Team;
import hu.vadavar.rija.services.BoardService;

public class GetBoardForTeamTask extends AsyncTask<Team, Void, Board> {
    private static final String TAG = "GetBoardsForTeamTask";
    private final Consumer<Board> callback;
    private final BoardService service;

    public GetBoardForTeamTask(Consumer<Board> callback) {
        this.callback = callback;

        service = new BoardService();
    }


    @Override
    protected Board doInBackground(Team... teams) {
        Task<Board> task = service.getBoardByTeam(teams[0].getId());

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
    protected void onPostExecute(Board board) {
        super.onPostExecute(board);

        callback.accept(board);
    }

}
