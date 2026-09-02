package com.computationaldesign.wera.jalo;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.enumeration.EnumerationManager;
import de.hybris.platform.jalo.enumeration.EnumerationType;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.type.AttributeDescriptor;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.util.Config;

class FilenameFilterExt implements FilenameFilter {
	private String m_strMatch = "";

	public void Init(String strMatch) {
		m_strMatch = strMatch;
	}

	public boolean accept(File dir, String name) {
		return name.contains(m_strMatch);
	}
};

public class DataImportOpal extends WeraManager {

	private String m_strTransferPath = "";

	private String m_strTransferFile = "";

	private SAXBuilder m_oSAXBuilder = null;

	private Document m_oDocument = null;

	private Element m_oElementRoot = null;

	private ArrayList m_alCATALOG_GROUP_SYSTEM = null;

	private ArrayList m_alARTICLE = null;

	private ArrayList m_alErrorLogFile = null;

	private ArrayList m_alLogFile = null;

	private XmlSupport m_oXmlSupport = null;

	private File m_hAktListFile = null;

	private WeraManager m_wm = null;

	private boolean m_bProductIsSet = false;
	private WeraProduct m_oDummyProduct = null;

	public DataImportOpal() {
		super();
		// TODO Auto-generated constructor stub

		// --- Support f�r div. Hilfsmethoden
		m_oXmlSupport = new XmlSupport();

		// --- Arrays f�r logs eranlegen
		m_alErrorLogFile = new ArrayList();
		m_alLogFile = new ArrayList();

		// --- Initialize XML-Support
		m_oSAXBuilder = new SAXBuilder(false); // validierenden Parser nutzen
		m_oSAXBuilder.setFeature("http://xml.org/sax/features/validation",
				false);

		// --- Initialize div.
		m_strTransferPath = Config.getParameter("wera.homepath") + "/import/opal/";
		m_alCATALOG_GROUP_SYSTEM = new ArrayList();
		m_alARTICLE = new ArrayList();
		m_wm = this;
		
		
		// --- Import- Produkt suchen / anlegen
		String strProduktCode = "import_produkt";
		m_oDummyProduct = (WeraProduct) m_weraCatalogVersion.getProduct(strProduktCode);

		if (m_oDummyProduct == null) {
			// --- Neuanlage
			System.out.println("++ not found=" + strProduktCode);
			m_alLogFile.add("=>Produkt CODE_NR wird angelegt " + strProduktCode);
			Map parameters = new HashMap();
			parameters.put("code", strProduktCode);

			// --- Neues Produkt
			m_oDummyProduct = m_wm.createWeraProdukt(parameters);
			m_oDummyProduct.afterSave(null);
		}
		
	}

	// --- Pr�ft, ob Dateien f�r den Import vorliegen
	public void ScheduleOpalImport() {

		// --- Initialize
		String strFileName = "";

		// --- Suche nach Sem-Dateien
		File hListing = new File(m_strTransferPath);

		FilenameFilterExt filterFileMatch = new FilenameFilterExt();
		filterFileMatch.Init(".ready");
		File[] files = hListing.listFiles(filterFileMatch);
		for (int iCnt = 0; iCnt < files.length; iCnt++) {

			// --- Get Filename
			m_hAktListFile = files[iCnt];

			// --- Importiere XML-Datei
			StartImport(m_hAktListFile.getName().replaceAll("ready", "xml"));

			// --- Aufr�umen
			EndImport();
		}

	}

	// --- Importiert die Datendateien nach Hybris
	public void StartImport(String strTransferFile) {

		// --- Debug
		System.out.println("StartImport." + strTransferFile);

		// --- Initialize
		m_strTransferFile = strTransferFile;

		// --- Lege Verzeichnisse an, falls nicht vorhanden
		System.out.println("Create Archiv-Directory mkdir()= "
				+ createDirectory(m_strTransferPath + "archiv"));
		System.out.println("Create Error-Directory mkdir()= "
				+ createDirectory(m_strTransferPath + "error"));

		// --- Open XML-Datei
		if (openTransferFile() != null) {

			// --- Initialize 
			m_oElementRoot = m_oDocument.getRootElement();

			// --- Catalog Group System
			readCATALOG_GROUP_SYSTEM();

			// --- Import- / Update der Produkte
			ImportProducts();
		}
	}

