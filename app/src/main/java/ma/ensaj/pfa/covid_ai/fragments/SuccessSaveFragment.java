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

public class SuccessSaveFragment extends Fragment {
    private ImageView logo_success;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_succes_save, container, false);

        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    logo_success = root.findViewById(R.id.logo_success);
                    Animation aniZoom = AnimationUtils.loadAnimation(getContext(),R.anim.anim);
                    logo_success.startAnimation(aniZoom);
                    sleep(3000);

                    String userId = getArguments().getString("userId");
                    UploadImageFragment uif = new UploadImageFragment();
                    Bundle args = new Bundle();
                    args.putString("userId", userId);
                    uif.setArguments(args);

//Inflate the fragment
                    getFragmentManager().beginTransaction().replace(R.id.container, uif).commit();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();


        return root;
    }

}
