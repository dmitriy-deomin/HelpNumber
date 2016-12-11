package dmitriy.deomin.helpnumber_free;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


public class Setting extends Activity {

    SharedPreferences mSettings;//
    public static final String APP_PREFERENCES = "p_settings"; // файл сохранялки
    final String ALERT_DIALOG ="alert_dialog"; //

    CheckBox checkBox_zvonit;
    CheckBox checkBox_ekran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);//

       checkBox_zvonit = (CheckBox)findViewById(R.id.checkBox_zvonit);
       checkBox_ekran = (CheckBox)findViewById(R.id.checkBox_ekran);

        if(save_read(ALERT_DIALOG)=="net"){
            checkBox_zvonit.setChecked(false);
        }else{
            checkBox_zvonit.setChecked(true);
        }

        if(save_read_bool("Vid")){
            checkBox_ekran.setChecked(true);
        }else{
            checkBox_ekran.setChecked(false);
        }

        Button o = (Button)findViewById(R.id.button_operator);
        String t=save_read("operator");
        if(t==null||t==""||t.length()==0){o.setText(R.string.Vash_operator_ne_vibrano);}
        else {o.setText(getString(R.string.Vash_operator)+t);}


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startNewMainActivity(this,MainActivity.class);
    }


    //эт кусок кода запускает активити а остальные ебашит нахер
    static void startNewMainActivity(Activity currentActivity, Class<? extends Activity> newTopActivityClass) {
        Intent intent = new Intent(currentActivity, newTopActivityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            intent.addFlags(0x8000); // equal to Intent.FLAG_ACTIVITY_CLEAR_TASK which is only available from API level 11
        currentActivity.startActivity(intent);
    }

    public void clik_vopros(View view) {

        if(!checkBox_zvonit.isChecked()){
            save_value(ALERT_DIALOG,"net");
            Toast.makeText(getApplicationContext(), getString(R.string.Budem_zvonit_srazu), Toast.LENGTH_SHORT).show();
        }else{
            save_value(ALERT_DIALOG,"da");
            Toast.makeText(getApplicationContext(), getString(R.string.Budem_sprashivat_pered_zvonkom), Toast.LENGTH_SHORT).show();
        }
    }


    public void clik_ekran(View view) {

        if(checkBox_ekran.isChecked()){
            save_value_bool("Vid",true);
        }else{
            save_value_bool("Vid",false);
        }
    }


    //-----------строки---------------------------------------------------------------------------------
    public void save_value(String Key,String Value){ //сохранение строки
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(Key, Value);
        if(editor.commit())
        {
            // Toast.makeText(getApplicationContext(), "Удачно сохранили", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), getString(R.string.Neudalos_naiti_nastroiku), Toast.LENGTH_SHORT).show();
        }
    }
    public String save_read(String key_save){  // чтение настройки
        if(mSettings.contains(key_save)) {
            return (mSettings.getString(key_save, ""));
        }
        return null;
    }
//------------------------------------------------------------------------------------------------

//--------------логика------------------------------------------------------------------
    public  void save_value_bool(String key,boolean value){
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean(key,value);
        if(editor.commit())
        {
            // Toast.makeText(getApplicationContext(), "Удачно сохранили", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), getString(R.string.Neudalos_naiti_nastroiku), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean save_read_bool(String key){
        if(mSettings.contains(key)){
            return (mSettings.getBoolean(key,false));
        }
        return false;
    }

    public void edit_number(View view) {
        Intent i = new Intent(this,Edit_number.class);
        startActivity(i);
    }

    public void Clik_operator(View view) {
        Intent i = new Intent(this,List_operatori.class);
        startActivity(i);
    }
}
