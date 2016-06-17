package me.xguox.notespod;


import android.app.ListFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
}
