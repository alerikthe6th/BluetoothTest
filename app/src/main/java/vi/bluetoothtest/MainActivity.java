package vi.bluetoothtest;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @RequiresPermission(Manifest.permission.BLUETOOTH)
    BluetoothAdapter btAdapter;
    ArrayAdapter<String> listAdapter;
    Button connectNew;
    ListView listView;
    Set<BluetoothDevice> devicesArray;
    ArrayList<String> pairedDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        if(btAdapter == null) {
            Toast.makeText(getApplicationContext(), "No bluetooth detected", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            if(!btAdapter.isEnabled()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, 1);
            }
            getPairedDevices();
        }
    }

    private void init() {
        connectNew = (Button) findViewById(R.id.bConnectNew);
        listView = (ListView) findViewById(R.id.listView);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, 0);
        listView.setAdapter(listAdapter);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        pairedDevices = new ArrayList<String>();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "Bluetooth must be enabled to continue", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void getPairedDevices() {
        devicesArray = btAdapter.getBondedDevices();
        if(devicesArray.size() > 0) {
            for(BluetoothDevice device: devicesArray) {
                listAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
    }
}
