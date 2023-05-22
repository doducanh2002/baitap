package org.up.finance.exception;

import org.up.finance.exception.base.BadRequestException;

public class WalletGroupParentConstraintException extends BadRequestException {

    public WalletGroupParentConstraintException(String id) {
        addParam("id", id);
        setCode("org.up.finance.exception.WalletGroupParentConstraintException");
    }
}
