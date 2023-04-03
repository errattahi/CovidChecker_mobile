package ma.ensaj.pfa.covid_ai.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import ma.ensaj.pfa.covid_ai.R;

public class DiseasesFragment extends Fragment {
    private CheckBox disease1,disease2,disease3,disease4,disease5,disease6,disease7,disease8,disease9,disease10,disease11,disease12,disease13,disease14,disease15,disease16;
    private Button next2,previous1;
    private ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_diseases, container, false);

        disease1 = root.findViewById(R.id.disease1);
        disease2 = root.findViewById(R.id.disease2);
        disease3 = root.findViewById(R.id.disease3);
        disease4 = root.findViewById(R.id.disease4);
        disease5 = root.findViewById(R.id.disease5);
        disease6 = root.findViewById(R.id.disease6);
        disease7 = root.findViewById(R.id.disease7);
        disease8 = root.findViewById(R.id.disease8);
        disease9 = root.findViewById(R.id.disease9);
        disease10 = root.findViewById(R.id.disease10);
        disease11 = root.findViewById(R.id.disease11);
        disease12 = root.findViewById(R.id.disease12);
        disease13 = root.findViewById(R.id.disease13);
        disease14 = root.findViewById(R.id.disease14);
        disease15 = root.findViewById(R.id.disease15);
        disease16 = root.findViewById(R.id.disease16);

        checkBoxes.add(disease1);
        checkBoxes.add(disease2);
        checkBoxes.add(disease3);
        checkBoxes.add(disease4);
        checkBoxes.add(disease5);
        checkBoxes.add(disease6);
        checkBoxes.add(disease7);
        checkBoxes.add(disease8);
        checkBoxes.add(disease9);
        checkBoxes.add(disease10);
        checkBoxes.add(disease11);
        checkBoxes.add(disease12);
        checkBoxes.add(disease13);
        checkBoxes.add(disease14);
        checkBoxes.add(disease15);
        checkBoxes.add(disease16);

        disease1.setOnClickListener(v -> {
            if (((CheckBox) v).isChecked()) {
                for(int i = 1;i<checkBoxes.size();i++){
                    checkBoxes.get(i).setEnabled(false);
                    checkBoxes.get(i).setChecked(false);
                }
            }else{
                for(int i = 1;i<checkBoxes.size();i++){
                    checkBoxes.get(i).setEnabled(true);
                }
            }
        });

        String gender = getArguments().getString("gender");
        String age = getArguments().getString("age");
        String region = getArguments().getString("region");
        String smoker = getArguments().getString("smoker");
        String pcr = getArguments().getString("pcr");
        String last_pcr = getArguments().getString("last_pcr");

        next2 = root.findViewById(R.id.next2);
        previous1 = root.findViewById(R.id.previous1);

        JsonObject paramObject = new JsonObject();

        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k = 1;
                for(int i = 0;i<checkBoxes.size();i++){
                    if(checkBoxes.get(i).isChecked()){
                        paramObject.addProperty("disease"+k++,checkBoxes.get(i).getText().toString());
                    }
                }

                if(paramObject.size()!=0){

                    SymptomsFragment sf = new SymptomsFragment ();
                    Bundle args = new Bundle();
                    args.putString("gender", gender);
                    args.putString("age", age);
                    args.putString("region", region);
                    args.putString("smoker", smoker);
                    args.putString("pcr", pcr);
                    args.putString("last_pcr", last_pcr);
                    args.putString("diseases", String.valueOf(paramObject));
                    sf.setArguments(args);

//Inflate the fragment
                    getFragmentManager().beginTransaction().replace(R.id.container, sf).commit();
                }

            }
        });

        previous1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalFormFragment pff = new PersonalFormFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, pff).commit();
            }
        });

        return root;
    }

}
