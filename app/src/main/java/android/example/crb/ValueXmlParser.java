package android.example.crb;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class ValueXmlParser {
    private ArrayList<Valute> valutes;

    public ValueXmlParser(){
        valutes = new ArrayList<>();
    }

    public ArrayList<Valute> getValutes(){
        return valutes;
    }

    public boolean parse(String xmlData){
        boolean status = true;
        Valute valute = null;
        boolean inEntry = false;
        String texValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){

                String tagName = xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if("Valute".equalsIgnoreCase(tagName)){
                           inEntry = true;
                           valute = new Valute();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        texValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(inEntry){
                            if("valute".equalsIgnoreCase(tagName)){
                                valutes.add(valute);
                                inEntry = false;
                            }else if("CharCode".equalsIgnoreCase(tagName)){
                                valute.setCharCode(texValue);
                            }else if("Nominal".equalsIgnoreCase(tagName)){
                                valute.setNominal(texValue);
                            }else if("Name".equalsIgnoreCase(tagName)){
                                valute.setName(texValue);
                            }else if("Value".equalsIgnoreCase(tagName)){
                                valute.setValue(texValue);
                            }
                        }
                        break;
                }
                eventType = xpp.next();
            }
        }catch (Exception e){
            status = false;
            e.printStackTrace();
        }

        return status;
    }

}
