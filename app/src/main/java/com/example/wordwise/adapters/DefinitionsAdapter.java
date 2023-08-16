package com.example.wordwise.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wordwise.R;
import com.example.wordwise.models.Definition;

import java.util.List;

public class DefinitionsAdapter extends ArrayAdapter<Definition> {
    public DefinitionsAdapter(@NonNull Context context, @NonNull List<Definition> objects) {
        super(context, 0, objects);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.definition_view, parent, false);
        }
        Definition def = getItem(position);
        assert def != null;
        TextView definition = listitemView.findViewById(R.id.definition);
        definition.setText(def.getDefinition());
        TextView example = listitemView.findViewById(R.id.example);
        if (!def.getExample().equals("")){
            example.setVisibility(View.VISIBLE);
            example.setText('"'+ def.getExample() +'"');
        }

        return listitemView;
    }
}
