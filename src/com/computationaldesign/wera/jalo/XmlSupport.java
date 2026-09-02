package com.computationaldesign.wera.jalo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.c2l.LocalizableItem;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.util.Config;
import java.util.Map;
import java.util.Set;

import org.jibble.simpleftp.*;

class StreamGobbler extends Thread
{
    InputStream is;
    String type;

    StreamGobbler(InputStream is, String type)
    {
        this.is = is;
        this.type = type;
    }

    public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null)
                System.out.println (type + ">" + line);
            } catch (IOException ioe)
              {
                ioe.printStackTrace();
              }
    }
}

public class XmlSupport {

	/** Edit the local|project.properties to change logging behavior (properties 'log4j.*'). */
	private static final Logger LOG = Logger.getLogger(XmlSupport.class.getName());
	
	class OrderComparator implements Comparator{
		public int compare(Object o1, Object o2) /*descending order*/  {

			// --- Initialize
			Integer iValue1 = new Integer(0);
			Integer iValue2 = new Integer(0);
			int iResult =  0;

			try {
				// --- Hole Values
				iValue1 = (Integer) ((Item) o1).getAttribute("order");
				iValue2 = (Integer) ((Item) o2).getAttribute("order");
				
			} catch (JaloSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if ( iValue1 == null || iValue2 == null )
				return 0;
			else
			   return iValue1.compareTo(iValue2);
		}
	}
	class StringComparator implements Comparator{
		public int compare(Object o1, Object o2) /*descending order*/  {

			if ( o1 == null || o2 == null )
				return 0;
			else
			   return o1.toString().compareTo(o2.toString());
		}
	}

	// --- Member
	public WeraManager m_wm = null;
	public JaloSession m_jaloSession = null;

	protected Language m_Language;
	protected String m_strLanguage;
	protected Language p_oDefaultLanguage = null;
	protected String m_strDTD_typ = null;
	protected String m_strDTD_name = null;
	

	
	// --- Knotenelemente
	Document m_oXmlDocument =  null;
	public Element m_rootElement = null;

	
	
	public XmlSupport() {
		super();
		// TODO Auto-generated constructor stub

		// --- Initialize
		m_wm = WeraManager.getInstance();
		m_jaloSession = JaloSession.getCurrentSession();
	}
        
        
        /**
         * generates a simple xml-list of a given ItemTyp, representing a list of attributes
         * @param oItemType
         * @param oNodeList
         * @param aAttributeList
         * @param strNodeName
         * @return Element oNodeList
         */
	public Element GenerateSimpleNodeList ( ComposedType oItemType, Element oNodeList, HashMap<String, Object> hashAttributeList, HashMap<String, Boolean> hashMandatory, String strNodeName ) {
            
            // --- initialiize
            Object oAttributeItemsXML       = "";
            String strAttributeExportXML    = "";
            boolean bOutputNodeItem         = true;

            
            // --- get all item instances
            final Set<Item> oItems = oItemType.getAllInstances();
            
            // --- iterate our item-list
            for ( final Item oItem : oItems ) { 
                
                // --- some save stuff here
                if ( hashAttributeList != null  ) {
                    
                    // --- initialize
                    Object oAtributeValue = null;
                    
                    // --- generate new XML-Node
                    final Element oNodeItem = new Element(strNodeName);
                    
                    // --- output this Item by default
                    bOutputNodeItem = true;
                    
                    // --- iterate at attribute-list
                    for ( final Map.Entry<String, Object> oEntry : hashAttributeList.entrySet() ) { 
                        
                        // --- get attribute-keys
                        strAttributeExportXML   = oEntry.getKey();
                        oAttributeItemsXML      = oEntry.getValue();
                        
                        // --- check of attritbute-list
                        if ( oAttributeItemsXML instanceof Collection ) {
                            
                            // --- iterate at list off attrbute-names
                            Object oCurrentItem = oItem;
                            for ( final String strAttributeName : ((Collection<String>)oAttributeItemsXML) ) { 

                                oCurrentItem = (Object)m_wm.getAttribute((Item)oCurrentItem, strAttributeName);
                                if ( oCurrentItem == null  ) {

                                    // --- attrbiute not found
                                    LOG.error( strNodeName + " - export, attrbute " + strAttributeName + " not found!!!" );
                                    break;

                                } else {
                                    // --- element is a collection, then get the first item
                                    //     TODO: special handling of a collection
                                    if ( oCurrentItem instanceof Collection )
                                        if ( ((Collection)oCurrentItem).size() != 0 )
                                            oCurrentItem = ((Collection)oCurrentItem).iterator().next();
                                }

                            } // --- for ( final String strAttributeName : ((Collection<String>)oAttributeItemsXML) ) { 

                            // --- get item value
                            if ( oCurrentItem != null )
                                oAtributeValue = (Object)oCurrentItem.toString();
                            
                        } else {

                            // --- get item value
                            // oAtributeValue = (Object)m_wm.getAttribute((Item)oItem, (String) oAttributeItemsXML);
                            oAtributeValue = (Object)m_wm.getAttribute((Item)oItem, (String) oAttributeItemsXML);
                            
                        }
                        

                        // --- Pflichfeld?
                        if ( hashMandatory.get (strAttributeExportXML) != null ) {
                            boolean bMandatoryAttribute = hashMandatory.get (strAttributeExportXML).booleanValue();
                            if ( bMandatoryAttribute && (oAtributeValue == null || oAtributeValue.equals("")) ) {
                                // --- one mandatory item not found, we break here and skip this NodeItem
                                LOG.info ( "+++ Attribute " + strAttributeExportXML + " ist mandatory but not defined (break).");
                                bOutputNodeItem = false;
                                break;
                            }
                        }
                        
                        // --- an other save stuff here
                        if ( oAtributeValue == null  ) oAtributeValue = "";
                        
                        // --- append attribute to current NodeItem
                        oNodeItem.setAttribute( strAttributeExportXML, oAtributeValue.toString() );
                        
                    } // --- for ( final String strAttributeName : aAttributeList ) { 

                    
                    // --- append to node-list
                    if ( bOutputNodeItem )
                        oNodeList.addContent(oNodeItem);
                    else 
                        LOG.info ( "+++ Element " + strNodeName + " - skipped." );
                    
                } // --- if ( aAttributeList != null  ) {
                
            } // --- for ( final Item oItem : oItems ) { 
            
            return oNodeList;
	}
        
