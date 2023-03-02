package com.example.proyecto1mertrimestre.fragment;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

import com.example.proyecto1mertrimestre.R;

public class PreferenciasFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferencias, rootKey);
    }
}
