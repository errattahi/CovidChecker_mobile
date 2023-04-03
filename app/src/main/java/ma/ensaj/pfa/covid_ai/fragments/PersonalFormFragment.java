package ma.ensaj.pfa.covid_ai.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ma.ensaj.pfa.covid_ai.R;

public class PersonalFormFragment extends Fragment {
    private Spinner genderSpinner,ageSpinner,regionSpinner,smokerSpinner,pcrSpinner,pcrPositiveSpinner;
    private Button next1;
    private TextView lbl_pcr_positive;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_personal_form, container, false);

        genderSpinner = root.findViewById(R.id.gender);
        ageSpinner = root.findViewById(R.id.age);
        regionSpinner = root.findViewById(R.id.region);
        smokerSpinner = root.findViewById(R.id.smoker);
        pcrSpinner = root.findViewById(R.id.pcr);
        pcrPositiveSpinner = root.findViewById(R.id.pcr_positive);
        pcrPositiveSpinner.setVisibility(View.GONE);
        lbl_pcr_positive = root.findViewById(R.id.lbl_pcr_positive);
        lbl_pcr_positive.setVisibility(View.GONE);

        pcrSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if(pcrSpinner.getSelectedItemId()==1){
                    lbl_pcr_positive.setVisibility(View.VISIBLE);
                    pcrPositiveSpinner.setVisibility(View.VISIBLE);
                }
                else {
                    lbl_pcr_positive.setVisibility(View.GONE);
                    pcrPositiveSpinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        next1 = root.findViewById(R.id.next1);

        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gender = genderSpinner.getSelectedItem().toString();
                String age = ageSpinner.getSelectedItem().toString();
                String region = regionSpinner.getSelectedItem().toString();
                String smoker = smokerSpinner.getSelectedItem().toString();
                String pcr = pcrSpinner.getSelectedItem().toString();
                String last_pcr;

                if(pcrPositiveSpinner.getVisibility() == View.VISIBLE){
                    last_pcr = pcrPositiveSpinner.getSelectedItem().toString();
                }else{
                    last_pcr = "";
                }


                DiseasesFragment df = new DiseasesFragment ();
                Bundle args = new Bundle();
                args.putString("gender", gender);
                args.putString("age", age);
                args.putString("region", region);
                args.putString("smoker", smoker);
                args.putString("pcr", pcr);
                args.putString("last_pcr", last_pcr);
                df.setArguments(args);
                //Inflate the fragment
                getFragmentManager().beginTransaction().replace(R.id.container, df).commit();
            }
        });

        return root;
    }

}
