package com.java.memoryinfoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView text;
    private TextView text2;
    private TextView text3;
    private TextView text4;
    private TextView text5;
    private TextView text6;
    private TextView text7;
    private TextView text8;
    private TextView text9;
    private TextView text10;

    private String finalValue = "";
    private String myDeviceModel;
    private String myOsName;
    private String myKernelVersion;
    private String myAPILevel;
    private String myDevice;
    private String myProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeView();
        allInfo();
        onClickListener();

    }

    private void initializeView() {
        button = findViewById(R.id.button);
        text = findViewById(R.id.text);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        text5 = findViewById(R.id.text5);
        text6 = findViewById(R.id.text6);
        text7 = findViewById(R.id.text7);
        text8 = findViewById(R.id.text8);
        text9 = findViewById(R.id.text9);
        text10 = findViewById(R.id.text10);
    }

    private void onClickListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText(myDeviceModel);
                text2.setText(getUseableRamSpace());
                text3.setText(getAvailableRamSpace());
                text4.setText(getUseableRomSpace());
                text5.setText(getAvailableRomSize());
                text6.setText(myOsName);
                text7.setText(myKernelVersion);
                text8.setText(myAPILevel);
                text9.setText(getScreenResolution());
                text10.setText(getNetworkType());
            }
        });
    }


    /*
        get all other system information of device
     */
    void allInfo() {
        myDeviceModel = android.os.Build.MODEL; // model name
        myKernelVersion = System.getProperty("os.version"); // kernel version
        myAPILevel = android.os.Build.VERSION.SDK; // API level
        myDevice = android.os.Build.DEVICE;
        myProduct = android.os.Build.PRODUCT;
        myOsName = Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();

    }


    /*
        get useable total ROM Space of device
    */
    String getUseableRomSpace() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long bytesAvailable = (long) stat.getBlockSize() * (long) stat.getBlockCount();
        double gb = bytesAvailable / 1073741824.0;
        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
        String s = twoDecimalForm.format(gb).concat(" GB");
        return s;
    }


    /*
       get available total ROM Space of device
   */
    public String getAvailableRomSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long availableBlocks = stat.getAvailableBlocksLong(); // get values in KB
        double gb = availableBlocks / 1048576.0;
        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
        String s = twoDecimalForm.format(gb).concat(" GB");
        return s;
    }


    /*
        get useable total RAM Space of device
    */
    public String getUseableRamSpace() {
        Context context = getApplicationContext();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
        if (memoryInfo.lowMemory) {
            finalValue = "Low Memory..!!";
        } else {
            long totalMemory = memoryInfo.totalMem; // get total RAM size

            double kb = totalMemory / 1024.0;
            double mb = totalMemory / 1048576.0;
            double gb = totalMemory / 1073741824.0;
            double tb = totalMemory / 1099511627776.0;

            if (tb > 1) {
                finalValue = twoDecimalForm.format(tb).concat(" TB");
            } else if (gb > 1) {
                finalValue = twoDecimalForm.format(gb).concat(" GB");
            } else if (mb > 1) {
                finalValue = twoDecimalForm.format(mb).concat(" MB");
            } else if (kb > 1) {
                finalValue = twoDecimalForm.format(mb).concat(" KB");
            } else {
                finalValue = twoDecimalForm.format(totalMemory).concat(" Bytes");
            }
        }

        return finalValue;
    }


    /*
       get available RAM space of device
   */
    public String getAvailableRamSpace() {
        Context context = getApplicationContext();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
        if (memoryInfo.lowMemory) {
            finalValue = "Low Memory..!!";
        } else {
            long totalMemory = memoryInfo.availMem;  // get available RAM size

            double kb = totalMemory / 1024.0;
            double mb = totalMemory / 1048576.0;
            double gb = totalMemory / 1073741824.0;
            double tb = totalMemory / 1099511627776.0;

            if (tb > 1) {
                finalValue = twoDecimalForm.format(tb).concat(" TB");
            } else if (gb > 1) {
                finalValue = twoDecimalForm.format(gb).concat(" GB");
            } else if (mb > 1) {
                finalValue = twoDecimalForm.format(mb).concat(" MB");
            } else if (kb > 1) {
                finalValue = twoDecimalForm.format(mb).concat(" KB");
            } else {
                finalValue = twoDecimalForm.format(totalMemory).concat(" Bytes");
            }
        }

        return finalValue;
    }


    /*
       get device screen resolution
   */
    String getScreenResolution() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        String s = String.valueOf(height) + " X " + String.valueOf(width);
        return s;
    }

    /*
       get device network type
   */
    String getNetworkType() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        String s = activeNetInfo.getTypeName();
        return s;
    }

}
