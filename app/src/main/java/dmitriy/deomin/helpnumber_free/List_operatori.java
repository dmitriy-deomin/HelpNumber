package dmitriy.deomin.helpnumber_free;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class List_operatori extends Activity {


    String mas[]={
      "Tele2",
      "Mtc",
      "Beeline",
      "Megafon",
      "Smarts"
    };


    private static final int CONTACT_PICK_RESULT = 0;
    private static final String LOG_TAG = "my_tag";

    String mContactId;
    String mPhoneNumber;
    String mContactName;
    String n;


    SharedPreferences mSettings;//
    public static final String APP_PREFERENCES = "p_settings"; // файл сохранялки



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_operatori);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);//

        if(save_read_bool("Vid")==true) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        // Получим идентификатор ListView
        final ListView listView = (ListView) findViewById(R.id.listView);
        //устанавливаем массив в ListView
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mas));
        listView.setTextFilterEnabled(true);

        //Обрабатываем щелчки на элементах ListView:
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                //реализация "operator"
                CharSequence k =((TextView) v).getText();
                String n  = k.toString();
                save_I_close(n);
            }
        });
    }



    public void save_I_close(String n){
        if (n.equals("Tele2")) {
            save_value("operator", "Tele2");
            Toast.makeText(getApplicationContext(), "Ваш оператор " + n, Toast.LENGTH_SHORT).show();
            this.onDestroy();

        } else if (n.equals("Mtc")) {
            save_value("operator", "Mtc");
            Toast.makeText(getApplicationContext(), "Ваш оператор " + n, Toast.LENGTH_SHORT).show();
            this.onDestroy();

        } else if (n.equals("Beeline")) {
            save_value("operator", "Beeline");
            Toast.makeText(getApplicationContext(), "Ваш оператор " + n, Toast.LENGTH_SHORT).show();
            this.onDestroy();

        } else if (n.equals("Megafon")) {
            save_value("operator", "Megafon");
            Toast.makeText(getApplicationContext(), "Ваш оператор " + n, Toast.LENGTH_SHORT).show();
            this.onDestroy();

        } else if (n.equals("Smarts")) {
            save_value("operator", "Smarts");
            Toast.makeText(getApplicationContext(), "Ваш оператор " + n, Toast.LENGTH_SHORT).show();
            this.onDestroy();

        }
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
    //--------------------------------------------------------------------------------------------
    public void save_value(String Key,String Value){ //сохранение строки
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(Key, Value);
        editor.commit();
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
            Spannable text = new SpannableString(getString(R.string.Neudalos_naiti_nastroiku));
            text.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean save_read_bool(String key){
        if(mSettings.contains(key)){
            return (mSettings.getBoolean(key,false));
        }
        return false;
    }
}
