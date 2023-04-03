package ma.ensaj.pfa.covid_ai.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import ma.ensaj.pfa.covid_ai.R;
import ma.ensaj.pfa.covid_ai.retrofit.network.RetrofitInstance;
import ma.ensaj.pfa.covid_ai.retrofit.service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SymptomsFragment extends Fragment {
    private CheckBox symptom1,symptom2,symptom3,symptom4,symptom5,symptom6,symptom7,symptom8,symptom9,symptom10,symptom11,symptom12,symptom13;
    private Button finish,previous2;
    private ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_symptoms, container, false);

        symptom1 = root.findViewById(R.id.symptom1);
        symptom2 = root.findViewById(R.id.symptom2);
        symptom3 = root.findViewById(R.id.symptom3);
        symptom4 = root.findViewById(R.id.symptom4);
        symptom5 = root.findViewById(R.id.symptom5);
        symptom6 = root.findViewById(R.id.symptom6);
        symptom7 = root.findViewById(R.id.symptom7);
        symptom8 = root.findViewById(R.id.symptom8);
        symptom9 = root.findViewById(R.id.symptom9);
        symptom10 = root.findViewById(R.id.symptom10);
        symptom11 = root.findViewById(R.id.symptom11);
        symptom12 = root.findViewById(R.id.symptom12);
        symptom13 = root.findViewById(R.id.symptom13);

        checkBoxes.add(symptom1);
        checkBoxes.add(symptom2);
        checkBoxes.add(symptom3);
        checkBoxes.add(symptom4);
        checkBoxes.add(symptom5);
        checkBoxes.add(symptom6);
        checkBoxes.add(symptom7);
        checkBoxes.add(symptom8);
        checkBoxes.add(symptom9);
        checkBoxes.add(symptom10);
        checkBoxes.add(symptom11);
        checkBoxes.add(symptom12);
        checkBoxes.add(symptom13);

        symptom1.setOnClickListener(v -> {
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
        JsonObject paramObject1 = new JsonParser().parse(getArguments().getString("diseases")).getAsJsonObject();

        finish = root.findViewById(R.id.finish);
        previous2 = root.findViewById(R.id.previous2);

        JsonObject paramObject2 = new JsonObject();

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int k = 1;
                for(int i = 0;i<checkBoxes.size();i++){
                    if(checkBoxes.get(i).isChecked()){
                        paramObject2.addProperty("symptom"+k++,checkBoxes.get(i).getText().toString());
                    }
                }

                if(paramObject2.size()!=0){
                    DataService service = RetrofitInstance.getInstance().create(DataService.class);

                    JsonObject paramObject = new JsonObject();
                    paramObject.addProperty("gender", gender);
                    paramObject.addProperty("age", age);
                    paramObject.addProperty("region", region);
                    paramObject.addProperty("smoker", smoker);
                    paramObject.addProperty("pcr", pcr);
                    paramObject.addProperty("last_pcr", last_pcr);
                    paramObject.add("diseases",paramObject1);
                    paramObject.add("symptoms",paramObject2);

                    Log.d("test",paramObject.toString());


                    Call<String> call = service.saveUser(paramObject);

                    //finally performing the call
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                            SuccessSaveFragment ssf = new SuccessSaveFragment();
                            Bundle args = new Bundle();
                            args.putString("userId", response.body());
                            ssf.setArguments(args);

//Inflate the fragment
                            getFragmentManager().beginTransaction().replace(R.id.container, ssf).commit();
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("erreur",t.getMessage());
                        }
                    });
                }



            }
        });
        previous2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiseasesFragment df = new DiseasesFragment();
                Bundle args = new Bundle();
                args.putString("gender", gender);
                args.putString("age", age);
                args.putString("region", region);
                args.putString("smoker", smoker);
                args.putString("pcr", pcr);
                df.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.container, df).commit();
            }
        });

        return root;
    }

}