	// --- Aufr�umen
	public void EndImport() {

		// --- Debug
		System.out.println("EndImport");

		// --- Logdatei schreiben
		if (m_alLogFile.size() > 0) {
			m_oXmlSupport._WriteFileFromArray(m_alLogFile, m_strTransferPath
					+ m_strTransferFile.replace("xml", "log"));
		}

		// --- Logs / Error schreiben
		if (m_alErrorLogFile.size() > 0) {

			// --- Fehler- Error - LOG schreiben
			m_oXmlSupport
					._WriteFileFromArray(m_alErrorLogFile, m_strTransferPath
							+ m_strTransferFile.replace("xml", "err"));

			// --- Dateien nach ERR verschieben
			bMoveFiles(m_strTransferFile.replace(".xml", ""), m_strTransferPath
					+ "error");

		} else {

			// --- Kein Fehler!!!
			// --- Dateien nach ARCHIV verschieben
			bMoveFiles(m_strTransferFile.replace(".xml", ""), m_strTransferPath
					+ "archiv");
		}

		// --- Initialize XML-Support
		m_oDocument = null;
		m_alCATALOG_GROUP_SYSTEM.clear();
		m_alARTICLE.clear();
		m_alErrorLogFile.clear();
		m_alLogFile.clear();
	}

	// --- �ffnen der Eingabedatei
	public Document openTransferFile() {

		// --- Debug
		System.out.println("openTransferFile");

		// --- �ffnen der Datei
		try {
			m_oDocument = m_oSAXBuilder.build(new File(m_strTransferPath
					+ m_strTransferFile));

		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			System.out.println("openTransferFile.JDOMException => Abbruch!!");
			m_alErrorLogFile.add(e.toString());
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("openTransferFile.IOException => Abbruch!!");
			m_alErrorLogFile.add(e.toString());
			e.printStackTrace();

		}

		return m_oDocument;
	}

	public void readCATALOG_GROUP_SYSTEM() {

		// --- Debug
		System.out.println("readCATALOG_GROUP_SYSTEM");

		// --- Initialize
		Element oCATALOG_STRUCTURE = null;
		Element oGROUP_NAME = null;
		Element oGROUP_ID = null;

		try {
			List children = XPath
					.selectNodes(m_oDocument,
							"//Hybris/T_NEW_CATALOG/CATALOG_GROUP_SYSTEM/CATALOG_STRUCTURE");
			if (children.size() > 0) {

				Iterator childrenIT = children.iterator();
				while (childrenIT.hasNext()) {

					// --- get node
					oCATALOG_STRUCTURE = (Element) childrenIT.next();
					if (oCATALOG_STRUCTURE.getAttribute("type").getValue()
							.equals("WeraProduct")) {

						if (!m_alCATALOG_GROUP_SYSTEM
								.contains(oCATALOG_STRUCTURE)) {

							oGROUP_ID = oCATALOG_STRUCTURE.getChild("GROUP_ID");
							if (oGROUP_ID != null
									&& oGROUP_ID.getTextTrim().length() > 0) {

								// --- remember node
								m_alCATALOG_GROUP_SYSTEM
										.add(oCATALOG_STRUCTURE);
							} else {
								// --- error
								String strGROUP_NAME = oCATALOG_STRUCTURE
										.getChild("GROUP_NAME").getText();
								m_alErrorLogFile
										.add("=>CODE_NR (GROUP_ID) fehlt, "
												+ strGROUP_NAME);
							}

						}

					}

				}
			}

		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			System.out.println("readCATALOG_GROUP_SYSTEM.JDOMException => Abbruch!!");
			m_alErrorLogFile.add(e.toString());
			e.printStackTrace();
		}
	}

