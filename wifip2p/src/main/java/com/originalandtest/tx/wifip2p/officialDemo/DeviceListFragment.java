package com.originalandtest.tx.wifip2p.officialDemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.originalandtest.tx.wifip2p.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taxi on 2017/2/20.
 */

public class DeviceListFragment extends ListFragment implements WifiP2pManager.PeerListListener {

    private final  List<WifiP2pDevice> peers = new ArrayList<>();
    private View mContentView;
    private WifiP2pDevice device;
    private ProgressDialog progressDialog;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new WiFiPeerListAdapter(getActivity(), R.layout.item_wifi, peers));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.device_list, container, false);
        return mContentView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        WifiP2pDevice device = (WifiP2pDevice) getListAdapter().getItem(position);
        if (getActivity() instanceof DeviceActionListener) {
            ((DeviceActionListener) getActivity()).showDetail(device);
        } else {
            throw new IllegalArgumentException("activity must implements DeviceActionListener");
        }
    }

    class WiFiPeerListAdapter extends ArrayAdapter<WifiP2pDevice> {
        private final List<WifiP2pDevice> items;

        public WiFiPeerListAdapter(Context context, int textViewResourceId, List<WifiP2pDevice> objects) {
            super(context, textViewResourceId, objects);
            this.items = objects;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                v = LayoutInflater.from(getActivity()).inflate(R.layout.item_wifi, null);
            }

            WifiP2pDevice item = getItem(position);
            if (item != null) {
                TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
                TextView tvContent = (TextView) v.findViewById(R.id.tvContent);

                tvTitle.setText(item.deviceName);
                tvContent.setText(getWifiContent(item));
            }
            return v;
        }
    }

    private String getWifiContent(WifiP2pDevice device) {
        String content = "";
        switch (device.status) {
            case WifiP2pDevice.AVAILABLE:
                content = "available";
                break;
            case WifiP2pDevice.CONNECTED:
                content = "connected";
                break;

            case WifiP2pDevice.FAILED:
                content = "failed";
                break;

            case WifiP2pDevice.INVITED:
                content = "invited";
                break;

            case WifiP2pDevice.UNAVAILABLE:
                content = "unavailable";
                break;

            default:
                content = "unknown";
                break;
        }
        return content;
    }


    public void updateDevice(WifiP2pDevice device) {
        this.device = device;
        TextView view = (TextView) mContentView.findViewById(R.id.my_name);
        view.setText(device.deviceName);
        view = (TextView) mContentView.findViewById(R.id.my_status);
        view.setText(getWifiContent(device));
    }

    public void initDiscovery() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        progressDialog = ProgressDialog.show(getActivity(), "Press back to cancel", "find peers", true, true
                , new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Log.d("taxi", "cancel");
                    }
                });
    }


    @Override
    public void onPeersAvailable(WifiP2pDeviceList peersList) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        peers.clear();
        peers.addAll(peersList.getDeviceList());
        ((WiFiPeerListAdapter)getListAdapter()).notifyDataSetChanged();
        if (peers.size() == 0) {

        }

    }


    public interface DeviceActionListener {

        void showDetail(WifiP2pDevice device);

        void cancelConnect();

        void connect(WifiConfiguration configuration);

        void disConnect();
    }
}

