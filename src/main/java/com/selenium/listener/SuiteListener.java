package com.selenium.listener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.testng.ISuite;
import org.testng.ISuiteListener;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class SuiteListener implements ISuiteListener{
		
	FileOutputStream op;
	Document document;
	PdfWriter pdf;
	Chapter chapter;
	PdfPTable table;
	List<PropertiesClass> properties = new ArrayList<>();
	File pdfFile = new File(System.getProperty("user.dir") + ("\\target\\PdfReport.pdf"));

	@Override
	public void onFinish(ISuite suite) {
		
		try {
			op = new FileOutputStream(pdfFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		document = new Document(PageSize.A4, 10, 10, 10, 10);
		try {
			pdf = PdfWriter.getInstance(document, op);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		document.open();
		Paragraph para = new Paragraph("Test Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15, BaseColor.RED));
		para.setAlignment(Element.ALIGN_CENTER);
		chapter = new Chapter(para, 1);
		chapter.setNumberDepth(0);
		table = new PdfPTable(9);
		table.setSpacingBefore(10);
		addTestName("Suite Name", suite.getXmlSuite().getName());
		
		createPdfReport();
		
		chapter.add(table);
		try {
			document.add(chapter);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		document.close();
		try {
			op.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		deleteImages();
		
		System.out.println("Pdf report generated.");
	}

	@Override
	public void onStart(ISuite suite) {
		passJenkinsParameters(suite);
		new File(System.getProperty("user.dir") + ("\\target\\generated-images")).mkdir();
	}
	
	public void passJenkinsParameters(ISuite suite) {
		Properties props = System.getProperties();
		Set<String> set = props.stringPropertyNames();
		Map<String,String> maps = suite.getXmlSuite().getAllParameters();
		for(Map.Entry<String, String> map : maps.entrySet()){
			if(set.contains(map.getKey())){
				map.setValue(props.getProperty(map.getKey()));
			}
		}
		suite.getXmlSuite().setParameters(maps);
	}
	
	public void addTestName(String headerName, String testName) {
		Paragraph para1 = new Paragraph(headerName, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK));
		PdfPCell cell1 = new PdfPCell(para1);
		cell1.setColspan(2);
		cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(cell1);
		
		Paragraph para2 = new Paragraph(testName, FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK));
		PdfPCell cell2 = new PdfPCell(para2);
		cell2.setColspan(7);
		cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(cell2);
	}
	
	public void addTableHeaders(String header) {
		Paragraph para = new Paragraph(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK));
		PdfPCell cell = new PdfPCell(para);
		cell.setColspan(2);
		table.addCell(cell);
	}
	
	public void addTableValues(String values) {
		Paragraph para = new Paragraph(values, FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK));
		PdfPCell cell = new PdfPCell(para);
		cell.setColspan(7);
		table.addCell(cell);
	}
	
	public void addImage(String methodName) {
		Image image;
		try {
			image = Image.getInstance(System.getProperty("user.dir") + ("\\target\\generated-images\\" + methodName + ".jpg"));
			image.scaleToFit(450f, 450f);
			
			PdfPCell cell = new PdfPCell(image);
			cell.setColspan(9);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(5f);
			table.addCell(cell);
		} catch (BadElementException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createPdfReport() {
		for(int i = 0; i < properties.size(); i++) {
			PropertiesClass pojo = properties.get(i);
			if(pojo.newTest) {
				addTestName("Test Name", pojo.testName);
			}
			else if(!pojo.newTest) {
				
				addTableHeaders("Step");
				addTableValues(pojo.step);
				
				addTableHeaders("Step Name");
				addTableValues(pojo.methodName);
				
				addTableHeaders("Parameters");
				if(pojo.parameters.length > 0) {
					String str = "";
					for(int j = 0; j < pojo.parameters.length; j++) {
						str = str + ", " + pojo.parameters[j];
						addTableValues(str.substring(2));
					}
				}
				else {
					addTableValues("--");
				}
				
				addTableHeaders("Status");
				addTableValues(pojo.status);
				
				if(pojo.status.equals("Failed")) {
					addTableHeaders("Failure Reason");
					addTableValues(pojo.failureMessage);
				}
				
				if(pojo.status.equals("Passed") || pojo.status.equals("Failed")) {
					addImage(pojo.methodName);
				}
				
				if(i != properties.size() - 1) {
					PdfPCell cell = new PdfPCell();
					cell.setColspan(9);
					cell.setPadding(10f);
					cell.disableBorderSide(4);
					cell.disableBorderSide(8);
					table.addCell(cell);
				}
			}
		}
	}
	
	public void deleteImages() {
		File file = new File(System.getProperty("user.dir") + ("\\target\\generated-images"));
		File [] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			files[i].delete();
			while(files[i].exists()){}
        }
		file.delete();
		while(file.exists()){}
	}

}
