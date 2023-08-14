package com.example.wordwise.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wordwise.R;
import com.example.wordwise.models.Definition;
import com.example.wordwise.models.Meaning;

import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

public class MeaningsAdapter extends ArrayAdapter<Meaning> {

    public MeaningsAdapter(@NonNull Context context, @NonNull List<Meaning> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.meaning_view, parent, false);
        }

        Meaning meaning = getItem(position);
        TextView part_of_speech = listitemView.findViewById(R.id.part_of_speech);
        LinearLayout definitions = listitemView.findViewById(R.id.definitions);
        TagGroup synonyms = listitemView.findViewById(R.id.synonyms);
        TagGroup antonyms = listitemView.findViewById(R.id.antonyms);

        assert meaning != null;
        ArrayList<Definition> definition_list = meaning.getDefinitions();
        ArrayList<String> synonyms_list = meaning.getSynonyms();
        ArrayList<String> antonyms_list = meaning.getAntonyms();

        String str = meaning.getPartOfSpeech();
        String partOfSpeech = str.substring(0, 1).toUpperCase() + str.substring(1);

        part_of_speech.setText(partOfSpeech);

        DefinitionsAdapter definitionsAdapter = new DefinitionsAdapter(this.getContext(), definition_list);

        for (int i = 0; i < definition_list.size(); i++) {
            View itemView = definitionsAdapter.getView(i, null, definitions);
            definitions.addView(itemView);
            ImageView img = new ImageView(this.getContext());
            img.setBackgroundResource(R.drawable.empty_rect);
            definitions.addView(img);
        }

        synonyms.setTags(synonyms_list);
        antonyms.setTags(antonyms_list);

        return listitemView;
    }
}
