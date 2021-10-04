package com.bootcampspringreact.desafio01.service.exceptions;

public class ResourceNotFoundException extends RuntimeException
{
    private static final long serialVersionUID = 5573572785382419086L;

    public ResourceNotFoundException(String msg)
    {
        super(msg);
    }
}