	// --- Import / Update der Produktdaten
	private void ImportProducts() {

		// --- Initialize
		String strGROUP_ID = "";
		WeraProduct oProduct = null;
		Element oGROUP_NAME = null;
		Element oProductNode = null;

		// --- Hole Import-Kategory
		Category oCategoryIMPORT_OPAL = m_weraCatalogVersion
				.getCategory("IMPORT_OPAL");

		// --- Schleife �ber alle Produktknoten
		for (Iterator itProducts = m_alCATALOG_GROUP_SYSTEM.iterator(); itProducts
				.hasNext();) {
			// --- get node
			oProductNode = (Element) itProducts.next();
			strGROUP_ID = oProductNode.getChild("GROUP_ID").getText();
			System.out.println("Read strGROUP_ID=" + strGROUP_ID );
			Item oProductItem = (Item) m_weraCatalogVersion.getProduct(strGROUP_ID);
			//System.out.println("Read oProductItem.getClass().toString()=" + oProductItem.getClass().toString() );

			// --- Hole Produktbeschreibungen
			List listGROUP_NAME = null;
			String strBeschDE = "";
			try {
				listGROUP_NAME = XPath.selectNodes(oProductNode,
				"GROUP_NAME[@lang='de']");
				if ( listGROUP_NAME.size() > 0 )
					strBeschDE = ((Element)listGROUP_NAME.get(0)).getTextTrim();
				listGROUP_NAME = XPath.selectNodes(oProductNode,
				"GROUP_NAME");
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				System.out.println("JDOMException => Abbruch!!");
				m_alErrorLogFile.add(e.toString());
				e.printStackTrace();
			}
			
			// --- Suche das Produkt
            Collection colSearchProducts =  m_weraCatalogVersion.getProducts(strGROUP_ID);
            for ( Iterator itSearchProducts = colSearchProducts.iterator(); itSearchProducts.hasNext(); ) {
                Product oSeachProduct = (Product) itSearchProducts.next();
                if ( oSeachProduct.getCode().equals(strGROUP_ID) && oSeachProduct instanceof WeraProduct ) {

                	// --- Deutsche beschreibung pr�fen!
                	if ( strBeschDE.equals(oSeachProduct.getName() )) {
                		// --- Identisches Produkt gefunden
                	   oProduct = (WeraProduct) oSeachProduct;
                	   break;
                	}
                }
            }
            	
            // --- Produkt muss neu angelegt werden
			if (oProduct == null) {
				// --- Neuanlage
				System.out.println("++ not found=" + strGROUP_ID);
				m_alLogFile.add("=>Produkt CODE_NR wird angelegt "
						+ strGROUP_ID);
				Map parameters = new HashMap();
				parameters.put("code", strGROUP_ID);

				// --- Pr�fe auf ProduktSet
				int counter = 0;
				for (int i = 0; i < strGROUP_ID.length(); i++)
					if (strGROUP_ID.charAt(i) == '/')
						counter++;
				m_bProductIsSet = (counter > 1);

				if (m_bProductIsSet)
					oProduct = m_wm.createWeraProduktSet(parameters);
				else
					oProduct = m_wm.createWeraProdukt(parameters);
				oProduct.afterSave(null);

				// --- Zurodnen der Produktbeschreibungen
				Iterator itGROUP_NAME = listGROUP_NAME.iterator();
				while (itGROUP_NAME.hasNext()) {
					oGROUP_NAME = (Element) itGROUP_NAME.next();
					SetLanguage(oGROUP_NAME.getAttribute("lang").getValue());
					oProduct.setName(oGROUP_NAME.getText());
					oProduct.setDescription1("Import Opal");
				}
				SetLanguage("de");

			}
			if ( oProduct instanceof WeraProductSet ) m_bProductIsSet = true;


			// --- Produkt exitiert nun
			if (oProduct != null) {
				// --- Update
				System.out.println("++ found=" + strGROUP_ID);
				m_alLogFile.add("=>Produkt CODE_NR (" + strGROUP_ID
						+ ") Daten werden eingelesen.");


				// --- In Importkategorie einf�gen
				Collection colProductsCategory = new ArrayList();
				Collection products = (Collection) m_wm.getAttribute(
						oCategoryIMPORT_OPAL, "products");
				if (products != null)
					colProductsCategory.addAll(products);

				Collection colCategoriesProduct = new ArrayList();
				Collection supercategories = (Collection) m_wm.getAttribute(
						oProduct, "supercategories");
				if (supercategories != null)
					colCategoriesProduct.addAll(supercategories);

				if (!colCategoriesProduct.contains(oCategoryIMPORT_OPAL)) {
					colProductsCategory.add(oProduct);
					colCategoriesProduct.add(oCategoryIMPORT_OPAL);
					m_wm.setAttribute(oProduct, "supercategories",
							colCategoriesProduct);
					m_wm.setAttribute(oCategoryIMPORT_OPAL, "products",
							colProductsCategory);
				}

				// --- Suchen der Artikel
				readARTICLE_TO_CATALOGGROUP_MAP(strGROUP_ID);

				// --- Importieren der Artikel
				ImportArtikel(oProduct, listGROUP_NAME);
			}
		}
	}

