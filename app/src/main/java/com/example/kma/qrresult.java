package com.example.kma;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;
import com.example.kma.dao.SinhVienDao;
import com.example.kma.model.SinhVien;


import com.example.kma.dao.SinhVienDao;
import com.example.kma.database.DBHeplper;
import com.example.kma.model.SinhVien;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class qrresult extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
    SinhVienDao svDao;
    ArrayList<SinhVien> listSV = new ArrayList<>();
    ZXingScannerView scannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView=new ZXingScannerView(this);
        setContentView(scannerView);


        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }

    @Override
    public void handleResult(Result rawResult) {
        boolean xetSv = false;
        svDao = new SinhVienDao(qrresult.this);
        listSV = svDao.getALL();
        String scancode = rawResult.getText();

        for (int i =0; i< listSV.size(); i++) {
            SinhVien svx = listSV.get(i);
            if (svx.getMaSv().matches(scancode)) {
                xetSv = true;
            }
        }
        if (xetSv == true) {
            for (int i =0; i< listSV.size(); i++) {
                SinhVien svx = listSV.get(i);
                if (svx.getMaSv().matches(scancode)) {
                    QRScannerActivity.scantext.setText("H??? v?? t??n: "+svx.getTenSv()+"\nM?? SV: "+svx.getMaSv()+"\nL???p: "+svx.getMaLop()+"\n???? ??i???m danh l??c: "+getDateTime());
                    String a = "??i???m danh th??nh c??ng!";
                    String data = "\n----------------------------------------------------------------------------------------" +
                            "\nH??? t??n: "+svx.getTenSv()+" | M?? SV: "+svx.getMaSv()+" | L???p: "+svx.getMaLop()+"\nTh???i gian: "+getDateTime();
                    saveData(data);
                    Toast.makeText(qrresult.this,"??i???m danh th??nh c??ng!",Toast.LENGTH_SHORT).show();
                }


            }

        } else {
            QRScannerActivity.scantext.setText("Ch??a c?? d??? li???u!");
            Toast.makeText(qrresult.this, " ??i???m danh th???t b???i!", Toast.LENGTH_SHORT).show();
        }




        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
    String simpleFileName = "diemDanh.txt";
    private void saveData(String data) {
        try {
            // Open Stream to write file.
            FileOutputStream out = this.openFileOutput(simpleFileName, MODE_APPEND);
            // Ghi d??? li???u.
            out.write(data.getBytes());
            out.close();
//            Toast.makeText(this,"File saved!",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this,"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
}