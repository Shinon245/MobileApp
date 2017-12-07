package edu.coloradomesa.mytutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class StartUpActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Button btnTutor, btnStudent;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnTutor = (Button) findViewById(R.id.tutor_login_button);
        btnStudent = (Button) findViewById(R.id.student_login_button);

        btnTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MyTutor","signin");
                startActivity(new Intent(StartUpActivity.this, LoginActivity.class));
            }
        });

        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartUpActivity.this, MainActivity.class));
            }
        });
    }
}
