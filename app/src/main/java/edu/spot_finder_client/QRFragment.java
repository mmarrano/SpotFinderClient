package edu.spot_finder_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import org.json.JSONArray;
import org.json.JSONObject;
import rest.Lot;
import rest.RESTCallback;
import rest.Spot;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link QRFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QRFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private IntentIntegrator scannerIntegrator;
    private View main;
    private boolean checkedIn = false;

    public QRFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment QRFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QRFragment newInstance() {
        return new QRFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        main = inflater.inflate(R.layout.fragment_qr, null, false);
        Button btn = (Button) main.findViewById(R.id.btn_check_in);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchScannerCheckIn();
            }
        });
        return main;
    }

    private void launchScannerCheckIn() {
        scannerIntegrator = IntentIntegrator.forSupportFragment(QRFragment.this);
        scannerIntegrator.setPrompt("Scan barcode");
        scannerIntegrator.setBeepEnabled(true);
        scannerIntegrator.setOrientationLocked(false);
        scannerIntegrator.initiateScan();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (!checkedIn) {
            checkIn(result.getContents());
        } else {
            checkOut(result.getContents());
        }
    }

    private String generateCheckInInfo(Spot spot) {
        Lot lot = spot.getLot();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Checked in to spot: ").append(spot.getId()).append("\n");
        stringBuilder.append("In lot: ").append(lot.getName()).append("\n");
        stringBuilder.append("Lot owned by: ").append(lot.getOwner()).append("\n");
        stringBuilder.append("Lot address: ").append(lot.getAddress()).append("\n");
        stringBuilder.append("Check-In time: ").append(Calendar.getInstance().getTime().toString());
        return stringBuilder.toString();
    }

    private String generateCheckOutInfo(Spot spot) {
        Lot lot = spot.getLot();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Checked out of spot: ").append(spot.getId()).append("\n");
        stringBuilder.append("In lot: ").append(lot.getName()).append("\n");
        stringBuilder.append("Lot owned by: ").append(lot.getOwner()).append("\n");
        stringBuilder.append("Lot address: ").append(lot.getAddress()).append("\n");
        stringBuilder.append("Check-Out time: ").append(Calendar.getInstance().getTime().toString());
        return stringBuilder.toString();
    }

    private void checkIn(String qrResult) {
        final TextView textView = (TextView) main.findViewById(R.id.tv_qr_results);
        Button btn = (Button) main.findViewById(R.id.btn_check_in);
        btn.setText(R.string.checked_in);
        Log.println(Log.INFO, "Result", qrResult);
        //Here im assuming the QR code contained just the spot id
        final Spot spot = new Spot(Integer.parseInt(qrResult));
        spot.checkIn(getActivity().getApplicationContext());
        spot.GET(getActivity().getApplicationContext(), new RESTCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                textView.setText(generateCheckInInfo(spot));
                checkedIn = true;
            }

            @Override
            public void onSuccess(JSONArray result) {

            }
        });

    }

    private void checkOut(String qrResult) {
        final TextView textView = (TextView) main.findViewById(R.id.tv_qr_results);
        Button btn = (Button) main.findViewById(R.id.btn_check_in);
        btn.setText(R.string.not_checked_in);
        Log.println(Log.INFO, "Result", qrResult);
        //Here im assuming the QR code contained just the spot id
        final Spot spot = new Spot(Integer.parseInt(qrResult));
        spot.checkOut(getActivity().getApplicationContext());
        spot.GET(getActivity().getApplicationContext(), new RESTCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                textView.setText(generateCheckOutInfo(spot));
                checkedIn = false;
            }

            @Override
            public void onSuccess(JSONArray result) {

            }
        });
    }

}
