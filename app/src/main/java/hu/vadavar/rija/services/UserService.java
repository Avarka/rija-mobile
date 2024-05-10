package hu.vadavar.rija.services;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import hu.vadavar.rija.models.users.User;

public class UserService {

    private static final String TAG = "UserService";
    private static final String COLLECTION_NAME = "Users";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Task<User> getUserById(String id) {
        return db.collection(COLLECTION_NAME).document(id).get().continueWith(task -> {
            if (task.isSuccessful()) {
                return task.getResult().toObject(User.class);
            } else {
                return null;
            }
        });
    }

    public Task<Void> createUser(User user) {
        return db.collection(COLLECTION_NAME).document(user.getId()).set(user);
    }
}
