package com.computationaldesign.wera.jalo;

import de.hybris.platform.util.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;



class classXmlSupport
{
	final static String m_CLASSNAME = "classXmlSupport";

	private Element m_oRootNode = null;
	private Document m_oDocument = null;
	private String m_stringXmlFilename;

	static String getClassName()
	{
		return "[" + m_CLASSNAME + "] ";
	}

	// --- Return Document
	public Document getDocument()
	{
		return m_oDocument;
	}

	// --- Return RootNode
	public Element getRootNode()
	{
		return m_oRootNode;
	}

	// --- Hole eine Liste aus der aktuellen Ebene
	public Iterator getXmlListe(Element oNote, final String stringChild)
	{
		// --- Initialize
		Iterator oListe = null;

		// --- Start-Node
		if (oNote == null)
		{
			oNote = m_oRootNode;
		}

		// --- Hole Liste
		if (oNote != null)
		{
			oListe = oNote.getChildren(stringChild).iterator();
		}

		return oListe;
	}

	// --- Suche ein Knoten-Element auf der aktuellen Ebene
	public Element getXmlElement(Element oNote, final String stringChild, final String stringValue)
	{
		// --- Initialize
		Element oElementRes = null;

		// --- Start-Node
		if (oNote == null)
		{
			oNote = m_oRootNode;
		}

		// --- Loop
		if (oNote != null)
		{
			final Iterator oListe = getXmlListe(oNote, stringChild);
			while (oListe.hasNext())
			{
				final Element oElement = (Element) oListe.next();
				if (stringValue.equals(oElement.getAttribute("id").getValue()))
				{
					// --- Hole Element und breche Bearbeitung ab
					oElementRes = oElement;
					break;
				}

			} // --- while ( oListe.hasNext() )

		} // --- if ( oNote != null )

		return oElementRes;
	}


	// --- Öffen der XML-Datei
	public Element openXmlFile(final String stringXmlFilename)
	{
		// --- Initialize
		m_oRootNode = null;
		m_oDocument = null;

		try
		{
			// --- Open XML-File
			final SAXBuilder builder = new SAXBuilder();
			m_oDocument = builder.build(new File(stringXmlFilename));

			// -- Get the root element
			m_oRootNode = m_oDocument.getRootElement();
			m_stringXmlFilename = stringXmlFilename;
		}
		catch (final Exception e)
		{
			System.out.println(getClassName() + " " + e.toString());
		}

		return m_oRootNode;
	}

	// --- Schreibe die XML-Datei zurück
	public void writeXMLFile()
	{
		final XMLOutputter oXMLOutputter = new XMLOutputter();
		try
		{
			final FileOutputStream oOutPutStream = new FileOutputStream(m_stringXmlFilename);
			oXMLOutputter.output(getDocument(), oOutPutStream);
		}
		catch (final Exception e)
		{
			System.out.println(getClassName() + " " + e.toString());
		}
	}

}


class classImageJob
{
	final static String m_CLASSNAME = "classImageJob";
	private static Logger LOG = Logger.getLogger(classImageJob.class.getName());

	protected String m_stringCmdFile = "tmp.cmd";
	protected String m_stringJobFile = "imagejobs.xml";
	protected String m_stringSystem = "";
	protected String m_stringJob = "";
	protected String m_stringDest_size = "";
	protected String m_stringDest_dpi = "";
	protected String m_stringDest_name = "";
	protected String m_stringDest_type = "";
	protected String m_stringDest_postfix = "";
	protected String m_stringTarg_name = "";
	protected String m_stringTarg_prefix = "";
	protected String m_stringTarg_postfix = "";
	protected String m_stringTarg_typ = "";
	protected String m_stringTarg_path = "";
	protected HashMap hashValues = new HashMap();

	protected String m_stringPk = "";
	protected String m_stringPk_temp = "";
	protected String m_stringReal_name = "";


	public String getPk()
	{
		return m_stringPk;
	}

	public String getPk_temp()
	{
		return m_stringPk_temp;
	}

	public String getReal_name()
	{
		return m_stringReal_name;
	}

	public String getTarg_prefix()
	{
		return m_stringTarg_prefix;
	}

