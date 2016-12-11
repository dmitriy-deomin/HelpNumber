package dmitriy.deomin.helpnumber_free;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;


public class Operatori extends Activity {

    SharedPreferences mSettings;//
    public static final String APP_PREFERENCES = "p_settings"; // файл сохранялки
    final String ALERT_DIALOG ="alert_dialog"; //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operatori);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);//

        if(save_read_bool("Vid")==true) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }


    public void clik(View view) {
        switch (view.getId()){
            case(R.id.button_tele2):
                Intent intent_tele2 = new Intent(Operatori.this, List_number.class);
                intent_tele2.putExtra("list_number", "tele2");
                startActivity(intent_tele2);
            break;
            case(R.id.button_mtc):
                Intent intent_mtc = new Intent(Operatori.this, List_number.class);
                intent_mtc.putExtra("list_number", "mtc");
                startActivity(intent_mtc);
                break;
            case(R.id.button_beeline):
                Intent intent_beeline = new Intent(Operatori.this, List_number.class);
                intent_beeline.putExtra("list_number", "beeline");
                startActivity(intent_beeline);
                break;
            case(R.id.button_megafon):
                Intent intent_megafon = new Intent(Operatori.this, List_number.class);
                intent_megafon.putExtra("list_number", "megafon");
                startActivity(intent_megafon);
                break;
            case(R.id.button_smarts):
                Intent intent_smarts = new Intent(Operatori.this, List_number.class);
                intent_smarts.putExtra("list_number", "smarts");
                startActivity(intent_smarts);
                break;
        }
    }
    public boolean save_read_bool(String key){
        if(mSettings.contains(key)){
            return (mSettings.getBoolean(key,false));
        }
        return false;
    }
}
