package dmitriy.deomin.helpnumber_free;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class Dual_number extends Activity {


    SharedPreferences mSettings;//
    public static final String APP_PREFERENCES = "p_settings"; // файл сохранялки
    String operator;
    private ArrayList<String> mas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dual_number);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);//

        if(save_read_bool("Vid")==true) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        operator = save_read("operator");
        mas = new ArrayList<String>(getStringArrayPref(this, "histori_user"));

        String dual_num[]=getIntent().getStringExtra("dual").split("_");

        // Получим идентификатор ListView
        final ListView listView = (ListView) findViewById(R.id.listView);
        //устанавливаем массив в ListView
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dual_num));
        listView.setTextFilterEnabled(true);

        //Обрабатываем щелчки на элементах ListView:
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                CharSequence k =((TextView) v).getText();
                String n=k.toString();

                mas.add(getIntent().getStringExtra("dual_user")+": "+n);//добавляем в массим
                setStringArrayPref(getApplicationContext(),"histori_user",mas); // перезаписываем


                Toast.makeText(getApplicationContext(), "Делаем запрос "+n, Toast.LENGTH_LONG).show();
                vibor_zaprosa(n);

            }
        });
    }





    public void vibor_zaprosa(String nomer){
        String s = getIntent().getStringExtra("key");
        if (s.equals("perezvoni")) {
            zapros_perezvoni(nomer);

        } else if (s.equals("popolni")) {
            zapros_popolni(nomer);

        }
    }

    public void zapros_perezvoni(String nom) {

        if (operator.equals("Tele2")) {
            Toast.makeText(getApplicationContext(), "Делаем запрос: " + " *118*" + nom + "#", Toast.LENGTH_LONG).show();
            coll("*118*" + nom + "#");//звоним

        } else if (operator.equals("Mtc")) {
            Toast.makeText(getApplicationContext(), "Делаем запрос: " + " *110*" + nom + "#", Toast.LENGTH_LONG).show();
            coll("*110*" + nom + "#");//звоним

        } else if (operator.equals("Beeline")) {
            Toast.makeText(getApplicationContext(), "Делаем запрос: " + " *144*" + nom + "#", Toast.LENGTH_LONG).show();
            coll("*144*" + nom + "#");//звоним

        } else if (operator.equals("Megafon")) {
            Toast.makeText(getApplicationContext(), "Делаем запрос: " + " *144#" + nom + "#", Toast.LENGTH_LONG).show();
            coll("*144#" + nom + "#");//звоним

        } else if (operator.equals("Smarts")) {
            Toast.makeText(getApplicationContext(), "Делаем запрос: " + nom, Toast.LENGTH_LONG).show();
            coll(nom);//звоним

        }

    }

    public void zapros_popolni(String nom) {

        if (operator.equals("Tele2")) {
            Toast.makeText(getApplicationContext(), "Делаем запрос: " + " *123*" + nom + "#", Toast.LENGTH_LONG).show();
            coll("*123*" + nom + "#");//звоним

        } else if (operator.equals("Mtc")) {
            Toast.makeText(getApplicationContext(), "Делаем запрос: " + " *116*" + nom + "#", Toast.LENGTH_LONG).show();
            coll("*116*" + nom + "#");//звоним

        } else if (operator.equals("Beeline")) {
            Toast.makeText(getApplicationContext(), "Делаем запрос: " + " *143*" + nom + "#", Toast.LENGTH_LONG).show();
            coll("*143*" + nom + "#");//звоним

        } else if (operator.equals("Megafon")) {
            Toast.makeText(getApplicationContext(), "Делаем запрос: " + " *143*" + nom + "#", Toast.LENGTH_LONG).show();
            coll("*143*" + nom + "#");//звоним

        } else if (operator.equals("Smarts")) {
            putText(nom);
            Toast.makeText(getApplicationContext(), "Делаем запрос: *112# номер скопирован в буфер", Toast.LENGTH_LONG).show();
            coll("*112#");//звоним

        }

    }

    public void coll(String farsh){
        // звоним
        String encod = null;
        try {
            encod = URLEncoder.encode(farsh, "UTF-8"); // делаем норм строку
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + encod)));
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


    public static boolean setStringArrayPref(Context context, String key, ArrayList<String> values) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        return editor.commit();
    }

    public static ArrayList<String> getStringArrayPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        ArrayList<String> urls = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }





    //копирование и чтение текста в буфер
//*****************************************************************************************************
    @SuppressWarnings("deprecation")
    public void putText(String text){
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES. HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = ClipData.newPlainText(text, text);
            clipboard.setPrimaryClip(clip);
        }
    }

    @SuppressWarnings("deprecation")
    public String getText(){
        String text = null;
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES. HONEYCOMB ) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            text =  clipboard.getText().toString();
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            text =  clipboard.getText().toString();
        }
        return text;
    }
//*****************************************************************************************************

}
