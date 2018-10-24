package app.example.app.progressdialog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Progress Dialog Example");

        this.showProgressDialog();
    }


    public void showProgressDialog(){
        Button showProgressButton = (Button) findViewById(R.id.progressDialog);
        final TextView progressText = (TextView) findViewById(R.id.progressText);

        showProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setIcon(R.mipmap.ic_launcher_round);
                progressDialog.setTitle("Waiting...");
                progressDialog.setMax(100);
                progressDialog.setCancelable(false);


                progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DecimalFormat df = new DecimalFormat("##.#%");
                        float percentage = (float)progressDialog.getProgress()/(float)progressDialog.getMax();
                        String percentString = df.format(percentage);
                        progressText.setText("Current percentage is "+ percentString);
                    }
                });

                progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressText.setText("You canceled the progress.");
                    }
                });

                progressDialog.setMessage("This is a horizontal progress dialog.");
                progressDialog.show();


                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int i = 0;
                        while (i<100){
                            try {
                                Thread.sleep(300);
                                progressDialog.incrementProgressBy(1);
                                progressDialog.incrementSecondaryProgressBy(5);
                                i++;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        progressDialog.dismiss();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressText.setText("Progress Completed");
                            }
                        });
                    }
                });
                thread.start();
            }
        });
    }
}
