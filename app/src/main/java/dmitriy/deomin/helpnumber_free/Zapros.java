package dmitriy.deomin.helpnumber_free;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class Zapros extends Activity {

    private static final int CONTACT_PICK_RESULT = 0;

    String mContactId;
    String mPhoneNumber;
    String mContactName;
    String n_kesh;

    String operator;

    private ArrayList<String> mas;

    SharedPreferences mSettings;//
    private ArrayAdapter<String> mAdapter;
    public static final String APP_PREFERENCES = "p_settings"; // файл сохранялки

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zapros);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);//

        if(save_read_bool("Vid")==true) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }


        //скроем лояут перевода, покажем лоят выбора пользователя
        ((LinearLayout)findViewById(R.id.perevod_loaut)).setVisibility(View.GONE);
        ((LinearLayout)findViewById(R.id.perevod_vibor_user_loaut)).setVisibility(View.VISIBLE);



        operator = save_read("operator");

        TextView t = (TextView)findViewById(R.id.textView);
        t.setText("Ваш оператор: "+operator);



        mas = new ArrayList<String>(getStringArrayPref(this, "histori_user"));

        // Получим идентификатор ListView
        final ListView listView = (ListView) findViewById(R.id.listView);
        //устанавливаем массив в ListView
        mAdapter=(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mas));
        listView.setAdapter(mAdapter);
        listView.setTextFilterEnabled(true);

        //Обрабатываем щелчки на элементах ListView:
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                //выбор сохраненого номера
                CharSequence k =((TextView) v).getText();
                String n=k.toString();

                String masiv[] = n.split(": ");//разделяем по намерам
                mContactName = masiv[0];
                mPhoneNumber = masiv[1];

                if(getIntent().getStringExtra("key").equals("perevod")) {
                    ((TextView)findViewById(R.id.textView_komu_perevodit)).setText(mContactName + ": " + mPhoneNumber);
                    vibor_zaprosa(mPhoneNumber);//набор запроса получателя
                }else{
                    vibor_zaprosa(mPhoneNumber);//набор запроса получателя
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final String selectedItem = parent.getItemAtPosition(position).toString(); //получаем строку
                //удаляем
                if (mas.remove(selectedItem))//пробуем удалить
                {
                    // сохранение номеров
                    if (setStringArrayPref(getApplicationContext(), "histori_user", mas))
                    {
                        //если сохранилось обновляем список
                        mAdapter.remove(selectedItem);
                        mAdapter.notifyDataSetChanged();
                        Toast(getString(R.string.delet_da) + selectedItem);
                    }
                    else
                    {
                     Toast_error(getString(R.string.error_save));
                    }
                }
                else
                {
                   Toast_error(getString(R.string.error_delet));
                }
                return true;
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
            Toast_error(getString(R.string.Neudalos_naiti_nastroiku));
        }
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



    public boolean save_read_bool(String key){
        if(mSettings.contains(key)){
            return (mSettings.getBoolean(key,false));
        }
        return false;
    }

    public void Сlik_send(View view) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICK_RESULT);
    }


    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case CONTACT_PICK_RESULT:
                    Uri contactData = data.getData();
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToNext()) {
                        mContactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        mContactName = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));

                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        // если есть телефоны, получаем и выводим их
                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + mContactId, null, null);


                            n_kesh = "";
                            while (phones.moveToNext()) {
                                mPhoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                mPhoneNumber = mPhoneNumber.replaceAll(" ", "").replaceAll("-", "").replaceAll("\\+", ""); //
                                mPhoneNumber= replaceCharAt(mPhoneNumber,0,'8');

                                if (n_kesh.isEmpty()) {
                                    n_kesh = mPhoneNumber;
                                } else {
                                    n_kesh = n_kesh + "_" + mPhoneNumber;
                                }

                            }

                            if(n_kesh.contains("_")){ // если номеров много откроем окно выбора нужного
                                Intent i = new Intent(this,Dual_number.class);
                                i.putExtra("dual",n_kesh);
                                i.putExtra("dual_user",mContactName);
                                i.putExtra("key",getIntent().getStringExtra("key"));
                                startActivity(i);
                            }else{
                                mas.add(mContactName+": "+n_kesh);//добавляем в массим
                                setStringArrayPref(this,"histori_user",mas); // перезаписываем
                                phones.close();
                                ((TextView)findViewById(R.id.textView_komu_perevodit)).setText(mContactName + ": " + mPhoneNumber);
                                vibor_zaprosa(n_kesh);//набор запроса
                            }

                            phones.close();
                        }
                    }
                    break;
            }

        } else {
          Toast_error("Ошибка");
        }
    }
    public static String replaceCharAt(String s, int pos, char c) {

        return s.substring(0,pos) + c + s.substring(pos+1);

    }

    public void vibor_zaprosa(String nomer){
        String s = getIntent().getStringExtra("key");
        if (s.equals("perezvoni")) {
            zapros_perezvoni(nomer);

        } else if (s.equals("popolni")) {
            zapros_popolni(nomer);

        } else if (s.equals("perevod")) {
            ((LinearLayout) findViewById(R.id.perevod_vibor_user_loaut)).setVisibility(View.GONE);
            ((LinearLayout) findViewById(R.id.perevod_loaut)).setVisibility(View.VISIBLE);

        }
    }

    public void zapros_perezvoni(String nom) {

        if (operator.equals("Tele2")) {
            coll("*118*" + nom + "#");//звоним

        } else if (operator.equals("Mtc")) {
            coll("*110*" + nom + "#");//звоним

        } else if (operator.equals("Beeline")) {
            coll("*144*" + nom + "#");//звоним

        } else if (operator.equals("Megafon")) {
            coll("*144#" + nom + "#");//звоним

        } else if (operator.equals("Smarts")) {
            coll(nom);//звоним

        }

    }

    public void zapros_popolni(String nom) {

        if (operator.equals("Tele2")) {
            coll("*123*" + nom + "#");//звоним

        } else if (operator.equals("Mtc")) {
            coll("*116*" + nom + "#");//звоним

        } else if (operator.equals("Beeline")) {
            coll("*143*" + nom + "#");//звоним

        } else if (operator.equals("Megafon")) {
            coll("*143*" + nom + "#");//звоним

        } else if (operator.equals("Smarts")) {
            putText(nom);
            coll("*112#");//звоним

        }

    }

    public void zapros_perevod(String nom,String symma) {

        if (operator.equals("Tele2")) {
            if (Integer.valueOf(symma) > 9) {
                coll("*145*" + nom + "*" + symma + "#");//звоним
            } else {
                Toast_error("Сумма меньше минимальной(10р)");
            }

        } else if (operator.equals("Mtc")) {
            if (Integer.valueOf(symma) > 0.9) {
                coll("*112*" + nom + "*" + symma + "#");//звоним
            } else {
                Toast_error("Сумма меньше минимальной(1р)");
            }

        } else if (operator.equals("Beeline")) {
            if (Integer.valueOf(symma) > 9) {
                coll("*145*" + nom + "*" + symma + "#");//звоним
            } else {
                Toast_error("Сумма меньше минимальной(10р)");
            }

        } else if (operator.equals("Megafon")) {
            if (Integer.valueOf(symma) > 0.9) {
                coll("*133*" + symma + "*" + nom + "#");//звоним
            } else {
                Toast_error("Сумма меньше минимальной(1р)");
            }

        } else if (operator.equals("Smarts")) {
            putText(nom);
            Toast("Номер скопирован в буфер");
            putText(nom);

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

        Toast("Делаем запрос: "+farsh);

        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + encod)));
        this.finish();
    }


    //копирование и чтение текста в буфер
