package com.example.wordwise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.wordwise.adapters.MeaningsAdapter;
import com.example.wordwise.models.Definition;
import com.example.wordwise.models.Meaning;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ShimmerFrameLayout shimmer_layout;
    TextInputLayout new_word;
    ListView main_meanings;
    TextView phonetic;
    MaterialButton define_btn;
    ImageButton sound_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DynamicColors.applyToActivitiesIfAvailable(MainActivity.this.getApplication());

        shimmer_layout = findViewById(R.id.shimmer_layout);
        new_word = findViewById(R.id.new_word);
        main_meanings = findViewById(R.id.main_meanings);
        define_btn = findViewById(R.id.define_btn);
        phonetic = findViewById(R.id.phonetic);
        sound_btn = findViewById(R.id.sound_btn);

        RequestQueue requestQueue = Volley.newRequestQueue(this);


        define_btn.setOnClickListener(view -> {
            new_word.clearFocus();

            shimmer_layout.setVisibility(View.VISIBLE);
            shimmer_layout.startShimmerAnimation();

            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

            String word = Objects.requireNonNull(Objects.requireNonNull(new_word.getEditText()).getText()).toString().trim();
            String request_string = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;


            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_string, null, response -> {
                try {
                    String phoneticString = response.getJSONObject(0).optString("phonetic");

                    String link = "";
                    int i = 0;
                    while (link.equals("")){
                        try {
                            link = response.getJSONObject(0).getJSONArray("phonetics").getJSONObject(i).optString("audio");
                        } catch (Exception ignored){
                            Toast.makeText(MainActivity.this, "Something in audio went wrong!!!", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        i++;
                    }
                    if (!link.equals("")){
                        MediaPlayer sound = new MediaPlayer();
                        sound.setDataSource(link);
                        sound.setOnPreparedListener(mediaPlayer -> sound_btn.setOnClickListener(view1 -> mediaPlayer.start()));
                        sound.prepareAsync();
                        sound_btn.setVisibility(View.VISIBLE);
                    }
                    phonetic.setText(phoneticString);
                    JSONArray objects = response.getJSONObject(0).getJSONArray("meanings");
                    ArrayList<Meaning> meanings = new ArrayList<>();
                    for (i = 0; i < objects.length(); i++) {
                        JSONObject object = objects.getJSONObject(i);
                        ArrayList<Definition> definitions = new ArrayList<>();
                        JSONArray response_definitions = object.getJSONArray("definitions");
                        for (int j = 0; j < response_definitions.length(); j++) {
                            Definition definition = new Definition();
                            JSONObject response_definition = response_definitions.getJSONObject(j);
                            definition.setDefinition(response_definition.getString("definition"));
                            definition.setExample(response_definition.optString("example"));
                            definitions.add(definition);
                        }
                        ArrayList<String> synonyms = new ArrayList<>();
                        JSONArray response_synonyms = object.getJSONArray("synonyms");
                        for (int j = 0; j < response_synonyms.length(); j++) {
                            synonyms.add(response_synonyms.getString(j));
                        }
                        ArrayList<String> antonyms = new ArrayList<>();
                        JSONArray response_antonyms = object.getJSONArray("antonyms");
                        for (int j = 0; j < response_antonyms.length(); j++) {
                            antonyms.add(response_antonyms.getString(j));
                        }
                        Meaning meaning = new Meaning();
                        meaning.setPartOfSpeech(object.getString("partOfSpeech"));
                        meaning.setDefinitions(definitions);
                        meaning.setSynonyms(synonyms);
                        meaning.setAntonyms(antonyms);
                        meanings.add(meaning);
                    }

                    shimmer_layout.stopShimmerAnimation();
                    shimmer_layout.setVisibility(View.GONE);
                    MeaningsAdapter meaningsAdapter = new MeaningsAdapter(MainActivity.this, meanings);
                    main_meanings.setAdapter(meaningsAdapter);
                } catch (JSONException | IOException e) {
                    throw new RuntimeException(e);
                }
            }, error -> Toast.makeText(MainActivity.this, "Something went wrong!!!", Toast.LENGTH_SHORT).show());

            requestQueue.add(jsonArrayRequest);
        });
    }
}