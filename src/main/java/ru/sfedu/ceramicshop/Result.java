package ru.sfedu.ceramicshop;

import ru.sfedu.ceramicshop.models.enums.Outcomes;

public class Result<T> {
    private T data;
    private String answer;
    private ru.sfedu.ceramicshop.models.enums.Outcomes status;

    public Result(ru.sfedu.ceramicshop.models.enums.Outcomes status, T data, String answer) {
        this.status = status;
        this.answer = answer;
        this.data = data;
    }

    public Result(ru.sfedu.ceramicshop.models.enums.Outcomes status, T data) {
        this.status = status;
        this.data = data;
    }

    public Result(ru.sfedu.ceramicshop.models.enums.Outcomes status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ru.sfedu.ceramicshop.models.enums.Outcomes getStatus() {
        return status;
    }

    public void setStatus(ru.sfedu.ceramicshop.models.enums.Outcomes status) {
        this.status = status;
    }
}
