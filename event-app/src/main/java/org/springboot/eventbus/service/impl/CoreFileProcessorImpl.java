package org.springboot.eventbus.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springboot.eventbus.domain.FileDto;

/**
 * Created by rdas on 1/15/2017.
 */
@Slf4j
public class CoreFileProcessorImpl implements FileProcessor {
    @Override
    public void processFile(FileDto fileDto) {
      log.info("Inside CoreFileProcessorImpl :: File Name :{}, File extention :{}",
              fileDto.getFileName(),fileDto.getFileExt());

    }
}
