package com.akb.latihansp_10117149.latihansharedpreference;

/*
Tanggal Pengerjaan : 29 April 2020
Deskripsi Pengerjaan : Membuat awal project sampai selesai =
- HomeActivity, LoginActivity, RegisterActivity
- UserModel
- Preferences
- UtilStatic
- activity_main, activity_register, activity_home
Tanggal Pengerjaan : 30 April 2020
Deskripsi Pengerjaan : Generate APK
NIM : 10117149
NAMA : M NIZAR MIFTAHUL ULYA
KELAS : IF4
 */

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.akb.latihansp_10117149.R;
import com.akb.latihansp_10117149.latihansharedpreferences.utils.Preferences;
import com.akb.latihansp_10117149.latihansharedpreferences.model.UserModel;

public class LoginActivity extends AppCompatActivity {

    private TextView txtMasuk;
    private TextView txtRegister;
    private EditText edtUsername;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        declareView();

        txtMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasiLogin();
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), RegisterActivity.class));
            }
        });
    }

    /**
     * ke MainActivity jika data Status Login dari Data Preferences bernilai true
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (Preferences.getLoggedInStatus(getBaseContext())) {
            startActivity(new Intent(getBaseContext(), HomeActivity.class));
            finish();
        }
    }


    private void declareView() {

        txtRegister = findViewById(R.id.txt_login_register);
        txtMasuk = findViewById(R.id.txt_login_masuk);
        edtUsername = findViewById(R.id.edt_login_username);
        edtPassword = findViewById(R.id.edt_login_password);

    }

    private void validasiLogin() {

        // Mereset semua Error dan fokus menjadi default
        edtUsername.setError(null);
        edtPassword.setError(null);
        View fokus = null;
        boolean cancel = false;

        //Set Input Value dari View
        String userName = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();


        // Jika form user kosong atau memenuhi kriteria di Method cekUser() maka, Set error di Form-
        // User dengan menset variable fokus dan error di Viewnya juga cancel menjadi true*/
        if (TextUtils.isEmpty(userName)) {
            edtUsername.setError("Harus diisi");
            fokus = edtUsername;
            cancel = true;
        } else if (!cekUser(userName)) {
            edtUsername.setError("Username Tidak Ditemukan");
            fokus = edtUsername;
            cancel = true;
        }

        // Jika form password kosong dan memenuhi kriteria di Method cekPassword maka,
        // Reaksinya sama dengan percabangan User di atas. Bedanya untuk Password dan Repassword*/
        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Harus Diisi");
            fokus = edtPassword;
            cancel = true;
        } else if (!cekPassword(password)) {
            edtPassword.setError("Data yang dimasukkan tidak sesuai");
            fokus = edtPassword;
            cancel = true;
        }

        // Jika cancel true, variable fokus mendapatkan fokus. Jika false, maka
        // Kembali ke LoginActivity dan Set User dan Password untuk data yang terdaftar */
        if (cancel) {
            fokus.requestFocus();
        } else {
            // Deklarasi Model
            UserModel userModel = new UserModel();
            userModel.setUsername(userName);
            userModel.setPassword(password);
            // Simpan data ke shared preferences
            Preferences.setUserPreferences(getBaseContext(), userModel);
            Preferences.setLoggedInStatus(getBaseContext(), true);
            //Pindah Halaman ke home
            startActivity(new Intent(getBaseContext(), HomeActivity.class));
            finish();
        }

    }

    // True jika parameter user sama dengan data user yang terdaftar dari Preferences */
    private boolean cekUser(String user) {
        return user.equals(Preferences.getRegisteredUser(getBaseContext()));
    }

    // True jika parameter password sama dengan parameter repassword */
    private boolean cekPassword(String password) {
        return password.equals(Preferences.getRegisteredPassword(getBaseContext()));
    }
}
