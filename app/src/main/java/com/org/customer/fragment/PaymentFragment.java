package com.org.customer.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.org.clinify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.razorpay.Checkout;

import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class PaymentFragment extends Fragment {

    private static final String TAG = PaymentFragment.class.getSimpleName();
    private String userEmail;
    private String userTimeStamp;
    private String selectedPlan;
    private String userIds;
    private String userId;
    private  SharedPreferences sharedPreferencesMaps;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_payment, container, false);
        Checkout.preload(getActivity());
        Button button = (Button) v.findViewById(R.id.btn_pay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
        sharedPreferencesMaps = getActivity().getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        String rupees = sharedPreferencesMaps.getString("money", "");
        TextView privacyPolicy = (TextView) v.findViewById(R.id.rupeesPayment);
        privacyPolicy.setText(rupees);
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse("https://razorpay.com/sample-application/"));
                startActivity(httpIntent);
            }
        });
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        return  v;

    }

      public void startPayment() {
        final Activity activity = getActivity();

        final Checkout co = new Checkout();
        co.setKeyID("rzp_live_crfioueDztvjB5");

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Clinifie org");
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            sharedPreferencesMaps = getActivity().getSharedPreferences("SharedPrefs", MODE_PRIVATE);
            String rupees = sharedPreferencesMaps.getString("money", "");
            options.put("amount", rupees);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "orgclinify@gmail.com");
            preFill.put("contact", "9096442019");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }
}
