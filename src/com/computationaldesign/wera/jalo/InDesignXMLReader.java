/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computationaldesign.wera.jalo;

import de.hybris.platform.jalo.product.Product;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;

import java.nio.charset.Charset;

class StandardCharsets {
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    //....
}


/**
 *
 * @author teschke
 */
class InDesignText {

	private final HashMap<String, String> hashAttributes;
	private String text;
	private int curColumn;

	/**
	 * constuctor
	 *
	 * @param strText
	 */
	public InDesignText(String strText) {

		// --- first initialize
		this.curColumn = 0;
		this.hashAttributes = new HashMap<String, String>();
		
		// --- text setzen
		this.setText(strText);
	}


	/**
	 * 
	 * @return 
	 */
	public boolean isEmpty() {
		
		return (this.text.length() <= 2);
	}

	public HashMap getAttributes() {
		return hashAttributes;
	}

	public void setAttribute(String strKey, String strValue) {
		this.hashAttributes.put(strKey, strValue);
	}

	public String getText() {
		return this.text.trim();
	}

	public void setText(String text) {
 		this.text = text.replace("\r", "").replace("\n", "").trim();
	}

	public int getCurColumn() {
		return curColumn;
	}

	public void setCurColumn(int curColumn) {
		this.curColumn = curColumn;
	}
}

/**
 * 
 * @author teschke
 */
class WeraProductInfo {
	
	private String pk;
	private String artnr;
	private String code;
	private Product weraproduct;
	private boolean insortiment;

	public WeraProductInfo() {
		
		// --- initialize empty object
		this.pk				= "";
		this.artnr			= "";
		this.code			= "";
		this.weraproduct	= null;
	}

	public WeraProductInfo(String pk, String artnr, String code) {
		this.pk				= pk;
		this.artnr			= artnr;
		this.code			= code;
		this.weraproduct	= weraproduct;
	}

	public WeraProductInfo( Product weraproduct, String code ) {
		this.pk				= weraproduct.getPK().toString();
		this.artnr			= weraproduct.getCode();
		this.code			= code;
		this.weraproduct	= (WeraProduct)weraproduct;
	}

	public WeraProduct getWeraproduct() {
		return (WeraProduct)weraproduct;
	}

