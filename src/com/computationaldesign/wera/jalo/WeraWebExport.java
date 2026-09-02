package com.computationaldesign.wera.jalo;

import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttribute;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.c2l.Country;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.util.Config;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


//Wrapperklassen f�r formatierte Ausgabe der Bildpfade
//BluestoreExportFormatter formatiert Bildpfade komplett mit TIF bzw. GIF Pfaden.
//Der Exporter muss �ber setExportFormatter ( "WeraWebExportFormatter" )
//vom Bluestore aus gesetzt werden.
class WeraWebExportFormatter extends ExportFormatter
{

	/** Used logger instance. */
	private static final Log LOG = LogFactory.getLog(WeraWebExport.class);

	@Override
	public String formatIconPath(final String s)
	{

		// --- Merken
		String strTmp = s;

		if (strTmp.startsWith("merkmale/"))
		{
			strTmp = strTmp.replace("merkmale/", "");
		}
		if (strTmp.startsWith("icons/"))
		{
			strTmp = strTmp.replace("icons/", "");
		}
		if (strTmp.startsWith("features/"))
		{
			strTmp = strTmp.replace("features/", "");
		}

		strTmp = strTmp.replace("pictures/", "").replace(".eps", ".jpg");

		if (strTmp.equals(""))
		{
			strTmp = "trans.gif";
		}

		return strTmp;
	}

	@Override
	public String formatPicturePath(final String s)
	{
		// --- Merken
		String strTmp = s;

		strTmp = strTmp.replace("pictures/", "").replace(".eps", ".jpg");

		if (strTmp.equals(""))
		{
			strTmp = "trans.gif";
		}

		return strTmp;
	}

	@Override
	public String formatIndesignSetSupS()
	{
		//return formatSetSupS();
		return "";
	}

	@Override
	public String formatIndesignSetSupE()
	{
		//return formatSetSupE();
		return "";
	}

	@Override
	public String formatSetSupS()
	{
		return "<sup>";
	}

	@Override
	public String formatSetSupE()
	{
		return "</sup>";
	}

	@Override
	public String formatSetSubS()
	{
		return "<sub>";
	}

	@Override
	public String formatSetSubE()
	{
		return "</sub>";
	}

	@Override
	public String formatSetContent(String s)
	{
		/*
		 * <cNoBreak:1> ;<cNoBreak:> <cNoBreak:> <ASCII-WIN>\r\n<Version:4><ParaStyle:links ohne
		 * Einzug><CharStyle:wera\\_tabelle> <CharStyle:> ##b## ##b/##
		 */

		if (s == null)
		{
			s = "";
		}
		s = s.replaceAll("##down##", "<sub>").replaceAll("##e_down##", "</sub>");
		s = s.replaceAll("##sub##", "<sub>").replaceAll("##e_sub##", "</sub>");
		s = s.replaceAll("##sup##", "<sup>").replaceAll("##e_sup##", "</sup>");

		return s.replaceAll("##b/##", "").replaceAll("##b##", "").replaceAll("\r\n", "")
				.replace("<cNoBreak:1>", "<span class=\"atom\">").replace("<cNoBreak:>", "</span>").replace("<ASCII-WIN>", "")
				.replace("<Version:4><ParaStyle:links ohne Einzug><CharStyle:wera\\_tabelle>", "").replace("<CharStyle:>", "")
				.replaceAll("<cPosition:Superscript>", "").replaceAll("<cPosition:>", "");
	}

	@Override
	public String formatCharakters(String s)
	{

		if (s == null)
		{
			s = "";
		}
		s = s.replaceAll("\u00AE", formatSetSupS() + "\u00AE" + formatSetSupE());

		return s;
	}

	@Override
	public String formatDescription(String s)
	{

		s = formatCharakters(s);
		if (s == null)
		{
			s = "";
		}
		s = s.replaceAll("##down##", "<sub>").replaceAll("##e_down##", "</sub>");
		s = s.replaceAll("##sub##", "<sub>").replaceAll("##e_sub##", "</sub>");
		s = s.replaceAll("##sup##", "<sup>").replaceAll("##e_sup##", "</sup>");

		return s.replaceAll("##b/##", "").replaceAll("##b##", "").replaceAll("##[^#]+##", "");
	}

	@Override
	public String formatFileDatum(final String strLang, final String strDatum)
	{
		LOG.info("WeraWebExportFormatter.formatFileDatum=>" + "website/" + strDatum);


		return "website/" + strDatum;
	}

	@Override
	public String formatXmlFilePrefix(final String s)
	{
		return "";
	}

	@Override
	public String formatCode(final String strLagerNr, final String strCode, final String strVarNr)
	{
		return strLagerNr + "<b>" + strCode + "</b>" + strVarNr;
	}

	@Override
	public String formatCodeUS(final String strLagerNr, final String strCode, final String strVarNr)
	{

		return strLagerNr + "<b>" + strCode + "</b>" + strVarNr;
	}
}


public class WeraWebExport extends WeraProduktExport
{

	/** Used logger instance. */
	private static final Log LOG = LogFactory.getLog(WeraWebExport.class);

	ArrayList m_aLogFile = new ArrayList();
	String m_strFileDatum = "";
	String m_strControlPath = "";
	String m_strControlOSPath;
	String m_strOutputPath = "";
	String m_strImgPath = "";
	String m_strDomain = "";
	String m_strCatalog_Postfix = "";
	public boolean m_bLoadExcludeList = false;

	public MediaCollector m_mediaCollector = null;

	public String m_strDataPath = "";
	private Object strLanguage;

	public WeraWebExport()
	{
		super();
		// TODO Auto-generated constructor stub
		setExportFormatter();

		// --- Outputdatum initialisieren
		m_strFileDatum = m_wm.InitOutputDatum();

		// --- Media Collector anlegen
		m_mediaCollector = new MediaCollector();

	}

	@Override
	protected Element _createTextElement(String strTypeName, String strTagContent, final Integer iOrder, final Integer iID,
			final String strLinkType, final boolean bConvertTag, String strName, int contentCounter, String strVPE, boolean firstItemOfContent)
	{
		//ystem.out.println ("WeraWebExport::_createTextElement() called");
		// --- Content bereinigen
		strTypeName = strTypeName.replaceAll("<p>", "");
		strTypeName = strTypeName.replaceAll("</p>", "");
		strTypeName = strTypeName.replaceAll("<b>", "");
		strTypeName = strTypeName.replaceAll("</b>", "");
		strTagContent = strTagContent.replaceAll("&quot;", "\"");


		// --- Element aufbauen
		final Element textXML = new Element("Text");
		textXML.setAttribute("Id", iID.toString());
		textXML.setAttribute("Type", strLinkType);
		textXML.setAttribute("LinkType", strLinkType);
		textXML.setAttribute("TypeName", strTypeName);
		textXML.setAttribute("Sequence", iOrder.toString());
		//textXML.setAttribute("LinkTypeName", strTypeName) ;

		// --- <TextBlock Language="German">In stabiler Klapptasche mit Klettverschluss zur praktischen Aufbewahrung.</TextBlock>
		final Element inhaltXML = new Element("TextBlock");
		inhaltXML.setAttribute("Language", m_strLanguage);
		//CDATA cdataText = new CDATA(strTagContent);
		strTagContent = this.getExportFormatter().formatDescription(strTagContent);
		inhaltXML.addContent(strTagContent);
		textXML.addContent(inhaltXML);
		return textXML;
	}

