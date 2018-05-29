package info.codestart.daniel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import info.codestart.daniel.Utils.PersonDBHelper;
import info.codestart.daniel.model.Person;

public class UpdateRecordActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private TextView mAgeEditText;
    private TextView mOccupationEditText;
    private TextView mImageEditText;
    private Button mUpdateBtn;

    private PersonDBHelper dbHelper;
    private long receivedPersonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_record);

        //init
        mNameEditText = (EditText)findViewById(R.id.userNameUpdate);
        mAgeEditText = (TextView)findViewById(R.id.userAgeUpdate);
        mOccupationEditText = (TextView) findViewById(R.id.userOccupationUpdate);
        mImageEditText = (TextView) findViewById(R.id.userProfileImageLinkUpdate);
        mUpdateBtn = (Button)findViewById(R.id.updateUserButton);

        dbHelper = new PersonDBHelper(this);

        try {
            //get intent to get person id
            receivedPersonId = getIntent().getLongExtra("USER_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /***populate user data before update***/
        Person queriedPerson = dbHelper.getPerson(receivedPersonId);
        //set field to this user data
        mNameEditText.setText(queriedPerson.getName());
        mAgeEditText.setText(queriedPerson.getAge());
        mOccupationEditText.setText(queriedPerson.getOccupation());
        mImageEditText.setText(queriedPerson.getImage());

        //listen to add button click to update
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                updatePerson();
            }
        });
    }

    private void updatePerson(){
        String name = mNameEditText.getText().toString().trim();
        String age = mAgeEditText.getText().toString().trim();
        String occupation = mOccupationEditText.getText().toString().trim();
        String image = mImageEditText.getText().toString().trim();


        if(name.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter a number", Toast.LENGTH_SHORT).show();
        }


        //create updated person

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
        goBackHome();

    }

    private void goBackHome(){
        startActivity(new Intent(this, MainActivity.class));
    }
}