	public String getTarg_postfix()
	{
		return m_stringTarg_postfix;
	}

	public String getDest_size()
	{
		return m_stringDest_size;
	}

	public String getDest_typ()
	{
		System.out.println("getDest_typ=" + m_stringDest_type);
		return m_stringDest_type;
	}

	public String getJobFile()
	{
		return m_stringJobFile;
	}

	public String getJob()
	{
		return m_stringJob;
	}

	public String getSystem()
	{
		return m_stringSystem;
	}

	public String getTarg_name()
	{
		return m_stringTarg_name;
	}

	public String getDest_name()
	{
		return m_stringDest_name;
	}

	static String getClassName()
	{
		return "[" + m_CLASSNAME + "] ";
	}

	public void putJobFile(final String cValue)
	{
		m_stringJobFile = cValue;
	}

	public void putPk(final String cValue)
	{
		m_stringPk = cValue;
	}

	public void putPk_temp(final String cValue)
	{
		m_stringPk_temp = cValue;
	}

	public void putReal_name(final String cValue)
	{
		m_stringReal_name = cValue;
	}

	public void putJob(final String cValue)
	{
		m_stringJob = cValue;
		System.out.println("m_stringJob=" + m_stringJob + " / cValue=" + cValue);
	}

	public void putSystem(final String cValue)
	{
		m_stringSystem = cValue;
	}

	public void putTarg_name_kpl(final String cValue)
	{
		m_stringTarg_name = cValue;
		m_stringTarg_name = m_stringTarg_name.replace('\\', '/');
		System.out.println("m_stringTarg_name=" + m_stringTarg_name);
	}

	public void putTarg_name(final String cValue)
	{
		final Pattern p = Pattern.compile("[.]");

		final String arrayString[] = p.split(cValue);

		if (arrayString.length >= 1)
		{
			m_stringTarg_prefix = arrayString[0];
		}
		if (arrayString.length >= 2)
		{
			m_stringTarg_typ = arrayString[1];
		}
		System.out.println("m_stringTarg_prefix=" + m_stringTarg_prefix);
		System.out.println("m_stringTarg_typ=" + m_stringTarg_typ);
	}

	public void putTarg_path(final String cValue)
	{
		m_stringTarg_path = cValue;
		m_stringTarg_path = m_stringTarg_path.replace('\\', '/');

		System.out.println("m_stringTarg_path=" + m_stringTarg_path);
	}

	public void putDest_name(final String cValue)
	{
		final Pattern p = Pattern.compile("[.]");

		m_stringDest_name = cValue;
		final String arrayString[] = p.split(cValue);
		if (arrayString.length >= 1)
		{
			m_stringDest_postfix = arrayString[0];
			//if( arrayString.length >= 2 )
			//    m_stringDest_type  = arrayString[1];
		}

		m_stringDest_name = m_stringDest_name.replace('\\', '/');
	}

	public void putDest_size(final String cValue)
	{
		m_stringDest_size = cValue;
	}

	public void putDest_dpi(final String cValue)
	{
		m_stringDest_dpi = cValue;
	}

	public void putDest_typ(final String cValue)
	{
		m_stringDest_type = cValue;
		System.out.println("putDest_typ=" + m_stringDest_type);
	}


