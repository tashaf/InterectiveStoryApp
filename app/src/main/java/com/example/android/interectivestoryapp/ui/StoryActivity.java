package com.example.android.interectivestoryapp.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.interectivestoryapp.R;
import com.example.android.interectivestoryapp.model.Page;
import com.example.android.interectivestoryapp.model.Story;

import java.util.Stack;

public class StoryActivity extends AppCompatActivity {
    public static final String TAG = StoryActivity.class.getSimpleName();
    private Story mStory;

    private String name;
    private ImageView storyImageView;
    private TextView storyTextView;
    private Button choice1Button;
    private Button choice2Button;

    private Stack<Integer> pageStack = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        storyImageView = findViewById(R.id.story_image_view);
        storyTextView = findViewById(R.id.story_text_view);
        choice1Button = findViewById(R.id.button_choice1);
        choice2Button = findViewById(R.id.button_choice2);


        Intent intent = getIntent();
        name = intent.getStringExtra(getString(R.string.name_key));
        if (name == null || name.isEmpty()) {
            name = "NoNameFound";
        }
        Log.d(TAG, "onCreate: " + name);
        Toast.makeText(this, "" + name, Toast.LENGTH_SHORT).show();
        mStory = new Story();
        loadPage(0);
    }

    private void loadPage(int pageNumber) {
        pageStack.push(pageNumber);

        final Page page = mStory.getPage(pageNumber);
        Drawable imageDrawale = ContextCompat.getDrawable(this, page.getImageId());
        storyImageView.setImageDrawable(imageDrawale);
        String pageText = getString(page.getTextId());
        // Add name if placeholder included wo'nt if not
        pageText = String.format(pageText, name);
        storyTextView.setText(pageText);

        if (page.isFinalPage()) {
            choice1Button.setVisibility(View.INVISIBLE);
            choice2Button.setText(R.string.play_again_button_text);

            choice2Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadPage(0);
                }
            });
        } else {

            loadButtons(page);
        }

    }

    private void loadButtons(final Page page) {
        choice1Button.setVisibility(View.VISIBLE);
        choice1Button.setText(page.getChoice1().getTextId());

        choice1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextPage = page.getChoice1().getNextPage();
                loadPage(nextPage);
            }
        });
        choice2Button.setVisibility(View.VISIBLE);
        choice2Button.setText(page.getChoice2().getTextId());

        choice2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextPage = page.getChoice2().getNextPage();
                loadPage(nextPage);
            }
        });
    }

    @Override
    public void onBackPressed() {
        pageStack.pop();
        if (pageStack.empty()) {
            super.onBackPressed();
        } else {
            loadPage(pageStack.pop());
            
        }
    }
}
