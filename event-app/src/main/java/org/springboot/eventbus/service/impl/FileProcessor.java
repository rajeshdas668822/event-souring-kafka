package org.springboot.eventbus.service.impl;

import org.springboot.eventbus.domain.FileDto;

/**
 * Created by rdas on 1/15/2017.
 */
public interface FileProcessor {
    public void processFile(FileDto fileDto);
}
