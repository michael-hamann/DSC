
package DSC;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Aliens_Michael
 */
public class ExcelReport {
    
    //make sheet
    public void create(){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("Name of sheet");
    }
    
    //make report
    
}
