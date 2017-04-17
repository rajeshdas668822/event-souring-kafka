package org.springboot.eventbus.service;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.component.restlet.RestletConsumer;
import org.apache.camel.component.restlet.RestletProducer;
import org.apache.camel.converter.stream.InputStreamCache;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by rdas on 3/28/2017.
 */

@Service("fileUploader")
public class FileUploader {

    public void upload(Exchange exchange) {

        MultipartFile multipartFile = exchange.getIn().getBody(MultipartFile.class);
        try {
            readXlsxFile(multipartFile);
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }


    public void readStreamXlsx(Exchange exchange){
        // System.out.println("!!!!Headers " + exchange.getIn().getHeaders());
        //  System.out.println("!!!!Body " + exchange.getIn().getBody());



        Workbook workbook = StreamingReader.builder()
                .rowCacheSize(100)    // number of rows to keep in memory (defaults to 10)
                .bufferSize(4096)     // buffer size to use when reading InputStream to file (defaults to 1024)
                .open(exchange.getIn().getBody(InputStreamCache.class));


        for (Sheet sheet : workbook) {
            System.out.println(sheet.getSheetName());




            int i =0;

            for (Row r : sheet) {
                if(i ==0){
                    System.out.print("Printing header...");
                    for (Cell c : r) {
                        System.out.println(c.getStringCellValue());
                    }
                    i++;

                }else {


                    System.out.print("Printing values...");
                    for (Cell c : r) {
                        System.out.println(c.getStringCellValue());
                    }
                }
            }
        }
    }


    public void readXlsxFile (MultipartFile multipartFile) throws Exception{

        InputStream inputStream = multipartFile.getInputStream();

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        System.out.print(cell.getStringCellValue());
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.print(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print(cell.getNumericCellValue());
                        break;
                }
                System.out.print(" - ");
            }
            System.out.println();
        }

        workbook.close();
        inputStream.close();
    }

}
