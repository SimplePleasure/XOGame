package com.game.trial.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.game.trial.base.IResponse;

import java.util.HashMap;
import java.util.Map;

public class ResponseTemplate implements IResponse {

    private boolean result;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> hints;

    public ResponseTemplate(boolean result) {
        this.result = result;
        hints = new HashMap<>();
    }

    public boolean isResult() {
        return result;
    }
    public void setResult(boolean result) {
        this.result = result;
    }
    public Map<String, String> getHints() {
        return hints;
    }
    public void setHints(Map<String, String> hints) {
        this.hints = hints;
    }
    public ResponseTemplate addHint(String k, String v) {
        hints.put(k, v);
        return this;
    }
}
