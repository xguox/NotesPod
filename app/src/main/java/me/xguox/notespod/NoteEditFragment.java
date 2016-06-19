package me.xguox.notespod;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteEditFragment extends Fragment {

    private ImageButton noteCatBtn;
    private Button saveButton;
    private EditText title, message;
    private Note.Category savedBtnCategory;
    private AlertDialog categoryDialogObject, confirmDialogObject;
    private static final String MODIFIED_CATEGORY = "Modified Category";

    private boolean newNote = false;
    private long noteId  = 0;

    public NoteEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            newNote = bundle.getBoolean(NoteDetailActivity.NEW_NOTE_EXTRA, false);
        }

        if (savedInstanceState != null) {
            savedBtnCategory = (Note.Category) savedInstanceState.get(MODIFIED_CATEGORY);
        }

        View fragmentLaout = inflater.inflate(R.layout.fragment_note_edit, container, false);

        title = (EditText) fragmentLaout.findViewById(R.id.editNoteTitle);
        message = (EditText) fragmentLaout.findViewById(R.id.editNoteMessage);
        noteCatBtn = (ImageButton) fragmentLaout.findViewById(R.id.editNoteBtn);
        saveButton = (Button) fragmentLaout.findViewById(R.id.saveNote);

        Intent intent = getActivity().getIntent();
        title.setText(intent.getExtras().getString(MainActivity.NOTE_TITLE_EXTRA, ""));
        message.setText(intent.getExtras().getString(MainActivity.NOTE_MESSAGE_EXTRA, ""));
        noteId = intent.getExtras().getLong(MainActivity.NOTE_ID_EXTRA, 0);

        if (savedBtnCategory != null) {
            noteCatBtn.setImageResource(Note.categoryToDrawable(savedBtnCategory));
        } else if (!newNote) {
            Note.Category noteCat = (Note.Category) intent.getSerializableExtra(MainActivity.NOTE_CATEGORY_EXTRA);
            savedBtnCategory = noteCat;
            noteCatBtn.setImageResource(Note.categoryToDrawable(noteCat));
        }

        buildCategoryDialog();
        buildConfirmDialog();

        noteCatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryDialogObject.show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialogObject.show();
            }
        });

        return fragmentLaout;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putSerializable(MODIFIED_CATEGORY, savedBtnCategory);
    }

    private void buildCategoryDialog() {
        final String[] categories = new String[] { "PERSONAL", "TECHNICAL", "QUOTE", "FINANCE", "JOKE" };
        AlertDialog.Builder categoryBuilder = new AlertDialog.Builder(getActivity());
        categoryBuilder.setTitle("Choose Note Type");
        categoryBuilder.setSingleChoiceItems(categories, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                categoryDialogObject.cancel();

                switch (which) {
                    case 0:
                        savedBtnCategory = Note.Category.PERSONAL;
                        noteCatBtn.setImageResource(R.drawable.a);
                        break;
                    case 1:
                        savedBtnCategory = Note.Category.TECHNICAL;
                        noteCatBtn.setImageResource(R.drawable.g);
                        break;
                    case 2:
                        savedBtnCategory = Note.Category.QUOTE;
                        noteCatBtn.setImageResource(R.drawable.s);
                        break;
                    case 3:
                        savedBtnCategory = Note.Category.FINANCE;
                        noteCatBtn.setImageResource(R.drawable.t);
                        break;
                    case 4:
                        savedBtnCategory = Note.Category.JOKE;
                        noteCatBtn.setImageResource(R.drawable.z);
                        break;
                }
            }
        });

        categoryDialogObject = categoryBuilder.create();
    }

    private void buildConfirmDialog() {
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(getActivity());

        confirmBuilder.setTitle("Are you sure");
        confirmBuilder.setMessage("Save the note?");

        confirmBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("Save Note", "Note title: " + title.getText() + " Message: "
                        + message.getText() + " Note Category: " + savedBtnCategory);

                DbAdapter dbAdapter = new DbAdapter(getActivity().getBaseContext());
                dbAdapter.open();

                if (newNote) {
                    dbAdapter.createNote(title.getText() + "", message.getText() + "",
                            (savedBtnCategory == null)? Note.Category.PERSONAL : savedBtnCategory);
                } else {
                    dbAdapter.updateNote(noteId, title.getText() + "", title.getText() + "", savedBtnCategory);
                }

                dbAdapter.close();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        confirmBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        confirmDialogObject = confirmBuilder.create();
    }
}
