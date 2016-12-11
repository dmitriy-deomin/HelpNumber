package dmitriy.deomin.helpnumber_free;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Save_numbers extends ListActivity implements AdapterView.OnItemLongClickListener{

    SharedPreferences mSettings;//
    public static final String APP_PREFERENCES = "p_settings"; // файл сохранялки
    final String ALERT_DIALOG ="alert_dialog"; //
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> masiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        masiv = new ArrayList<String>(getStringArrayPref(this, "save_gorod"));
        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,masiv);
        setListAdapter(mAdapter);
        getListView().setOnItemLongClickListener(this);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
                    CharSequence k =((TextView) v).getText();
                    String n=k.toString();
                    String mas[] = n.split(": ");

                    String nomer = mas[1];
                    String gorod = mas[0];
                    zvonok(nomer,gorod);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

    final String selectedItem = parent.getItemAtPosition(position).toString(); //получаем строку

        //подкрашиваем
        Spannable text = new SpannableString(getString(R.string.vi_deistvitelno_hotite_delete)+selectedItem + "? ");
        text.setSpan(new ForegroundColorSpan(Color.RED),0,text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(getString(R.string.delete));
        b.setIcon(R.drawable.delete);
        b.setMessage(text);
        b.setCancelable(true);
        b.setNegativeButton(getString(R.string.net), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //нечего не делаем
            }
        });
        b.setPositiveButton(getString(R.string.da), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //удаляем

                ArrayList<String> masiv;
                masiv = new ArrayList<String>(getStringArrayPref(getApplicationContext(), "save_gorod")); // чтение сохраненых номеров

                if (masiv.remove(selectedItem))//пробуем удалить
                {
                     // сохранение номеров
                    if (setStringArrayPref(getApplicationContext(), "save_gorod", masiv))
                    {
                        //если сохранилось обновляем список
                        mAdapter.remove(selectedItem);
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), getString(R.string.delet_da) + selectedItem, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), getString(R.string.error_save), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_delet), Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog alert = b.create();
        alert.show();

        return true;
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
}
