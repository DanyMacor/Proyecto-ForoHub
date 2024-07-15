package com.forohub.ForoHub.exception;

import org.springframework.dao.DataAccessException;

import java.io.IOException;

public class MalaRespuesta {
    public MalaRespuesta(String s) {
        super(s);
    }
    public MalaRespuesta(String s, DataAccessException e) {
        super(s,e);
    }

    public MalaRespuesta(String s, IOException e) {
        super(s,e);
    }
}