    protected String getValidString ( String strString )
    {
    	if ( strString == null )
    		return "";
    	else
    		return strString;
    }	
	// --- Setzen der Sprache
	public void initLanguage ( String strLanguage ) {
		
		// --- Setze Sprache, und Defaultsprache=de
		m_strLanguage  = strLanguage;
		m_Language = m_jaloSession.getC2LManager().getLanguageByIsoCode( strLanguage );
		p_oDefaultLanguage = m_jaloSession.getSessionContext().getLanguage();
		m_jaloSession.getSessionContext().setLanguage(m_Language);
		m_jaloSession.setAttribute(LocalizableItem.LANGUAGE_FALLBACK_ENABLED, Boolean.TRUE);
	}

	// Zurücksetzen der Sprache, Aaufräumen
	public void cleanUp () {
		
		// --- Zurücksetzen der Sprache
		if ( p_oDefaultLanguage != null )
			m_jaloSession.getSessionContext().setLanguage(p_oDefaultLanguage);

		// --- XML-Knoten zurücksetzen
		m_rootElement = null;
	}

	protected void initDTD(String strDTD_typ, String strDTD_name) {
		// TODO Auto-generated method stub
		m_strDTD_typ = strDTD_typ;
		m_strDTD_name = strDTD_name;
	}

	protected Element oGetNewElement( String strElementName, String strContent )
	{
		// --- Anlegen des Elements
		Element oElement = new Element(strElementName);
		oElement.addContent(strContent);

		return oElement;
	}
	
