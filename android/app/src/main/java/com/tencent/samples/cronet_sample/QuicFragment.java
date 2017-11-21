

/**
 * Created by lancelotluo on 2017/11/16.
 */

package com.tencent.samples.quic_sample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicLong;

public class QuicFragment extends Fragment {
    private static final String TAG = "MainActivity";
    private SwipeRefreshLayout swipeRefreshLayout;
    private AtomicLong cronetLatency = new AtomicLong();
    private long totalLatency;
    private long numberOfImages;
    private com.tencent.samples.quic_sample.QuicViewAdapter viewAdapter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstancestate) {
        view = inflater.inflate(R.layout.quic_fragment, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.quic_images_activity_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
            }
        });
        loadItems();
        return view;
    }
    private void loadItems() {
        numberOfImages = 0;

        RecyclerView cronetView = (RecyclerView) view.findViewById(R.id.quic_images_view);
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(getActivity(), 3);

        viewAdapter = new com.tencent.samples.quic_sample.QuicViewAdapter(getActivity());
        // In order to enable Netlog, a Cronet logging system, enable write permissions.
        // Find more info about Netlog here:
        // https://www.chromium.org/developers/design-documents/network-stack/netlog
        enableWritingPermissionForLogging();

        cronetView.setLayoutManager(gridLayoutManager);
        cronetView.setAdapter(viewAdapter);
        cronetView.setItemAnimator(new DefaultItemAnimator());
        onItemsLoadComplete();

    }

    private void enableWritingPermissionForLogging() {
        int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        int permission = ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    getActivity(),
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    private void onItemsLoadComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void setUpToolbar() {
        /*Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        getActivity().setSupportActionBar(toolbar);
        getActivity().getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((TextView) toolbar.findViewById(R.id.title)).setText(R.string.toolbar_title);
        */
    }

 }