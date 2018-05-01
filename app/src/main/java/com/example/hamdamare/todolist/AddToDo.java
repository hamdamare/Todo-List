package com.example.hamdamare.todolist;
/*Citations: https://www.youtube.com/watch?v=RXtj4TxMmW0&t=358s
              May 1 2018
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class AddToDo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }


    public void getname(View view){
        Intent activitythatcalled = getIntent();
        String previousactivity = activitythatcalled.getExtras().getString("callingActivity","MainActivity");


        TextView callingctivitymessage = (TextView) findViewById(R.id.name);
        TextView callingctivitymessage3 = (TextView) findViewById(R.id.date);
        TextView callingctivitymessage4 = (TextView) findViewById(R.id.Description);

        callingctivitymessage.append(" "+previousactivity);
        callingctivitymessage3.append(" "+previousactivity);
        callingctivitymessage4.append(" "+previousactivity);


    }


    //checking if input is in proper format
    public boolean Valid() {
        EditText name = (EditText) (findViewById(R.id.name));
        EditText comment = (EditText) (findViewById(R.id.Description));
        EditText date = (EditText) (findViewById(R.id.date));


        String name_str = name.getText().toString();
        String comment_str = comment.getText().toString();
        String date_str = date.getText().toString();

        boolean valid = true;


        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        //DATE CHECK
        try {
            Date Date = format.parse(date_str);
        } catch (ParseException e) {
            valid = false;
            date.setError("DATE yyyy-mm-dd FORMAT");
            return valid;
        }


        if (name_str.isEmpty()) {
            valid = false;
            name.setError("Enter a Name");
        }


        if (comment_str.isEmpty()) {
            valid = false;
            comment.setError("Enter a Comment");
        }
        return valid;
    }



    //static variable newline to give you a new line
    public static String newline = System.getProperty("line.separator");

    public void oneditlist(View view){

    }


    //sending information to main activity
    public void onsendusersname(View view) {
        if (Valid()) {
            EditText usernameET = (EditText) findViewById(R.id.name);
            EditText userdateET = (EditText) findViewById(R.id.date);
            EditText usercommentET = (EditText) findViewById(R.id.Description);


            String username = String.valueOf(usernameET.getText());
            String userdate = String.valueOf(userdateET.getText());
            String usercomment = String.valueOf(usercommentET.getText());

            String name = ("NAME: " + username);
            String details = ("NAME: " + username+ newline + newline + "Date: " + userdate + newline + "Comments :" + usercomment);
            name.trim();
            details.trim();


            //information thats going back
            Intent intent = new Intent();

            //adding input
            intent.putExtra("name", name);
            intent.putExtra("details",details);
            setResult(RESULT_OK, intent);

            //clearing text for
            userdateET.setText("");
            usercommentET.setText("");
            usernameET.setText("");

            //jumping back to next sceen
            finish();
        }
    }

}