	// --- Anlegen der Artikel
	private void ImportArtikel(WeraProduct product, List listGROUP_NAME) {
		// TODO Auto-generated method stub
		// --- Hole WeraProduktType
		
		ComposedType WeraProductType = null;
		if ( (product instanceof WeraProductSet) )
			   // --- WeraProduktSet
			   WeraProductType = JaloSession.getCurrentSession().getTypeManager().getComposedType(WeraProductSet.class);
		else
			   // --- WeraProdukt
			   WeraProductType = JaloSession.getCurrentSession().getTypeManager().getComposedType(WeraProduct.class);

		// --- Hole Default-WeraVariantType
		// AttributeDescriptor variantType = WeraProductType.getAttributeDescriptor("variantType");
		// ComposedType WeraVariantenType = (ComposedType) variantType.getDefaultValue();
		
		// --- Initialize
		Boolean oAkitv = new Boolean(true);
		Element oARTIKEL = null;
		Element oFEATURE = null;
		WeraVariante oWeraVariante = null;
		WeraVarianteSet oWeraVarianteSet = null;
		String strCode = "";
		HashMap hashmapFeatures = new HashMap(); 
		Collection colVariants = (Collection) m_wm.getAttribute(product,"variants");
		Collection colNewVariants = new ArrayList();
		if ( colVariants != null ) colNewVariants.addAll(colVariants);
		
		// --- Schleife �ber alle Artikel
		for ( Iterator itARTIKEL=m_alARTICLE.iterator(); itARTIKEL.hasNext(); ) {
		
			// --- get node
			oARTIKEL = (Element) itARTIKEL.next();
			
            // --- Einlesen der Artikel-Features
			readARTICLE_FEATURES ( oARTIKEL, hashmapFeatures);
			
            oFEATURE = (Element) hashmapFeatures.get("code");		
			if ( oFEATURE != null && oFEATURE.getChild("FVALUE").getTextTrim() != "" ) {
                // --- Get Code-Nr
            	strCode = oFEATURE.getChild("FVALUE").getTextTrim();
    			oWeraVariante = (WeraVariante) m_weraCatalogVersion.getProduct(strCode);
    			if ( oWeraVariante == null ) {
    				System.out.println("++create WeraVariante Set=" + m_bProductIsSet + ", strCode=" + strCode);
    				Map parameters = new HashMap();
    				parameters.put("code", strCode);
    				if ( m_bProductIsSet )
        				parameters.put("baseProduct", m_oDummyProduct );
    				else
        				parameters.put("baseProduct", product);
					oWeraVariante = m_wm.createWeraProductVariante( parameters );
    			}
    			else
				    System.out.println("++found WeraVariante=" + strCode);

				// --- Zuordnen der Werte
    			_ImportArtikel( oWeraVariante, hashmapFeatures, listGROUP_NAME);


				// --- Satz
				if ( m_bProductIsSet ) {
	    			if (oWeraVariante != null) {
						Map parameters = new HashMap();
						parameters.put("code", strCode);
						parameters.put("weravariants", oWeraVariante);
						parameters.put("baseProduct", product);
						oWeraVarianteSet = (WeraVarianteSet) m_wm
								.createWeraProductVarianteSet(
										JaloSession.getCurrentSession()
												.getTypeManager()
												.getComposedType(
														WeraVarianteSet.class),
										parameters);
					    
		    			// --- Zuordnen der Werte
		    			_ImportArtikel( oWeraVarianteSet, hashmapFeatures, listGROUP_NAME);
					    
						colNewVariants.add(oWeraVarianteSet);
					}
				} else {
					
					// --- Variante dem Produkt zuf�gen
	    			if ( oWeraVariante != null && !colNewVariants.contains(oWeraVariante) ) {
	    			    colNewVariants.add(oWeraVariante);
	    			}
				}

				try {
					product.setAttribute( "variants", colNewVariants);
				} catch (JaloInvalidParameterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("ImportArtikel.JaloInvalidParameterException => Abbruch!!");
					m_alErrorLogFile.add(e.toString());
				} catch (JaloSecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("ImportArtikel.JaloSecurityException => Abbruch!!");
					m_alErrorLogFile.add(e.toString());
				} catch (JaloBusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("ImportArtikel.JaloBusinessException => Abbruch!!");
					m_alErrorLogFile.add(e.toString());
				}

				m_alLogFile.add("===>Artikel (" + strCode
						+ ") Daten werden eingelesen.");
            }
            else
			   m_alErrorLogFile.add("=>FNAME (code/FVALUE) is missing - skip import articel" );				

		}

	}
	
