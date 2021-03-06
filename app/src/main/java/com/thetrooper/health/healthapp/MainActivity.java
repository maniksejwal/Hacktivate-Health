package com.thetrooper.health.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.thetrooper.health.healthapp.Chat.ChatActivity;

import java.util.Arrays;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    private final int RC_SIGN_IN = 666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, ChatActivity.class));
        } else {
            // not signed in
            startActivityForResult(
                    // Get an instance of AuthUI based on the default app
                    AuthUI.getInstance().createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                                    new AuthUI.IdpConfig.FacebookBuilder().build()))
                            .build(),
                    RC_SIGN_IN);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, FormActivity.class));
                finish();
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, "No network", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(this, "Unknown Error", Toast.LENGTH_SHORT).show();
                Timber.e("Sign-in error: ", response.getError());
            }
        }
    }
}
