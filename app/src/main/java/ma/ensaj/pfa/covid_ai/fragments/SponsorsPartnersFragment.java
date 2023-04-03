package ma.ensaj.pfa.covid_ai.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import ma.ensaj.pfa.covid_ai.R;
import ma.ensaj.pfa.covid_ai.adapters.CustomPagerAdapter;
import ma.ensaj.pfa.covid_ai.beans.Sponsor;

public class SponsorsPartnersFragment extends Fragment {
    private ImageView logo_success;
    private List<Sponsor> sponsorList;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.sponsors_partners_fragment, container, false);

        sponsorList = new ArrayList<Sponsor>();
        sponsorList.add(new Sponsor(R.mipmap.cnrst,"National Center for Scientific and Technical Research","https://www.cnrst.ma/index.php/fr/"));
        sponsorList.add(new Sponsor(R.mipmap.ensaj,"National School of Applied Sciences - El-Jadida","http://www.ensaj.ucd.ac.ma"));
        sponsorList.add(new Sponsor(R.mipmap.ucd,"Chouaib Doukkali University","http://www.ucd.ac.ma"));
        sponsorList.add(new Sponsor(R.mipmap.uphdf,"University Polytechnic Hauts-de-France","https://www.uphf.fr/"));
        sponsorList.add(new Sponsor(R.mipmap.uh2,"Hassan II University of Casablanca","http://www.univh2c.ma/"));

        ViewPager viewPager = (ViewPager) root.findViewById(R.id.viewpager);
        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(root.getContext());

        customPagerAdapter.setSponsorList(sponsorList);
        viewPager.setAdapter(customPagerAdapter);

        return root;
    }

}