	// --- Konvertiere Zeile
	public String convertCmdLine(final Iterator oVarListe, String stringCmdLine)
	{
		// --- Loop around the vars
		String stringName;
		String stringValue;
		Element oElement;
		while (oVarListe.hasNext())
		{
			// --- Get and replace alle vars
			oElement = (Element) oVarListe.next();
			stringName = oElement.getAttribute("id").getValue();
			stringValue = oElement.getText();
			//		   stringValue   = oElement.getValue();
			stringCmdLine = stringCmdLine.replaceAll("%" + stringName + "%", stringValue);
		}

		stringCmdLine = stringCmdLine.replaceAll("%media.webroot%", getMediaWeb_name());
		stringCmdLine = stringCmdLine.replaceAll("%targ_name%", m_stringTarg_name);
		stringCmdLine = stringCmdLine.replaceAll("%targ_postfix%", m_stringTarg_postfix);
		stringCmdLine = stringCmdLine.replaceAll("%targ_prefix%", m_stringTarg_prefix);
		stringCmdLine = stringCmdLine.replaceAll("%targ_path%", m_stringTarg_path);
		stringCmdLine = stringCmdLine.replaceAll("%dest_dpi%", m_stringDest_dpi);
		stringCmdLine = stringCmdLine.replaceAll("%dest_size%", m_stringDest_size);
		stringCmdLine = stringCmdLine.replaceAll("%dest_name%", m_stringDest_name);
		stringCmdLine = stringCmdLine.replaceAll("%dest_postfix%", m_stringDest_postfix);
		stringCmdLine = stringCmdLine.replaceAll("%dest_type%", m_stringDest_type);
		stringCmdLine = stringCmdLine.replaceAll("%pk%", m_stringPk);
		stringCmdLine = stringCmdLine.replaceAll("%pk_temp%", m_stringPk_temp);
		stringCmdLine = stringCmdLine.replaceAll("%real_name%", m_stringReal_name);

		String cKey = "";
		String cValue = "";
		for (final Iterator it1 = hashValues.keySet().iterator(); it1.hasNext();)
		{
			cKey = (String) it1.next();
			cValue = (String) hashValues.get(cKey);
			System.out.println("Key=" + cKey + ", Value=" + cValue);
			stringCmdLine = stringCmdLine.replaceAll("%" + cKey + "%", cValue);
		}

		return stringCmdLine;
	}

	// --- Generiere Cmd-File
	public void createCmdFile(final Element oConfiguration, final Iterator oCmdListe)
	{
		// --- Initialize
		FileOutputStream fw = null;
		String stringCmdLine;

		// --- Hole Variablen
		try
		{
			// --- Neues Datei-Object
			final File fileTmpFile = File.createTempFile("im_", ".cmd");
			fw = new FileOutputStream(fileTmpFile);
			m_stringCmdFile = fileTmpFile.getAbsolutePath();
			System.out.println(m_stringCmdFile);


			// --- Loop around the Cmds
			while (oCmdListe.hasNext())
			{
				// --- Hole Element und konvertiere Inhalt
				final Iterator oVarListe = oConfiguration.getChildren("variable").iterator();
				final Element oCmdNode = (Element) oCmdListe.next();
				stringCmdLine = convertCmdLine(oVarListe, oCmdNode.getText());
				//              stringCmdLine      = convertCmdLine ( oVarListe, oCmdNode.getValue() );

				// --- Konvertiere Zeile 
				if (Config.getParameter("wera.os").compareTo("windows") == 0)
				{
					stringCmdLine = stringCmdLine.replace('/', '\\');
				}

				if (Config.getParameter("wera.os").compareTo("windows") == 0)
				{
					stringCmdLine = stringCmdLine.replaceAll("%removedir%", "del /Q ");
				}
				else
				{
					stringCmdLine = stringCmdLine.replaceAll("%removedir%", "rm -rf ");
				}

				// --- Schreibe Zeile
				fw.write(stringCmdLine.getBytes());
				fw.write("\n".getBytes());

			}

		}
		catch (final IOException e)
		{
			System.out.println("Konnte Datei nicht erstellen (" + m_stringCmdFile + ")");
		}
		finally
		{
			try
			{
				if (fw != null)
				{
					fw.close();
				}
			}
			catch (final IOException e)
			{
			}
		}
	}


	// --- Ausführen der CMD-Datei
	public void startCmdFile(final Element oCmd)
	{

		try
		{
			// --- Hole Commando für Betriebssystem
			final Pattern p = Pattern.compile("[ ]");
			m_stringCmdFile = m_stringCmdFile.replace('\\', '/');
			String stringCmd = oCmd.getText() + " " + m_stringCmdFile;
			stringCmd = oCmd.getText().replaceAll("%cmd_file%", m_stringCmdFile);
			final String arrayProcessString[] = p.split(stringCmd);
			LOG.info("startCmdFile(): stringCmd = " + stringCmd);

			// --- CMD-File ausführen
			final Process send = Runtime.getRuntime().exec(arrayProcessString);

			// --- read Input
			final BufferedReader in = new BufferedReader(new InputStreamReader(send.getInputStream()));
			for (String s; (s = in.readLine()) != null;)
			{
				LOG.info(s);
			}

			// --- Ende
			send.waitFor();
			LOG.info("startCmdFile(): Return exit value: " + send.exitValue());
			LOG.info("startCmdFile(): Done.");
		}
		catch (final InterruptedException e)
		{
			System.out.println(e.toString());
		}
		catch (final IOException e)
		{
			System.out.println(e.toString());
		}
	}

