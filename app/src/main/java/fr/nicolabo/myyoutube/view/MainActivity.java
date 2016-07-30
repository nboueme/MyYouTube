package fr.nicolabo.myyoutube.view;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.nicolabo.myyoutube.R;
import fr.nicolabo.myyoutube.model.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.appBarLayout) AppBarLayout appBarLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.tabs) TabLayout tabLayout;
    private ActionBar actionBar;

    private int[] tabIcons = {
            R.drawable.ic_home_white,
            R.drawable.ic_favorite_white,
            R.drawable.ic_settings_white
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*SharedPreferences settings = getSharedPreferences("PREF_THEME", 0);

        String theme = settings.getString("theme", "");
        switch(theme){
            case "dark":
                setTheme(R.style.MyYouTubeTheme_Dark);
                break;
        }*/

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar != null) actionBar.setTitle(R.string.home);

        setupViewPager(viewPager);

        if (tabLayout != null) tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        tabLayout.addOnTabSelectedListener(this);
    }

    private void setupTabIcons() {
        for (int i = 0; i < tabIcons.length; i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
            if (i > 0) tabLayout.getTabAt(i).getIcon().setAlpha(127);
        }
    }

    private void setupViewPager(final ViewPager viewPager) {
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new ListFragment(), getResources().getString(R.string.home));
        adapter.addFragment(new FavoritesFragment(), getResources().getString(R.string.favorites));
        adapter.addFragment(new SettingsFragment(), getResources().getString(R.string.settings));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                appBarLayout.setExpanded(true, true);
                switch (position) {
                    case 0:
                        if (actionBar != null) actionBar.setTitle(adapter.getFragmentTitleList().get(position));
                        break;
                    case 1:
                        if (actionBar != null) actionBar.setTitle(adapter.getFragmentTitleList().get(position));
                        break;
                    case 2:
                        if (actionBar != null) actionBar.setTitle(adapter.getFragmentTitleList().get(position));
                        break;
                    default:
                        if (actionBar != null) actionBar.setTitle(R.string.app_name);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getIcon() != null) tab.getIcon().setAlpha(255);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (tab.getIcon() != null) tab.getIcon().setAlpha(127);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
