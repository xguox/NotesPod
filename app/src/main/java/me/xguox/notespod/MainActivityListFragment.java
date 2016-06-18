package me.xguox.notespod;


import android.app.ListFragment;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityListFragment extends ListFragment {

    private  ArrayList<Note> notes;
    private  NoteAdapter noteAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*
        String[] values = new String[] { "Android", "Windows", "Linux", "macOS",
                "Ubuntu", "iOS", "BlackBerry", "Arch Linux", "OS/2", "Win7", "XP", "Win10" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);

        setListAdapter(adapter);
        */

        notes = new ArrayList<Note>();
        notes.add(new Note("This is a new Note Title",
                "This is the body of my note@@@!!!", Note.Category.FINANCE));
        notes.add(new Note("Android App Development Tutorial 43 ",
                "Android App Development Tutorial - 43 - Creating Row Layout :" +
                        " Custom ArrayAdapter 01 Learn free for ...", Note.Category.JOKE));
        notes.add(new Note("Tutorial Aplikasi Android -",
                "Tutorial Pembangunan Aplikasi Android - Creating " +
                        "Row Layout Custom ArrayAdapter - Membuat Row ...\n", Note.Category.PERSONAL));
        notes.add(new Note("Part 1 Creating Row Layout ...",
                "Implementing Custom ArrayAdapter Part 1 Creating " +
                        "Row Layout 4925980. jothi prakash ... Android App ...", Note.Category.QUOTE));
        notes.add(new Note("This is a new Note Title",
                "This is the body of my note@@@!!!", Note.Category.TECHNICAL));
        notes.add(new Note("Tutorial 43 creating an form - The Nail",
                "This video of Android App Development Tutorial 43 Creating Row Layout Custom Arrayadapter 01 w" +
                        "as uploaded by TutorialBaba on February 17, 2016.", Note.Category.TECHNICAL));
        notes.add(new Note("This is a new Note Title",
                "This is the body of my note@@@!!!", Note.Category.QUOTE));
        notes.add(new Note("Tutorial no 43 creating realistic fabric shaders in arnold for c",
                "This video of Android App Development Tutorial 43 Creating Row Layout Custom Arrayadapter 01 was" +
                        " uploaded by TutorialBaba on" +
                        " May 27, 2016. Play Video.@@!!!", Note.Category.FINANCE));

        noteAdapter = new NoteAdapter(getActivity(), notes);
        setListAdapter(noteAdapter);

        registerForContextMenu(getListView());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        launchNoteDetailActivity(MainActivity.FragmentToLaunch.VIEW, position);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int rowPosition = info.position;

        switch (item.getItemId()) {
            case R.id.edit:
                launchNoteDetailActivity(MainActivity.FragmentToLaunch.EDIT, rowPosition);
                Log.d("Menu Clicked!", "We press edit!");
                return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu, menu);
    }

    private void launchNoteDetailActivity(MainActivity.FragmentToLaunch ftl, int position) {
        Note note = (Note) getListAdapter().getItem(position);

        Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
        intent.putExtra(MainActivity.NOTE_ID_EXTRA, note.getId());
        intent.putExtra(MainActivity.NOTE_TITLE_EXTRA, note.getTitle());
        intent.putExtra(MainActivity.NOTE_MESSAGE_EXTRA, note.getMessage());
        intent.putExtra(MainActivity.NOTE_CATEGORY_EXTRA, note.getCategory());

        switch(ftl) {
            case VIEW:
                intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA, MainActivity.FragmentToLaunch.VIEW);
                break;
            case EDIT:
                intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA, MainActivity.FragmentToLaunch.EDIT);
                break;
        }

        startActivity(intent);
    }
}
