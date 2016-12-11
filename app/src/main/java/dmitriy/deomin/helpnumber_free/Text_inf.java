package dmitriy.deomin.helpnumber_free;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;


public class Text_inf extends Activity {

    TextView textView;
    Spannable text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_inf);
        textView =(TextView)findViewById(R.id.textView);

        String s = getIntent().getStringExtra("text_inf");
        if (s.equals("pogar")) {//форматирование текста
            //UnderlineSpan() - подчеркнутый текст
            //StyleSpan(Typeface.BOLD) - полужирный тектс
            //StyleSpan(Typeface.ITALIC) - курсив
            //ForegroundColorSpan(Color.GREEN) - цвет
            text = new SpannableString(getResources().getText(R.string.Kak_vizvat_pozar_text));

            text.setSpan(new UnderlineSpan(), 0, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            text.setSpan(new ForegroundColorSpan(Color.GREEN), 0, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.setText(text);

        } else if (s.equals("122")) {
            text = new SpannableString(getResources().getText(R.string.Abaut_112_text));

            text.setSpan(new UnderlineSpan(), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            text.setSpan(new ForegroundColorSpan(Color.GREEN), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.setText(text);

        } else if (s.equals("polis")) {
            text = new SpannableString(getResources().getText(R.string.Kak_vizvat_pol_text));

            text.setSpan(new UnderlineSpan(), 0, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            text.setSpan(new ForegroundColorSpan(Color.GREEN), 0, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.setText(text);

        } else if (s.equals("skor")) {
            text = new SpannableString(getResources().getText(R.string.Kak_vizvat_skoru_text));

            text.setSpan(new UnderlineSpan(), 0, 50, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            text.setSpan(new ForegroundColorSpan(Color.GREEN), 0, 50, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.setText(text);

        }

    }


}
