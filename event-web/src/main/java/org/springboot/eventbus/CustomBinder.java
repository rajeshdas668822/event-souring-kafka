package org.springboot.eventbus;

import org.apache.camel.Exchange;
import org.apache.camel.component.http.DefaultHttpBinding;
import org.apache.camel.component.http.HttpMessage;
import org.apache.camel.component.restlet.DefaultRestletBinding;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.ext.fileupload.RestletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by rdas on 3/29/2017.
 */





@Service("custumBinder")
public class CustomBinder extends DefaultHttpBinding {

    @Autowired
    private CommonsMultipartResolver commonsMultipartResolver;

    @Override
    public void readRequest(HttpServletRequest request, HttpMessage message) {
        super.readRequest(request, message);
        MultipartHttpServletRequest multipartHttpServletRequest =  commonsMultipartResolver.resolveMultipart(request);
        //String fileName = multipartHttpServletRequest.getHeaderNames()
       //
        Map<String, MultipartFile> multipartFileMap = multipartHttpServletRequest.getFileMap();
        MultipartFile multipartFile =  multipartFileMap.get("file");
        message.setBody(multipartFile);
        message.getHeaders().put("FileName",multipartFile.getOriginalFilename());

    }
}
