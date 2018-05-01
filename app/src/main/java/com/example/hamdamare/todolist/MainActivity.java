package com.example.hamdamare.todolist;

/*Citations: https://www.youtube.com/watch?v=RXtj4TxMmW0&t=358s
              May 1 2018
 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;




public class MainActivity extends AppCompatActivity {
    private ArrayList<String> arraylist;
    private ArrayAdapter<String> arrayadapter;
    private Object view;
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        arraylist = new ArrayList<String>();
        arrayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist);
        ListView listviewtodo = (ListView) findViewById(R.id.sub_list);
        listviewtodo.setAdapter(arrayadapter);



        //context menu appears here when an item in the list is pressed on
        registerForContextMenu(listviewtodo);


        try{

            //wrapped with scanner to make it easier to read in a line by line
            Log.i("ON CREATE", "HI,the on create has occured");



            Scanner scanner = new Scanner(openFileInput("ToDo.txt"));
            while(scanner.hasNextLine()){
                String todo = scanner.nextLine();
                arrayadapter.add(todo);
                listviewtodo.setAdapter(arrayadapter);

            }
            scanner.close();


        }catch(Exception e){
            Log.i("ON CREATE",e.getMessage());
        }


    }


    //there will be a context menu that appears when the item in the context menu is pressed long
    @Override
    public void onCreateContextMenu(ContextMenu  menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() != R.id.sub_list) {
            return;
        }

        String[] options = {"Details","Delete Task","Edit Task","Return"};
        //set title of context menu then add options
        for(String option : options){
            menu.add(option);

        }

    }



    //if edit navigate back to AddToDO and user can edit input
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //called whenever user selects one of the options from the context menu


        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int selectedIndex = info.position;



        if (item.getTitle().equals("Delete Task")) {
            //adapter cannot remove items by index so we instead use the array list to remove selected option
            arraylist.remove(selectedIndex);
            //notify array adapter to update its content
            arrayadapter.notifyDataSetChanged();
        }


        if(item.getTitle().equals("Edit Task")) {
            Intent intent = new Intent(this,AddToDo.class);
            startActivity(intent);
            arrayadapter.notifyDataSetChanged();
        }


        if(item.getTitle().equals("Details")) {
                myDialog.setContentView(R.layout.popupdescription);

        }


        return true;

    }


    public void getnameonclick(View view) {
        Intent getnameintent = new Intent(this,AddToDo.class);
        final int result = 1;
        getnameintent.putExtra("calling activity", "MainActivity");
        startActivityForResult(getnameintent,result);
        arrayadapter.notifyDataSetChanged();
    }


    //saving all tasks to file when user clicks the back button
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String namesentback = data.getStringExtra("name");
        String detailssentback = data.getStringExtra("details");
        arraylist.add(namesentback);
        System.out.println(namesentback);

        LayoutInflater layout = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layout.inflate(R.layout.popupdescription, null);
        TextView tv = (TextView) popupView.findViewById(R.id.textView);
        tv.setText(detailssentback);

        arrayadapter.notifyDataSetChanged();

    }


    //saving userdata when they exit app
    @Override
    public void onBackPressed() {
        //open an output file write each task and then close it
        try{
            Log.i("ON BACK PRESSED", "Hi, the on back pressed event has occured");

            //openfile output returns an openfile output stream object
            //wrapped with print writter class because itss easier to print out strings to the file
            PrintWriter pw = new PrintWriter(openFileOutput("ToDo.txt", Context.MODE_PRIVATE));
            for(String todo : arraylist){
                pw.println(todo);
            }

            pw.close();

        } catch (Exception e) {
            Log.i("ON BACK PRESSED",e.getMessage());
        }
        finish();
    }


}



