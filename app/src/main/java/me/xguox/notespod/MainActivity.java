package me.xguox.notespod;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static final String NOTE_ID_EXTRA = "Note Identifier";
    public static final String NOTE_TITLE_EXTRA = "Note Title";
    public static final String NOTE_MESSAGE_EXTRA = "Note Message";
    public static final String NOTE_CATEGORY_EXTRA = "Note Category";
    public static final String NOTE_FRAGMENT_TO_LOAD_EXTRA = "Fragment To Load";

    public enum FragmentToLaunch { VIEW, EDIT, CREATE }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_add_note) {
            Intent intent = new Intent(this, NoteDetailActivity.class);
            intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA, FragmentToLaunch.CREATE);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
