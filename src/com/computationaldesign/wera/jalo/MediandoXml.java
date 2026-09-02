package com.computationaldesign.wera.jalo;

import com.sun.org.apache.bcel.internal.generic.LoadClass;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.zip.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Comment;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import de.hybris.platform.catalog.jalo.classification.ClassificationAttribute;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.util.Config;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.core.PK;

import org.apache.log4j.Logger;

public class MediandoXml extends XmlSupport {

	private static final Logger LOG = Logger.getLogger(WeraKatalog.class.getName());

	// --- Member
	public String g_strAbmessung = "";
	public HashMap g_oHashMapCodeNr = new HashMap();

	// --- Z�hler
	public int m_iOffsetIDEbene1 = 0;
	public int m_iOffsetIDEbene2 = 0;
	public int m_iOffsetIDEbene3 = 0;
	public int m_nCountProductSet = 0;

	// --- Knotenelemente
	public Element m_oTreegroupLink = null;
	public Element m_oReferenceElements = null;
	public Element m_oTreegroupList = null;
	public Element m_oTreegroup = null;
	public Element m_oProductList = null;
	public Element m_oProductLink = null;
	public Element m_oProductJoinList = null;
	public Element m_oProductJoin = null;
	public Element m_oTextList = null;
	public Element m_oTextItem = null;
	public Element m_oAttributeList = null;
	public Element m_oAttributeListArtikel = null;
	public Element m_articelXML = null;
	public Element m_motivelistXML = null;
	public Element m_bildXML = null;
	public Element m_oArticelList = null;

	protected String m_strExportLanguage = "de";
	protected Collection<String> m_languages	= null;

	/**
	 *
	 */
	public MediandoXml() {
		super();
		// TODO Auto-generated constructor stub

		// --- Initialize
		m_wm = WeraManager.getInstance();
		m_jaloSession = JaloSession.getCurrentSession();
	}

	/**
	 * Zur�cksetzen der Sprache, Aaufr�umen
	 */
	public void cleanUp() {

		// --- Zur�cksetzen der Sprache
		if (p_oDefaultLanguage != null) {
			m_jaloSession.getSessionContext().setLanguage(p_oDefaultLanguage);
		}

		// --- XML-Knoten zur�cksetzen
		m_rootElement = null;
		m_oTreegroupLink = null;
		m_oReferenceElements = null;
		m_oTreegroupList = null;
		m_oTreegroup = null;
		m_oProductList = null;
		m_oProductLink = null;
		m_oProductJoinList = null;
		m_oProductJoin = null;
		m_oTextList = null;
		m_oTextItem = null;
		m_oAttributeList = null;
		m_oAttributeListArtikel = null;
		m_articelXML = null;
		m_motivelistXML = null;
		m_bildXML = null;
		m_oArticelList = null;

		// --- Z�hler zur�cksetzen
		m_nCountProductSet = 0;
		m_iOffsetIDEbene1 = 0;
		m_iOffsetIDEbene2 = 0;
		m_iOffsetIDEbene3 = 0;
	}

	/**
	 *
	 * @param sBasePath
	 * @param strEntry
	 * @param iRecursivCounter
	 * @return
	 */
	private Collection listDirRecursiv(String sBasePath, String strEntry, int iRecursivCounter) {

		// --- Initialize
		LOG.info("++[listDirRecursiv] sBasePath=" + sBasePath);
		LOG.info("   strEntry=" + strEntry);
		File fileTest = null;
		Collection colFileList = new ArrayList();

		// --- Get File
		sBasePath	= sBasePath.toLowerCase();
		strEntry	= strEntry.toLowerCase();
		File f = new File(sBasePath + "/" + strEntry);
		if (f != null) {

			String files[] = f.list();
			for (int i = 0; i < files.length; i++) {

				fileTest = new File(sBasePath + "/" + strEntry + "/" + files[i]);
				// --- Ist es schon wieder ein Verzeichnis?
				if (fileTest.isDirectory()) {

					// --- get all Files
					LOG.info("++[DIR] iRecursivCounter=" + iRecursivCounter);
					LOG.info("   files[i]=" + files[i]);
					colFileList.addAll(listDirRecursiv(sBasePath + "/" + strEntry, files[i], iRecursivCounter + 1));
				} else {
					// --- remember this file
					LOG.info("++[FILE] iRecursivCounter=" + iRecursivCounter);
					LOG.info("   files[i]=" + files[i]);
					if (iRecursivCounter == 0) {
						colFileList.add(files[i]);
					} else {
						colFileList.add(strEntry + "/" + files[i]);
					}
				}

				// --- Aufr�umen
				fileTest = null;
			}
		} // --- if ( f !=  null ) {

		return colFileList;
	}

	public void deleteDirRecursiv(String sBasePath, String strEntry) {

		// --- Initialize
		File fileTest = null;
		Collection colFileList = new ArrayList();

		// --- Get File
		File f = new File(sBasePath + "/" + strEntry);
		if (f != null) {

			String files[] = f.list();
			for (int i = 0; i < files.length; i++) {

				fileTest = new File(sBasePath + "/" + strEntry + "/" + files[i]);
				// --- Ist es schon wieder ein Verzeichnis?
				if (fileTest.isDirectory()) {

					// --- get all Files
					deleteDirRecursiv(sBasePath + "/" + strEntry, files[i]);
					fileTest.delete();
				} else {
					// --- delete this file
					fileTest.delete();
				}

				// --- Aufr�umen
				fileTest = null;
			}

			// --- Aufr�umen
			files = null;

		} // --- if ( f !=  null ) {

		// --- L�sche Basefile
		f.delete();
		f = null;
	}

