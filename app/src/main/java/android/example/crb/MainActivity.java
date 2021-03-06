package android.example.crb;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Valute> currencies;
    LinearLayout.LayoutParams params;
    LinearLayout mainLayout;
    LinearLayout.LayoutParams textParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializedParams();
        mainLayout = findViewById(R.id.mainLayout);

        Thread threadForCurrencies = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                  getCurrencies();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        threadForCurrencies.start();

        try {
            threadForCurrencies.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < currencies.size(); i++){
            changeParams(currencies.get(i));
        }
    }

    private void changeParams(Valute val){
        LinearLayout layout = new LinearLayout(this);
        layout.setBackground(getResources().getDrawable(R.drawable.cardlaoyt));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(params);
        addTextView(layout, val);
        mainLayout.addView(layout);
    }

    //Инициализируем параметры элементов
    private void initializedParams(){
        textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textParams.setMargins(40, 30, 10, 10);

        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 400);
        params.gravity = Gravity.CENTER;
        params.setMargins(20, 15, 20, 15);

    }
    private void addTextView(LinearLayout layout, Valute val){
         TextView nominalWithCode = new TextView(this);
         TextView name = new TextView(this);
         TextView value = new TextView(this);

         nominalWithCode.setText(val.getNominal() + " " + val.getCharCode());
         name.setText(val.getName());
         value.setText(val.getValue());

         nominalWithCode.setTextColor(Color.BLACK);
         name.setTextColor(Color.BLACK);
         value.setTextColor(Color.BLACK);

         nominalWithCode.setTextSize(17);
         name.setTextSize(15);
         value.setTextSize(15);

         name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

         layout.addView(name, textParams);
         layout.addView(nominalWithCode, textParams);
         layout.addView(value, textParams);
    }

    private void getCurrencies() throws IOException {
        String content = download("https://www.cbr.ru/scripts/XML_daily.asp?date_req=");
        ValueXmlParser vlParser = new ValueXmlParser();
        vlParser.parse(content);
        currencies = vlParser.getValutes();
    }

    private String download(String urlPath) throws IOException{
        StringBuilder xmlResult = new StringBuilder();
        BufferedReader reader = null;
        InputStream stream = null;
        HttpsURLConnection connection = null;

        try {
            URL url = new URL(urlPath);
            connection = (HttpsURLConnection) url.openConnection();
            stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream, "Cp1251"));
            String line;

            while((line=reader.readLine()) != null) {
                xmlResult.append(line);
            }

            return xmlResult.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}

