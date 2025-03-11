package com.sooft.test.service;

import com.sooft.test.dto.TransferDTO;
import com.sooft.test.exception.CompanyNotFoundException;

public interface TransferService {

    TransferDTO addTransfer(TransferDTO transferDto) throws CompanyNotFoundException;

}