package com.kdk.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {
	private ArrayList<String> items;
	private ArrayAdapter<String> itemsAdapter;
	private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        lvItems = (ListView) findViewById(R.id.lvTodoItems);
        loadItems();       
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
        
    }
    
    public void addTodoItem(View v){
    	EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    	itemsAdapter.add(etNewItem.getText().toString());
    	etNewItem.setText("");
    	saveItems();
    }
    
    private void setupListViewListener(){
    	lvItems.setOnItemLongClickListener( new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item,
					int pos, long id) {
				items.remove(pos);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
				
			}
    		
    	});
    }
    private void loadItems(){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try{
    		items = new ArrayList<String>(FileUtils.readLines(todoFile));
    	}
    	catch(IOException e){
    		items = new ArrayList<String>();
    		e.printStackTrace();
    	}
    }
    private void saveItems(){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try{
    		FileUtils.writeLines(todoFile, items);
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }
}
