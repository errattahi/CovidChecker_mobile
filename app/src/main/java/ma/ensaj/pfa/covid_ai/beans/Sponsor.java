package ma.ensaj.pfa.covid_ai.beans;

import android.graphics.drawable.Drawable;

public class Sponsor {
    private int logo;
    private String name;
    private String url;

    public Sponsor(int logo, String name, String url) {
        this.logo = logo;
        this.name = name;
        this.url = url;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Sponsor{" +
                "logo=" + logo +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