	public String getMediaWeb_name()
	{
		// TODO Auto-generated method stub
		String strMediaWeb = Config.getParameter("media.replication.dirs");
		if (strMediaWeb == null)
		{
			strMediaWeb = "";
		}

		return strMediaWeb.replace('\\', '/') + "/sys_master";
	}

	public void putVar(final String key, final String value)
	{
		// TODO Auto-generated method stub
		hashValues.put(key, value);
	}
}



public class imageKonv
{

	final static String m_CLASSNAME = "imageKonv";

	private static Logger LOG = Logger.getLogger(imageKonv.class.getName());

	protected String m_stringPK;

	// --- Create all used instances
	protected classXmlSupport m_oXmlSupport = new classXmlSupport();
	protected classXmlSupport m_oXmlSupportOut = new classXmlSupport();
	public classImageJob m_oImageJob = new classImageJob();
	protected String m_stringVariantPath = Config.getParameter("wera.homepath") + Config.getParameter("wera.imagepath");

	static String getClassName()
	{
		return "[" + m_CLASSNAME + "] ";
	}

	public void imageKonv()
	{
		// --- Create all used instances
		//m_oXmlSupport = new classXmlSupport();
		//m_oImageJob   = new classImageJob();

		// --- Initialize
		//m_oImageJob.putJobFile ( "c:/wera_testarea/imagejobs.xml" );
		//m_oImageJob.putSystem  ( "windows" );
	}

	public void Initialize()
	{
		// --- Initialize
		m_oImageJob.putJobFile(Config.getParameter("wera.im.imagemagik") + Config.getParameter("wera.im.jobfile"));
		m_oImageJob.putSystem(Config.getParameter("wera.os"));
	}

	public void putVar(final String cKey, final String cValue)
	{
		m_oImageJob.putVar(cKey, cValue);
	}

	public void putPk_temp(final String cValue)
	{
		m_oImageJob.putPk_temp(cValue);
	}

	public void putReal_name(final String cValue)
	{
		m_oImageJob.putReal_name(cValue);
	}

	public void putVariantPath(final String cValue)
	{
		m_stringVariantPath = cValue;
	}

	public void putPK(final String stringPK)
	{
		m_stringPK = stringPK;
		m_oImageJob.putPk(stringPK);
	}

	public String getJobFile()
	{
		return m_oImageJob.getJobFile();
	}

	public void putJob(final String stringJob)
	{
		m_oImageJob.putJob(stringJob);
	}

	public void putTarg_path(final String cValue)
	{
		m_oImageJob.putTarg_path(cValue);
	}

	public void putTarg_name_kpl(final String cValue)
	{
		m_oImageJob.putTarg_name_kpl(cValue);
	}

	public void putTarg_name(final String stringTarg_name)
	{
		m_oImageJob.putTarg_name(stringTarg_name);
	}

	public void putDest_name(final String stringDest_name)
	{
		m_oImageJob.putDest_name(stringDest_name);
	}

	public void putDest_typ(final String stringDest_typ)
	{
		m_oImageJob.putDest_typ(stringDest_typ);
	}

	public void putDest_size(final String stringDest_size)
	{
		m_oImageJob.putDest_size(stringDest_size);
	}

	public String getResultImage()
	{
		String stringResultImage;

		// ---
		stringResultImage = m_oImageJob.getTarg_prefix() + "_" + m_oImageJob.getDest_size() + "." + m_oImageJob.getDest_typ();

		return stringResultImage;
	}

	// --- Teste ob Datei vorhanden ist
	private boolean bCheckFile(final String stringFile)
	{
		boolean bResult;

		// --- Initliaze
		bResult = false;

		// --- Teste, Image bereits existiert
		final File fileTest = new File(stringFile);
		bResult = fileTest.exists();
		if (bResult)
		{
			LOG.info("bCheckFile(): " + stringFile + " does exist!");
		}
		else
		{
			LOG.info("bCheckFile(): " + stringFile + " does NOT exist!");
		}

		return bResult;
	}

