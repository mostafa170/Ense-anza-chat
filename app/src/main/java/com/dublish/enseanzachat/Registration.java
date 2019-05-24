package com.dublish.enseanzachat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dublish.enseanzachat.Base.BaseActivity;
import com.dublish.enseanzachat.FireBaseUtils.UsersDao;
import com.dublish.enseanzachat.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Registration extends BaseActivity implements View.OnClickListener {

    protected TextInputLayout userName;
    protected TextInputLayout email;
    protected TextInputLayout password;
    protected Button register;
    protected TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_registration);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register) {
            String sUsername = userName.getEditText().getText().toString();
            if (sUsername.trim().equals("")) {
                userName.setError(getString(R.string.required));
                return;
            }
            userName.setError(null);
            String sPassword = password.getEditText().getText().toString();
            if (sPassword.trim().equals("")) {
                password.setError(getString(R.string.required));
                return;

            } else if (sPassword.length() < 6) {
                password.setError(getString(R.string.min_6_chars));
                return;

            }
            password.setError(null);

            String sEmail = email.getEditText().getText().toString();
            if (!isEmailValid(sEmail)) {
                email.setError(getString(R.string.not_valid));
                return;

            }
            email.setError(null);

            User user = new User(sUsername, sPassword, sEmail);
            registerUser(user);

        } else if (view.getId() == R.id.login) {
            startActivity(new Intent(activity,Login.class));
            finish();
        }
    }

    public void registerUser(final User user) {
        DataHolder.currentUser = user;
        showProgressBar(R.string.loading, R.string.please_wait);
        UsersDao.getUsersByEmail(user.getEmail())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        hideProgressBar();
                        if (dataSnapshot.hasChildren()) {
                            showMessage(R.string.error, R.string.email_registered_before);
                        } else {
                            showProgressBar(R.string.loading, R.string.please_wait);
                            UsersDao.AddUser(user, onUserAdded, onUserAddFail);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        hideProgressBar();
                        showMessage(getString(R.string.error), databaseError.getMessage());

                    }
                });


    }

    boolean isEmailValid(String email) {
        if (!TextUtils.isEmpty(email) &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return true;
        return false;

    }

    OnSuccessListener onUserAdded = new OnSuccessListener() {
        @Override
        public void onSuccess(Object o) {
            hideProgressBar();
            saveStringValue("email",DataHolder.currentUser.getEmail());
            saveStringValue("password",DataHolder.currentUser.getPassword());


            showConfirmationMessage(R.string.success, R.string.user_added, R.string.ok,
                    new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            startActivity(new Intent(activity, HomeActivity.class));
                            finish();

                        }
                    });
        }
    };
    OnFailureListener onUserAddFail = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            hideProgressBar();
            showMessage(getString(R.string.fail), e.getLocalizedMessage());

        }
    };

    private void initView() {
        userName = (TextInputLayout) findViewById(R.id.user_name);
        email = (TextInputLayout) findViewById(R.id.email);
        password = (TextInputLayout) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(Registration.this);
        login = (TextView) findViewById(R.id.login);
        login.setOnClickListener(Registration.this);
    }
}
