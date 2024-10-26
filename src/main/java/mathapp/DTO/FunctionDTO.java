package mathapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FunctionDTO {
    private Long  functionId;
    private String name;
    private Double xFrom;
    private Double xTo;
    private Integer count;

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("xFrom")
    public Double getXFrom() {
        return xFrom;
    }

    public void setXFrom(Double xFrom) {
        this.xFrom = xFrom;
    }

    @JsonProperty("xTo")
    public Double getXTo() {
        return xTo;
    }

    public void setXTo(Double xTo) {
        this.xTo = xTo;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}