//*****************************************************************************************************
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
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

    public void Clik_perevod_bobla(View view) {
       zapros_perevod(mPhoneNumber,((EditText)findViewById(R.id.editText_summa)).getText().toString());
    }
//*****************************************************************************************************


    public  void  Toast(String mesag){
        SuperToast.create(this, mesag, SuperToast.Duration.LONG,
                Style.getStyle(Style.GREEN, SuperToast.Animations.FLYIN)).show();
    }
    public void Toast_error(String mesag){
        SuperToast.create(this, mesag, SuperToast.Duration.LONG,
                Style.getStyle(Style.RED, SuperToast.Animations.POPUP)).show();
    }

    public void Edit_vibor_operator(View view) {
        Intent i_p = new Intent(getApplicationContext(),List_operatori.class);
        startActivity(i_p);
    }


    public void Info_dialog(){

        String z = null;

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View content = LayoutInflater.from(this).inflate(R.layout.custon_dialog_text_info, null);
        builder.setView(content);
        final AlertDialog alertDialog = builder.create();

        if (operator.equals("Tele2")) {
            ((Button) content.findViewById(R.id.button_dialog_uznat_maksimalnuy)).setVisibility(View.VISIBLE);
            ((Button) content.findViewById(R.id.button_dialog_text_info_komisia)).setVisibility(View.VISIBLE);
            ((TextView) content.findViewById(R.id.textView_info_dialog_text)).setText(getString(R.string.info_perevoda_tele2));
            z = "*104#";

        } else if (operator.equals("Mtc")) {
            ((Button) content.findViewById(R.id.button_dialog_uznat_maksimalnuy)).setVisibility(View.GONE);
            ((Button) content.findViewById(R.id.button_dialog_text_info_komisia)).setVisibility(View.GONE);
            ((TextView) content.findViewById(R.id.textView_info_dialog_text)).setText(getString(R.string.info_perevoda_mts));

        } else if (operator.equals("Beeline")) {
            ((Button) content.findViewById(R.id.button_dialog_uznat_maksimalnuy)).setVisibility(View.GONE);
            ((Button) content.findViewById(R.id.button_dialog_text_info_komisia)).setVisibility(View.GONE);
            ((TextView) content.findViewById(R.id.textView_info_dialog_text)).setText(getString(R.string.info_perevoda_beeline));

        } else if (operator.equals("Megafon")) {
            ((Button) content.findViewById(R.id.button_dialog_uznat_maksimalnuy)).setVisibility(View.GONE);
            ((Button) content.findViewById(R.id.button_dialog_text_info_komisia)).setVisibility(View.GONE);
            ((TextView) content.findViewById(R.id.textView_info_dialog_text)).setText(getString(R.string.info_perevoda_megafon));

        } else if (operator.equals("Smarts")) {
            ((Button) content.findViewById(R.id.button_dialog_uznat_maksimalnuy)).setVisibility(View.GONE);
            ((Button) content.findViewById(R.id.button_dialog_text_info_komisia)).setVisibility(View.GONE);
            ((TextView) content.findViewById(R.id.textView_info_dialog_text)).setText("Информация не найдена");

        }

        final String finalZ = z;
        ((Button)content.findViewById(R.id.button_dialog_uznat_maksimalnuy)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
                coll(finalZ);
            }
        });
        ((Button)content.findViewById(R.id.button_close_dioalog_text)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });



        ((Button) content.findViewById(R.id.button_dialog_text_info_komisia)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coll("*145#");
            }
        });
        alertDialog.show();

    }


    public void Uznat_summu_perevoda(View view) {
      Info_dialog();
    }
}
