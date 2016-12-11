package dmitriy.deomin.helpnumber_free;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class List_number extends Activity {

    SharedPreferences mSettings;//
    public static final String APP_PREFERENCES = "p_settings"; // файл сохранялки
    final String ALERT_DIALOG ="alert_dialog"; //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_number);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);//


        // Получим идентификатор ListView
        final ListView listView = (ListView) findViewById(R.id.listView);
        //устанавливаем массив в ListView
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,spisok(getIntent().getStringExtra("list_number"))  ));

        listView.setTextFilterEnabled(true);

        //Обрабатываем щелчки на элементах ListView:
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                //реализация
                CharSequence k =((TextView) v).getText();
                String n=k.toString();

                if(n.contains(" SMS "))
                {
                    String mas[] = n.split(": ");
                    Uri uri = Uri.parse("smsto:0500");
                    Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                    it.putExtra("sms_body", "Здравствуйте ");
                    startActivity(it);
                }
                else
                {
                    String mas[] = n.split(": ");

                    String nomer = mas[1];
                    String gorod = mas[0];
                    zvonok(nomer, gorod);
                }
            }
        });
    }



//



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

////сохранение массива другие сервисные номера
//    public void save_mas(){
//        Set<String> drugie = new HashSet<String>();
//        drugie.add("«Ребенок в опасности»\n122");
//        drugie.add("«Ребенок в опасности»\n123");
//        drugie.add( "Информацию и инструкцию по пользованию электронными госуслугами онлайн\n115");
//        drugie.add("Телефон доверия полиции\n128");
//        SharedPreferences.Editor e = mSettings.edit();
//        e.putStringSet("drugie", drugie);
//        e.apply();
//    }
///чтение массива
    public String[] save_mas_read(String mas){
        Set<String> ret;
        ret = mSettings.getStringSet(mas, new HashSet<String>());
            // Конвертируем Set в массив
            String[] myArray = {};
            myArray = ret.toArray(new String[ret.size()]);
            return myArray;
    }

    public String[] spisok(String s){ // возвращает массив
        if (s.equals("tele2")) {
            return getResources().getStringArray(R.array.array_list_tele2);
        } else if (s.equals("mtc")) {
            return getResources().getStringArray(R.array.array_list_mts);
        } else if (s.equals("beeline")) {
            return getResources().getStringArray(R.array.array_list_beeline);
        } else if (s.equals("megafon")) {
            return getResources().getStringArray(R.array.array_list_megafon);
        } else if (s.equals("vse")) {
            return getResources().getStringArray(R.array.array_list_vse);
        } else if (s.equals("smarts")) {
            return getResources().getStringArray(R.array.array_list_smarts);
        }
        return new String[0];
    }

    public void zvonok(final String number,String s_s){
        if(save_read(ALERT_DIALOG)=="net") {
            // звоним
            Intent i = new Intent(Intent.ACTION_CALL);
            i.setData(Uri.parse("tel:" + number));
            startActivity(i);

        }else{
            AlertDialog.Builder b = new AlertDialog.Builder(List_number.this);
            b.setTitle(getString(R.string.Zvonok))
                    .setIcon(R.drawable.ico_phone)
                    .setMessage(getString(R.string.Vi_deistvitelno_hotite_pozvonit) + s_s + "? " + number)
                    .setCancelable(true)
                    .setNegativeButton(getString(R.string.net), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //нечего не делаем
                        }
                    })
                    .setPositiveButton(getString(R.string.da), new DialogInterface.OnClickListener() {
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