	@Override
	public String doPrevJob()
	{

		// --- lade ExcludeListe (Produktliste) f�r die aktuelle Sprache
		if (m_bLoadExcludeList)
		{
			final String strFileName = Config.getParameter("wera.homepath") + "/export/website/control/web_" + m_strLanguage
					+ "_exclude.txt";
			LoadExcludeList(strFileName);
		}

		// --- Alle Produkte exportieren
		m_wm.m_bCheckForActivation = false;

		return "";
	}


	/**
	 * Version f�r Webausgabe, keine �nderung der Datenstruktur
	 * 
	 * @param iCntIcon
	 * @param iOrder
	 * @param oIcon1
	 * @param oTexList
	 * @return Element
	 */
	@Override
	protected Element _assign2TextList(final int iCntIcon, final int iOrder, final Element oIconLinks, final Element textXML,
			final Element oTexList)
	{

		// --- 1. - 2. icon
		textXML.addContent(oIconLinks);

		return oTexList;
	}

	@Override
	protected Element createBildElement(final String strTagName, final WeraMedia weramedia, final String strPrio,
			final String strDirectory, final String strCodeNrProduct)
	{
		// TODO Auto-generated method stub

		//m_weraProductAkt
		// --- Initialize
		String strLine = m_weraProductAkt.getCode() + "\t";
		strLine += strTagName + "\t\t";


		// --- ICON
		if (strTagName.equals("ICON1") || strTagName.equals("ICON2"))
		{
			if (weramedia != null)
			{
				m_mediaCollector.addMedia(weramedia.getPK().toString(), weramedia.getRealFileName(), weramedia.getFileName(),
						"products/icons");
				strLine += "OK\t";
				strLine += weramedia.getRealFileName() + "\t";
			}
			else
			{
				strLine += "ERROR\t\t";
			}
		}

		if (strTagName.equals("FEATRUREICONS1") || strTagName.equals("FEATRUREICONS2") || strTagName.equals("FEATRUREICONS3")
				|| strTagName.equals("FEATRUREICONS4") || strTagName.equals("FEATRUREICONS5"))
		{
			if (weramedia != null)
			{
				m_mediaCollector.addMedia(weramedia.getPK().toString(), weramedia.getRealFileName(), weramedia.getFileName(),
						"products/features");
				strLine += "OK\t";
				strLine += weramedia.getRealFileName() + "\t";
			}
			else
			{
				strLine += "ERROR\t\t";
			}
		}

		/* TODO get weramedia (prouct) and register it */
		if (strTagName.equals("PICTURE1"))
		{

			// --- EPS
			if (weramedia != null)
			{
				strLine += "OK\t";
			}
			else
			{
				strLine += "FEHLT\t";
			}

			// --- Initialize
			WeraMedia weramediaTmp = null;
			final String cProductNr = WeraProduct.s_normalizeFilenameForImageLookup(m_weraProductAkt.getCode());

			Collection medias = m_weraProductAkt.getPictures2();
			if (medias != null && medias.size() > 0)
			{
				weramediaTmp = (WeraMedia) medias.iterator().next();
				m_mediaCollector.addMedia(weramediaTmp.getPK().toString(), cProductNr + ".jpg", weramediaTmp.getFileName(),
						"products/100");
				strLine += "OK\t";
			}
			else
			{
				strLine += "FEHLT\t";
			}

			medias = m_weraProductAkt.getPictures3();
			if (medias != null && medias.size() > 0)
			{
				weramediaTmp = (WeraMedia) medias.iterator().next();
				m_mediaCollector.addMedia(weramediaTmp.getPK().toString(), cProductNr + ".jpg", weramediaTmp.getFileName(),
						"products/260");
				strLine += "OK\t";
			}
			else
			{
				strLine += "FEHLT\t";
			}

			medias = m_weraProductAkt.getFeatureicons1();
			if (medias != null && medias.size() > 0)
			{
				weramediaTmp = (WeraMedia) medias.iterator().next();
				m_mediaCollector.addMedia(weramediaTmp.getPK().toString(), cProductNr + ".jpg", weramediaTmp.getFileName(),
						"products/380");

				// --- Produkt / Kategory Index aufbauen
				m_mediaCollector.addMediaInfoByCategory(cProductNr, m_oCategoryWera.getCode());

				strLine += "OK\t";
			}
			else
			{
				strLine += "FEHLT\t";
			}

		}


		// --- LOG f�r Bilder
		m_aLogFile.add(strLine);

		return super.createBildElement(strTagName, weramedia, strPrio, strDirectory, strCodeNrProduct);
	}

