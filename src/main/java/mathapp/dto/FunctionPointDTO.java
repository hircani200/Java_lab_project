package mathapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FunctionPointDTO {
    private Long pointId;
    private Long functionId;
    private Double xValue;
    private Double yValue;

    public Long getPointId() {
        return pointId;
    }

    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    @JsonProperty("xValue")
    public Double getXValue() {
        return xValue;
    }

    public void setXValue(Double xValue) {
        this.xValue = xValue;
    }

    @JsonProperty("yValue")
    public Double getYValue() {
        return yValue;
    }

    public void setYValue(Double yValue) {
        this.yValue = yValue;
    }
}
