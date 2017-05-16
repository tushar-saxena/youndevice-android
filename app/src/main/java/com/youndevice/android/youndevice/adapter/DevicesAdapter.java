package com.youndevice.android.youndevice.adapter;

import java.util.List;

import com.youndevice.android.youndevice.R;
import com.youndevice.android.youndevice.backend.model.Device;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;



public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.RecyclerViewHolder>{

    public interface DeviceListener {
        void onDeviceToggleChanged(View DeviceView, int DevicePosition, boolean state);
        void onDeviceTextClick(View DeviceView, int DevicePosition);
    }

    private final List<Device> Devices;
    private final DeviceListener DeviceListener;
    public Context context;

    public DevicesAdapter(@NonNull Context context, @NonNull DeviceListener DeviceMenuListener,
                          @NonNull List<Device> Devices) {
        this.context = context;
        this.DeviceListener = DeviceMenuListener;
        this.Devices = Devices;
    }

    public Device getItem(int position) {
        return Devices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return Devices.size();
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_item_device, parent, false);

        return new RecyclerViewHolder(itemView);
    }

    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        final Device currentDevice = getItem(position);

        holder.titleText.setText(currentDevice.getId());
        holder.messageText.setText(currentDevice.getDescription());
        holder.listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeviceListener.onDeviceTextClick(view, position);
            }
        });

        holder.toggleDevice.setChecked(currentDevice.getEnableStatus());

        holder.toggleDevice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                DeviceListener.onDeviceToggleChanged(compoundButton,position,b);
            }
        });
    }

    static final class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_title) TextView titleText;
        @BindView(R.id.text_message) TextView messageText;
        @BindView(R.id.toggle_device) SwitchCompat toggleDevice;
        @BindView(R.id.list_item) LinearLayout listItem;

        RecyclerViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
