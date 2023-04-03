package ma.ensaj.pfa.covid_ai.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ma.ensaj.pfa.covid_ai.R;

public class LearnMoreNegativeFragment extends Fragment {
    private ImageView logo_success;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.learn_more_negative_fragment, container, false);
        return root;
    }

}
