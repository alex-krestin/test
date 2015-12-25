package it.uniba.carloan.presentation.helper;

// https://en.wikipedia.org/wiki/Delegation_pattern

import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;

public interface Helper {
    Response execute(TransferObject to);
}