	// --- Zuordnen der Werte
	private void _ImportArtikel(WeraVariante oWeraVariante, HashMap hashmapFeatures, List listGROUP_NAME) {

		// --- Initialize
		Boolean oAkitv = new Boolean(true);

		// --- Hole Variantennummer
		String strVariantenNr = "001";
		Element oFEATURE = (Element) hashmapFeatures.get("variantenNr");	
		if ( oFEATURE != null && oFEATURE.getChild("FVALUE").getTextTrim() != "" ) 
		   strVariantenNr = oFEATURE.getChild("FVALUE").getTextTrim();

		// --- Hole lagerNR
		String strlagerNR = "05";
		oFEATURE = (Element) hashmapFeatures.get("lagerNR");	
		if ( oFEATURE != null && oFEATURE.getChild("FVALUE").getTextTrim() != "" ) {
		   strlagerNR = oFEATURE.getChild("FVALUE").getTextTrim();
		   m_wm.setAttribute(oWeraVariante,"LagerNr",strlagerNR);
		}
		
		// --- Hole ean
		String strean = "";
		oFEATURE = (Element) hashmapFeatures.get("ean");	
		if ( oFEATURE != null && oFEATURE.getChild("FVALUE").getTextTrim() != "" ) {
			strean = oFEATURE.getChild("FVALUE").getTextTrim();
			   m_wm.setAttribute(oWeraVariante,"ean",strean);
		}

		// --- Hole contentQuantity
		String strcontentQuantity = "";
		oFEATURE = (Element) hashmapFeatures.get("contentQuantity");	
		if ( oFEATURE != null && oFEATURE.getChild("FVALUE").getTextTrim() != "" ) {
			strcontentQuantity = oFEATURE.getChild("FVALUE").getTextTrim();
			   m_wm.setAttribute(oWeraVariante,"contentQuantity",new Integer(strcontentQuantity));
		}
		
		// --- Hole Gewicht
		String strGewicht = "";
		oFEATURE = (Element) hashmapFeatures.get("Gewicht");	
		if ( oFEATURE != null && oFEATURE.getChild("FVALUE").getTextTrim() != "" ) {
			strGewicht = oFEATURE.getChild("FVALUE").getTextTrim();
			   m_wm.setAttribute(oWeraVariante,"Gewicht",strGewicht);
		}

		// --- Hole GewVE
		String strGewVE = "";
		oFEATURE = (Element) hashmapFeatures.get("GewVE");	
		if ( oFEATURE != null && oFEATURE.getChild("FVALUE").getTextTrim() != "" ) {
			strGewVE = oFEATURE.getChild("FVALUE").getTextTrim();
			   m_wm.setAttribute(oWeraVariante,"GewVE",strGewicht);
		}

		// --- Hole Ursprungsland
		String strUrsprungsland = "";
		oFEATURE = (Element) hashmapFeatures.get("Ursprungsland");	
		if ( oFEATURE != null && oFEATURE.getChild("FVALUE").getTextTrim() != "" ) {
			strUrsprungsland = oFEATURE.getChild("FVALUE").getTextTrim();
			   m_wm.setAttribute(oWeraVariante,"Ursprungsland",strUrsprungsland);
		}

		// --- Hole ZolltarifNr
		String strZolltarifNr = "";
		oFEATURE = (Element) hashmapFeatures.get("ZolltarifNr");	
		if ( oFEATURE != null && oFEATURE.getChild("FVALUE").getTextTrim() != "" ) {
			strZolltarifNr = oFEATURE.getChild("FVALUE").getTextTrim();
			   m_wm.setAttribute(oWeraVariante,"ZolltarifNr",strZolltarifNr);
		}

		
		// ---- GewichtEinheit (Attribute)
		String strGewichtEinheit = "";
		EnumerationManager em = JaloSession.getCurrentSession().getEnumerationManager();
		EnumerationType    et = null;
		EnumerationValue   ev = null;
		et = em.getEnumerationType( "EinheitEnum" );
		EnumerationValue evDefault            = em.getEnumerationValue( et, "gramm" );
		oFEATURE = (Element) hashmapFeatures.get("GewichtEinheit");	
		if ( oFEATURE != null && oFEATURE.getChild("FVALUE").getTextTrim() != "" ) {
			
			strGewichtEinheit = oFEATURE.getChild("FVALUE").getTextTrim();
			evDefault  = em.getEnumerationValue( et, strGewichtEinheit.toLowerCase() );
		}
		m_wm.setAttribute(oWeraVariante,"GewichtEinheit",evDefault);
		
		// --- Hole ZolltarifNr
		String  strAbmessung = "";
		oFEATURE = (Element) hashmapFeatures.get("abmessung_1");	
		if ( oFEATURE != null && oFEATURE.getChild("FVALUE").getTextTrim() != "" ) 
			strAbmessung += oFEATURE.getChild("FDESCR").getTextTrim() + "=" + oFEATURE.getChild("FVALUE").getTextTrim() + "\n";
		oFEATURE = (Element) hashmapFeatures.get("abmessung_2");	
		if ( oFEATURE != null && oFEATURE.getChild("FVALUE").getTextTrim() != "" ) 
			strAbmessung += oFEATURE.getChild("FDESCR").getTextTrim() + "=" + oFEATURE.getChild("FVALUE").getTextTrim() + "\n";
		oFEATURE = (Element) hashmapFeatures.get("abmessung_3");	
		if ( oFEATURE != null && oFEATURE.getChild("FVALUE").getTextTrim() != "" ) 
			strAbmessung += oFEATURE.getChild("FDESCR").getTextTrim() + "=" + oFEATURE.getChild("FVALUE").getTextTrim() + "\n";
		
		// --- Lokalisierete Werte setzen
		Iterator itGROUP_NAME = listGROUP_NAME.iterator();
		while (itGROUP_NAME.hasNext()) {
			Element oGROUP_NAME = (Element) itGROUP_NAME.next();
			SetLanguage(oGROUP_NAME.getAttribute("lang").getValue());

			oWeraVariante.setDescription(strAbmessung);
			oWeraVariante.setName(oGROUP_NAME.getText());
			oWeraVariante.setVariantenNr(strVariantenNr);
			oWeraVariante.setAktiv(oAkitv);
		}
		SetLanguage("de");
}
//	 --- Einlesen der Artikel-Features
	private void readARTICLE_FEATURES(Element oARTIKEL, HashMap hashmapFeatures) { 
		
		// --- Initialize
		Element oFEATURE = null;
		Element oFNAME = null;
		String strKey = "";
		
		try {
			// --- Hole alle Artikel des Produktes
			List listARTICLE_FEATURES = XPath.selectNodes(oARTIKEL,
					"ARTICLE_FEATURES/FEATURE" );

			// --- Schleife �ber alle Artikel des Produktes
			if (listARTICLE_FEATURES.size() > 0) {
				Iterator childrenIT = listARTICLE_FEATURES.iterator();
				while (childrenIT.hasNext()) {
					// --- get node
					oFEATURE = (Element) childrenIT.next();
					oFNAME = oFEATURE.getChild("FNAME");
					if ( oFNAME != null ) {
					   strKey = oFNAME.getText();
					   hashmapFeatures.put(strKey,oFEATURE);
					}
					else
						   m_alErrorLogFile.add("=>FNAME is missing" );				}
			}

		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			System.out.println("readARTICLE_FEATURES.JDOMException => Abbruch!!");
			m_alErrorLogFile.add(e.toString());
			e.printStackTrace();
		}

	}
	
