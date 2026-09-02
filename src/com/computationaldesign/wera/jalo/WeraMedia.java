package com.computationaldesign.wera.jalo;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.TypeManager;
import de.hybris.platform.util.Config;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;


@SuppressWarnings("PMD")
public class WeraMedia extends GeneratedWeraMedia
{
	private static Logger LOG = Logger.getLogger(WeraMedia.class.getName());

	public WeraMedia()
	{
		// empty
	}

        
	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes)
			throws JaloBusinessException
	{
		// business code placed here will be executed before the item is created
		// then create the item
		final Item item = super.createItem(ctx, type, allAttributes);
		// business code placed here will be executed after the item was created
		// and return the item
		return item;
	}

	public void beforeSave(final Map values)
	{
		LOG.info("beforeSave(): for code " + getCode() + " - nothing happening here.");
	}

	public void phyiscal_copy(final File source, final File dest) throws IOException {
		LOG.info("phyiscal_copy() called ...");
		FileChannel in = null, out = null;
		try {
			in = new FileInputStream(source).getChannel();
			out = new FileOutputStream(dest).getChannel();

			final long size = in.size();
			final MappedByteBuffer buf = in.map(FileChannel.MapMode.READ_ONLY, 0, size);

			out.write(buf);

		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
		LOG.info("phyiscal_copy() finished.");
	}	
	
	public void afterSave(final Map values)
	{
		LOG.info("afterSave(): for WeraMedia with code " + getCode() + " and mime " + getMime() + "  - START");

		if (getMime().equals("image/jpeg")) {
		    LOG.info("afterSave(): mime is jpg => copy to jpg folder for pdf processing.");
		    String sMediaCode = this.getCode().trim();
		    if ( sMediaCode.endsWith(".jpg")) {
			sMediaCode = sMediaCode.substring( 0, sMediaCode.length()-5 );
			
			String sDestFilename = sMediaCode + "_cmyk_300dpi.jpg";

			String sSourceFile = Config.getParameter("media.replication.dirs") + File.separator + "sys_master" + File.separator + getFileName();
			String sDestPath = Config.getParameter("wera.homepath") + File.separator + "imagepool" + File.separator + "pictures" + File.separator + "jpg" + File.separator + sDestFilename;

			final File fSource = new File(sSourceFile);
			final File fDest = new File(sDestPath);		    
			try {
			    phyiscal_copy(fSource, fDest);
			    LOG.info("afterSave(): successfully saved iamge to destination folder: "+sDestPath);
			} catch ( IOException ioe ) {
			    LOG.error("afterSave(): Exception copying image file from: "+sSourceFile+ " to dest: "+sDestPath);
			    LOG.error("afterSave(): "+ioe.getMessage());
			}
			
		    }
		}
		
		/*
		if (getMime().equals("application/postscript"))
		{
			LOG.info("afterSave(): mime is postscript => create variants...");
			LOG.info("afterSave(): creating JPG 72 variant, size: 230px, job id: upload_72");
			createVariant("jpg", "230", "upload_72");

			// --- JPG mit 300 DPI (CMYK)
			LOG.info("afterSave(): creating external JPG 300 variant, colorspace CMYK, 300dpi");
			//createExternalVariant("jpg", "", "rgb", "300");
			createExternalVariant("jpg", "", "cmyk", "300");

			try
			{
				setMime("image/jpeg");
				LOG.info("afterSave(): Setting mime to jpeg!");
			}
			catch (final Exception e)
			{
				LOG.warn("afterSave(): Exception setting mime to jpeg!");
				e.printStackTrace();
			}
		}
		*/

		LOG.info("afterSave() for WeraMedia with code " + getCode() + " and mime " + getMime() + "  - ENDE");
	}

	public static Set getAllInstances()
	{


		final ComposedType WeraMediaType = TypeManager.getInstance().getComposedType(WeraMedia.class);

		return WeraMediaType.getAllInstances();

	}


	public void setFile(final File file, final String originalName, final String mimeType)
	{


		try
		{
			final DataInputStream stream = new DataInputStream(new FileInputStream(file));
			setData(stream, originalName, mimeType);
		}
		catch (final FileNotFoundException e)
		{
			// throw new WebMCSystemException(e);
			// SJ12 Exception?
		}
	}



	@Override
	public String getMime()
	{
		// TODO Auto-generated method stub

		return super.getMime();
	}

	/*
	 * alte version, die in der WebMC die icons als broken images anzeigt public String getPreview() { // TODO
	 * Auto-generated method stub setThumbname(getPK().toString()+".jpg"); setThumbpath("/medias/");
	 * 
	 * return getThumbpath() + getThumbname(); }
	 */

	public String getPreview()
	{
		// TODO Auto-generated method stub
		setThumbpath("/medias/sys_master/");
		final String sMime = getMime();
		final String sPk = getPK().toString();
		if (sMime.equals("image/gif"))
		{
			setThumbname(sPk + ".gif");
		}
		else
		{
			if (sMime.equals("image/png"))
			{
				setThumbname(sPk + ".png");
			}
			else
			{
				setThumbname(sPk + ".jpg");
			}
		}
		return getThumbpath() + getThumbname();
	}

         
	@Override
	public String getURL()
	{
		// --- Initialize
		String strFileName = getRealFileName();

		// TODO Auto-generated method stub
		final String strMime = getMime();
		if (strMime != null && strMime.equals("application/postscript"))
		{
			return getPreview();
		}
		else
		{
			
			return super.getURL();
			
			/**
			 * Anzeige der WeraMedia-Objekete per http
			 * spï¿½ter bei Anbindung an DAM in ï¿½hnlicher Form verwenden
			 * 19.10.2016 GT
			 */
/*			
			strFileName	=	strFileName.replaceAll(".eps", ".jpg");
			return "http://www-de.wera.de/fileadmin/products/img/products/380/" + strFileName;
*/
		}
	}

	@Override
	public String getURL2()
	{
		// TODO Auto-generated method stub
		return super.getURL();
	}

	@Override
	public String getFileName()
	{
		// TODO Auto-generated method stub
		
		return super.getFileName();
	}

	@Override
	public void setHybrisfilename(final SessionContext ctx, final String param)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public String getHybrisfilename(final SessionContext ctx)
	{
		return super.getFileName();
	}


	// ********************************************************************************
	@Override
	public boolean setFile(final File arg0) throws JaloBusinessException
	{
		// TODO Auto-generated method stub
		return super.setFile(arg0);
	}

	public void dumpCol(final Collection collection, final String strText)
	{
		final Item oObject = (Item) WeraManager.checkContaining(collection, "code", getCode());
		if (oObject != null)
		{
			try
			{
				System.out.println("Produkt ist -" + strText + oObject.getAttribute("code") + " / " + oObject.getAttribute("name"));
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
		}
		else
		{
			System.out.println("Produkt ist NICHT -" + strText);
		}
		System.out.println(strText + "-Anzahl" + collection.size());

	}


	public String getFileNameOfVariant(final String strFormat, final String strWidth, final String cColorspace, final String cDPI)
	{
		// --- Initialize
		String strFileName = getRealFileName();
		String cFileExt = "";

		// --- Breite
		if (strWidth.length() > 0)
		{
			cFileExt += "_" + strWidth + "x";
		}

		// --- Colorspace
		if (cColorspace.length() > 0)
		{
			cFileExt += "_" + cColorspace;
		}

		// --- DPI
		if (cDPI.length() > 0)
		{
			cFileExt += "_" + cDPI + "dpi";
		}

		// --- Baue den Filenamen auf
		if ( strFileName == null ) {
			strFileName = "";
		}
		cFileExt += "." + strFormat;
		strFileName = strFileName.replaceAll(".eps", cFileExt);

		return strFileName;
	}

	public String getPathNameOfVariant(final String strFormat)
	{
		return Config.getParameter("wera.homepath") + "/imagepool/pictures/" + strFormat;
	}

	public boolean checkIfExistsExternalVariant(final String strFormat, final String strWidth, final String cColorspace,
			final String cDPI)
	{

		// --- Intialize
		final String strFileName = getPathNameOfVariant(strFormat) + "/"
				+ getFileNameOfVariant(strFormat, strWidth, cColorspace, cDPI);

		// --- Prï¿½fe, ob die Datei vorhanden ist
		final File testfile = new File(strFileName);
		return testfile.exists();
	}

	public boolean checkIfVariantNeedsUpdateFromNewerEPSFile(final String strFormat, final String strWidth,
			final String cColorspace, final String cDPI)
	{

		boolean rval = true;
		long tsEPSLastModified = 0;
		long tsVariantLastModified = 0;

		// --- If variant does not exist, create it anyway
		final String strFileNameVariant = getPathNameOfVariant(strFormat) + "/"
				+ getFileNameOfVariant(strFormat, strWidth, cColorspace, cDPI);

		// --- Prï¿½fe, ob die Datei vorhanden ist
		final File testfileVariant = new File(strFileNameVariant);

		// --- LastModified Timestamp der Variante ermitteln
		if (testfileVariant.exists())
		{
			tsVariantLastModified = testfileVariant.lastModified();
		}

		// --- Nun Quelldatei (EPS) prï¿½fen auf Timestamp und vergleichen
		final String strFileName = getPathNameOfVariant("") + getRealFileName();
		System.out.println("checkIfVariantNeedsUpdateFromNewerEPSFile: Name of EPS = " + strFileName);
		final File testfile = new File(strFileName);
		if (testfile.exists())
		{
			tsEPSLastModified = testfile.lastModified();
			// --- Ist das EPS neuer als die Variante? 
			// --- Wenn nicht, soll Variante nicht neu berechnet werden.
			if (tsEPSLastModified <= tsVariantLastModified)
			{
				rval = false;
				System.out.println("checkIfVariantNeedsUpdateFromNewerEPSFile: Variante ist mind. so neu wie EPS.");
			}
			else
			{
				System.out.println("checkIfVariantNeedsUpdateFromNewerEPSFile: EPS neuer als Variante!");
			}
		}
		return rval;
	}



	public long getFileSizeOfExternalVariant(final String strFormat, final String strWidth, final String cColorspace,
			final String cDPI)
	{

		// --- Intialize
		final String strFileName = getPathNameOfVariant(strFormat) + "/"
				+ getFileNameOfVariant(strFormat, strWidth, cColorspace, cDPI);

		// --- Prï¿½fe, ob die Datei vorhanden ist
		final File testfile = new File(strFileName);
		return testfile.length();
	}

	public void createExternalVariant(final String strFormat, final String strWidth, final String cColorspace, final String cDPI)
	{
		// --- Initialie
		final imageKonv oImageKonv = new imageKonv();

		// --- Breite
		if (strWidth.length() == 0)
		{
			oImageKonv.putVar("resize", "");
		}
		else
		{
			oImageKonv.putVar("resize", " -resize " + strWidth);
		}

		// --- Colorspace
		if (cColorspace.length() == 0)
		{
			oImageKonv.putVar("colorspace", "");
		}
		else
		{
			oImageKonv.putVar("colorspace", " -colorspace " + cColorspace);
		}

		// --- DPI
		if (cDPI.length() == 0)
		{
			oImageKonv.putVar("dpi", "");
		}
		else
		{
			oImageKonv.putVar("dpi", " -density " + cDPI + " -units PixelsPerInch ");
		}

		// --- Create all used instances
		System.out.println("createVariant.start");
		oImageKonv.Initialize();
		if (strFormat.equals("eps"))
		{
			oImageKonv.putJob("getexternalvariant");
		}
		else
		{
			oImageKonv.putJob("createexternalvariant");
			oImageKonv.putDest_typ(strFormat);
			if (strWidth.trim().length() > 0)
			{
				oImageKonv.putDest_size(strWidth.trim());
			}
		}

		// --- Zieldatei
		final String strDestPathName = getPathNameOfVariant(strFormat);
		WeraManager.getInstance().createDirectory(strDestPathName);
		final String strDestFileName = getFileNameOfVariant(strFormat, strWidth, cColorspace, cDPI);
		oImageKonv.putDest_name(strDestPathName + "/" + strDestFileName);

		// --- Quelldatei ( EPS )
		oImageKonv.putTarg_path(Config.getParameter("wera.homepath") + "/imagepool/pictures");
		oImageKonv.putTarg_name(getFileName());
		oImageKonv.putReal_name(getRealFileName());


		// --- Konvertiere 
		final String stringResultJob = oImageKonv.startJob();
		System.out.println(stringResultJob);
	}

	public String createVariant(final String strFormat, final String strSize, String strJobName)
	{
		LOG.info("createVariant() called");
		// --- Initialize
		if (strJobName.trim().length() == 0)
		{
			// --- Default-Job
			strJobName = "upload";
		}

		String strMediaKategorie = getMediakategorie();
		if (strMediaKategorie == null)
		{
			strMediaKategorie = "";
		}
		else
		{
			strMediaKategorie = strMediaKategorie + "\\";
		}
		LOG.info("createVariant(): media category: " + strMediaKategorie);

		// create an image converter instance and initialize it
		final imageKonv oImageKonv = new imageKonv();
		oImageKonv.Initialize();


		oImageKonv.putJob(strJobName);
		oImageKonv.putDest_typ(strFormat);
		oImageKonv.putDest_size(strSize);
		oImageKonv.putTarg_name_kpl(Config.getParameter("media.replication.dirs") + "\\sys_master\\" + getFileName());
		oImageKonv.putTarg_name(getFileName());
		oImageKonv.putTarg_path(Config.getParameter("wera.homepath") + Config.getParameter("wera.imagepath") + strMediaKategorie);
		oImageKonv
				.putDest_name(Config.getParameter("wera.homepath") + Config.getParameter("wera.imagepath") + "\\" + getFileName());


		//oImageKonv.putPK(getPK().toString());
		oImageKonv.putPK(Long.toString(getDataPKAsPrimitive()));

		//oImageKonv.putPk_temp("temp_" + getPK().toString());
		oImageKonv.putPk_temp("temp_" + Long.toString(getDataPKAsPrimitive()));

		oImageKonv.putReal_name(getRealFileName());

		// --- Konvertiere 
		LOG.info("createVariant(): delegating image conversion...");
		final String stringResultJob = oImageKonv.startJob();
		LOG.info("createVariant(): returning from image conversion with: " + stringResultJob);

		final String sThumbname = getPK().toString() + "." + strFormat;
		LOG.info("createVariant(): setting thumbname to " + sThumbname);
		setThumbname(sThumbname);
		setThumbpath("/medias/sys_master/");


		LOG.info("createVariant() finished.");
		return "";

	}

	// ********************************************************************************

	// --- PrÃ¼fe ob die Datei vorhanden sit und lege ggf. eine an
	public void checkCreate()
	{
		// TODO Auto-generated method stub

		// --- Prï¿½fe, ob die Mediadatei existiert
		File testfile = new File(Config.getParameter("media.replication.dirs") + "\\" + getFileName());
		if (!testfile.exists())
		{
			// --- PrÃ¼fen, ob eine Dateien in einem anderen Format vorliegt
			final String strFileName = getFileName().split("\\.")[0];
			// --- Datei lÃ¶schen
			final String[] aExtList =
			{ ".jpg", ".gif", ".ps", ".eps" };
			for (int iCnt = 0; iCnt < aExtList.length; iCnt++)
			{
				testfile = new File(Config.getParameter("media.replication.dirs") + "\\" + strFileName + aExtList[iCnt]);
				if (testfile.exists())
				{
					System.out.println("Lï¿½sche=" + strFileName + aExtList[iCnt] + "-result=" + testfile.delete());
				}
			}
		}
		// --- Datei anlegen
		FileWriter newFile;
		try
		{
			newFile = new FileWriter(Config.getParameter("media.replication.dirs") + "\\" + getFileName());
			newFile.write("\r\n");
			newFile.close();
		}
		catch (final IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
