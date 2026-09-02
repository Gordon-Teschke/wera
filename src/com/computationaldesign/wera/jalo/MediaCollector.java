package com.computationaldesign.wera.jalo;

import de.hybris.platform.util.Config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author teschke
 */
public class MediaCollector {

	/**
	 * Used logger instance.
	 */
	private static final Log LOG = LogFactory.getLog(MediaCollector.class);

	String m_StrBasePath = "";
	HashMap m_hashFileList = new HashMap();
	HashMap m_hashCategoryList = new HashMap();
	long lCounter = 0;

	/**
	 * 
	 * @return 
	 */
	public HashMap getM_hashFileList() {
		return m_hashFileList;
	}

	/**
	 * 
	 * @param fileList 
	 */
	public void setM_hashFileList(final HashMap fileList) {
		m_hashFileList = fileList;
	}

	/**
	 * 
	 */
	public MediaCollector() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @return 
	 */
	public String getM_StrBasePath() {
		return m_StrBasePath;
	}

	/**
	 * 
	 * @param strBasePath 
	 */
	public void setM_StrBasePath(final String strBasePath) {
		m_StrBasePath = strBasePath;
	}

	/**
	 * Neues Media - Object anlegen (inkl. Kategorie Information)
	 * 
	 * @param strRealFileName
	 * @param strCategory 
	 */
	public void addMediaInfoByCategory(final String strRealFileName, final String strCategory) {

		// --- Category Liste erweitern
		Collection colImages = null;
		if (m_hashCategoryList.containsKey(strCategory)) {
			colImages = (Collection) m_hashCategoryList.get(strCategory);
		} else {
			colImages = new ArrayList();
		}
		colImages.add(strRealFileName);
		m_hashCategoryList.put(strCategory, colImages);
	}

	/**
	 * Neues Media - Object anlegen
	 * 
	 * @param strPk
	 * @param strRealFileName
	 * @param strFileNameHybris
	 * @param strDestPath 
	 */
	public void addMedia(String strPk, final String strRealFileName, final String strFileNameHybris, final String strDestPath) {

		//LOG.info("++addMedia.strRealFileName="+strRealFileName);
		// --- HashNode aufbauen
		final HashMap hashMediaInfo = new HashMap();
		hashMediaInfo.put("strRealFileName", strRealFileName);
		hashMediaInfo.put("strFileNameHybris", strFileNameHybris);
		hashMediaInfo.put("strDestPath", strDestPath);

		// --- Object merken
		strPk += ":" + Long.toString(lCounter++);
		m_hashFileList.put(strPk, hashMediaInfo);
	}

