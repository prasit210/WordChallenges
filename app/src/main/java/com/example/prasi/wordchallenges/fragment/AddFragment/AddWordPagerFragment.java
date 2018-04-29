package com.example.prasi.wordchallenges.fragment.AddFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prasi.wordchallenges.R;
import com.example.prasi.wordchallenges.view.AddWordPagerAdapter;


public class AddWordPagerFragment extends Fragment {
    private ViewPager pager;
    public AddWordPagerFragment() {
        super();
    }

    public static AddWordPagerFragment newInstance() {
        AddWordPagerFragment fragment = new AddWordPagerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_addwordpager, container, false);
        initInstances(rootView);
        pager.setAdapter(new AddWordPagerAdapter(getChildFragmentManager(),getActivity()));
        return rootView;
    }

    private void initInstances(View rootView) {
        pager = (ViewPager) rootView.findViewById(R.id.addpager);
        // Init 'View' instance(s) with rootView.findViewById here
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }
}
