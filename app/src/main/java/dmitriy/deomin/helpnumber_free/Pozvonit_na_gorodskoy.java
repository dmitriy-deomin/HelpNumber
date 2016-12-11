package dmitriy.deomin.helpnumber_free;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class Pozvonit_na_gorodskoy extends Activity {

    SharedPreferences mSettings;//
    public static final String APP_PREFERENCES = "p_settings"; // файл сохранялки
    final String ALERT_DIALOG ="alert_dialog"; //
    final String NOMER_USER = "nomer_user";
    final String KOD_USER = "kod_user";
    final String GOROD_USER = "gorod_user";
    final String NOMER_STROKI = "nomer_stroki";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pozvonit_na_gorodskoy);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);//

        if(save_read_bool("Vid")==true) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        final EditText k_e =(EditText)findViewById(R.id.editText_kod);
        final EditText n_e =(EditText)findViewById(R.id.editText_number);
        final EditText t_f =(EditText)findViewById(R.id.editText_find);
        final Button b_c = (Button) findViewById(R.id.button_coll);

        // Получим идентификатор ListView
        final ListView listView = (ListView) findViewById(R.id.listView);
        //устанавливаем массив в ListView

        final ArrayAdapter <String> stringArrayAdapter = new ArrayAdapter<String>(this,R.layout.delegat_list,getResources().getStringArray(R.array.kod_goroda));

        listView.setAdapter(stringArrayAdapter);
        listView.setTextFilterEnabled(true);

        //пролистываем до нужного элемента и выделяем строку
       if(save_read(NOMER_STROKI)!=null){
           final int pozicia_save = Integer.valueOf(save_read(NOMER_STROKI));
           if(pozicia_save>0){
               listView.setSelection(pozicia_save);

               // LinearLayout linearLayout = (LinearLayout)listView.getAdapter().getItem(pozicia_save);
               //  linearLayout.setBackgroundColor(getResources().getColor(R.color.Red));
           }
       }

        //Обрабатываем щелчки на элементах ListView:
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                //реализация
                CharSequence k =((TextView) v).getText();
                String n=k.toString();
                String mas[] = n.split(": ");
                String gorod=mas[0];
                String kod=mas[1];
                k_e.setText(kod);
                Toast.makeText(getApplicationContext(), getString(R.string.Vibrali_gorod)+n, Toast.LENGTH_SHORT).show();

                //сохраняем позицию
                save_value(NOMER_STROKI,String.valueOf(position));
                //сохраняем город, код будет сохранятся при изменении Edittext
                save_value(GOROD_USER,gorod);
            }
        });

        t_f.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // текст только что изменили
                stringArrayAdapter.getFilter().filter(s);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // текст будет изменен
            }
            @Override
            public void afterTextChanged(Editable s) {
                // текст уже изменили
            }
        });

        k_e.setText(save_read(KOD_USER));
        n_e.setText(save_read(NOMER_USER));

        b_c.setText(getString(R.string.Pozvonit_2)+ "+7 " +save_read(KOD_USER)+" "+save_read(NOMER_USER));

        n_e.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // текст только что изменили
                save_value(NOMER_USER, n_e.getText().toString());
                b_c.setText(getString(R.string.Pozvonit_2) +"+7 "+ k_e.getText().toString() + " " + n_e.getText().toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // текст будет изменен
            }
            @Override
            public void afterTextChanged(Editable s) {
                // текст уже изменили
            }
        });

        k_e.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // текст только что изменили
                save_value(KOD_USER,k_e.getText().toString());
                b_c.setText(getString(R.string.Pozvonit_2)+"+7 "+k_e.getText().toString()+" "+n_e.getText().toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // текст будет изменен
            }
            @Override
            public void afterTextChanged(Editable s) {
                // текст уже изменили
            }
        });
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pozvonit_na_gorodskoy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) { //сохранить в список
            //сохранение массива другие сервисные номера
            final EditText k_e =(EditText)findViewById(R.id.editText_kod);
            final EditText n_e =(EditText)findViewById(R.id.editText_number);

            String k_n= "+7"+k_e.getText().toString()+n_e.getText().toString();

            ArrayList<String> masiv;
            masiv = new ArrayList<String>(getStringArrayPref(this, "save_gorod")); // чтение сохраненых номеров


            masiv.add(save_read(GOROD_USER)+": "+k_n);
            // сохранение номеров
                if(setStringArrayPref(this, "save_gorod", masiv))
                {
                    Toast.makeText(getApplicationContext(), getString(R.string.save)+(save_read(GOROD_USER)+": "+k_n), Toast.LENGTH_SHORT).show();
                }
            else
            {
                    Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }

        if(id==R.id.action_save_read){ // открыть список сохраненых номеров
            Intent intent_g;
            intent_g = new Intent(Pozvonit_na_gorodskoy.this, Save_numbers.class);
            startActivity(intent_g);
        }

        if(id == R.id.action_save_bufer) {
            //сохранение массива другие сервисные номера
            final EditText k_e = (EditText) findViewById(R.id.editText_kod);
            final EditText n_e = (EditText) findViewById(R.id.editText_number);

            String k_n = "+7" + k_e.getText().toString() + n_e.getText().toString();
            putText(k_n);
            Toast.makeText(getApplicationContext(), getString(R.string.save)+k_n, Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
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
            android.content.ClipData clip = ClipData.newPlainText(text,text);
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

    public void clik_call(View view) {

        final EditText k_e =(EditText)findViewById(R.id.editText_kod);
        final EditText n_e =(EditText)findViewById(R.id.editText_number);

        String k_n= "+7"+k_e.getText().toString()+n_e.getText().toString();

        zvonok(k_n,save_read(GOROD_USER));
    }
    public void zvonok(final String number,String s_s){
        if(save_read(ALERT_DIALOG)=="net") {
            // звоним
            Intent i = new Intent(Intent.ACTION_CALL);
            i.setData(Uri.parse("tel:" + number));
            startActivity(i);

        }else{
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("Звонок")
                    .setIcon(R.drawable.ico_phone)
                    .setMessage(getString(R.string.vi_deistvitelno_hotite_pozvonot) + s_s + "? " + number)
                    .setCancelable(true)
                    .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //нечего не делаем
                        }
                    })
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // звоним
                            Intent i = new Intent(Intent.ACTION_CALL);
                            i.setData(Uri.parse("tel:" + number));
                            startActivity(i);
                        }
                    });
            AlertDialog alert = b.create();
            alert.show();
        }
    }
}