	@Override
	public String doPostJob(final Collection colXMLFileList)
	{

		String strXmlFile = "";

		// --- FileListe - Real Rootcontent "WeraWebSite"
		final Element root = new Element("WeraWebsite");

		// --- Schleife �ber alle Dateien
		for (final Iterator it1 = colXMLFileList.iterator(); it1.hasNext();)
		{
			strXmlFile = (String) it1.next();

			LOG.info("Datei=" + strXmlFile);
			final Element oNode = new Element("File").addContent(strXmlFile);
			oNode.setAttribute("type", "mediando");
			root.addContent(oNode);
		}

		// --- Document erzeugen und schreiben
		final Document doc = new Document(root);


		// --- Formatierung
		final XMLOutputter outp = new XMLOutputter(Format.getPrettyFormat());
		//outp.setIndent("  ");
		//outp.setNewlines(true);
		FileOutputStream out = null;
		try
		{
			out = new FileOutputStream(m_strControlPath + m_strLanguage + "_filelist.xml", false);
		}
		catch (final FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			outp.output(doc, out);
		}
		catch (final IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// --- Logfile schreiben
		MediandoXml oSupport = new MediandoXml();
		oSupport._WriteFileFromArray(m_aLogFile, m_strControlPath + m_strLanguage + "_export.txt");
		oSupport = null;

		// --- Aufr�umen
		colXMLFileList.clear();
		m_aLogFile.clear();

		return "";
	}

	// --- Umkopieren der Mediendaten
	public void archiveMedias()
	{

		// --- Mediadaten archivieren
		m_mediaCollector.archiveMedias();

		// --- Bildarchive / Kategory Index herstellen
		m_mediaCollector.generateMediaListByCategory(m_strImgPath + "/products/index");
	}

	// --- Ausf�hren der Shell-Scripte
	public void StartBatch()
	{

		final XmlSupport oXmlSupport = new XmlSupport();
		oXmlSupport.startCmdFile(m_strControlOSPath + "webgenerate.cmd");
	}

	// --- Erzeugen der Shell-Scripte
	public String GenerateBatch(final String strPath, final Collection colLanguages, final String strVersion)
	{

		// --- Tempor�r
		//m_strControlPath = "c:/home/hybris/export/website/20080425_151831/control/";
		//m_strDataPath = "c:/home/hybris/export/website/20080425_151831/data/";
		m_strOutputPath = "x:/website/hybris_ausgabe_webseite/entwicklung/templates/test.co-de.de/";

		// --- Variablen Initialisieren
		String strOutpathHtml = "";
		String strCRLF = "";
		final ArrayList aBatchFile = new ArrayList();
		String strLanguage = "";
		String strLine = "";
		final XmlSupport oXmlSupport = new XmlSupport();

		LOG.info("GenerateBatch(): Using template version suffix >" + strVersion + "<");

		// --- Templatespfade f�r Windows / Linux initialisieren
		if (Config.getParameter("wera.os").equals("linux"))
		{
			strCRLF = "\n";
			m_strControlOSPath = Config.getParameter("wera.homepath") + "/export/website/control/templates-linux" + strVersion + "/";
		}
		else
		{
			strCRLF = "\r\n";
			m_strControlOSPath = Config.getParameter("wera.homepath") + "/export/website/control/templates-windows" + strVersion
					+ "/";
		}
		// --- Scripttemplate Datei �ffnen und einlesen
		BufferedReader in;
		try
		{
			in = new BufferedReader(new InputStreamReader(new FileInputStream(m_strControlOSPath + "batchfile.txt")));

			String strCode = "";
			try
			{
				while ((strCode = in.readLine()) != null)
				{
					aBatchFile.add(strCode);
					LOG.info("strCode=" + strCode);
				}
				in.close();


				// --- Hauptdatei zum generieren aller Scripte
				final FileWriter hScript = new FileWriter(m_strControlOSPath + "webgenerate.cmd");
				if (Config.getParameter("wera.os").equals("linux"))
				{
					hScript.write("#!/bin/sh" + strCRLF);
					hScript.write("#" + strCRLF);
					hScript.write("" + strCRLF);
				}

				// --- Schleife �ber alle Ausgabesprachen
				for (final Iterator itLang = colLanguages.iterator(); itLang.hasNext();)
				{
					strLanguage = (String) itLang.next();

					// --- Languagescript registrieren
					hScript.write(m_strControlOSPath + strLanguage + "_webgenerate.cmd" + strCRLF);

					// --- Erzeugen der Batch-Scripts
					final FileWriter hScriptLang = new FileWriter(m_strControlOSPath + strLanguage + "_webgenerate.cmd");
					if (hScriptLang != null)
					{

						// --- Schleife �ber Scripttemplate
						for (final Iterator itBatch = aBatchFile.iterator(); itBatch.hasNext();)
						{
							strLine = (String) itBatch.next();

							// --- Platzhalter ersetzen
							strLine = strBatchReplacement(strLine, strLanguage);
							if (strLine.contains("OUPUT_PATH="))
							{
								strOutpathHtml = strLine.replaceAll("OUPUT_PATH=", "");
							}

							// --- Zeile schreiben
							hScriptLang.write(strLine + strCRLF);
						}

						// --- Datei schliessen und ggf. als ausf�hrbar freigeben
						hScriptLang.close();
						if (Config.getParameter("wera.os").equals("linux"))
						{
							oXmlSupport.startCmdFile("dos2unix " + m_strControlOSPath + strLanguage + "_webgenerate.cmd");
							oXmlSupport.startCmdFile("chmod a+x " + m_strControlOSPath + strLanguage + "_webgenerate.cmd");
						}
					}
				}

				// --- Datei schliessen und ggf. als ausf�hrbar freigeben
				hScript.close();
				if (Config.getParameter("wera.os").equals("linux"))
				{
					oXmlSupport.startCmdFile("dos2unix " + m_strControlOSPath + "webgenerate.cmd");
					oXmlSupport.startCmdFile("chmod a+x " + m_strControlOSPath + "webgenerate.cmd");
				}

			}
			catch (final IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
		catch (final FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// --- Aufr�umen
		aBatchFile.clear();

		return strOutpathHtml;
	}

	public static String join(final String[] pieces)
	{

		return join(pieces, " ");

	}

	public static String join(final String[] pieces, final char sep)
	{

		return join(pieces, String.valueOf(sep));

	}

	public static String join(final String[] pieces, final String sep)
	{

		if (pieces.length == 0)
		{
			return "";
		}



		final StringBuffer buf = new StringBuffer();

		buf.append(pieces[0]);

		for (int i = 1, n = pieces.length; i < n; i++)
		{
			buf.append(sep).append(pieces[i]);
		}

		return buf.toString();

	}

	private String strBatchReplacement(String strLine, final String strLanguage)
	{
		// TODO Auto-generated method stub

		String strControlPath = m_strControlPath;
		String strDataPath = m_strDataPath;

		if (Config.getParameter("wera.os").equals("windows"))
		{
			strControlPath = strControlPath.replaceAll("/", "\\\\");
			strDataPath = strDataPath.replaceAll("/", "\\\\");
		}

		strLine = strLine.replaceAll("##param_language##", strLanguage);

		if (strLanguage.equals("us-en"))
		{
			strLine = strLine.replaceAll("##param_languagedir##", "us/");
		}
		else
		{
			strLine = strLine.replaceAll("##param_languagedir##", strLanguage + "/");
		}

		strLine = strLine.replace("##param_outputpath##", m_strOutputPath);
		strLine = strLine.replace("##param_path_xmlfiles##", strDataPath);
		strLine = strLine.replace("##param_path_controllfiles##", strControlPath);
		strLine = strLine.replace("##param_domain##", m_strDomain);
		strLine = strLine.replace("##param_imagedir##", m_strImgPath);
		strLine = strLine.replace("##param_catalog_postfix##", m_strCatalog_Postfix);

		return strLine;
	}

	@Override
	public void setExportFormatter()
	{
		// TODO Auto-generated method stub
		this.m_oExportFormatter = new WeraWebExportFormatter();
	}


	/**
	 * check Auslaufartikel
	 * 
	 * @param oWeraProduct
	 * @return boolean
	 */
	private boolean __isAuslaufArticle(final Product oProduct)
	{

		// --- inialize
		Boolean bProduktAuslauf = new Boolean(false);

		// --- validate Param
		if (oProduct != null)
		{

			// --- Auslaufartikel filtern
			bProduktAuslauf = (Boolean) m_wm.getAttribute(oProduct, "artikel_auslauf");
			if (bProduktAuslauf == null)
			{
				bProduktAuslauf = new Boolean(false);
			}

		} // --- if ( oProduct != null ) {

		return bProduktAuslauf.booleanValue();
	}

	/**
	 * dummy filter products
	 * 
	 * @param WeraProduct
	 * @return boolean
	 */
	@Override
	protected boolean _isFilteredProduct(final WeraProduct oWeraProduct)
	{

		// --- inialize
		boolean bProduktAuslauf = false;

		if (oWeraProduct instanceof WeraProductSet)
		{

			// --- Verkaufsartikel
			bProduktAuslauf = __isAuslaufArticle(oWeraProduct);

		}
		else
		{

			// --- pr�fe, ob das gesamte Produkt als Auslaufartikel gekennzeichnet wurde
			bProduktAuslauf = __isAuslaufArticle(oWeraProduct);
			if (!bProduktAuslauf)
			{

				// --- pr�fe, ob alle Variante des Produkts als Auslauf gekenntzeichnet sind
				try
				{

					// --- pr�fe alle Varianten
					final Collection articles = m_wm.getVariants(oWeraProduct);

					// --- Schleife �ber alle Artikel und Fussnoten zusammensuchen
					for (final Iterator it1 = articles.iterator(); it1.hasNext();)
					{

						// --- Hole Artikel / Variante
						final WeraVariante oWeraVariante = (WeraVariante) it1.next();

						// --- pr�fe ob Variante
						bProduktAuslauf = __isAuslaufArticle(oWeraVariante);
						if (bProduktAuslauf == false)
						{
							// --- Produkt ist kein Auslauf, da mindest eine Variante aktiv ist
							break;
						}

					} // ---for (final Iterator it1 = articles.iterator(); it1.hasNext();) {

				}
				catch (final Exception e)
				{
					LOG.error("_isFilteredProduct Message=" + e.getMessage());
				}

			}// --- if ( !bProduktAuslauf ) {
		}

		return bProduktAuslauf;
	}

	/**
	 * filter varianten
	 * 
	 * @param WeraVariante
	 * @return boolean
	 */
	@Override
	protected boolean _isFilteredVariante(final WeraVariante oWeraVariante)
	{

		// --- Auslaufartikel filtern
		return __isAuslaufArticle(oWeraVariante);
	}


	@Override
	public String CreateOutputPath(final String strLanguage, final String strFileDatum)
	{
		// TODO Auto-generated method stub

		try
		{
			m_strDomain = (String) m_weraCatalogVersion.getAttribute("domain");

			// --- Typeo3 - Erg�nzuzng zu "catalog_[m_strCatalog_Postfix].html initialisieren
			final Collection territories = m_weraCatalogVersion.getTerritories();
			m_strCatalog_Postfix = "de";
			if (territories != null && territories.size() > 0)
			{
				final Country oCountry = (Country) territories.iterator().next();
				if (oCountry != null)
				{
					m_strCatalog_Postfix = oCountry.getIsoCode();
				}
			}
		}
		catch (final JaloInvalidParameterException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (final JaloSecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (m_strDomain == null)
		{
			m_strDomain = m_strCatalogversion;
		}

		m_strImgPath = Config.getParameter("wera.homepath") + "/export/website/" + m_strDomain.toLowerCase() + "/" + m_strFileDatum
				+ "/img/";
		m_strDataPath = Config.getParameter("wera.homepath") + "/export/website/" + m_strDomain.toLowerCase() + "/"
				+ m_strFileDatum + "/data/";
		m_strControlPath = Config.getParameter("wera.homepath") + "/export/website/" + m_strDomain.toLowerCase() + "/"
				+ m_strFileDatum + "/control/";
		m_wm.createDirectory(Config.getParameter("wera.homepath") + "/export/website/" + m_strDomain.toLowerCase());
		m_wm.createDirectory(Config.getParameter("wera.homepath") + "/export/website/" + m_strDomain.toLowerCase() + "/"
				+ m_strFileDatum);
		LOG.info("mkdir()= " + m_strImgPath + ", result=" + m_wm.createDirectory(m_strImgPath));
		LOG.info("mkdir()= " + m_strDataPath + ", result=" + m_wm.createDirectory(m_strDataPath));
		LOG.info("mkdir()= " + m_strControlPath + ", result=" + m_wm.createDirectory(m_strControlPath));

		// --- Imagepath setzen / und Filesystem initialisieren
		m_mediaCollector.setM_StrBasePath(m_strImgPath);
		m_wm.createDirectory(m_strImgPath + "/category");
		m_wm.createDirectory(m_strImgPath + "/products");
		m_wm.createDirectory(m_strImgPath + "/products/index");
		m_wm.createDirectory(m_strImgPath + "/icons");
		m_wm.createDirectory(m_strImgPath + "/features");

		return m_strDataPath;
	}

	/**
	 * Marketingtexte (optional, zur Zeit nur WebExport)
	 * 
	 * @Override
	 * @param String
	 *           strDescription2 - marktingtext
	 * @param Element
	 *           oTextList - XML-Node TEXT-LIST
	 * @return
	 */
	@Override
	public Element createMarketingTextElement(final String strDescription2, final Element oTextList)
	{

		// --- initialize
		Element textXML = null;

		// --- Webmarketing
		if (strDescription2 != null && strDescription2.length() > 0)
		{
			textXML = super.createTextElementFliesstext(strDescription2, oTextList, "BLT_MT");
		}


		return textXML;
	}

	/**
	 * Weblink-List (optional, zur Zeit nur WebExport)
	 * 
	 * @Override
	 * @param WeraProduct
	 *           produktXML - xml node product
	 * @param Element
	 *           weraProduct - current weraproduct
	 * @return Element oWeblinkList - result xml-list
	 */
	@Override
	public Element createWeblinkList(final Element produktXML, final WeraProduct weraProduct)
	{

		// --- initialize
		int iSequence = 1;
		Element oWeblinkList = null;

		// --- get all crossselling products
		final Collection<Weblink> colWeblinks = (Collection) m_wm.getAttribute(weraProduct, "weblinks");

		// --- loop over list
		for (final Weblink oWeblink : colWeblinks)
		{

			// --- Refcode
			final String strCode = (String) m_wm.getAttribute(oWeblink, "code");
			if (strCode != null)
			{
				// --- <Weblink-List>
				if (oWeblinkList == null)
				{
					oWeblinkList = new Element("Weblink-List");
				}

				// --- Element-Node anlegen
				final Element oWeblinkNode = new Element("Weblink");
				oWeblinkList.addContent(oWeblinkNode);
				oWeblinkNode.setAttribute("Refcode", strCode);

				// --- Order
				oWeblinkNode.setAttribute("Sequence", new Integer(iSequence++).toString());
			}

		} // --- for ( final ExtImage oExtImage : colExtImages ) { 


		// --- Liste �bernehmen falls Links enthalten
		if (oWeblinkList != null)
		{
			produktXML.addContent(oWeblinkList);
		}


		return oWeblinkList;
	}

	/**
	 * Crosslink-List (optional, zur Zeit nur WebExport)
	 * 
	 * @Override
	 * @param WeraProduct
	 *           produktXML - xml node product
	 * @param Element
	 *           weraProduct - current weraproduct
	 * @return Element oCrosslinkList - result xml-list
	 */
	@Override
	public Element createCrosslinkList(final Element produktXML, final WeraProduct weraProduct)
	{

		// --- initialize
		Category oCategoryWera = null;
		int iSequence = 1;
		Element oCrosslinkList = null;

		// --- get all crossselling products
		final Collection<WeraProduct> colXSellingProducts = weraProduct.getCrossSellingProducts();

		// --- loop over list
		for (final WeraProduct xwp : colXSellingProducts)
		{
			LOG.info(xwp.getCode());
			final boolean bBelongsToCV = xwp.belongsToCatalogVersion(m_weraCatalogVersion);
			if (bBelongsToCV)
			{

				// --- Link-List anlegen falls noch nicht vorhanden
				if (oCrosslinkList == null)
				{
					oCrosslinkList = new Element("Crosslink-List");
				}

				// --- Element-Node anlegen
				final Element oCrosslinkNode = new Element("Crosslink");
				oCrosslinkList.addContent(oCrosslinkNode);

				// --- code
				oCrosslinkNode.setAttribute("Code", xwp.getCode());

				// --- Title
				String strName = xwp.getName();
				if (strName == null)
				{
					strName = "";
				}
				oCrosslinkNode.setAttribute("Title", strName);
				oCrosslinkNode.setAttribute("Text", strName);

				// --- get all category information ---------------------------------------------------------------------
				// --- get the category (first one only)
				oCategoryWera = xwp.getFirstCategoryByProduct(m_strCatalogversion);
				if (oCategoryWera == null)
				{
					// --- i know it's wrong, but shouldn't be here
					oCategoryWera = m_oCategoryWera;
					LOG.error("no category-info found, we take the default category!!");
				}
				// --- get all category information ---------------------------------------------------------------------

				// --- URL
				final String strNormalizedUrl_Product = WeraProduct.s_normalizeFilenameForImageLookup(xwp.getCode());
				final String strNormalizedUrl_Category = m_wm.strNormalizeURL(oCategoryWera, m_strLanguage);
				oCrosslinkNode.setAttribute("NormalizedUrl", strNormalizedUrl_Category + "_" + strNormalizedUrl_Product);

				// --- Order
				oCrosslinkNode.setAttribute("Sequence", new Integer(iSequence++).toString());

			} // --- if ( bBelongsToCV) {

		} // --- for ( final WeraProduct xwp : colXSellingProducts ) {

		// --- Liste �bernehmen falls Links enthalten
		if (oCrosslinkList != null)
		{
			produktXML.addContent(oCrosslinkList);
		}

		return oCrosslinkList;
	}

	/**
	 * External-Image-List (optional, zur Zeit nur WebExport)
	 * 
	 * @Override
	 * @param WeraProduct
	 *           produktXML - xml node product
	 * @param Element
	 *           weraProduct - current weraproduct
	 * @return Element oExternalImageList - result xml-list
	 */
	@Override
	public Element createExternalImageList(final Element produktXML, final WeraProduct weraProduct)
	{

		// --- initialize
		int iSequence = 1;
		Element oExternalImageList = null;

		// --- get all crossselling products
		final Collection<ExtImage> colExtImages = (Collection) m_wm.getAttribute(weraProduct, "extImages");

		// --- loop over list
		for (final ExtImage oExtImage : colExtImages)
		{

			// --- Refcode
			final String strCode = (String) m_wm.getAttribute(oExtImage, "code");
			if (strCode != null)
			{
				// --- <External-ImageList>
				if (oExternalImageList == null)
				{
					oExternalImageList = new Element("External-Image-List");
				}

				// --- Element-Node anlegen
				final Element oExternalImageNode = new Element("image");
				oExternalImageList.addContent(oExternalImageNode);
				oExternalImageNode.setAttribute("Refcode", strCode);

				// --- Order
				oExternalImageNode.setAttribute("Sequence", new Integer(iSequence++).toString());
			}

		} // --- for ( final ExtImage oExtImage : colExtImages ) { 

		// --- Liste �bernehmen falls Links enthalten
		if (oExternalImageList != null)
		{
			produktXML.addContent(oExternalImageList);
		}

		return oExternalImageList;
	}

	@Override
	protected Collection genTextList(String strTagContent)
	{
		//LOG.info("WeraWebExport::genTextList called.");
		// --- Initialize
		final Collection colResult = new ArrayList();
		final boolean bLeftElement = false;
		String strTypeName = "";

		/* Content s�ubern und nach <br/> splitten */
		strTagContent = prepareForLineSplit(strTagContent);
		final String aList[] = strTagContent.split("<br/>");

		// --- Schleife �ber alle Eintr�ge
		boolean bOutput = false;
		output("normalized-aList.length=" + aList.length, 2);
		for (int iPos = 0; iPos < aList.length; iPos++)
		{
			// --- strTypeName
			bOutput = false;
			final String aElements[] = aList[iPos].split("</b>");

			// --- Content
			if (aElements.length == 2)
			{
				// <b>Name</b>Content<br/>
				strTypeName = aElements[0].trim();
				strTagContent = aElements[1].trim();
				bOutput = true;
			}
			else
			{
				if (aElements.length == 1)
				{
					// <b>Name</b><br/> oder Text_ohne_Fettformatierung<br/>
					if (aElements[0].contains("<b>"))
					{
						strTypeName = aElements[0].trim();
						strTagContent = "";
						bOutput = true;
					}
					else
					{
						strTypeName = "  ";
						strTagContent = aElements[0].trim();
						bOutput = true;
					}
				}
				else
				{
					// Text_mit_mindestens_3_Fettformatierungen<br/>
					if (aElements.length > 0)
					{
						strTypeName = "  ";
						strTagContent = aElements[0].trim();
						bOutput = true;
					}
				}
			}

			if (bOutput)
			{

				// --- Gibt es ein linkes Tabellenelement=
				strTypeName = strTypeName.replaceAll("<b>", "");
				strTagContent = strTagContent.replaceAll("<b>", "");
				if (strTypeName.length() > 0)
				{
					colResult.add(strTypeName);
				}
				if (strTagContent.length() > 0)
				{
					colResult.add(strTagContent);
				}
			}
		}

		return colResult;
	}

	@Override
	protected void SetCheckForActivation(final boolean checkForActivation)
	{
		// TODO Auto-generated method stub
		// --- Keine Aktiv-Pr�fung
		super.SetCheckForActivation(false);
	}

	// --- Merkmal-Icons archivieren
	@Override
	protected Element _initMerkmalIcon(final ClassAttributeAssignment classattributeassignment, final Outputcontrol outputcontrol,
			final WeraMedia weramedia, final String strHashCode, final String strDirectory, final String strOrder)
	{
		// TODO Auto-generated method stub

		// --- Initialize
		ClassificationAttribute classificationAttribute = null;
		if (classattributeassignment != null)
		{
			classificationAttribute = classattributeassignment.getClassificationAttribute();
		}
		WeraMedia icon = null;
		Collection icons = new ArrayList();

		// --- �bernehme �bergegebenes Object
		if (weramedia != null)
		{
			icons.add(weramedia);
		}
		else
		{
			// --- Hole Icon aus Ausgabesteuerung
			if (outputcontrol != null)
			{
				icons = (Collection) getAttribute(outputcontrol, "icons");
			}
			if (icons == null || icons.size() == 0)
			{
				// --- Fallback aus CA
				if (classattributeassignment != null)
				{
					icons = (Collection) getAttribute(classattributeassignment, "icons");
				}
			}
		}
		//

		// --- Initialize
		String strLine = m_weraProductAkt.getCode() + "\t";
		strLine += "merkmalicon\t";

		// --- Wurde ein Icon gefunden
		if (icons != null && icons.size() > 0)
		{
			// --- Initialisiere Daten
			icon = (WeraMedia) icons.iterator().next();
		}

		// --- 
		if (icon != null)
		{
			m_mediaCollector.addMedia(icon.getPK().toString(), icon.getRealFileName(), icon.getFileName(), "products/symbols");
			strLine += "OK\t";
		}
		else
		{
			strLine += "FEHLT\t";
		}
		if (classificationAttribute != null)
		{
			strLine += "\t\t\t\t" + classificationAttribute.getCode();
		}
		else
		{
			strLine += "\t\t\t\tCA=NULL";
		}

		// --- LOG schreiben
		m_aLogFile.add(strLine);

		return super._initMerkmalIcon(classattributeassignment, outputcontrol, weramedia, strHashCode, strDirectory, strOrder);
	}

	@Override
	protected Element _initImage(final WeraMedia weramedia, final String strDirectory, String cProductNr,
			final boolean bProductBild, String strTagName)
	{

		output("s._initImage=", 2);


		// --- <File Id="0" MimeType="image/gif" Media="INTERNET" FileName="\images\pictos\aussen_sechskant_durchmesser.gif" Thumbnail="" ColorDepth="" Height="" Size="" Software="" Author="" Sequence="10" MediaName="INTERNET" Keywords="">
		final Element oFile = new Element("File");
		m_iOffset++;
		oFile.setAttribute("PrintFileName", "I" + new Integer(m_iOffset).toString() + ".eps");
		if (weramedia != null)
		{

			//oFile.setAttribute("PrintMimeType", "image/eps" );
			if (bProductBild)
			{
				// --- Produktbild
				cProductNr = WeraProduct.s_normalizeFilenameForImageLookup(cProductNr);
				oFile.setAttribute("PrintFileName", getExportFormatter().formatPicturePath(strDirectory + "/" + cProductNr + ".eps"));
			}
			else
			{
				String strRealName = weramedia.getRealFileName();
				if (strRealName != null)
				{
					strRealName = strRealName.replace("\\", "/");
					final Pattern p = Pattern.compile("/");
					final String[] aRealName = p.split(strRealName);
					if (aRealName.length > 1)
					{
						strRealName = aRealName[aRealName.length - 1];
					}
					//strRealName = strRealName.replaceAll(".jpg",".eps");
					//strRealName = strRealName.replaceAll(".gif",".eps");
					oFile.setAttribute("PrintFileName", getExportFormatter().formatIconPath(strDirectory + "/" + strRealName));
				}
			}
		}
		else
		{
			// --- Bereinige ProduktNr f�r Bildsuche
			if (!cProductNr.equals(""))
			{

				cProductNr = WeraProduct.s_normalizeFilenameForImageLookup(cProductNr);
				oFile.setAttribute("PrintFileName", getExportFormatter().formatPicturePath(strDirectory + "/" + cProductNr + ".eps"));

			}
			else
			{
				m_iOffset++;
				oFile.setAttribute("PrintFileName", "I" + new Integer(m_iOffset).toString() + ".eps");
			}
		}

		output("e._initImage=", 2);

		return oFile;
	}


	public final String createSearchDataLine(final String sPk, final String sProductType, final String sCode,
			final String sArtikelNr, final String sName, final String sImageFilename, final String sCategoryName1,
			final String sCategoryPath1, final String sCategoryName2, final String sCategoryPath2, final String sDefaultPriority)
	{
		final String sSep = "|";
		final String sLine = sPk + sSep + sProductType + sSep + sCode + sSep + sArtikelNr + sSep + sName + sSep + sImageFilename
				+ sSep + sCategoryName1 + sSep + sCategoryPath1 + sSep + sCategoryName2 + sSep + sCategoryPath2 + sSep
				+ sDefaultPriority;
		return sLine;
	}

	/**
	 * @param colLangReq
	 *           : Collection of languages
	 * @param colCategories
	 * @return status message string
	 * 
	 *         exports a csv list of all product data relevant for the web search using the
	 *         "<domain_dir>/<language_dir>/" export path. one file per language is generated, i.e.: "de_search_data.txt"
	 */
	public String GenerateSearchData(final Collection colLangReq, final String sCatalogVersionId, final Collection colCategories,
			final String sTemplateVersion)
	{
		final String rVal = "Success";
		// "template_version"

		final HashMap<String, HashSet<Category>> hProductPK2Supercategories = new HashMap();

		LOG.info("GenerateSearchData(): START");

		m_wm.SetLanguage("de");

		// create datetime to be used as version id for the file
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final Calendar cal = Calendar.getInstance();
		LOG.info(dateFormat.format(cal.getTime()));

		// get the requested web catalog
		LOG.info("GenerateSearchData(): Requesting catalogversion web /   " + sCatalogVersionId);
		final CatalogVersion reqCv = m_wm.getCatalogVersion("web", sCatalogVersionId);
		final CatalogVersion weramasterCv = m_wm.getCatalogVersion("weracatalog", "weramaster");

		// Pass 1: Build a map 'hProductPK2Supercategories' of product PKs to a set of all its supercategories
		LOG.info("GenerateSearchData(): Pass 1: Building map product => supercategories ... ");
		for (final Iterator itCat = colCategories.iterator(); itCat.hasNext();)
		{
			final String sCurrentCategory = (String) (itCat.next());
			LOG.info("GenerateSearchData(): Pass 1: Processing category " + sCurrentCategory);

			final Category oCurrentCategory = reqCv.getCategory(sCurrentCategory);
			final Category oMappedCategory = weramasterCv.getCategory(sCurrentCategory);
			if (oCurrentCategory != null)
			{
				// hPKSetProcessed
				final List<Product> lProducts = oCurrentCategory.getProducts();
				for (final Product p : lProducts)
				{
					if (p instanceof WeraProduct)
					{
						final WeraProduct wp = (WeraProduct) p;
						final String sPK = wp.getPK().toString();

						// --- auslaufartikel filtern
						if (_isFilteredProduct(wp))
						{
							continue;
						}
						// --- auslaufartikel filtern


						HashSet<Category> hCat = hProductPK2Supercategories.get(sPK);
						if (hCat == null)
						{
							hCat = new HashSet();
						}
						if (oMappedCategory != null)
						{
							hCat.add(oMappedCategory);
						}
						else
						{
							hCat.add(oCurrentCategory);
						}
						hProductPK2Supercategories.put(sPK, hCat);
					}
				}
			}
		}
		LOG.info("GenerateSearchData(): Pass 1 Complete.");


		// for each requested language process all categories and their products
		LOG.info("GenerateSearchData(): Pass 2: Generating data ...");
		for (final Iterator itLang = colLangReq.iterator(); itLang.hasNext();)
		{
			final String sLang = (String) itLang.next();
			LOG.info("GenerateSearchData(): Pass 2: processing language " + sLang);

			String sDomainName = null;
			try
			{
				sDomainName = (String) reqCv.getAttribute("domain");
			}
			catch (final JaloInvalidParameterException e2)
			{
				// YTODO Auto-generated catch block
				e2.printStackTrace();
			}
			catch (final JaloSecurityException e2)
			{
				// YTODO Auto-generated catch block
				e2.printStackTrace();
			}

			final String sDomain = sDomainName.toLowerCase();

			// use output directory dependend on template choice 
			String sOutputhtmlDir = "outputhtml";
			if (sTemplateVersion != null && sTemplateVersion.startsWith("_v"))
			{
				sOutputhtmlDir += sTemplateVersion;
			}

			// setup destination directory and file
			final String sOutputDomainPath = Config.getParameter("wera.homepath") + "/export/website/" + sOutputhtmlDir + "/"
					+ sDomainName.toLowerCase() + "/";

			String sLangDir = sLang;
			if (sDomain.equals("wera-tools.co.uk"))
			{
				sLangDir = "en-UK";
			}
			else
			{
				if (sDomain.equals("weratools.com"))
				{
					if (sLang.equals("us-en"))
					{
						sLangDir = "en-US";
					}
					if (sLang.equals("us-es"))
					{
						sLangDir = "es-US";
					}
					if (sLang.equals("us-fr"))
					{
						sLangDir = "fr-CA";
					}
				}
			}

			final String sOutputPath = sOutputDomainPath + sLangDir + "/";
			LOG.info("GenerateSearchData(): Pass 2: creating domain directory: " + m_wm.createDirectory(sOutputDomainPath));
			LOG.info("GenerateSearchData(): Pass 2: creating language directory: " + m_wm.createDirectory(sOutputPath));

			FileWriter out = null;
			try
			{
				out = new FileWriter(sOutputPath + sLang + "_search-data.txt", false);
				LOG.info("GenerateSearchData(): Pass 2: creating data file: " + sLang + "_search-data.txt");
				out.write("# " + System.getProperty("line.separator"));
				out.write("# Wera Search Data: " + sLang + System.getProperty("line.separator"));
				out.write("# Version: " + dateFormat.format(cal.getTime()) + System.getProperty("line.separator"));
				out.write("# Format: <Entity-PK>|<Entity-Type>|<Entity-9-digit-Code>|<Product-CODE>|<Product-Name>|<Product-Imagename>|<CategoryName1>|<CategoryPath1>|<CategoryName2>|<CategoryPath2>|<ProductPriority>"
						+ System.getProperty("line.separator"));
				out.write("# " + System.getProperty("line.separator"));
			}
			catch (final IOException e1)
			{
				// YTODO Auto-generated catch block
				e1.printStackTrace();
			}

			m_wm.SetLanguage(sLang);

			final HashSet<String> hSetProducts = new HashSet();

			for (final Iterator itCat = colCategories.iterator(); itCat.hasNext();)
			{
				final String sCurrentCategory = (String) (itCat.next());
				LOG.info("GenerateSearchData(): Pass 2: processing category: " + sCurrentCategory + " (" + sLang + ")");

				final Category oCurrentCategory = reqCv.getCategory(sCurrentCategory);

				if (oCurrentCategory != null)
				{
					final List<Product> lProducts = oCurrentCategory.getProducts();
					for (final Product p : lProducts)
					{
						if (p instanceof WeraProduct)
						{

							// initialize data common to all WeraProduct types
							final WeraProduct wp = (WeraProduct) p;

							// --- auslaufartikel filtern
							if (_isFilteredProduct(wp))
							{
								continue;
							}
							// --- auslaufartikel filtern

							WeraProductSet wps = null;
							WeraProductSetinSet wpsis = null;
							String sPk = null;
							String sCode = null;
							String sArtikelNr = null;
							String sName = null;
							String sImageFilename = null;
							String sDefaultPriority = null;
							String sCategoryName1 = "";
							String sCategoryName2 = "";
							String sCategoryPath1 = "";
							String sCategoryPath2 = "";
							String sProductType = "V";

							LOG.info("GenerateSearchData(): Pass 2: Processing product " + wp.getCode() + " (" + sLang + ")");

							// get base product's code
							sArtikelNr = wp.getCode();
							// get base product's name
							sName = wp.getName();
							// ... and eliminate any line feed or carriage return which would destroy our output format
							if (sName != null)
							{
								sName = sName.replaceAll("[\n\r]", "");
							}
							// get base product's image file name
							sImageFilename = WeraProduct.s_normalizeFilenameForImageLookup(sArtikelNr);
							// get base product's default search priority							
							int iPriorityWebSearchProduct = 0;
							try
							{
								iPriorityWebSearchProduct = wp.getPriorityWebSearch().intValue();
							}
							catch (final Exception e)
							{
								// LOG.warn("GenerateSearchData(): Exception trying to get the search priority of product " + wp.getCode());
							}

							// get base product's 1st and 2nd super category, ignore any others
							final HashSet<Category> hCat = hProductPK2Supercategories.get(wp.getPK().toString());
							int iCnt = 0;
							int iPriorityWebSearchCategory = 0;
							if (hCat == null)
							{
								LOG.info("GenerateSearchData(): could not fetch super category for product " + wp.getCode()
										+ ", maybe due to 'auslauf' flag.");
							}
							else
							{
								for (final Category oSuperCat : hCat)
								{
									iCnt++;
									int iPrio = 0;
									try
									{
										iPrio = ((Integer) oSuperCat.getAttribute("priorityWebSearch")).intValue();
										if (iPrio > iPriorityWebSearchCategory)
										{
											iPriorityWebSearchCategory = iPrio;
										}
									}
									catch (final JaloInvalidParameterException e)
									{
										// YTODO Auto-generated catch block
										LOG.warn("iPriorityWebSearch(): InvalidParameter Exception trying to get int value of priorityWebSearch of category "
												+ oSuperCat.getCode());
									}
									catch (final JaloSecurityException e)
									{
										// YTODO Auto-generated catch block
										LOG.warn("iPriorityWebSearch(): JaloSecurityException Exception trying to get int value of priorityWebSearch of category "
												+ oSuperCat.getCode());
									}

									final String sCategoryName = oSuperCat.getName();
									final String sCategoryPath = m_wm.strNormalizeURL(oSuperCat, sLang);
									switch (iCnt)
									{
										case 1:
											sCategoryName1 = sCategoryName;
											sCategoryPath1 = sCategoryPath;
											break;
										case 2:
											sCategoryName2 = sCategoryName;
											sCategoryPath2 = sCategoryPath;
											break;
									}
									if (iCnt > 1)
									{
										break;
									}
								}
							}
							// set search priority of this product to either the product's priority or the category's priority, if product's priority is empty
							sDefaultPriority = Integer.toString(iPriorityWebSearchProduct);
							if (iPriorityWebSearchProduct == 0 && iPriorityWebSearchCategory > 0)
							{
								sDefaultPriority = Integer.toString(iPriorityWebSearchCategory);
							}


							try
							{

								if (p instanceof WeraProductSet)
								{
									if (p instanceof WeraProductSetinSet)
									{
										// check subsets of SiS and export their data
										wpsis = (WeraProductSetinSet) p;

										sProductType = "SiS";
										sCode = "";

										final Collection<WeraProductSetVariants> colWPSV = wpsis.getWeraproductsetvariants_qual();
										for (final WeraProductSetVariants wpsv : colWPSV)
										{
											final WeraProductSet wpsSiS = wpsv.getWeraproductsets();
											sCode = (String) wpsSiS.getAttribute("artNr")
													+ (String) wpsSiS.getLocalizedProperty("variantenNr");
											sPk = wpsSiS.getPK().toString();
											try
											{
												final String sLineOfData = createSearchDataLine(sPk, sProductType, sCode, sArtikelNr, sName,
														sImageFilename, sCategoryName1, sCategoryPath1, sCategoryName2, sCategoryPath2,
														sDefaultPriority);
												if (!hSetProducts.contains(sPk))
												{
													out.write(sLineOfData + System.getProperty("line.separator"));
													hSetProducts.add(sPk);
												}
											}
											catch (final JaloInvalidParameterException e)
											{
												// YTODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									}
									else
									{
										// handle non-SiS sets
										wps = (WeraProductSet) p;
										sPk = wps.getPK().toString();
										sProductType = "S";
										try
										{
											sCode = (String) wps.getAttribute("artNr") + (String) wps.getLocalizedProperty("variantenNr");
											final String sLineOfData = createSearchDataLine(sPk, sProductType, sCode, sArtikelNr, sName,
													sImageFilename, sCategoryName1, sCategoryPath1, sCategoryName2, sCategoryPath2,
													sDefaultPriority);

											if (!hSetProducts.contains(sPk))
											{
												out.write(sLineOfData + System.getProperty("line.separator"));
												hSetProducts.add(sPk);
											}

										}
										catch (final JaloInvalidParameterException e)
										{
											// YTODO Auto-generated catch block
											e.printStackTrace();
										}
										catch (final JaloSecurityException e)
										{
											// YTODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}
								else
								{
									// handle standard products: export entities are their respective variants
									Collection colVariants = null;
									colVariants = wp.getVarianten();
									for (final Iterator itVariants = colVariants.iterator(); itVariants.hasNext();)
									{
										final WeraVariante wv = (WeraVariante) itVariants.next();

										// --- auslaufartikel filtern
										if (_isFilteredVariante(wv))
										{
											continue;
										}
										// --- auslaufartikel filtern										

										sPk = wv.getPK().toString();
										sCode = wv.getCode() + wv.getVariantenNr();
										final String sLineOfData = createSearchDataLine(sPk, sProductType, sCode, sArtikelNr, sName,
												sImageFilename, sCategoryName1, sCategoryPath1, sCategoryName2, sCategoryPath2,
												sDefaultPriority);
										if (!hSetProducts.contains(sPk))
										{
											out.write(sLineOfData + System.getProperty("line.separator"));
											hSetProducts.add(sPk);
										}
									}

								}

							}
							catch (final JaloInvalidParameterException e)
							{
								// YTODO Auto-generated catch block
								e.printStackTrace();
							}
							catch (final JaloSecurityException e)
							{
								// YTODO Auto-generated catch block
								e.printStackTrace();
							}
							catch (final IOException e)
							{
								// YTODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}



				}

			}
			if (out != null)
			{
				try
				{
					out.close();
					LOG.info("GenerateSearchData(): Pass 2: Finalizing output file.");
				}
				catch (final IOException e)
				{
					// YTODO Auto-generated catch block
					e.printStackTrace();
				}
			}


		}
		m_wm.SetLanguage("de");
		return rVal;
	}
}
