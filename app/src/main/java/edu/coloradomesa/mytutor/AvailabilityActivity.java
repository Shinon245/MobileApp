package edu.coloradomesa.mytutor;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AvailabilityActivity extends AppCompatActivity {

    public Button change;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public String available;
    public Map<String, Object> availabilityset = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        // set the view now
        setContentView(R.layout.activity_availability);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        change = (Button) findViewById(R.id.btnFindMe);

        final String username= user.getEmail();


        DocumentReference tutor = db.collection("Tutors").document(username);
        tutor.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    available = (doc.get("availability").toString());
                    if(available == "true") {
                        change.setText("Click to become unavailable");
                        change.setTextSize(48);
                        change.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                    }
                    else {
                        change.setText("Click to become available");
                        change.setTextSize(48);
                        change.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    }
                }
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(change.getText() == "Click to become unavailable") {
                    change.setText("Click to become available");
                    change.setTextSize(48);
                    change.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    db.collection("Tutors").document(username)
                            .update("availability",false);
                    available = "true";
                }
                else {
                    change.setText("Click to become unavailable");
                    change.setTextSize(48);
                    change.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                    db.collection("Tutors").document(username)
                            .update("availability", true);
                    available = "false";
                }
            }
        });
    }
}
