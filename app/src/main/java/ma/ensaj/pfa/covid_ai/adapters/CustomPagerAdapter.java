package ma.ensaj.pfa.covid_ai.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import org.checkerframework.common.subtyping.qual.Bottom;

import java.util.List;

import ma.ensaj.pfa.covid_ai.R;
import ma.ensaj.pfa.covid_ai.beans.Sponsor;

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<Sponsor> sponsorList;

    public CustomPagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Sponsor current = sponsorList.get(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View itemView = inflater.inflate(R.layout.sponsor_item, container, false);

        ImageView logo = (ImageView) itemView.findViewById(R.id.logo_sponsor);
        TextView name = (TextView) itemView.findViewById(R.id.name_sponsor);
        TextView visit = (Button) itemView.findViewById(R.id.visit);
        ProgressBar page_progress = (ProgressBar) itemView.findViewById(R.id.page_progress);


        page_progress.setProgress((int) (((position+1)/(double)sponsorList.size())*100));

        logo.setImageResource(current.getLogo());
        name.setText(current.getName());
        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse(current.getUrl());
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                container.getContext().startActivity(launchBrowser);
            }
        });

        container.addView(itemView);
        // Return the page
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        if (sponsorList != null)
            return sponsorList.size();
        else return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return sponsorList.get(position).getName();
    }

    public void setSponsorList(List<Sponsor> sponsorList) {
        this.sponsorList = sponsorList;
    }
}
