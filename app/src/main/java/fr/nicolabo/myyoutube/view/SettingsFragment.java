package fr.nicolabo.myyoutube.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.nicolabo.myyoutube.R;

/**
 * Created by nicolasboueme on 16/05/2016.
 */
public class SettingsFragment extends Fragment {

    private TextView textView;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tablayout_settings, container, false);

        textView = (TextView) view.findViewById(R.id.settings);
        textView.setText("Mais oui c'est clair");

        return view;
    }
}