	// --- Teste ob Datei vorhanden ist
	private boolean bCheckImage()
	{
		LOG.info("bCheckImage() called");
		boolean bResult;
		String stringResultImage;

		// --- Initliaze
		bResult = false;
		stringResultImage = "";
/*
		// --- Öffnen der XML-Datei
		LOG.info("bCheckImage(): Opening XML job file " + m_stringVariantPath + m_stringPK + ".xml" + " for reading.");
		final Element oRootNode = m_oXmlSupportOut.openXmlFile(m_stringVariantPath + m_stringPK + ".xml");
		if (oRootNode != null)
		{
			final Iterator oVarListe = oRootNode.getChildren("variant").iterator();
			// --- Loop around the Cmds
			while (oVarListe.hasNext())
			{
				LOG.info("bCheckImage(): typ_org=" + m_oImageJob.getDest_typ());
				LOG.info("bCheckImage(): size_org=" + m_oImageJob.getDest_size());
				// --- Hole Element und konvertiere Inhalt
				final Element oElement = (Element) oVarListe.next();
				if (oElement.getAttribute("sizex").getValue().toString().equals(m_oImageJob.getDest_size())
						&& oElement.getAttribute("typ").getValue().toString().equals(m_oImageJob.getDest_typ()))
				{
					LOG.info("bCheckImage(): typ=" + oElement.getAttribute("typ").getValue());
					LOG.info("bCheckImage(): sizex=" + oElement.getAttribute("sizex").getValue());
					LOG.info("bCheckImage(): Element ok gefunden.");
					stringResultImage = oElement.getAttribute("realname").getValue().toString();
					LOG.info("bCheckImage(): " + getClassName() + " Datei in XML gefunden.");
					break;
				}
				else
				{
					LOG.info("bCheckImage(): typ=" + oElement.getAttribute("typ").getValue());
					LOG.info("bCheckImage(): sizex=" + oElement.getAttribute("sizex").getValue());
					LOG.info("bCheckImage(): Element nicht gefunden.");
				}
				LOG.info("bCheckImage(): ---");
			}
		}
		else
		{
			LOG.info("bCheckImage(): " + getClassName() + " oRootNode == null");
		}

		// --- Teste ob Datei vorhanden ist
		if (stringResultImage.length() > 0)
		{
			LOG.info("bCheckImage(): Found result image " + stringResultImage + " - checking if file exists...");
			bResult = bCheckFile(m_stringVariantPath + stringResultImage);
		}
**/
		LOG.info("bCheckImage(): Returning " + bResult);
		return bResult;
	}

	// --- Registriere Variante

	private boolean bMakeImagePermanent(final String stringResultImage)
	{
		boolean bResult;

		// --- Initliaze
		bResult = false;
		return bResult;
	}


	public void showParam()
	{
		LOG.info(" ====== displaying current imageKonv job params ====== ");
		LOG.info("media.replication.dirs        = " + m_oImageJob.getMediaWeb_name());
		LOG.info("destination name              = " + m_oImageJob.getDest_name());
		LOG.info("target name                   = " + m_oImageJob.getTarg_name());
		LOG.info("target prefix                 = " + m_oImageJob.getTarg_prefix());
		LOG.info("target postfix                = " + m_oImageJob.getTarg_postfix());
		LOG.info("destination size              = " + m_oImageJob.getDest_size());
		LOG.info("destination type              = " + m_oImageJob.getDest_typ());
		LOG.info("job file                      = " + m_oImageJob.getJobFile());
		LOG.info("real name                     = " + m_oImageJob.getReal_name());
		LOG.info("system                        = " + m_oImageJob.getSystem());
		LOG.info("PK                            = " + m_oImageJob.getPk().toString());
		LOG.info("PK temp                       = " + m_oImageJob.getPk_temp().toString());

	}

