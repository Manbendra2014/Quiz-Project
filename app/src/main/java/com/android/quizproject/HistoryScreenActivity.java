package com.android.quizproject;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;  // Make sure you import RecyclerView
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class HistoryScreenActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private List<ScoreHistory> historyList;
    private HistoryAdapter historyAdapter;
    private RecyclerView historyListView;  // RecyclerView instead of ListView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_screen);
        db = FirebaseFirestore.getInstance();
        historyListView = findViewById(R.id.historyListView);
        historyList = new ArrayList<>();
        historyAdapter = new HistoryAdapter(this, historyList);
        historyListView.setLayoutManager(new LinearLayoutManager(this));
        historyListView.setAdapter(historyAdapter);
        loadHistory();
    }
    private void loadHistory() {
        db.collection("scoreHistory")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    historyList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        ScoreHistory scoreHistory = document.toObject(ScoreHistory.class);
                        historyList.add(scoreHistory);
                    }
                    historyAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load history.", Toast.LENGTH_SHORT).show());
    }
}