package edu.coloradomesa.mytutor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    final static String TAG = "MainActivity";
    public boolean isthere = false;
    public List<String> math = new ArrayList<String>();
    public List<String> english = new ArrayList<String>();
    public List<String> accounting = new ArrayList<String>();
    public List<String> compsci = new ArrayList<String>();
    public List<String> compbuis = new ArrayList<String>();

    public static final String PREFERENCES = "MySharedPreferences";
    public FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    class CheckIfDone {
        Boolean isThere1;
        Boolean isThere2;
        public String clase;
        boolean done() { return isThere1 != null && isThere2 != null; }
        void nextStep() {
            if (done()) {
                if (isThere1 || isThere2) {
                    clase = clase + "       Currently Available";
                } else {
                    clase = clase + "       Currently Not Available";
                }
                if (clase.contains("MATH")) {
                    math.add(clase);
                }
                else if (clase.contains("ENGL")) {
                    english.add(clase);
                }
                else if (clase.contains("ACCT")) {
                    accounting.add(clase);
                }
                else if (clase.contains("CSCI")) {
                    compsci.add(clase);
                }
                else if (clase.contains("CISB")) {
                    compbuis.add(clase);
                }
            }
        }

    }
    private void nextWhenDone(final CheckIfDone status, String clase) {
        CollectionReference classesRef = db.collection("Tutors");
        status.clase = clase;

        classesRef.whereEqualTo("class1", clase)
                .whereEqualTo("availability", true)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                boolean myStatus = false;
                if (task.isSuccessful()) {
                    myStatus=!task.getResult().isEmpty();
                }
                status.isThere1 = myStatus;
                status.nextStep();
            }
        });
            classesRef.whereEqualTo("class2", clase)
                    .whereEqualTo("availability", true)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    boolean myStatus = false;
                    if (task.isSuccessful()) {
                        myStatus=!task.getResult().isEmpty();
                    }
                    status.isThere2 = myStatus;
                    status.nextStep();
                }
            });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Accounting Classes");
        listDataHeader.add("Computer Info. Systems Classes");
        listDataHeader.add("Computer Science Classes");
        listDataHeader.add("English Classes");
        listDataHeader.add("Math Classes");


        // adding the math classes along with availability
        CheckIfDone status = new CheckIfDone();
        nextWhenDone(status,"MATH090");
        Log.i(TAG, "Finished Math090");

        CheckIfDone status2 = new CheckIfDone();
        nextWhenDone(status2,"ACCT205");
        Log.i(TAG, "Finished ACCT205");

        CheckIfDone status3 = new CheckIfDone();
        nextWhenDone(status3,"CISB247");
        Log.i(TAG, "Finished CISB247");

        CheckIfDone status4= new CheckIfDone();
        nextWhenDone(status4,"CSCI111");
        Log.i(TAG, "Finished CCSCI111");

        CheckIfDone status5 = new CheckIfDone();
        nextWhenDone(status5,"ENGL112");
        Log.i(TAG, "Finished ENGL112");


        //Collections.sort(math);
        listDataChild.put(listDataHeader.get(0), accounting); // Header, Child data
        listDataChild.put(listDataHeader.get(1), compbuis);
        listDataChild.put(listDataHeader.get(2), compsci);
        listDataChild.put(listDataHeader.get(3), english);
        listDataChild.put(listDataHeader.get(4), math);
    }

    }