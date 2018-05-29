package info.codestart.daniel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

import info.codestart.daniel.Utils.PersonDBHelper;
import info.codestart.daniel.model.Person;

public class AddRecordActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private TextView mAgeEditText;
    private TextView mOccupationEditText;
    private TextView mImageEditText;
    private Button mAddBtn;

    // define variables for the widgets
    private EditText billAmountEditText;
    private TextView percentTextView;
    private Button   percentUpButton;
    private Button   percentDownButton;
    private Button   buttonCalc;
    private Button   buttonHistory;
    private TextView tipTextView;
    private TextView totalTextView;

    // define instance variables that should be saved
    private String billAmountString = "";
    private float tipPercent = .15f;

    private PersonDBHelper dbHelper;
    Date currentTime = Calendar.getInstance().getTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        //init
        mNameEditText = (EditText)findViewById(R.id.billAmountEditText);
        mAgeEditText = (TextView)findViewById(R.id.percentTextView);
        mOccupationEditText = (TextView)findViewById(R.id.tipTextView);
        mImageEditText = (TextView)findViewById(R.id.totalTextView);
        mAddBtn = (Button)findViewById(R.id.addNewUserButton);
        percentUpButton = (Button) findViewById(R.id.percentUpButton);
        percentDownButton = (Button) findViewById(R.id.percentDownButton);
        buttonCalc = (Button) findViewById(R.id.buttonCalc);
        buttonHistory = (Button) findViewById(R.id.buttonHistory);


        //listen to add button click
        mAddBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        savePerson();
                    }
                });

        buttonCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateAndDisplay();
            }
        });

        percentUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipPercent = tipPercent + .01f;
                calculateAndDisplay();
            }
        });
        percentDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipPercent = tipPercent - .01f;
                calculateAndDisplay();
            }
        });

        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackHome();
            }
        });
    }

    public void calculateAndDisplay() {

        // get the bill amount
        billAmountString = mNameEditText.getText().toString();
        float billAmount;
        if (billAmountString.equals("")) {
            billAmount = 0;
        }
        else {
            billAmount = Float.parseFloat(billAmountString);
        }

        // calculate tip and total
        float tipAmount;
        tipAmount = billAmount;
        float totalAmount = billAmount + tipAmount;

        // display the other results with formatting
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        mOccupationEditText.setText(currency.format(tipAmount));
        mImageEditText.setText(currency.format(totalAmount));

        NumberFormat percent = NumberFormat.getPercentInstance();
        mAgeEditText.setText(percent.format(tipPercent));

    }

    private void savePerson(){
        String name = mNameEditText.getText().toString().trim();
        String age = mAgeEditText.getText().toString().trim();
        String occupation = mOccupationEditText.getText().toString().trim();
        String image = mImageEditText.getText().toString().trim();
        String time = currentTime.toString();
        dbHelper = new PersonDBHelper(this);

        if(name.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter a number", Toast.LENGTH_SHORT).show();
        }

        //create new person
        Person person = new Person(name, age, occupation, image, currentTime.toString());
        dbHelper.saveNewPerson(person);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
        goBackHome();

    }

    private void goBackHome(){
        startActivity(new Intent(AddRecordActivity.this, MainActivity.class));
    }
}