	public void writeDocument ( String strXmlPath, String strXmlFile  ) {
		
		// --- Debug
		LOG.info ("Ausgabe in Datei  =" + strXmlPath + strXmlFile);
		
		// --- Document erzeugen und schreiben
		m_oXmlDocument = new Document(m_rootElement);
		
		// --- DTD setzen
		if ( m_strDTD_typ != null ) {
			DocType xhtml = new DocType( m_strDTD_typ, m_strDTD_name );
			m_oXmlDocument.setDocType(xhtml);
		}
		
		// --- Formatierung
		XMLOutputter outp = new XMLOutputter(Format.getPrettyFormat());
		FileOutputStream out;
		
		try {
			out = new FileOutputStream(strXmlPath + strXmlFile, false );
			outp.output( m_oXmlDocument, out );
			out.close();
			out = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public boolean bFTP_Transfer ( String strServer, String strUser, String strPW, 
			String strDestPath, String strFile ) {
		
		// --- Initialize
		boolean bResult = true;
		
		// --- Ausgabe
		LOG.info ("FTP-Trasnfer");
		LOG.info ("strServer="   + strServer);
		LOG.info ("strDestPath=" + strDestPath);
		LOG.info ("FTP-strFile=" + strFile);
		
		
		// --- Bilder umkopieren
		// --- FTP-Transfer
		try {
			SimpleFTP ftp = new SimpleFTP();
			
			// Connect to an FTP server on port 21.
			ftp.connect(strServer, 21, strUser, strPW);
			
			// Set binary mode.
			ftp.bin();
			
			// Change to a new working directory on the FTP server.
			ftp.cwd(strDestPath);
			
			// Upload some files.
			ftp.stor(new File(strFile));
			
			// Quit from the FTP server.
			ftp.disconnect();
		}
		catch (IOException e) {
			// Jibble.
			e.printStackTrace();
			bResult = false;
		}
		
		
		return bResult;
	}
	   // --- Ausführen der CMD-Datei
	public void startCmdFile( String stringCmd )
	{
		try
		{
   	        // --- Hole Commando für Betriebssystem
   	        Pattern p = Pattern.compile( "[ ]" );
            String arrayProcessString[] = p.split ( stringCmd );
 		    LOG.info ( "stringCmd="  + stringCmd );

            // --- CMD-File ausführen
			Process send = Runtime.getRuntime().exec( arrayProcessString );

            // --- read Input
			BufferedReader in = new BufferedReader(
			  new InputStreamReader(send.getInputStream()) );
			for ( String s; (s = in.readLine()) != null; )
				  LOG.info ( s );
			
	        // any error message?
	        StreamGobbler errorGobbler = new
	        StreamGobbler(send.getErrorStream(), "ERROR");

	        // any output?
	        StreamGobbler outputGobbler = new
	        StreamGobbler(send.getInputStream(), "OUTPUT");

	        // kick them off
	        errorGobbler.start();
	        outputGobbler.start();
			// --- Ende
			int returnCode = send.waitFor();
			LOG.info ( "Rückgabewert: " + returnCode );
			LOG.info ("Ready." );
		}
		catch (InterruptedException e)
		{
			LOG.info (e.toString());
		}
		catch (IOException e)
		{
			LOG.info (e.toString());
		}
	}
	public void _WriteFileFromHash(ArrayList acsv_output, HashSet headerSet, String strFile, String m_strDelim, String m_strSeparater) {
		// TODO Auto-generated method stub
        
		// --- Initalize
		String strKey = "";
		String strValue = "";
		HashMap hLine = new HashMap();
        ArrayList aArrayLog = new ArrayList();
        String strLine = "";

        // --- Sortiere die Header
        ArrayList aHeaders = new ArrayList();
        aHeaders.addAll(headerSet);
        StringComparator oStringComparator = new StringComparator();
        Collections.sort((List) aHeaders, oStringComparator);
        
		// --- Schleife über aller Zeilen
		for ( Iterator it1 = acsv_output.iterator(); it1.hasNext(); ) {

			// --- Hole das aktuelle Datenhash
			strLine = "";
			hLine   = (HashMap) it1.next();
			
			// --- Schleife über alle Keys;
			for ( Iterator it2 = aHeaders.iterator(); it2.hasNext(); ) {
				
				// --- Hole einen Key
				strKey = (String)it2.next();
				if ( hLine.containsKey(strKey) )
					strValue = (String) hLine.get(strKey);
				else
					strValue = "";
				
				// --- Baue die Zeile zusammen
				strLine += m_strSeparater + m_strDelim + strValue + m_strDelim;
			}
			
			// --- Zeile für Ausgabe merken
			aArrayLog.add( strLine.substring(1) );
		}
		
		// --- Ausgabe der Daten
		if ( aArrayLog.size() > 0 )
		   _WriteFileFromArrayEncoding ( aArrayLog, strFile, "UTF-8" );
	}
	
	public void _WriteFileFromArray( ArrayList aArrayLog, String strFile ) {
		
		_WriteFileFromArrayEncoding( aArrayLog, strFile,  null );
	}
	
	public void _WriteFileFromArrayEncoding( ArrayList aArrayLog, String strFile, String strEncoding ) {
		
		// --- Initializee
		String strLine = "";
		
		// --- Verzeichnisnamen anpassen falls erforderlich
		if ( Config.getParameter("wera.os").equals("linux") ) { 
			strFile=strFile.replace("\\","/");
			strFile=strFile.replace("\\","/");
		}
		else { 
			strFile=strFile.replace("/","\\");
			strFile=strFile.replace("/","\\");
		}
		
		// --- LOG-File
		try {
			// --- Öffnen der LOG-Datei
			BufferedWriter oFileWriterLog = null;
			if ( strEncoding == null ) 
			   oFileWriterLog = new BufferedWriter (new FileWriter ( strFile ));
			else
			   oFileWriterLog = new BufferedWriter (new OutputStreamWriter (new FileOutputStream (strFile), strEncoding)); 
			LOG.info ("Datei wird geschrieben..., " + aArrayLog.size() + " Zeilen.");
			if ( oFileWriterLog != null && aArrayLog.size() > 0 ) {
			   
				// ---- Schleife über alle Zeilen
				for ( Iterator it1 = aArrayLog.iterator(); it1.hasNext(); ) {
					strLine = (String) it1.next();
					oFileWriterLog.write(strLine + "\r\n");
				}
				
				// --- Schliesen
				oFileWriterLog.flush();
				oFileWriterLog.close();

				// --- Log löschen
				aArrayLog.clear();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    // --- Verschieben einer Datei
    boolean _bMoveFile ( String strFile, String strDirectory ){
	 
	    // File (or directory) to be moved
	    File file = new File(strFile);
	    
	    // Destination directory
	    File dir = new File(strDirectory);
	    
	    // Move file to new directory
	    return file.renameTo(new File(dir, file.getName()));
     }
}