	/**
	 * Zipt ein File oder ein Verzeichnis
	 * 
	 * @param strKatalogName
	 * @param strSrcName
	 * @param strDestName
	 * @return 
	 */
	public String zipRequestedFilesV2(String strKatalogName, String strSrcName, String strDestName) {
		final int BUFFER = 2048;

		// --- immer lowerCase
		strKatalogName		= strKatalogName.toLowerCase();
		strSrcName			= strSrcName.toLowerCase();

		// --- preset
		String sBasePath = Config.getParameter("wera.exportpath") + "katalog/" + strKatalogName + "/";
		String sResultPath = Config.getParameter("wera.homepath") + "/tmp/";
		String strResultFile = "";
		LOG.info("zipRequestedFilesV2.sBasePath=" + sBasePath);

		try {
			BufferedInputStream origin = null;

			// Check, if some files of this category have already been created. If so, use counter on filename
			File fCheck = new File(sResultPath + strDestName + ".zip");
			int iCnt = 0;
			while (fCheck.exists()) {
				fCheck = null;
				fCheck = new File(sResultPath + strDestName + "_" + ++iCnt + ".zip");
			}
			fCheck = null;
			if (iCnt > 0) {
				strDestName = strDestName + "_" + iCnt;
			}
			strResultFile = sResultPath + strDestName + ".zip";

			FileOutputStream dest = new FileOutputStream(strResultFile);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
			//out.setMethod(ZipOutputStream.DEFLATED);
			byte data[] = new byte[BUFFER];

			// get a list of files from current directory
			Collection colFileList = new ArrayList();
			colFileList.addAll(listDirRecursiv(sBasePath, strSrcName, 0));

			// --- Schleife �ber alles Files
			LOG.info("Anzahl: " + colFileList.size());
			for (Iterator it1 = colFileList.iterator(); it1.hasNext();) {
				String strFile = (String) it1.next();
				LOG.info("Adding: " + sBasePath + strSrcName + "/" + strFile);

				FileInputStream fi = new FileInputStream(sBasePath + strSrcName + "/" + strFile);
				origin = new BufferedInputStream(fi, BUFFER);
				ZipEntry entry = new ZipEntry(strFile);
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();

			}
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return strResultFile;

	}

	/**
	 * Zipt ein File oder ein Verzeichnis
	 * 
	 * @param strSrcName
	 * @param strDestName
	 * @return 
	 */
	public String zipRequestedFiles(String strSrcName, String strDestName) {

		LOG.info("zipRequestedFiles() called ...");

		// --- immer lowerCase
		strSrcName			= strSrcName.toLowerCase();


		final int BUFFER = 2048;
		String sBasePath = Config.getParameter("wera.exportpath");
		String sResultPath = Config.getParameter("wera.homepath") + "/tmp/";
		String strResultFile = "";

		try {
			BufferedInputStream origin = null;

			// Check, if some files of this category have already been created. If so, use counter on filename
			File fCheck = new File(sResultPath + strDestName + ".zip");
			int iCnt = 0;
			while (fCheck.exists()) {
				fCheck = null;
				fCheck = new File(sResultPath + strDestName + "_" + ++iCnt + ".zip");
			}
			fCheck = null;
			if (iCnt > 0) {
				strDestName = strDestName + "_" + iCnt;
			}
			strResultFile = sResultPath + strDestName + ".zip";

			FileOutputStream dest = new FileOutputStream(strResultFile);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
			//out.setMethod(ZipOutputStream.DEFLATED);
			byte data[] = new byte[BUFFER];

			// get a list of files from current directory
			Collection colFileList = new ArrayList();
			colFileList.addAll(listDirRecursiv(sBasePath, strSrcName, 0));

			// --- Schleifew �ber alles Files
			LOG.info("Anzahl: " + colFileList.size());
			for (Iterator it1 = colFileList.iterator(); it1.hasNext();) {
				String strFile = (String) it1.next();
				LOG.info("Adding: " + sBasePath + strSrcName + "/" + strFile);

				FileInputStream fi = new FileInputStream(sBasePath + strSrcName + "/" + strFile);
				origin = new BufferedInputStream(fi, BUFFER);
				ZipEntry entry = new ZipEntry(strFile);
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();

			}
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return strResultFile;
	}

	/**
	 * download file
	 * 
	 * @param response
	 * @param strResultFile
	 * @param bDeleteFile
	 * @return 
	 */
	public String strDownloadFile(HttpServletResponse response, String strResultFile, boolean bDeleteFile) {

		LOG.info("strDownloadFile() called ...");

		// --- Initlialize
		String strResult = "Download von " + strResultFile;
		strResult += " wurde erfolgreich abgeschlossen.";

		BufferedInputStream in;
		try {
			// --- File-Param 
			File oFile = new File(strResultFile);
			if (oFile.exists()) {
				String strContentSize = new Long(oFile.length()).toString();
				String strFileName = oFile.getName();

				// --- debug info
				LOG.info("Datei:" + strResultFile);
				LOG.info("strContentSize:" + strContentSize);

				// --- Header
/*				
				response.setHeader("Content-Size", strContentSize);
				response.setHeader("Content-Transfer-Encoding", "Binary");
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment; filename=" + strFileName);
*/				
				//response.setHeader("Cache-Control", "public");
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Pragma", "public");
				response.setHeader("Content-Description",  "File Transfer");
				response.setContentType("application/octet-stream");
				//response.setHeader("Content-Disposition", "attachment; filename=" + strFileName );
				response.setHeader("Content-Disposition", "inline; filename=\""  + strFileName +"\"" );
				response.setHeader("Content-Transfer-Encoding", "binary");
				response.setHeader("Content-Length", strContentSize);				
				ServletOutputStream out = response.getOutputStream();
/*
				response.setHeader("Pragma", "public");
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Cache-Control", "public");
				response.setHeader("Content-Description",  "File Transfer");
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + strResultFile + "\"");
				response.setHeader("Content-Transfer-Encoding", "binary");
				response.setHeader("Content-Length", strContentSize);				
*/
				in = new BufferedInputStream(new FileInputStream(strResultFile));
				byte[] buf = new byte[4 * 1024]; // 60K buffer
				int bytesRead;
				while ((bytesRead = in.read(buf)) != -1) {
					out.write(buf, 0, bytesRead);
				}

				// --- Stream schliessen
				out.flush();
				out.close();
				in.close();

				// --- Datei l�schen
				if (bDeleteFile) {
					LOG.info("File delete=" + strResultFile);
					oFile.delete();
				}
			} else {
				strResult = "Downloaddatei " + strResultFile;
				strResult += " wurde wurde nicht gefunden.";
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return strResult;
	}

	/**
	 * 
	 * @param strCatalogversion
	 * @return 
	 */
	public boolean bInitMediando(String strCatalogversion) {
		SAXBuilder builder = new SAXBuilder();  // parameters control validation, etc
		//Document doc = builder.build(strXmlFile);

		// --- MediaXML - Real Rootcontent "Inbetween"
		m_rootElement = new Element("MediaXML");
		m_rootElement.setAttribute("Language", m_Language.getName());
		//root.setAttribute("LanguageName", "Leertext");
		//root.setAttribute("ThumbnailRoot", "TD");
		//root.setAttribute("MotiveRoot", "TD");

		// --- <ReferenceElements HierarchyRefCode="ONLINE_CATALOG_03/04" Type="HIERARCHY">
		m_oReferenceElements = new Element("ReferenceElements");
		m_oReferenceElements.setAttribute("HierarchyRefCode", strCatalogversion);
		m_oReferenceElements.setAttribute("Type", "HIERARCHY");
		m_rootElement.addContent(m_oReferenceElements);

		// --- <TreegroupLink LinkedId="1376" LinkedRefCode="ONLINE_CATALOG_03/04" LinkedType="ROOT" LinkedWorkflow="OKE_DATEN" Sequence="0" Hierarchy="ONLINE_CATALOG_03/04" LinkedName="ONLINE CATALOG 03/04" LinkedTypeName="ROOT" LinkedWorkflowName="OKE_DATEN">
		m_oTreegroupLink = new Element("TreegroupLink");
		m_oTreegroupLink.setAttribute("LinkedId", "root");
		m_oTreegroupLink.setAttribute("LinkedRefCode", "datum");
		m_oTreegroupLink.setAttribute("LinkedType", "ROOT");
		//oKatalogLnkList.setAttribute("LinkedWorkflow",     "OK");
		m_oTreegroupLink.setAttribute("Sequence", "0");
		//oKatalogLnkList.setAttribute("Hierarchy",          strCatalogversion);
		m_oTreegroupLink.setAttribute("LinkedName", "S�tze");
		m_oTreegroupLink.setAttribute("LinkedTypeName", "ROOT");
		//oKatalogLnkList.setAttribute("LinkedWorkflowName", "OK");
		m_oReferenceElements.addContent(m_oTreegroupLink);

		// --- <Treegroup-List>
		m_oTreegroupList = new Element("Treegroup-List");
		m_rootElement.addContent(m_oTreegroupList);

		// --- <Treegroup Id="1376" Type="ROOT" RefCode="ONLINE_CATALOG_03/04" Workflow="OKE_DATEN" TypeName="ROOT" Name="ONLINE CATALOG 03/04" WorkflowName="OKE_DATEN">
		m_oTreegroup = _createTreegroupElement("root", "ROOT", strCatalogversion);

		// --- <Product-List>
		m_oProductList = new Element("Product-List");
		m_rootElement.addContent(m_oProductList);

		// --- Ausgabedatum
		String strDatum = (new Date()).toLocaleString();
		Comment dateComment = new Comment(" Datenexport vom: " + strDatum);
		m_rootElement.addContent(dateComment);

		return true;
	}

	/**
	 * 
	 * @param strId
	 * @param strType
	 * @param strCatalogversion
	 * @return 
	 */
	private Element _createTreegroupElement(String strId, String strType, String strCatalogversion) {
		Element oTreegroup = new Element("Treegroup");
		oTreegroup.setAttribute("Id", strId);
		oTreegroup.setAttribute("Type", strType);
		oTreegroup.setAttribute("RefCode", strCatalogversion);
		//oTreegroup.setAttribute("Workflow",     "GEPRUEFFT" ); 
		oTreegroup.setAttribute("TypeName", strType);
		oTreegroup.setAttribute("Name", strCatalogversion);
		//oTreegroup.setAttribute("WorkflowName", "GEPRUEFFT" );

		return oTreegroup;
	}

	/**
	 * 
	 * @param strTagName
	 * @param weramedia
	 * @param strPrio
	 * @param strDirectory
	 * @param strCodeNrProduct
	 * @return 
	 */
	public Element createBildElement(String strTagName, WeraMedia weramedia, String strPrio, String strDirectory, String strCodeNrProduct) {
		Element oMotive = new Element("Motive");

		// --- Initialize
		String strImageExtention = "";
		String strCode = "";
		String strRealName = "";
		if (weramedia != null) {
			strCode = weramedia.getCode();
			strRealName = weramedia.getRealFileName();
			if ( strRealName != null && strTagName.contains("FEATRUREICON") ) {
				strRealName		= strRealName.replaceAll(".jpg", ".eps");
				strRealName		= strRealName.replaceAll(".gif", ".eps");
				strImageExtention	= ".eps";
				System.out.println("EPS ****************** " + strRealName );
			}
			else {
				String[] aRealName	= strRealName.split(".");
				strImageExtention		= "." + aRealName[1];
				System.out.println( strImageExtention + " ****************** " + strRealName );
			}
		}

		// --- Neues Element anlegen
		// --- <Motive Id="0" Type="ATTRIBUTE_IMAGE" LinkType="ATTRIBUTE_IMAGE" RefCode="ATTR_aussen_sechskant_durchmesser" Thumbnail="" Sequence="0" TypeName="ATTRIBUTE_IMAGE" LinkTypeName="ATTRIBUTE_IMAGE" Name="Leertext" Keywords="">
		oMotive.setAttribute("Id", strCode);
		oMotive.setAttribute("TypeName", strTagName);
		oMotive.setAttribute("Sequence", strPrio);
		//oMotive.setAttribute("RefCode",      strCode );
		//oMotive.setAttribute("Type",         strTagName );
		//oMotive.setAttribute("LinkType",     strTagName );
		//oMotive.setAttribute("Thumbnail",    "" );
		//oMotive.setAttribute("LinkTypeName", strTagName );
		//oMotive.setAttribute("Name",         strRealName );
		//oMotive.setAttribute("Keywords",     "" );

		// --- <File-List>
		Element oFileList = new Element("File-List");

		// --- <File Id="0" MimeType="image/gif" Media="INTERNET" FileName="\images\pictos\aussen_sechskant_durchmesser.gif" Thumbnail="" ColorDepth="" Height="" Size="" Software="" Author="" Sequence="10" MediaName="INTERNET" Keywords="">
		oFileList.addContent(_initImage(weramedia, strDirectory, strCodeNrProduct, strImageExtention ));

		// --- Setze zusammen
		oMotive.addContent(oFileList);

		return oMotive;
	}

	/**
	 * 
	 * @param weramedia
	 * @param strDirectory
	 * @param cProductNr
	 * @return 
	 */
	protected Element _initImage(WeraMedia weramedia, String strDirectory, String cProductNr, String strImageExtention ) {

		// --- <File Id="0" MimeType="image/gif" Media="INTERNET" FileName="\images\pictos\aussen_sechskant_durchmesser.gif" Thumbnail="" ColorDepth="" Height="" Size="" Software="" Author="" Sequence="10" MediaName="INTERNET" Keywords="">
		Element oFile = new Element("File");
		if (weramedia != null) {

			//oFile.setAttribute("PrintMimeType", "image/eps" );
			String strRealName = weramedia.getRealFileName();
			//LOG.info("realname vorher="+strRealName);
			if (strRealName != null) {
				strRealName = strRealName.replace("\\", "/");
				Pattern p = Pattern.compile("/");
				String[] aRealName = p.split(strRealName);
				if (aRealName.length > 1) {
					strRealName = aRealName[aRealName.length - 1];
				}
			//LOG.info("realname vorher="+strRealName);
				strRealName = strRealName.replaceAll(".eps", strImageExtention /* ".eps" */ );
				strRealName = strRealName.replaceAll(".jpg", strImageExtention /* ".eps" */ );
				strRealName = strRealName.replaceAll(".gif", strImageExtention /* ".eps" */ );
			// LOG.info("realname nacher="+strRealName);
			}
			oFile.setAttribute("PrintFileName", strDirectory + "/" + strRealName);

			//oFile.setAttribute("MimeType", weramedia.getMime() );
			//oFile.setAttribute("FileName", weramedia.getFileName() );
		} else {
			// --- Bereinige ProduktNr f�r Bildsuche
			if (!cProductNr.equals("")) {

				cProductNr = WeraProduct.s_normalizeFilenameForImageLookup(cProductNr);
				oFile.setAttribute("PrintFileName", strDirectory + "/" + cProductNr + strImageExtention );
				
			} else {
				m_iOffsetIDEbene3++;
				oFile.setAttribute("PrintFileName", strDirectory + "/test_trans.eps");
			}
			//oFile.setAttribute("PrintMimeType", "image/eps" );
			//oFile.setAttribute("MimeType",   "" );
			//oFile.setAttribute("FileName",   "" );
		}
		//oFile.setAttribute("Sequence",   "10" );
		//oFile.setAttribute("Id",         "0" );
		//oFile.setAttribute("Media",      "PRINT" );
		//oFile.setAttribute("Thumbnail",  "" );
		//oFile.setAttribute("ColorDepth", "" );
		//oFile.setAttribute("Height",     "" );
		//oFile.setAttribute("Size",       "" );
		//oFile.setAttribute("Software",   "" );
		//	oFile.setAttribute("Author",     "" );
		//oFile.setAttribute("MediaName",  "PRINT" );
		//oFile.setAttribute("Keywords",   "" );

		return oFile;
	}

	/**
	 * 
	 * @param strTypeName
	 * @param strTagContent
	 * @param iOrder
	 * @param iID
	 * @param strLinkType
	 * @param bConvertTag
	 * @return 
	 */
	protected Element _createTextElement(String strTypeName, String strTagContent, Integer iOrder, Integer iID, String strLinkType, boolean bConvertTag) {
		// --- Content bereinigen
		strTypeName = strTypeName.replaceAll("<p>", "");
		strTypeName = strTypeName.replaceAll("</p>", "");
		strTypeName = strTypeName.replaceAll("<b>", "");
		strTypeName = strTypeName.replaceAll("</b>", "");

		// --- Text konvertieren
		//System.out.println("v.strTagContent="+strTagContent);
		strTagContent = strTagContent.replaceAll("</", "##e_");
		if (bConvertTag) {
			strTagContent = strTagContent.replaceAll("<", "##");
			strTagContent = strTagContent.replaceAll(">", "##");
		}
/////////////////////////// 
// entfernt template-optimierung					
//		strTagContent = strTagContent.replaceAll("##sup##", "##sub##");
//		strTagContent = strTagContent.replaceAll("##e_sup##", "##e_sub##");
		strTagContent = strTagContent.replaceAll("##sup##", "");
		strTagContent = strTagContent.replaceAll("##e_sup##", "");
/////////////////////////// 
		strTagContent = strTagContent.replaceAll("&quot;", "\"");
		//System.out.println("n.strTagContent="+strTagContent);
		//strTypeName = strTypeName.trim();
		//strTagContent = strTagContent.trim();

		// --- Element aufbauen
		Element textXML = new Element("Text");
		textXML.setAttribute("Id", iID.toString());
		textXML.setAttribute("Type", strLinkType);
		textXML.setAttribute("LinkType", strLinkType);
		textXML.setAttribute("TypeName", strTypeName);
		textXML.setAttribute("Sequence", iOrder.toString());
		//textXML.setAttribute("LinkTypeName", strTypeName) ;

		// --- <TextBlock Language="German">In stabiler Klapptasche mit Klettverschluss zur praktischen Aufbewahrung.</TextBlock>
		Element inhaltXML = new Element("TextBlock");
		inhaltXML.setAttribute("Language", m_strLanguage);
		//CDATA cdataText = new CDATA(strTagContent);
		strTagContent = strTagContent.replaceAll("##br##", " ");
		strTagContent = strTagContent.replaceAll("##br /##", " ");
		strTagContent = strTagContent.replaceAll("##br/##", " ");
		inhaltXML.addContent(strTagContent);
		textXML.addContent(inhaltXML);

		return textXML;
	}

	/**
	 * 
	 * @param strTypeName
	 * @param strTagContent
	 * @param iOrder
	 * @param iID
	 * @param strLinkType
	 * @param bConvertTag
	 * @return 
	 */
	public Element createTextElement(String strTypeName, String strTagContent, Integer iOrder, Object iID, String strLinkType, boolean bConvertTag) {
		// --- Content bereinigen
		strTypeName = strTypeName.replaceAll("<p>", "");
		strTypeName = strTypeName.replaceAll("</p>", "");
		strTypeName = strTypeName.replaceAll("<b>", "");
		strTypeName = strTypeName.replaceAll("</b>", "");

		// --- Text konvertieren
		if (strTagContent == null) {
			strTagContent = "";
		}
		//System.out.println("v.strTagContent="+strTagContent);
		strTagContent = strTagContent.replaceAll("</", "##e_");
		if (bConvertTag) {
			strTagContent = strTagContent.replaceAll("<", "##");
			strTagContent = strTagContent.replaceAll(">", "##");
		}
/////////////////////////// 
// entfernt template-optimierung					
//		strTagContent = strTagContent.replaceAll("##sup##", "##sub##");
//		strTagContent = strTagContent.replaceAll("##e_sup##", "##e_sub##");
		strTagContent = strTagContent.replaceAll("##sup##", "");
		strTagContent = strTagContent.replaceAll("##e_sup##", "");
/////////////////////////// 
		strTagContent = strTagContent.replaceAll("&quot;", "\"");
		//System.out.println("n.strTagContent="+strTagContent);
		//strTypeName = strTypeName.trim();
		//strTagContent = strTagContent.trim();

		// --- Element aufbauen
		Element textXML = new Element("Text");
		if (iID instanceof Integer) {
			textXML.setAttribute("Id", iID.toString());
		} else {
			if (iID == null) {
				iID = "";
			}
			textXML.setAttribute("Id", iID.toString());
		}
		textXML.setAttribute("Type", strLinkType);
		textXML.setAttribute("LinkType", strLinkType);
		textXML.setAttribute("TypeName", strTypeName);
		textXML.setAttribute("Sequence", iOrder.toString());
		//textXML.setAttribute("LinkTypeName", strTypeName) ;

		// --- <TextBlock Language="German">In stabiler Klapptasche mit Klettverschluss zur praktischen Aufbewahrung.</TextBlock>
		Element inhaltXML = new Element("TextBlock");
		inhaltXML.setAttribute("Language", m_strLanguage);
		//CDATA cdataText = new CDATA(strTagContent);
		strTagContent = strTagContent.replaceAll("##br##", " ");
		strTagContent = strTagContent.replaceAll("##br /##", " ");
		strTagContent = strTagContent.replaceAll("##br/##", " ");
		inhaltXML.addContent(strTagContent);
		textXML.addContent(inhaltXML);

		return textXML;
	}

	/**
	 *
	 * @param strTypeName
	 * @param strTagContent
	 * @param iOrder
	 * @param iID
	 * @param strLinkType
	 * @param bConvertTag
	 * @return
	 */
	public Element createTextElementWithLanguage(String strTypeName, String strTagContent, Integer iOrder, Object iID, String strLinkType, boolean bConvertTag, String strLanguage) {
		// --- Content bereinigen
		strTypeName = strTypeName.replaceAll("<p>", "");
		strTypeName = strTypeName.replaceAll("</p>", "");
		strTypeName = strTypeName.replaceAll("<b>", "");
		strTypeName = strTypeName.replaceAll("</b>", "");

		// --- Text konvertieren
		strTagContent = strTagContent.replaceAll("</", "##e_");
		if (bConvertTag) {
			strTagContent = strTagContent.replaceAll("<", "##");
			strTagContent = strTagContent.replaceAll(">", "##");
		}
///////////////////////////
// entfernt template-optimierung
		strTagContent = strTagContent.replaceAll("##sup##", "");
		strTagContent = strTagContent.replaceAll("##e_sup##", "");
///////////////////////////
		strTagContent = strTagContent.replaceAll("&quot;", "\"");

		// --- Element aufbauen
		Element textXML = new Element("Text");
		textXML.setAttribute("Id", iID.toString());
		textXML.setAttribute("Type", strLinkType);
		textXML.setAttribute("LinkType", strLinkType);
		textXML.setAttribute("TypeName", strTypeName);
		textXML.setAttribute("Sequence", iOrder.toString());

		// --- <TextBlock Language="German">In stabiler Klapptasche mit Klettverschluss zur praktischen Aufbewahrung.</TextBlock>
		Element inhaltXML = new Element("TextBlock");
		inhaltXML.setAttribute("Language", strLanguage);
		strTagContent = strTagContent.replaceAll("##br##", " ");
		strTagContent = strTagContent.replaceAll("##br /##", " ");
		strTagContent = strTagContent.replaceAll("##br/##", " ");
		inhaltXML.addContent(strTagContent);
		textXML.addContent(inhaltXML);

		return textXML;
	}

	/**
	 * 
	 * @param classificationAttribute
	 * @param outputcontrol
	 * @param weramedia
	 * @param strHashCode
	 * @param strDirectory
	 * @param strOrder
	 * @return 
	 */
	protected Element _initMerkmalIcon(ClassificationAttribute classificationAttribute, Outputcontrol outputcontrol, WeraMedia weramedia, String strHashCode, String strDirectory, String strOrder) {

		// --- Korrektur - Alle Grafiken aus einem Bilderordner
		strDirectory = "pictures";

		// --- Initialize
		Element oMotive = new Element("Motive");
		WeraMedia icon = null;
		String strIconCode = strHashCode;
		String strIconRealName = "";
		String strCaCode = "";
		Collection icons = new ArrayList();
		if (classificationAttribute != null) {
			strCaCode = classificationAttribute.getCode();
		}

		// --- �bernehme �bergegebenes Object 
		if (weramedia != null) {
			icons.add(weramedia);
		} else {
			// --- Hole Icon aus Ausgabesteuerung
			if (outputcontrol != null) {
				icons = (Collection) m_wm.getAttribute(outputcontrol, "icons");
			}
			if (icons == null || icons.size() == 0) // --- Fallback aus CA			
			{
				if (classificationAttribute != null) {
					icons = (Collection) m_wm.getAttribute(classificationAttribute, "icons");
				}
			}

		}

		// --- Wurde ein Icon gefunden
		if (icons != null && icons.size() > 0) {

			// --- Initialisiere Daten
			icon = (WeraMedia) icons.iterator().next();
			strIconCode = icon.getCode() + "_" + strOrder;
			strIconRealName = icon.getRealFileName();
		}
		if (strIconRealName != null) {
			strIconRealName = strIconRealName.replaceAll(".jpg", ".eps");
			strIconRealName = strIconRealName.replaceAll(".gif", ".eps");
		}

		// --- Neues Element anlegen
		// --- <Motive Id="0" Type="ATTRIBUTE_IMAGE" LinkType="ATTRIBUTE_IMAGE" RefCode="ATTR_aussen_sechskant_durchmesser" Thumbnail="" Sequence="0" TypeName="ATTRIBUTE_IMAGE" LinkTypeName="ATTRIBUTE_IMAGE" Name="Leertext" Keywords="">
		oMotive.setAttribute("Id", strIconCode);
		oMotive.setAttribute("Type", "ATTRIBUTE_IMAGE");
		oMotive.setAttribute("LinkType", "ATTRIBUTE_IMAGE");
		oMotive.setAttribute("RefCode", strIconCode);
		//oMotive.setAttribute("Thumbnail",    "" );
		oMotive.setAttribute("Sequence", strOrder);
		oMotive.setAttribute("TypeName", strIconRealName);
		oMotive.setAttribute("LinkTypeName", strCaCode);
		oMotive.setAttribute("Name", strIconRealName);
		//oMotive.setAttribute("Keywords",     "" );

		/*		
		 // --- Attribute-Header-List (ICON)
		  if ( strHashCode != null ) {
		  HashMap hashCA = (HashMap)m_hashAttributeHeaderList.get(strHashCode);
		  if ( hashCA != null ) {
		  hashCA.put("FileName","");
		  hashCA.put("PrintFileName","");
		  if ( icon != null ) {
		  hashCA.put("FileName",      icon.getFileName());
		  hashCA.put("PrintFileName", strDirectory + "/" + strIconRealName);
		  m_hashAttributeHeaderList.put ( strHashCode, hashCA );
		  }
		  }
		  }
		 */
		// --- Pr�fe OutputControl
		String stringUnit = "";

		// --- <File-List>
		Element oFileList = new Element("File-List");

		// --- <File Id="0" MimeType="image/gif" Media="INTERNET" FileName="\images\pictos\aussen_sechskant_durchmesser.gif" Thumbnail="" ColorDepth="" Height="" Size="" Software="" Author="" Sequence="10" MediaName="INTERNET" Keywords="">
		Element oFile = _initImage(icon, strDirectory, "", ".eps");
		strIconRealName = oFile.getAttributeValue("PrintFileName");
		oMotive.setAttribute("TypeName", strIconRealName);
		oMotive.setAttribute("Name", strIconRealName);
		oFileList.addContent(oFile);

		// --- Setze zusammen
		oMotive.addContent(oFileList);

		return oMotive;
	}

	/**
	 * Inhalt der S�tze
	 * 
	 * @param weraProductSet
	 * @param oTexList
	 * @param oLocalRefId
	 * @return 
	 */
	public Element createContentElement(WeraProductSet weraProductSet, Element oTexList, Integer oLocalRefId) {

		// --- Initialize
		Item oWeraProduct 					= null;
		Element oFirstElement 				= null;
		WeraMedia icon1 					= null;
		WeraMedia icon2 					= null;
		ArrayList aContent 					= null;
		ArrayList colHash 					= null;
		HashMap oHashMapProdukt 			= null;
		HashMap oHashMapArtikel 			= null;

		// --- Exportsprache sichern
		String strTmpExportLanguage = m_strExportLanguage;

		// --- hole den Inhalt des Satz
		aContent = weraProductSet.generateWeraProductSetData();
		//weraProductSet.debugOutWeraProductSetData();

		String strTypeName = "";
		String strTagContent = "";
		int iPos = 0;
		//Random oRandom = new Random( 100 );
		//int iID = oRandom.nextInt();
		//if ( iID < 0 )
		//	iID *= -1;

		// --- Schleife �ber alle Content-Inhalte
		if (aContent != null && aContent.size() > 0) {
			for (Iterator it1 = aContent.iterator(); it1.hasNext();) {

				// --- Hole Map
				oHashMapProdukt = (HashMap) it1.next();
				iPos++;

				// --- Initialize
				// strTypeName = (String) "TEST:" + oHashMapProdukt.get("code");

// -------------------------------------------------- verwende lokalisierte Artikelnummer in Sätzen
				// --- Exportsprache zurücksetzen
				m_strExportLanguage	= strTmpExportLanguage;
				initLanguage(m_strExportLanguage);

				// --- hole das Produkt des Satzes
				PK oPK 								= (PK) oHashMapProdukt.get("pk");
				oWeraProduct 						= JaloSession.getCurrentSession().getItem(oPK);

				// --- hole die lokalisierte Artikelnummer unter berücksichtigung der Exportsprache
				strTypeName = this.getLocalicedArtNr ( oWeraProduct );

// -------------------------------------------------- verwende lokalisierte Artikelnummer in Sätzen

				icon1 = (WeraMedia) oHashMapProdukt.get("icons1");
				icon2 = (WeraMedia) oHashMapProdukt.get("icons2");
				strTagContent = "";

				colHash = (ArrayList) oHashMapProdukt.get("colHashArtikel");
				if (colHash != null && colHash.size() > 0) {
					int iPos1 = 0;
					for (Iterator it2 = colHash.iterator(); it2.hasNext();) {
						// --- Hole Map
						iPos1++;
						oHashMapArtikel = (HashMap) it2.next();
						if (oHashMapArtikel != null) {
							if (iPos1 < colHash.size()) {
/////////////////////////// 
// entfernt template-optimierung					
//								strTagContent += "<cNoBreak:1>" + oHashMapArtikel.get("value")
//										+ ";<cNoBreak:> ";
/////////////////////////// 
								strTagContent += oHashMapArtikel.get("value") + "; ";
							} else {
/////////////////////////// 
// entfernt template-optimierung					
//								strTagContent += "<cNoBreak:1>" + oHashMapArtikel.get("value")
//										+ "<cNoBreak:>";
/////////////////////////// 
								strTagContent += oHashMapArtikel.get("value");
							}
						}
					}
				}

				// --- Hier korrigieren wir die Dezimalpunkte nach Sprachen
				if (!m_strLanguage.equals("en") && !m_strLanguage.equals("us-en")) {
					strTagContent = strTagContent.replace(".", ",");
				}

				// --- Content korrigieren
				if (strTagContent.length() > 2) {
					strTagContent = strTagContent.trim();
				}

				// --- Formatmarken f�r Indesign
/////////////////////////// 
// entfernt template-optimierung					
//				strTagContent = "<ASCII-WIN>\r\n<Version:4><ParaStyle:links ohne Einzug><CharStyle:wera\\_tabelle>" + strTagContent;
//				strTagContent = strTagContent + "<CharStyle:>";
/////////////////////////// 
				strTagContent = strTagContent;

				// --- Hochstellen des Registerzeichens
/////////////////////////// 
// entfernt template-optimierung					
//				strTypeName = strTypeName.replace("\u00ae", "##sub##" + "\u00ae" + "##e_sub##");
//				strTagContent = strTagContent.replace("\u00ae", "##sub##" + "\u00ae" + "##e_sub##");
/////////////////////////// 

				// --- Zusammenhalten einer Zeile
				Element textXML = _createTextElement(strTypeName, strTagContent, new Integer(iPos), new Integer(oLocalRefId.intValue() + iPos - 1), "BLT_SET", false);
				m_iOffsetIDEbene3++;
				oTexList.addContent(textXML);

				///////////////////////////////////////////////////////////////////////
				// --- Default oder lokalisierte Ausgabe
				boolean isLocalized			= false;
				String strLocalicedArtNrVgl	= null;

				// --- schleife über alle sprachen
				for ( String language : m_languages ) {

					// --- sprache setzen
					m_strExportLanguage = language;
					initLanguage(language);

					// --- hole die lokalisierte Artikelnummer unter berücksichtigung der Exportsprache
					String strLocalicedArtNr 	= this.getLocalicedArtNr ( oWeraProduct );
					strLocalicedArtNr			= strLocalicedArtNr.trim();

					///// Prüfung ob die Artikelnummer in den Sprachen einheitlich ist, oder ob sie sich ändert ////
					// --- Artikelnummer beim ersten Mal merken
					if ( strLocalicedArtNrVgl == null ) {

						strLocalicedArtNrVgl	 = strLocalicedArtNr;

						// --- Textelement einbauen - default
						Element textTitle	= new Element("TextTitle");
						textTitle.setAttribute("Language", "default" );
						textTitle.addContent(strLocalicedArtNr);
						textXML.addContent(textTitle);
					}
					// --- prüfen, ob sich die Artikelnummer ändert
					if ( !strLocalicedArtNr.equals(strLocalicedArtNrVgl) ) {
						// --- Artikelnummern sind in den Sprachen unterschiedlich
						isLocalized	= true;
					}
					///// Prüfung ob die Artikelnummer in den Sprachen einheitlich ist, oder ob sie sich ändert ////

					// --- Textelement einbauen
					Element textTitle	= new Element("TextTitle");
					textTitle.setAttribute("Language", language );
					textTitle.addContent(strLocalicedArtNr);
					textXML.addContent(textTitle);

				} // for ( String language : m_languages ) {

				// --- Flag setzen ob, die Stüclliste dieses Artikels lokalisierte Artikelnummern enthält
				textXML.setAttribute( "isLocalizedSTL", isLocalized ? "JA" : "NEIN" );
				///////////////////////////////////////////////////////////////////////

				// --- 1. Textelement merken
				if (oFirstElement == null) {
					oFirstElement = textXML;
				}

				// --- Icon 1 
				if (icon1 != null) {
					Element oIcon1 = _initMerkmalIcon(null, null, icon1, null, "icons", "1");
					oIcon1.setAttribute("Sequence", "1");
					textXML.addContent(oIcon1);
				}

				// --- Icon 2 
				if (icon2 != null) {
					Element oIcon2 = _initMerkmalIcon(null, null, icon2, null, "icons", "2");
					oIcon2.setAttribute("Sequence", "2");
					textXML.addContent(oIcon2);
				}

			} // --- for ( Iterator it1 = aContent.iterator(); it1.hasNext(); ) {

		} // --- if ( aContent != null && aContent.size() > 0 ) {

		// --- Exportsprache zurücksetzen
		m_strExportLanguage	= strTmpExportLanguage;
		initLanguage(m_strExportLanguage);

		return oFirstElement;
	}

	/**
	 * BLT_DISPLAYSET: Display Produkttitle hinzufügen Preislistenversion
	 *
	 * @param paramProduct
	 */
	protected void setDisplayTitle(Item paramProduct, Object iID, String strLinkType, String quantityPrefix ) {

		// --- preset
		Item product			= null;
		String strAttributeKey	= "";
		String strProduktName	= "";
		String strProduktNameEN	= "";

		// --- Element aufbauen
		Element textXML = new Element("Text");
		textXML.setAttribute("Id", iID.toString() );
		textXML.setAttribute("Type", strLinkType);
		textXML.setAttribute("LinkType", strLinkType );
		m_oTextList.addContent(textXML);

		// --- sonderloesung (fusch) -----------------------------------

		// --- hole vom Original Produkt aus dem Feld name
		product			= paramProduct;
		strAttributeKey	= "name";

		// --- ist 9840 oder 9850 und variante dann hole das Basisprodukt
		if ( paramProduct instanceof WeraVariante ) {

			Item productBase		= (WeraProduct) m_wm.getAttribute(paramProduct, "baseproduct" );
			String strProduktCode 	= (String) m_wm.getAttribute(productBase, "code");

			if ( strProduktCode.equals("9840") || strProduktCode.equals("9850") ) {
				// --- Wert vom Basisprodukt aus dem Feld artikelnr_index holen
				strAttributeKey	= "artikelnr_index";
				product			= productBase;
			}

		}
		// --- sonderloesung (fusch) -----------------------------------

		// --- schleife über alle sprachen
		for (String language : m_languages) {

			// --- sprache setzen
			initLanguage(language);

			// --- produktname holen
			strProduktName = (String) m_wm.getAttribute(product, strAttributeKey );
			if (strProduktName == null) {
				strProduktName = "??";
			}

			// --- Setze Sprache, und De
			if ( language.equals("de") ) {

				// --- hole name in en
				initLanguage("en");
				strProduktNameEN = (String) m_wm.getAttribute(product, strAttributeKey );

				if (strProduktNameEN == null) {
					strProduktNameEN = "??";
				}
				if ( !strProduktName.trim().toLowerCase().equals(strProduktNameEN.trim().toLowerCase()) ) {
					strProduktName = strProduktName + " /\n" + strProduktNameEN;
				}
			}
			if ( quantityPrefix == null ) {
				quantityPrefix	= "";
			}

			// --- <TextBlock Language="German">Title</TextBlock>
			Element inhaltXML = new Element("TextBlock");
			inhaltXML.setAttribute("Language", language);
			inhaltXML.addContent( quantityPrefix + strProduktName);
			textXML.addContent(inhaltXML);

		} // for (String language : Collection<String> languages) {

		// --- sprache zurücksetzen
		initLanguage(m_strExportLanguage);
	}

	/**
	 * Inhalt der SIS (Wrapper)
	 * 
	 * @param weraProductSet
	 * @param oTexList
	 * @param oLocalRefId
	 * @param nCountProductSet
	 * @return 
	 */
	public Element createContentElementSetinSet(WeraProductSet weraProductSet, Element oTexList, Integer oLocalRefId, int nCountProductSet) {
		m_nCountProductSet = nCountProductSet;
		// LOG.info("createContentElementSetinSet V1 - m_nCountProductSet=" + m_nCountProductSet);
		return this.createContentElementSetinSet(weraProductSet, oTexList, oLocalRefId);
	}

	/**
	 * Inhalt der SIS
	 * 
	 * @param weraProductSet
	 * @param oTexList
	 * @param oLocalRefId
	 * @return 
	 */
	public Element createContentElementSetinSet(WeraProductSet weraProductSet, Element oTexList, Integer oLocalRefId) {

		// --- Initialize
		LOG.info("createContentElementSetinSet V2 - m_nCountProductSet=" + m_nCountProductSet);
		Element oFirstElement = null;
		WeraMedia icon1 = null;
		WeraMedia icon2 = null;
		ArrayList aContent = null;
		ArrayList colHash = null;
		g_oHashMapCodeNr = new HashMap();
		HashMap oHashMapProdukt = null;
		HashMap oHashMapArtikel = null;
		aContent = weraProductSet.generateWeraProductSetData();
		String strTypeNameProduktCode = "";
		String strTypeName = "";
		String strTagContent = "";
		int iPos = 0;
		g_strAbmessung = "";

		// --- pr�fe auf Display
		Boolean bIstDisplay = null;
		if (weraProductSet instanceof WeraProductSetinSet) {
			bIstDisplay = (Boolean) m_wm.getAttribute(weraProductSet, "ist_display");
		}
		if (bIstDisplay == null) {
			bIstDisplay = new Boolean(false);
		}

		//Random oRandom = new Random( 100 );
		//int iID = oRandom.nextInt();
		//if ( iID < 0 )
		//	iID *= -1;
		// --- Schleife �ber alle Content-Inhalte
		if (aContent != null && aContent.size() > 0) {
			for (Iterator it1 = aContent.iterator(); it1.hasNext();) {

				// --- Hole Map
				oHashMapProdukt = (HashMap) it1.next();

				// --- hole das Produkt des Satzes
				PK oPK 									= (PK) oHashMapProdukt.get("pk");
				Item itemWeraProductSet					= JaloSession.getCurrentSession().getItem(oPK);

//////// DEBUG
// LOG.info("createContentElementSetinSet weraProductSet.getCode()=" + weraProductSet.getCode() );
// LOG.info("createContentElementSetinSet (String) oHashMapProdukt.get(code)=" + (String) oHashMapProdukt.get("code") );

// String strTypeNameProduktCode1 = this.getLocalicedArtNr ( (Item) weraProductSet );
// String strTypeNameProduktCode2 = this.getLocalicedArtNr ( itemWeraProductSet );
// LOG.info("createContentElementSetinSet getLocalicedArtNr (weraProductSet)=" + strTypeNameProduktCode1 );
//LOG.info("createContentElementSetinSet getLocalicedArtNr (itemWeraProductSet)=" + strTypeNameProduktCode2 );
//////// DEBUG

				// --- TypeName
				strTypeName = "";
				if (bIstDisplay.booleanValue()) {

					// --- Display-Darstellung
					if (iPos == 0) {
						strTypeName = (String) weraProductSet.getCode();
					}
					// strTypeNameProduktCode = (String) oHashMapProdukt.get("code");

					// --- Holen der lokalisierten Artikelnummer "artikelnr_index" von einem Item beliebigen Typs
// LOG.info("createContentElementSetinSet V1=" );
					strTypeNameProduktCode = this.getLocalicedArtNr ( itemWeraProductSet );

				} else {

					// --- SB-Darstellung
					if (m_nCountProductSet == 1) {

						// --- 1 x n
						// --- Holen der lokalisierten Artikelnummer "artikelnr_index" von einem Item beliebigen Typs
// LOG.info("createContentElementSetinSet V2=" );
						strTypeNameProduktCode = this.getLocalicedArtNr ( itemWeraProductSet );
/*
						// --- 1 x n
						strTypeName = (String) weraProductSet.getCode();
						strTypeNameProduktCode = (String) oHashMapProdukt.get("code");
*/

					} else {
// LOG.info("createContentElementSetinSet V3=" );

						// --- Holen der lokalisierten Artikelnummer "artikelnr_index" von einem Item beliebigen Typs
						strTypeNameProduktCode = this.getLocalicedArtNr ( (Item) weraProductSet );
// LOG.info("createContentElementSetinSet V3 (neu)=" + strTypeNameProduktCode );
//LOG.info("createContentElementSetinSet V3 (alt)=" + (String) weraProductSet.getCode() );
/*
						// --- n x n
						strTypeName = (String) oHashMapProdukt.get("code");
						strTypeNameProduktCode = (String) weraProductSet.getCode();

						// --- hier korrektur Artikelnummer, ggf. bei Fehler nochmal prüfen
						strTypeNameProduktCode 	= strTypeName;
*/
					}
				}

				// --- Initialize
				iPos++;
				//strTypeName   = (String) oHashMapProdukt.get("code");
                        // LOG.info ( "super.createContentElementSetinSet Satz.getCode()=" + strTypeName);
                        //LOG.info ( "super.createContentElementSetinSet Produkt.getCode()=" + oHashMapProdukt.get("code"));
				g_oHashMapCodeNr.put(iPos, oHashMapProdukt.get("code"));

				// --- icons
				icon1 = (WeraMedia) oHashMapProdukt.get("icons1");
				icon2 = (WeraMedia) oHashMapProdukt.get("icons2");
				strTagContent = "";
				g_oHashMapCodeNr.put(iPos, oHashMapProdukt.get("code"));

				colHash = (ArrayList) oHashMapProdukt.get("colHashArtikel");
				if (colHash != null && colHash.size() > 0) {
					int iPos1 = 0;
					for (Iterator it2 = colHash.iterator(); it2.hasNext();) {
						// --- Hole Map
						iPos1++;
						oHashMapArtikel = (HashMap) it2.next();
						if (oHashMapArtikel != null) {
							if (iPos1 < colHash.size()) {
/////////////////////////// 
// entfernt template-optimierung					
//								strTagContent += "<cNoBreak:1>" + oHashMapArtikel.get("value")
//										+ ";<cNoBreak:> ";
/////////////////////////// 
								strTagContent += oHashMapArtikel.get("value") + "; ";
							} else {
/////////////////////////// 
// entfernt template-optimierung					
//								strTagContent += "<cNoBreak:1>" + oHashMapArtikel.get("value")
//										+ "<cNoBreak:>";
/////////////////////////// 
								strTagContent += oHashMapArtikel.get("value");
							}
						}
					}
				}

				// --- Hier korrigieren wir die Dezimalpunkte nach Sprachen
				if (!m_strLanguage.equals("en") && !m_strLanguage.equals("us-en")) {
					strTagContent = strTagContent.replace(".", ",");
				}

				// --- Content korrigieren
				if (strTagContent.length() > 2) {
					strTagContent = strTagContent.trim();
				}

				// --- Hochstellen des Registerzeichens
/////////////////////////// ASCII-WIN
// entfernt template-optimierung					
				//strTypeNameProduktCode = strTypeName.replace("\u00ae", "##sub##" + "\u00ae" + "##e_sub##");
				//strTypeName = strTypeName.replace("\u00ae", "##sub##" + "\u00ae" + "##e_sub##");
				//strTagContent = strTagContent.replace("\u00ae", "##sub##" + "\u00ae" + "##e_sub##");
/////////////////////////// 

				// --- Abmessung merken
				g_strAbmessung += strTagContent;
				if (iPos < aContent.size()) {
					g_strAbmessung += "\r\n";
				}

				// --- Formatmarken f�r Indesign
/////////////////////////// 
// entfernt template-optimierung					
//				strTagContent = "<ASCII-WIN>\r\n<Version:4><ParaStyle:links ohne Einzug><CharStyle:wera\\_tabelle>" + strTagContent;
//				strTagContent = strTagContent + "<CharStyle:>";
/////////////////////////// 
				strTagContent = strTagContent;

				// --- Zusammenhalten einer Zeile 
				//Element textXML = _createTextElement ( strTypeName, strTagContent, new Integer(iPos), oLocalRefId, "BLT_SET", false );
				Element textXML = _createTextElement(strTypeNameProduktCode, strTagContent, new Integer(iPos), new Integer(oLocalRefId.intValue() + iPos - 1), "BLT_SET", false);
				m_iOffsetIDEbene3++;
				oTexList.addContent(textXML);

				///////////////////////////////////////////////////////////////////////
				// --- Default oder lokalisierte Ausgabe
				boolean isLocalized			= false;
				String strLocalicedArtNrVgl	= null;

				// --- schleife über alle sprachen
				for ( String language : m_languages ) {

					// --- sprache setzen
					m_strExportLanguage = language;
					initLanguage(language);

					// --- hole die lokalisierte Artikelnummer unter berücksichtigung der Exportsprache
					// String strLocalicedArtNr 	= this.getLocalicedArtNr ( oWeraProduct );
					String strLocalicedArtNr 	= this.getLocalicedArtNr ( itemWeraProductSet );
					strLocalicedArtNr			= strLocalicedArtNr.trim();

					///// Prüfung ob die Artikelnummer in den Sprachen einheitlich ist, oder ob sie sich ändert ////
					// --- Artikelnummer beim ersten Mal merken
					if ( strLocalicedArtNrVgl == null ) {

						strLocalicedArtNrVgl	 = strLocalicedArtNr;

						// --- Textelement einbauen - default
						Element textTitle	= new Element("TextTitle");
						textTitle.setAttribute("Language", "default" );
						textTitle.addContent(strLocalicedArtNr);
						textXML.addContent(textTitle);
					}
					// --- prüfen, ob sich die Artikelnummer ändert
					if ( !strLocalicedArtNr.equals(strLocalicedArtNrVgl) ) {
						// --- Artikelnummern sind in den Sprachen unterschiedlich
						isLocalized	= true;
					}
					///// Prüfung ob die Artikelnummer in den Sprachen einheitlich ist, oder ob sie sich ändert ////

					// --- Textelement einbauen
					Element textTitle	= new Element("TextTitle");
					textTitle.setAttribute("Language", language );
					textTitle.addContent(strLocalicedArtNr);
					textXML.addContent(textTitle);

				} // for ( String language : m_languages ) {

				// --- Flag setzen ob, die Stüclliste dieses Artikels lokalisierte Artikelnummern enthält
				textXML.setAttribute( "isLocalizedSTL", isLocalized ? "JA" : "NEIN" );
				///////////////////////////////////////////////////////////////////////


				// --- 1. Textelement merken
				if (oFirstElement == null) {
					oFirstElement = textXML;
				}

				// --- Icon 1 
				if (icon1 != null) {
					Element oIcon1 = _initMerkmalIcon(null, null, icon1, null, "icons", "1");
					oIcon1.setAttribute("Sequence", "1");
					textXML.addContent(oIcon1);
				}

				// --- Icon 2 
				if (icon2 != null) {
					Element oIcon2 = _initMerkmalIcon(null, null, icon2, null, "icons", "2");
					oIcon2.setAttribute("Sequence", "2");
					textXML.addContent(oIcon2);
				}

			} // --- for ( Iterator it1 = aContent.iterator(); it1.hasNext(); ) {

		} // --- if ( aContent != null && aContent.size() > 0 ) {

		return oFirstElement;
	}


	/**
	 * Holen der lokalisierten Artikelnummer "artikelnr_index" von einem Item beliebigen Typs
	 * Berücksichten der Exportsprache.
	 * Bei "de" wird de / en zurückgegben sofern diese variieren
	 *
	 * @param Item weraProduct
	 * @return String strArtNr
     */
	public String getLocalicedArtNr ( Item weraProductParam ) {

		// --- preset
		Item weraProduct 	= null;
		String strArtNr		= "";

		// --- prüfe ob das weraProduct eine Varinate, dann verwende das BasisPtodukt
		if ( weraProductParam instanceof WeraVariante ) {

			// --- hole basisprodukt
			weraProduct = (Item) m_wm.getAttribute(weraProduct, "baseproduct");

		} else {

			weraProduct	= (Item) weraProductParam;
		}

		// --- hole neutrale Artikel EN
		String artikelnr_indexExportLanguage 	= (String) m_wm.getAttribute(weraProduct, "artikelnr_index" );
		artikelnr_indexExportLanguage	= artikelnr_indexExportLanguage.trim();
		if ( artikelnr_indexExportLanguage == null ) { artikelnr_indexExportLanguage = ""; }
		String artikelnr_indexEN 				= artikelnr_indexExportLanguage;

		if ( m_strExportLanguage.equals("de") ) {

			// --- Setze Sprache, und Defaultsprache=de
			initLanguage("en");

			// --- hole neutre Artikel EN
			artikelnr_indexEN 	= (String) m_wm.getAttribute(weraProduct, "artikelnr_index" );

			// --- setze alte spreache
			initLanguage( m_strExportLanguage);
		}
		artikelnr_indexEN	= artikelnr_indexEN.trim();

		// --- vergleiche de / en Version
		if ( !artikelnr_indexExportLanguage.equals(artikelnr_indexEN) ) {
			// --- localisierte Arikelnummer EN anfügen
			strArtNr	= artikelnr_indexExportLanguage + " / "  + artikelnr_indexEN;
		} else {

			// --- lokalisierte Artikelnummer nur Exportsprache
			strArtNr	= artikelnr_indexExportLanguage;
		}

		return strArtNr;
	}
}