	public void setWeraproduct(Product weraproduct) {
		this.weraproduct = weraproduct;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getArtnr() {
		return artnr;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setArtnr(String artnr) {
		this.artnr = artnr;
	}

	public boolean isInsortiment() {
		return insortiment;
	}

	public void setInsortiment(boolean insortiment) {
		this.insortiment = insortiment;
	}
}


/**
 * Indesign xml-node code-tag
 *
 * @author teschke
 */
class InDesignNode {

	private String code;
	private WeraProductInfo weraproductinfo;
	private boolean bFootnote;
	private boolean bValid;
	private boolean bTitle;
	private boolean bDescription;
	private Attributes curAttributes;
	private ArrayList<InDesignText> texts;
	private int curColumn;

	/**
	 * constructor
	 */
	public InDesignNode() {

		// --- first initialize
		this.curColumn = 0;
		this.weraproductinfo = null;
		this.bValid = true;
		this.bFootnote = false;
		this.bTitle = false;
		this.bDescription = false;
		this.texts = new ArrayList<InDesignText>();
	}

	/**
	 * appends a text to our text-list
	 *
	 * @param text
	 */
	public void appendText(String text, Attributes curParamAttributes) {

		// --- new text-elemet
		InDesignText oInDesignText = new InDesignText(text);

		// --- übertrage die attribute in einen hashmap
		if (curParamAttributes != null) {

			for (int nPos = 0; nPos < curParamAttributes.getLength(); nPos++) {
				//System.out.println("++++++++attr-name=" + curParamAttributes.getLocalName(nPos) + " / value=" + curParamAttributes.getValue(nPos));
				oInDesignText.setAttribute(curParamAttributes.getLocalName(nPos), curParamAttributes.getValue(nPos));
			}
		}

		// --- spalte
		this.curColumn++;
		oInDesignText.setCurColumn(curColumn);

		// --- collect text-elements
		this.texts.add(oInDesignText);
	}

	public boolean isbFootnote() {
		return bFootnote;
	}

	public void setbFootnote(boolean bFootnote) {
		this.bFootnote = bFootnote;
	}

	public boolean isbTitle() {
		return bTitle;
	}

	public void setbTitle(boolean bTitle) {
		this.bTitle = bTitle;
	}

	public boolean isbDescription() {
		return bDescription;
	}

	public boolean isbValid() {
		return bValid;
	}

	public void setbValid(boolean bValid) {
		this.bValid = bValid;
	}

	public void setbDescription(boolean bDescription) {
		this.bDescription = bDescription;
	}

	public ArrayList<InDesignText> getTexts() {
		return texts;
	}

	public void setTexts(ArrayList<InDesignText> texts) {
		this.texts = texts;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public WeraProductInfo getWeraproductinfo() {
		return weraproductinfo;
	}

	public void setWeraproductinfo(WeraProductInfo weraproductinfo) {
		this.weraproductinfo = weraproductinfo;
	}

	public int getCurColumn() {
		return curColumn;
	}

	public void setCurColumn(int curColumn) {
		this.curColumn = curColumn;
	}

	@Override
	public String toString() {
		return "[[" + this.code + "]]";
	}
}

/**
 *
 * @author teschke
 */
class InDesignContentHandler implements ContentHandler {

	private final ArrayList<InDesignNode> alleInDesignElemente;
	private final StringBuffer currentValue;
	private String currentNodeName;
	private Attributes curAttributes;
	private InDesignNode oInDesignNode;
	private InDesignFileWriter oInDesignFileWriter;
	private boolean isStoryNode;
	public HashMap<String,String> hashLookupCode2PK;
	public HashMap<String,WeraProductInfo> hashLookupWeraProduct;

	/**
	 * constructor
	 *
	 * @param oInDesignFileWriter
	 */
	public InDesignContentHandler() {

		// --- some initialize
		this.currentValue = new StringBuffer();
		this.currentNodeName = "";
		this.oInDesignNode = null;
		this.oInDesignFileWriter = null;
		this.curAttributes = null;
		this.isStoryNode = false;
		this.alleInDesignElemente = new ArrayList<InDesignNode>();
		this.hashLookupCode2PK		= new HashMap<String,String>();
		this.hashLookupWeraProduct	= new HashMap<String,WeraProductInfo>();
	}

	/**
	 * Aktuelle Zeichen die gelesen werden, werden in eine Zwischenvariable
	 * gespeichert
	 *
	 * @param ch
	 * @param start
	 * @param length
	 * @throws SAXException
	 */
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		// --- filter --------------------------------------------------------------------
		if (this.oInDesignNode == null || this.isStoryNode) {

			// --- current node is unvalid
			return;
		}
		// --- filter --------------------------------------------------------------------

		// --- get the current characters
		//String currentCharacters = new String(ch, start, length);
		//currentCharacters	= currentCharacters.trim();
		//currentCharacters	= currentCharacters.replace("\r", "").replaceAll("\n", "");
		//this.oInDesignFileWriter.write("characters.currentValue=" + currentCharacters + "= (" + currentCharacters.length() + ")\r\n", true);

		// --- get tcurrent string value
		if ( length > 0 ) {

			// --- collect string
			// this.currentValue.append(currentCharactersstart);
			this.currentValue.append( ch, start, length );
			this.oInDesignFileWriter.write("characters.append.currentValue=" + this.currentValue + "=\r\n", true);
			
		} else {
			
			// --- skip empty
			this.oInDesignFileWriter.write("characters.skipped=\r\n", true);
		}
	}

	/**
	 * Methode wird aufgerufen wenn der Parser zu einem Start-Tag kommt
	 *
	 * @param uri
	 * @param localName
	 * @param qName
	 * @param atts
	 * @throws SAXException
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {

		// --- filter --------------------------------------------------------------------
		if (localName.contains("Story") || this.isStoryNode) {

			// --- oberster Node ist eine Story
			this.isStoryNode = true;

			// --- current node is unvalid
			return;
		}
		if (localName.contains("table")) {

			// --- hint
			this.oInDesignFileWriter.write("startElement.start\r\n", true);
			this.oInDesignFileWriter.write("startElement.localName=" + localName + "frame=" + atts.getValue("frame") + "\r\n", true);

			if (atts.getValue("frame").equals("all")) {

				// --- hint
				this.oInDesignFileWriter.write("startElement=unvalid\r\n", true);

				// --- current node als invalid markieren
				this.oInDesignNode.setbValid(false);
				return;
			}
		}
		// --- filter --------------------------------------------------------------------

		// --- preset
		this.isStoryNode = false;

		// --- prüfen ob ein neuer code-tags beginnt
		if (localName.contains("_")) {

			// --- nodename merken
			currentNodeName = localName;
			this.oInDesignFileWriter.write("startElement=" + currentNodeName + "\r\n", true);

			// --- Neue InDesignNode erzeugen
			this.oInDesignNode = new InDesignNode();

			// --- code-nummer merken
			this.oInDesignNode.setCode(currentNodeName.replace("_", ""));

		} else {

			// --- neu row
			if (localName.equals("row")) {

				// --- neue zeile beginnt hier
				this.oInDesignNode.setCurColumn(0);
			}

			// --- unterelement eines code-tags
			this.oInDesignFileWriter.write("startElement (na)=" + localName + " qName=" + qName + "\r\n", true);
			if (localName.equals("table")) {

				// --- description oder footnote
				this.oInDesignNode.setbDescription(true);
			}
		}

		// --- attribute merken falls vorhanden
		if (this.oInDesignNode != null && atts.getLength() > 0) {

			// --- attribute merken
			this.curAttributes = atts;
		}
	}

	/**
	 * Methode wird aufgerufen wenn der Parser zu einem End-Tag kommt
	 *
	 * @param uri
	 * @param localName
	 * @param qName
	 * @throws SAXException
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		// --- hint
		this.oInDesignFileWriter.write("endElement.start\r\n", true);
		this.oInDesignFileWriter.write("endElement.currentNodeName=" + localName + "\r\n", true);

		// --- filter --------------------------------------------------------------------
		if (localName.contains("Story")) {

			// --- Story-Node ist beendet
			this.isStoryNode = false;

			// --- current node is unvalid
			return;
		}
		if (this.oInDesignNode == null) {

			// --- current node is unvalid
			return;
		}
		// --- filter --------------------------------------------------------------------

		// --- ist ein text vorhanden
		if (this.currentValue.toString().trim().length() > 0) {

			this.oInDesignFileWriter.write("endElement.currentValue=" + currentValue + "= (" + this.currentValue.length() + ")\r\n", true);

			// --- text einfügen
			this.oInDesignNode.appendText(this.currentValue.toString(), this.curAttributes);
			this.oInDesignFileWriter.write("InDesignNode.appendText=" + this.currentValue.toString() + "=\r\n", true);

			// --- wenn es nicht description oder fussnote ist dann ist es ein title
			if (!this.oInDesignNode.isbDescription() && !this.oInDesignNode.isbFootnote()) {

				// --- weder beschreibung noch fussnote, dann ist es ein title
				this.oInDesignNode.setbTitle(true);

			} else {

				// --- prüfe auf fussnote
				if (this.currentValue.toString().contains(")") && this.currentValue.length() <= 4) {

					// --- es ist wahrscheinlich eine fussnote
					this.oInDesignNode.setbFootnote(true);
					this.oInDesignNode.setbDescription(false);
				}
			}

			// --- Stringbuffer leeren
			this.currentValue.delete(0, this.currentValue.length());

		} // --- if ( this.currentValue.length() > 0 ) {

		// --- prüfen ob ein neuer code-tags endet
		if (localName.contains("_")) {

			// --- werapoductinfo suchen
			String strPK	= "";
			if ( (strPK=this.hashLookupCode2PK.get(this.oInDesignNode.getCode())) != null ) {
				
				// --- hole weraproductinfo
				WeraProductInfo oWeraProductInfo	= (WeraProductInfo)this.hashLookupWeraProduct.get( strPK );
				oWeraProductInfo.setInsortiment(true);
				
				// --- info zurückschreiben
				this.hashLookupWeraProduct.put(strPK, oWeraProductInfo);
				
				// -- übernehme das Produktinfo
				this.oInDesignNode.setWeraproductinfo(oWeraProductInfo);
			}
			
			// --- in element-liste merken
			alleInDesignElemente.add(this.oInDesignNode);

			// --- element wurde verarbeitet und kann gelöscht werden
			this.oInDesignNode = null;
		}

		this.oInDesignFileWriter.write("endElement.ende\r\n", true);
	}

	public void endDocument() throws SAXException {
	}

	public void endPrefixMapping(String prefix) throws SAXException {
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
	}

	public void setDocumentLocator(Locator locator) {
	}

	public void skippedEntity(String name) throws SAXException {
	}

	public void startDocument() throws SAXException {
	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
	}

	public ArrayList<InDesignNode> getAlleInDesignElemente() {
		return alleInDesignElemente;
	}

	// -------------------------------------------------------------------------------------------
	// --- getter / setter
	// -------------------------------------------------------------------------------------------
	public void setoFileWriter(InDesignFileWriter oFileWriter) {
		this.oInDesignFileWriter = oFileWriter;
	}

}

/**
 *
 * @author teschke
 */
class InDesignFileWriter extends FileWriter {

	/**
	 * constructor
	 *
	 * @param file
	 * @throws IOException
	 */
	public InDesignFileWriter(File file) throws IOException {

		// --- parent constructor
		super(file);
	}

	/**
	 *
	 * @param string
	 * @param i
	 * @param i1
	 * @param bLog2Console
	 */
	public void write(String string, boolean bLog2Console) {

		try {

			// --- write
			super.write(string); //To change body of generated methods, choose Tools | Templates.

			// --- log to console
			if (bLog2Console) {

				InDesignXMLReader.LOG.info(string);
			}

		} catch (IOException e) {

			InDesignXMLReader.LOG.info(e.getMessage());
		}
	}

}

/**
 *
 * @author teschke
 */
public class InDesignXMLReader {

	public static final Logger LOG = Logger.getLogger(InDesignXMLReader.class.getName());

	private InDesignFileWriter oInDesignFileWriter;
	private InDesignContentHandler oIndsignContenHandler;
	private final WeraManager oWeraManager;
	
	public InDesignXMLReader() {

		// --- initialize
		this.oWeraManager			= WeraManager.getInstance();
	}

	/**
	 * alle XML-Dateien eines Verzeichnisses verarbeiten
	 *
	 * @param strScanPath
	 * @param strCatalogCompare
	 * @param strCatalogVersionCompare
	 * @param iMode - 1= Analyse, 2= Rückschreiben nach Hybris, 3= alles
	 * 
	 */
	public void conertAllXmlFiles( final String strScanPath,  final String strCatalogCompare,  final String strCatalogVersionCompare, int iMode ) {

		// --- debug
		LOG.info("conertAllXmlFiles.START");

		// ---------------------------------------------------------------------
		// --- initialize
		// ---------------------------------------------------------------------
		// --- my content handler
		this.oIndsignContenHandler = new InDesignContentHandler();
		
		// --- sortiment einlesen
		this.__collectSortiment(strCatalogCompare, strCatalogVersionCompare);
		

		
		// ---------------------------------------------------------------------
		// --- datei liste erstellen und einlesen
		// ---------------------------------------------------------------------
		File actual = new File(strScanPath );
		for (File f : actual.listFiles()) {

			// --- nur xml-datei verarbeiten
			if (f.isFile() && f.getName().contains(".xml")) {

				try {

					// --- XML-Datei einlesen
					this.readXML( f.getAbsolutePath(), false );

				} catch (IOException ex) {
					Logger.getLogger(InDesignXMLReader.class.getName()).log(Level.SEVERE, null, ex);
				} catch (SAXException ex) {
					Logger.getLogger(InDesignXMLReader.class.getName()).log(Level.SEVERE, null, ex);
				}
			}

		} // --- for( File f : actual.listFiles()){


		// ---------------------------------------------------------------------
		// --- auswertung
		// ---------------------------------------------------------------------
		try {

			switch ( iMode ) {
				default:
				case 1:
					
					// --- erstellen einer report-datei zur kontrolle
					this.__performReport( strScanPath, strCatalogVersionCompare );
					break;
					
				case 2:
					
					// --- Rückschreiben nach Hybris
					this.__performHybrisImport();
					break;
					
				case 3:
					
					// --- erstellen einer report-datei zur kontrolle
					this.__performReport( strScanPath, strCatalogVersionCompare );
					
					// --- Rückschreiben nach Hybris
					this.__performHybrisImport();
					break;
			}

			
		} catch (IOException ex) {
			Logger.getLogger(InDesignXMLReader.class.getName()).log(Level.SEVERE, null, ex);
		}

		// --- debug
		LOG.info("conertAllXmlFiles.ENDE");
	}

	/**
	 * XML-Datei einlesen
	 *
	 * @param strXmlFile
	 * @param bDetailedDump
	 */
	public void readXML( final String strXmlFile, final boolean bDetailedDump) throws IOException, SAXException {

		// --- debug
		LOG.log(Level.INFO, "Bearbeite: {0}", strXmlFile);

		// ---------------------------------------------------------------------
		// --- initialize
		// ---------------------------------------------------------------------
		File oLogFile = new File(strXmlFile.replace(".xml", ".txt"));

		// --- creates the file
		oLogFile.createNewFile();

		// --- creates a FileWriter Object
		this.oInDesignFileWriter = new InDesignFileWriter(oLogFile);

		// --- inject filewriter
		oIndsignContenHandler.setoFileWriter(this.oInDesignFileWriter);

		// ---------------------------------------------------------------------
		// --- xml-datei einlesen
		// ---------------------------------------------------------------------
		// XMLReader erzeugen
		XMLReader xmlReader = XMLReaderFactory.createXMLReader();

		// Pfad zur XML Datei
		FileReader reader = new FileReader(strXmlFile);
		InputSource inputSource = new InputSource(reader);

		// DTD kann optional übergeben werden
		// inputSource.setSystemId("X:\\personen.dtd");
		// PersonenContentHandler wird übergeben
		xmlReader.setContentHandler(oIndsignContenHandler);

		// Parsen wird gestartet
		xmlReader.parse(inputSource);

		// ---------------------------------------------------------------------
		// --- auswertung
		// ---------------------------------------------------------------------
		if (bDetailedDump) {

			oInDesignFileWriter.write("\r\n\r\nErgebniss (" + oIndsignContenHandler.getAlleInDesignElemente().size() + "):\r\n");

			// --- iterate on all elemens
			for (InDesignNode oInDesignElement : oIndsignContenHandler.getAlleInDesignElemente()) {

				// --- code nummer
				oInDesignFileWriter.write("code=" + oInDesignElement.getCode() + "\r\n");

				// --- text-typ
				if (oInDesignElement.isbDescription()) {
					oInDesignFileWriter.write("type=Description" + "\r\n");
				} else if (oInDesignElement.isbTitle()) {
					oInDesignFileWriter.write("type=title" + "\r\n");
				} else if (oInDesignElement.isbFootnote()) {
					oInDesignFileWriter.write("type=footnote" + "\r\n");
				} else {
					oInDesignFileWriter.write("type=unkown" + "\r\n");
				}

				// --- liste der texte
				ArrayList<InDesignText> aTexts = oInDesignElement.getTexts();
				oInDesignFileWriter.write("Anzahl Texte (" + aTexts.size() + "):\r\n");
				for (InDesignText oInDesignText : aTexts) {

					// --- text
					oInDesignFileWriter.write("   Text=" + oInDesignText.getText() + ", col=" + oInDesignText.getCurColumn() + "\r\n");

					// --- liste der attribute
					HashMap<String, String> hashAttributes = oInDesignText.getAttributes();
					if (hashAttributes != null) {

						oInDesignFileWriter.write("Anzahl Attribute (" + hashAttributes.size() + "):\r\n");
						for (Map.Entry<String, String> attribute : hashAttributes.entrySet()) {
							oInDesignFileWriter.write("   Attr=" + attribute.getKey() + "=" + attribute.getValue() + "\r\n");
						}

					} // --- if ( hashAttributes != null ) {

				} // --- for (InDesignText oInDesignText : aTexts) {

				// --- abschlusse
				oInDesignFileWriter.write("---------" + "\r\n");

			} // --- for (InDesignNode oInDesignElement : oIndsignContenHandler.getAlleInDesignElemente())

			// --- Writes the content to the file
			oInDesignFileWriter.flush();
			oInDesignFileWriter.close();

		} // --- if ( bDetailedDump ) {
	}


	/**
	 * Sortiemnt aus referenz-katalog auslesen
	 * 
	 * @param strCatalog
	 * @param strCatalogversion 
	 */
	private void __collectSortiment( final String strCatalog, final String strCatalogversion ) {
		
		// --- katalog-support
		WeraKatalog wk = new WeraKatalog();
		wk.InitCatalogPricelist(strCatalogversion);

		// --- Alle alle Produkte sortiert nach Order
		ArrayList<HashMap> productsSorted = (ArrayList<HashMap>) wk.getProductsFromCatalogVersion (strCatalog, strCatalogversion, 1, true );
		for ( HashMap hashmapValues : productsSorted ) {

			// --- hint
			// LOG.info("+++");

			// --- Hole Produkt
			Product oWeraProduct = (WeraProduct) hashmapValues.get("product");

			// --- weratyp auswerten
			if ( oWeraProduct instanceof WeraProductSetinSet ) {
				
				// --- Wera Produts Set in Set -------------------------------------------------------------

				// --- Satz in Satz (Liste der Sätze
				// --- Hole alle akitven Varianten des Products
				Collection<WeraProductSetVariants> colWeraProductSetVariants = (Collection<WeraProductSetVariants>)oWeraManager.getAttribute(oWeraProduct, "weraproductsetvariants_qual");

				// --- Schleife über alle Artikel
				for ( WeraProductSetVariants oWeraProductSetVariants : colWeraProductSetVariants ) {

					// --- Hole den aktuellen Artikel
					WeraProductSet oWeraProductset = (WeraProductSet) oWeraManager.getAttribute(oWeraProductSetVariants, "weraproductsets");

					// --- hint
					// LOG.info("ArtNr # " + oWeraProductset.getCode() );
				
					// --- code-nummer ermitteln
					String strCodeNr	= this.__getCodeNr ( oWeraProductset );

					// --- collect current product
					oIndsignContenHandler.hashLookupWeraProduct.put( oWeraProduct.getPK().toString(), 
							new WeraProductInfo( oWeraProduct, strCodeNr )
					);

					// --- collect - codenr
					oIndsignContenHandler.hashLookupCode2PK.put( strCodeNr, oWeraProduct.getPK().toString() );
					// LOG.info("WeraProductSetinSet.CodeNr # " + strCodeNr);

				}
				
					
			} else if ( oWeraProduct instanceof WeraProductSet ) {
				
				// --- Wera Produts Set ---------------------------------------------------------------------

				// --- hint
				// LOG.info("ArtNr # " + oWeraProduct.getCode());
				
				// --- code-nummer ermitteln
				String strCodeNr	= this.__getCodeNr ( oWeraProduct );
			
				// --- collect current product
				oIndsignContenHandler.hashLookupWeraProduct.put( oWeraProduct.getPK().toString(), 
						new WeraProductInfo( oWeraProduct, strCodeNr )
				);

				// --- collect - codenr
				oIndsignContenHandler.hashLookupCode2PK.put( strCodeNr, oWeraProduct.getPK().toString() );
				// LOG.info("WeraProductSet.CodeNr # " + strCodeNr);
				
				
			} else { // instanceof WeraProduct
				
				// --- Wera Produts -------------------------------------------------------------------------

				// --- hint
				// LOG.info("ArtNr # " + oWeraProduct.getCode());
				
				// --- Hole alle akitven Varianten des Products
				Collection<WeraVariante> colWeraVariantes = (Collection<WeraVariante>) ((WeraProduct) oWeraProduct).getVarianten();
				
				// --- schleife über alle artikel
				for ( WeraVariante oWeraVariante : colWeraVariantes ) {
		
					// --- code-nummer ermitteln
					String strCodeNr	= this.__getCodeNr ( oWeraVariante );
					
					// --- collect current product
					oIndsignContenHandler.hashLookupWeraProduct.put( oWeraProduct.getPK().toString(), 
							new WeraProductInfo( oWeraProduct, strCodeNr )
					);
					
					// --- collect - codenr
					oIndsignContenHandler.hashLookupCode2PK.put( strCodeNr, oWeraProduct.getPK().toString() );
					// LOG.info("WeraProduct.CodeNr # " + strCodeNr);
					
				} // --- for ( WeraVariante oWeraVariante : colWeraVariantes ) {
				
			}
			
			
		} // --- for ( HashMap hashmapValues : productsSorted ) {
		
	}

	
	/**
	 * ermitteln der codenummer bei verschiedenen Producttypen
	 * 
	 * @param oWeraProduct
	 * @return 
	 */
	private String __getCodeNr ( Product oWeraProduct ) {
	
		// --- initialize
		String strCodeNr	= "";
	
		// --- code-nummer ermitteln
		String strLagerNr	= (String)oWeraManager.getAttribute(oWeraProduct,"lagerNr" );
		if ( strLagerNr == null || strLagerNr.equals("")) {
			strLagerNr	= "05";
		}
		String strVariantenNr	= (String)oWeraManager.getAttribute(oWeraProduct,"variantenNr" );
		if ( strVariantenNr == null || strVariantenNr.equals("")) {
			strVariantenNr	= "001";
		}
		
		// --- code ertmiiteln
		if ( oWeraProduct instanceof WeraProductSet ) {
		
			// --- codenr aus feld artnr ermitteln
			strCodeNr	= (String)oWeraManager.getAttribute(oWeraProduct,"artnr" );
		
		} else {
			
			// --- codenr aus feld code ermitteln
			strCodeNr	= (String)oWeraManager.getAttribute(oWeraProduct,"code" );
		}
		if ( strCodeNr == null || strCodeNr.equals("")) {
			strCodeNr	= "??????";
		}

		
		return strLagerNr + strCodeNr + strVariantenNr;
	}
	
	/**
	 * Daten nach Hybris importieren
	 * 
	 */
	private void __performHybrisImport() {
		

		// ----------------------------------------------------------------------------------------------------------
		// Liste der indesign katalog-texte
		// ----------------------------------------------------------------------------------------------------------
		
		// --- initialize
		
		// --- sprache (CN) setzen
		this.oWeraManager.SetLanguage( "cn" );

		// --- iterate on all elemens
		for (InDesignNode oIndesignNode : oIndsignContenHandler.getAlleInDesignElemente()) {

			// --- prüfung und filtern ----------------------------------------------------
			// --- skip invalid text-node
			if (!oIndesignNode.isbValid()) {
				continue;
			}
			
			// --- prüfe weraproducinfo 
			WeraProductInfo oWeraProductInfo = oIndesignNode.getWeraproductinfo();
			
			// --- wenn keine produkt verknüpfung gefunden wurde ignorieren wir den artikel erstmal
			if ( oWeraProductInfo == null ) {
				/**
				 * @todo: suche in katalog weramaster
				 */
				continue;
			}

			
			
			// --- text ermitteln -------------------------------------------------------
			// --- filter
			// --- holen der textListe
			ArrayList<InDesignText> aTexts = oIndesignNode.getTexts();
			if ( aTexts == null || aTexts.isEmpty() ) {
				// --- kein Text vorhanden
				continue;
			}

				
			// --- holen des ziel object
			WeraProduct oWeraProduct	= oWeraProductInfo.getWeraproduct();
			LOG.info("---");
			LOG.info("code="+oWeraProduct.getCode());
		
			// --- preset
			String strCurrentText	=	"";
			
			// --- text ermitteln
			if (oIndesignNode.isbDescription()) { 
			
				// --- ermitteln des Beschreibungstexts
				strCurrentText = this.__getDescription(aTexts);
						
			} else {

				// --- text -  title oder footnote
				strCurrentText = aTexts.get(0).getText();

			}

			
			
			// --- daten nach hybris zurückschreiben -------------------------------------
			// --- filter
			if ( strCurrentText.isEmpty() ) {
				// --- kein Text vorhanden
				continue;
			}
				
			// --- text-typ berücksichtigen und Daten nach Hybris zurückschreiben
			if (oIndesignNode.isbDescription()) {
				
				// --- text ist ein beschreibungs-feld, setzen
				oWeraProduct.setDescription1(strCurrentText);

				
			} else if (oIndesignNode.isbTitle()) {

				// --- titel setzen
				oWeraProduct.setName(strCurrentText);

				
			} else if (oIndesignNode.isbFootnote()) {
				
				// --- text ist eine fussnote
				/**
				 * @todo umsetzen
				 */
				
			}



		} // --- for (InDesignNode oIndesignNode : oIndsignContenHandler.getAlleInDesignElemente()) {

		
		// --- sprache (DE) setzen
		this.oWeraManager.SetLanguage( "de" );
	}


	/**
	 * ermtteln der Beschreibung aus der Textliste
	 * zusammenbau gemäss Hybris Konvention mit HTML-Tags
	 * Beispiel:
	 * <b>Anwendung:</b> Karosseriebau, KFZ-Bereich, Maschinenbau<br />
	 * <b>Köpfe:</b> Uretan, mittelhart<br />
	 * <b>Stiel:</b> Esche
	 * 
	 * @param aTexts 
	 * @return strResultText
	 */
	private String __getDescription(ArrayList<InDesignText> aTexts ) {
		
		byte ptext_b_start[] = "<b>".getBytes(StandardCharsets.ISO_8859_1); 
		String strB_Start = new String(ptext_b_start, StandardCharsets.UTF_8); 
		byte ptext_b_end[] = "</b>".getBytes(StandardCharsets.ISO_8859_1); 
		String strB_End = new String(ptext_b_end, StandardCharsets.UTF_8); 
		byte ptext_b_br[] = "<br/>".getBytes(StandardCharsets.ISO_8859_1); 
		String strBR = new String(ptext_b_br, StandardCharsets.UTF_8); 

		// --- inialtize
		ArrayList<String> aTmpTextListe	= new ArrayList<String>();
		String strResultText			= "";
		InDesignText oInDesignText		= null;
		int nCurColumn					= 1;
		
		// --- interate on text-list
		for ( int nTextElement=0; nTextElement < aTexts.size(); nTextElement++ ) {
			
			// --- hole das aktuelle Text-Element
			oInDesignText	= aTexts.get(nTextElement);

			// --- leere Elmente übrspringen
			// LOG.info( "skip.__getDescription.oInDesignText.getText() C=" + oInDesignText.getCurColumn()  + ", L=" + oInDesignText.getText().length());
			if ( oInDesignText.isEmpty() ) {
				// --- ersten description-text mit einer einrückung ausgeben
				continue;
			}

			
			// --- aktuelle Spalte holen
			nCurColumn	= oInDesignText.getCurColumn();

			// --- Wenn das Element aus der ersten Spalte ist prüfe ob es eine 2. Spalte gibt
			if ( nCurColumn == 1 ) {

				// --- prüfe die Spalte des nächste Elements
				if ( (nTextElement+1) == aTexts.size() || aTexts.get(nTextElement+1).getCurColumn() == 1 ) {

					// --- es gibt keine 1. Spalte, daher das aktuelle element über die gesamte breite plazierens
					nCurColumn	= 2;
				}

			}

			// --- erster description-text muss anders behandelt werden
			if ( nCurColumn == 1 ) {

				// --- ersten description-text mit einer einrückung ausgeben
				strResultText += strB_Start + oInDesignText.getText() + strB_End;

			} else {

				// --- ersten description-text mit einer einrückung ausgeben
				strResultText += oInDesignText.getText() + strBR;
			}

		} // --- for ( int nTextElement=0; nTextElement < aTexts.size(); nTextElement++ ) {

			
		return strResultText;
	}

	/**
	 * Analyse-Textliste erzeugen
	 * 
	 * @param strExportPath
	 * @param strCatalogVersionCompare
	 * @throws IOException 
	 */
	private void __performReport( final String strExportPath, final String strCatalogVersionCompare ) throws IOException {
		
	
		// ----------------------------------------------------------------------------------------------------------
		// Liste der indesign katalog-texte
		// ----------------------------------------------------------------------------------------------------------
		
		// --- initialize
		File oResultFile = new File(strExportPath + "indesign_texte.txt");
		FileWriter oLocalFileWriter = new FileWriter(oResultFile);

		// --- header schreibem
		oLocalFileWriter.write(
			"pk\tartnr\tkatalog_vergleich\tsortiment\tcode\ttype\ttitle_oder_fussnote\tspalte 1\tspalte 2\tspalte 1\tspalte 2\tspalte 1\tspalte 2"
					+ "\tspalte 1\tspalte 2\tspalte 1\tspalte 2\tspalte 1\tspalte 2\r\n"
		);
		
		// --- iterate on all elemens
		for ( String strPK : this.oIndsignContenHandler.hashLookupWeraProduct.keySet() ) {

			// --- hole WeraProductInfo-Object
			WeraProductInfo oWeraProductInfo = this.oIndsignContenHandler.hashLookupWeraProduct.get(strPK);
			
			// --- skip invalid text-node
			if ( !oWeraProductInfo.isInsortiment() ) {

				// --- eintrag ist nicht im Sortiment
				oLocalFileWriter.write(oWeraProductInfo.getPk() + "\t" + oWeraProductInfo.getArtnr() + "\t" 
						+ strCatalogVersionCompare 
						+ "\tnicht im Sortiment\t" + oWeraProductInfo.getCode() + "\r\n");
			}
			
		} // --- for (WeraProductInfo oWeraProductInfo : oIndsignContenHandler.hashLookupWeraProduct() ) {


		
		// ----------------------------------------------------------------------------------------------------------
		// Liste der indesign katalog-texte
		// ----------------------------------------------------------------------------------------------------------
		
		// --- initialize

		// --- iterate on all elemens
		for (InDesignNode oIndesignNode : oIndsignContenHandler.getAlleInDesignElemente()) {

			// --- skip invalid text-node
			if (!oIndesignNode.isbValid()) {
				continue;
			}

			// --- code nummer
			String strLine = oIndesignNode.getCode() + "\t";

			// --- text-typ
			if (oIndesignNode.isbDescription()) {
				strLine += "description" + "\t";
			} else if (oIndesignNode.isbTitle()) {
				strLine += "title" + "\t";
			} else if (oIndesignNode.isbFootnote()) {
				strLine += "footnote" + "\t";
			} else {
				strLine += "unkown" + "\t";
			}

			// --- liste der texte
			ArrayList<InDesignText> aTexts = oIndesignNode.getTexts();
			boolean isFirstDescription = true;
			for (InDesignText oInDesignText : aTexts) {

				// --- erster description-text muss anders behandelt werden
				if (oIndesignNode.isbDescription()) {

					if (isFirstDescription) {
						// --- ersten description-text mit einer einrückung ausgeben
						strLine += "\t";

						// --- keine weiteren einrückungen
						isFirstDescription = false;
					}

					// --- ersten description-text mit einer einrückung ausgeben
					strLine += oInDesignText.getText();

					if (oInDesignText.getText().length() > 0) {
						// --- ersten description-text mit einer einrückung ausgeben
						strLine += "\t";
					}

				} else {

					// --- text -  title oder footnote
					strLine += oInDesignText.getText() + "\t";

				}

			} // --- for (InDesignText oInDesignText : aTexts) {

			// --- prüfe weraproducinfo 
			WeraProductInfo oWeraProductInfo = oIndesignNode.getWeraproductinfo();
			
			// --- abschluss
			if ( oWeraProductInfo != null ) {
				
				// --- hybris artikel wurde gefunden
				oLocalFileWriter.write( oWeraProductInfo.getPk() + "\t" + oWeraProductInfo.getArtnr() 
											+ "\t" + strCatalogVersionCompare + "\tim Sortiment\t" + strLine + "\r\n");
				
			} else {
				
				// --- hybris artikel wurde gefunden
				oLocalFileWriter.write( "\t\t" + strCatalogVersionCompare + "\tanderer Katalog\t" + strLine + "\r\n");
			}

		} // --- for (InDesignNode oIndesignNode : oIndsignContenHandler.getAlleInDesignElemente()) {

		
		// --- Writes the content to the file
		oLocalFileWriter.flush();
		oLocalFileWriter.close();
	
	}

}	