	public void readARTICLE_TO_CATALOGGROUP_MAP(String strCode) {

		// --- Debug
		System.out.println("readARTICLE_TO_CATALOGGROUP_MAP");

		// --- Initialize
		Element oART_ID = null;

		try {
			// --- Hole alle Artikel des Produktes
			List listART_ID = XPath.selectNodes(m_oDocument,
					"//Hybris/T_NEW_CATALOG/ARTICLE_TO_CATALOGGROUP_MAP[CATALOG_GROUP_ID='"
							+ strCode + "']/ART_ID");

			// --- Schleife �ber alle Artikel des Produktes
			if (listART_ID.size() > 0) {
				Iterator childrenIT = listART_ID.iterator();
				while (childrenIT.hasNext()) {
					// --- get node
					oART_ID = (Element) childrenIT.next();
					System.out.println("oART_ID=" + oART_ID.getText());

					// --- Einlesen der Artikelnode
					List listARTICLE = XPath.selectNodes(m_oDocument,
							"//Hybris/T_NEW_CATALOG/ARTICLE[SUPPLIER_AID='"
									+ oART_ID.getText() + "']");
					System.out.println("listARTICLE=" + listARTICLE);
					if (listARTICLE != null && listARTICLE.size() > 0)
						m_alARTICLE.add(listARTICLE.get(0));
				}

			}

		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			System.out.println("readARTICLE_TO_CATALOGGROUP_MAP.JDOMException => Abbruch!!");
			m_alErrorLogFile.add(e.toString());
			e.printStackTrace();
		}
	}

	// --- Verschieben der Importdat
	private boolean bMoveFiles(String strFiles, String strDirectory) {
		// TODO Auto-generated method stub

		// --- Suche nach allen Dateien im Import-Pfad
		File hListing = new File(m_strTransferPath);

		// --- Filtern aller Dateien mit gleichem prefix
		FilenameFilterExt filterFileMatch = new FilenameFilterExt();
		filterFileMatch.Init(strFiles);
		File[] files = hListing.listFiles(filterFileMatch);
		for (int iCnt = 0; iCnt < files.length; iCnt++) {

			// --- Pr�fe, ob die Datei bereits im Zielverzeichnis existiert und l�sche sie
			File hFile = new File(strDirectory + "/" + files[iCnt].getName());
			if (hFile.exists())
				hFile.delete();

			// --- Verschieben einer Datei
			m_oXmlSupport._bMoveFile(m_strTransferPath + files[iCnt].getName(),
					strDirectory);
		}

		return true;
	}

}
