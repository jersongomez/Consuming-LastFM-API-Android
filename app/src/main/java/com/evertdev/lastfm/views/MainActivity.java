package com.evertdev.lastfm.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.evertdev.lastfm.R;
import com.evertdev.lastfm.adapters.MainPagerAdapter;
import com.evertdev.lastfm.models.Album;
import com.evertdev.lastfm.models.Artist;
import com.evertdev.lastfm.models.Track;
import com.evertdev.lastfm.views.topalbumslisting.TopAlbumsFragment;
import com.evertdev.lastfm.views.topartistslisting.TopArtistsFragment;
import com.evertdev.lastfm.views.toptrackslisting.TopTracksFragment;
import com.mancj.materialsearchbar.MaterialSearchBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TopArtistsFragment.OnFragmentInteractionListener, TopAlbumsFragment.OnFragmentInteractionListener, TopTracksFragment.OnFragmentInteractionListener {

    @BindView(R.id.tl_main)
    TabLayout mTabLayout;
    @BindView(R.id.vp_main)
    ViewPager mViewPager;
    MainPagerAdapter mAdapter;
    /*@BindView(R.id.edt_search)
    TextInputEditText searchEditText;*/
    @BindView(R.id.searchBar)
    MaterialSearchBar searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        looseSearchEditTextFocus();
        initializeFragments();

        searchEditText.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isValidSearch(searchEditText.getText().toString())) {
                    searchUser(searchEditText.getText().toString());
                } else {
                    showEnterValidUserNameToast();
                }
            }
        });




    }


    private void initializeFragments() {
        mAdapter = new MainPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void showEnterValidUserNameToast() {
        Toast.makeText(this, R.string.please_enter_a_user_name, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidSearch(String search) {
        if (TextUtils.isEmpty(search))
            return false;
        return true;
    }

    // loops the base fragments and notify them to search with the given userName
    private void searchUser(String userName) {
        for (Fragment fr : mAdapter.getFragments()
                ) {
            if (fr instanceof BaseFragment) {
                ((BaseFragment) fr).searchUserName(userName);
            }
        }

    }


    @Override
    public void onArtistClicked(Artist artist) {
        // open artist url
        openUrl(artist.getUrl());
    }

    @Override
    public void onAlbumClicked(Album album) {
        openUrl(album.getUrl());
    }

    @Override
    public void onTrackClicked(Track track) {
        openUrl(track.getUrl());
    }

    void openUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    // hide keyboard after search
    private void looseSearchEditTextFocus() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        searchEditText.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        }
    }
}
