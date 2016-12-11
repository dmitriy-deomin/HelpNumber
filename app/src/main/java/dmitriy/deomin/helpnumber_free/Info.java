package dmitriy.deomin.helpnumber_free;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;


public class Info extends Activity {

    SharedPreferences mSettings;//
    public static final String APP_PREFERENCES = "p_settings"; // файл сохранялки
    final String ALERT_DIALOG ="alert_dialog"; //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);//

        if(save_read_bool("Vid")==true) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }


    public boolean save_read_bool(String key){
        if(mSettings.contains(key)){
            return (mSettings.getBoolean(key,false));
        }
        return false;
    }

    public void clik(View view) {
        switch (view.getId()){
            case (R.id.button_pogar):
                Intent i_pogar = new Intent(this,Text_inf.class);
                i_pogar.putExtra("text_inf","pogar");
                startActivity(i_pogar);
                break;
            case (R.id.button_122):
                Intent i_122 = new Intent(this,Text_inf.class);
                i_122.putExtra("text_inf","122");
                startActivity(i_122);
                break;
            case(R.id.button_polis):
                Intent i_polis = new Intent(this,Text_inf.class);
                i_polis.putExtra("text_inf","polis");
                startActivity(i_polis);
                break;
            case(R.id.button_skor):
                Intent i_skor = new Intent(this,Text_inf.class);
                i_skor.putExtra("text_inf","skor");
                startActivity(i_skor);
                break;
        }
    }
}
