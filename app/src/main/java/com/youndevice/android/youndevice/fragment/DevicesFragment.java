package com.youndevice.android.youndevice.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.youndevice.android.youndevice.R;
import com.youndevice.android.youndevice.activity.DeviceDetailActivity;
import com.youndevice.android.youndevice.adapter.DevicesAdapter;
import com.youndevice.android.youndevice.backend.BackendClient;
import com.youndevice.android.youndevice.backend.model.Device;
import com.youndevice.android.youndevice.util.ColorSchemer;
import com.youndevice.android.youndevice.util.ErrorUtil;
import com.youndevice.android.youndevice.util.Intents;
import com.youndevice.android.youndevice.util.ViewDirector;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class DevicesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        DevicesAdapter.DeviceListener, SearchView.OnQueryTextListener {

    @BindView(R.id.list) RecyclerView recyclerView;
    @BindView(R.id.content) SwipeRefreshLayout contentLayout;

    ArrayList<Device> triggers;
    private boolean isDevicesFragmentAvailable;
    public SearchView searchView;
    public String searchText;
    public DevicesAdapter triggersAdapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        isDevicesFragmentAvailable = true;
        setUpBindings();
        setUpRefreshing();
        setUpDevices();
        setUpList();
        setUpState(state);
        setUpMenu();
    }

    private void setUpState(Bundle state) {
        Icepick.restoreInstanceState(this, state);
    }

    private void setUpList() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setUpBindings() {
        ButterKnife.bind(this, getView());
    }

    private void setUpRefreshing() {
        contentLayout.setOnRefreshListener(this);
        contentLayout.setColorSchemeResources(ColorSchemer.getScheme());
    }

    private void setUpMenu() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onRefresh() {
        setUpDevicesForced();
    }

    private void setUpDevicesForced() {
        BackendClient.of(this).getDevices(new DevicesCallback(this));
    }

    @OnClick(R.id.button_retry)
    public void setUpDevices() {
        if (triggers == null) {
            showProgress();
            setUpDevicesForced();
        } else {
            setUpDevices(triggers);
        }
    }

    private void showProgress() {
        ViewDirector.of(this).using(R.id.animator).show(R.id.progress);
    }

    private void setUpDevices(List<Device> triggers) {
        this.triggers = new ArrayList<>(triggers);

        sortDevices(this.triggers);

        recyclerView.setAdapter(new DevicesAdapter(getActivity(), this, triggers));

        hideRefreshing();

        showList();
    }

    private void sortDevices(List<Device> triggers) {
        Collections.sort(triggers, new DevicesComparator());
    }

    private void hideRefreshing() {
        contentLayout.setRefreshing(false);
    }

    private void showList() {
        ViewDirector.of(this).using(R.id.animator).show(R.id.content);
    }

    private void showMessage() {
        ViewDirector.of(this).using(R.id.animator).show(R.id.message);
    }

    private DevicesAdapter getDevicesAdapter() {
        return (DevicesAdapter) recyclerView.getAdapter();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.search, menu);

            MenuItem item = menu.findItem(R.id.menu_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        if (searchText != null) {
            searchView.setQuery(searchText, false);
        }
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (triggers != null && triggers.size() != 0) {
            if (!TextUtils.isEmpty(query)) {
                ArrayList<Device> filteredMetrics = new ArrayList<>();
                filteredMetrics.clear();
                for (int i = 0; i < triggers.size(); i++) {
                    String alertID = triggers.get(i).getId().toLowerCase();
                    if (alertID.contains(query.toLowerCase())) {
                        filteredMetrics.add(triggers.get(i));
                    }
                }
                triggersAdapter = new DevicesAdapter(getActivity(), this, filteredMetrics);
                recyclerView.setAdapter(triggersAdapter);
                searchText = query;
            } else {
                triggersAdapter = new DevicesAdapter(getActivity(), this, this.triggers);
                recyclerView.setAdapter(triggersAdapter);
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        tearDownState(state);
    }

    private void tearDownState(Bundle state) {
        Icepick.saveInstanceState(this, state);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        isDevicesFragmentAvailable = false;
    }

    @Override
    public void onDeviceToggleChanged(View DeviceView, int triggerPosition, boolean state) {
        Device updatedDevice = this.triggers.get(triggerPosition);
        updatedDevice.setEnabledStatus(state);
        if (state) {
            Snackbar snackbar = Snackbar.make(getView(), R.string.device_on, Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else {
            Snackbar snackbar = Snackbar.make(getView(), R.string.device_off, Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    @Override
    public void onDeviceTextClick(View triggerView, int triggerPosition) {
        Intent intent = new Intent(getActivity(), DeviceDetailActivity.class);
        Device device = getDevicesAdapter().getItem(triggerPosition);
        intent.putExtra(Intents.Extras.DEVICE, device.getId());
        startActivity(intent);
    }

    private static final class DevicesCallback implements Callback<List<Device>> {

        DevicesFragment fragment;

        public DevicesCallback(DevicesFragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {

            if (getDevicesFragment().isDevicesFragmentAvailable && response.isSuccessful()) {
                List<Device> devices = response.body();
                if (!devices.isEmpty()) {
                    getDevicesFragment().setUpDevices(devices);
                } else {
                    getDevicesFragment().showMessage();
                }
            } else {
                ErrorUtil.showError(getDevicesFragment(), R.id.animator, R.id.error);
            }

        }

        private DevicesFragment getDevicesFragment() {
            return fragment;
        }


        @Override
        public void onFailure(Call<List<Device>> call, Throwable t) {
            Timber.d(t, "Devices fetching failed.");

            if (getDevicesFragment().isDevicesFragmentAvailable) {
                ErrorUtil.showError(getDevicesFragment(), R.id.animator, R.id.error);
            }
        }
    }

    private static final class DevicesComparator implements Comparator<Device> {
        @Override
        public int compare(Device leftDevice, Device rightDevice) {
            String leftDeviceDescription = leftDevice.getId();
            String rightDeviceDescription = rightDevice.getId();

            return leftDeviceDescription.compareTo(rightDeviceDescription);
        }
    }
}
