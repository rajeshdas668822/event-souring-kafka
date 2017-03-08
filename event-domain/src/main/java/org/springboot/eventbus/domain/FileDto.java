package org.springboot.eventbus.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.InputStream;

/**
 * Created by rdas on 1/15/2017.
 */
@Getter @Setter @NoArgsConstructor
public class FileDto {
    private String fileExt;
    private String fileName;
    private InputStream inputStream;
}
