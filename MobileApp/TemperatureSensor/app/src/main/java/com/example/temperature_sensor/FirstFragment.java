package com.example.temperature_sensor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.temperature_sensor.databinding.FragmentFirstBinding;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.ClientProtocolException;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.HttpClientBuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class FirstFragment extends Fragment {
    int temperature = 0;
    String current_temperature = "";
    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }


    private class SetTemperature extends AsyncTask<Integer, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            try {

                URL url = new URL("http://192.168.0.103:8080/api/device/" + integers[0].toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String test;
                StringBuilder response = new StringBuilder();

                while ((test = reader.readLine()) != null) {
                    response.append(test);
                }
                reader.close();

                System.out.println(response.toString());
            } catch (Exception e) {
                System.out.println(e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private class GetTemperature extends AsyncTask<Integer, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            try {

                URL url = new URL("http://192.168.0.103:8080/api/actual/raspberrypi/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String test;
                StringBuilder response = new StringBuilder();

                while ((test = reader.readLine()) != null) {
                    response.append(test);
                }
                reader.close();

//                System.out.println(response.toString());
//                System.out.println(response);
                JSONObject first = new JSONObject(response.toString());
                current_temperature = first.getString("message");
            } catch (Exception e) {
                System.out.println(e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                GetTemperature getTemperature = new GetTemperature();
                getTemperature.execute();

//                System.out.println("Pizda " + current_temperature);
                binding.textView4.setText(current_temperature);
            }
        },0,5000);

        binding.buttonFirst2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temperature += 1;
                SetTemperature setTemperature = new SetTemperature();
                setTemperature.execute(temperature);
                binding.textView.setText(Integer.toString(temperature));
            }
        });

        binding.buttonFirst3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temperature -= 1;
                SetTemperature setTemperature = new SetTemperature();
                setTemperature.execute(temperature);
                binding.textView.setText(Integer.toString(temperature));
            }
        });

        binding.textView.setText(Integer.toString(temperature));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}