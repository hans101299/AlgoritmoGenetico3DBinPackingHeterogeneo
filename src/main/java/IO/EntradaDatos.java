package IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import estructuras.Contenedor;
import estructuras.Paquete;
import estructuras.Rotacion;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EntradaDatos {

    public static ArrayList<Paquete> leerDatosPaquetes() throws IOException, InvalidFormatException {
        ArrayList<Paquete> paquetes = new ArrayList<>();
        File pkg = new File("src\\main\\java\\IO\\data\\entrada.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(pkg);
        XSSFSheet sheet = wb.getSheet("PAQUETES");     //creating a Sheet object to retrieve object
        Iterator<Row> itr = sheet.iterator();
        itr.next();
        while (itr.hasNext())
        {
            ArrayList<Rotacion> rotaciones = new ArrayList<Rotacion>();
            Row row = itr.next();
            if(row.getCell(0) == null || row.getCell(0).getCellType() == CellType.BLANK){
                break;
            }
            rotaciones.add(new Rotacion((float) row.getCell(1).getNumericCellValue(),
                    (float) row.getCell(2).getNumericCellValue(), (float) row.getCell(3).getNumericCellValue(),
                    (float) row.getCell(4).getNumericCellValue()));
            for (int i = 6; true ; i+=3) {
                if(row.getCell(i) == null || row.getCell(i).getCellType() == CellType.BLANK){
                    break;
                }
                try {
                    rotaciones.add(new Rotacion((float) row.getCell(i).getNumericCellValue(),
                            (float) row.getCell(i+1).getNumericCellValue(), (float) row.getCell(i+2).getNumericCellValue(),
                            (float) row.getCell(4).getNumericCellValue()));
                }
                catch (Exception e){
                    System.out.println("Error en la cantidad de datos de una rotación");
                }
            }
            paquetes.add(new Paquete(row.getCell(0).getStringCellValue(),(float) row.getCell(1).getNumericCellValue(),
                    (float) row.getCell(2).getNumericCellValue(), (float) row.getCell(3).getNumericCellValue(),
                    (float) row.getCell(4).getNumericCellValue(),(float) row.getCell(5).getNumericCellValue(),
                    rotaciones
                    ));
        }
        return paquetes;
    }

    public static ArrayList<Contenedor> leerDatosContenedores() throws IOException, InvalidFormatException {
        ArrayList<Contenedor> contenedores = new ArrayList<>();
        ArrayList<Rotacion> rotaciones = new ArrayList<Rotacion>();
        File file = new File("src\\main\\java\\IO\\data\\entrada.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(file);
        XSSFSheet sheet = wb.getSheet("CONTENEDORES");     //creating a Sheet object to retrieve object
        Iterator<Row> itr = sheet.iterator();
        itr.next();
        while (itr.hasNext())
        {
            Row row = itr.next();
            if(row.getCell(0) == null || row.getCell(0).getCellType() == CellType.BLANK){
                break;
            }

            contenedores.add(new Contenedor((float) row.getCell(1).getNumericCellValue(),
                    (float) row.getCell(2).getNumericCellValue(), (float) row.getCell(3).getNumericCellValue(),
                    (float) row.getCell(4).getNumericCellValue()
            ));
        }
        return contenedores;
    }

}
