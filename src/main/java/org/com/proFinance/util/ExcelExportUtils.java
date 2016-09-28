package org.com.proFinance.util;

import java.util.Calendar;
import java.util.Date;

import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.Orientation;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


/**
 * @author mario.hjunior
 *
 */
public class ExcelExportUtils {

	/**
	 * Cria pasta no arquivo criado. Créditos Cristian
	 * 
	 * @param wb - WritableWorkbook to create new sheet in
	 * @param sheetName - name to be given to new sheet
	 * @param sheetIndex - position in sheet tabs at bottom of workbook
	 * @return - a new WritableSheet in given WritableWorkbook
	 */
	public static WritableSheet createSheet(WritableWorkbook wb, String sheetName, int sheetIndex) {

		return wb.createSheet(sheetName, sheetIndex);

	}

	public static void adicionarCelula(int coluna, int linha, String conteudo, boolean isHeader, WritableSheet sheet) throws RowsExceededException, WriteException {

		// create a new cell with contents at position
		Label newCell = new Label(coluna, linha, conteudo);

		if (isHeader) {

			WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
			WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
			headerFormat.setAlignment(Alignment.LEFT);
			newCell.setCellFormat(headerFormat);

		}

		sheet.addCell(newCell);
	}

	public static void adicionarCelulaData(int coluna, int linha, Date data, boolean isHeader, WritableSheet sheet) throws RowsExceededException, WriteException {

		if (data == null) {
			return;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime((Date) data);
		Date gmtDate = new Date(((Date) data).getTime() + (calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET)));

		WritableCellFormat dateFormat = new WritableCellFormat(jxl.write.DateFormats.FORMAT2);

		DateTime dc = new DateTime(coluna, linha, gmtDate, dateFormat);

		sheet.addCell(dc);
	}

	public static void adicionarCelulaNumeral(int coluna, int linha, double valor, boolean isHeader, WritableSheet sheet) throws RowsExceededException, WriteException {

		jxl.write.Number number = new jxl.write.Number(coluna, linha, valor);

		sheet.addCell(number);
	}

	public static void adicionarCelulaNumeralDinheiro(int coluna, int linha, double valor, boolean isHeader, WritableSheet sheet) throws RowsExceededException, WriteException {

		WritableCellFormat numberFormat = new WritableCellFormat(NumberFormats.FLOAT);

		jxl.write.Number number = new jxl.write.Number(coluna, linha, valor, numberFormat);

		sheet.addCell(number);
	}

	public static void adicionarCelula(int coluna, int linha, String conteudo, boolean isHeader, WritableSheet sheet, Integer widthInChars) throws RowsExceededException, WriteException {
		adicionarCelula(coluna, linha, conteudo, isHeader, sheet, widthInChars, Alignment.LEFT, Colour.LIGHT_GREEN);
	}

	public static void adicionarCelula(int coluna, int linha, String conteudo, boolean isHeader, WritableSheet sheet, Integer widthInChars, Alignment alignment) throws RowsExceededException,
			WriteException {
		adicionarCelula(coluna, linha, conteudo, isHeader, sheet, widthInChars, alignment, Colour.LIGHT_GREEN);
	}

	public static void adicionarCelula(int coluna, int linha, String conteudo, boolean isHeader, WritableSheet sheet, Integer widthInChars, Colour backgroundColor) throws RowsExceededException,
			WriteException {
		adicionarCelula(coluna, linha, conteudo, isHeader, sheet, widthInChars, Alignment.LEFT, backgroundColor);
	}

	public static void adicionarCelula(int coluna, int linha, String conteudo, boolean isHeader, WritableSheet sheet, Integer widthInChars, Alignment alignment, Colour backgroundColor)
			throws RowsExceededException, WriteException {
		// create a new cell with contents at position
		sheet.setColumnView(coluna, widthInChars);

		// create a new cell with contents at position
		Label newCell = new Label(coluna, linha, conteudo);

		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setAlignment(alignment);
		newCell.setCellFormat(cellFormat);

		if (isHeader) {
			// give header cells size 10 Arial bolded
			WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
			WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
			headerFormat.setBackground(backgroundColor);
			// center align the cells' contents
			headerFormat.setAlignment(alignment);
			newCell.setCellFormat(headerFormat);
		}

		sheet.addCell(newCell);

	}

	public static void adicionarCelula(int coluna, int linha, String conteudo, boolean isHeader, WritableSheet sheet, Alignment alignment) throws RowsExceededException, WriteException {

		Label newCell = new Label(coluna, linha, conteudo);

		if (isHeader) {
			// give header cells size 10 Arial bolded
			WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
			WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
			// center align the cells' conteudo
			headerFormat.setAlignment(alignment);
			newCell.setCellFormat(headerFormat);
		}

		sheet.addCell(newCell);

	}

	/**
	 * Cria celulas com formato específico (displayFormat).
	 * 
	 * @param coluna
	 * @param linha
	 * @param conteudo
	 * @param isHeader
	 * @param sheet
	 * @param displayFormat Ex.: NumberFormats.FLOAT
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public static Label adicionarCelula(int coluna, int linha, String conteudo, boolean isHeader, WritableSheet sheet, DisplayFormat displayFormat) throws RowsExceededException, WriteException {

		// create a new cell with contents at position
		Label newCell = new Label(coluna, linha, conteudo);

		if (isHeader) {
			// give header cells size 10 Arial bolded
			WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
			WritableCellFormat headerFormat = new WritableCellFormat(headerFont, displayFormat);
			headerFormat.setOrientation(Orientation.HORIZONTAL);
			// center align the cells' contents
			headerFormat.setAlignment(Alignment.LEFT);
			newCell.setCellFormat(headerFormat);
		}

		sheet.addCell(newCell);
		return newCell;
	}

	public static void mesclarCelulas(WritableSheet ws, int coluna1, int linha1, int coluna2, int linha2) throws RowsExceededException, WriteException {
		ws.mergeCells(coluna1, linha1, coluna2, linha2);
	}

}
