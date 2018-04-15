package ca.mcgill.ecse223.resto.application;

import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.persistence.PersistenceObjectStream;
import ca.mcgill.ecse223.resto.view.RestoAppPage;

public class RestoAppApplication {
	
	private static RestoApp  restoapp;
	private static RestoAppPage restoAppPage;
	private static String filename = "menu.resto";
	
	//Start of application
	public static void main(String[] args) {
		
		//Start UI
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                restoAppPage = new RestoAppPage();
                restoAppPage.setVisible(true);
            }
        });
	}

	//Get the RestoApp
	public static RestoApp getRestoapp() {
		if (restoapp == null) {			
			restoapp = load();
		}
 		return restoapp;
	}
	
	//Get the RestoApp Page
	public static RestoAppPage getRestoAppPage() {
		return restoAppPage;
	}
	
	//Save RestoApp to menu.resto
	public static void save() {
		PersistenceObjectStream.serialize(restoapp);
		}
	
	//Load RestoApp from menu.resto
	public static RestoApp load() {
		PersistenceObjectStream.setFilename(filename);
		restoapp = (RestoApp) PersistenceObjectStream.deserialize();
		if (restoapp == null) {
			restoapp = new RestoApp();
		}else {
			restoapp.reinitialize();
		}
		return restoapp;
	}

}
