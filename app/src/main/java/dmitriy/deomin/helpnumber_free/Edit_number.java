package dmitriy.deomin.helpnumber_free;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;


public class Edit_number extends Activity {

    SharedPreferences mSettings;//
    public static final String APP_PREFERENCES = "p_settings"; // файл сохранялки
    final String ALERT_DIALOG ="alert_dialog"; //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_number);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);//


//простыня

        EditText tw_e_s_s,te_s_s; //112
        tw_e_s_s = (EditText)findViewById(R.id.textView_e_s_s);
        te_s_s = (EditText)findViewById(R.id.editText_e_s_s);

        te_s_s.setText(save_read("112"));
        tw_e_s_s.setText(save_read("112w"));

        te_s_s.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // текст только что изменили
              save_value("112",s.toString());
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
        tw_e_s_s.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // текст только что изменили
                save_value("112w",s.toString());
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


        EditText tw_p_ch,te_p_ch; //101
        tw_p_ch = (EditText)findViewById(R.id.textView_p_ch);
        te_p_ch = (EditText)findViewById(R.id.editText_p_ch);

        te_p_ch.setText(save_read("101"));
        tw_p_ch.setText(save_read("101w"));

        te_p_ch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // текст только что изменили
                save_value("101",s.toString());
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
        tw_p_ch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // текст только что изменили
                save_value("101w",s.toString());
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


        final EditText tw_pol,te_pol; //102
        tw_pol = (EditText)findViewById(R.id.textView_pol);
        te_pol = (EditText)findViewById(R.id.editText_pol);

        te_pol.setText(save_read("102"));
        tw_pol.setText(save_read("102w"));

        te_pol.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // текст только что изменили
                save_value("102",s.toString());

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
        tw_pol.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // текст только что изменили
                save_value("102w",s.toString());
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


        final EditText tw_skor,te_skor; //103
        tw_skor = (EditText)findViewById(R.id.textView_skor);
        te_skor = (EditText)findViewById(R.id.editText_skor);

        te_skor.setText(save_read("103"));
        tw_skor.setText(save_read("103w"));

        te_skor.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // текст только что изменили
               save_value("103",s.toString());
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
        tw_skor.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // текст только что изменили
               save_value("103w",s.toString());
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

        final EditText tw_gaz,te_gaz; //104
        tw_gaz = (EditText)findViewById(R.id.textView_gaz);
        te_gaz = (EditText)findViewById(R.id.editText_gaz);

        te_gaz.setText(save_read("104"));
        tw_gaz.setText(save_read("104w"));

        te_gaz.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // текст только что изменили
                save_value("104",s.toString());

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
        tw_gaz.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // текст только что изменили
                save_value("104w",s.toString());
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
}
