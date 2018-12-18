package com.example.minhnhi.quanlyktx.view.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minhnhi.quanlyktx.R;

public class RegisterUserAccountFragment extends Fragment implements View.OnClickListener {
    private EditText mssv, password, password_confirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.activity_register_account, container, false);
        mssv = view.findViewById(R.id.name);
        password = view.findViewById(R.id.password);
        password_confirm = view.findViewById(R.id.password_confirm);

        view.findViewById(R.id.btn_sign_in).setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sign_in:
                String nameStr = mssv.getText().toString(),
                        passStr = password.getText().toString(),
                        passConf = password_confirm.getText().toString();
                if(nameStr.isEmpty()){
                    Toast.makeText(this.getContext(), "Mã số sinh viên trống!", Toast.LENGTH_SHORT).show();
                    return;
                }else if(passStr.isEmpty()){
                    Toast.makeText(this.getContext(), "Hãy nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!passStr.equals(passConf)){
                    Toast.makeText(this.getContext(), "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show();
                    return;
                }

                RegisterUserDetailsFragment detailsFragment = new RegisterUserDetailsFragment();
                detailsFragment.setMssv(nameStr);
                detailsFragment.setPassword(passStr);

                getFragmentManager().beginTransaction()
                        .replace(R.id.container, detailsFragment)
                        .commit();
                break;
        }
    }
}
