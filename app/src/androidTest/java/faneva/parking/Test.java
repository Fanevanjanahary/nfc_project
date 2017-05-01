package faneva.parking;

import android.test.AndroidTestCase;
import android.util.Log;

import faneva.parking.util.Service;

/**
 * Created by ANDRIAMIADANTSOA on 01/05/2017.
 */
public class Test  extends AndroidTestCase {

    String tag="nfc_parking";
    /*public void testUrl() throws Exception {
        String req = Config.HEURE_ARRIVEE+"matri=1715 TBA";
        Log.d(tag, "urrrl" + Service.getHeureArrivee("1715 TBA"));
    }*/

    public void testAjoutVehicule() throws Exception {
        Log.d(tag, "ajout========" + Service.ajouterVehicule("Citroen","Familiale","C8","Noir","0508 TBA"));
    }
}
