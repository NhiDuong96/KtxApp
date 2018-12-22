package com.example.minhnhi.quanlyktx.view.login;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.Gender;
import com.example.minhnhi.quanlyktx.beans.UserAccount;
import com.example.minhnhi.quanlyktx.beans.AccountRegister;
import com.example.minhnhi.quanlyktx.utils.AccountManager;
import com.example.minhnhi.quanlyktx.utils.InputView;
import com.example.minhnhi.quanlyktx.view.user.UserActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterUserDetailsFragment extends Fragment {

    private String mssv;

    private String password;


    private static List<String> years = new ArrayList<>();
    static {
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= 1975; i--)
        {
            years.add(String.valueOf(i));
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.activity_register_user_details, container, false);

        final InputView name, className, address;
        final EditText phone;

        name = view.findViewById(R.id.name);

        className = view.findViewById(R.id.className);

        address = view.findViewById(R.id.address);

        phone = view.findViewById(R.id.phone);


        Spinner gender = view.findViewById(R.id.gender);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, Gender.keys());
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        gender.setAdapter(adapter);

        Spinner year = view.findViewById(R.id.year);
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, years);
        year.setAdapter(adapter);
        year.setSelection(0);

        view.findViewById(R.id.btn_sign_in).setOnClickListener(v -> {
            AccountManager accountManager = AccountManager.getAccountManager();
            accountManager.setListener(new AccountManager.OnLoginSuccessListener() {
                @Override
                public void onLoggedSuccess(UserAccount account) {
                    Intent intent = new Intent(RegisterUserDetailsFragment.this.getContext(), UserActivity.class);
                    intent.putExtra("account", account);
                    startActivity(intent);
                }

                @Override
                public void onLoggedFailed(String msg) {
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                }
            });

            AccountRegister req = new AccountRegister();
            req.setFullName(name.getText());
            req.setAddress(address.getText());
            req.setMssv(getMssv());
            req.setNameClass(className.getText());
            req.setPhone(phone.getText().toString());
            req.setUserName(getMssv());
            req.setPassword(getPassword());
            req.setGender(gender.getSelectedItemPosition());

            Resources res = getResources();
            String uri = res.getString(R.string.host) + res.getString(R.string.sign_in_uri);
            accountManager.signIn(req, uri);
        });
        return view;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
