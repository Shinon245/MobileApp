package edu.coloradomesa.mytutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SetClassesActivity extends AppCompatActivity {
    private TextView mTextMessage1, mTextMessage2;
    private EditText ClassOne, ClassTwo;
    private static final String TAG = "MyActivity";
    private Button submit;
    public boolean isthere = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        // set the view now
        setContentView(R.layout.activity_classes);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final String username= user.getEmail();
        String toput = "Welcome to your class list " + username + "!";

        mTextMessage1 = (TextView) findViewById(R.id.class_help1);
        mTextMessage2 = (TextView) findViewById(R.id.class_help2);
        mTextMessage1.setText(toput);
        ClassOne = (EditText) findViewById(R.id.class_one);
        ClassTwo = (EditText) findViewById(R.id.class_two);
        submit = (Button) findViewById(R.id.btn_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String class1 = ClassOne.getText().toString();
                String class2 = ClassTwo.getText().toString();

                Map<String, Object> classlist = new HashMap<>();
                classlist.put("class1", class1.toUpperCase());
                classlist.put("class2", class2.toUpperCase());
                classlist.put("availability", true);

                db.collection("Tutors").document(username)
                        .set(classlist);

                Toast.makeText(SetClassesActivity.this, "Classes Succesfully Added! Redirecting.", Toast.LENGTH_LONG).show();

                startActivity(new Intent(SetClassesActivity.this, AvailabilityActivity.class));
                finish();
            }
        });
    }
}
