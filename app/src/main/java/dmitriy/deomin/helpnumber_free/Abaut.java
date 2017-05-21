package dmitriy.deomin.helpnumber_free;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class Abaut extends Activity {

    TextView textView;
    PackageInfo pinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abaut);

        try {
            pinfo = this.getPackageManager().getPackageInfo(getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        textView = (TextView)findViewById(R.id.ver);
        //тень
        textView.setShadowLayer(
                3f,   //float radius
                1f,  //float dx
                1f,  //float dy
                0xFFFFFFFF //int color
        );
        textView.setText(
                "Номера помощи\n" + "Версия:  "+pinfo.versionName+
                        "\n\n\n"+
                        "Поддержать проект \nQiwi 79027228625"
        );
    }

    public void clik(View view) {
        Uri uri = Uri.parse("mailto:58627@bk.ru");
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        startActivity(it);
    }

    public void otziv(View view) {
        Uri uri = Uri.parse("market://details?id=dmitriy.deomin.helpnumber_free");
                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(it);
    }

    public void Ok(View view) {
        this.finish();
    }

    public void politika(View view) {

        Intent i = new Intent(this,Politika.class);
        startActivity(i);

    }
}