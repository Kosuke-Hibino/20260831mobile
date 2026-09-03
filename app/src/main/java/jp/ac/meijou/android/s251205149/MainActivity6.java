package jp.ac.meijou.android.s251205149;

import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.stream.Collectors;

import jp.ac.meijou.android.s251205149.databinding.ActivityMain5Binding;
import jp.ac.meijou.android.s251205149.databinding.ActivityMain6Binding;

public class MainActivity6 extends AppCompatActivity {

    private ActivityMain6Binding binding;
    private ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMain6Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        connectivityManager = getSystemService(ConnectivityManager.class);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        var currentNetwork = connectivityManager.getActiveNetwork();
        updateTransport(currentNetwork);
        updateIPAddress(currentNetwork);
    }

    private void updateTransport(Network network) {
        var caps = connectivityManager.getNetworkCapabilities(network);
        if(caps != null) {
            String transport;
            if(caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                transport = "モバイル通信";
            }else if(caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                transport = "Wi-Fi";
            }else {
                transport = "その他";
            }
            binding.text2.setText(transport);
        }
    }
    private void updateIPAddress(Network network) {
        var linkProperties = connectivityManager.getLinkProperties(network);
        if(linkProperties != null) {
            var addresses = linkProperties.getLinkAddresses().stream()
                    .map(LinkAddress::toString)
                    .collect(Collectors.joining("\n"));
            binding.text4.setText(addresses);
        }
    }
}