	/**
	 * Bildarchive / Kategory Index herstellen
	 * 
	 * @param strDestDirectory 
	 */
	public void generateMediaListByCategory(final String strDestDirectory) {

		LOG.info("++generateMediaListByCategory ...");

		// --- Schleife über alle Kategorien
		for (final Iterator itCategory = m_hashCategoryList.keySet().iterator(); itCategory.hasNext();) {
			final String strCategory = (String) itCategory.next();

			final Collection colImages = (Collection) m_hashCategoryList.get(strCategory);
			final String strOutput = StringUtils.join(colImages, "\t");
			try {

				final String strDestFile = strDestDirectory + File.separator + strCategory + ".txt";
				LOG.info("++generateMediaListByCategory strDestFile=" + strDestFile);
				final BufferedWriter out = new BufferedWriter(new FileWriter(strDestFile));
				out.write(strOutput);
				out.flush();
				out.close();

			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} // --- for ( Iterator itCategory = m_hashCategoryList.keySet().iterator(); itCategory.hasNext() ) {
	}

	/**
	 * 
	 */
	public void archiveMedias() {

		LOG.info("++archiveMedias ...");

		// --- Initialize
		final WeraManager wm = WeraManager.getInstance();
		FileWriter oFileWriterLog = null;
		String strDestFile = "";
		String strSrcFile = "";

		// --- Schleife über alle Medias
		for (final Iterator it1 = m_hashFileList.keySet().iterator(); it1.hasNext();) {
			// --- Get MediaInfos
			final HashMap hashMediaInfo = (HashMap) m_hashFileList.get(it1.next());

			// --- Quelldatei / Zieldatei
			strDestFile = m_StrBasePath + hashMediaInfo.get("strDestPath") + File.separator + hashMediaInfo.get("strRealFileName");
			strSrcFile = Config.getParameter("media.replication.dirs") + File.separator + "sys_master" + File.separator
					+ hashMediaInfo.get("strFileNameHybris");

			// --- Zielverzeichnis anlegen, falls nicht vorhanden
			wm.createDirectory(m_StrBasePath + hashMediaInfo.get("strDestPath"));

			// --- Datei umkopieren
			// LOG.info ("copy " + strSrcFile + " nach=>" + strDestFile);
			try {
				final File inFile = new File(strSrcFile);
				final File outFile = new File(strDestFile);
				if (inFile.exists()) {
					copy(inFile, outFile);
				} else {
					LOG.error("Datei nicht vorhanden=" + strSrcFile);

					// --- Logdatei anlegen, bei Bedarf
					if (oFileWriterLog == null) {
						oFileWriterLog = new FileWriter(m_StrBasePath + hashMediaInfo.get("strDestPath") + File.separator
								+ "fehlende_bilder.txt");
					}
					oFileWriterLog.write(strSrcFile + "\r\n");
				}

			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// --- Schliesen
		try {
			if (oFileWriterLog != null) {
				oFileWriterLog.close();
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void archiveMedias300DPI() {

		LOG.info("++archiveMedias300DPI ...");

		// --- Initialize
		final WeraManager wm = WeraManager.getInstance();
		FileWriter oFileWriterLog = null;
		String strDestFile = "";
		String strSrcFile = "";

		// --- Schleife über alle Medias
		for (final Iterator it1 = m_hashFileList.keySet().iterator(); it1.hasNext();) {
			// --- Get MediaInfos
			final HashMap hashMediaInfo = (HashMap) m_hashFileList.get(it1.next());

			// --- Zieldatei
			strDestFile = m_StrBasePath + hashMediaInfo.get("strDestPath") + File.separator + hashMediaInfo.get("strRealFileName");

			// --- Quelldatei
			final String strFileName = (String) hashMediaInfo.get("strRealFileName");
			strSrcFile = "/home/hybris/imagepool/pictures/jpg" + File.separator + strFileName.replace(".jpg", "_cmyk_300dpi.jpg");

			// --- Zielverzeichnis anlegen, falls nicht vorhanden
			wm.createDirectory(m_StrBasePath + hashMediaInfo.get("strDestPath"));

			// --- Datei umkopieren
			// LOG.info ("copy " + strSrcFile + " nach=>" + strDestFile);
			try {
				final File inFile = new File(strSrcFile);
				final File outFile = new File(strDestFile);
				if (inFile.exists()) {
					copy(inFile, outFile);
				} else {
					LOG.error("Datei nicht vorhanden=" + strSrcFile);

					// --- Logdatei anlegen, bei Bedarf
					if (oFileWriterLog == null) {
						oFileWriterLog = new FileWriter(m_StrBasePath + hashMediaInfo.get("strDestPath") + File.separator
								+ "fehlende_bilder.txt");
					}
					oFileWriterLog.write(strSrcFile + "\r\n");
				}

			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// --- Schliesen
		try {
			if (oFileWriterLog != null) {
				oFileWriterLog.close();
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fast & simple file copy.
	 * 
	 * @param source
	 * @param dest
	 * @throws IOException 
	 */
	public static void copy(final File source, final File dest) throws IOException {
		final InputStream in = new FileInputStream(source);
		final OutputStream out = new FileOutputStream(dest);

		//    	 Transfer bytes from in to out
		final byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

}