	public String startJob()
	{

		LOG.info("startJob() called.");
		showParam();

		// --- Teste ob Datei vorhanden ist
		final String stringResultImage = m_stringVariantPath + getResultImage();
		LOG.info("startJob() resulting image will be: " + stringResultImage);

		if (bCheckImage())
		{
			// --- Es gibt nicht für uns zu tun.
			return getClassName() + stringResultImage + " existiert bereits.";
		}
		LOG.info("startJob(): Opening XML job file " + m_oImageJob.getJobFile());


		// --- Initialize
		String stringResult = "";

		// --- Öffne XML-Datei
		m_oXmlSupport.openXmlFile(m_oImageJob.getJobFile());
		final Element oConfiguration = m_oXmlSupport.getXmlElement(null, "configuration", m_oImageJob.getSystem());


		final Element oJob = m_oXmlSupport.getXmlElement(null, "job", m_oImageJob.getJob());

		if (oJob != null)
		{
			LOG.info("startJob(): <job> element found with id " + oJob.getAttribute("id").getValue());

			final Element oPlatform = m_oXmlSupport.getXmlElement(oJob, "platform", m_oImageJob.getSystem());
			if (oPlatform != null)
			{
				final Iterator oCmdListe = m_oXmlSupport.getXmlListe(oPlatform, "cmd");
				if (oCmdListe != null)
				{
					// --- Schreibe CMD-Datei
					m_oImageJob.createCmdFile(oConfiguration, oCmdListe);

					// --- Ausführen der CMD-Datei
					final Element oMainCmd = m_oXmlSupport.getXmlElement(oConfiguration, "variable", "command");
					final Element oPreCmd = m_oXmlSupport.getXmlElement(oConfiguration, "variable", "pre_cmd");
					final Element oPostCmd = m_oXmlSupport.getXmlElement(oConfiguration, "variable", "post_cmd");
					if (oMainCmd != null)
					{
						// --- Starte Job
						// --- Vorbereitung
						if (oPreCmd != null)
						{
							m_oImageJob.startCmdFile(oPreCmd);
						}

						// --- Starte Hauptjob
						m_oImageJob.startCmdFile(oMainCmd);

						// --- Nacharbeiten
						if (oPostCmd != null)
						{
							m_oImageJob.startCmdFile(oPostCmd);
						}

						// --- Prüfe Erfolg, und registriere Variante
						if (bMakeImagePermanent(stringResultImage))
						{
							stringResult = getClassName() + stringResultImage + " erzeugt.";
						}
						else
						{
							stringResult = getClassName() + stringResultImage + " nicht erzeugt.";
						}
					}
					else
					{
						stringResult = getClassName() + "varible@command ist nicht definiert.";
					}
				}
			}
			else
			{
				stringResult = getClassName() + "oPlatform nicht definiert.";
			}
		}
		else
		{
			stringResult = getClassName() + "Job-Command nicht definiert.";
		}

		return stringResult;
	}

	// --- MAIN
	public void main(final String args[])
	{

		// --- Initialize
		m_oImageJob.putJobFile("imagejobs.xml");
		m_oImageJob.putSystem("windows");
		if (args.length < 2)
		{
			System.out.println("Using: <jobname> <imagename> [desttype] [destsize]");
			System.out.println("jobname:   Bezeichnung der Jobs");
			System.out.println("imagename: Name der Eingabedatei");
			System.out.println("desttype:  Typ der Ausgabedatei (Optional)");
			System.out.println("destsize:  Größe der Ausgabedatei (Optional)");
			System.out.println("destdpi:   DPI-Größe (Optional)");
			return;
		}
		System.out.println("Anzahl:  " + args.length);
		System.out.println("Arg1:  " + args[0]);
		System.out.println("Arg2:  " + args[1]);
		m_oImageJob.putJob(args[0]);
		m_oImageJob.putTarg_name(args[1]);
		if (args.length >= 3)
		{
			System.out.println("Arg3:  " + args[2]);
			m_oImageJob.putDest_typ(args[2]);
		}
		if (args.length >= 4)
		{
			System.out.println("Arg4:  " + args[3]);
			m_oImageJob.putDest_size(args[3]);
		}
		if (args.length >= 5)
		{
			System.out.println("Arg5:  " + args[4]);
			m_oImageJob.putDest_dpi(args[4]);
		}

		// --- ImageJob starten
		startJob();


	} // --- public static void main(String args[])

}
