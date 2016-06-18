package me.xguox.notespod;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteEditFragment extends Fragment {


    public NoteEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLaout = inflater.inflate(R.layout.fragment_note_edit, container, false);

        EditText title = (EditText) fragmentLaout.findViewById(R.id.editNoteTitle);
        EditText message = (EditText) fragmentLaout.findViewById(R.id.editNoteMessage);
        ImageButton noteCatBtn = (ImageButton) fragmentLaout.findViewById(R.id.editNoteBtn);

        Intent intent = getActivity().getIntent();
        title.setText(intent.getExtras().getString(MainActivity.NOTE_TITLE_EXTRA));
        message.setText(intent.getExtras().getString(MainActivity.NOTE_MESSAGE_EXTRA));

        Note.Category noteCat = (Note.Category) intent.getSerializableExtra(MainActivity.NOTE_CATEGORY_EXTRA);
        noteCatBtn.setImageResource(Note.categoryToDrawable(noteCat));

        return fragmentLaout;
    }

}
