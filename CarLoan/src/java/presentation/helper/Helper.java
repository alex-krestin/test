package presentation.helper;

// https://en.wikipedia.org/wiki/Delegation_pattern

import entity.Response;
import entity.TransferObject;

public interface Helper {
    Response execute(TransferObject to);
}