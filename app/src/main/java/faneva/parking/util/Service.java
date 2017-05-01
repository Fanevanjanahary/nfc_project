package faneva.parking.util;

import android.text.format.Time;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ANDRIAMIADANTSOA .
 */
public class Service {

    public static String getRequest(String uri) throws Exception{
        InputStream is = null;
        String result = null;
        HttpClient httpClient = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(uri);

        HttpResponse response = httpClient.execute(httpGet);

        HttpEntity entity = response.getEntity();

        is = entity.getContent();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
        StringBuilder sb = new StringBuilder();

        String line = null;
        while ((line = reader.readLine()) != null)
        {
            sb.append(line + "\n");
        }
        result = sb.toString();
        return result;

    }

    public static String ajouterVehicule(String marque, String categorie, String type, String couleur, String matriculation) throws Exception {
        String urlAjoutVehicule = Config.AJOUT_VEHICULE+"marque="+marque.replace(" ","%20")+"&categorie="+categorie+"&type="+type.replace(" ","%20")+"&couleur="+couleur.replace(" ","%20")+"&matri="+matriculation.replace(" ","%20");
        String result = getRequest(urlAjoutVehicule);
        return  result;
    }

    public static String getHeureArrivee(String matri) throws Exception {
        String urlHeureV = Config.HEURE_ARRIVEE+"matri="+matri.replace(" ","%20");
        String result = getRequest(urlHeureV);
        return result;
    }

    public static String addhistorique(String matri) throws Exception {
        String urlUpdate = Config.AJOUT_HISTORIQUE+"matri="+matri.replace(" ","%20");
        String result = getRequest(urlUpdate);
        return result;
    }

    public static int getDuree(String matri) throws Exception {
        String[] desc = getHeureArrivee(matri).split(" ");
        Time heurea = new  Time (desc[1]);
        int ha =heurea.hour; int min = heurea.minute;
        Time today = new Time(Time.getCurrentTimezone());
        int hs = today.hour; int mins = today.minute;
        int difh =ha - hs; int mind = min-mins;
        int duree = difh*60 + mind ;
        return duree;
        
    }
}
