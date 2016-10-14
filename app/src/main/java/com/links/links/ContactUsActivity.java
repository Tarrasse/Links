package com.links.links;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;

import com.links.links.firebase.ContactUsMessageSender;

public class ContactUsActivity extends AppCompatActivity {

    public static String EXTRA_SERVIC_TYPE = "extra_service_type";

    private EditText mNameInput;
    private EditText mEmailInput;
    private EditText mTopicInput;
    private EditText mMessage;
    private CardView mCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        mNameInput = (EditText) findViewById(R.id.input_name);
        mEmailInput = (EditText) findViewById(R.id.input_email);
        mTopicInput= (EditText) findViewById(R.id.input_topic);
        mMessage= (EditText) findViewById(R.id.input_message);
        mCardView = (CardView) findViewById(R.id.contact_us_cardview);


        Intent intent = getIntent();
        if (intent != null ){
            String service = intent.getStringExtra(EXTRA_SERVIC_TYPE);
            if (service != null){
                mTopicInput.setText(service);
            }
        }

    }



    public void onClick (View V){
        String name = mNameInput.getText().toString();
        String email = mEmailInput.getText().toString();
        String topic = mTopicInput.getText().toString();
        String message = mMessage.getText().toString();

        if (Utility.isEmptyOrNull(name)){
//            Toast.makeText(getApplicationContext(), "PLEASE ENTER NAME", Toast.LENGTH_SHORT).show();
            Snackbar.make(mCardView, "PLEASE ENTER NAME", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }
        if (Utility.isEmptyOrNull(topic)){
//            Toast.makeText(getApplicationContext(), "PLEASE ENTER TOPIC", Toast.LENGTH_SHORT).show();
            Snackbar.make(mCardView, "PLEASE ENTER TOPIC", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }
        if (Utility.isEmptyOrNull(email)){
//            Toast.makeText(getApplicationContext(), "PLEASE ENTER E-MAIL", Toast.LENGTH_SHORT).show();
            Snackbar.make(mCardView, "PLEASE ENTER E-MAIL", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }
        if (Utility.isEmptyOrNull(message)){
//            Toast.makeText(getApplicationContext(), "PLEASE ENTER MESSAGE", Toast.LENGTH_SHORT).show();
            Snackbar.make(mCardView, "PLEASE ENTER MESSAGE", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }


        Intent intent = new Intent(this, ContactUsMessageSender.class);
        intent.putExtra(ContactUsMessageSender.CLIENT_NAME, name);
        intent.putExtra(ContactUsMessageSender.EMAIL, email);
        intent.putExtra(ContactUsMessageSender.SERVICE, topic);
        intent.putExtra(ContactUsMessageSender.MESSAGE, message);
        startService(intent);
        finish();

    }



}
