package presentation.helper;

// https://en.wikipedia.org/wiki/Delegation_pattern

import entity.TransferObject;

public interface Helper {
    TransferObject execute(TransferObject to);
}