package dmitriy.deomin.helpnumber_free;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends Activity {
    SharedPreferences mSettings;//
    public static final String APP_PREFERENCES = "p_settings"; // файл сохранялки
    final String ALERT_DIALOG ="alert_dialog"; //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);//

        if(save_read_bool("Vid")==true) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        reneme_button();
    }

    public void reneme_button(){
        //ставим имена кнопкам
        Button b_112,b_101,b_102, b_103,b_104;
        b_112 = (Button) findViewById(R.id.button_spas);
        b_112.setText(label_button("112w",getString(R.string.button_edinay_slugba)));
        b_101 = (Button) findViewById(R.id.button_pogar);
        b_101.setText(label_button("101w",getString(R.string.button_pozar)));
        b_102 = (Button) findViewById(R.id.button_polisia);
        b_102.setText(label_button("102w",getResources().getString(R.string.button_polis)));
        b_103 = (Button) findViewById(R.id.button_skoray);
        b_103.setText(label_button("103w",getString(R.string.button_skoray)));
        b_104 = (Button) findViewById(R.id.button_gaz);
        b_104.setText(label_button("104w",getString(R.string.button_zag)));
    }

    public String label_button(String value,String defaul){
        String s = save_read(value);
        if(s==null||s==""||s.length()==0||s.isEmpty()){return defaul;}else{return s;}
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.abaut) {
            abaut();
        }
        if(id==R.id.exit){
            this.finish();
        }

        if(id==R.id.vopros_alert_dialog){

        Intent i = new Intent(this,Setting.class);
            startActivity(i);
            this.onDestroy();
        }

        if(id==R.id.action_info){
            Intent i = new Intent(this,Info.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public void clik(View view) {
        switch (view.getId()){
            case (R.id.button_pogar):
                zvonok(number_call("101","101"),label_button("101w",getString(R.string.Pogarnay_chast)));
                break;
            case (R.id.button_spas):
                zvonok(number_call("112","112"),label_button("112w",getString(R.string.edinay_slugba_spasenia)));
                break;
            case (R.id.button_polisia):
                zvonok(number_call("102","102"),label_button("102w",getString(R.string.Policia)));
                break;
            case (R.id.button_skoray):
                zvonok(number_call("103","103"),label_button("103w",getString(R.string.Skoray)));
                break;
            case (R.id.button_gaz):
                zvonok(number_call("104","104"),label_button("104w",getString(R.string.Gaz)));
                break;
        }
    }

    public String number_call(String value,String def){
        String n = save_read(value);
        if(n==null||n==""||n.length()==0){return def;}else{return n;}
    }

    public void abaut(){
        Intent i = new Intent(this,Abaut.class);
        startActivity(i);
    }

    public void zvonok(final String number,String s_s){
       if(save_read(ALERT_DIALOG)=="net") {
           // звоним
           Intent i = new Intent(Intent.ACTION_CALL);
           i.setData(Uri.parse("tel:" + number));
           startActivity(i);

       }else{
           AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
           String v;
           int sdk = android.os.Build.VERSION.SDK_INT;
           if(sdk < android.os.Build.VERSION_CODES. HONEYCOMB) {
               v = "";
           } else {
               v = getString(R.string.vi_deistvitelno_hotite_pozvonot);
           }
           String p = v+s_s+"? "+ number;

           //подкрашиваем
           Spannable text = new SpannableString(p);
           text.setSpan(new ForegroundColorSpan(Color.RED),v.length(),p.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

           b.setTitle(getString(R.string.zvonok))
                   .setIcon(R.drawable.ico_phone)
                   .setMessage(text)
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
                           String  encodedPhonenumber = null;
                           try {
                               encodedPhonenumber = URLEncoder.encode(number, "UTF-8"); // делаем норм строку
                           } catch (UnsupportedEncodingException e) {
                               e.printStackTrace();
                           }
                           startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + encodedPhonenumber)));

                       }
                   });
           AlertDialog alert = b.create();
           alert.show();
       }
    }

    public void clik_operatori(View view) {
        Intent i = new Intent(this,Operatori.class);
        startActivity(i);
    }

    public void clik_vse_slugbi(View view) {
        Intent intent_vse_slugbi = new Intent(this, List_number.class);
        intent_vse_slugbi.putExtra("list_number", "vse");
        startActivity(intent_vse_slugbi);
    }

    public void clik_gorod(View view) {
        Intent i = new Intent(this,Pozvonit_na_gorodskoy.class);
        startActivity(i);
    }



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

    public void Clik_n(View view) {
        switch (view.getId()){
//            case(R.id.button_perezvoni):
//
//                String t=save_read("operator");
//                if(t==null||t==""||t.length()==0)
//                { Intent i_p = new Intent(this,List_operatori.class);
//                  startActivity(i_p);}
//                else
//                {Intent z = new Intent(this,Zapros.class);
//                 z.putExtra("key","perezvoni");startActivity(z);}
//
//                break;
            case(R.id.button_dolg): // тут прям реализуем

                String t_d =save_read("operator");
                if(t_d ==null|| t_d ==""|| t_d.length()==0)
                { Intent i_p = new Intent(this,List_operatori.class);
                    startActivity(i_p);}
                else{zapros_dolg(t_d);}

                break;
            case(R.id.button_popolni):

                String t_p =save_read("operator");
                if(t_p ==null|| t_p ==""|| t_p.length()==0)
                { Intent i_p = new Intent(this,List_operatori.class);
                    startActivity(i_p);}
                else
                {Intent z = new Intent(this,Zapros.class);
                    z.putExtra("key","popolni");startActivity(z);}

                break;
            case(R.id.button_perevesti):

                String t_per =save_read("operator");
                if(t_per ==null|| t_per ==""|| t_per.length()==0)
                { Intent i_p = new Intent(this,List_operatori.class);
                    startActivity(i_p);}
                else
                {Intent z = new Intent(this,Zapros.class);
                    z.putExtra("key","perevod");startActivity(z);}

                break;
        }
    }


    public void zapros_dolg(String n) {
        if (n.equals("Tele2")) {
            Vopros_pro_operatora("Tele2", "*122*1#");

        } else if (n.equals("Mtc")) {
            Vopros_pro_operatora("Mtc", "*111*123#");

        } else if (n.equals("Beeline")) {
            Vopros_pro_operatora("Beeline", "*141#");

        } else if (n.equals("Megafon")) {
            Vopros_pro_operatora("Megafon", "*106#");

        } else if (n.equals("Smarts")) {
            Vopros_pro_operatora("Smarts", "*101*5#");

        }
    }

    public void Vopros_pro_operatora(String operator, final String zapros){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View content = LayoutInflater.from(this).inflate(R.layout.custon_dialog, null);
        builder.setView(content);
        final AlertDialog alertDialog = builder.create();
        ((TextView)content.findViewById(R.id.textView_dialog_operator)).setText(operator);
        ((Button)content.findViewById(R.id.button_dialog_da)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
                Toast("Выполням запрос:"+zapros);
                coll(zapros);
            }
        });
        ((Button)content.findViewById(R.id.button_dialog_net)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
                Intent i_p = new Intent(getApplicationContext(),List_operatori.class);
                startActivity(i_p);
            }
        });

        alertDialog.show();
    }




    public void coll(String n){
        // звоним
        String encod = null;
        try {
            encod = URLEncoder.encode(n, "UTF-8"); // делаем норм строку
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + encod)));
    }

    public  void  Toast(String mesag){
        SuperToast.create(this, mesag, SuperToast.Duration.LONG,
                Style.getStyle(Style.GREEN, SuperToast.Animations.FLYIN)).show();
    }
    public void Toast_error(String mesag){
        SuperToast.create(this, mesag, SuperToast.Duration.LONG,
                Style.getStyle(Style.RED, SuperToast.Animations.POPUP)).show();
    }

    public static boolean isOnline(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }
}
