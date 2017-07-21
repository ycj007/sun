package com;

import com.com.ycj.AppleBox;
import com.com.ycj.GenericityBoxIml;
import com.com.ycj.io.FileProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static com.com.ycj.io.FileProvider.*;

/**
 * Created by Mtime on 2017/7/10.
 */
public class Run {
    public static void main(String[] args) throws IOException {
        File inFile = getInFile();
        File outFile = getOutFile();


        //FileProvider.printContent(inFile,1);
        FileProvider.fileCopy4(inFile,outFile);
        //Files.copy(inFile.toPath(), outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

    }



}
