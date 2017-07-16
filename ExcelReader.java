
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    private static List<String> FieldNames;
    private static List<String> DataTypes;
    private static List<String> FieldLength;
    private static List<String> FieldRequired;
    private static String objectName;
    private static int nameindex, datatypeindex, lengthindex, requiredindex;
    private static DataFormatter df=new DataFormatter();

    public ExcelReader(String filename,String objectName) throws Exception {
        FieldNames=new ArrayList<>();
        DataTypes=new ArrayList<>();
        FieldLength=new ArrayList<>();
        FieldRequired=new ArrayList<>();
        this.objectName=objectName;
        extract(filename);
    }

    public static void main(String[] args) throws Exception {

        String filename = "Enter file name";
        ExcelReader x=new ExcelReader(filename,"enter sheet name");
        System.out.println(x.getFieldNames());
        System.out.println(x.getDataTypes());
        System.out.println(x.getFieldLength());
        System.out.println(x.getFieldRequired());

    }

    public static void extract(String filename) throws Exception {

        FileInputStream fis;
        fis = new FileInputStream(filename);
        HSSFWorkbook workbook = new HSSFWorkbook(fis);
        HSSFSheet sheet;
        sheet = workbook.getSheet("sheet name"));

        Row row;
        Cell name,type,length,req;

        getColumnIndex(sheet.getRow(0));

        for (int rowIndex = 1; rowIndex < sheet.getLastRowNum()+1; rowIndex++) {

            row = sheet.getRow(rowIndex);

            if (row != null) {

                name=row.getCell(nameindex);
                type=row.getCell(datatypeindex);
                length=row.getCell(lengthindex);
                req=row.getCell(requiredindex);

                if(name!=null) {

                    FieldNames.add(name.getStringCellValue());

                    if(type!=null) {
                        if(type.getStringCellValue().contains("Numeric"))
                            DataTypes.add("Decimal");
                        else if(type.getStringCellValue().contains("Date"))
                            DataTypes.add("DATE");
                        else
                            DataTypes.add(type.getStringCellValue());
                    }
                    else
                        DataTypes.add(null);

                    if(length!=null)
                        FieldLength.add(String.valueOf(df.formatCellValue(length)));
                    else
                        FieldLength.add("");

                    if(req!=null) {

                        String value=req.getStringCellValue();
                        if(value.equalsIgnoreCase("NO"))
                            FieldRequired.add("true");
                        else
                            FieldRequired.add("");
                    }
                    else
                        FieldRequired.add("");

                }

                int currentIndex=DataTypes.size()-1;
                if(DataTypes.get(currentIndex).equalsIgnoreCase("DATE"))
                    FieldLength.set(currentIndex,"");
            }
        }
    }

    public static void getColumnIndex(Row row) {

        for (int colIndex = 0; colIndex < row.getLastCellNum(); colIndex++) {

            Cell cell=row.getCell(colIndex);

            if(cell!=null) {

                String name = String.valueOf(df.formatCellValue(cell));
                if (name.equals("PHYSICAL NAME"))
                    nameindex = colIndex;
                else if (name.equals("DATA TYPE"))
                    datatypeindex = colIndex;
                else if (name.contains("FORMAT"))
                    lengthindex = colIndex;
                else if (name.equals("NULLABLE"))
                    requiredindex = colIndex;
            }

        }

    }

    public static List<String> getDataTypes() {
        return DataTypes;
    }

    public static List<String> getFieldNames() {
        return FieldNames;
    }

    public static List<String> getFieldLength() {
        return FieldLength;
    }

    public static List<String> getFieldRequired() {
        return FieldRequired;
    }


}



